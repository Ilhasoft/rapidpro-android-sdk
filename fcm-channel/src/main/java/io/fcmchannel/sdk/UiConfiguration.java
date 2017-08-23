package io.fcmchannel.sdk;

import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.StyleRes;

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

    @ColorInt
    private int toolbarColor = INVALID_VALUE;

    @ColorInt
    private int titleColor = INVALID_VALUE;

    @StyleRes
    private int theme = INVALID_VALUE;

    private String permissionMessage = "";

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

    public UiConfiguration setToolbarColor(@ColorInt int toolbarColor) {
        this.toolbarColor = toolbarColor;
        return this;
    }

    public int getTitleColor() {
        return titleColor;
    }

    public UiConfiguration setTitleColor(@ColorInt int titleColor) {
        this.titleColor = titleColor;
        return this;
    }

    public int getTheme() {
        return theme;
    }

    public UiConfiguration setTheme(@StyleRes int theme) {
        this.theme = theme;
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

    public String getPermissionMessage() {
        return permissionMessage;
    }

    public UiConfiguration setPermissionMessage(String permissionMessage) {
        this.permissionMessage = permissionMessage;
        return this;
    }
}
