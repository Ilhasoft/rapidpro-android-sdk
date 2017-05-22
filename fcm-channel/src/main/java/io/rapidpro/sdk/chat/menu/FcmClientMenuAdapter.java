package io.rapidpro.sdk.chat.menu;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import io.mattcarroll.hover.HoverMenuAdapter;
import io.mattcarroll.hover.NavigatorContent;
import io.rapidpro.sdk.FcmClient;
import io.rapidpro.sdk.R;
import io.rapidpro.sdk.util.BitmapHelper;

/**
 * Created by John Cordeiro on 5/11/17.
 * Copyright © 2017 rapidpro-android-sdk, Inc. All rights reserved.
 */

class FcmClientMenuAdapter implements HoverMenuAdapter {

    private final Context mContext;
    private final FcmClientNavigatorContent fcmClientNavigatorContent;
    private final int badgeCount;

    private TextView badge;

    FcmClientMenuAdapter(@NonNull Context context, int badgeCount) {
        mContext = context.getApplicationContext();
        fcmClientNavigatorContent = new FcmClientNavigatorContent(context);
        this.badgeCount = badgeCount;
    }

    @Override
    public int getTabCount() {
        return 1;
    }

    @Override
    public long getTabId(int position) {
        return position;
    }

    @Override
    public View getTabView(int position) {
        int iconResource = FcmClient.getUiConfiguration().getIconFloatingChat();
        return createTabView(iconResource);
    }

    @Override
    public NavigatorContent getNavigatorContent(int position) {
        return fcmClientNavigatorContent;
    }

    @Override
    public void addContentChangeListener(@NonNull ContentChangeListener listener) {}

    @Override
    public void removeContentChangeListener(@NonNull ContentChangeListener listener) {}

    private View createTabView(@DrawableRes int tabBitmapRes) {
        View tabView = LayoutInflater.from(mContext).inflate(R.layout.fcm_client_tab_view, null);
        badge = (TextView) tabView.findViewById(R.id.badge);
        setBadgeCount(badgeCount);

        RoundedBitmapDrawable roundedBitmap = BitmapHelper.getRoundedBitmap(mContext, tabBitmapRes, getRadius());
        ImageView icon = (ImageView) tabView.findViewById(R.id.icon);
        icon.setImageDrawable(roundedBitmap);
        return tabView;
    }

    private int getRadius() {
        return (int) mContext.getResources().getDimension(R.dimen.fcm_client_chat_icon_radius);
    }

    void setBadgeCount(int badgeCount) {
        if (badge != null) {
            if (badgeCount > 0) {
                badge.setVisibility(View.VISIBLE);
                badge.setText(String.valueOf(badgeCount));
            } else {
                badge.setVisibility(View.GONE);
                badge.setText(String.valueOf(0));
            }
        }
    }

}
