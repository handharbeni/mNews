package illiyin.mhandharbeni.databasemodule.model.account.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Beni on 11/03/2018.
 */

public class RequestChallengeCode {
    @SerializedName("token")
    @Expose
    String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
