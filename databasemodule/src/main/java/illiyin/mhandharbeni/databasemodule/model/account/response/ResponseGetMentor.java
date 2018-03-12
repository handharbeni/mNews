package illiyin.mhandharbeni.databasemodule.model.account.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import illiyin.mhandharbeni.databasemodule.model.account.response.data.get_mentor.DataPages;

/**
 * Created by Beni on 11/03/2018.
 */

public class ResponseGetMentor {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private DataPages data;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataPages getData() {
        return data;
    }

    public void setData(DataPages data) {
        this.data = data;
    }
}
