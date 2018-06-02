package illiyin.mhandharbeni.databasemodule.utils;

import android.content.Context;
import android.content.SharedPreferences;

import illiyin.mhandharbeni.databasemodule.model.mnews.response.data.get_featured_post.DataFeaturedPost;

public class SharedPref {

    private Context context;

    String PREFS_NAME = "DATABASEMODULE";
    String PREFS_KEY = "KEYSDBMODULE";

    private DataFeaturedPost dataFeaturedPost;
    private String serializedData;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public SharedPref(Context context) {
        this.context = context;
        this.sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public String createObject(DataFeaturedPost dataFeatured){
        dataFeaturedPost = dataFeatured;
        serializedData = dataFeaturedPost.serialize();
        return serializedData;
    }

    public void setFeatured(String serializedData){
        editor = sharedPreferences.edit();
        editor.putString(PREFS_KEY, serializedData);
        editor.commit();
    }

    public DataFeaturedPost getFeatured(){
        String serializedMenu = sharedPreferences.getString(PREFS_KEY, null);
        return dataFeaturedPost.create(serializedData);
    }

}
