package illiyin.mhandharbeni.databasemodule.model.mnews.response.data.get_comment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;

/**
 * Created by Beni on 12/03/2018.
 */

public class DataGetComment extends RealmObject {
    @SerializedName("page")
    @Expose
    private String page;
    @SerializedName("pages")
    @Expose
    private Integer pages;
    @SerializedName("total")
    @Expose
    private Integer total;
    @SerializedName("comments")
    @Expose
    @Ignore
    private List<Comment> comments = null;

    public DataGetComment() {
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

}
