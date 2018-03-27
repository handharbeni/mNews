package illiyin.mhandharbeni.databasemodule.model.mnews;

import android.content.Context;
import android.util.Log;

import java.util.List;

import illiyin.mhandharbeni.databasemodule.generator.ServiceGenerator;
import illiyin.mhandharbeni.databasemodule.model.mnews.response.ResponseGetMenus;
import illiyin.mhandharbeni.databasemodule.model.mnews.response.data.get_menus.Child;
import illiyin.mhandharbeni.databasemodule.model.mnews.response.data.get_menus.DataMenus;
import illiyin.mhandharbeni.databasemodule.services.MnewsServices;
import illiyin.mhandharbeni.realmlibrary.Crud;
import io.realm.Realm;
import io.realm.RealmList;
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
}
