package com.mdirect.mnews;

import android.app.Dialog;
import android.app.Service;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.crash.FirebaseCrash;
import com.mdirect.mnews.value.Constants;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import illiyin.mhandharbeni.databasemodule.generator.ServiceGenerator;
import illiyin.mhandharbeni.databasemodule.generator.ServiceGeneratorAccount;
import illiyin.mhandharbeni.databasemodule.model.account.response.ResponseUserProfile;
import illiyin.mhandharbeni.databasemodule.model.account.response.data.account.AccessToken;
import illiyin.mhandharbeni.databasemodule.model.account.response.data.account.RequestToken;
import illiyin.mhandharbeni.databasemodule.model.account.response.data.user_profile.DataUserProfile;
import illiyin.mhandharbeni.databasemodule.model.account.response.data.user_profile.DataUserProfileAlamat;
import illiyin.mhandharbeni.databasemodule.services.MdirectAccountServices;
import illiyin.mhandharbeni.databasemodule.services.MnewsServices;
import illiyin.mhandharbeni.servicemodule.ServiceAdapter;
import illiyin.mhandharbeni.sessionlibrary.Session;
import illiyin.mhandharbeni.sessionlibrary.SessionListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Beni on 07/03/2018.
 */

public class BaseApps extends AppCompatActivity implements SessionListener {
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }
    public Integer REQUIREMENTS_ANDROID = Build.VERSION_CODES.JELLY_BEAN;

    public static String CONTENTTYPE_JSON = "application/json";
    public static String CONTENTTYPE_URLENCODED = "application/x-www-form-urlencoded";

    public static String KEY_APP_ID = "appid";
    public static String KEY_APP_SECRET = "appsecret";
    public static String KEY_CLIENT_TOKEN = "token";
    public static String KEY_CLIENT_USERTOKEN = "usertoken";
    public static String KEY_CLIENT_ID = "clientid";
    public static String KEY_CLIENT_SECRET = "clientsecret";

    public static String KEY_ID = "id";
    public static String KEY_ROLE = "role";
    public static String KEY_USERNAME = "username";
    public static String KEY_NAME = "name";
    public static String KEY_EMAIL = "email";
    public static String KEY_PHONENUMBER = "phone_number";
    public static String KEY_FOTOPROFILE = "foto_profil";
    public static String KEY_JABATAN = "jabatan";
    public static String KEY_KEAHLIAN = "keahlian";
    public static String KEY_ALAMAT = "alamat_pribadi";
    public static String KEY_KOTA = "kota";
    public static String KEY_PROPINSI = "provinsi";
    public static String KEY_LINKWEB = "link_website";
    public static String KEY_MEDSOS_LINKEDIN = "medsos_linkedin";
    public static String KEY_MEDSOS_FB = "medsos_fb";
    public static String KEY_MEDSOS_TWITTER = "medsos_twitter";
    public static String KEY_MEDSOS_GOOGLEPLUS = "medsos_googleplus";
    public static String KEY_MEDSOS_IG = "medsos_ig";
    public static String KEY_DESCRIPTION = "description";

    public String MDIRECT_LOGIN_URL = Constants.MDIRECT_LOGIN_URL;
    public String MDIRECT_URL = Constants.MDIRECT_URL;
    public String MNEWS_URL = Constants.MNEWS_URL;
    public String APP_ID = Constants.APP_ID;
    public String APP_SECRET = Constants.APP_SECRET;
    public String REDIRECT_URI = Constants.REDIRECT_URI;
    public String REDIRECT_URI_MENU = Constants.REDIRECT_URI_MENU;

    private ServiceAdapter serviceAdapter;

    private Session session;

    private MdirectAccountServices mdirectAccountServices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        session = new Session(this, this);

        FirebaseCrash.log("Activity created");

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        mdirectAccountServices = ServiceGeneratorAccount.createService(MdirectAccountServices.class);


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        CalligraphyConfig.initDefault(new CalligraphyConfig
                .Builder()
                .setDefaultFontPath("fonts/Montserrat_Light.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        initModule();
        startModule();
    }
    public boolean isLoggedIn(){
        boolean loggedIn = false;
        if (getAccessToken() != null){
            if (!getAccessToken().equalsIgnoreCase("aJ5QElpvadHaiz7mcPNPVQx0P3Xxx0P3Xx")){
                showLog("usertoken", getAccessToken());
                showLog("usertoken", "return true");
                loggedIn = true;
            }
        }
        return loggedIn;
    }
    public String getSession(){
        return session.getCustomParams(Session.KEY, "aJ5QElpvadHaiz7mcPNPVQx0P3Xxx0P3Xx");
    }
    public void setSession(String key){
        showLog("usertoken real token", key);
        showLog("usertoken", session.decryptString("aJ5QElpvadHaiz7mcPNPVQx0P3Xxx0P3Xx"));

        Call<RequestToken> call = mdirectAccountServices.getAccessToken(key);
        new getAccesToken().execute(call);

        session.setCustomParams(Session.KEY, key);
    }
    public void setAccessToken(String accessToken){
        showLog("usertoken accesstoken", accessToken);
        session.setCustomParams(Session.KEY_TOKEN, accessToken);
    }
    public String getAccessToken(){
        return session.getCustomParams(Session.KEY_TOKEN, "aJ5QElpvadHaiz7mcPNPVQx0P3Xxx0P3Xx");
    }
    public void setCustomPreferences(String key, String value){
        session.setCustomParams(key, value);
    }
    public Boolean logout(){
        final boolean[] returns = {false};
        if (!getAccessToken().equalsIgnoreCase("aJ5QElpvadHaiz7mcPNPVQx0P3Xxx0P3Xx")) {
            String token = getAccessToken();

            Map<String, String> headers = new HashMap<>();
            headers.put("appid", "174863748390695");
            headers.put("appsecret", "2BdUbH680ERDaJD1LGjX6Td7jR5Z5O2TJzPgNDOjlo4IANW3W9CYEZQ2OuT01cRpyFqxLWGknKWB46h2d6p4qOBEGvLDISRNAxb0hVgrtpy3K5sPNtsMQDjzwAt2MTvP");
            headers.put("usertoken", token);

            Call<ResponseUserProfile> logout = mdirectAccountServices.logout(headers);
            logout.enqueue(new Callback<ResponseUserProfile>() {
                @Override
                public void onResponse(Call<ResponseUserProfile> call, Response<ResponseUserProfile> response) {
                    if (response.isSuccessful()){
                        if (response.body().getSuccess()){
                            setAccessToken("aJ5QElpvadHaiz7mcPNPVQx0P3Xxx0P3Xx");
                            returns[0] = true;
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseUserProfile> call, Throwable t) {
                    returns[0] = false;
                }
            });

        }
        return returns[0];
    }
    public void getMe(){
        if (!getAccessToken().equalsIgnoreCase("aJ5QElpvadHaiz7mcPNPVQx0P3Xxx0P3Xx")){
            String token = getAccessToken();

            Map<String, String> headers = new HashMap<>();
            headers.put("appid", "174863748390695");
            headers.put("appsecret", "2BdUbH680ERDaJD1LGjX6Td7jR5Z5O2TJzPgNDOjlo4IANW3W9CYEZQ2OuT01cRpyFqxLWGknKWB46h2d6p4qOBEGvLDISRNAxb0hVgrtpy3K5sPNtsMQDjzwAt2MTvP");
            headers.put("usertoken", token);

            Call<ResponseUserProfile> callMe = mdirectAccountServices.getMe(headers);
            callMe.enqueue(new Callback<ResponseUserProfile>() {
                @Override
                public void onResponse(Call<ResponseUserProfile> call, Response<ResponseUserProfile> response) {
                    if (response.isSuccessful()){
                        if (response.body().getSuccess()){
                            DataUserProfile dataUserProfile = response.body().getData();
                            DataUserProfileAlamat dataUserProfileAlamat = dataUserProfile.getAlamatPribadi();

                            setCustomPreferences(KEY_ID, String.valueOf(dataUserProfile.getId()));
                            setCustomPreferences(KEY_ROLE, dataUserProfile.getRole());
                            setCustomPreferences(KEY_USERNAME, dataUserProfile.getUsername());
                            setCustomPreferences(KEY_NAME, dataUserProfile.getName());
                            setCustomPreferences(KEY_EMAIL, dataUserProfile.getEmail());
                            setCustomPreferences(KEY_PHONENUMBER, dataUserProfile.getPhoneNumber());
                            setCustomPreferences(KEY_FOTOPROFILE, dataUserProfile.getFotoProfil());
                            setCustomPreferences(KEY_JABATAN, dataUserProfile.getJabatan());
                            setCustomPreferences(KEY_KEAHLIAN, dataUserProfile.getKeahlian());
                            setCustomPreferences(
                                    KEY_ALAMAT,
                                        dataUserProfileAlamat.getJln()+" "
                                        +dataUserProfileAlamat.getNoBangunan()+" "
                                        +dataUserProfileAlamat.getRtrw()+" "
                                        +dataUserProfileAlamat.getKodepos()
                            );
                            setCustomPreferences(KEY_KOTA, String.valueOf(dataUserProfile.getKota()));
                            setCustomPreferences(KEY_PROPINSI, String.valueOf(dataUserProfile.getProvinsi()));
                            setCustomPreferences(KEY_LINKWEB, dataUserProfile.getLinkWebsite());
                            setCustomPreferences(KEY_MEDSOS_LINKEDIN, dataUserProfile.getMedsosLinkedin());
                            setCustomPreferences(KEY_MEDSOS_FB, dataUserProfile.getMedsosFb());
                            setCustomPreferences(KEY_MEDSOS_TWITTER, dataUserProfile.getMedsosTwitter());
                            setCustomPreferences(KEY_MEDSOS_GOOGLEPLUS, dataUserProfile.getMedsosGoogleplus());
                            setCustomPreferences(KEY_MEDSOS_IG, dataUserProfile.getMedsosIg());
                            setCustomPreferences(KEY_DESCRIPTION, dataUserProfile.getDescription());
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseUserProfile> call, Throwable t) {

                }
            });

        }
    }
    public String getCustomPreferences(String key){
        return session.getCustomParams(key, "nothing");
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    private void initModule(){
        serviceAdapter = new ServiceAdapter(getApplicationContext());
    }
    private void startModule(){
        serviceAdapter.startService();
    }
    public void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    public void showLog(String tag, String message){
        Log.d(tag, "showLog: "+message);
    }
    public void writeLog(Throwable throwable){
        FirebaseCrash.report(throwable);
    }
    public void writeLog(String message){
        FirebaseCrash.log(message);
    }

    @Override
    public void sessionChange() {

    }

    class getAccesToken extends AsyncTask<Call, Void, AccessToken>{
//        Dialog dialog;
        /*load dimulai*/
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            dialog = new Dialog(getApplicationContext());
//            dialog.setCancelable(false);
//            dialog.setTitle("Mendapatkan Token");
//            dialog.show();
//        }

        /*load dilakukan*/
        @Override
        protected AccessToken doInBackground(Call[] calls) {
            Call<RequestToken> call = calls[0];
            try {
                final Response<RequestToken> callx = call.execute();
                showLog("usertoken resultRequest", callx.body().getSuccess().toString());
                if (callx.isSuccessful()){

//                    AccessToken accessToken = callx.body().getData();
//                    setAccessToken(accessToken.getToken());

                    return callx.body().getData();
                }
            }catch (IOException e){
                writeLog(e);
            }
            return null;
        }

        /*load selesai*/
        @Override
        protected void onPostExecute(AccessToken requestToken) {
            super.onPostExecute(requestToken);
            if (requestToken != null){
                setAccessToken(requestToken.getToken());
            }
//            dialog.dismiss();
        }
    }
//    @Override
//    protected void onResume() {
//        super.onResume();
////        http://localhost/login/callback?token=OFnGtZEsyIdZXzrQp2HxELIVeUpVVwRun03e1QdbLiBXOqWBEeJOnthsQDSlSMrzi6NYiKZa20I9Amx7Isd0JwsWHuyBnJGLRsjaWJeQ1t7JffMqDaGXJAFlRFXmII7D&next=intent%253A%252F%252Flogin%253Fcode%253D%257BCODE%257D&withmail=
//        if (getIntent().getData() != null){
//            Uri uri = getIntent().getData();
//            if (uri != null) {
//                showLog("usertoken", getIntent().getData().toString());
//                String code = uri.getQueryParameter("token");
//                if (code != null) {
//                    setSession(code);
//                } else if (uri.getQueryParameter("error") != null) {
//                    showToast("Terjadi Error, Silakan Login kembali");
//                }
//            }
//        }
//    }

}
