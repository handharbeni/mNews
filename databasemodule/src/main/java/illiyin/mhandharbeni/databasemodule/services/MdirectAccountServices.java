package illiyin.mhandharbeni.databasemodule.services;

import illiyin.mhandharbeni.databasemodule.model.account.response.data.account.RequestToken;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

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
}
