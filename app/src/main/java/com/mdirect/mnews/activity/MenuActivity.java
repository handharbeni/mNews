package com.mdirect.mnews.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mdirect.mnews.BaseApps;
import com.mdirect.mnews.R;
import com.mdirect.mnews.adapter.AdapterItemKanal;
import com.mdirect.mnews.utils.ClickListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import illiyin.mhandharbeni.databasemodule.generator.ServiceGeneratorAccount;
import illiyin.mhandharbeni.databasemodule.model.mnews.response.data.get_menus.DataMenus;
import illiyin.mhandharbeni.databasemodule.services.MdirectAccountServices;
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

    @BindView(R.id.txtLogin)
    TextView txtLogin;

    @BindView(R.id.txtAturanKebijakan)
    TextView txtAturanKebijakan;

    @BindView(R.id.txtTentangKami)
    TextView txtTentangKami;

    @BindView(R.id.rvItemKanal)
    RecyclerView rvItemKanal;

    @BindView(R.id.imgUser) ImageView imgUser;
    @BindView(R.id.txtUsername) TextView txtUsername;


    private Crud crudMenus;
    private ArrayList<DataMenus> listKanal;
    private AdapterItemKanal adapterItemKanal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_menu);

        ButterKnife.bind(this);


        initFeature();
        initDataProfil();
        populateDataKanal();
    }

    public void initDataProfil(){
        String fotoProfil = getCustomPreferences(KEY_FOTOPROFILE);
        String nama = getCustomPreferences(KEY_NAME);
        if (!fotoProfil.equalsIgnoreCase("nothing")){
            /*load image user*/
            Glide.with(this).load(fotoProfil).into(imgUser);
        }else if(!nama.equalsIgnoreCase("nothing")){
            /*load name user*/
            txtUsername.setText(nama);
        }else if(!fotoProfil.equalsIgnoreCase("nothing") && !nama.equalsIgnoreCase("nothing")){
            /*load all*/
            Glide.with(this).load(fotoProfil).into(imgUser);
            txtUsername.setText(nama);
        }
    }

    private void initFeature(){
        if (!isLoggedIn()){
            rlProfil.setVisibility(View.GONE);
            txtKeluar.setVisibility(View.GONE);
            imgUser.setVisibility(View.GONE);
            txtUsername.setVisibility(View.GONE);
            txtLogin.setVisibility(View.VISIBLE);
        }else{
            rlProfil.setVisibility(View.VISIBLE);
            txtKeluar.setVisibility(View.VISIBLE);
            imgUser.setVisibility(View.VISIBLE);
            txtUsername.setVisibility(View.VISIBLE);
            txtLogin.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.txtLogin)
    public void login(){
        Intent i = new Intent(this, Login.class);
        startActivity(i);
//        if (!isLoggedIn()){
//            String url = MDIRECT_LOGIN_URL+"?appid="+APP_ID+"&next="+REDIRECT_URI_MENU;
//            showLog("usertoken", url);
//            Intent intent = new Intent(
//                    Intent.ACTION_VIEW,
//                    Uri.parse(url));
//            startActivityForResult(intent, 01);
//        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        http://localhost/login/callback?token=OFnGtZEsyIdZXzrQp2HxELIVeUpVVwRun03e1QdbLiBXOqWBEeJOnthsQDSlSMrzi6NYiKZa20I9Amx7Isd0JwsWHuyBnJGLRsjaWJeQ1t7JffMqDaGXJAFlRFXmII7D&next=intent%253A%252F%252Flogin%253Fcode%253D%257BCODE%257D&withmail=
        if (getIntent().getData() != null){
            Uri uri = getIntent().getData();
            if (uri != null) {
                String code = uri.getQueryParameter("token");
                if (code != null) {
                    setSession(code);
                } else if (uri.getQueryParameter("error") != null) {
                    showToast("Terjadi Error, Silakan Login kembali");
                }
            }
        }
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
        Boolean keluar = logout();
        showLog("usertoken", String.valueOf(keluar));
        if (keluar){
            finished();
        }
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
