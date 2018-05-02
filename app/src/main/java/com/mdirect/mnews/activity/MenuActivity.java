package com.mdirect.mnews.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mdirect.mnews.BaseApps;
import com.mdirect.mnews.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Beni on 01/05/2018.
 */

public class MenuActivity extends BaseApps {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_menu);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.imgClose)
    public void doClose(){
        finish();
    }

    @OnClick(R.id.rlBeranda)
    public void clickBeranda(){

    }
    @OnClick(R.id.rlKanal)
    public void clickKanal(){

    }
    @OnClick(R.id.rlProfil)
    public void clickProfil(){

    }
    @OnClick(R.id.txtKeluar)
    public void clickKeluar(){

    }
    @OnClick(R.id.txtAturanKebijakan)
    public void clickAturanKebijakan(){

    }
    @OnClick(R.id.txtTentangKami)
    public void clickTentangKami(){

    }
}
