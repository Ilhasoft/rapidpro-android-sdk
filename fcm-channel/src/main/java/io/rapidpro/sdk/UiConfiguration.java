package io.rapidpro.sdk;

import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;

/**
 * Created by john-mac on 7/1/16.
 */
public class UiConfiguration {

    public static final int INVALID_VALUE = -1;

    @DrawableRes
    private int backResource = R.drawable.fcm_client_ic_arrow_back_white;

    @DrawableRes
    private int iconResource = INVALID_VALUE;

    @DrawableRes
    private int iconFloatingChat = INVALID_VALUE;

    @ColorRes
    private int toolbarColor = INVALID_VALUE;

    @ColorRes
    private int titleColor = android.R.color.white;

    private String titleString = "";

    public int getBackResource() {
        return backResource;
    }

    public UiConfiguration setBackResource(int backResource) {
        this.backResource = backResource;
        return this;
    }

    public int getIconResource() {
        return iconResource != UiConfiguration.INVALID_VALUE ? iconResource : FcmClient.getAppIcon();
    }

    public UiConfiguration setIconResource(int iconResource) {
        this.iconResource = iconResource;
        return this;
    }

    public int getToolbarColor() {
        return toolbarColor;
    }

    public UiConfiguration setToolbarColor(int toolbarColor) {
        this.toolbarColor = toolbarColor;
        return this;
    }

    public int getTitleColor() {
        return titleColor;
    }

    public UiConfiguration setTitleColor(int titleColor) {
        this.titleColor = titleColor;
        return this;
    }

    public String getTitleString() {
        return titleString;
    }

    public UiConfiguration setTitleString(String titleString) {
        this.titleString = titleString;
        return this;
    }

    public int getIconFloatingChat() {
        return iconFloatingChat != UiConfiguration.INVALID_VALUE ? iconFloatingChat : FcmClient.getAppIcon();
    }

    public UiConfiguration setIconFloatingChat(int iconFloatingChat) {
        this.iconFloatingChat = iconFloatingChat;
        return this;
    }
}
