package com.mdirect.mnews.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.mdirect.mnews.BaseApps;
import com.mdirect.mnews.R;
import com.mdirect.mnews.adapter.AdapterItemNews;
import com.mdirect.mnews.utils.ClickListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import illiyin.mhandharbeni.databasemodule.generator.ServiceGenerator;
import illiyin.mhandharbeni.databasemodule.model.mnews.AdapterRequest;
import illiyin.mhandharbeni.databasemodule.model.mnews.response.ResponseGetAllPost;
import illiyin.mhandharbeni.databasemodule.model.mnews.response.ResponseGetPostKategori;
import illiyin.mhandharbeni.databasemodule.model.mnews.response.data.get_all_post.DataGetAllPost;
import illiyin.mhandharbeni.databasemodule.model.mnews.response.data.get_menus.DataMenus;
import illiyin.mhandharbeni.databasemodule.services.MnewsServices;
import illiyin.mhandharbeni.realmlibrary.Crud;
import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class ListNewsKategori extends BaseApps implements ClickListener {
    @BindView(R.id.rvListNews)
    RecyclerView rvListNews;

    private String slug_kategori = "nothing";
    public static String KEY_SLUG = "key_slug";

    private Crud crud;
    private DataMenus dataMenus;

    private Crud crudNews;
    private DataGetAllPost dataGetAllPost;

    private AdapterItemNews adapterItemNews;
    private ArrayList<DataGetAllPost> listNews;
    private LinearLayoutManager llm;

    private MnewsServices mnewsServices;
    private AdapterRequest adapterRequest;

    private int PAGE_SIZE = 5;

    private boolean isLastPage = false;
    private int currentPage = 1;
    private int selectedSortByKey = 0;
    private int selectedSortOrderKey = 1;
    private boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_list_news);

        initBundle();
        initModule();

    }

    private void initBundle(){
        Bundle bundle = getIntent().getExtras();
        if (bundle!=null)
            slug_kategori = bundle.getString(KEY_SLUG);
    }

    @Override
    protected void onStart() {
        super.onStart();
        initDataLocal();
    }

    private String getIds(){
        return slug_kategori;
    }

    private void initDataLocal(){
        if (!slug_kategori.equalsIgnoreCase("nothing")){
            /*do fill data*/
            RealmResults results = null;
            if (getIds() != null){
                results = crudNews.read("kategoriName", getIds());
            }
            listNews = new ArrayList<>();
            if (results != null && results.size() > 0){
                for (int i=0;i<results.size();i++){
                    DataGetAllPost dGetAllPost = (DataGetAllPost) results.get(i);
                    listNews.add(dGetAllPost);
                }
            }

            adapterItemNews = new AdapterItemNews(this, listNews, this);
            llm = new LinearLayoutManager(this);
            rvListNews.setLayoutManager(llm);
            rvListNews.setItemAnimator(new DefaultItemAnimator());
            rvListNews.setAdapter(adapterItemNews);
            rvListNews.addOnScrollListener(recyclerViewOnScrollListener);
        }
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
                        && totalItemCount >= PAGE_SIZE) {
                    isLoading = true;
                    Log.d(TAG, "onScrolled: Bottom Has Reach");
                    syncNow(String.valueOf(currentPage+(totalItemCount/PAGE_SIZE)));
                }
            }
        }
    };

    private void syncNow(final String page){
        Log.d(TAG, "syncNow: Page :"+page);
        runOnUiThread(new Runnable() {
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
                    Crud crudNewx = new Crud(getApplicationContext(), post);
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
                final Crud crudNewx = new Crud(getApplicationContext(), post);


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
    private void initModule(){
        adapterRequest = new AdapterRequest(getApplicationContext());
        dataMenus = new DataMenus();
        crud = new Crud(getApplicationContext(), dataMenus);

        dataGetAllPost = new DataGetAllPost();
        crudNews = new Crud(getApplicationContext(), dataGetAllPost);

        mnewsServices = ServiceGenerator.createService(MnewsServices.class);
    }

    @Override
    public void clicked(int id) {

    }

    @Override
    public void clicked(String id) {

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
