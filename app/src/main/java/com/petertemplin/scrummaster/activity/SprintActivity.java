package com.petertemplin.scrummaster.activity;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.petertemplin.scrummaster.R;
import com.petertemplin.scrummaster.data.DataUtils;
import com.petertemplin.scrummaster.models.Sprint;
import com.petertemplin.scrummaster.models.Task;
import com.petertemplin.scrummaster.util.DateUtils;


public class SprintActivity extends ActionBarActivity {

    Button startButton;
    Button endButton;

    Sprint currentSprint;
    ListView sprintTaskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sprint);

        sprintTaskList = (ListView) findViewById(R.id.sprintTaskList);
        currentSprint = DataUtils.getInstance(this).getLatestSprint();

        // set the buttons
        updateButtons();
        // set the details
        setSprintDetails();

        // for testing, use all backlog tasks
        ArrayAdapter<Task> adapter = new ArrayAdapter<>(this, R.layout.sprint_task_list_item, currentSprint.toArray());
        sprintTaskList.setAdapter(adapter);
        sprintTaskList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startTaskDetails(view);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sprint, menu);
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

    public void startTaskDetails(View view) {
        Intent intent = new Intent(this, ViewTaskActivity.class);
        intent.putExtra(ViewTaskActivity.VIEWING_TASK_TITLE, ((TextView)view).getText().toString());
        startActivity(intent);
    }

    public void updateButtons() {
        if (startButton == null) {
            startButton = (Button) findViewById(R.id.sprintStartButton);
            startButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startSprint();
                }
            });
        }
        if (endButton == null) {
            endButton = (Button) findViewById(R.id.sprintButtonEnd);
            endButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    endSprint();
                }
            });
        }
        if (currentSprint.isStarted() == null || !currentSprint.isStarted()) {
            startButton.setVisibility(View.GONE);
            endButton.setVisibility(View.VISIBLE);
        } else {
            startButton.setVisibility(View.VISIBLE);
            endButton.setVisibility(View.GONE);
        }
    }

    public void startSprint() {
        currentSprint.setStarted(true);
        currentSprint.setStartDate(DateUtils.currentDateToString());
        currentSprint.save(this);
    }

    public void endSprint() {
        currentSprint.setEndDate(DateUtils.currentDateToString());
        currentSprint.save(this);
    }

    public void setSprintDetails() {
        TextView titleView = (TextView) findViewById(R.id.sprint_title);
        String title = currentSprint.getName();
        if (title == null) {
            title = "New Sprint";
        }
        titleView.setText(title);

        TextView descView = (TextView) findViewById(R.id.sprint_description);
        String description = currentSprint.getDescription();
        if (description == null) {
            descView.setVisibility(View.GONE);
        } else {
            descView.setText(description);
        }

        TextView durationView = (TextView) findViewById(R.id.sprint_duration);
        String duration = currentSprint.getDuration();
        if (duration == null) {
            duration = "No time limit";
        }
        durationView.setText(duration);

        TextView remainingView = (TextView) findViewById(R.id.sprint_time_remaining);
        String remaining = currentSprint.getTimeRemaining();
        remainingView.setText(remaining);
    }
}
