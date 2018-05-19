package com.mdirect.mnews;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.h6ah4i.android.tablayouthelper.TabLayoutHelper;
import com.mdirect.mnews.activity.DetailNews;
import com.mdirect.mnews.activity.MenuActivity;
import com.mdirect.mnews.adapter.AdapterItemNews;
import com.mdirect.mnews.adapter.TabsPagerAdapter;
import com.mdirect.mnews.utils.ClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import illiyin.mhandharbeni.databasemodule.model.mnews.response.data.get_all_post.DataGetAllPost;
import illiyin.mhandharbeni.databasemodule.model.mnews.response.data.get_menus.DataMenus;
import illiyin.mhandharbeni.realmlibrary.Crud;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;


public class MainActivity extends BaseApps implements TabLayout.OnTabSelectedListener {
    private static final String TAG = "MainActivity";

    public static final String KEY_POSITION = "key_position";
    public Integer currentPosition = 0;


    @Nullable
    @BindView(R.id.mainStub)
    ViewStub mainStub;

    @Nullable
    @BindView(R.id.searchStub)
    ViewStub searchStub;

    @BindView(R.id.etSearch)
    TextView etSearch;

    @BindView(R.id.imgMenu)
    ImageView imgMenu;

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


    @OnClick(R.id.imgMenu)
    public void onMenuClick(){
        Intent i = new Intent(this, MenuActivity.class);
        startActivity(i);
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
                    tabMainSearch.updateSearch(s.toString());
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
    private void resumeActivity(){
        if (!etSearch.getText().toString().isEmpty()){
            hiddenTab();
            showLog("Result Search", "Is Not Empty");
            /*tab main search*/
            tabMainSearch.updateSearch(etSearch.getText().toString());
        }else{
            hiddenSearch();
        }
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

    class TabMainSearch implements ClickListener{
        private Crud crudNews;
        private DataGetAllPost dataGetAllPost;

        private AdapterItemNews adapterItemNews;
        private ArrayList<DataGetAllPost> listNews;
        private LinearLayoutManager llm;

        @Nullable
        @BindView(R.id.txtKeteranganSearch)
        TextView txtKeteranganSearch;

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
            initModuleSearch();
        }

        private void initModuleSearch(){
            dataGetAllPost = new DataGetAllPost();
            crudNews = new Crud(context, dataGetAllPost);

            listNews = new ArrayList<>();


            adapterItemNews = new AdapterItemNews(context, listNews, this);
            llm = new LinearLayoutManager(context);
            rvSearch.setLayoutManager(llm);
            rvSearch.setAdapter(adapterItemNews);
        }

        public void destroyBind(){
            if (unbinder != null){
                unbinder.unbind();
                unbinder = null;
            }
        }

        public void updateSearch(String message){
            RealmResults results = crudNews.contains("slugId", message);
            showLog("Result Search", results.size());
            if (results.size() > 0){
                listNews.clear();
                rvSearch.setVisibility(View.VISIBLE);
                ketNull.setVisibility(View.GONE);
                for (int i = 0; i < results.size(); i++) {
                    DataGetAllPost newData = (DataGetAllPost) results.get(i);
                    listNews.add(newData);
                    showLog("Result Search : i ", i);
                }
                showLog("Result Search : count list", listNews.size());
                adapterItemNews.updateData(listNews);
                adapterItemNews.notifyDataSetChanged();
            }else{
                rvSearch.setVisibility(View.GONE);
                ketNull.setVisibility(View.VISIBLE);
            }
        }


        @Override
        public void clicked(int id) {

        }

        @Override
        public void clicked(String id) {
            Bundle bundle = new Bundle();
            bundle.putString(DetailNews.KEY_SLUGID, id);

            Intent i = new Intent(context, DetailNews.class);
            i.putExtras(bundle);
            context.startActivity(i);
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
            showLog(KEY_POSITION, getCustomPreferences(KEY_POSITION));
            try {
                currentPosition = !getCustomPreferences(KEY_POSITION).equalsIgnoreCase("nothing") ?
                        Integer.valueOf(getCustomPreferences(KEY_POSITION))-1:currentPosition;
            }catch (NumberFormatException e){
                currentPosition = 0;
            }
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

                TabLayoutHelper mTabLayoutHelper = new TabLayoutHelper(tabLayout, pager);
                mTabLayoutHelper.setAutoAdjustTabModeEnabled(false);
                tabLayout.setupWithViewPager(pager);
                tabLayout.setTabTextColors(getResources().getColor(R.color.article_normal), getResources().getColor(R.color.tagline_active));
                tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.tagline_active));
                setDividerTabLayout();

                pager.setCurrentItem(currentPosition);
                pager.setActivated(true);
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

                pager.setCurrentItem(currentPosition);
                pager.setActivated(true);
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

    @Override
    protected void onResume() {
        super.onResume();
        resumeActivity();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        resumeActivity();
    }
}
