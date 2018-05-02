package com.mdirect.mnews;

import android.app.Dialog;
import android.app.Service;
import android.content.Context;
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

import illiyin.mhandharbeni.databasemodule.generator.ServiceGenerator;
import illiyin.mhandharbeni.databasemodule.generator.ServiceGeneratorAccount;
import illiyin.mhandharbeni.databasemodule.model.account.response.data.account.AccessToken;
import illiyin.mhandharbeni.databasemodule.model.account.response.data.account.RequestToken;
import illiyin.mhandharbeni.databasemodule.services.MdirectAccountServices;
import illiyin.mhandharbeni.servicemodule.ServiceAdapter;
import illiyin.mhandharbeni.sessionlibrary.Session;
import illiyin.mhandharbeni.sessionlibrary.SessionListener;
import retrofit2.Call;
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

    public String MDIRECT_LOGIN_URL = Constants.MDIRECT_LOGIN_URL;
    public String MDIRECT_URL = Constants.MDIRECT_URL;
    public String MNEWS_URL = Constants.MNEWS_URL;
    public String APP_ID = Constants.APP_ID;
    public String APP_SECRET = Constants.APP_SECRET;
    public String REDIRECT_URI = Constants.REDIRECT_URI;

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
            if (!getAccessToken().equalsIgnoreCase(session.decryptString("aJ5QElpvadHaiz7mcPNPVQx0P3Xxx0P3Xx"))){
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
}
