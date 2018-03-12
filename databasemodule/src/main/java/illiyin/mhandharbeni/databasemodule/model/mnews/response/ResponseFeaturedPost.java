package illiyin.mhandharbeni.databasemodule.model.mnews.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import illiyin.mhandharbeni.databasemodule.model.mnews.response.data.get_featured_post.DataFeaturedPost;

/**
 * Created by Beni on 12/03/2018.
 */

public class ResponseFeaturedPost {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private DataFeaturedPost data;

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

    public DataFeaturedPost getData() {
        return data;
    }

    public void setData(DataFeaturedPost data) {
        this.data = data;
    }

}
