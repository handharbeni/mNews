package com.mdirect.mnews.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

import com.mdirect.mnews.BaseApps;
import com.mdirect.mnews.R;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import illiyin.mhandharbeni.databasemodule.generator.ServiceGenerator;
import illiyin.mhandharbeni.databasemodule.model.mnews.response.data.general.StringResponse;
import illiyin.mhandharbeni.databasemodule.services.MnewsServices;
import retrofit2.Call;
import retrofit2.Response;

public class FullHtmlActivity extends BaseApps {

    @BindView(R.id.imgClose)
    ImageView imgClose;

    @BindView(R.id.webView)
    WebView webView;

    private MnewsServices mnewsServices;

    private String fullHtml = "nothing";
    public static String KEY_FIELD = "key_field";
    private Integer field = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_html);
        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        initBundle();
        initModule();
    }

    private void initBundle(){
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            field = bundle.getInt(KEY_FIELD);
        }
    }

    private void initModule(){
        mnewsServices = ServiceGenerator.createService(MnewsServices.class);
        Call<StringResponse> get = mnewsServices.getAbout();
        switch (field){
            case 0:
                /*about*/
                get = mnewsServices.getAbout();
                break;
            case 1:
                /*tos*/
                get = mnewsServices.getTOS();
                break;
            case 2:
                get = mnewsServices.getAbout();
                break;
            default:
                get = mnewsServices.getAbout();
                break;
        }
        new asyncFullUrl().execute(get);
    }

    private void fillWebView(){
        WebSettings cs = webView.getSettings();
        cs.setJavaScriptEnabled(true);
        cs.setUseWideViewPort(false);
        cs.setLoadWithOverviewMode(true);

        webView.loadData(fullHtml, "text/html", "UTF-8");
    }

    class asyncFullUrl extends AsyncTask<Call, Void, StringResponse>{

        @Override
        protected StringResponse doInBackground(Call[] calls) {
            Call<StringResponse> get = calls[0];
            try{
                final Response<StringResponse> response = get.execute();
                if (response.isSuccessful()){
                    StringResponse responseString = response.body();
                    if (responseString.getSuccess()){
                        fullHtml = responseString.getMessage();
                    }
                }
            } catch (IOException e) {
                writeLog(e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(StringResponse stringResponse) {
            super.onPostExecute(stringResponse);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    fillWebView();
                }
            });
        }
    }
}
