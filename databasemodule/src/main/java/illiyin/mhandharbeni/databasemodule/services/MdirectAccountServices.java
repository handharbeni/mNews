package illiyin.mhandharbeni.databasemodule.services;

import java.util.Map;
import java.util.Observable;

import illiyin.mhandharbeni.databasemodule.model.account.request.RequestUpdateProfile;
import illiyin.mhandharbeni.databasemodule.model.account.response.ResponseUserProfile;
import illiyin.mhandharbeni.databasemodule.model.account.response.data.account.RequestToken;
import illiyin.mhandharbeni.databasemodule.model.account.response.data.user_profile.DataUserProfile;
import illiyin.mhandharbeni.databasemodule.model.mnews.response.ResponseDeleteComment;
import illiyin.mhandharbeni.databasemodule.model.mnews.response.data.general.StringResponse;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

import static illiyin.mhandharbeni.databasemodule.services.MnewsServices.mnewstoken;

/**
 * Created by Beni on 10/03/2018.
 */

public interface MdirectAccountServices {
    @Headers({
            "appsecret: 2BdUbH680ERDaJD1LGjX6Td7jR5Z5O2TJzPgNDOjlo4IANW3W9CYEZQ2OuT01cRpyFqxLWGknKWB46h2d6p4qOBEGvLDISRNAxb0hVgrtpy3K5sPNtsMQDjzwAt2MTvP",
            "appid: 174863748390695",
            "mnewstoken: "+mnewstoken
    })
    @FormUrlEncoded
    @POST("token_request")
    Call<RequestToken> getAccessToken(@Field("token") String token);

    @GET("me")
    Call<ResponseUserProfile> getMe(@HeaderMap Map<String, String> header);

//    @Headers({
//            "appsecret: 2BdUbH680ERDaJD1LGjX6Td7jR5Z5O2TJzPgNDOjlo4IANW3W9CYEZQ2OuT01cRpyFqxLWGknKWB46h2d6p4qOBEGvLDISRNAxb0hVgrtpy3K5sPNtsMQDjzwAt2MTvP",
//            "appid: 174863748390695",
//            "mnewstoken: "+mnewstoken
//    })
    @POST("me")
    Call<StringResponse> updateMe(@HeaderMap Map<String, String> header, @Body RequestUpdateProfile dataUserProfile);

    @Multipart
    @POST("me")
    Call<String> imageMe(@HeaderMap Map<String, String> header, @Part MultipartBody.Part name, @Part MultipartBody.Part email, @Part MultipartBody.Part userfile);


    @GET("logout")
    Call<ResponseUserProfile> logout(@HeaderMap Map<String, String> header);
}
