package io.fcmchannel.sdk.core.models.network;

import com.google.gson.annotations.SerializedName;

/**
 * Created by John Cordeiro on 5/10/17.
 */

public class FcmRegistrationResponse {

    @SerializedName("contact_uuid")
    private String contactUuid;

    public String getContactUuid() {
        return contactUuid;
    }

    public void setContactUuid(String contactUuid) {
        this.contactUuid = contactUuid;
    }
}
