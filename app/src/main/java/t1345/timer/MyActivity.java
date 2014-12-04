package t1345.timer;

import android.app.Activity;
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
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class MyActivity extends Activity {

    static String TAG = "myLogs";
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
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "oncreate create");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        btStart = (Button) findViewById(R.id.button);
        btStop = (Button) findViewById(R.id.stop_button);
        btStop.setVisibility(View.INVISIBLE);
        btStart.setVisibility(View.INVISIBLE);
        tvTimerTime = (TextView) findViewById(R.id.Short_time);
        tvTimerTime.setVisibility(View.INVISIBLE);
        tvStartTime = (TextView) findViewById(R.id.tvTime);
        intent = new Intent(this, amService.class);

        sConn = new ServiceConnection() {
            public void onServiceConnected(ComponentName name, IBinder binder) {
                Log.d(TAG, "MainActivity onServiceConnected");
                myService = ((amService.MyBinder) binder).getService();
                bound = true;
            }

            public void onServiceDisconnected(ComponentName name) {
                Log.d(TAG, "MainActivity onServiceDisconnected");
                bound = false;
            }
        };
    }

    @Override
    protected void onStart() {
        Log.d(TAG, "onStart start");

           super.onStart();

        bindService(intent, sConn, 0);


//             if (bound) {
//            btStop.setVisibility(View.VISIBLE);
//            AtimeCalcul(AbackTimeList(amService.timeHour, amService.timeMinute, amService.quant));//пустое активти((
//            // то на случай если баунд(зп коннект отвечает) тру .. должно  работать проверю щас
//        }
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG,"ondestroy bag");

        super.onDestroy();
    }

    static public String formatTime(long millis) //format Time for tv
    {


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
                    calSet.add(Calendar.MINUTE, (105 * 4) - 10);
                    if (calNow.compareTo(calSet) > 0) {
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
            tvStartTime.setText("start in : " + hourOfDay + ":" + minute);
            showDialog(42);
        }
    };
    @Override
    protected void onStop() {
        super.onStop();
        if (!bound) return;
        unbindService(sConn);
        bound = false;
    }


    public void Startbt(View v) {//Start button я понял
        btStop.setVisibility(View.VISIBLE);
        btStart.setVisibility(View.INVISIBLE);
        tvTimerTime.setVisibility(View.VISIBLE);
        if (bound) {
            stopService(new Intent(this, amService.class));
        } else {
            Bundle extra = new Bundle();
            // tut prover luche не понимаю как это сл=деалть
            System.out.println(" Hout minute lesson: " + myHour + " " + myMinute + " " + lessonNum);

            Log.d(TAG, "start button ");
            startService(new Intent(this, amService.class)
                            .putExtra("timeHour", myHour)
                                .putExtra("timeMinute", myMinute)
                                    .putExtra("quant", lessonNum));

            Log.d(TAG, "start button after connect ");

            // intent.putExtras(extra);


            Log.d(TAG, "start button after send ");

                System.out.println(bound);



                    AtimeCalcul(AbackTimeList(myHour, myMinute, lessonNum));//amService.timeHour, amService.timeMinute, amService.quant));

        }
    }

    public void AtimeCalcul(final List<Calendar> list)

    {


        if (list.size() == 0)
        {


            Toast.makeText(getApplicationContext(), "Dobby is free", Toast.LENGTH_LONG).show();

        } else {
            Calendar myDate = Calendar.getInstance();

            long ch = list.get(0).getTimeInMillis() - myDate.getTimeInMillis();
            new CountDownTimer(ch, 1000) {

                public void onTick(long millisUntilFinished) {
                    tvTimerTime.setText(formatTime(millisUntilFinished));
                    System.out.println("intentiti: " + amService.timeHour + " " + amService.timeMinute  + " " + amService.quant);

                }

                public void onFinish() {




                    list.remove(0);
                    AtimeCalcul(list); // recursiya?да я передаю список со значениям . он из них сроит таймеры и на финише далеят элемент списка первый элемент и перезапуска..
                }
            }.start();

        }
    }


    private List<Calendar> AbackTimeList(int sethours, int setminute, int mquant) {//A это то что функция в активти
        //a zachem s bolshoy bukvi funciyu nazivat? ой не знаю
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
        return quasi_date;
    }

    ;

    public void Stopbt(View view) {// stop button
        if (bound) {
            stopService(new Intent(this, amService.class));
        }
        Intent refresh = new Intent(this, MyActivity.class);
        startActivity(refresh);
        this.finish(); //

    }


}
