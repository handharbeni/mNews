package illiyin.mhandharbeni.databasemodule.model.mnews.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import illiyin.mhandharbeni.databasemodule.model.mnews.response.data.get_menus.DataMenus;

/**
 * Created by Beni on 12/03/2018.
 */

public class ResponseGetMenus {
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<DataMenus> data = null;

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

    public List<DataMenus> getData() {
        return data;
    }

    public void setData(List<DataMenus> data) {
        this.data = data;
    }
}
