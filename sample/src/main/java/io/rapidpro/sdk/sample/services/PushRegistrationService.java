package io.rapidpro.sdk.sample.services;

import android.util.Log;

import java.io.IOException;

import io.rapidpro.sdk.FcmClient;
import io.rapidpro.sdk.core.models.Contact;
import io.rapidpro.sdk.services.FcmClientRegistrationIntentService;

/**
 * Created by john-mac on 7/2/16.
 */
public class PushRegistrationService extends FcmClientRegistrationIntentService {

    private static final String TAG = "PushRegistrationService";

    @Override
    public void onGcmRegistered(String pushIdentity, Contact contact) {
        contact.setName("IlhaPush Sample User");
        contact.setEmail("sample@gmail.com");

        try {
            FcmClient.updateContact(contact);
        } catch (IOException e) {
            Log.e(TAG, "onGcmRegistered: ", e);
        }
    }
}
