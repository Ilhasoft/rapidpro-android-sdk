package io.rapidpro.sdk.core.models.network;

import com.google.gson.annotations.SerializedName;

/**
 * Created by John Cordeiro on 5/10/17.
 * Copyright © 2017 rapidpro-android-sdk, Inc. All rights reserved.
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
