package io.rapidpro.sdk.listeners;

import io.rapidpro.sdk.core.models.Message;

/**
 * Created by john-mac on 6/29/16.
 */
public interface LoadMessageListener extends ErrorListener {

    void onMessageLoaded(Message message);

}
