package com.example.budul;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    Button alarm_on, alarm_off;
    TextView updateText;
    TimePicker TimePicker;
    AlarmManager alarmManager;
    PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        alarm_on = (Button) findViewById(R.id.alarm_on);
        alarm_off = (Button) findViewById(R.id.alarm_of);
        updateText = (TextView) findViewById(R.id.updateTimeText);
        TimePicker = (TimePicker) findViewById(R.id.timePicker);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        final Calendar calendar = Calendar.getInstance();
        final Intent my_intent = new Intent(getApplicationContext(), AlarmReceiver.class);

        alarm_on.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void  onClick(View v) {
                calendar.set(calendar.HOUR_OF_DAY, TimePicker.getHour());
                calendar.set(Calendar.MINUTE, TimePicker.getMinute());
                int hour = TimePicker.getHour();
                int minute = TimePicker.getMinute();
                String hour_string = String.valueOf(hour);
                String minute_string = String.valueOf(minute);
                if(hour > 12) {
                    hour_string = String.valueOf(hour - 12);
                }
                if(minute < 10) {
                    minute_string = "0" + String.valueOf(minute);
                }
                setTimeText("Будильник поставлен на " + hour_string +":" + minute_string);
                pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, my_intent, PendingIntent.FLAG_UPDATE_CURRENT);
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                Toast toast = Toast.makeText(getApplicationContext(),
                        "НЕ ПРОСПИ БРАТ!!!", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
        alarm_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTimeText("Будильник выключен");
            }
        });

    }

    private void setTimeText(String s) {
        updateText.setText(s);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
