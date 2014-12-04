package t1345.timer;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutorService;


public class amService extends Service {
    final String LOG_TAG = "myLogs";
    ExecutorService es;
    static String serSrting = "hello";
    static int timeHour = 20;
    static int timeMinute = 45;
    static int quant  = 5;
    MyBinder binder = new MyBinder();
    NotificationManager nm;

    public void onCreate() {
        super.onCreate();
        nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Log.d(LOG_TAG, "MyService onCreate");
        Log.e(LOG_TAG, "MyService onCreate error");

    }

    public void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "MyService onDestroy");
        Log.e(LOG_TAG, "MyService onDestroy error");


    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("Intent value: " + String.valueOf(intent));
        if (intent == null) {
            return -1;
        }


        Log.d(LOG_TAG, "MyService onStartCommand");
        // ya je govoril, chto intent==null
       if (intent != null) {

            timeHour = intent.getIntExtra("timeHour", 14);
            timeMinute = intent.getIntExtra("timeMinute", 45);
            quant = intent.getIntExtra("quant", 4);
            Log.d(LOG_TAG, "Onstart getextra work");
//
//            while (true) {
//        if (timeHour != 0) {
                StimeCalcul(SbackTimeList(timeHour, timeMinute, quant));

    }
            Log.e(LOG_TAG, "Onstart timecalcul error");
    System.out.print("intent/getextra" + timeHour + " :" + timeMinute + ":" + quant);
//        System.out.print("intent/getextra" + timeHour + " :" + timeMinute + ":" + quant);

        //}


        //return super.onStartCommand(intent, flags, startId); в чем проблема? он ничего из активити не береть . зато активити береть и исползует значения из сервиса вооот
        return super.onStartCommand(intent,flags,startId);




    }

    public void StimeCalcul(final List<Calendar> list)
    //вычисление и подача сигнала сервису уведомленя
    {


        if (list.size() == 0)// проблемная зона , эта часть если закоменнтировать то раблтает
        // если даже лист присвоит null все равно не раблотает вылетает с фатал еррором
        {

            stopSelf();

        } else {
            Calendar myDate = Calendar.getInstance();

            long ch = list.get(0).getTimeInMillis() - myDate.getTimeInMillis();


            new CountDownTimer(ch, 1000) {

                public void onTick(long millisUntilFinished) {


                }

                public void onFinish() {

                    sendNotif();
                    //класс для возврата из уведомления в основной активити (реализован: скоро покажу)
                    list.remove(0);
                    StimeCalcul(list);
                }
            }.start();
            Log.d(LOG_TAG,"send notif qorck");

        }
    }

    public List<Calendar> SbackTimeList(int sethours, int setminute, int mquant) {
        Calendar mcalNow = Calendar.getInstance();
        Calendar mcalSet = (Calendar) mcalNow.clone();

        mcalSet.set(Calendar.HOUR_OF_DAY, sethours);
        mcalSet.set(Calendar.MINUTE, setminute);
        mcalSet.set(Calendar.SECOND, 0);
        mcalSet.set(Calendar.MILLISECOND, 0);

        List<String> mTime_list = new ArrayList<String>();
        List<Calendar> quasi_date = new ArrayList<Calendar>();


        long util;


        quasi_date.add((Calendar) mcalSet.clone());


        for (int i = 0; i < mquant; i++) { //add alarm_time in  list


            mcalSet.add(Calendar.MINUTE, +5);

            quasi_date.add((Calendar) mcalSet.clone());
            mcalSet.add(Calendar.MINUTE, +1);

            quasi_date.add((Calendar) mcalSet.clone());
            mcalSet.add(Calendar.MINUTE, +5);

            quasi_date.add((Calendar) mcalSet.clone());
            if (i < mquant - 1) { //add large break time
                mcalSet.add(Calendar.MINUTE, +2);

                quasi_date.add((Calendar) mcalSet.clone());

            }


        }


        int i = 0;
        while (i < quasi_date.size()) {
            if (mcalNow.compareTo(quasi_date.get(0)) < 0) {
                quasi_date.remove(0);
                i++;
            } else {
                break;
            }
        }
        Log.d(LOG_TAG,"list workc");
        return quasi_date;

    }


    void sendNotif() {

        Notification notif = new Notification(R.drawable.timer, "Time",
                System.currentTimeMillis());

        Intent intent = new Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_LAUNCHER)
                .setComponent(getPackageManager().getLaunchIntentForPackage(getPackageName()).getComponent());

        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);


        notif.setLatestEventInfo(this, "Ding, dong, bell))", "Звонок ", pIntent);

        notif.flags |= Notification.FLAG_AUTO_CANCEL;


        nm.notify(1, notif);
        Log.d(LOG_TAG,"notif work");

    }

    public IBinder onBind(Intent arg0) {
        Log.d(LOG_TAG, "MyService onBind");
        return binder;
    }


    class MyBinder extends Binder {

        amService getService() {
            return amService.this;
        }
    }
}