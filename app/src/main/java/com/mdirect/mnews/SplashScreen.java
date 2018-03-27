package com.mdirect.mnews;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import illiyin.mhandharbeni.databasemodule.model.mnews.AdapterRequest;
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
    }

    @Override
    protected void onStart() {
        super.onStart();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                initModule();
                initData();
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
                Intent i = new Intent(SplashScreen.this, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();
            }
        }else{
            Intent i = new Intent(SplashScreen.this, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            finish();
        }
    }
}
