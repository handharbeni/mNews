package illiyin.mhandharbeni.servicemodule.service;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.IBinder;

import com.google.firebase.crash.FirebaseCrash;

import java.util.concurrent.TimeUnit;

import illiyin.mhandharbeni.databasemodule.model.mnews.AdapterRequest;
import rx.Scheduler;
import rx.functions.Action0;
import rx.schedulers.Schedulers;


/**
 * Created by root on 17/07/17.
 */

public class MainService extends Service {
    private static final String TAG = "MyLocationService";
    private LocationManager mLocationManager = null;
    private static final int LOCATION_INTERVAL = 1000;
    private static final float LOCATION_DISTANCE = 5f;

    private static int PERIODICALLY_CALL_TWO_SECONDS = 2 * 1000;
    private static int PERIODICALLY_CALL_FIVE_SECONDS = 5 * 1000;
    private static int PERIODICALLY_CALL_TEN_SECONDS = 10 * 1000;
    private static int PERIODICALLY_CALL_FIFTEEN_SECONDS = 15 * 1000;
    private static int PERIODICALLY_CALL_TWENTY_SECONDS = 20 * 1000;
    private static int DELAY_CALL = 500;

    private AdapterRequest adapterRequest;


    public static Boolean serviceRunning = false;

    @Override
    public void onCreate() {
        syncMenus();
        syncFeaturedPost();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        serviceRunning = true;
        adapterRequest = new AdapterRequest(getApplicationContext());
        return START_STICKY;
    }


    private boolean checkIsRunning(Class<?> serviceClass) {
        try {
            ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
            assert manager != null;
            for (ActivityManager.RunningServiceInfo service : manager
                    .getRunningServices(Integer.MAX_VALUE)) {
                if (serviceClass.getName().equals(service.service.getClassName())) {
                    return true;
                }
            }
        }catch (Exception e){
            FirebaseCrash.report(e);
            return false;
        }
        return false;
    }
    public void syncMenus(){
        Action0 action0 = new Action0() {
            @Override
            public void call() {
                adapterRequest.syncMenus();
            }
        };
        Scheduler.Worker worker = Schedulers.newThread().createWorker();
        worker.schedulePeriodically(action0, DELAY_CALL, PERIODICALLY_CALL_TEN_SECONDS, TimeUnit.MILLISECONDS);
    }
    public void syncFeaturedPost(){
        Action0 action0 = new Action0() {
            @Override
            public void call() {
                adapterRequest.syncFeatureds();
            }
        };
        Scheduler.Worker worker = Schedulers.newThread().createWorker();
        worker.schedulePeriodically(action0, DELAY_CALL, PERIODICALLY_CALL_TWENTY_SECONDS, TimeUnit.MILLISECONDS);
    }

    @Override
    public void onDestroy() {
        serviceRunning = false;
        super.onDestroy();
    }
    private void initializeLocationManager() {
        if (mLocationManager == null) {
            mLocationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        }
    }
}
