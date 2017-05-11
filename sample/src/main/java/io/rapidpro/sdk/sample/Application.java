package io.rapidpro.sdk.sample;

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
                .setHost("https://rapidpro.ilhasoft.mobi/")
                .setToken("f417616c57339f28e07bcedf8d4f23f74614bcb5")
                .setChannel("b1dac4b8-eace-4ade-86cc-b0e0a3b6aa52")
                .setRegistrationServiceClass(PushRegistrationService.class)
                .setUiConfiguration(new UiConfiguration()
                        .setIconResource(R.mipmap.ic_launcher)
                        .setTitleString("RapidCon")));
    }
}
