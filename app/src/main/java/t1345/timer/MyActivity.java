package t1345.timer;

import android.app.Activity;
import android.app.AlarmManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class MyActivity extends Activity {

    ListView time_list;
    static   String TAG = "my_timer";
    Date curTime = new Date(System.currentTimeMillis());
    int hours, minute, quant;
    List<Date> curTime_list = new ArrayList<Date>();
    AlarmManager am ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e(TAG,"oncreate step1");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        Log.e(TAG,"oncreate step2");

        
    }

    static public String formatTime(long millis) {
        Log.e(TAG,"format_time");



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

    static private List<Date> back_time_list(Date currTime , int sethours, int setminute, int mquant) {
        List<String> mTime_list = new ArrayList<String>();
        List<Date> quasi_date = new ArrayList<Date>();
        Log.e(TAG,"back_time");


        currTime.setHours(sethours);
        currTime.setMinutes(setminute);
        long util;
        Log.e(TAG,"ser_time in back time");

        //util = (Long) currTime.clone();
        Log.e(TAG,"set_time in back time");
      //  mTime_list.add((String) formatTime(util));
        quasi_date.add((Date)currTime.clone());

        Log.e(TAG,"add list back_time");

        for (int i = 0; i < mquant; i++) { //add alarm_time in  list
            Log.e(TAG,"loop in back_time");


            currTime.setMinutes(currTime.getMinutes() + 45);
            quasi_date.add((Date)currTime.clone());
            currTime.setMinutes(currTime.getMinutes() + 5);
            quasi_date.add((Date)currTime.clone());
            currTime.setMinutes(currTime.getMinutes() + 45);
            quasi_date.add((Date)currTime.clone());
            if (i < mquant-1) { //add large break time
                currTime.setMinutes(currTime.getMinutes() + 10);
                quasi_date.add((Date)currTime.clone());

            }


        }
        for (int i = 0; i <quasi_date.size()  ; i++) {
            mTime_list.add(formatTime(quasi_date.get(i).getTime()));


        }
        return quasi_date;



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
