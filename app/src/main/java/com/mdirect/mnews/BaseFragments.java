package com.mdirect.mnews;

import android.support.v4.app.Fragment;
import android.util.Log;

public class BaseFragments extends Fragment {
    public void showLog(String tag, String message){
        Log.d(tag, "showLog: "+message);
    }
}
