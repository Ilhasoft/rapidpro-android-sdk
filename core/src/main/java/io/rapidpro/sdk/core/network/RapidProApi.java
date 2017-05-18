package io.rapidpro.sdk.core.network;

import java.util.Map;

import io.rapidpro.sdk.core.models.Boundary;
import io.rapidpro.sdk.core.models.FlowDefinition;
import io.rapidpro.sdk.core.models.FlowRun;
import io.rapidpro.sdk.core.models.FlowStepSet;
import io.rapidpro.sdk.core.models.Group;
import io.rapidpro.sdk.core.models.Message;
import io.rapidpro.sdk.core.models.network.ApiResponse;
import io.rapidpro.sdk.core.models.network.FcmRegistrationResponse;
import io.rapidpro.sdk.core.models.v2.Contact;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by gualberto on 6/13/16.
 */
public interface RapidProApi {

    @FormUrlEncoded
    @POST("handlers/fcm/register/{channel}/")
    Call<FcmRegistrationResponse> registerFcmContact(@Path("channel") String channel,
                                                     @Field("urn") String urn,
                                                     @Field("fcm_token") String fcmToken);

    @FormUrlEncoded
    @POST("handlers/fcm/receive/{channel}")
    Call<ResponseBody> sendReceivedMessage(@Path("channel") String channel,
                                           @Field("from") String from,
                                           @Field("fcm_token") String fcmToken,
                                           @Field("msg") String msg);

    @GET("api/v2/groups.json")
    Call<ApiResponse<Group>> listGroups(@Header("Authorization") String token);

    @GET("api/v2/fields.json")
    Call<ApiResponse<io.rapidpro.sdk.core.models.Field>> listFields(@Header("Authorization") String token);

    @GET("api/v1/boundaries.json")
    Call<ApiResponse<Boundary>> listBoundaries(@Header("Authorization") String token
            , @Query("page") Integer page, @Query("aliases") Boolean aliases);

    @GET("api/v2/messages.json")
    Call<ApiResponse<Message>> listMessages(@Header("Authorization") String token, @Query("contact") String contactUuid);

    @GET("api/v2/messages.json")
    Call<ApiResponse<Message>> listMessageById(@Header("Authorization") String token, @Query("id") Integer messageId);

    @GET("api/v2/runs.json")
    Call<ApiResponse<FlowRun>>  listRuns(@Header("Authorization") String token
            , @Query("contact") String uuid, @Query("after") String after);

    @GET("api/v2/definitions.json")
    Call<FlowDefinition> loadFlowDefinition(@Header("Authorization") String token, @Query("flow") String flowUuid);

    @POST("api/v1/steps")
    @Headers({ "Accept: application/json", "Content-Type: application/json" })
    Map<String, Object> saveFlowStepSet(@Header("Authorization") String token, @Body FlowStepSet flowStepSet);

    @GET("api/v1/contacts.json")
    Call<ApiResponse<io.rapidpro.sdk.core.models.v1.Contact>> loadContactV1(@Header("Authorization") String token, @Query("urns") String urn);

    @GET("api/v2/contacts.json")
    Call<ApiResponse<Contact>> loadContactV2(@Header("Authorization") String token, @Query("urn") String urn);

    @POST("api/v1/contacts.json")
    Call<io.rapidpro.sdk.core.models.v1.Contact> saveContactV1(@Header("Authorization") String token,
                                @Query("uuid") String contactUuid, @Body io.rapidpro.sdk.core.models.v1.Contact contact);

    @POST("api/v2/contacts.json")
    Call<Contact> saveContactV2(@Header("Authorization") String token,
                                @Query("uuid") String contactUuid, @Body io.rapidpro.sdk.core.models.v1.Contact contact);

    @POST("api/v2/contacts.json")
    Call<Contact> saveContactV2(@Header("Authorization") String token,
                                @Query("uuid") String contactUuid, @Body Contact contact);
}
