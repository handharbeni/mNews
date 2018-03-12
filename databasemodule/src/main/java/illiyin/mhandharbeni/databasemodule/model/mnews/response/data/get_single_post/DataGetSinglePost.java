package illiyin.mhandharbeni.databasemodule.model.mnews.response.data.get_single_post;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Beni on 12/03/2018.
 */

public class DataGetSinglePost {

    @SerializedName("single")
    @Expose
    private Single single;
    @SerializedName("related")
    @Expose
    private List<Related> related = null;

    public Single getSingle() {
        return single;
    }

    public void setSingle(Single single) {
        this.single = single;
    }

    public List<Related> getRelated() {
        return related;
    }

    public void setRelated(List<Related> related) {
        this.related = related;
    }
}
