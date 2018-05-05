package com.mdirect.mnews.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mdirect.mnews.BaseApps;
import com.mdirect.mnews.R;
import com.mdirect.mnews.adapter.AdapterItemComment;
import com.mdirect.mnews.utils.ClickListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;
import illiyin.mhandharbeni.databasemodule.generator.ServiceGenerator;
import illiyin.mhandharbeni.databasemodule.model.mnews.response.ResponseDeleteComment;
import illiyin.mhandharbeni.databasemodule.model.mnews.response.ResponseGetComment;
import illiyin.mhandharbeni.databasemodule.model.mnews.response.ResponseReportComment;
import illiyin.mhandharbeni.databasemodule.model.mnews.response.data.get_comment.Comment;
import illiyin.mhandharbeni.databasemodule.model.mnews.response.data.get_comment.DataGetComment;
import illiyin.mhandharbeni.databasemodule.services.MnewsServices;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Beni on 02/04/2018.
 */

public class ListComment extends BaseApps implements ClickListener {
    private static final String TAG = ListComment.class.getSimpleName();
    public static String KEY_SLUGID = "slug_id";
    public static String KEY_COUNT_COMMENT = "count_comment";
    public static String KEY_TITLE_NEWS = "title_news";
    public static String KEY_IMAGE_NEWS = "image_news";
    public static String KEY_DATE_NEWS = "date_news";


    private static String slug_id = "slug_id";
    private static String count_comment = "0";
    private static String title_news = "Title News";
    private static String image_news = "Image News";
    private static String date_news = "Date News";

    private MnewsServices mnewsServices;

    private AdapterItemComment adapterItemComment;
    private List<Comment> listComment;


