package illiyin.mhandharbeni.databasemodule.model.mnews.response.data.get_single_post;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import illiyin.mhandharbeni.databasemodule.model.mnews.response.data.general.Author;
import illiyin.mhandharbeni.databasemodule.model.mnews.response.data.general.Properties;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;

/**
 * Created by Beni on 12/03/2018.
 */

public class Single extends RealmObject {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("slug_id")
    @Expose
    private String slugId;
    @SerializedName("post_type")
    @Expose
    private String postType;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("post")
    @Expose
    private String post;
    @SerializedName("video")
    @Expose
    private String video;
    @SerializedName("gallery_id")
    @Expose
    private String galleryId;
    @SerializedName("kategori")
    @Expose
    private String kategori;
    @SerializedName("tag")
    @Expose
    private String tag;
    @SerializedName("featured_img")
    @Expose
    private String featuredImg;
    @SerializedName("thumb_only")
    @Expose
    private String thumbOnly;
    @SerializedName("fimg_caption")
    @Expose
    private String fimgCaption;
    @SerializedName("featured_post")
    @Expose
    private String featuredPost;
    @SerializedName("editor_pick")
    @Expose
    private String editorPick;
    @SerializedName("duplicate")
    @Expose
    private Integer duplicate;
    @SerializedName("view")
    @Expose
    private Integer view;
    @SerializedName("properties")
    @Expose
    @Ignore
    private List<Properties> properties;
    @SerializedName("date_published")
    @Expose
    private String datePublished;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("author")
    @Expose
    @Ignore
    private List<Author> author;

    public Single() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSlugId() {
        return slugId;
    }

    public void setSlugId(String slugId) {
        this.slugId = slugId;
    }

    public String getPostType() {
        return postType;
    }

    public void setPostType(String postType) {
        this.postType = postType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getGalleryId() {
        return galleryId;
    }

    public void setGalleryId(String galleryId) {
        this.galleryId = galleryId;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getFeaturedImg() {
        return featuredImg;
    }

    public void setFeaturedImg(String featuredImg) {
        this.featuredImg = featuredImg;
    }

    public String getThumbOnly() {
        return thumbOnly;
    }

    public void setThumbOnly(String thumbOnly) {
        this.thumbOnly = thumbOnly;
    }

    public String getFimgCaption() {
        return fimgCaption;
    }

    public void setFimgCaption(String fimgCaption) {
        this.fimgCaption = fimgCaption;
    }

    public String getFeaturedPost() {
        return featuredPost;
    }

    public void setFeaturedPost(String featuredPost) {
        this.featuredPost = featuredPost;
    }

    public String getEditorPick() {
        return editorPick;
    }

    public void setEditorPick(String editorPick) {
        this.editorPick = editorPick;
    }

    public Integer getDuplicate() {
        return duplicate;
    }

    public void setDuplicate(Integer duplicate) {
        this.duplicate = duplicate;
    }

    public Integer getView() {
        return view;
    }

    public void setView(Integer view) {
        this.view = view;
    }

    public List<Properties> getProperties() {
        return properties;
    }

    public void setProperties(List<Properties> properties) {
        this.properties = properties;
    }

    public String getDatePublished() {
        return datePublished;
    }

    public void setDatePublished(String datePublished) {
        this.datePublished = datePublished;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<Author> getAuthor() {
        return author;
    }

    public void setAuthor(List<Author> author) {
        this.author = author;
    }
}
