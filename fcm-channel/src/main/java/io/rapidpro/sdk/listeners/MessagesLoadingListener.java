package io.rapidpro.sdk.listeners;

import java.util.List;

import io.rapidpro.sdk.core.models.Message;

/**
 * Created by john-mac on 6/28/16.
 */
public interface MessagesLoadingListener extends ErrorListener {

    void onMessagesLoaded(List<Message> messages);

}
