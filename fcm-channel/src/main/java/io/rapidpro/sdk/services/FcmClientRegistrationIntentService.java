package io.rapidpro.sdk.services;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;

import io.rapidpro.sdk.FcmClient;
import io.rapidpro.sdk.core.adapters.ContactBuilder;
import io.rapidpro.sdk.core.models.Contact;
import io.rapidpro.sdk.persistence.Preferences;
import retrofit2.Response;

/**
 * Created by john-mac on 6/27/16.
 */
public class FcmClientRegistrationIntentService extends IntentService {

    private static final String TAG = "RegistrationIntent";

    public FcmClientRegistrationIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            String fcmToken = FirebaseInstanceId.getInstance().getToken();

            Preferences preferences = FcmClient.getPreferences();
            preferences.setIdentity(fcmToken);

            Contact contact = null;
            if (!TextUtils.isEmpty(FcmClient.getToken())) {
                Response<Contact> response = saveContactWithToken(fcmToken, FcmClient.getToken());
                contact = response.body();
                contact.setPhone(null);
                preferences.setContactUuid(contact.getUuid());
            }

            preferences.apply();
            onGcmRegistered(fcmToken, contact);
        } catch (Exception exception) {
            Log.e(TAG, "onHandleIntent: ", exception);
        }

        Intent registrationComplete = new Intent(Preferences.REGISTRATION_COMPLETE);
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
    }

    private Response<Contact> saveContactWithToken(String gcmId, String token) throws java.io.IOException {
        ContactBuilder contactBuilder = new ContactBuilder();
        contactBuilder.setFcmId(gcmId);

        return contactBuilder.saveContact(FcmClient.getHost(), token).execute();
    }

    public void onGcmRegistered(String pushIdentity, Contact contact){}

}
