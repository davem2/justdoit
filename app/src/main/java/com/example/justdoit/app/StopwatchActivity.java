package com.example.justdoit.app;

import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Toast;


public class StopwatchActivity extends ActionBarActivity {

    private Chronometer timeSpentChronometer;
    private Button pauseButton;
    private Button didItButton;

    private boolean isTimerActive = false;
    private static final String START_TICK = "START_TICK";
    private static final String LAST_PAUSE_TICK = "LAST_PAUSE_TICK";
    private static final String LAST_SUSPEND_TICK = "LAST_SUSPEND_TICK";
    private long startTick = 0L;
    private long lastPauseTick = 0L;
    private long lastSuspendTick = 0L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);

        timeSpentChronometer = (Chronometer) findViewById(R.id.timeSpentChronometer);
        pauseButton = (Button) findViewById(R.id.pauseButton);
        didItButton = (Button) findViewById(R.id.didItButton);

        // Stuff specific to initial startup or restoration
        if( savedInstanceState == null ) {
            // Just starting
            isTimerActive = true;
            startTick = SystemClock.elapsedRealtime();
            timeSpentChronometer.setBase(startTick);
            timeSpentChronometer.start();

        } else {
            // App is being restored
            startTick = savedInstanceState.getLong(START_TICK);
            lastSuspendTick = savedInstanceState.getLong(LAST_SUSPEND_TICK);
            if( isTimerActive ) {
                timeSpentChronometer.setBase(SystemClock.elapsedRealtime());
                timeSpentChronometer.start();
            }
        }

        setButtonOnClickListeners();
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save instance
        outState.putLong(START_TICK,startTick);
        outState.putLong(LAST_SUSPEND_TICK, (SystemClock.elapsedRealtime()));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.stopwatch, menu);
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

    private void setButtonOnClickListeners() {

        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( isTimerActive ) {
                    isTimerActive = false;
                    lastPauseTick = SystemClock.elapsedRealtime();

                    timeSpentChronometer.stop();

                } else {
                    isTimerActive = true;
                    long ticksSpentPaused = SystemClock.elapsedRealtime() - lastPauseTick;

                    timeSpentChronometer.setBase(SystemClock.elapsedRealtime() - ticksSpentPaused);
                    timeSpentChronometer.start();
                }
            }
        });

        didItButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isTimerActive = false;
                timeSpentChronometer.stop();
            }
        });
    }


}
