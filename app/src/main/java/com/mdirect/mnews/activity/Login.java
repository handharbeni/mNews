package com.mdirect.mnews.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.Button;

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

    @BindView(R.id.layoutSuccess)
    ConstraintLayout layoutSuccess;

    @BindView(R.id.layoutButton)
    ConstraintLayout layoutButtons;

    @BindView(R.id.btnBack)
    Button btnBack;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login_register);

        ButterKnife.bind(this);
        setContext(getWindow().getContext());
    }

    @OnClick(R.id.btnLogin)
    public void clickLogin(){
        if (!isLoggedIn()){
            String url = MDIRECT_LOGIN_URL+"?appid="+APP_ID+"&next="+REDIRECT_URI;
            showLog("usertoken URI", url);
            Intent intent = new Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(url));
//            startActivityForResult(intent, 01);
            startActivity(intent);
            finish();
        }
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        showToast("Result Login "+requestCode);
//        if (requestCode == 01){
//            checkToken();
//        }
//    }

    @Override
    protected void onResume() {
        super.onResume();
        checkToken();
    }

    private void checkToken(){
        if (progressDialog == null)
            progressDialog = new ProgressDialog(Login.this);

        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        if (getIntent().getData() != null){
            showLog("DataIntent", getIntent().getData().toString());
            Uri uri = getIntent().getData();
            if (uri != null) {
                String code = uri.getQueryParameter("token");
                if (code != null) {
                    setSession(code);
                    showSuccess();
                } else if (uri.getQueryParameter("error") != null) {
                    showToast("Terjadi Error, Silakan Login kembali");
                }
            }
        }
        progressDialog.dismiss();

    }
    private void showSuccess(){
        layoutButtons.setVisibility(View.GONE);
        layoutSuccess.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.btnBack)
    public void backFinish(){
        finish();
    }
}
