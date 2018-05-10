package com.mdirect.mnews.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mdirect.mnews.BaseApps;
import com.mdirect.mnews.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProfileActivity extends BaseApps {
    @BindView(R.id.updateProfile)
    ImageView updateProfile;

    @BindView(R.id.back)
    ImageView imageBack;

    @BindView(R.id.profileImage) ImageView profileImage;
    @BindView(R.id.profileName) TextView profileName;
    @BindView(R.id.profileUsername) TextView profileUsername;
    @BindView(R.id.profileDeskripsi) TextView profileDeskripsi;
    @BindView(R.id.profileEmail) TextView profileEmail;
    @BindView(R.id.profileWeb) TextView profileWeb;
    @BindView(R.id.profileSOCFb) ImageView profileSOCFB;
    @BindView(R.id.profileSOCTwitter) ImageView profileSOCTwitter;
    @BindView(R.id.profileSOCIg) ImageView profileSOCIG;
    @BindView(R.id.profileSOCGp) ImageView profileSOCGP;
    @BindView(R.id.profileSOCIn) ImageView profileSOCIN;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_profile);

        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        initData();
    }

    public void initData(){
        Glide.with(this).load(getPreferences(KEY_FOTOPROFILE)).into(profileImage);
        profileName.setText(getPreferences(KEY_NAME));
        profileUsername.setText("("+getPreferences(KEY_USERNAME)+")");
        profileDeskripsi.setText(getPreferences(KEY_DESCRIPTION));
        profileEmail.setText(getPreferences(KEY_EMAIL));
        profileWeb.setText(getPreferences(KEY_LINKWEB));
//        profileSOCFB.setText(getPreferences(KEY_MEDSOS_FB));
//        profileSOCTwitter.setText(getPreferences(KEY_MEDSOS_TWITTER));
//        profileSOCIG.setText(getPreferences(KEY_MEDSOS_IG));
//        profileSOCGP.setText(getPreferences(KEY_MEDSOS_GOOGLEPLUS));
//        profileSOCIN.setText(getPreferences(KEY_MEDSOS_LINKEDIN));
    }

    @OnClick(R.id.updateProfile)
    public void updateProfile(){
        Intent i  = new Intent(this, UpdateProfile.class);
        startActivity(i);
    }

    @OnClick(R.id.back)
    public void back(){
        finish();
    }

}
