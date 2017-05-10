package io.rapidpro.sdk;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.io.IOException;

import io.rapidpro.sdk.chat.FcmClientChatActivity;
import io.rapidpro.sdk.chat.FcmClientChatFragment;
import io.rapidpro.sdk.core.adapters.ContactBuilder;
import io.rapidpro.sdk.core.models.Contact;
import io.rapidpro.sdk.core.models.Message;
import io.rapidpro.sdk.core.models.network.ApiResponse;
import io.rapidpro.sdk.core.network.RapidProServices;
import io.rapidpro.sdk.listeners.ContactListener;
import io.rapidpro.sdk.listeners.LoadMessageListener;
import io.rapidpro.sdk.listeners.MessagesLoadingListener;
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

        registerFcmIfNeeded();
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
        services.sendReceivedMessage(channel, preferences.getIdentity(), message).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {}
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {}
        });
    }

    public static void sendMessage(String message, final SendMessageListener listener) {
        services.sendReceivedMessage(channel, preferences.getIdentity(), message).enqueue(new Callback<ResponseBody>() {
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

    public static void loadMessage(Integer messageId, final LoadMessageListener listener) {
        RapidProServices services = new RapidProServices(host, getToken());
        services.loadMessageById(messageId).enqueue(new Callback<ApiResponse<Message>>() {
            @Override
            public void onResponse(Call<ApiResponse<Message>> call, Response<ApiResponse<Message>> response) {
                if (response.isSuccessful() && response.body().getResults() != null
                && !response.body().getResults().isEmpty()) {
                    listener.onMessageLoaded(response.body().getResults().get(0));
                } else {
                    listener.onError(getExceptionForErrorResponse(response), context.getString(R.string.fcm_client_error_load_message));
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Message>> call, Throwable exception) {
                listener.onError(exception, context.getString(R.string.fcm_client_error_load_message));
            }
        });
    }

    public static void loadMessages(final MessagesLoadingListener listener) {
        final RapidProServices services = new RapidProServices(host, getToken());
        String contactUuid = getPreferences().getContactUuid();
        if (!TextUtils.isEmpty(contactUuid)) {
            loadMessagesWithContact(services, contactUuid, listener);
        } else {
            loadContact(listener, services);
        }
    }

    private static void loadContact(final MessagesLoadingListener listener, final RapidProServices services) {
        services.loadContactsByUrn("fcm:" + getPreferences().getIdentity()).enqueue(new Callback<ApiResponse<Contact>>() {
            @Override
            public void onResponse(Call<ApiResponse<Contact>> call, Response<ApiResponse<Contact>> response) {
                if (response.isSuccessful() && response.body().getCount() > 0) {
                    Contact contact = response.body().getResults().get(0);
                    loadMessagesWithContact(services, contact.getUuid(), listener);
                } else {
                    listener.onError(getExceptionForErrorResponse(response)
                            , context.getString(R.string.fcm_client_error_load_messages));
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Contact>> call, Throwable throwable) {
                listener.onError(throwable, context.getString(R.string.fcm_client_error_load_messages));
            }
        });
    }

    private static void loadMessagesWithContact(RapidProServices services, String contactUuid, final MessagesLoadingListener listener) {
        services.loadMessages(contactUuid).enqueue(new Callback<ApiResponse<Message>>() {
            @Override
            public void onResponse(Call<ApiResponse<Message>> call, Response<ApiResponse<Message>> response) {
                if (response.isSuccessful()) {
                    listener.onMessagesLoaded(response.body().getResults());
                } else {
                    listener.onError(getExceptionForErrorResponse(response), context.getString(R.string.fcm_client_error_load_messages));
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Message>> call, Throwable throwable) {
                listener.onError(throwable, context.getString(R.string.fcm_client_error_load_messages));
            }
        });
    }

    public static Contact getContact() {
        Contact contact = new Contact();
        contact.setUuid(preferences.getContactUuid());
        return contact;
    }

    public static void updateContact(final Contact contact, final ContactListener listener) {
        contact.setUuid(preferences.getContactUuid());

        RapidProServices services = new RapidProServices(host, token);
        services.saveContact(contact).enqueue(new Callback<Contact>() {
            @Override
            public void onResponse(Call<Contact> call, Response<Contact> response) {
                if (response.isSuccessful()) {
                    listener.onContactSaved(response.body());
                } else {
                    listener.onError(getExceptionForErrorResponse(response)
                            , context.getString(R.string.fcm_client_error_contact_update));
                }
            }

            @Override
            public void onFailure(Call<Contact> call, Throwable throwable) {
                listener.onError(throwable, context.getString(R.string.fcm_client_error_contact_update));
            }
        });
    }

    public static void updateContact(final Contact contact) throws IOException {
        contact.setUuid(preferences.getContactUuid());

        RapidProServices services = new RapidProServices(host, token);
        services.saveContact(contact).execute();
    }

    public static Response<Contact> saveContactWithToken(String FcmClientToken, String token) throws java.io.IOException {
        ContactBuilder contactBuilder = new ContactBuilder();
        contactBuilder.setFcmId(FcmClientToken);

        return contactBuilder.saveContact(host, token).execute();
    }

    public static void forceRegistration() {
        Class<? extends FcmClientRegistrationIntentService> registrationIntentService =
                registrationServiceClass != null ? registrationServiceClass : FcmClientRegistrationIntentService.class;
        context.startService(new Intent(context, registrationIntentService));
    }

    @NonNull
    private static IllegalStateException getExceptionForErrorResponse(Response response) {
        return new IllegalStateException("Response not successful. HTTP Code: " + response.code() + " Response: " + response.raw());
    }

    private static void registerFcmIfNeeded() {
        if (forceRegistration || TextUtils.isEmpty(preferences.getIdentity())) {
            forceRegistration();
        }
    }

    public static void setForceRegistration(Boolean forceRegistration) {
        FcmClient.forceRegistration = forceRegistration;
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
