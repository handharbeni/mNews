package com.mdirect.mnews;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseApps {
    private static final String TAG = "MainActivity";
    @BindView(R.id.btnLogin)
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnLogin)
    public void showLogin(){
        String url = MDIRECT_LOGIN_URL+"?appid="+APP_ID+"&next="+REDIRECT_URI;
        Log.d(TAG, "showLogin: "+url);
        Intent intent = new Intent(
                Intent.ACTION_VIEW,
                Uri.parse(url));
        startActivity(intent);
    }
    @Override
    protected void onResume() {
        super.onResume();
//        http://localhost/login/callback?token=OFnGtZEsyIdZXzrQp2HxELIVeUpVVwRun03e1QdbLiBXOqWBEeJOnthsQDSlSMrzi6NYiKZa20I9Amx7Isd0JwsWHuyBnJGLRsjaWJeQ1t7JffMqDaGXJAFlRFXmII7D&next=intent%253A%252F%252Flogin%253Fcode%253D%257BCODE%257D&withmail=
        if (getIntent().getData() != null){
            Log.d(TAG, "onResume: "+getIntent().getData().toString());
            Uri uri = getIntent().getData();
            if (uri != null) {
                String code = uri.getQueryParameter("token");
                Log.d(TAG, "onResume: "+code);
                if (code != null) {
                    Toast.makeText(this, code, Toast.LENGTH_SHORT).show();
                } else if (uri.getQueryParameter("error") != null) {
                    Toast.makeText(this, uri.getQueryParameter("error"), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

}
