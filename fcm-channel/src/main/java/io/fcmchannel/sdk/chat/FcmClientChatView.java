package io.fcmchannel.sdk.chat;

import java.util.List;

import io.fcmchannel.sdk.core.models.FlowRuleset;
import io.fcmchannel.sdk.core.models.Message;

/**
 * Created by john-mac on 6/30/16.
 */
interface FcmClientChatView {

    void addNewMessage(String messageText);

    void onMessagesLoaded(List<Message> messages);

    void onMessageLoaded(Message message);

    void showLoading();

    void dismissLoading();

    void showMessage(int messageId);

    void showMessage(String message);

    void setCurrentRulesets(FlowRuleset rulesets);

    Message getLastMessage();

}
