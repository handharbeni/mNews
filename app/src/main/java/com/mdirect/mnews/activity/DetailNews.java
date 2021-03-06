package com.mdirect.mnews.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;
import com.mdirect.mnews.BaseApps;
import com.mdirect.mnews.R;
import com.mdirect.mnews.adapter.AdapterRelated;
import com.mdirect.mnews.utils.ClickListener;
import com.mdirect.mnews.utils.DateFormatter;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import illiyin.mhandharbeni.databasemodule.generator.ServiceGenerator;
import illiyin.mhandharbeni.databasemodule.model.mnews.response.ResponseGetComment;
import illiyin.mhandharbeni.databasemodule.model.mnews.response.ResponseGetSinglePost;
import illiyin.mhandharbeni.databasemodule.model.mnews.response.data.get_comment.DataGetComment;
import illiyin.mhandharbeni.databasemodule.model.mnews.response.data.get_single_post.DataGetSinglePost;
import illiyin.mhandharbeni.databasemodule.model.mnews.response.data.get_single_post.Related;
import illiyin.mhandharbeni.databasemodule.model.mnews.response.data.get_single_post.Single;
import illiyin.mhandharbeni.databasemodule.services.MnewsServices;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Beni on 02/04/2018.
 */

public class DetailNews extends BaseApps implements ClickListener {
    private static final String TAG = DetailNews.class.getSimpleName();
    public static String KEY_SLUGID = "slug_id";

    @BindView(R.id.rootView) ScrollView rootView;

    private SkeletonScreen skeletonScreen;

    private MnewsServices mnewsServices;

    private String slug_id = null;

    private String count_comment = null;
    private String title_news = null;
    private String image_news = null;
    private String date_news = null;

    @BindView(R.id.detail_img_banner) ImageView img_banner;
    @BindView(R.id.detail_tvTitle) TextView tvTitle;
    @BindView(R.id.detail_tvDate) TextView tvDate;
    @BindView(R.id.detail_tvContent) WebView tvContent;
    @BindView(R.id.detail_btnComment) AppCompatButton btnComment;
    @BindView(R.id.rvRelated) RecyclerView rvRelated;
    @BindView(R.id.back) ImageView back;
    @BindView(R.id.titleToolbar) TextView titleToolbar;

    private List<Related> listRelated;
    private AdapterRelated adapterRelated;
    private DateFormatter dateFormatter;

