package io.fcmchannel.sdk.services;

import com.google.firebase.iid.FirebaseInstanceIdService;

import io.fcmchannel.sdk.FcmClient;

/**
 * Created by john-mac on 6/28/16.
 */
public class FcmClientInstanceIntentIdService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        FcmClient.refreshContactToken();
    }
}
