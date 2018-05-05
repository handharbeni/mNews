package com.mdirect.mnews.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;

import com.mdirect.mnews.BaseApps;
import com.mdirect.mnews.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Beni on 02/04/2018.
 */

public class Login extends BaseApps {
    @BindView(R.id.btnLogin)
    AppCompatButton btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login_register);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnLogin)
    public void clickLogin(){
        if (!isLoggedIn()){
            String url = MDIRECT_LOGIN_URL+"?appid="+APP_ID+"&next="+REDIRECT_URI_MENU;
            showLog("usertoken URI", url);
            Intent intent = new Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(url));
            startActivity(intent);
        }
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        showToast("Result Login "+requestCode);
//        if (requestCode == 01){
//            if (getIntent().getData() != null){
//                Uri uri = getIntent().getData();
//                if (uri != null) {
//                    String code = uri.getQueryParameter("token");
//                    if (code != null) {
//                        setSession(code);
//                        finish();
//                    } else if (uri.getQueryParameter("error") != null) {
//                        showToast("Terjadi Error, Silakan Login kembali");
//                    }
//                }
//            }
//        }
//    }

    @Override
    protected void onResume() {
        super.onResume();
        if (getIntent().getData() != null){
            Uri uri = getIntent().getData();
            if (uri != null) {
                showLog("usertoken U R I ", String.valueOf(uri));
                String code = uri.getQueryParameter("token");
                if (code != null) {
                    setSession(code);
                    finish();
                } else if (uri.getQueryParameter("error") != null) {
                    showToast("Terjadi Error, Silakan Login kembali");
                }
            }
        }
    }
}
