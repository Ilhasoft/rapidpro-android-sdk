package io.fcmchannel.sdk.services;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;

import java.util.Collections;

import io.fcmchannel.sdk.FcmClient;
import io.fcmchannel.sdk.core.models.network.FcmRegistrationResponse;
import io.fcmchannel.sdk.core.models.v2.Contact;
import io.fcmchannel.sdk.core.network.RestServices;
import io.fcmchannel.sdk.persistence.Preferences;
import retrofit2.Response;

/**
 * Created by john-mac on 6/27/16.
 */
public class FcmClientRegistrationIntentService extends IntentService {

    public static final String REGISTRATION_COMPLETE = "io.fcmchannel.sdk.RegistrationCompleted";

    private static final String TAG = "RegistrationIntent";

    public static final String EXTRA_URN = "urn";
    public static final String EXTRA_CONTACT_UUID = "contactUuid";

    public FcmClientRegistrationIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            String urn = intent.getStringExtra(EXTRA_URN);
            String contactUuid = intent.getStringExtra(EXTRA_CONTACT_UUID);

            String fcmToken = FirebaseInstanceId.getInstance().getToken();

            Preferences preferences = FcmClient.getPreferences();
            Contact contact = null;

            if (!TextUtils.isEmpty(FcmClient.getToken())) {
                Response<FcmRegistrationResponse> response = saveContactWithToken(urn, fcmToken, contactUuid);
                FcmRegistrationResponse fcmRegistrationResponse = response.body();

                preferences.setContactUuid(fcmRegistrationResponse.getContactUuid());
                preferences.setFcmToken(fcmToken);
                preferences.setUrn(urn);

                contact = new Contact();
                contact.setUuid(fcmRegistrationResponse.getContactUuid());
                contact.setUrns(Collections.singletonList(FcmClient.URN_PREFIX_FCM + urn));
            }

            preferences.commit();
            FcmClient.setPreferences(preferences);

            onFcmRegistered(fcmToken, contact);
        } catch (Exception exception) {
            Log.e(TAG, "onHandleIntent: ", exception);
        }

        Intent registrationComplete = new Intent(REGISTRATION_COMPLETE);
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
    }

    private Response<FcmRegistrationResponse> saveContactWithToken(String urn, String fcmToken,
                                                                   String contactUuid) throws java.io.IOException {
        RestServices restServices = new RestServices(FcmClient.getHost(), FcmClient.getToken());
        return restServices.registerFcmContact(FcmClient.getChannel(), urn, fcmToken, contactUuid).execute();
    }

    public void onFcmRegistered(String pushIdentity, Contact contact){}

}
