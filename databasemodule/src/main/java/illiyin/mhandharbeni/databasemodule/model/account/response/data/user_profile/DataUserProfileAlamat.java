package illiyin.mhandharbeni.databasemodule.model.account.response.data.user_profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Beni on 11/03/2018.
 */

public class DataUserProfileAlamat {
    @SerializedName("jln")
    @Expose
    private String jln;
    @SerializedName("no_bangunan")
    @Expose
    private String noBangunan;
    @SerializedName("rtrw")
    @Expose
    private String rtrw;
    @SerializedName("kodepos")
    @Expose
    private String kodepos;

    public String getJln() {
        return jln;
    }

    public void setJln(String jln) {
        this.jln = jln;
    }

    public String getNoBangunan() {
        return noBangunan;
    }

    public void setNoBangunan(String noBangunan) {
        this.noBangunan = noBangunan;
    }

    public String getRtrw() {
        return rtrw;
    }

    public void setRtrw(String rtrw) {
        this.rtrw = rtrw;
    }

    public String getKodepos() {
        return kodepos;
    }

    public void setKodepos(String kodepos) {
        this.kodepos = kodepos;
    }
}
