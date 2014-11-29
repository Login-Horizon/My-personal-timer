package t1345.timer;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import java.util.List;
import java.util.concurrent.ExecutorService;


public class amService extends Service {
    final String LOG_TAG = "myLogs";
    ExecutorService es;

    NotificationManager nm;

    public void onCreate() {
        super.onCreate();
        nm  = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Log.d(LOG_TAG, "MyService onCreate");

    }

    public void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "MyService onDestroy");

    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        int count;
        Log.d(LOG_TAG, "MyService onStartCommand");
        int timeHour = intent.getIntExtra("timeHour", 13);
        int timeMinute = intent.getIntExtra("timeMinute",45);
        int quant = intent.getIntExtra("quant",3);
        countDTimer(back_time_list(timeHour,timeMinute,quant));

        return super.onStartCommand(intent, flags, startId);
    }
     private List<Calendar> back_time_list( int sethours, int setminute, int mquant) {
             Calendar mcalNow = Calendar.getInstance();
             Calendar mcalSet = (Calendar) mcalNow.clone();
             if(mcalSet.compareTo(mcalNow) <= 0){
                 //Today Set time passed, count to tomorrow
                 mcalSet.add(Calendar.DATE, 1);
             }

//             mcalSet.set(Calendar.HOUR_OF_DAY, sethours);
//             mcalSet.set(Calendar.MINUTE, setminute);
             mcalSet.set(Calendar.SECOND, 0);
             mcalSet.set(Calendar.MILLISECOND, 0);

             List<String> mTime_list = new ArrayList<String>();
             List<Calendar> quasi_date = new ArrayList<Calendar>();





             long util;




             quasi_date.add((Calendar) mcalSet.clone());


             for (int i = 0; i < mquant; i++) { //add alarm_time in  list
                 Toast.makeText(this,mcalSet.toString(),Toast.LENGTH_SHORT);


                 mcalSet.add(Calendar.MINUTE, +5);

                 quasi_date.add((Calendar)mcalSet.clone());
                 mcalSet.add(Calendar.MINUTE, +1);

                 quasi_date.add((Calendar)mcalSet.clone());
                 mcalSet.add(Calendar.MINUTE, +5);

                 quasi_date.add((Calendar)mcalSet.clone());
                 if (i < mquant-1) { //add large break time
                     mcalSet.add(Calendar.MINUTE, +2);

                     quasi_date.add((Calendar)mcalSet.clone());

                 }


             }



        return quasi_date;


    }
    void sendNotif() {

        Notification notif = new Notification(R.drawable.ic_launcher, "Time",
                System.currentTimeMillis());

        Intent intent =new Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_LAUNCHER)
                .setComponent(getPackageManager().getLaunchIntentForPackage(getPackageName()).getComponent());

        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);


        notif.setLatestEventInfo(this, "Р—РІРѕРЅРѕРє", "Zvonok", pIntent);

        notif.flags |= Notification.FLAG_AUTO_CANCEL;



        nm.notify(1, notif);

    }

    static   public String formatTime(long millis)
    {
        Log.i("my", "Workformat");


        String output = "";
        long seconds = millis / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;

        seconds = seconds % 60;
        minutes = minutes % 60;
        hours = hours % 60;

        String secondsD = String.valueOf(seconds);
        String minutesD = String.valueOf(minutes);
        String hoursD = String.valueOf(hours);

        if (seconds < 10)
            secondsD = "0" + seconds;
        if (minutes < 10)
            minutesD = "0" + minutes;

        if (hours < 10)
            hoursD = "0" + hours;

        output = hoursD + " : " + minutesD + " : " + secondsD;

        return output;
    }

    public void countDTimer  (final List<Calendar> timeList) {
        final long ch;
        final Calendar time = Calendar.getInstance();
        if (timeList.size() != 0) {
            ch =timeList.get(0).getTimeInMillis()- time.getTimeInMillis()  ;
            new CountDownTimer(ch, 1000) {

                public void onTick(long millisUntilFinished) {
                   Log.i("time go","mytime");


                }

                public void onFinish() {
                    Toast.makeText(getApplicationContext(),formatTime(ch),Toast.LENGTH_SHORT);
                    sendNotif();



                    timeList.remove(0);
                   countDTimer(timeList);
                }
            }.start();

        }

    }


    public IBinder onBind(Intent arg0) {
        return null;
    }


}