package com.mdirect.mnews;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewStub;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.h6ah4i.android.tablayouthelper.TabLayoutHelper;
import com.mdirect.mnews.adapter.TabsPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import illiyin.mhandharbeni.databasemodule.model.mnews.response.data.get_menus.DataMenus;
import illiyin.mhandharbeni.realmlibrary.Crud;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;


public class MainActivity extends BaseApps implements TabLayout.OnTabSelectedListener {
    private static final String TAG = "MainActivity";

    @Nullable
    @BindView(R.id.mainStub)
    ViewStub mainStub;

    @Nullable
    @BindView(R.id.searchStub)
    ViewStub searchStub;

    @BindView(R.id.etSearch)
    TextView etSearch;

    private Crud crudMenus;

    View vTab;
    TabMainView tabMainView;

    View vSearch;
    TabMainSearch tabMainSearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initModule();

        setContentView(R.layout.layout_main);
        ButterKnife.bind(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        initViewSearch();
        initViewStub();
        initSearch();
        hiddenSearch();
    }


    private void initSearch(){
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()){
                    /*tab*/
                    hiddenSearch();
                }else{
                    /*search*/
                    hiddenTab();
                }
            }
        });
    }

    private void initModule(){
        DataMenus dataMenus = new DataMenus();
        crudMenus = new Crud(getApplicationContext(),dataMenus);
    }


    private void initChangeData(){}

    private void initViewStub(){
        if (mainStub.getParent() != null){
            mainStub.setLayoutResource(R.layout.layout_tab_main);
            vTab = mainStub.inflate();
        }
        tabMainView = new TabMainView(this, vTab);
        tabMainView.initParameter();
        tabMainView.initData();
        tabMainView.setViewPager();
    }
    private void initViewSearch(){
        if (searchStub.getParent() != null){
            searchStub.setLayoutResource(R.layout.layout_tab_main_search);
            vSearch = searchStub.inflate();
            tabMainSearch = new TabMainSearch(this, vSearch);
        }
    }
    private void hiddenSearch(){
        mainStub.setVisibility(View.VISIBLE);
        searchStub.setVisibility(View.GONE);
    }
    private void hiddenTab(){
        searchStub.setVisibility(View.VISIBLE);
        mainStub.setVisibility(View.GONE);
    }
    @Override
    public void onTabSelected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    class TabMainSearch{
        @Nullable
        @BindView(R.id.rvSearch)
        RecyclerView rvSearch;

        @Nullable
        @BindView(R.id.ketNull)
        RelativeLayout ketNull;

        Context context;

        private Unbinder unbinder = null;

        public TabMainSearch(Context context, View view){
            this.context = context;
            unbinder = ButterKnife.bind(this, view);
        }

        public void destroyBind(){
            if (unbinder != null){
                unbinder.unbind();
                unbinder = null;
            }
        }

    }

    class TabMainView{
        private List<String> listItem;
        private TabsPagerAdapter tabsPagerAdapter;

        @Nullable
        @BindView(R.id.tabLayout)
        TabLayout tabLayout;

        @Nullable
        @BindView(R.id.pager)
        ViewPager pager;

        Context context;
        private Unbinder unbinder  = null;

        public TabMainView(Context context, View view){
            this.context = context;
            unbinder = ButterKnife.bind(this, view);
        }

        public void initParameter(){
            listItem = new ArrayList<>();
        }

        public void initData(){
            listItem.clear();
            listItem.add("Semua Kanal");
            RealmResults results = crudMenus.read();
            for (int i=0;i<results.size();i++){
                DataMenus dataMenus = (DataMenus) results.get(i);
                listItem.add(dataMenus.getName());
            }
            results.addChangeListener(new RealmChangeListener<RealmResults>() {
                @Override
                public void onChange(RealmResults realmResults) {
                    listItem.clear();
                    listItem.add("Semua Kanal");
                    for (int i=0;i<realmResults.size();i++){
                        DataMenus dataMenus = (DataMenus) realmResults.get(i);
                        listItem.add(dataMenus.getName());
                    }
                    setViewPagerUpdate();
                    tabsPagerAdapter.notifyDataSetChanged();
                }
            });
        }
        public void setViewPager(){
            if (tabLayout != null && pager != null){
                tabsPagerAdapter = new TabsPagerAdapter(this.context, getSupportFragmentManager(), listItem);
                pager.setAdapter(tabsPagerAdapter);
                pager.setCurrentItem(0);

                TabLayoutHelper mTabLayoutHelper = new TabLayoutHelper(tabLayout, pager);
                mTabLayoutHelper.setAutoAdjustTabModeEnabled(false);
                tabLayout.setupWithViewPager(pager);
                tabLayout.setTabTextColors(getResources().getColor(R.color.article_normal), getResources().getColor(R.color.tagline_active));
                tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.tagline_active));
                setDividerTabLayout();
            }
        }
        public void setViewPagerUpdate(){
            if (tabLayout != null && pager != null){
                tabsPagerAdapter = new TabsPagerAdapter(this.context, getSupportFragmentManager(), listItem);
                pager.setAdapter(tabsPagerAdapter);

                TabLayoutHelper mTabLayoutHelper = new TabLayoutHelper(tabLayout, pager);
                mTabLayoutHelper.setAutoAdjustTabModeEnabled(false);
                tabLayout.setupWithViewPager(pager);
                tabLayout.setTabTextColors(getResources().getColor(R.color.article_normal), getResources().getColor(R.color.tagline_active));
                tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.tagline_active));
                setDividerTabLayout();
            }
        }

        private void setDividerTabLayout(){
            for (int i=0;i<tabLayout.getTabCount();i++){
                View root = tabLayout.getChildAt(i);
                if (root instanceof LinearLayout) {
                    ((LinearLayout) root).setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
                    ((LinearLayout) root).setDividerPadding(10);
                    ((LinearLayout) root).setDividerDrawable(getResources().getDrawable(R.drawable.layout_separator_tab));
                }
            }
        }
        public void destroyBind(){
            if (unbinder != null){
                unbinder.unbind();
                unbinder = null;
            }
        }
    }
}
