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
import java.util.Date;
import java.util.List;


public class MyActivity extends Activity {

    ListView time_list;
    static   String TAG = "my_timer";
    Date curTime = new Date(System.currentTimeMillis());
    int hours, minute, quant;
    List<Date> curTime_list = new ArrayList<Date>();
    AlarmManager am ;
    Button bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e(TAG,"oncreate step1");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        bt = (Button) findViewById(R.id.button);

        Log.e(TAG,"oncreate step2");

    }
    public  void  Omclick(View v){
        startService(new Intent(this, amService.class).putExtra("timeHour", 13));
        startService(new Intent(this, amService.class).putExtra("timeMinute", 45));
        startService(new Intent(this, amService.class).putExtra("quant",3));


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
