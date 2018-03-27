package com.mdirect.mnews;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.widget.Toast;

import com.mdirect.mnews.value.Constants;

import illiyin.mhandharbeni.servicemodule.ServiceAdapter;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Beni on 07/03/2018.
 */

public class BaseApps extends AppCompatActivity{
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        CalligraphyConfig.initDefault(new CalligraphyConfig
                .Builder()
                .setDefaultFontPath("fonts/Montserrat_Black.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        initModule();
        startModule();
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

}
