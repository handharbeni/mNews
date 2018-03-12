package illiyin.mhandharbeni.databasemodule.model.mnews.response.data.get_single_post;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Beni on 12/03/2018.
 */

public class Properties {
    @SerializedName("meta_title")
    @Expose
    private String metaTitle;
    @SerializedName("meta_desc")
    @Expose
    private String metaDesc;
    @SerializedName("meta_keyword")
    @Expose
    private String metaKeyword;

    public String getMetaTitle() {
        return metaTitle;
    }

    public void setMetaTitle(String metaTitle) {
        this.metaTitle = metaTitle;
    }

    public String getMetaDesc() {
        return metaDesc;
    }

    public void setMetaDesc(String metaDesc) {
        this.metaDesc = metaDesc;
    }

    public String getMetaKeyword() {
        return metaKeyword;
    }

    public void setMetaKeyword(String metaKeyword) {
        this.metaKeyword = metaKeyword;
    }
}
