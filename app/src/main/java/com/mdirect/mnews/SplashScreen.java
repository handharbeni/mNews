package com.mdirect.mnews;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import illiyin.mhandharbeni.databasemodule.model.mnews.AdapterRequest;
import illiyin.mhandharbeni.databasemodule.model.mnews.response.data.get_all_post.DataGetAllPost;
import illiyin.mhandharbeni.databasemodule.model.mnews.response.data.get_menus.DataMenus;
import illiyin.mhandharbeni.realmlibrary.Crud;
import io.realm.RealmResults;

/**
 * Created by Beni on 26/03/2018.
 */

public class SplashScreen extends BaseApps{
    Crud crud;
    DataMenus dataMenus;

    AdapterRequest adapterRequest;

    @BindView(R.id.idLoading)
    TextView idLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);
        ButterKnife.bind(this);

        if (android.os.Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.INTERNET) == PackageManager.PERMISSION_DENIED || checkSelfPermission(Manifest.permission.ACCESS_NETWORK_STATE) == PackageManager.PERMISSION_DENIED) {
                Intent i = new Intent(this, PermissionPage.class);
                startActivity(i);
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
            try {
                getMe();
                initModule();
                initData();
            }catch (Exception e){
                writeLog(e);
            }
            }
        },5000);
    }

    public void initModule(){
        dataMenus = new DataMenus();
        crud = new Crud(this, dataMenus);

        adapterRequest = new AdapterRequest(this);
    }

    public void initData(){
        idLoading.setText("Loading Data Menu");
        RealmResults resultsMenu = crud.read();
        if (resultsMenu.size() < 1){
            /*data sudah ada*/
            boolean returns = adapterRequest.syncMenus(true);
            if (returns){
                /*pindah ke mainactivity*/
                initFeatured();
            }
        }else{
            initFeatured();
        }
    }

    public void initFeatured(){
        idLoading.setText("Loading Featured");
        Boolean isTrue = adapterRequest.syncFeatured(true);
        if (isTrue){
            initNews();
        }
    }

    public void initNews(){
        idLoading.setText("Loading News");
        DataGetAllPost dataGetAllPost = new DataGetAllPost();
        Crud crudNews = new Crud(getApplicationContext(), dataGetAllPost);
        RealmResults results = crudNews.read();
        if (results.size() < 1){
            Boolean isTrue = adapterRequest.syncPost("1", true);
            if (isTrue){
                gotoMain();
            }
        }else{
            gotoMain();
        }
    }

    private void gotoMain(){
        Intent i = new Intent(SplashScreen.this, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        finish();
    }
}
