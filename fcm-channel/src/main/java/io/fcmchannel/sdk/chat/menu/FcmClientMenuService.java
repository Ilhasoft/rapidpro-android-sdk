package io.fcmchannel.sdk.chat.menu;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.view.ContextThemeWrapper;

import io.fcmchannel.sdk.FcmClient;
import io.fcmchannel.sdk.R;
import io.fcmchannel.sdk.services.FcmClientIntentService;
import io.mattcarroll.hover.HoverView;
import io.mattcarroll.hover.window.HoverMenuService;

/**
 * Created by John Cordeiro on 5/11/17.
 */

public class FcmClientMenuService extends HoverMenuService {

    public static final String EXTRA_UNREAD_MESSAGES = "unreadMessages";

    private static boolean visible = false;
    private static boolean expanded = false;

    private FcmClientMenu menu;
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
    protected void onHoverMenuLaunched(@NonNull Intent intent, @NonNull HoverView hoverView) {
        super.onHoverMenuLaunched(intent, hoverView);
        menu = new FcmClientMenu(getApplicationContext(), unreadMessages);
        hoverView.setMenu(menu);
        hoverView.addOnExpandAndCollapseListener(onExpandAndCollapseListener);
        hoverView.collapse();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        visible = false;

        LocalBroadcastManager.getInstance(this).unregisterReceiver(onMessageReceiver);
    }

    @Override
    protected Context getContextForHoverMenu() {
        return new ContextThemeWrapper(this, R.style.AppTheme);
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
        if (menu != null) {
            menu.setBadgeCount(badgeCount);
        }
    }

    public static boolean isVisible() {
        return visible;
    }

    public static boolean isExpanded() {
        return expanded;
    }

    private HoverView.Listener onExpandAndCollapseListener = new HoverView.Listener() {
        @Override
        public void onExpanding() {}

        @Override
        public void onExpanded() {
            expanded = true;
            FcmClient.getPreferences().setUnreadMessages(0).commit();
            setUnreadMessages(0);
        }

        @Override
        public void onCollapsing() {}

        @Override
        public void onCollapsed() {
            expanded = false;
        }

        @Override
        public void onClosing() {}

        @Override
        public void onClosed() {}
    };

}
