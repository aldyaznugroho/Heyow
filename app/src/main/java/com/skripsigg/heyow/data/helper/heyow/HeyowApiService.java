package com.skripsigg.heyow.data.helper.heyow;

import com.skripsigg.heyow.models.MatchDetailResponse;
import com.skripsigg.heyow.models.HistoryModel;
import com.skripsigg.heyow.models.FeedMatchResponse;
import com.skripsigg.heyow.models.NotificationResponse;
import com.skripsigg.heyow.models.NotificationBodyPayload;
import com.skripsigg.heyow.models.ResponseMessage;
import com.skripsigg.heyow.models.UserModel;
import com.skripsigg.heyow.utils.others.Constants;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by Aldyaz on 12/2/2016.
 */

public interface HeyowApiService {

    @FormUrlEncoded
    @POST(Constants.LOGIN_ENDPOINT)
    Observable<UserModel> createUser(@FieldMap Map<String, String> fields);

    @FormUrlEncoded
    @POST(Constants.ADD_MATCH_ENDPOINT)
    Observable<MatchDetailResponse> addMatch(@FieldMap Map<String, String> fields);

    @GET(Constants.USER_CREATED_JOINED_MATCH_ENDPOINT)
    Observable<HistoryModel> createdJoinedMatch(@Query("get_one_user_where_user_id") String userId);

    @GET(Constants.MATCH_BY_ID_ENDPOINT)
    Observable<MatchDetailResponse> matchById(@Query("get_detail_match_where_match_id") String matchId);

    @FormUrlEncoded
    @POST(Constants.JOIN_MATCH_ENDPOINT)
    Observable<ResponseMessage> joinMatch(@FieldMap Map<String, String> fields);

    @FormUrlEncoded
    @POST(Constants.LEAVE_MATCH_ENDPOINT)
    Observable<ResponseMessage> leaveMatch(@FieldMap Map<String, String> fields);

    @FormUrlEncoded
    @HTTP(method = "DELETE", path = Constants.DELETE_MATCH_ENDPOINT, hasBody = true)
    Observable<ResponseMessage> deleteMatch(@Field("match_id") String matchId);

    @GET(Constants.NEAR_ME_ENDPOINT)
    Observable<FeedMatchResponse> getNearMe(@QueryMap Map<String, String> options);

    @GET(Constants.BY_CATEGORY_ENDPOINT)
    Observable<FeedMatchResponse> getInterest(@Query("category") List<Object> category);

    @FormUrlEncoded
    @POST(Constants.LOGOUT_ENDPOINT)
    Observable<ResponseMessage> logoutUser(@Field("user_id") String userId);

    @FormUrlEncoded
    @POST(Constants.SEARCH_MATCH_ENDPOINT)
    Observable<FeedMatchResponse> searchMatchResults(@FieldMap Map<String, String> fields);

    @Headers({
            "Content-Type: application/json",
            "Authorization: key=" + Constants.SERVER_KEY
    })
    @POST("send")
    Observable<NotificationResponse> sendNotif(@Body NotificationBodyPayload notificationSendBody);

    /* =========================================================== */

    @FormUrlEncoded
    @POST(Constants.ADD_MATCH_ENDPOINT)
    Call<MatchDetailResponse> postMatch(@FieldMap Map<String, String> fields);

    @FormUrlEncoded
    @POST(Constants.SEARCH_MATCH_ENDPOINT)
    Call<FeedMatchResponse> searchMatch(@FieldMap Map<String, String> fields);

    @FormUrlEncoded
    @PUT(Constants.EDIT_MATCH_ENDPOINT)
    Call<MatchDetailResponse> editMatch(@FieldMap Map<String, String> fields);
}
