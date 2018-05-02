package com.mdirect.mnews.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.mdirect.mnews.BaseApps;
import com.mdirect.mnews.R;

import java.io.IOException;

import butterknife.BindView;
import illiyin.mhandharbeni.databasemodule.model.mnews.response.ResponseGetPostKategori;
import illiyin.mhandharbeni.databasemodule.model.mnews.response.data.general.StringResponse;
import retrofit2.Call;
import retrofit2.Response;

public class ListNewsKategori extends BaseApps {
    @BindView(R.id.rvListNews)
    RecyclerView rvListNews;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_list_news);
    }


    class asyncFullUrl extends AsyncTask<Call, Void, ResponseGetPostKategori> {

        @Override
        protected ResponseGetPostKategori doInBackground(Call[] calls) {
            Call<ResponseGetPostKategori> get = calls[0];
            try{
                final Response<ResponseGetPostKategori> response = get.execute();
                if (response.isSuccessful()){
                    ResponseGetPostKategori responseString = response.body();
                    if (responseString.getSuccess()){
//                        fullHtml = responseString.getMessage();
                    }
                }
            } catch (IOException e) {
                writeLog(e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(ResponseGetPostKategori stringResponse) {
            super.onPostExecute(stringResponse);
        }
    }
}
