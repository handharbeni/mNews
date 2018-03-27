package illiyin.mhandharbeni.databasemodule.model.mnews;

import android.content.Context;
import android.util.Log;

import org.json.JSONObject;

import java.util.List;

import illiyin.mhandharbeni.databasemodule.generator.ServiceGenerator;
import illiyin.mhandharbeni.databasemodule.model.mnews.response.ResponseFeaturedPost;
import illiyin.mhandharbeni.databasemodule.model.mnews.response.ResponseGetAllPost;
import illiyin.mhandharbeni.databasemodule.model.mnews.response.ResponseGetMenus;
import illiyin.mhandharbeni.databasemodule.model.mnews.response.data.general.Author;
import illiyin.mhandharbeni.databasemodule.model.mnews.response.data.general.Properties;
import illiyin.mhandharbeni.databasemodule.model.mnews.response.data.get_all_post.DataGetAllPost;
import illiyin.mhandharbeni.databasemodule.model.mnews.response.data.get_featured_post.DataFeaturedPost;
import illiyin.mhandharbeni.databasemodule.model.mnews.response.data.get_menus.Child;
import illiyin.mhandharbeni.databasemodule.model.mnews.response.data.get_menus.DataMenus;
import illiyin.mhandharbeni.databasemodule.services.MnewsServices;
import illiyin.mhandharbeni.realmlibrary.Crud;
import io.realm.RealmList;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observer;
import rx.schedulers.Schedulers;

import static android.content.ContentValues.TAG;

/**
 * Created by Beni on 24/03/2018.
 */

public class AdapterRequest {
    Context context;
    private MnewsServices mnewsServices;
    public AdapterRequest(Context context){
        this.context = context;
        initRetrofit();
    }

    public void initRetrofit(){
        mnewsServices = ServiceGenerator.createService(MnewsServices.class);
    }

