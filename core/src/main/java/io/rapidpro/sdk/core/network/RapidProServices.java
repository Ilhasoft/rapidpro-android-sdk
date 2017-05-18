package io.rapidpro.sdk.core.network;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import io.rapidpro.sdk.core.adapters.GsonDateTypeAdapter;
import io.rapidpro.sdk.core.adapters.HashMapTypeAdapter;
import io.rapidpro.sdk.core.models.Boundary;
import io.rapidpro.sdk.core.models.Field;
import io.rapidpro.sdk.core.models.FlowDefinition;
import io.rapidpro.sdk.core.models.FlowRun;
import io.rapidpro.sdk.core.models.Group;
import io.rapidpro.sdk.core.models.Message;
import io.rapidpro.sdk.core.models.network.ApiResponse;
import io.rapidpro.sdk.core.models.network.FcmRegistrationResponse;
import io.rapidpro.sdk.core.models.v2.Contact;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by gualberto on 6/13/16.
 */
public class RapidProServices {

    private static final String TOKEN_FORMAT = "Token %s";
    private static final int TIMEOUT_IN_SECONDS = 1;

    private String token;
    private String host;

    private RapidProApi rapidProApi;
    private GsonDateTypeAdapter gsonDateTypeAdapter;

    public RapidProServices(String host, String token) {
        this.token = token;
        this.host = host;

        checkFields(token);
        buildApi();
    }

    private void checkFields(String token) {
        if (this.token != null && !token.startsWith("Token")) {
            this.token = String.format(TOKEN_FORMAT, token);
        }
    }

    private void buildApi() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .writeTimeout(TIMEOUT_IN_SECONDS, TimeUnit.MINUTES)
                .connectTimeout(TIMEOUT_IN_SECONDS, TimeUnit.MINUTES)
                .readTimeout(TIMEOUT_IN_SECONDS, TimeUnit.MINUTES)
                .addInterceptor(logging)
                .build();

        gsonDateTypeAdapter = new GsonDateTypeAdapter();
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .registerTypeAdapter(Date.class, gsonDateTypeAdapter)
                .registerTypeAdapter(HashMap.class, new HashMapTypeAdapter())
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(host)
                .client(client)
                .addConverterFactory (GsonConverterFactory.create(gson))
                .build();

        rapidProApi = retrofit.create(RapidProApi.class);
    }

    public Call<ApiResponse<Boundary>> loadBoundaries(Integer page, Boolean aliases) {
        return rapidProApi.listBoundaries(token, page, aliases);
    }

    public Call<ApiResponse<Group>> loadGroups() {
        return rapidProApi.listGroups(token);
    }

    public Call<ApiResponse<Field>> loadFields() {
        return rapidProApi.listFields(token);
    }

    public Call<ApiResponse<FlowRun>> loadRuns(String userUuid) {
        return rapidProApi.listRuns(token, userUuid, null);
    }

    public Call<ApiResponse<FlowRun>> loadRuns(String userUuid, Date after) {
        return rapidProApi.listRuns(token, userUuid, gsonDateTypeAdapter.serializeDate(after));
    }

    public Call<FlowDefinition> loadFlowDefinition(String flowUuid) {
        return rapidProApi.loadFlowDefinition(token, flowUuid);
    }

    public Call<ApiResponse<io.rapidpro.sdk.core.models.v1.Contact>> loadContactV1(String urn) {
        return rapidProApi.loadContactV1(token, urn);
    }

    public Call<ApiResponse<Contact>> loadContactV2(String urn) {
        return rapidProApi.loadContactV2(token, urn);
    }

    public Call<FcmRegistrationResponse> registerFcmContact(String channel, String urn, String fcmToken) {
        return rapidProApi.registerFcmContact(channel, urn, fcmToken);
    }

    public Call<ResponseBody> sendReceivedMessage(String channel, String from, String fcmToken, String msg) {
        return rapidProApi.sendReceivedMessage(channel, from, fcmToken, msg);
    }

    public Call<io.rapidpro.sdk.core.models.v1.Contact> saveContactV1(io.rapidpro.sdk.core.models.v1.Contact contact) {
        return rapidProApi.saveContactV1(token, contact.getUuid(), contact);
    }

    public Call<Contact> saveContactV2(io.rapidpro.sdk.core.models.v1.Contact contact) {
        return rapidProApi.saveContactV2(token, contact.getUuid(), contact);
    }

    public Call<Contact> saveContactV2(Contact contact) {
        return rapidProApi.saveContactV2(token, contact.getUuid(), contact);
    }

    public Call<ApiResponse<Message>> loadMessages(String contactUuid) {
        return rapidProApi.listMessages(token, contactUuid);
    }

    public Call<ApiResponse<Message>> loadMessageById(Integer messageId) {
        return rapidProApi.listMessageById(token, messageId);
    }

}
