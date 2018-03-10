package illiyin.mhandharbeni.databasemodule.services;

import illiyin.mhandharbeni.databasemodule.generator.AccessToken;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Beni on 10/03/2018.
 */

public interface MdirectAccountServices {
    @FormUrlEncoded
    @POST("")
    Call<AccessToken> getAccessToken(@Field("code") String code, @Field("grant_type") String grant_type);
}
