package io.fcmchannel.sdk.chat.menu;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import io.fcmchannel.sdk.FcmClient;
import io.fcmchannel.sdk.R;
import io.fcmchannel.sdk.util.BitmapHelper;
import io.mattcarroll.hover.HoverMenu;

/**
 * Created by John Cordeiro on 5/11/17.
 */

class FcmClientMenu extends HoverMenu {

    private static final String ID_MENU = "fcm_client_menu";
    private static final String ID_CHAT_SECTION = "chat_section";

    private final Context mContext;
    private final Section mChatSection;
    private final FcmClientContent fcmClientContent;
    private final int badgeCount;

    private TextView badge;

    FcmClientMenu(@NonNull Context context, int badgeCount) {
        mContext = context.getApplicationContext();
        fcmClientContent = new FcmClientContent(context);
        this.badgeCount = badgeCount;

        int iconResource = FcmClient.getUiConfiguration().getIconFloatingChat();
        mChatSection = new Section(
                new SectionId(ID_CHAT_SECTION),
                createTabView(iconResource),
                fcmClientContent
        );
    }

    @Override
    public String getId() {
        return ID_MENU;
    }

    @Override
    public int getSectionCount() {
        return 1;
    }

    @Nullable
    @Override
    public Section getSection(int index) {
        if (0 == index) {
            return mChatSection;
        }
        return null;
    }

    @Nullable
    @Override
    public Section getSection(@NonNull SectionId sectionId) {
        if (sectionId.equals(mChatSection.getId())) {
            return mChatSection;
        }
        return null;
    }

    @NonNull
    @Override
    public List<Section> getSections() {
        return Collections.singletonList(mChatSection);
    }

    private View createTabView(@DrawableRes int tabBitmapRes) {
        View tabView = LayoutInflater.from(mContext).inflate(R.layout.fcm_client_tab_view, null);
        badge = tabView.findViewById(R.id.badge);
        setBadgeCount(badgeCount);

        RoundedBitmapDrawable roundedBitmap = BitmapHelper.getRoundedBitmap(mContext, tabBitmapRes, getRadius());
        ImageView icon = tabView.findViewById(R.id.icon);
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
