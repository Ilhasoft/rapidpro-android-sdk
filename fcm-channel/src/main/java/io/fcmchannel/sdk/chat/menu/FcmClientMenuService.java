package io.fcmchannel.sdk.chat.menu;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.view.ContextThemeWrapper;

import io.mattcarroll.hover.HoverMenuAdapter;
import io.mattcarroll.hover.Navigator;
import io.mattcarroll.hover.defaulthovermenu.DefaultNavigator;
import io.mattcarroll.hover.defaulthovermenu.window.HoverMenuService;
import io.fcmchannel.sdk.FcmClient;
import io.fcmchannel.sdk.R;
import io.fcmchannel.sdk.persistence.Preferences;
import io.fcmchannel.sdk.services.FcmClientIntentService;

/**
 * Created by John Cordeiro on 5/11/17.
 */

public class FcmClientMenuService extends HoverMenuService {

    public static final String EXTRA_UNREAD_MESSAGES = "unreadMessages";

    private static boolean visible = false;
    private static boolean expanded = false;

    private FcmClientMenuAdapter menuAdapter;
    private int unreadMessages = 0;

    public static void showFloatingMenu(Context context) {
        showFloatingMenu(context, FcmClient.getPreferences().getUnreadMessages());
    }

    public static void showFloatingMenu(Context context, int unreadMessages) {
        Intent intent = new Intent(context, FcmClientMenuService.class);
        intent.putExtra(EXTRA_UNREAD_MESSAGES, unreadMessages);
        context.startService(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            unreadMessages = intent.getIntExtra(EXTRA_UNREAD_MESSAGES, 0);
            setUnreadMessages(unreadMessages);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        visible = true;
        unreadMessages = FcmClient.getUnreadMessages();

        IntentFilter intentFilter = new IntentFilter(FcmClientIntentService.ACTION_MESSAGE_RECEIVED);
        LocalBroadcastManager.getInstance(this).registerReceiver(onMessageReceiver, intentFilter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        visible = false;

        LocalBroadcastManager.getInstance(this).unregisterReceiver(onMessageReceiver);
    }

    @Override
    public void onHoverMenuCollapsed() {
        expanded = false;
    }

    @Override
    public void onHoverMenuExpanded() {
        expanded = true;

        FcmClient.getPreferences().setUnreadMessages(0).commit();
        setUnreadMessages(0);
    }

    @Override
    protected Context getContextForHoverMenu() {
        return new ContextThemeWrapper(this, R.style.AppTheme);
    }

    @Override
    protected HoverMenuAdapter createHoverMenuAdapter() {
        menuAdapter = new FcmClientMenuAdapter(getApplicationContext(), unreadMessages);
        return menuAdapter;
    }

    @Override
    protected Navigator createNavigator() {
        return new DefaultNavigator(getApplicationContext(), false);
    }

    private BroadcastReceiver onMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (!expanded) {
                unreadMessages = FcmClient.getPreferences().getUnreadMessages();
                setUnreadMessages(unreadMessages);
            }
        }
    };

    private void setUnreadMessages(int badgeCount) {
        if (menuAdapter != null) {
            menuAdapter.setBadgeCount(badgeCount);
        }
    }

    public static boolean isVisible() {
        return visible;
    }

    public static boolean isExpanded() {
        return expanded;
    }
}