    /*Bind View*/

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.titleToolbar)
    TextView titleToolbar;

    @BindView(R.id.rvComment)
    RecyclerView rvComment;

    @BindView(R.id.imgNews)
    ImageView imgNews;
    @BindView(R.id.titleNews)
    TextView titleNews;
    @BindView(R.id.dateNews)
    TextView dateNews;

    @BindView(R.id.etSendComment)
    EditText etSendComment;
    @BindView(R.id.btnSendComment)
    ImageView btnSendComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main_list_comments);
        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        initBundle();
        initModule();
        initList();
    }

    @OnTouch(R.id.etSendComment)
    boolean onSendTouch(){
        Intent i = new Intent(this, Login.class);
        startActivity(i);
//        if (!isLoggedIn()){
//            String url = MDIRECT_LOGIN_URL+"?appid="+APP_ID+"&next="+REDIRECT_URI;
//            Intent intent = new Intent(
//                    Intent.ACTION_VIEW,
//                    Uri.parse(url));
//            startActivityForResult(intent, 02);
//        }
        return false;
    }

    @OnClick(R.id.btnSendComment)
    public void doSendComment(){
        if (!isLoggedIn()){
            onSendTouch();
        }else{
            String token = getAccessToken();
            String comment = etSendComment.getText().toString();

            Map<String, String> headers = new HashMap<>();
            headers.put("usertoken", token);
            headers.put("mnewstoken", MnewsServices.mnewstoken);

            showLog("usertoken used token ", token);

            Call<ResponseGetComment> sendComment = mnewsServices.postComment(headers, slug_id, comment);
            sendComment.enqueue(new Callback<ResponseGetComment>() {
                @Override
                public void onResponse(Call<ResponseGetComment> call, Response<ResponseGetComment> response) {
                    if (response.isSuccessful()){
                        etSendComment.setText("");
                        Call<ResponseGetComment> callComments = mnewsServices.getComment(slug_id, "1");
                        new callComment().execute(callComments);
                    }
                }

                @Override
                public void onFailure(Call<ResponseGetComment> call, Throwable t) {
                    showToast("Gagal mengirim komentar, Coba lagi ...");
                }
            });
        }
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
    private void initList(){
        Call<ResponseGetComment> call = mnewsServices.getComment(slug_id, "1");
        new callComment().execute(call);
    }

    private void initModule(){
        mnewsServices = ServiceGenerator.createService(MnewsServices.class);
    }

    private void initBundle(){
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            slug_id = bundle.getString(KEY_SLUGID) != null ? bundle.getString(KEY_SLUGID) : slug_id;
            count_comment = bundle.getString(KEY_COUNT_COMMENT) != null ? bundle.getString(KEY_COUNT_COMMENT) : count_comment;
            title_news = bundle.getString(KEY_TITLE_NEWS) != null ? bundle.getString(KEY_TITLE_NEWS) : title_news;
            image_news = bundle.getString(KEY_IMAGE_NEWS) != null ? bundle.getString(KEY_IMAGE_NEWS) : image_news;
            date_news = bundle.getString(KEY_DATE_NEWS) != null ? bundle.getString(KEY_DATE_NEWS) : date_news;
            initView();
        }else{
            showToast("Terjadi Kesalahan!");
            finish();
        }
    }

    private void initView(){
        titleToolbar.setText("Komentar ("+count_comment+")");

        Glide.with(this).load(image_news).into(imgNews);
        titleNews.setText(title_news);
        dateNews.setText(date_news);
    }

    private void initSkeleton(){

    }

    @Override
    public void clicked(int id) {

    }

    @Override
    public void clicked(String id) {

    }

    private class callComment extends AsyncTask<Call, Void, DataGetComment>{

        @Override
        protected DataGetComment doInBackground(Call[] calls) {
            Call<ResponseGetComment> call = calls[0];
            try {
                final Response<ResponseGetComment> callx = call.execute();
                if (callx.isSuccessful()){
                    return callx.body().getData();
                }
            }catch (Exception e){
                writeLog(e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(DataGetComment dataGetComment) {
            super.onPostExecute(dataGetComment);
            listComment = new ArrayList<>();
            listComment = dataGetComment.getComments();
            initAdapter();
        }
    }

    private void initAdapter(){
        if (listComment != null || listComment.size() > 0){
            adapterItemComment = new AdapterItemComment(this, listComment, this);

            LinearLayoutManager llm = new LinearLayoutManager(this);
            rvComment.setLayoutManager(llm);
            rvComment.setAdapter(adapterItemComment);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getTitle().toString().equalsIgnoreCase("HAPUS")){
            deleteComment(item.getOrder());
        }else if(item.getTitle().toString().equalsIgnoreCase("LAPORKAN")){
            reportComment(item.getOrder());
        }
        return super.onContextItemSelected(item);
    }

    private void deleteComment(int id){
        String token = getAccessToken();

        Map<String, String> headers = new HashMap<>();
        headers.put("usertoken", token);
        headers.put("mnewstoken", MnewsServices.mnewstoken);

        showLog("usertoken used token ", token);

        Call<ResponseDeleteComment> sendComment = mnewsServices.deleteComment(headers, String.valueOf(id));
        sendComment.enqueue(new Callback<ResponseDeleteComment>() {
            @Override
            public void onResponse(Call<ResponseDeleteComment> call, Response<ResponseDeleteComment> response) {
                if (response.isSuccessful()){
                    showToast("Hapus Komentar berhasil ..");
                    etSendComment.setText("");
                    Call<ResponseGetComment> callComments = mnewsServices.getComment(slug_id, "1");
                    new callComment().execute(callComments);
                }
            }

            @Override
            public void onFailure(Call<ResponseDeleteComment> call, Throwable t) {
                showToast("Hapus Komentar gagal, silakan coba lagi ..");
            }
        });
    }
    private void reportComment(int id){
        String token = getAccessToken();

        Map<String, String> headers = new HashMap<>();
        headers.put("usertoken", token);
        headers.put("mnewstoken", MnewsServices.mnewstoken);

        showLog("usertoken used token ", token);

        Call<ResponseReportComment> sendComment = mnewsServices.reportComment(headers, String.valueOf(id));
        sendComment.enqueue(new Callback<ResponseReportComment>() {
            @Override
            public void onResponse(Call<ResponseReportComment> call, Response<ResponseReportComment> response) {
                if (response.isSuccessful()){
                    showToast("Laporan Komentar berhasil ..");
                    etSendComment.setText("");
                    Call<ResponseGetComment> callComments = mnewsServices.getComment(slug_id, "1");
                    new callComment().execute(callComments);
                }
            }

            @Override
            public void onFailure(Call<ResponseReportComment> call, Throwable t) {
                showToast("Laporan Komentar gagal, silakan coba lagi ..");
            }
        });
    }
}
