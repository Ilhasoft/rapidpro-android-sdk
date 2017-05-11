package io.rapidpro.sdk.chat;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import java.util.Date;

import io.rapidpro.sdk.FcmClient;
import io.rapidpro.sdk.R;
import io.rapidpro.sdk.core.models.Contact;
import io.rapidpro.sdk.core.models.FlowRuleset;
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

    }

    private void loadContact() {
        view.showLoading();

        String urn = "fcm:" + FcmClient.getPreferences().getUrn();
        services.loadContactsByUrn(urn).enqueue(new Callback<ApiResponse<Contact>>() {
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

            @Override
            public void onFailure(Call<ApiResponse<Contact>> call, Throwable throwable) {
                view.dismissLoading();
                view.showMessage(R.string.fcm_client_error_load_messages);
            }
        });
    }

    private void loadMessagesWithContact(String contactUuid) {
        view.showLoading();
        services.loadMessages(contactUuid).enqueue(new Callback<ApiResponse<Message>>() {
            @Override
            public void onResponse(Call<ApiResponse<Message>> call, Response<ApiResponse<Message>> response) {
                view.dismissLoading();
                if (response.isSuccessful()) {
                    view.onMessagesLoaded(response.body().getResults());
                } else {
                    view.showMessage(R.string.fcm_client_error_load_messages);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Message>> call, Throwable throwable) {
                view.dismissLoading();
                view.showMessage(R.string.fcm_client_error_load_messages);
            }
        });
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
