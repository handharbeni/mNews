package illiyin.mhandharbeni.databasemodule.model.mnews.response.data.get_menus;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Created by Beni on 12/03/2018.
 */

public class Child extends RealmObject {
    private Integer idMenus;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("slug")
    @Expose
    private String slug;

    public Child() {
    }

    public Integer getIdMenus() {
        return idMenus;
    }

    public void setIdMenus(Integer idMenus) {
        this.idMenus = idMenus;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }
}
