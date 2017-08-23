package io.fcmchannel.sdk.chat;

import android.os.Bundle;
import android.text.TextUtils;

import java.util.Date;
import java.util.List;

import io.fcmchannel.sdk.FcmClient;
import io.fcmchannel.sdk.R;
import io.fcmchannel.sdk.core.managers.FlowRunnerManager;
import io.fcmchannel.sdk.core.models.Flow;
import io.fcmchannel.sdk.core.models.FlowDefinition;
import io.fcmchannel.sdk.core.models.FlowRuleset;
import io.fcmchannel.sdk.core.models.FlowRun;
import io.fcmchannel.sdk.core.models.FlowStep;
import io.fcmchannel.sdk.core.models.Message;
import io.fcmchannel.sdk.core.models.Type;
import io.fcmchannel.sdk.core.models.TypeValidation;
import io.fcmchannel.sdk.core.models.network.ApiResponse;
import io.fcmchannel.sdk.core.models.v2.Contact;
import io.fcmchannel.sdk.core.network.RestServices;
import io.fcmchannel.sdk.util.BundleHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by john-mac on 6/30/16.
 */
class FcmClientChatPresenter {

    private final FcmClientChatView view;
    private final RestServices services;

    FcmClientChatPresenter(FcmClientChatView view) {
        this.view = view;
        this.services = new RestServices(FcmClient.getHost(), FcmClient.getToken());
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
            services.loadRuns(FcmClient.getContact().getUuid()).enqueue(new Callback<ApiResponse<FlowRun>>() {
                @Override
                public void onResponse(Call<ApiResponse<FlowRun>> call, Response<ApiResponse<FlowRun>> response) {
                    if (hasLoadedSuccessfullyFlowRun(response)) {
                        FlowRun flowRun = response.body().getResults().get(0);
                        getLastRulesetFromFlowRun(flowRun);
                    }
                }

                private boolean hasLoadedSuccessfullyFlowRun(Response<ApiResponse<FlowRun>> response) {
                    return response.isSuccessful() && response.body().getResults() != null && response.body().getResults().size() > 0;
                }

                @Override
                public void onFailure(Call<ApiResponse<FlowRun>> call, Throwable t) { /* Do nothing */}
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
        services.loadFlowDefinition(flowRun.getFlow().getUuid()).enqueue(new Callback<FlowDefinition>() {
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

            @Override
            public void onFailure(Call<FlowDefinition> call, Throwable t) { /* Do nothing */ }

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
        services.loadContactV2(urn).enqueue(new FcmClientCallback<ApiResponse<Contact>>(this) {
            @Override
            public void onResponse(Call<ApiResponse<Contact>> call, Response<ApiResponse<Contact>> response) {
                view.dismissLoading();
                if (response.isSuccessful() && response.body() != null && response.body().getResults() != null) {
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
        view.onMessagesLoaded(messages);
    }

    void loadMessage(Bundle data) {
        Message message = BundleHelper.getMessage(data);
        message.setCreatedOn(new Date());
        view.onMessageLoaded(message);
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
