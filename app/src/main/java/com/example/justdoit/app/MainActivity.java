package com.example.justdoit.app;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;


public class MainActivity extends ActionBarActivity {

    private TextView suggestedActivityTextView;
    private Button suggestActivityButton;
    private Button doItButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        suggestedActivityTextView = (TextView) findViewById(R.id.suggestedActivityTextView);
        suggestActivityButton = (Button) findViewById(R.id.suggestActivityButton);
        doItButton = (Button) findViewById(R.id.doItButton);

        // Set suggested activity to something random on load
        String s = getRandomSuggestedActivity();
        suggestedActivityTextView.setText(s);

        // Stuff specific to initial startup or restoration
        if( savedInstanceState == null ) {
            // Just starting
        } else {
            // App is being restored
        }

        setButtonOnClickListeners();
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save instance
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
                // Launch stopwatch activity
                Intent intent = new Intent(MainActivity.this, StopwatchActivity.class);
                startActivity(intent);
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
                "Work on this app",
                "Add dotfiles to github"
        };

        int i = new Random().nextInt(allActivities.length);
        return allActivities[i];
    }

}
