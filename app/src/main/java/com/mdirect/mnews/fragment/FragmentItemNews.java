package com.mdirect.mnews.fragment;

import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;

import com.jaredrummler.android.widget.AnimatedSvgView;
import com.mdirect.mnews.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import illiyin.mhandharbeni.databasemodule.model.mnews.response.data.get_menus.DataMenus;
import illiyin.mhandharbeni.realmlibrary.Crud;
import io.realm.RealmResults;

/**
 * Created by Beni on 23/03/2018.
 */

public class FragmentItemNews extends Fragment {
    View v;

    private String ids;
    private Crud crud;
    private DataMenus dataMenus;

    @BindView(R.id.featured)
    ViewStub featured;

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
        dataMenus = new DataMenus();
        crud = new Crud(getActivity().getApplicationContext(), dataMenus);
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
            RealmResults results = crud.read("name", getIds());
        }
    }

    public String getIds(){
        return this.ids;
    }
    public void setIds(String ids) {
        this.ids = ids;
    }
}
