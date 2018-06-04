package com.mdirect.mnews.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mdirect.mnews.BaseFragments;
import com.mdirect.mnews.R;
import com.mdirect.mnews.activity.DetailNews;
import com.mdirect.mnews.adapter.AdapterItemNews;
import com.mdirect.mnews.utils.ClickListener;
import com.mdirect.mnews.utils.DateFormatter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import illiyin.mhandharbeni.databasemodule.generator.ServiceGenerator;
import illiyin.mhandharbeni.databasemodule.model.mnews.AdapterRequest;
import illiyin.mhandharbeni.databasemodule.model.mnews.response.ResponseGetAllPost;
import illiyin.mhandharbeni.databasemodule.model.mnews.response.data.get_all_post.DataGetAllPost;
import illiyin.mhandharbeni.databasemodule.model.mnews.response.data.get_featured_post.DataFeaturedPost;
import illiyin.mhandharbeni.databasemodule.model.mnews.response.data.get_menus.DataMenus;
import illiyin.mhandharbeni.databasemodule.services.MnewsServices;
import illiyin.mhandharbeni.realmlibrary.Crud;
import illiyin.mhandharbeni.realmlibrary.RealmListener;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by Beni on 23/03/2018.
 */

public class FragmentItemNews extends BaseFragments implements RealmListener, ClickListener {
    View v;

    private static String KEY_ID = "ID";

    private String ids;
    private Crud crud;
    private DataMenus dataMenus;

    private Crud crudNews;
    private DataGetAllPost dataGetAllPost;

    private Crud crudFeatured;
    private DataFeaturedPost dataFeaturedPost;

    private AdapterRequest adapterRequest;

    private AdapterItemNews adapterItemNews;
    private ArrayList<DataGetAllPost> listNews;
    private LinearLayoutManager llm;

    @BindView(R.id.featured)
    ViewStub featured;

    @BindView(R.id.rvItemNews)
    RecyclerView rvItemNews;

    @BindView(R.id.loader)
    ImageView loader;

    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;

    private int PAGE_SIZE = 3;

    private boolean isLastPage = false;
    private int currentPage = 1;
    private int selectedSortByKey = 0;
    private int selectedSortOrderKey = 1;
    private boolean isLoading = false;

    private MnewsServices mnewsServices;

    Featured cFeatured;

    private String width, height;

