package illiyin.mhandharbeni.databasemodule.model.account.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Beni on 11/03/2018.
 */

public class RequestUserToken {
    @SerializedName("token")
    @Expose
    String token;

    @SerializedName("mail_code")
    @Expose
    String mail_code;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMail_code() {
        return mail_code;
    }

    public void setMail_code(String mail_code) {
        this.mail_code = mail_code;
    }
}
