package illiyin.mhandharbeni.databasemodule.model.mnews.response.data.get_menus;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;

/**
 * Created by Beni on 12/03/2018.
 */

public class DataMenus extends RealmObject {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("slug")
    @Expose
    private String slug;
    @SerializedName("children")
    @Expose
    @Ignore
    private RealmList<Child> children = null;

    public DataMenus() {
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

    public RealmList<Child> getChildren() {
        return children;
    }

    public void setChildren(RealmList<Child> children) {
        this.children = children;
    }
}
