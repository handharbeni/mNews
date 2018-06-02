package com.mdirect.mnews;

import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;

import java.util.HashMap;

public class BaseFragments extends Fragment {
    public void showLog(String tag, String message){
        Log.d(tag, "showLog: "+message);
    }
    public HashMap<String, String> getWidthHeight(){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        HashMap<String, String> wh = new HashMap<>();
        wh.put("height", String.valueOf(height));
        wh.put("width", String.valueOf(width));
        return wh;
    }
}
