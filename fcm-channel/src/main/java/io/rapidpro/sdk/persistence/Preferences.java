package io.rapidpro.sdk.persistence;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by john-mac on 6/27/16.
 */
public class Preferences {

    private static final String KEY_URN = "urn";
    private static final String KEY_FCM_TOKEN = "fcmToken";
    private static final String KEY_CONTACT_UUID = "contactUuid";

    private static final String PREFERENCES_NAME = "io.rapidpro.sdk.preferences";

    private final SharedPreferences sharedPreferences;
    private final String prefix;

    private final Map<String, String> objects;

    public Preferences(Context context) {
        this.sharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        this.prefix = context.getPackageName();
        this.objects = new HashMap<>();
    }

    public String getContactUuid() {
        return sharedPreferences.getString(getKey(KEY_CONTACT_UUID), null);
    }

    public Preferences setContactUuid(String contactUuid) {
        this.objects.put(getKey(KEY_CONTACT_UUID), contactUuid);
        return this;
    }

    public Preferences setUrn(String urn) {
        this.objects.put(getKey(KEY_URN), urn);
        return this;
    }

    public String getUrn() {
        return sharedPreferences.getString(getKey(KEY_URN), null);
    }

    public Preferences setFcmToken(String fcmToken) {
        this.objects.put(getKey(KEY_FCM_TOKEN), fcmToken);
        return this;
    }

    public String getFcmToken() {
        return sharedPreferences.getString(getKey(KEY_FCM_TOKEN), null);
    }

    @SuppressLint("ApplySharedPref")
    public void commit() {
        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        for (String key : objects.keySet()) {
            editor.putString(key, objects.get(key));
        }
        editor.commit();
    }

    public void apply() {
        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        for (String key : objects.keySet()) {
            editor.putString(key, objects.get(key));
        }
        editor.apply();
    }

    @NonNull
    private String getKey(String key) {
        return prefix  + "_" + key;
    }
}
