package illiyin.mhandharbeni.servicemodule.service;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.IBinder;

import com.google.firebase.crash.FirebaseCrash;


/**
 * Created by root on 17/07/17.
 */

public class MainService extends Service {
    private static final String TAG = "MyLocationService";
    private LocationManager mLocationManager = null;
    private static final int LOCATION_INTERVAL = 1000;
    private static final float LOCATION_DISTANCE = 5f;

    private static int PERIODICALLY_CALL = 2 * 1000;
    private static int DELAY_CALL = 500;


    public static Boolean serviceRunning = false;

    @Override
    public void onCreate() {
        syncChat();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        serviceRunning = true;
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
    public void syncChat(){
//        Action0 action0 = new Action0() {
//            @Override
//            public void call() {
//                if (!checkIsRunning(ChatService.class)){
//                    Intent is = new Intent(getBaseContext(), ChatService.class);
//                    startService(is);
//                }
//            }
//        };
//        Scheduler.Worker worker = Schedulers.newThread().createWorker();
//        worker.schedulePeriodically(action0, DELAY_CALL, PERIODICALLY_CALL, TimeUnit.MILLISECONDS);
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