    public void syncMenus(){
        mnewsServices.getMenus()
                .subscribeOn(Schedulers.trampoline())
                .observeOn(Schedulers.newThread())
                .unsubscribeOn(Schedulers.trampoline())
                .subscribe(new Observer<ResponseGetMenus>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ResponseGetMenus responseGetMenus) {
                        if (responseGetMenus.getSuccess()){
                            DataMenus dataMenus = new DataMenus();
                            Crud crudRealmMenus = new Crud(context, dataMenus);
                            List<DataMenus> data = responseGetMenus.getData();
                            if (data.size() > 0){
                                int j = 0;
                                for (DataMenus dMenus:data){
                                    Boolean duplicate = crudRealmMenus.checkDuplicate("id", dMenus.getId());
                                    if (duplicate){
                                        /*update*/
                                        crudRealmMenus.openObject();
                                        crudRealmMenus.getRealmObject("id", dMenus.getId());
                                        crudRealmMenus.update(dMenus);
                                        crudRealmMenus.commitObject();
                                    }else{
                                        /*create*/
                                        crudRealmMenus.create(dMenus);
                                    }
                                    syncChildMenus(dMenus.getId(), dMenus.getChildren());
                                    j++;
                                }
                            }
                            crudRealmMenus.closeRealm();
                        }
                    }
                });
    }
    public boolean syncMenus(Boolean isTrue){
        mnewsServices.getMenus()
                .subscribeOn(Schedulers.trampoline())
                .observeOn(Schedulers.newThread())
                .unsubscribeOn(Schedulers.trampoline())
                .subscribe(new Observer<ResponseGetMenus>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ResponseGetMenus responseGetMenus) {
                        if (responseGetMenus.getSuccess()){
                            DataMenus dataMenus = new DataMenus();
                            Crud crudRealmMenus = new Crud(context, dataMenus);
                            List<DataMenus> data = responseGetMenus.getData();
                            if (data.size() > 0){
                                int j = 0;
                                for (DataMenus dMenus:data){
                                    Boolean duplicate = crudRealmMenus.checkDuplicate("id", dMenus.getId());
                                    if (duplicate){
                                        /*update*/
                                        crudRealmMenus.openObject();
                                        crudRealmMenus.getRealmObject("id", dMenus.getId());
                                        crudRealmMenus.update(dMenus);
                                        crudRealmMenus.commitObject();
                                    }else{
                                        /*create*/
                                        crudRealmMenus.create(dMenus);
                                    }
                                    syncChildMenus(dMenus.getId(), dMenus.getChildren());
                                    j++;
                                }
                            }
                            crudRealmMenus.closeRealm();
                        }
                    }
                });
        return true;
    }
    private void syncChildMenus(int idMenus, RealmList<Child> children){
        Child child = new Child();
        Crud crudChild = new Crud(context, child);
        for (Child dChild: children) {
            dChild.setIdMenus(idMenus);
            Boolean duplicateChild = crudChild.checkDuplicate("id", child.getId());
            if (duplicateChild) {
                /*update*/
                crudChild.openObject();
                crudChild.getRealmObject("id", dChild.getId());
                crudChild.update(dChild);
                crudChild.commitObject();
            } else {
                /*create*/
                crudChild.create(dChild);
            }
        }
        crudChild.closeRealm();
    }
    public void syncFeatured(){
        Log.d(TAG, "syncFeatured: ");
        mnewsServices.getFeaturedPosts()
                .subscribeOn(Schedulers.trampoline())
                .observeOn(Schedulers.newThread())
                .unsubscribeOn(Schedulers.trampoline())
                .subscribe(new Observer<ResponseFeaturedPost>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ResponseFeaturedPost responseFeaturedPost) {
//                        Log.d(TAG, "onNext: Insert New :"+responseFeaturedPost.getSuccess());
//                        if (responseFeaturedPost.getSuccess()){
//                            DataFeaturedPost data = (DataFeaturedPost) responseFeaturedPost.getData();
//                            if (data != null){
//                                DataFeaturedPost dataFeaturedPost = new DataFeaturedPost();
//                                Crud crudFeaturedPost = new Crud(context, dataFeaturedPost);
//                                crudFeaturedPost.openObject();
//                                crudFeaturedPost.deleteAll(DataFeaturedPost.class);
//                                crudFeaturedPost.commitObject();
//                                    /*delete all data featured*/
//                                    /*create new*/
//                                Log.d(TAG, "onNext: Insert New :"+data.getId());
//                                crudFeaturedPost.create(data);
//                                syncProperties(data.getId(), data.getProperties());
//                                crudFeaturedPost.closeRealm();
//                            }
//
//                        }
                    }
                });

    }
    public boolean syncFeatured(Boolean isTrue){
        Log.d(TAG, "syncFeatured: isTrue");
        mnewsServices.getFeaturedPosts()
                .subscribeOn(Schedulers.trampoline())
                .observeOn(Schedulers.newThread())
                .unsubscribeOn(Schedulers.trampoline())
                .subscribe(new Observer<ResponseFeaturedPost>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(ResponseFeaturedPost responseFeaturedPost) {
//                        Log.d(TAG, "onNext: Insert New :"+responseFeaturedPost.getSuccess());
//                        if (responseFeaturedPost.getSuccess()){
//                            JSONObject jsonObject = responseFeaturedPost.getData();
//                            DataFeaturedPost data = (DataFeaturedPost) responseFeaturedPost.getData();
//                            if (data != null){
//                                DataFeaturedPost dataFeaturedPost = new DataFeaturedPost();
//                                Crud crudFeaturedPost = new Crud(context, dataFeaturedPost);
//                                crudFeaturedPost.openObject();
//                                crudFeaturedPost.deleteAll(DataFeaturedPost.class);
//                                crudFeaturedPost.commitObject();
//                                    /*delete all data featured*/
//                                    /*create new*/
//                                Log.d(TAG, "onNext: Insert New :"+data.getId());
//                                crudFeaturedPost.create(data);
//                                syncProperties(data.getId(), data.getProperties());
//                                crudFeaturedPost.closeRealm();
//                            }
//
//                        }
                    }
                });
        return true;
    }
    private void syncProperties(int id, List<Properties> properties){
        Properties dataProperties = new Properties();
        Crud crudProperties = new Crud(context, dataProperties);
        if (properties.size() > 0){
            for (Properties dProperties:properties){
                Boolean duplicate = crudProperties.checkDuplicate("id", id);
                if (!duplicate){
                    dProperties.setId(id);
                    crudProperties.create(dProperties);
                }
            }
        }
        crudProperties.closeRealm();
    }
    private void syncProperties(int id, Properties properties){
        Properties dataProperties = new Properties();
        Crud crudProperties = new Crud(context, dataProperties);
        if (properties != null){
            Boolean duplicate = crudProperties.checkDuplicate("id", id);
            if (!duplicate){
                properties.setId(id);
                crudProperties.create(properties);
            }
        }
        crudProperties.closeRealm();
    }
    private void syncAuthor(int id, List<Author> authors){
        Author dataAuthor = new Author();
        Crud crudAuthor = new Crud(context, dataAuthor);
        if (authors.size() > 0){
            for (Author dAuthor:authors){
                Boolean duplicate = crudAuthor.checkDuplicate("id", id);
                if (!duplicate){
                    dAuthor.setId(id);
                    crudAuthor.create(dAuthor);
                }
            }
        }
        crudAuthor.closeRealm();
    }

    public Boolean syncPost(String page, boolean isTrue){
        syncPost(page);
        return isTrue;
    }
    public void syncPost(String page){
        Log.d(TAG, "syncPost: GetAllPost");
        mnewsServices.getAllPost(page).enqueue(new Callback<ResponseGetAllPost>() {
            @Override
            public void onResponse(Call<ResponseGetAllPost> call, Response<ResponseGetAllPost> response) {
                if (response.body().getSuccess()){
                    DataGetAllPost dataGetAllPost = new DataGetAllPost();
                    Crud crudGetAllPost = new Crud(context, dataGetAllPost);
                    List<DataGetAllPost> dataResponse = response.body().getData();
                        if (dataResponse.size() > 0){
                            for (DataGetAllPost dGetAllPost:dataResponse){
                                Log.d(TAG, "onNext: Id :"+dGetAllPost.getId());
                                Boolean duplicate = crudGetAllPost.checkDuplicate("id", dGetAllPost.getId());
                                if (duplicate){
                                    /*update data*/
                                    RealmResults resultsDuplicate = crudGetAllPost.read("id", dGetAllPost.getId());
                                    DataGetAllPost resultData = (DataGetAllPost) resultsDuplicate.get(0);
                                    if (dGetAllPost.getUpdatedAt() != null){
                                        if (!resultData.getUpdatedAt().equalsIgnoreCase(dGetAllPost.getUpdatedAt())){
                                    /*update*/
                                            crudGetAllPost.openObject();
                                            crudGetAllPost.getRealmObject("id", dGetAllPost.getId());
                                            crudGetAllPost.update(dGetAllPost);
                                            crudGetAllPost.commitObject();
                                        }
                                    }

//                                        syncProperties(dGetAllPost.getId(), dGetAllPost.getProperties());
                                }else{
                                    /*create data*/
                                    crudGetAllPost.create(dGetAllPost);
//                                        syncProperties(dGetAllPost.getId(), dGetAllPost.getProperties());
                                }
                            }
                        }
                    crudGetAllPost.closeRealm();
                }
            }

            @Override
            public void onFailure(Call<ResponseGetAllPost> call, Throwable t) {

            }
        });
    }
}