    public static FragmentItemNews newInstance(String ids) {

        Bundle args = new Bundle();
        args.putString(KEY_ID, ids);
        FragmentItemNews fragment = new FragmentItemNews();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        HashMap<String, String> widhtHeight = getWidthHeight();
        width = widhtHeight.get("width");
        height = widhtHeight.get("height");
        if (bundle != null){
            setIds(bundle.getString(KEY_ID));
            Log.d(TAG, "onCreate: "+getIds());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.layout_main_fragment, container, false);


        ButterKnife.bind(this, v);
        initModule();
        initData();
//        startLoader();

        syncNow(String.valueOf(1), true);

        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                syncNow(String.valueOf(1), true);
            }
        });


        listenerRealmUpdate();
        Log.d(TAG, "onCreateView: "+getIds());
        return v;
    }

    private void listenerRealmUpdate(){
        crudNews.getRealm().addChangeListener(new RealmChangeListener<Realm>() {
            @Override
            public void onChange(Realm realm) {
                updateData();
            }
        });
    }

    private void initModule(){
        adapterRequest = new AdapterRequest(getActivity().getApplicationContext());
        dataMenus = new DataMenus();
        crud = new Crud(getActivity().getApplicationContext(), dataMenus);

        dataGetAllPost = new DataGetAllPost();
        crudNews = new Crud(getActivity().getApplicationContext(), dataGetAllPost);

        dataFeaturedPost = new DataFeaturedPost();
        crudFeatured = new Crud(getActivity().getApplicationContext(), dataFeaturedPost);

        mnewsServices = ServiceGenerator.createService(MnewsServices.class);

        if (featured.getParent() != null){
            Log.d(TAG, "initModule: Featured Have Parent");
            featured.setLayoutResource(R.layout.layout_item_featured);
            cFeatured = new Featured(getActivity().getApplicationContext(), featured.inflate(), this);
        }
    }
    private void updateData(){
        try {
            int i = 0;
            RealmResults results = null;
            results = crudNews.read();
            showLog(TAG, "JUMLAH DATA "+results.size());
            if (getIds() != null){
                if (getIds().equalsIgnoreCase("Semua Kanal")){
                    i = 2;
                    getFeaturedPost();
//                featured.setVisibility(View.VISIBLE);
                    results = crudNews.read();

                    if (results.size() < 1){
                        featured.setVisibility(View.GONE);
                    }else{
                        featured.setVisibility(View.VISIBLE);
                    }
                    showLog(TAG, String.valueOf("Jumlah Data Kategori "+getIds()+" ADALAH "+results.size()));
                }else{
                    i = 0;
                    featured.setVisibility(View.GONE);
                    results = crudNews.read("kategoriName", getIds());
                    showLog(TAG, String.valueOf("Jumlah Data Kategori "+getIds()+" ADALAH "+results.size()));
                }
            }
            listNews = new ArrayList<>();
            if (results.size() > 0){
                try {
                    do {
                        DataGetAllPost dGetAllPost = (DataGetAllPost) results.get(i);
                        listNews.add(dGetAllPost);
                        i++;
                    }while (i<results.size());
                }catch (ArrayIndexOutOfBoundsException e){
                    e.printStackTrace();
                }
            }
            adapterItemNews.updateData(listNews);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void initData(){
        try {
            int i = 0;
            RealmResults results = null;
            results = crudNews.read();
            showLog(TAG, "JUMLAH DATA "+results.size());
            if (getIds() != null){
                if (getIds().equalsIgnoreCase("Semua Kanal")){
                    i = 2;
                    getFeaturedPost();
                    results = crudNews.read();
                    if (results.size() < 1){
                        featured.setVisibility(View.GONE);
                    }else{
                        featured.setVisibility(View.VISIBLE);
                    }
                    showLog(TAG, String.valueOf("Jumlah Data Kategori "+getIds()+" ADALAH "+results.size()));
                }else{
                    i = 0;
                    featured.setVisibility(View.GONE);
                    results = crudNews.read("kategoriName", getIds());
                    showLog(TAG, String.valueOf("Jumlah Data Kategori "+getIds()+" ADALAH "+results.size()));
                }
            }
            listNews = new ArrayList<>();
            if (results.size() > 0){
                try {
                    do {
                        DataGetAllPost dGetAllPost = (DataGetAllPost) results.get(i);
                        listNews.add(dGetAllPost);
                        i++;
                    }while (i<results.size());
                }catch (ArrayIndexOutOfBoundsException e){
                    e.printStackTrace();
                }
            }

            adapterItemNews = new AdapterItemNews(getActivity().getApplicationContext(), listNews, this);
            llm = new LinearLayoutManager(getActivity());
            rvItemNews.setLayoutManager(llm);
            rvItemNews.setItemAnimator(new DefaultItemAnimator());
            rvItemNews.setAdapter(adapterItemNews);
            rvItemNews.addOnScrollListener(recyclerViewOnScrollListener);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public String getIds(){
        return this.ids;
    }
    public void setIds(String ids) {
        this.ids = ids;
    }

    private RecyclerView.OnScrollListener recyclerViewOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            int visibleItemCount = llm.getChildCount();
            int totalItemCount = llm.getItemCount();
            int firstVisibleItemPosition = llm.findFirstVisibleItemPosition();

            if (!isLoading && !isLastPage) {
                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0
                        /*&& totalItemCount >= PAGE_SIZE*/) {
                    isLoading = true;
                    loader.setVisibility(View.VISIBLE);
                    Log.d(TAG, "onScrolled: Bottom Has Reach");
                    syncNow(String.valueOf(currentPage+(totalItemCount/PAGE_SIZE)));
                }
            }
        }
    };
    private void syncNow(final String page){
        Log.d(TAG, "syncNow: Page :"+page);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mnewsServices.getAllPost(page).enqueue(findNewsCallback);
            }
        });
    }
    private void syncNow(final String page, final Boolean swipe){
        Log.d(TAG, "syncNow: Page :"+page);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mnewsServices.getAllPost(page).enqueue(findNewsCallback);
                swipeLayout.setRefreshing(false);
                loader.setVisibility(View.GONE);
            }
        });
    }
    private Callback<ResponseGetAllPost> findNewsCallback = new Callback<ResponseGetAllPost>() {
        @Override
        public void onResponse(Call<ResponseGetAllPost> call, Response<ResponseGetAllPost> response) {
            isLoading = false;

            if (!response.isSuccessful()) {
                int responseCode = response.code();
                if(responseCode == 504) { // 504 Unsatisfiable Request (only-if-cached)
                }
                loader.setVisibility(View.GONE);
                return;
            }
            ResponseGetAllPost responseGetAllPost = response.body();
            if (response != null){
                assert responseGetAllPost != null;
                final List<DataGetAllPost> newData = responseGetAllPost.getData();
                if (newData.size() > 0){
                    try {
                        crudNews.getRealm().executeTransactionAsync(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                realm.copyToRealmOrUpdate(newData);
                            }
                        }, new Realm.Transaction.OnSuccess() {
                            @Override
                            public void onSuccess() {
                                /*update adapter*/
                                updateData();
                                loader.setVisibility(View.GONE);
                            }
                        });
                    }catch (Exception e){
                        e.printStackTrace();
                    }
//                    loader.setVisibility(View.GONE);
                }
            }
        }

        @Override
        public void onFailure(Call<ResponseGetAllPost> call, Throwable t) {
            if (!call.isCanceled()){
                isLoading = false;
                loader.setVisibility(View.GONE);
            }
        }
    };


    @Override
    public void onUpdate() {
    }

    @Override
    public void onDestroy() {
        if (crudNews != null && crud != null){
            crudNews.closeRealm();
            crud.closeRealm();
        }
        super.onDestroy();
    }

    @Override
    public void clicked(int id) {

    }

    @Override
    public void clicked(String id) {
        Toast.makeText(getActivity().getApplicationContext(), id, Toast.LENGTH_SHORT).show();
        Bundle bundle = new Bundle();
        bundle.putString(DetailNews.KEY_SLUGID, id);

        Intent i = new Intent(getActivity().getApplicationContext(), DetailNews.class);
        i.putExtras(bundle);
        getActivity().startActivity(i);
    }

    private void getFeaturedPost(){
//        Boolean getFeatured  = adapterRequest.syncFeatured(true);
//        if (getFeatured){
            cFeatured.loadFirst();
            cFeatured.loadSecond();
            cFeatured.loadThird();
//        }
    }

    class Featured{
        private Crud crudFeatured;
        private DataFeaturedPost dataFeaturedPost;

        private Crud crudNews;
        private DataGetAllPost dataGetAllPost;


        /*first*/
        @Nullable
        @BindView(R.id.imageCover)
        ImageView imageCover;

        @Nullable
        @BindView(R.id.txtTitleFirst)
        TextView txtTitleFirst;

        @Nullable
        @BindView(R.id.txtKeteranganFirst)
        TextView txtKetaranganFirst;



        /*seconds*/
        @Nullable
        @BindView(R.id.imageSecond)
        ImageView imageSecond;

        @Nullable
        @BindView(R.id.txtTitleSeconds)
        TextView txtTitleSeconds;

        @Nullable
        @BindView(R.id.txtTglSeconds)
        TextView txtTglSeconds;

        @Nullable
        @BindView(R.id.mainLayoutSecond)
        CardView mainLayoutSecond;



        /*third*/
        @Nullable
        @BindView(R.id.imageThird)
        ImageView imageThird;

        @Nullable
        @BindView(R.id.txtTitleThirds)
        TextView txtTitleThirds;

        @Nullable
        @BindView(R.id.txtTglThirds)
        TextView txtTglThirds;

        @Nullable
        @BindView(R.id.mainLayoutThird)
        CardView mainLayoutThird;

        private Context ctx;
        private DateFormatter dateFormatter;
        private ClickListener clickListener;

        Featured(Context ctx, View view, ClickListener clickListener) {
            this.ctx = ctx;

            this.dataFeaturedPost = new DataFeaturedPost();
            this.crudFeatured = new Crud(this.ctx, this.dataFeaturedPost);

            this.dataGetAllPost = new DataGetAllPost();
            this.crudNews = new Crud(this.ctx, this.dataGetAllPost);

            this.dateFormatter = new DateFormatter();

            this.clickListener = clickListener;

            ButterKnife.bind(this, view);
        }

        void loadFirst(){
//            showLog(TAG, "LoadFirst");
//            RealmResults results = this.crudFeatured.read();
//            showLog(TAG, String.valueOf(results.size()));
//            if (results.size() > 0){
//                DataFeaturedPost dataFeaturedPost = (DataFeaturedPost) results.get(0);
//                assert dataFeaturedPost != null;
//                txtTitleFirst.setText(dataFeaturedPost.getTitle());
//                txtKetaranganFirst.setText(dataFeaturedPost.getFeaturedPost());
//                Glide.with(this.ctx).load(dataFeaturedPost.getFeaturedImg()).into(imageCover);
//            }
            showLog(TAG, "LoadSeconds");
            RealmResults results = this.crudNews.read();
            showLog(TAG, "Result Seconds Featured "+results.size());
            if (results.size() > 0){
                final DataGetAllPost dataGetAllPost = (DataGetAllPost) results.get(0);
                assert dataGetAllPost != null;
                txtTitleFirst.setText(dataGetAllPost.getTitle());
                txtKetaranganFirst.setText(dateFormatter.format(dataGetAllPost.getCreatedAt()));
                Glide.with(this.ctx).load(dataGetAllPost.getFeaturedImg()).into(imageCover);
                imageCover.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickListener.clicked(dataGetAllPost.getSlugId());
                    }
                });
            }
        }

        void loadSecond(){
            showLog(TAG, "LoadSeconds");
            RealmResults results = this.crudNews.read();
            showLog(TAG, "Result Seconds Featured "+results.size());
            if (results.size() > 0){
                final DataGetAllPost dataGetAllPost = (DataGetAllPost) results.get(0);
                assert dataGetAllPost != null;
                txtTitleSeconds.setText(dataGetAllPost.getTitle());
                txtTglSeconds.setText(dateFormatter.format(dataGetAllPost.getCreatedAt()));
                RequestOptions options = new RequestOptions();
                options.override((Integer.valueOf(width)-10)/2);
                Glide.with(this.ctx).load(dataGetAllPost.getFeaturedImg()).apply(options).into(imageSecond);
                assert mainLayoutSecond != null;
                imageSecond.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickListener.clicked(dataGetAllPost.getSlugId());
                    }
                });
            }
        }

        void loadThird(){
            showLog(TAG, "LoadThird");
            RealmResults results = this.crudNews.read();
            showLog(TAG, "Result Third Featured "+results.size());
            if (results.size() > 0){
                final DataGetAllPost dataGetAllPost = (DataGetAllPost) results.get(1);
                assert dataGetAllPost != null;
                txtTitleThirds.setText(dataGetAllPost.getTitle());
                txtTglThirds.setText(dateFormatter.format(dataGetAllPost.getCreatedAt()));
                RequestOptions options = new RequestOptions();
                options.override((Integer.valueOf(width)-10)/2);
                Glide.with(this.ctx).load(dataGetAllPost.getFeaturedImg()).apply(options).into(imageThird);
                assert mainLayoutThird != null;
                imageThird.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickListener.clicked(dataGetAllPost.getSlugId());
                    }
                });
            }
        }
    }

}
