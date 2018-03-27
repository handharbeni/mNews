package illiyin.mhandharbeni.databasemodule.model.mnews.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import illiyin.mhandharbeni.databasemodule.model.mnews.response.data.get_single_post.DataGetSinglePost;
import io.realm.annotations.Ignore;

/**
 * Created by Beni on 12/03/2018.
 */

public class ResponseGetSinglePost {
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("data")
    @Expose
    @Ignore
    private DataGetSinglePost data;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public DataGetSinglePost getData() {
        return data;
    }

    public void setData(DataGetSinglePost data) {
        this.data = data;
    }

}
