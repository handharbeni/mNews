package illiyin.mhandharbeni.databasemodule.model.account.response.data.get_user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Beni on 11/03/2018.
 */

public class DataGetUser {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("role")
    @Expose
    private String role;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("phone_number")
    @Expose
    private String phoneNumber;
    @SerializedName("foto_profil")
    @Expose
    private String fotoProfil;
    @SerializedName("jabatan")
    @Expose
    private String jabatan;
    @SerializedName("keahlian")
    @Expose
    private String keahlian;
    @SerializedName("alamat_pribadi")
    @Expose
    private String alamatPribadi;
    @SerializedName("kota")
    @Expose
    private String kota;
    @SerializedName("provinsi")
    @Expose
    private String provinsi;
    @SerializedName("link_website")
    @Expose
    private String linkWebsite;
    @SerializedName("medsos_linkedin")
    @Expose
    private String medsosLinkedin;
    @SerializedName("medsos_fb")
    @Expose
    private String medsosFb;
    @SerializedName("medsos_twitter")
    @Expose
    private String medsosTwitter;
    @SerializedName("medsos_googleplus")
    @Expose
    private String medsosGoogleplus;
    @SerializedName("medsos_ig")
    @Expose
    private String medsosIg;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("description")
    @Expose
    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFotoProfil() {
        return fotoProfil;
    }

    public void setFotoProfil(String fotoProfil) {
        this.fotoProfil = fotoProfil;
    }

    public String getJabatan() {
        return jabatan;
    }

    public void setJabatan(String jabatan) {
        this.jabatan = jabatan;
    }

    public String getKeahlian() {
        return keahlian;
    }

    public void setKeahlian(String keahlian) {
        this.keahlian = keahlian;
    }

    public String getAlamatPribadi() {
        return alamatPribadi;
    }

    public void setAlamatPribadi(String alamatPribadi) {
        this.alamatPribadi = alamatPribadi;
    }

    public String getKota() {
        return kota;
    }

    public void setKota(String kota) {
        this.kota = kota;
    }

    public String getProvinsi() {
        return provinsi;
    }

    public void setProvinsi(String provinsi) {
        this.provinsi = provinsi;
    }

    public String getLinkWebsite() {
        return linkWebsite;
    }

    public void setLinkWebsite(String linkWebsite) {
        this.linkWebsite = linkWebsite;
    }

    public String getMedsosLinkedin() {
        return medsosLinkedin;
    }

    public void setMedsosLinkedin(String medsosLinkedin) {
        this.medsosLinkedin = medsosLinkedin;
    }

    public String getMedsosFb() {
        return medsosFb;
    }

    public void setMedsosFb(String medsosFb) {
        this.medsosFb = medsosFb;
    }

    public String getMedsosTwitter() {
        return medsosTwitter;
    }

    public void setMedsosTwitter(String medsosTwitter) {
        this.medsosTwitter = medsosTwitter;
    }

    public String getMedsosGoogleplus() {
        return medsosGoogleplus;
    }

    public void setMedsosGoogleplus(String medsosGoogleplus) {
        this.medsosGoogleplus = medsosGoogleplus;
    }

    public String getMedsosIg() {
        return medsosIg;
    }

    public void setMedsosIg(String medsosIg) {
        this.medsosIg = medsosIg;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