    private HashMap<String, String> getWidthHeight(){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        HashMap<String, String> wh = new HashMap<>();
        wh.put("height", String.valueOf(height));
        wh.put("width", String.valueOf(width));
        return wh;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main_detail_news);
        ButterKnife.bind(this);
        startSkeleton();
        dateFormatter = new DateFormatter();
    }

    private void startSkeleton(){
        skeletonScreen = Skeleton.bind(rootView)
                .load(R.layout.layout_load_news_before)
                .duration(1000)
                .color(R.color.shimmer_color)
                .angle(30)
                .show();
    }
    private void stopSkeleton(){
        skeletonScreen.hide();
    }

    @Override
    protected void onStart() {
        super.onStart();
        initAll();
    }

    private void initAll(){
        initBundle();
        initModule();
        initCall();
    }

    private void initAll(String slug_ids){
        rootView.fullScroll(ScrollView.FOCUS_UP);
        startSkeleton();
        slug_id = slug_ids != null ? slug_ids : slug_id;
        initModule();
        initCall();
    }

    private void initModule(){
        mnewsServices = ServiceGenerator.createService(MnewsServices.class);
    }

    private void initBundle(){
        Bundle bundle = getIntent().getExtras();
        slug_id = bundle != null ? bundle.getString(KEY_SLUGID) : slug_id;
    }

    private void initCall(){
        if (slug_id != null){
            Call<ResponseGetSinglePost> call = mnewsServices.getSinglePost(slug_id);
            new CallSingleNews().execute(call);
        }
    }

    @Override
    public void clicked(int id) {

    }

    @Override
    public void clicked(String id) {
        initAll(id);
    }

    private class CallSingleNews extends AsyncTask<Call, Void, DataGetSinglePost>{

        @Override
        protected DataGetSinglePost doInBackground(Call[] calls) {
            Call<ResponseGetSinglePost> call = calls[0];
            try {
                final Response<ResponseGetSinglePost> callx = call.execute();
                if (callx.isSuccessful()){
                    return callx.body().getData();
                }
            } catch (IOException e) {
                writeLog(e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(final DataGetSinglePost s) {
            super.onPostExecute(s);
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @SuppressLint("CheckResult")
                @Override
                public void run() {
                    try {
                        listRelated = new ArrayList<>();
                        Single single = s.getSingle();
                        listRelated = s.getRelated();

                        title_news = single.getTitle();
                        image_news = single.getFeaturedImg();
                        date_news = dateFormatter.format(single.getDatePublished());
                        final int[] w = { 0 };
                        final int[] h = { 0 };
                        Glide.with(getApplicationContext())
                                .asBitmap()
                                .load(single.getFeaturedImg())
                                .into(new SimpleTarget<Bitmap>() {
                                    @Override
                                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                        w[0] = resource.getWidth();
                                        h[0] = resource.getHeight();
                                    }
                                });
                        final RequestOptions options = new RequestOptions();
                        w[0] = w[0]==0?1:w[0];
                        int rasio = (Integer.valueOf(getWidthHeight().get("width")) / w[0]);
                        Log.d(TAG, "run: "+rasio);
                        Log.d(TAG, "run: "+w[0]);
                        Log.d(TAG, "run: "+getWidthHeight().get("width"));
                        w[0] = rasio * w[0];
                        h[0] = rasio * h[0];
                        options.override(w[0], h[0]);
                        Glide.with(getApplicationContext())
                                .load(single.getFeaturedImg())
                                .apply(options)
                                .into(img_banner);
                        tvTitle.setText(single.getTitle());
                        titleToolbar.setText(single.getTitle());

                        tvDate.setText(single.getKategori() +"-"+ dateFormatter.format(single.getCreatedAt()));
                        tvContent.setWebViewClient(new WebViewClient(){
                            @Override
                            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                                return false;
                            }
                        });
                        tvContent.setOnLongClickListener(new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View view) {
                                return true;
                            }
                        });
                        tvContent.setLongClickable(false);
                        tvContent.setHapticFeedbackEnabled(false);
                        WebSettings cs = tvContent.getSettings();
                        cs.setJavaScriptEnabled(true);
                        cs.setUseWideViewPort(false);
                        cs.setLoadWithOverviewMode(true);
                        String contents;
                        contents = "<style>" +
                                "img{display: inline!important; height: auto!important; max-width: 100%!important; margin: 2px!important}" +
                                "p{margin-top:3px; margin-bottom:3px}" +
                                "iframe{top:0;left:0;width:100%;}" +
                                "</style>";
                        contents += single.getPost()
                                .replace("<img", "<img class='img-responsive'")
                                .replace("//cdn", "https://cdn")
                                .replace("//www", "https://www");

                        tvContent.loadData(contents, "text/html", "UTF-8");

                        skeletonScreen.hide();

                        Call<ResponseGetComment> callComment = mnewsServices.getComment(slug_id, "1");
                        new GetCountComment().execute(callComment);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    private class GetCountComment extends AsyncTask<Call, Void, DataGetComment>{

        @Override
        protected DataGetComment doInBackground(Call[] calls) {
            Call<ResponseGetComment> call = calls[0];
            try {
                final Response<ResponseGetComment> callx = call.execute();
                if (callx.isSuccessful()){
                    return callx.body().getData();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(DataGetComment dataGetComment) {
            super.onPostExecute(dataGetComment);

            String countComment = String.valueOf(dataGetComment.getTotal());

            count_comment = countComment;

            btnComment.setText("KOMENTAR ( "+countComment+" )");

            initAdapterRelated();
        }
    }

    @OnClick(R.id.detail_btnComment)
    public void showComment(){
        Bundle bundle = new Bundle();
        bundle.putString(ListComment.KEY_SLUGID, slug_id);
        bundle.putString(ListComment.KEY_COUNT_COMMENT, count_comment);
        bundle.putString(ListComment.KEY_TITLE_NEWS, title_news);
        bundle.putString(ListComment.KEY_IMAGE_NEWS, image_news);
        bundle.putString(ListComment.KEY_DATE_NEWS, date_news);

        Intent i = new Intent(DetailNews.this, ListComment.class);
        i.putExtras(bundle);
        startActivity(i);
    }

    private void initAdapterRelated(){
        if (listRelated != null){
            adapterRelated = new AdapterRelated(this, listRelated, this);

            LinearLayoutManager llm = new LinearLayoutManager(this);
            rvRelated.setLayoutManager(llm);

            rvRelated.setAdapter(adapterRelated);
        }
    }

    public static class MyHandler extends android.os.Handler {
        private final WeakReference<DetailNews> activityWeakReference;

        MyHandler(DetailNews activity) {
            this.activityWeakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (activityWeakReference.get() != null) {
                activityWeakReference.get().skeletonScreen.hide();
            }
        }
    }

    @OnClick(R.id.back)
    public void doBack(){
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
