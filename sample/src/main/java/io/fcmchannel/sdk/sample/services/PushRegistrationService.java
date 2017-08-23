package io.fcmchannel.sdk.sample.services;

import android.util.Log;

import io.fcmchannel.sdk.FcmClient;
import io.fcmchannel.sdk.core.models.v2.Contact;
import io.fcmchannel.sdk.core.network.RestServices;
import io.fcmchannel.sdk.services.FcmClientRegistrationIntentService;

/**
 * Created by john-mac on 7/2/16.
 */
public class PushRegistrationService extends FcmClientRegistrationIntentService {

    private static final String TAG = "RegistrationService";

    @Override
    public void onFcmRegistered(String pushIdentity, Contact contact) {
        contact.setName("FCM Channel Sample User");
        contact.setEmail("sample@gmail.com");

        try {
            RestServices restServices = FcmClient.getServices();
            restServices.saveContactV2(contact).execute();
        } catch (Exception exception) {
            Log.e(TAG, "onFcmRegistered: ", exception);
        }
    }
}
