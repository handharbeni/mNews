package com.mdirect.mnews.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mdirect.mnews.BaseFragments;
import com.mdirect.mnews.R;
import com.mdirect.mnews.activity.DetailNews;
import com.mdirect.mnews.adapter.AdapterItemNews;
import com.mdirect.mnews.utils.ClickListener;

import java.util.ArrayList;
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

    private int PAGE_SIZE = 3;

    private boolean isLastPage = false;
    private int currentPage = 1;
    private int selectedSortByKey = 0;
    private int selectedSortOrderKey = 1;
    private boolean isLoading = false;

    private MnewsServices mnewsServices;

    Featured cFeatured;

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
        Log.d(TAG, "onCreateView: "+getIds());
        return v;
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
            featured.setLayoutResource(R.layout.layout_item_featured);
            cFeatured = new Featured(getActivity().getApplicationContext(), featured.inflate());
        }
    }

    private void initData(){
//        Drawable drawable = logo.getDrawable();
//        if (drawable instanceof Animatable) {
//            ((Animatable) drawable).start();
//        }
        RealmResults results = null;
        results = crudNews.read();
        showLog(TAG, "JUMLAH DATA "+results.size());
        if (getIds() != null){
            if (getIds().equalsIgnoreCase("Semua Kanal")){
//                featured.setLayoutResource(R.layout.layout_item_featured);
//                featured.inflate();

                getFeaturedPost();
                featured.setVisibility(View.VISIBLE);
                results = crudNews.read();
                showLog(TAG, String.valueOf("Jumlah Data Kategori "+getIds()+" ADALAH "+results.size()));
            }else{
                featured.setVisibility(View.GONE);
                results = crudNews.read("kategoriName", getIds());
                showLog(TAG, String.valueOf("Jumlah Data Kategori "+getIds()+" ADALAH "+results.size()));
            }
        }
        listNews = new ArrayList<>();
        if (results.size() > 0){
            for (int i=0;i<results.size();i++){
                DataGetAllPost dGetAllPost = (DataGetAllPost) results.get(i);
                listNews.add(dGetAllPost);
            }
        }

        adapterItemNews = new AdapterItemNews(getActivity().getApplicationContext(), listNews, this);
        llm = new LinearLayoutManager(getActivity());
        rvItemNews.setLayoutManager(llm);
        rvItemNews.setItemAnimator(new DefaultItemAnimator());
        rvItemNews.setAdapter(adapterItemNews);
        rvItemNews.addOnScrollListener(recyclerViewOnScrollListener);
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

    private Callback<ResponseGetAllPost> findNewsCallback = new Callback<ResponseGetAllPost>() {
        @Override
        public void onResponse(Call<ResponseGetAllPost> call, Response<ResponseGetAllPost> response) {
            isLoading = false;

            if (!response.isSuccessful()) {
                int responseCode = response.code();
                if(responseCode == 504) { // 504 Unsatisfiable Request (only-if-cached)
                }
                return;
            }
            ResponseGetAllPost responseGetAllPost = response.body();
            if (response != null){
                assert responseGetAllPost != null;
                List<DataGetAllPost> newData = responseGetAllPost.getData();
                if (newData.size() > 0){
                    for (final DataGetAllPost dGetAllPost:newData){
                        RealmResults results = crudNews.read("slugId",dGetAllPost.getSlugId());
                        if (results.size() > 0){
                            /*update*/
                            updateRealm(dGetAllPost);
                        }else{
                            /*create*/
                            if (getIds().equalsIgnoreCase(dGetAllPost.getKategoriName())){
                                adapterItemNews.updateData(dGetAllPost);
                            }
                            createRealm(dGetAllPost);
                        }
                    }

                }
            }
        }

        @Override
        public void onFailure(Call<ResponseGetAllPost> call, Throwable t) {
            if (!call.isCanceled()){
                isLoading = false;
            }
        }
    };

    public void createRealm(final DataGetAllPost dataGetAllPost){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    DataGetAllPost post = new DataGetAllPost();
                    Crud crudNewx = new Crud(getContext(), post);
                    crudNewx.create(dataGetAllPost);
                    crudNewx.closeRealm();
                }catch (IllegalStateException e){

                }
            }
        }).start();
    }

    public void updateRealm(final DataGetAllPost dataGetAllPost){
        new Thread(new Runnable() {
            @Override
            public void run() {
                DataGetAllPost post = new DataGetAllPost();
                final Crud crudNewx = new Crud(getContext(), post);


                RealmResults results = crudNewx.read("slugId",dataGetAllPost.getSlugId());
                if (results.size() > 0){
                    final DataGetAllPost oldPost = (DataGetAllPost) results.get(0);
                    assert oldPost != null;
                    if (oldPost.getUpdatedAt() != null){
                        if (!oldPost.getUpdatedAt().equalsIgnoreCase(dataGetAllPost.getUpdatedAt())){
                            /*update*/
                            try {

                                crudNewx.getRealm().executeTransaction(new Realm.Transaction() {
                                    @Override
                                    public void execute(Realm realm) {
                                        if (!crudNewx.getRealm().isInTransaction()){
                                            crudNewx.openObject();
                                        }
                                        crudNewx.getRealmObject("slugId", oldPost.getSlugId());
                                        crudNewx.update(dataGetAllPost);
                                        crudNewx.commitObject();

                                    }
                                });
                            }catch (IllegalStateException e){

                            }
                        }
                    }else{
                        try {
                            crudNewx.getRealm().executeTransaction(new Realm.Transaction() {
                                @Override
                                public void execute(Realm realm) {
                                    if (!crudNewx.getRealm().isInTransaction()){
                                        crudNewx.openObject();
                                    }
                                    crudNewx.getRealmObject("slugId", oldPost.getSlugId());
                                    crudNewx.update(dataGetAllPost);
                                    crudNewx.commitObject();
                                }
                            });
                        }catch (IllegalStateException e){
                        }
                    }
                }

                crudNewx.closeRealm();
            }
        }).start();
    }

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
        Boolean getFeatured  = adapterRequest.syncFeatured(true);
        if (getFeatured){
            cFeatured.loadFirst();
        }
    }

    private void loadFeaturedData(){
        RealmResults resultFeature = crudFeatured.read();
        if (resultFeature.size() > 0){
            DataFeaturedPost dataFeatured = (DataFeaturedPost) resultFeature.get(0);

        }
    }

    class Featured{
        private Crud crudFeatured;
        private DataFeaturedPost dataFeaturedPost;

        @Nullable
        @BindView(R.id.imageCover)
        ImageView imageCover;

        @Nullable
        @BindView(R.id.txtTitleFirst)
        TextView txtTitleFirst;

        @Nullable
        @BindView(R.id.txtKeteranganFirst)
        TextView txtKetaranganFirst;

        private Context ctx;

        public Featured(Context ctx, View view) {
            this.ctx = ctx;
            this.dataFeaturedPost = new DataFeaturedPost();
            this.crudFeatured = new Crud(this.ctx, this.dataFeaturedPost);

            ButterKnife.bind(this.ctx, view);
        }

        public void loadFirst(){
            showLog(TAG, "LoadFirst");
            RealmResults results = this.crudFeatured.read();
            showLog(TAG, String.valueOf(results.size()));
            if (results.size() > 0){
                DataFeaturedPost dataFeaturedPost = (DataFeaturedPost) results.get(0);
                txtTitleFirst.setText(dataFeaturedPost.getTitle());
                txtKetaranganFirst.setText(dataFeaturedPost.getFeaturedPost());
                Glide.with(this.ctx).load(dataFeaturedPost.getFeaturedImg()).into(imageCover);
            }
        }

        public void loadSecond(){

        }
    }

}
