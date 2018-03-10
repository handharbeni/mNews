package illiyin.mhandharbeni.realmlibrary;

import android.content.Context;

import illiyin.mhandharbeni.crudrealmmodul.CRUDRealm;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by root on 17/07/17.
 */

public class Crud {
    Context context;
    CRUDRealm crudRealm;
    private RealmObject realmObject;
    public Crud(Context context, RealmObject realmObject) {
        this.context = context;
        this.realmObject = realmObject;
        if (crudRealm == null){
            crudRealm = new CRUDRealm(this.context, this.realmObject);
        }
    }

    public RealmObject getRealmObject(){
        return this.realmObject;
    }

    public void create(RealmObject realmObject){
        crudRealm.create(realmObject);
    }
    public RealmResults read(){

        return crudRealm.read();
    }
    public RealmResults read(String key, String value){
        return crudRealm.read(key, value);
    }

    public Crud reads(String key, Integer value){
        read(key,value);
        return this;
    }
    public RealmResults read(String key, String[] value){
        return crudRealm.read(key, value);
    }
    public RealmResults read(String key, Integer value){
        return crudRealm.read(key, value);
    }
    public RealmResults read(String key, Integer[] value){
        return crudRealm.read(key, value);
    }
    public RealmResults like(String key, String filter, Case casing){
        return crudRealm.like(key, filter, casing);
    }
    public RealmResults contains(String key, String filter){
        return crudRealm.contains(key, filter);
    }
    public RealmResults readSorted(String keySorted, Sort sort){
        return crudRealm.read().sort(keySorted, sort);
    }
    public RealmResults readSorted(String key, String value, String keySorted, Sort sort){
        return crudRealm.read(key, value).sort(keySorted, sort);
    }
    public RealmResults readSorted(String key, String[] value, String keySorted, Sort sort){
        return crudRealm.read(key, value).sort(keySorted, sort);
    }
    public RealmResults readSorted(String key, Integer value, String keySorted, Sort sort){
        return crudRealm.read(key, value).sort(keySorted, sort);
    }
    public RealmResults readSorted(String key, Integer[] value, String keySorted, Sort sort){
        return crudRealm.read(key, value).sort(keySorted, sort);
    }

    public RealmObject getRealmObject(String key, String value){
        RealmObject objectUpdate = crudRealm.setObjectUpdate(key, value);
        return objectUpdate;
    }
    public RealmObject getRealmObject(String key, Integer value){
        RealmObject objectUpdate = crudRealm.setObjectUpdate(key, value);
        return objectUpdate;
    }
    public void openObject(){
        crudRealm.openObject();
    }
    public void commitObject(){
        crudRealm.commitObject();
    }
    public void closeRealm(){
        if (crudRealm!=null){
            if (crudRealm.realm.isInTransaction()){
                crudRealm.realm.cancelTransaction();
            }
            crudRealm.closeRealm();
            crudRealm = null;
        }
    }
    public void update(RealmObject realmObject){
        crudRealm.update(realmObject);
    }
    public void delete(String key, String value){
        crudRealm.delete(key, value);
    }
    public void delete(String key, Integer value){
        crudRealm.delete(key, value);
    }
    public void deleteAll(final Class<? extends RealmObject> objectModels){
        crudRealm.realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(objectModels);
            }
        });
    }
    public Boolean checkDuplicate(String key, String value){
        RealmResults realmResults = crudRealm.read(key, value);
        if (realmResults.size() > 0) {
            return true;
        } else {
            return false;
        }
    }
    public Boolean checkDuplicate(String key, Integer value){
        RealmResults realmResults = crudRealm.read(key, value);
        if (realmResults.size() > 0) {
            return true;
        } else {
            return false;
        }
    }
}
