package io.rapidpro.sdk.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Html;
import android.text.TextUtils;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import io.rapidpro.sdk.FcmClient;
import io.rapidpro.sdk.chat.FcmClientChatActivity;
import io.rapidpro.sdk.chat.menu.FcmClientMenuService;
import io.rapidpro.sdk.persistence.Preferences;
import io.rapidpro.sdk.util.BundleHelper;

/**
 * Created by john-mac on 6/29/16.
 */
public class FcmClientIntentService extends FirebaseMessagingService {

    public static final String ACTION_MESSAGE_RECEIVED = "io.rapidpro.sdk.MESSAGE_RECEIVED";
    private static final String TYPE_RAPIDPRO = "rapidpro";

    private static final int NOTIFICATION_ID = 30;

    public static final String KEY_DATA = "data";
    private static final String KEY_TYPE = "type";
    private static final String KEY_MESSAGE = "message";
    private static final String KEY_TITLE = "title";

    @Override
    public final void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Map<String, String> data = remoteMessage.getData();
        String type = data.get(KEY_TYPE);

        if (isRapidproType(type)) {
            Intent pushReceiveIntent = new Intent(ACTION_MESSAGE_RECEIVED);
            pushReceiveIntent.putExtra(KEY_DATA, BundleHelper.convertToBundleFrom(data));

            if (!FcmClient.isChatVisible()) {
                int unreadMessages = increaseUnreadMessages();
                if (!FcmClientMenuService.isVisible()) {
                    showFloatingMenu(unreadMessages);
                }
                showLocalNotification(data.get(KEY_TITLE), data.get(KEY_MESSAGE));
            }
            LocalBroadcastManager.getInstance(this).sendBroadcast(pushReceiveIntent);
        }
    }

    private void showFloatingMenu(int unreadMessages) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Settings.canDrawOverlays(this)) {
                FcmClientMenuService.showFloatingMenu(getApplicationContext(), unreadMessages);
            }
        } else {
            FcmClientMenuService.showFloatingMenu(getApplicationContext(), unreadMessages);
        }
    }

    private int increaseUnreadMessages() {
        Preferences preferences = FcmClient.getPreferences();
        int newUnreadMessages = preferences.getUnreadMessages() + 1;
        preferences.setUnreadMessages(newUnreadMessages);
        preferences.commit();

        return newUnreadMessages;
    }

    private void showLocalNotification(String title, String message) {
        message = handleNotificationMessage(message);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(getApplicationContext())
                        .setSmallIcon(getApplicationInfo().icon)
                        .setContentTitle(title)
                        .setContentText(message)
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(message))
                        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                        .setAutoCancel(true);
        mBuilder.setContentIntent(createPendingIntent());
        onCreateLocalNotification(mBuilder);
    }

    protected void onCreateLocalNotification(NotificationCompat.Builder builder) {
        NotificationManager mNotificationManager =
                (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    private String handleNotificationMessage(String message) {
        return !TextUtils.isEmpty(message) ? Html.fromHtml(message).toString() : message;
    }

    private boolean isRapidproType(String type) {
        return type != null && type.equals(TYPE_RAPIDPRO);
    }

    private PendingIntent createPendingIntent() {
        Intent chatIntent = new Intent(this, FcmClientChatActivity.class);
        chatIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return PendingIntent.getActivity(this, 0, chatIntent, PendingIntent.FLAG_CANCEL_CURRENT);
    }
}
