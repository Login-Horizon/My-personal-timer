package t1345.timer;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class MyActivity extends Activity {

    ListView time_list;
    static String TAG = "my_timer";
    Date curTime = new Date(System.currentTimeMillis());
    int hours, minute, quant;
    List<Date> curTime_list = new ArrayList<Date>();
    AlarmManager am;

    static int lessonNum = 3;
    static int myHour = 13;
    static int myMinute = 45;
    Button btStart;
    Button btStop;
    TextView tvTimerTime;
    TextView tvStartTime;

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
        Log.e(TAG, "oncreate step2");

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
                    btStart.setVisibility(View.VISIBLE);


                }
            });


            builder.setCancelable(false);
            return builder.create();
        }

        return super.onCreateDialog(id);
    }

    public void  Startbt (View v){
        btStop.setVisibility(View.VISIBLE);
        btStart.setVisibility(View.INVISIBLE);
        tvTimerTime.setVisibility(View.VISIBLE);

    }
    TimePickerDialog.OnTimeSetListener setTime = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {


            myHour = hourOfDay;
            myMinute = minute;
            showDialog(42);



        }
    };


}
