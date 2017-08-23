package io.fcmchannel.sdk.sample.services;

import android.support.v4.app.NotificationCompat;

import io.fcmchannel.sdk.services.FcmClientIntentService;

/**
 * Created by john-mac on 6/29/16.
 */
public class ReceiverIntentService extends FcmClientIntentService {

    @Override
    public void onCreateLocalNotification(NotificationCompat.Builder mBuilder) {
        mBuilder.setSmallIcon(io.fcmchannel.sdk.R.drawable.fcm_client_ic_send_message);
        super.onCreateLocalNotification(mBuilder);
    }
}
