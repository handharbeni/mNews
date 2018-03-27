package illiyin.mhandharbeni.databasemodule.model.mnews.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import illiyin.mhandharbeni.databasemodule.model.mnews.response.data.get_post_kategori.DataPostKategori;
import illiyin.mhandharbeni.databasemodule.model.mnews.response.data.get_post_kategori.Meta;
import io.realm.annotations.Ignore;

/**
 * Created by Beni on 12/03/2018.
 */

public class ResponseGetPostKategori {

    @SerializedName("data")
    @Expose
    @Ignore
    private List<DataPostKategori> data = null;
    @SerializedName("meta")
    @Expose
    private Meta meta;
    @SerializedName("success")
    @Expose
    private Boolean success;

    public List<DataPostKategori> getData() {
        return data;
    }

    public void setData(List<DataPostKategori> data) {
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
}
