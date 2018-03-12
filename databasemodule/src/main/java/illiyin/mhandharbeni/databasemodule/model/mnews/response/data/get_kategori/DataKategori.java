package illiyin.mhandharbeni.databasemodule.model.mnews.response.data.get_kategori;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Beni on 12/03/2018.
 */

public class DataKategori {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("kategori_id")
    @Expose
    private String kategoriId;
    @SerializedName("parent")
    @Expose
    private String parent;
    @SerializedName("kategori_name")
    @Expose
    private String kategoriName;
    @SerializedName("kategori_slug")
    @Expose
    private String kategoriSlug;
    @SerializedName("active")
    @Expose
    private Integer active;
    @SerializedName("viewer")
    @Expose
    private Integer viewer;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKategoriId() {
        return kategoriId;
    }

    public void setKategoriId(String kategoriId) {
        this.kategoriId = kategoriId;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getKategoriName() {
        return kategoriName;
    }

    public void setKategoriName(String kategoriName) {
        this.kategoriName = kategoriName;
    }

    public String getKategoriSlug() {
        return kategoriSlug;
    }

    public void setKategoriSlug(String kategoriSlug) {
        this.kategoriSlug = kategoriSlug;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public Integer getViewer() {
        return viewer;
    }

    public void setViewer(Integer viewer) {
        this.viewer = viewer;
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
}
