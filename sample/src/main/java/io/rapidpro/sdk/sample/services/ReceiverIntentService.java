package io.rapidpro.sdk.sample.services;

import android.support.v4.app.NotificationCompat;

import io.rapidpro.sdk.services.FcmClientIntentService;

/**
 * Created by john-mac on 6/29/16.
 */
public class ReceiverIntentService extends FcmClientIntentService {

    @Override
    public void onCreateLocalNotification(NotificationCompat.Builder mBuilder) {
        mBuilder.setSmallIcon(io.rapidpro.sdk.R.drawable.fcm_client_ic_send_message);
        super.onCreateLocalNotification(mBuilder);
    }
}
