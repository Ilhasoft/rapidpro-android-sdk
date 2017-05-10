package io.rapidpro.sdk;

import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;

/**
 * Created by john-mac on 7/1/16.
 */
public class UiConfiguration {

    public static final int INVALID_COLOR = -1;

    @DrawableRes
    private int backResource = R.drawable.rapidpro_ic_arrow_back_white;

    @DrawableRes
    private int iconResource = R.drawable.fcm_client_ic_send_message;

    @ColorRes
    private int toolbarColor = INVALID_COLOR;

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
        return iconResource;
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
}
