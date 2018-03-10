package com.mdirect.mnews;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Beni on 07/03/2018.
 */

public class BaseApps extends AppCompatActivity{
    public Integer REQUIREMENTS_ANDROID = Build.VERSION_CODES.JELLY_BEAN;

    public static String CONTENTTYPE_JSON = "application/json";
    public static String CONTENTTYPE_URLENCODED = "application/x-www-form-urlencoded";

    public static String KEY_APP_ID = "appid";
    public static String KEY_APP_SECRET = "appsecret";
    public static String KEY_CLIENT_TOKEN = "token";
    public static String KEY_CLIENT_USERTOKEN = "usertoken";
    public static String KEY_CLIENT_ID = "clientid";
    public static String KEY_CLIENT_SECRET = "clientsecret";

    public String APP_ID = "";
    public String APP_SECRET = "";

    public String CLIENT_TOKEN = "";

    public String CLIENT_ID = "";
    public String CLIENT_SECRET = "";


}
