package t1345.timer;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class MyActivity extends Activity {

    ListView time_list;
    static String TAG = "my_timer";
    Date curTime = new Date(System.currentTimeMillis());
    int hours, minute, quant;
    List<Date> curTime_list = new ArrayList<Date>();
    AlarmManager am;

    Calendar calNow = Calendar.getInstance();
    Calendar calSet = (Calendar) calNow.clone();


    static int lessonNum = 3;
    static int myHour = 13;
    static int myMinute = 45;
    Button btStart;
    Button btStop;
    TextView tvTimerTime;
    TextView tvStartTime;
    boolean bound = false;
    ServiceConnection sConn;

    amService myService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e(TAG, "oncreate step1");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        btStart = (Button) findViewById(R.id.button);
        btStop = (Button) findViewById(R.id.stop_button);
        btStop.setVisibility(View.INVISIBLE);
        btStart.setVisibility(View.INVISIBLE);
        tvTimerTime = (TextView) findViewById(R.id.Short_time);
        tvTimerTime.setVisibility(View.INVISIBLE);
        tvStartTime = (TextView) findViewById(R.id.tvTime);

        sConn = new ServiceConnection() {
    public void onServiceConnected(ComponentName name, IBinder binder) {
        Log.d("Qn_Tag", "MainActivity onServiceConnected");
        myService = ((amService.MyBinder) binder).getService();
        bound = true;
    }

    public void onServiceDisconnected(ComponentName name) {
        Log.d("Qn_tog", "MainActivity onServiceDisconnected");
        bound = false;
    }
};

}

    static public String formatTime(long millis) //format Time for tv
    {
        Log.i(TAG, "Workformat");

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

    public void SetTimeClick(View view) {
        showDialog(1);

    }

    protected Dialog onCreateDialog(int id) {
        if (id == 1) {

            TimePickerDialog tpd = new TimePickerDialog(this, AlertDialog.THEME_HOLO_DARK, setTime, 13, 45, true);
            return tpd;
        }
        if (id == 42) {
            final String[] mLessons_num = {"1", "2", "3", "4"};
            AlertDialog.Builder builder = new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_DARK);
            builder.setTitle("Number of lessons "); // заголовок для диалога
            builder.setItems(mLessons_num, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {
                    // TODO Auto-generated method stub

                    if (mLessons_num[item] == "1") {
                        lessonNum = 1;
                    } else if (mLessons_num[item] == "2") {
                        lessonNum = 2;
                    } else if (mLessons_num[item] == "3") {
                        lessonNum = 3;
                    } else {
                        lessonNum = 4;
                    }
                    calSet.add(Calendar.MINUTE,(105*4)-10);
                    if (calNow.compareTo(calSet)<0){
                        Toast.makeText(getApplicationContext(), "please write current time", Toast.LENGTH_SHORT).show();
                        showDialog(1);
                    }
                    btStart.setVisibility(View.VISIBLE);


                }
            });


            builder.setCancelable(false);
            return builder.create();
        }

        return super.onCreateDialog(id);
    }

    TimePickerDialog.OnTimeSetListener setTime = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {


            myHour = hourOfDay;
            myMinute = minute;
            showDialog(42);



        }
    };


    public void  Startbt (View v){
        btStop.setVisibility(View.VISIBLE);
        btStart.setVisibility(View.INVISIBLE);
        tvTimerTime.setVisibility(View.VISIBLE);
        if (bound){
            stopService(new Intent(this,amService.class));
        }
        startService(new Intent(this, amService.class).putExtra("timeHour", myHour).putExtra("timeMinute", myMinute).putExtra("quant",lessonNum));


    }

    public void timeCalcul(final List<Calendar> list)
    //вычисление и подача сигнала сервису уведомленя
    // н совсем понятно работает ли корректно
    {
        Log.e(TAG, "Error time_calcul");


        if (list.size() == 0)// проблемная зона , эта часть если закоменнтировать то раблтает
        // если даже лист присвоит null все равно не раблотает вылетает с фатал еррором
        {
            Log.i(TAG, "Work");
            Log.e(TAG, "Error time_calcul");

            Toast.makeText(getApplicationContext(), "Dobby is free", Toast.LENGTH_LONG).show();

        } else {
            Log.i(TAG, "Work");
            Log.e(TAG, "Error time_calcul");
            Calendar myDate = Calendar.getInstance();

            long ch = list.get(0).getTimeInMillis() -  myDate.getTimeInMillis() ;


            //    Toast.makeText(this, "time now" + formatTime(dates.get(0).getTime()), Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "time now" + formatTime(myDate.getTimeInMillis()), Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "time set" + formatTime(ch), Toast.LENGTH_SHORT).show();

            new CountDownTimer(ch, 1000) {

                public void onTick(long millisUntilFinished) {
                    tvTimerTime.setText(formatTime(millisUntilFinished));


                }

                public void onFinish() {


                    list.remove(0);
                    timeCalcul(list);
                }
            }.start();

        }
    }



    private List<Calendar> back_time_list( int sethours, int setminute, int mquant) {
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

            quasi_date.add((Calendar)mcalSet.clone());
            mcalSet.add(Calendar.MINUTE, +1);

            quasi_date.add((Calendar)mcalSet.clone());
            mcalSet.add(Calendar.MINUTE, +5);

            quasi_date.add((Calendar)mcalSet.clone());
            if (i < mquant-1) { //add large break time
                mcalSet.add(Calendar.MINUTE, +1);

                quasi_date.add((Calendar)mcalSet.clone());

            }
            Toast.makeText(this,quasi_date.get(quasi_date.size()-1).getTime().toString(),Toast.LENGTH_SHORT);


        }






        int i = 0;
        while (i<quasi_date.size()){
            if (mcalNow.compareTo(quasi_date.get(0))<0){
                quasi_date.remove(0);
                i++;
            }
            else {
                break;
            }
        }
        return quasi_date;    };

    public void Stopbt (View view){
        if (bound){
            stopService(new Intent(this,amService.class));
        }
        Intent refresh = new Intent(this, MyActivity.class);
        startActivity(refresh);
        this.finish(); //

    }


}
