package io.rapidpro.sdk.chat;

import android.os.Bundle;
import android.text.TextUtils;

import java.util.Date;
import java.util.List;

import io.rapidpro.sdk.FcmClient;
import io.rapidpro.sdk.R;
import io.rapidpro.sdk.core.managers.FlowRunnerManager;
import io.rapidpro.sdk.core.models.Contact;
import io.rapidpro.sdk.core.models.Flow;
import io.rapidpro.sdk.core.models.FlowDefinition;
import io.rapidpro.sdk.core.models.FlowRuleset;
import io.rapidpro.sdk.core.models.FlowRun;
import io.rapidpro.sdk.core.models.FlowStep;
import io.rapidpro.sdk.core.models.Message;
import io.rapidpro.sdk.core.models.Type;
import io.rapidpro.sdk.core.models.TypeValidation;
import io.rapidpro.sdk.core.models.network.ApiResponse;
import io.rapidpro.sdk.core.network.RapidProServices;
import io.rapidpro.sdk.util.BundleHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by john-mac on 6/30/16.
 */
class FcmClientChatPresenter {

    private final FcmClientChatView view;
    private final RapidProServices services;

    FcmClientChatPresenter(FcmClientChatView view) {
        this.view = view;
        this.services = new RapidProServices(FcmClient.getHost(), FcmClient.getToken());
    }

    void loadMessages() {
        if (FcmClient.isContactRegistered()) {
            String contactUuid = FcmClient.getContact().getUuid();
            if (!TextUtils.isEmpty(contactUuid)) {
                loadMessagesWithContact(contactUuid);
            } else {
                loadContact();
            }
        }
    }

    void loadCurrentRulesets() {
        Message message = view.getLastMessage();
        if (message != null) {
            services.loadRuns(FcmClient.getContact().getUuid()).enqueue(new FcmClientCallback<ApiResponse<FlowRun>>(this) {
                @Override
                public void onResponse(Call<ApiResponse<FlowRun>> call, Response<ApiResponse<FlowRun>> response) {
                    if (hasLoadedSuccessfullyFlowRun(response)) {
                        FlowRun flowRun = response.body().getResults().get(0);
                        getLastRulesetFromFlowRun(flowRun);
                    } else {
                        view.showMessage(R.string.fcm_client_error_load_messages);
                    }
                }

                private boolean hasLoadedSuccessfullyFlowRun(Response<ApiResponse<FlowRun>> response) {
                    return response.isSuccessful() && response.body().getResults() != null && response.body().getResults().size() > 0;
                }
            });
        }
    }

    private void getLastRulesetFromFlowRun(FlowRun flowRun) {
        List<FlowStep> flowSteps = flowRun.getPath();

        if (isValidFlowRun(flowRun, flowSteps)) {
            final FlowStep latestFlowStep = flowSteps.get(flowSteps.size() - 1);
            loadFlow(flowRun, latestFlowStep);
        }
    }

    private boolean isValidFlowRun(FlowRun flowRun, List<FlowStep> flowSteps) {
        return FlowRunnerManager.isFlowActive(flowRun) && flowSteps != null && flowSteps.size() > 0;
    }

    private void loadFlow(FlowRun flowRun, final FlowStep flowStep) {
        services.loadFlowDefinition(flowRun.getFlow().getUuid()).enqueue(new FcmClientCallback<FlowDefinition>(this) {
            @Override
            public void onResponse(Call<FlowDefinition> call, Response<FlowDefinition> response) {
                if (hasLoadedSuccessfullyFlows(response)) {
                    FlowDefinition definition = response.body();
                    Flow flow = definition.getFlows().get(0);

                    getRulesetFromFlow(flowStep, flow);
                }
            }

            private boolean hasLoadedSuccessfullyFlows(Response<FlowDefinition> response) {
                return response.isSuccessful() && response.body() != null
                    && response.body().getFlows() != null && response.body().getFlows().size() > 0;
            }
        });
    }

    private void getRulesetFromFlow(FlowStep flowStep, Flow flow) {
        int indexOfRuleset = flow.getRuleSets().indexOf(new FlowRuleset(flowStep.getNode()));

        if (indexOfRuleset >= 0) {
            FlowRuleset ruleset = flow.getRuleSets().get(indexOfRuleset);
            view.setCurrentRulesets(ruleset);
        }
    }

    private void loadContact() {
        view.showLoading();

        String urn = FcmClient.URN_PREFIX_FCM + FcmClient.getPreferences().getUrn();
        services.loadContactsByUrn(urn).enqueue(new FcmClientCallback<ApiResponse<Contact>>(this) {
            @Override
            public void onResponse(Call<ApiResponse<Contact>> call, Response<ApiResponse<Contact>> response) {
                view.dismissLoading();
                if (response.isSuccessful() && response.body().getCount() > 0) {
                    Contact contact = response.body().getResults().get(0);
                    loadMessagesWithContact(contact.getUuid());
                } else {
                    view.showMessage(R.string.fcm_client_error_load_messages);
                }
            }
        });
    }

    void onRequestFailed() {
        view.dismissLoading();
        view.showMessage(R.string.fcm_client_error_load_messages);
    }

    private void loadMessagesWithContact(String contactUuid) {
        view.showLoading();
        services.loadMessages(contactUuid).enqueue(new Callback<ApiResponse<Message>>() {
            @Override
            public void onResponse(Call<ApiResponse<Message>> call, Response<ApiResponse<Message>> response) {
                view.dismissLoading();
                if (response.isSuccessful()) {
                    onMessagesLoaded(response.body().getResults());
                } else {
                    view.showMessage(R.string.fcm_client_error_load_messages);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Message>> call, Throwable throwable) {
                onRequestFailed();
            }
        });
    }

    private void onMessagesLoaded(List<Message> messages) {
        FcmClient.getPreferences().setUnreadMessages(0).apply();
        view.onMessagesLoaded(messages);
    }

    void loadMessage(Bundle data) {
        view.onMessageLoaded(BundleHelper.getMessage(data));
    }

    Message createChatMessage(String messageText) {
        Message chatMessage = new Message();
        setId(chatMessage);
        chatMessage.setText(messageText);
        chatMessage.setCreatedOn(new Date());
        chatMessage.setDirection(Message.DIRECTION_INCOMING);
        return chatMessage;
    }

    private void setId(Message chatMessage) {
        Message lastMessage = view.getLastMessage();
        if (lastMessage != null) {
            chatMessage.setId(lastMessage.getId()+1);
        } else {
            chatMessage.setId(0);
        }
    }

    Type getFirstType(FlowRuleset ruleset) {
        return TypeValidation.getTypeValidationForRule(ruleset.getRules().get(0)).getType();
    }

    public void sendMessage(String messageText) {
        view.addNewMessage(messageText);
        FcmClient.sendMessage(messageText);
    }
}
