package illiyin.mhandharbeni.databasemodule.model.mnews.response.data.get_comment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;

/**
 * Created by Beni on 12/03/2018.
 */

public class Comment extends RealmObject {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("post_slug")
    @Expose
    private String postSlug;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("author")
    @Expose
    @Ignore
    private Author author;

    public Comment() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPostSlug() {
        return postSlug;
    }

    public void setPostSlug(String postSlug) {
        this.postSlug = postSlug;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}
