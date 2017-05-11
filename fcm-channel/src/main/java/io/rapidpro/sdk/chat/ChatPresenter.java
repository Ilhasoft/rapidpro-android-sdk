package io.rapidpro.sdk.chat;

import android.os.Bundle;
import android.text.TextUtils;

import java.util.Date;
import java.util.List;

import io.rapidpro.sdk.FcmClient;
import io.rapidpro.sdk.core.models.Message;
import io.rapidpro.sdk.core.models.Type;
import io.rapidpro.sdk.core.models.TypeValidation;
import io.rapidpro.sdk.listeners.LoadMessageListener;
import io.rapidpro.sdk.listeners.MessagesLoadingListener;
import io.rapidpro.sdk.util.BundleHelper;

/**
 * Created by john-mac on 6/30/16.
 */
public class ChatPresenter {

    private ChatView view;

    public ChatPresenter(ChatView view) {
        this.view = view;
    }

    public void loadMessages() {
        if (!TextUtils.isEmpty(FcmClient.getPreferences().getFcmToken())) {
            view.showLoading();
            FcmClient.loadMessages(new MessagesLoadingListener() {
                @Override
                public void onMessagesLoaded(List<Message> messages) {
                    view.dismissLoading();
                    view.onMessagesLoaded(messages);
                }

                @Override
                public void onError(Throwable exception, String message) {
                    view.dismissLoading();
                    view.showMessage(message);
                }
            });
        }
    }

    public void loadMessage(Bundle data) {
        FcmClient.loadMessage(BundleHelper.getMessageId(data), new LoadMessageListener() {
            @Override
            public void onMessageLoaded(Message message) {
                view.onMessageLoaded(message);
            }

            @Override
            public void onError(Throwable exception, String message) {
                view.showMessage(message);
            }
        });
    }

    public Message createChatMessage(String messageText) {
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

    public Type getFirstType(Message lastMessage) {
        return TypeValidation.getTypeValidationForRule(lastMessage.getRuleset()
                .getRules().get(0)).getType();
    }

    public void sendMessage(String messageText) {
        view.addNewMessage(messageText);
        FcmClient.sendMessage(messageText);
    }

}
