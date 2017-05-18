package io.rapidpro.sdk.sample.services;

import android.util.Log;

import io.rapidpro.sdk.FcmClient;
import io.rapidpro.sdk.core.models.v2.Contact;
import io.rapidpro.sdk.core.network.RapidProServices;
import io.rapidpro.sdk.services.FcmClientRegistrationIntentService;

/**
 * Created by john-mac on 7/2/16.
 */
public class PushRegistrationService extends FcmClientRegistrationIntentService {

    private static final String TAG = "RegistrationService";

    @Override
    public void onFcmRegistered(String pushIdentity, Contact contact) {
        contact.setName("RapidPRO Sample User");
        contact.setEmail("sample@gmail.com");

        try {
            RapidProServices rapidProServices = FcmClient.getServices();
            rapidProServices.saveContact(contact).execute();
        } catch (Exception exception) {
            Log.e(TAG, "onFcmRegistered: ", exception);
        }
    }
}
