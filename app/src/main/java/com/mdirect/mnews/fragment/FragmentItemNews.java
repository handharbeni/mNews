package com.mdirect.mnews.fragment;

import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
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

import com.jaredrummler.android.widget.AnimatedSvgView;
import com.mdirect.mnews.R;
import com.mdirect.mnews.adapter.AdapterItemNews;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import illiyin.mhandharbeni.databasemodule.generator.ServiceGenerator;
import illiyin.mhandharbeni.databasemodule.model.mnews.AdapterRequest;
import illiyin.mhandharbeni.databasemodule.model.mnews.response.data.get_all_post.DataGetAllPost;
import illiyin.mhandharbeni.databasemodule.model.mnews.response.data.get_menus.DataMenus;
import illiyin.mhandharbeni.databasemodule.services.MnewsServices;
import illiyin.mhandharbeni.realmlibrary.Crud;
import io.realm.RealmResults;

import static android.content.ContentValues.TAG;

/**
 * Created by Beni on 23/03/2018.
 */

public class FragmentItemNews extends Fragment {
    View v;

    private String ids;
    private Crud crud;
    private DataMenus dataMenus;

    private Crud crudNews;
    private DataGetAllPost dataGetAllPost;

    private AdapterRequest adapterRequest;

    private AdapterItemNews adapterItemNews;
    private ArrayList<DataGetAllPost> listNews;
    private LinearLayoutManager llm;

    @BindView(R.id.featured)
    ViewStub featured;

    @BindView(R.id.rvItemNews)
    RecyclerView rvItemNews;

    private int PAGE_SIZE = 5;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.layout_main_fragment, container, false);


        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        v = view;
        initModule();
        initData();
    }

    private void initModule(){
        adapterRequest = new AdapterRequest(getActivity().getApplicationContext());
        dataMenus = new DataMenus();
        crud = new Crud(getActivity().getApplicationContext(), dataMenus);

        dataGetAllPost = new DataGetAllPost();
        crudNews = new Crud(getActivity().getApplicationContext(), dataGetAllPost);
    }

    private void initData(){
//        Drawable drawable = logo.getDrawable();
//        if (drawable instanceof Animatable) {
//            ((Animatable) drawable).start();
//        }
        if (getIds().equalsIgnoreCase("Semua Kanal")){
            featured.setLayoutResource(R.layout.layout_item_featured);
            featured.inflate();
        }else{
            featured.setVisibility(View.GONE);
        }
        listNews = new ArrayList<>();
        RealmResults results = crudNews.read();
        if (results.size() > 0){
            for (int i=0;i<results.size();i++){
                DataGetAllPost dGetAllPost = (DataGetAllPost) results.get(i);
                listNews.add(dGetAllPost);
            }
        }

        adapterItemNews = new AdapterItemNews(getActivity().getApplicationContext(), listNews);
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

//            if (!isLoading && !isLastPage) {
                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0
                        && totalItemCount >= PAGE_SIZE) {
                    Log.d(TAG, "onScrolled: Bottom Has Reach");
                }
//            }
        }
    };
}
