package t1345.timer;

import android.app.Activity;
import android.app.AlarmManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class MyActivity extends Activity {

    ListView time_list;
    static   String TAG = "my_timer";
    Date curTime = new Date(System.currentTimeMillis());
    int hours, minute, quant;
    List<Date> curTime_list = new ArrayList<Date>();
    AlarmManager am ;
    Button btStart;
    Button btList;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e(TAG,"oncreate step1");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        btStart = (Button) findViewById(R.id.btStart);
        btList = (Button) findViewById(R.id.btList);

        Log.e(TAG,"oncreate step2");

    }
    public  void CalculClick(View v){
        startService(new Intent(this, amService.class).putExtra("timeHour", 13));
        startService(new Intent(this, amService.class).putExtra("timeMinute", 45));
        startService(new Intent(this, amService.class).putExtra("quant",3));


    }
    public void GoToList(View v){
        setContentView(R.layout.time_list);
        listView = (ListView) findViewById(R.id.listView);
        Date curTime  = new Date(System.nanoTime());
        ArrayAdapter<String> arrayAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,back_time_list(13,45,3));
        Log.e(TAG,"oncreate step2");

        listView.setAdapter(arrayAdapter);

    }
    static private List<String> back_time_list( int sethours, int setminute, int mquant) {
        Calendar mcalNow = Calendar.getInstance();
        Calendar mcalSet = (Calendar) mcalNow.clone();
        if(mcalSet.compareTo(mcalNow) <= 0){
            //Today Set time passed, count to tomorrow
            mcalSet.add(Calendar.DATE, 1);
        }

        mcalSet.set(Calendar.HOUR_OF_DAY, sethours);
        mcalSet.set(Calendar.MINUTE, setminute);
        mcalSet.set(Calendar.SECOND, 0);
        mcalSet.set(Calendar.MILLISECOND, 0);

        List<String> mTime_list = new ArrayList<String>();
        List<Calendar> quasi_date = new ArrayList<Calendar>();
        Log.e(TAG,"back_time");




        long util;
        Log.e(TAG,"set_time in back time");


        Log.e(TAG,"set_time in back time");
        quasi_date.add((Calendar) mcalSet.clone());

        Log.e(TAG,"add list back_time");

        for (int i = 0; i < mquant; i++) { //add alarm_time in  list
            Log.e(TAG,"loop in back_time");

            mcalSet.add(Calendar.MINUTE, +45);

            quasi_date.add((Calendar)mcalSet.clone());
            mcalSet.add(Calendar.MINUTE, +5);

            quasi_date.add((Calendar)mcalSet.clone());
            mcalSet.add(Calendar.MINUTE, +45);

            quasi_date.add((Calendar)mcalSet.clone());
            if (i < mquant-1) { //add large break time
                mcalSet.add(Calendar.MINUTE, +10);

                quasi_date.add((Calendar)mcalSet.clone());

            }


        }
        for (int i = 0; i <quasi_date.size()  ; i++) {
            mTime_list.add((quasi_date.get(i).getTime().toString()));


        }
        return mTime_list;


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
