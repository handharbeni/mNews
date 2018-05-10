package com.mdirect.mnews.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.bumptech.glide.Glide;
import com.mdirect.mnews.BaseApps;
import com.mdirect.mnews.R;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import illiyin.mhandharbeni.databasemodule.generator.ServiceGeneratorAccount;
import illiyin.mhandharbeni.databasemodule.model.account.request.RequestUpdateProfile;
import illiyin.mhandharbeni.databasemodule.model.account.response.data.user_profile.DataUserProfile;
import illiyin.mhandharbeni.databasemodule.model.mnews.response.data.general.StringResponse;
import illiyin.mhandharbeni.databasemodule.services.MdirectAccountServices;
import illiyin.mhandharbeni.databasemodule.utils.ProgressRequestBody;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateProfile extends BaseApps implements ProgressRequestBody.UploadCallbacks {
    @BindView(R.id.back) ImageView imageBack;
    @BindView(R.id.rootView) ScrollView rootView;

    @BindView(R.id.profileImage) ImageView profileImage;
    @BindView(R.id.profileName) EditText profileName;
    @BindView(R.id.profileUsername) EditText profileUsername;
    @BindView(R.id.profileEmail) EditText profileEmail;
    @BindView(R.id.profileWeb) EditText profileWeb;
    @BindView(R.id.profileSOCFb) EditText profileSOCFB;
    @BindView(R.id.profileSOCTwitter) EditText profileSOCTwitter;
    @BindView(R.id.profileSOCIg) EditText profileSOCIG;
    @BindView(R.id.profileSOCGp) EditText profileSOCGP;
    @BindView(R.id.profileSOCIn) EditText profileSOCIN;
    @BindView(R.id.btnSimpan) AppCompatButton btnSimpan;

    MdirectAccountServices mdirectAccountServices;


    private ProgressDialog progressDialog;
    private String imagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_profile_update);
        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        initModule();

        initData();
    }

    public void initModule(){
        mdirectAccountServices = ServiceGeneratorAccount.createService(MdirectAccountServices.class);
    }

    @OnClick(R.id.back)
    public void doBack(){
        finish();
    }

    @OnClick(R.id.btnSimpan)
    public void doSave(){
        if (isValid(profileName) &&
                isValid(profileUsername) &&
                isValid(profileName) &&
                isValid(profileEmail) &&
                isValid(profileWeb) &&
                isValid(profileSOCFB) &&
                isValid(profileSOCTwitter) &&
                isValid(profileSOCIG) &&
                isValid(profileSOCGP) &&
                isValid(profileSOCIN)){

            save();
        }
    }

    private void save(){

        String token = getAccessToken();

        Map<String, String> headers = new HashMap<>();
        headers.put("appid", "174863748390695");
        headers.put("appsecret", "2BdUbH680ERDaJD1LGjX6Td7jR5Z5O2TJzPgNDOjlo4IANW3W9CYEZQ2OuT01cRpyFqxLWGknKWB46h2d6p4qOBEGvLDISRNAxb0hVgrtpy3K5sPNtsMQDjzwAt2MTvP");
        headers.put("usertoken", token);

        RequestUpdateProfile dataUserProfile = new RequestUpdateProfile();
        dataUserProfile.setName(profileName.getText().toString());
        dataUserProfile.setEmail(profileEmail.getText().toString());
        dataUserProfile.setWebsite(profileWeb.getText().toString());
        dataUserProfile.setFacebook(profileSOCFB.getText().toString());
        dataUserProfile.setTwitter(profileSOCTwitter.getText().toString());
        dataUserProfile.setInstagram(profileSOCIG.getText().toString());
        dataUserProfile.setGplus(profileSOCGP.getText().toString());
        dataUserProfile.setLinkedin(profileSOCIN.getText().toString());

        Call<StringResponse> responseUpdate = mdirectAccountServices.updateMe(headers, dataUserProfile);
        new updateProfile().execute(responseUpdate);
    }

    private boolean isValid(final EditText editText){
        boolean returns = false;
        if (!editText.getText().toString().equalsIgnoreCase("")){
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    rootView.scrollTo(0, editText.getBottom());
                }
            });
            returns = true;
        }else{
            editText.setError("Tidak Boleh Kosong");
        }
        return returns;
    }

    public void initData(){
        Glide.with(this).load(getPreferences(KEY_FOTOPROFILE)).into(profileImage);
        profileName.setText(getPreferences(KEY_NAME));
        profileUsername.setText(getPreferences(KEY_USERNAME));
        profileEmail.setText(getPreferences(KEY_EMAIL));
        profileWeb.setText(getPreferences(KEY_LINKWEB));
        profileSOCFB.setText(getPreferences(KEY_MEDSOS_FB));
        profileSOCTwitter.setText(getPreferences(KEY_MEDSOS_TWITTER));
        profileSOCIG.setText(getPreferences(KEY_MEDSOS_IG));
        profileSOCGP.setText(getPreferences(KEY_MEDSOS_GOOGLEPLUS));
        profileSOCIN.setText(getPreferences(KEY_MEDSOS_LINKEDIN));
    }

    @Override
    public void onProgressUpdate(int percentage) {
        progressDialog.setProgress(percentage);
    }

    @Override
    public void onError() {

    }

    @Override
    public void onFinish() {
        progressDialog.setProgress(100);
        hideProgress();
    }

    class updateProfile extends AsyncTask<Call, Void, Boolean>{
        Dialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new Dialog(getWindow().getContext());
            dialog.setTitle("Sedang Menyimpan Profil");
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected Boolean doInBackground(Call... calls) {
            final boolean[] returns = {false};
            Call<StringResponse> call = calls[0];
            call.enqueue(new Callback<StringResponse>() {
                @Override
                public void onResponse(Call<StringResponse> call, Response<StringResponse> response) {
                    if (response.isSuccessful()){
                        if (response.body().getSuccess()){
                            returns[0] = true;
                        }

                    }
                }

                @Override
                public void onFailure(Call<StringResponse> call, Throwable t) {

                }
            });
            return returns[0];
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
//            if (aBoolean){
                /*dismiss dialog*/
                dialog.dismiss();
//            }
        }
    }

    @OnClick(R.id.profileImage)
    public void changeImage(){
        final Intent galleryIntent = new Intent();
        galleryIntent.setType("image/*");
        galleryIntent.setAction(Intent.ACTION_PICK);

        final Intent chooserIntent = Intent.createChooser(galleryIntent, "Pilih Image");
        startActivityForResult(chooserIntent, 1010);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode == Activity.RESULT_OK && requestCode == 1010) {
            if (data == null) {
                return;
            }
            Uri selectedImageUri = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            assert selectedImageUri != null;
            @SuppressLint("Recycle")
            Cursor cursor = getContentResolver().query(selectedImageUri, filePathColumn, null, null, null);

            if (cursor != null) {
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imagePath = cursor.getString(columnIndex);
                try {
                    progress_upload();
                } catch (Exception e) {
                    showLog("upload Image", e.getMessage());
                }
            }
            hideProgress();
        }
    }
    private void progress_upload() {
        showLog("UPLOAD IMAGE", "PROGRESS UPLOAD");
        showProgress();

        String token = getAccessToken();

        Map<String, String> headers = new HashMap<>();
        headers.put("appid", "174863748390695");
        headers.put("appsecret", "2BdUbH680ERDaJD1LGjX6Td7jR5Z5O2TJzPgNDOjlo4IANW3W9CYEZQ2OuT01cRpyFqxLWGknKWB46h2d6p4qOBEGvLDISRNAxb0hVgrtpy3K5sPNtsMQDjzwAt2MTvP");
        headers.put("usertoken", token);


        final File file = new File(imagePath);
        ProgressRequestBody fileBody = new ProgressRequestBody(file, this);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("avatar", file.getName(), fileBody);
        MultipartBody.Part namePart = MultipartBody.Part.createFormData("name", profileName.getText().toString());
        MultipartBody.Part emailPart = MultipartBody.Part.createFormData("email", profileEmail.getText().toString());

        Call call = mdirectAccountServices.imageMe(headers, namePart, emailPart, filePart);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                showLog("UPLOAD IMAGE", response.body().toString());
                onFinish();
            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull Throwable t) {

            }
        });
    }
    private void showProgress(){
        progressDialog = new ProgressDialog(this);
        progressDialog.setMax(100);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setCancelable(true);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Uploading");
        progressDialog.show();
    }
    private void hideProgress(){
        if (progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }

}
