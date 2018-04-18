package com.mdirect.mnews.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mdirect.mnews.BaseApps;
import com.mdirect.mnews.R;
import com.mdirect.mnews.adapter.AdapterItemComment;
import com.mdirect.mnews.utils.ClickListener;

import java.io.IOException;
import java.security.spec.ECField;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTouch;
import illiyin.mhandharbeni.databasemodule.generator.ServiceGenerator;
import illiyin.mhandharbeni.databasemodule.model.mnews.response.ResponseGetComment;
import illiyin.mhandharbeni.databasemodule.model.mnews.response.data.get_comment.Comment;
import illiyin.mhandharbeni.databasemodule.model.mnews.response.data.get_comment.DataGetComment;
import illiyin.mhandharbeni.databasemodule.services.MnewsServices;
import retrofit2.Call;
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
        showToast("Touched");
        return false;
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
}
