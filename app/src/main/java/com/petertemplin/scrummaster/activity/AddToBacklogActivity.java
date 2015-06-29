package com.petertemplin.scrummaster.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

import com.petertemplin.scrummaster.R;
import com.petertemplin.scrummaster.data.DataUtils;
import com.petertemplin.scrummaster.models.Task;
import com.petertemplin.scrummaster.util.StringUtils;

import java.text.NumberFormat;


public class AddToBacklogActivity extends Activity {

    String name;
    String description;
    Integer priority;
    String estimate;
    Integer points;

    SeekBar pointsBar;
    SeekBar priorityBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_back_log);

        pointsBar = (SeekBar) findViewById(R.id.taskPointsSeekbar);
        pointsBar.setMax(Task.POINTS_INTERVALS);
        pointsBar.setProgress(Task.DEFAULT_POINTS);

        priorityBar = (SeekBar) findViewById(R.id.taskPrioritySeekBar);
        priorityBar.setMax(Task.MAX_PRIORITY - 1);
        priorityBar.setProgress(Task.DEFAULT_PRIORITY);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_to_back_log, menu);
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
        } else if (id == R.id.action_confirm) {
            submit();
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean validate() {

        EditText editName = (EditText) findViewById(R.id.editName);
        name = editName.getText().toString();
        if (name == null || StringUtils.isEmpty(name)) {
            Toast toast = Toast.makeText(this, "Task must be named", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, -100);
            toast.show();
            return false;
        }

        priority = priorityBar.getProgress();

        EditText editDesc = (EditText) findViewById(R.id.editDescription);
        description = editDesc.getText().toString();
        if (description == null || StringUtils.isEmpty(description)) {
            description = Task.DEFAULT_DESCRIPTION;
        }

        EditText editEstimate = (EditText) findViewById(R.id.editEstimatedTime);
        estimate = editEstimate.getText().toString();
        if (editEstimate == null || StringUtils.isEmpty(estimate)) {
            estimate = Task.DEFAULT_ESTIMATE;
        }

        points = pointsBar.getProgress();
        switch(points) {
            case 4:
                points = 5;
                break;
            case 5:
                points = 8;
                break;
            case 6:
                points = 13;
                break;
            case 7:
                points = 21;
                break;
        }


        return true;
    }

    private void submit() {
        boolean validated = validate();
        if (!validated) {
            return;
        }

        Task task = new Task(1, name);
        task.setDescription(description);
        task.setPriority(priority);
        task.setEstimatedTime(estimate);
        task.setPoints(points);
        DataUtils.getInstance(this).addTask(task);

        Intent intent = new Intent(this, BacklogActivity.class);
        startActivity(intent);
    }
}
