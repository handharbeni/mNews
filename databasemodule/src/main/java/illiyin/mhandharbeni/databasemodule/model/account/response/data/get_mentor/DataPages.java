package illiyin.mhandharbeni.databasemodule.model.account.response.data.get_mentor;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Beni on 11/03/2018.
 */

public class DataPages {

    @SerializedName("pages")
    @Expose
    private Integer pages;
    @SerializedName("users")
    @Expose
    private List<DataGetMentor> users = null;

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public List<DataGetMentor> getUsers() {
        return users;
    }

    public void setUsers(List<DataGetMentor> users) {
        this.users = users;
    }
}
