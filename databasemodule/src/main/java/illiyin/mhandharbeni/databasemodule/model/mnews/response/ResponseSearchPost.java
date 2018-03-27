package illiyin.mhandharbeni.databasemodule.model.mnews.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import illiyin.mhandharbeni.databasemodule.model.mnews.response.data.get_search_post.DataSearchPost;
import illiyin.mhandharbeni.databasemodule.model.mnews.response.data.get_search_post.Meta;
import io.realm.annotations.Ignore;

/**
 * Created by Beni on 12/03/2018.
 */

public class ResponseSearchPost {
    @SerializedName("data")
    @Expose
    @Ignore
    private List<DataSearchPost> data = null;
    @SerializedName("meta")
    @Expose
    @Ignore
    private Meta meta;
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("message")
    @Expose
    private String message;

    public List<DataSearchPost> getData() {
        return data;
    }

    public void setData(List<DataSearchPost> data) {
        this.data = data;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
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
