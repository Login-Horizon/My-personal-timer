package t1345.timer;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by Home on 19.11.2014.
 */
public class amService extends Service {


    public void onCreate() {
        super.onCreate();

    }

    public int onStartCommand(Intent intent, int flags, int startId) {

        someTask();
        return super.onStartCommand(intent, flags, startId);
    }

    public void onDestroy() {
        super.onDestroy();
    }

    public IBinder onBind(Intent intent) {

        return null;
    }

    void someTask() {
    }
}
