package com.mdirect.mnews.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mdirect.mnews.BaseApps;
import com.mdirect.mnews.R;
import com.mdirect.mnews.adapter.AdapterItemKanal;
import com.mdirect.mnews.utils.ClickListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import illiyin.mhandharbeni.databasemodule.model.mnews.response.data.get_menus.DataMenus;
import illiyin.mhandharbeni.realmlibrary.Crud;
import io.realm.RealmResults;

import static com.mdirect.mnews.activity.FullHtmlActivity.KEY_FIELD;
import static com.mdirect.mnews.activity.ListNewsKategori.KEY_SLUG;

/**
 * Created by Beni on 01/05/2018.
 */

public class MenuActivity extends BaseApps implements ClickListener {
    @BindView(R.id.imgClose)
    ImageView imgClose;

    @BindView(R.id.rlBeranda)
    RelativeLayout rlBeranda;

    @BindView(R.id.rlKanal)
    RelativeLayout rlKanal;

    @BindView(R.id.rlProfil)
    RelativeLayout rlProfil;

    @BindView(R.id.txtKeluar)
    TextView txtKeluar;

    @BindView(R.id.txtAturanKebijakan)
    TextView txtAturanKebijakan;

    @BindView(R.id.txtTentangKami)
    TextView txtTentangKami;

    @BindView(R.id.rvItemKanal)
    RecyclerView rvItemKanal;


    private Crud crudMenus;
    private ArrayList<DataMenus> listKanal;
    private AdapterItemKanal adapterItemKanal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_menu);

        ButterKnife.bind(this);
        populateDataKanal();
    }

    @OnClick(R.id.imgClose)
    public void doClose(){
        finished();
    }

    @OnClick(R.id.rlBeranda)
    public void clickBeranda(){
        finished();
    }
    @OnClick(R.id.rlKanal)
    public void clickKanal(){
//        expand more kanal
        if (rvItemKanal.getVisibility() == View.GONE){
            rvItemKanal.setVisibility(View.VISIBLE);
        }else if(rvItemKanal.getVisibility() == View.VISIBLE){
            rvItemKanal.setVisibility(View.GONE);
        }
    }
    @OnClick(R.id.rlProfil)
    public void clickProfil(){

    }
    @OnClick(R.id.txtKeluar)
    public void clickKeluar(){

    }
    @OnClick(R.id.txtAturanKebijakan)
    public void clickAturanKebijakan(){
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_FIELD, 1);

        Intent i = new Intent(this, FullHtmlActivity.class);
        i.putExtras(bundle);
        startActivity(i);
    }
    @OnClick(R.id.txtTentangKami)
    public void clickTentangKami(){
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_FIELD, 0);

        Intent i = new Intent(this, FullHtmlActivity.class);
        i.putExtras(bundle);
        startActivity(i);
    }

    private void finished(){
        finish();
    }
    private void populateDataKanal(){
        listKanal = new ArrayList<>();

        DataMenus dataMenus = new DataMenus();
        crudMenus = new Crud(getApplicationContext(),dataMenus);
        RealmResults resultMenus = crudMenus.read();
        for (int i=0;i<resultMenus.size();i++){
            DataMenus dMenu = (DataMenus) resultMenus.get(i);
            listKanal.add(dMenu);
        }

        adapterItemKanal = new AdapterItemKanal(listKanal, this, this);
        rvItemKanal.setAdapter(adapterItemKanal);


        LinearLayoutManager llm = new LinearLayoutManager(this);
        rvItemKanal.setLayoutManager(llm);

        rvItemKanal.setVisibility(View.GONE);
    }
    private void populateDataProfile(){

    }

    @Override
    public void clicked(int id) {

    }

    @Override
    public void clicked(String id) {
        Bundle bundle = new Bundle();
        bundle.putString(ListNewsKategori.KEY_SLUG, id);

        Intent i = new Intent(this, ListNewsKategori.class);
        i.putExtras(bundle);
        startActivity(i);
    }
}
