package io.rapidpro.sdk;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.io.IOException;
import java.util.Collections;

import io.rapidpro.sdk.chat.FcmClientChatActivity;
import io.rapidpro.sdk.chat.FcmClientChatFragment;
import io.rapidpro.sdk.core.models.Contact;
import io.rapidpro.sdk.core.models.network.FcmRegistrationResponse;
import io.rapidpro.sdk.core.network.RapidProServices;
import io.rapidpro.sdk.listeners.ContactListener;
import io.rapidpro.sdk.listeners.SendMessageListener;
import io.rapidpro.sdk.persistence.Preferences;
import io.rapidpro.sdk.services.FcmClientRegistrationIntentService;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by John Cordeiro on 5/10/17.
 * Copyright © 2017 rapidpro-android-sdk, Inc. All rights reserved.
 */

@SuppressWarnings({"unused", "WeakerAccess"})
public class FcmClient {

    private static Context context;
    private static String token;
    private static String host;
    private static String channel;
    private static Boolean forceRegistration = false;
    private static Class<? extends FcmClientRegistrationIntentService> registrationServiceClass;
    private static UiConfiguration uiConfiguration;

    private static Preferences preferences;
    private static RapidProServices services;

    FcmClient() {}

    public static void initialize(Builder builder) {
        initialize(builder.context, builder.host, builder.token,
                builder.channel, builder.registrationServiceClass, builder.uiConfiguration);
    }

    private static void initialize(Context context, String host, String token, String channel
            , Class<? extends FcmClientRegistrationIntentService> registrationServiceClass, UiConfiguration uiConfiguration) {
        FcmClient.context = context;
        FcmClient.host = host;
        FcmClient.token = token;
        FcmClient.channel = channel;
        FcmClient.registrationServiceClass = registrationServiceClass;
        FcmClient.preferences = new Preferences(context);
        FcmClient.services = new RapidProServices(host, getToken());
        FcmClient.uiConfiguration = uiConfiguration;
    }

    public static UiConfiguration getUiConfiguration() {
        return uiConfiguration;
    }

    public static void startFcmClientChatActivity(Context context) {
        startFcmClientChatActivity(context, token, channel);
    }

    public static FcmClientChatFragment getFcmClientChatFragment(String token, String channel) {
        FcmClient.token = token;
        FcmClient.channel = channel;
        FcmClient.services = new RapidProServices(host, token);
        return new FcmClientChatFragment();
    }

    public static void startFcmClientChatActivity(Context context, String token, String channel) {
        FcmClient.token = token;
        FcmClient.channel = channel;
        FcmClient.services = new RapidProServices(host, token);
        context.startActivity(new Intent(context, FcmClientChatActivity.class));
    }

    public static void setUiConfiguration(UiConfiguration uiConfiguration) {
        FcmClient.uiConfiguration = uiConfiguration;
    }

    public static void sendMessage(String message) {
        services.sendReceivedMessage(channel, preferences.getUrn(), preferences.getFcmToken(), message)
                .enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {}
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {}
        });
    }

    public static void sendMessage(String message, final SendMessageListener listener) {
        services.sendReceivedMessage(channel, preferences.getUrn(), preferences.getFcmToken(), message)
                .enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    listener.onSendMessage();
                } else {
                    listener.onError(getExceptionForErrorResponse(response), context.getString(R.string.fcm_client_error_message_send));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                listener.onError(throwable, context.getString(R.string.fcm_client_error_message_send));
            }
        });
    }

    public static Contact getContact() {
        Contact contact = new Contact();
        contact.setUuid(preferences.getContactUuid());
        contact.setUrns(Collections.singletonList(preferences.getUrn()));
        return contact;
    }

    public static Response<FcmRegistrationResponse> saveContactWithToken(String urn, String fcmToken, String token) throws java.io.IOException {
        RapidProServices rapidProServices = new RapidProServices(host, token);
        return rapidProServices.registerFcmContact(channel, urn, fcmToken).execute();
    }

    @NonNull
    private static IllegalStateException getExceptionForErrorResponse(Response response) {
        return new IllegalStateException("Response not successful. HTTP Code: " + response.code() + " Response: " + response.raw());
    }

    public static void refreshContactToken() {
        String urn = preferences.getUrn();
        if (!TextUtils.isEmpty(urn)) {
            registerContact(urn);
        }
    }

    public static void registerContactIfNeeded(String urn) {
        if (!isContactRegistered()) {
            registerContact(urn);
        }
    }

    public static void registerContact(String urn) {
        Class<? extends FcmClientRegistrationIntentService> registrationIntentService =
                registrationServiceClass != null ? registrationServiceClass : FcmClientRegistrationIntentService.class;

        Intent registrationIntent = new Intent(context, registrationIntentService);
        registrationIntent.putExtra(FcmClientRegistrationIntentService.EXTRA_URN, urn);
        context.startService(registrationIntent);
    }

    public static boolean isContactRegistered() {
        return !TextUtils.isEmpty(preferences.getFcmToken()) && !TextUtils.isEmpty(preferences.getContactUuid());
    }

    public static RapidProServices getServices() {
        return services;
    }

    public static boolean isChatVisible() {
        return FcmClientChatFragment.visible;
    }

    public static void setPreferences(Preferences preferences) {
        FcmClient.preferences = preferences;
    }

    public static Preferences getPreferences() {
        return preferences;
    }

    public static Context getContext() {
        return context;
    }

    public static String getToken() {
        return token;
    }

    public static String getChannel() {
        return channel;
    }

    public static String getHost() {
        return host;
    }

    public static class Builder {

        private Context context;
        private String token;
        private String host;
        private String channel;
        private Class<? extends FcmClientRegistrationIntentService>
                registrationServiceClass = FcmClientRegistrationIntentService.class;
        private UiConfiguration uiConfiguration;

        public Builder(Context context) {
            this.context = context;
            this.uiConfiguration = new UiConfiguration();
        }

        public Builder setContext(Context context) {
            this.context = context;
            return this;
        }

        public Builder setToken(String token) {
            this.token = token;
            return this;
        }

        public Builder setHost(String host) {
            this.host = host;
            return this;
        }

        public Builder setChannel(String channel) {
            this.channel = channel;
            return this;
        }

        public Builder setRegistrationServiceClass(Class<? extends FcmClientRegistrationIntentService>
                                                           registrationServiceClass) {
            this.registrationServiceClass = registrationServiceClass;
            return this;
        }

        public Builder setUiConfiguration(UiConfiguration uiConfiguration) {
            this.uiConfiguration = uiConfiguration;
            return this;
        }
    }

}
