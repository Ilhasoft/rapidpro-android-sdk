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
                .setChannel("7815c7fb-eed7-41fe-b585-2a244d398fcb")
                .setRegistrationServiceClass(PushRegistrationService.class)
                .setUiConfiguration(new UiConfiguration()
                        .setIconResource(R.mipmap.ic_launcher)
                        .setTitleString("RapidCon")));
    }
}
