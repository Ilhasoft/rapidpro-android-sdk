package io.rapidpro.sdk.sample;

import android.support.annotation.ColorRes;
import android.support.v4.content.res.ResourcesCompat;

import io.rapidpro.sdk.FcmClient;
import io.rapidpro.sdk.UiConfiguration;
import io.rapidpro.sdk.sample.services.PushRegistrationService;

/**
 * Created by John Cordeiro on 5/10/17.
 * Copyright © 2017 rapidpro-android-sdk, Inc. All rights reserved.
 */

public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();

        FcmClient.initialize(new FcmClient.Builder(this)
                .setHost(Constants.HOST)
                .setToken(Constants.TOKEN)
                .setChannel(Constants.CHANNEL)
                .setRegistrationServiceClass(PushRegistrationService.class)
                .setUiConfiguration(new UiConfiguration()
                        .setPermissionMessage("Please give me permission to open floating chat!")
                        .setTheme(R.style.AppTheme_Blue)
                        .setIconResource(R.mipmap.ic_launcher)
                        .setIconFloatingChat(R.mipmap.ic_launcher)
                        .setTitleColor(getColorCompat(android.R.color.white))
                        .setTitleString("RapidPRO Sample SDK")));
    }

    private int getColorCompat(@ColorRes int colorRes) {
        return ResourcesCompat.getColor(getResources(), colorRes, getTheme());
    }
}
