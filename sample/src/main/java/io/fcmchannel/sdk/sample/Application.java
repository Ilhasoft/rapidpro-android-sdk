package io.fcmchannel.sdk.sample;

import android.support.annotation.ColorRes;
import android.support.v4.content.res.ResourcesCompat;

import io.fcmchannel.sdk.FcmClient;
import io.fcmchannel.sdk.UiConfiguration;
import io.fcmchannel.sdk.sample.services.PushRegistrationService;

/**
 * Created by John Cordeiro on 5/10/17.
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
                        .setTitleString("FCM Channel Sample")));
    }

    private int getColorCompat(@ColorRes int colorRes) {
        return ResourcesCompat.getColor(getResources(), colorRes, getTheme());
    }
}
