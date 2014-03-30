package com.example.justdoit.app;

import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import java.util.Random;


public class MainActivity extends ActionBarActivity {

    private TextView suggestedActivityTextView;
    private Button suggestActivityButton;
    private Button doItButton;
    private Chronometer timeSpentChronometer;

    private boolean isTimerActive = false;
    private static final String START_TICK = "START_TICK";
    private static final String LAST_SUSPEND_TICK = "LAST_SUSPEND_TICK";
    private long startTick = 0L;
    private long lastSuspendTick = 0L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        suggestedActivityTextView = (TextView) findViewById(R.id.suggestedActivityTextView);
        suggestActivityButton = (Button) findViewById(R.id.suggestActivityButton);
        doItButton = (Button) findViewById(R.id.doItButton);
        timeSpentChronometer = (Chronometer) findViewById(R.id.timeSpentChronometer);


        // Set suggested activity to something random on load
        String s = getRandomSuggestedActivity();
        suggestedActivityTextView.setText(s);


        // Stuff specific to initial startup or restoration
        if( savedInstanceState == null ) {
            // Just starting
            isTimerActive = false;
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
        outState.putLong(LAST_SUSPEND_TICK,(SystemClock.elapsedRealtime()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

        suggestActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Set suggested activity to something new
                String s = getRandomSuggestedActivity();
                suggestedActivityTextView.setText(s);
            }
        });

        doItButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start timer for 30+ mins
                // TODO make timer/did it button into its own activity

                isTimerActive = true;
                startTick = SystemClock.elapsedRealtime();
                timeSpentChronometer.setBase(startTick);
                timeSpentChronometer.start();
            }
        });
    }

    private String getRandomSuggestedActivity() {

        // TEMP for debug.. activities should be pulled from external source (flatfile? dropbox? webservice?)
        String allActivities[] = {
                "Go for a walk in the woods",
                "Read a book",
                "Work on closet shelf",
                "Build PC from spare parts",
                "Play Guitar",
                "Paint wooden chest",
                "Draw something from drawme folder",
                "Dust out computers",
                "Stretch / do yoga",
                "Go for a bike ride",
                "Learn to read/write korean",
                "Learn to read/write chinese",
                "Work on paper mache train tunnel",
                "Make paper mache piggie bank",
                "Work on scroll for lauren",
                "Plaster work",
                "Reschedule mulroy (~May 8th)",
                "Set up personal website",
                "Work on demo site for resume",
                "Work on android app",
                "Work on iOS app",
                "Clean room",
                "Work on python project",
                "Go take pictures",
                "Learn a song on guitar",
                "Learn to compose music on fruityloops",
                "Learn MIDI",
                "Work on HTML5 version of this app",
                "Work on this app"
        };

        int i = new Random().nextInt(allActivities.length);
        return allActivities[i];
    }

}
