// TODO: Replace com.example in package name to something unique
package com.example.justdoit.app;

import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;


public class StopwatchActivity extends ActionBarActivity {
    private Chronometer timeSpentChronometer;
    private Button pauseButton;
    private Button didItButton;

    private static final String TICKS_WHEN_STOPPED = "TICKS_WHEN_STOPPED";
    private static final String IS_TIMER_ACTIVE = "IS_TIMER_ACTIVE";
    private long ticksWhenStopped = 0L;
    private boolean isTimerActive = false;

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
            timeSpentChronometer.setBase(SystemClock.elapsedRealtime());
            timeSpentChronometer.start();

            // TODO: Use alarm manager to schedule an alarm to go off in 30 minutes

        } else {
            // App is being restored
            ticksWhenStopped = savedInstanceState.getLong(TICKS_WHEN_STOPPED);
            isTimerActive = savedInstanceState.getBoolean(IS_TIMER_ACTIVE);
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
        outState.putLong(TICKS_WHEN_STOPPED, ticksWhenStopped);
        outState.putBoolean(IS_TIMER_ACTIVE, isTimerActive);
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
                    ticksWhenStopped =  SystemClock.elapsedRealtime() - timeSpentChronometer.getBase();
                    pauseButton.setText(getString(R.string.unpauseButton));
                    timeSpentChronometer.stop();

                    // TODO: set "snooze" alarm for 5 mins in case user forgets app is paused
                } else {
                    isTimerActive = true;
                    pauseButton.setText(getString(R.string.pauseButton));
                    timeSpentChronometer.setBase(SystemClock.elapsedRealtime() - ticksWhenStopped);
                    timeSpentChronometer.start();
                }
            }
        });

        didItButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isTimerActive = false;
                timeSpentChronometer.stop();

                // TODO: rename didItButton to "~quit early" until 30 minutes has passed then set to "DID IT!"

                // TODO: record results for reporting feature?

            }
        });
    }
}
