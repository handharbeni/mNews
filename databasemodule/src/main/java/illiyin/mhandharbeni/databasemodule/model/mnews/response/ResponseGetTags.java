package illiyin.mhandharbeni.databasemodule.model.mnews.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import illiyin.mhandharbeni.databasemodule.model.mnews.response.data.get_tags.DataGetTags;

/**
 * Created by Beni on 12/03/2018.
 */

public class ResponseGetTags {

    @SerializedName("data")
    @Expose
    private List<DataGetTags> data = null;
    @SerializedName("total")
    @Expose
    private Integer total;
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("message")
    @Expose
    private String message;

    public List<DataGetTags> getData() {
        return data;
    }

    public void setData(List<DataGetTags> data) {
        this.data = data;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

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
}
