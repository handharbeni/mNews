package illiyin.mhandharbeni.databasemodule.model.mnews;

import android.content.Context;
import android.util.Log;

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
import illiyin.mhandharbeni.databasemodule.utils.SharedPref;
import illiyin.mhandharbeni.realmlibrary.Crud;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observer;
import rx.Subscriber;
import rx.schedulers.Schedulers;

import static android.content.ContentValues.TAG;

/**
 * Created by Beni on 24/03/2018.
 */

public class AdapterRequest {
    Context context;
    private MnewsServices mnewsServices;
    private illiyin.mhandharbeni.databasemodule.utils.SharedPref sharedPref;
    public AdapterRequest(Context context){
        this.context = context;
        initRetrofit();
        sharedPref = new SharedPref(this.context);
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
                                for (final DataMenus dMenus:data){
                                    Boolean duplicate = crudRealmMenus.checkDuplicate("id", dMenus.getId());
                                    if (duplicate){
                                        /*update*/
                                        crudRealmMenus.getRealm().executeTransactionAsync(new Realm.Transaction() {
                                            @Override
                                            public void execute(Realm realm) {
                                                realm.beginTransaction();
                                                realm.copyToRealmOrUpdate(dMenus);
                                                realm.commitTransaction();
                                            }
                                        }, new Realm.Transaction.OnError() {
                                            @Override
                                            public void onError(Throwable error) {
                                                Log.d(TAG, "onError: "+error);
                                            }
                                        });
                                    }else{
                                        crudRealmMenus.getRealm().executeTransactionAsync(new Realm.Transaction() {
                                            @Override
                                            public void execute(Realm realm) {
                                                realm.beginTransaction();
                                                realm.copyToRealm(dMenus);
                                                realm.commitTransaction();
                                            }
                                        }, new Realm.Transaction.OnError() {
                                            @Override
                                            public void onError(Throwable error) {
                                                Log.d(TAG, "onError: "+error);
                                            }
                                        });
                                        /*create*/
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
        Log.d(TAG, "syncMenus: Loaded");
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
                        DataMenus dataMenus = new DataMenus();
                        Crud crudRealmMenus = new Crud(context, dataMenus);
                        List<DataMenus> data = responseGetMenus.getData();
                        if (responseGetMenus.getSuccess()){
                            if (data.size() > 0){
                                int j = 0;
                                for (final DataMenus dMenus:data){
                                    Boolean duplicate = crudRealmMenus.checkDuplicate("id", dMenus.getId());
                                    if (duplicate){
                                        /*update*/
                                        crudRealmMenus.getRealm().executeTransactionAsync(new Realm.Transaction() {
                                            @Override
                                            public void execute(Realm realm) {
                                                realm.beginTransaction();
                                                realm.copyToRealmOrUpdate(dMenus);
                                                realm.commitTransaction();
                                            }
                                        }, new Realm.Transaction.OnError() {
                                            @Override
                                            public void onError(Throwable error) {
                                                Log.d(TAG, "onError: "+error);
                                            }
                                        });
                                    }else{
                                        crudRealmMenus.getRealm().executeTransactionAsync(new Realm.Transaction() {
                                            @Override
                                            public void execute(Realm realm) {
                                                realm.beginTransaction();
                                                realm.copyToRealm(dMenus);
                                                realm.commitTransaction();
                                            }
                                        }, new Realm.Transaction.OnError() {
                                            @Override
                                            public void onError(Throwable error) {
                                                Log.d(TAG, "onError: "+error);
                                            }
                                        });
                                        /*create*/
                                    }
                                    syncChildMenus(dMenus.getId(), dMenus.getChildren());
                                    j++;
                                }
                            }
                            crudRealmMenus.closeRealm();
                        }
                        crudRealmMenus.closeRealm();
                    }
                });
        return true;
    }
    private void syncChildMenus(int idMenus, RealmList<Child> children){
        Child child = new Child();
        Crud crudChild = new Crud(context, child);
        for (final Child dChild: children) {
            dChild.setIdMenus(idMenus);
            Boolean duplicateChild = crudChild.checkDuplicate("id", child.getId());
            if (duplicateChild) {
                crudChild.getRealm().executeTransactionAsync(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        realm.beginTransaction();
                        realm.copyToRealmOrUpdate(dChild);
                        realm.commitTransaction();
                    }
                }, new Realm.Transaction.OnError() {
                    @Override
                    public void onError(Throwable error) {
                        Log.d(TAG, "onError: "+error);
                    }
                });
                /*update*/
            } else {
                /*create*/
                crudChild.getRealm().executeTransactionAsync(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        realm.beginTransaction();
                        realm.copyToRealm(dChild);
                        realm.commitTransaction();
                    }
                }, new Realm.Transaction.OnError() {
                    @Override
                    public void onError(Throwable error) {
                        Log.d(TAG, "onError: "+error);
                    }
                });
            }
        }
        crudChild.closeRealm();
    }
    public void syncFeatureds(){
        mnewsServices.getFeaturedPosts()
                .subscribeOn(Schedulers.trampoline())
                .observeOn(Schedulers.newThread())
                .subscribeOn(Schedulers.trampoline())
                .subscribe(new Subscriber<ResponseFeaturedPost>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ResponseFeaturedPost response) {
                        if (response.getSuccess()){
                            final DataFeaturedPost data = response.getData();
                            if (data != null){
                                DataFeaturedPost dataFeaturedPost = new DataFeaturedPost();
                                final Crud crudFeaturedPost = new Crud(context, dataFeaturedPost);
                                crudFeaturedPost.getRealm().executeTransactionAsync(new Realm.Transaction() {
                                    @Override
                                    public void execute(Realm realm) {
                                        realm.beginTransaction();
                                        realm.deleteAll();
                                        realm.commitTransaction();
                                        realm.beginTransaction();
                                        realm.copyToRealmOrUpdate(data);
                                        realm.commitTransaction();
                                        syncProperties(data.getId(), data.getProperties());

                                    }
                                }, new Realm.Transaction.OnSuccess() {
                                    @Override
                                    public void onSuccess() {
                                    }
                                });
                                crudFeaturedPost.closeRealm();
                            }

                        }

                    }
                });
    }

    public boolean syncFeatured(Boolean isTrue){
        final boolean[] returns = {false};
        mnewsServices.getFeaturedPosts()
                .subscribeOn(Schedulers.trampoline())
                .observeOn(Schedulers.newThread())
                .subscribeOn(Schedulers.trampoline())
                .subscribe(new Subscriber<ResponseFeaturedPost>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        returns[0] = false;
                    }

                    @Override
                    public void onNext(ResponseFeaturedPost response) {
                        if (response.getSuccess()){
                            final DataFeaturedPost data = response.getData();
                            if (data != null){
                                DataFeaturedPost dataFeaturedPost = new DataFeaturedPost();
                                final Crud crudFeaturedPost = new Crud(context, dataFeaturedPost);
                                crudFeaturedPost.getRealm().executeTransactionAsync(new Realm.Transaction() {
                                    @Override
                                    public void execute(Realm realm) {
                                        realm.deleteAll();
                                        realm.beginTransaction();
                                        realm.copyToRealmOrUpdate(data);
                                        realm.commitTransaction();
                                        syncProperties(data.getId(), data.getProperties());
                                    }
                                }, new Realm.Transaction.OnSuccess() {
                                    @Override
                                    public void onSuccess() {
//                                        crudFeaturedPost.create(data);
                                    }
                                }, new Realm.Transaction.OnError() {
                                    @Override
                                    public void onError(Throwable error) {
                                        Log.d(TAG, "onError: "+error);
                                        returns[0] = false;
                                    }
                                });
                                crudFeaturedPost.closeRealm();
                            }

                        }

                    }
                });
        return returns[0];
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
    private void syncProperties(int id, final Properties properties){
        Properties dataProperties = new Properties();
        Crud crudProperties = new Crud(context, dataProperties);
        if (properties != null){
            properties.setId(id);
            Boolean duplicate = crudProperties.checkDuplicate("id", id);
            if (!duplicate){
                crudProperties.getRealm().executeTransactionAsync(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        realm.beginTransaction();
                        realm.copyToRealmOrUpdate(properties);
                        realm.commitTransaction();
                    }
                }, new Realm.Transaction.OnSuccess() {
                    @Override
                    public void onSuccess() {

                    }
                }, new Realm.Transaction.OnError() {
                    @Override
                    public void onError(Throwable error) {
                        Log.d(TAG, "onError: "+error);
                    }
                });
            }
        }
        crudProperties.closeRealm();
    }
    private void syncAuthor(final int id, List<Author> authors){
        Author dataAuthor = new Author();
        final Crud crudAuthor = new Crud(context, dataAuthor);
        if (authors.size() > 0){
            for (final Author dAuthor:authors){
                dAuthor.setId(id);
                Boolean duplicate = crudAuthor.checkDuplicate("id", id);
                if (!duplicate){
                    crudAuthor.getRealm().executeTransactionAsync(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            realm.beginTransaction();
                            realm.copyToRealmOrUpdate(dAuthor);
                            realm.commitTransaction();
                        }
                    }, new Realm.Transaction.OnSuccess() {
                        @Override
                        public void onSuccess() {

                        }
                    }, new Realm.Transaction.OnError() {
                        @Override
                        public void onError(Throwable error) {
                            Log.d(TAG, "onError: "+error);
                        }
                    });
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
                    final Crud crudGetAllPost = new Crud(context, dataGetAllPost);
                    List<DataGetAllPost> dataResponse = response.body().getData();
                        if (dataResponse.size() > 0){
                            for (final DataGetAllPost dGetAllPost:dataResponse){
                                Log.d(TAG, "onNext: Id :"+dGetAllPost.getId());
                                RealmResults duplicate = crudGetAllPost.read("slugId", dGetAllPost.getSlugId());
                                if (duplicate.size() > 0){
                                    /*update data*/
                                    RealmResults resultsDuplicate = crudGetAllPost.read("slugId", dGetAllPost.getSlugId());
                                    DataGetAllPost resultData = (DataGetAllPost) resultsDuplicate.get(0);
                                    if (dGetAllPost.getUpdatedAt() != null){
                                        if (!resultData.getUpdatedAt().equalsIgnoreCase(dGetAllPost.getUpdatedAt())){
                                            crudGetAllPost.getRealm().executeTransactionAsync(new Realm.Transaction() {
                                                @Override
                                                public void execute(Realm realm) {
                                                    /*update*/
                                                    realm.beginTransaction();
                                                    realm.copyToRealmOrUpdate(dGetAllPost);
                                                    realm.commitTransaction();

                                                }
                                            });
                                        }
                                    }

//                                        syncProperties(dGetAllPost.getId(), dGetAllPost.getProperties());
                                }else{
                                    /*create data*/
                                    crudGetAllPost.getRealm().executeTransactionAsync(new Realm.Transaction() {
                                        @Override
                                        public void execute(Realm realm) {
                                            realm.beginTransaction();
                                            realm.copyToRealmOrUpdate(dGetAllPost);
                                            realm.commitTransaction();
                                        }
                                    }, new Realm.Transaction.OnSuccess() {
                                        @Override
                                        public void onSuccess() {

                                        }
                                    });
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
