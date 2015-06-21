package com.petertemplin.scrummaster.activity;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.petertemplin.scrummaster.R;
import com.petertemplin.scrummaster.data.DataUtils;
import com.petertemplin.scrummaster.models.Task;


public class ViewTaskActivity extends ActionBarActivity {

    public static final String VIEWING_TASK_ID = "taskId";

    public Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task);

        String taskTitle = getIntent().getStringExtra(VIEWING_TASK_ID);
        taskTitle = taskTitle.substring(0, taskTitle.indexOf("-"));
        int taskId = 0;
        try {
            taskId = Integer.parseInt(taskTitle);
        } catch (Exception e) {
            taskId = 1;
        }
        task = DataUtils.getInstance(this).findTaskById(taskId);

        // setup all of the fields of the activity
        TextView titleText = (TextView) findViewById(R.id.viewTaskTitle);
        titleText.setText(task.toString());

        TextView descText = (TextView) findViewById(R.id.viewTaskDescription);
        if (task.getDescription() == null) {
            descText.setText("No description");
        } else {
            descText.setText(task.getDescription());
        }

        TextView priorityText = (TextView) findViewById(R.id.viewTaskPriority);
        if (task.getPriority() == 0) {
            priorityText.setText("");
        } else {
            priorityText.setText("Priority: " + task.getPriority());
        }

        TextView estimatedText = (TextView) findViewById(R.id.viewTaskEstimate);
        if (task.getEstimatedTime() == null) {
            estimatedText.setText("Estimated time: 0");
        } else {
            estimatedText.setText("Estimated time: " + task.getEstimatedTime());
        }

        TextView pointsText = (TextView) findViewById(R.id.viewTaskPoints);
        if (task.getPoints() == 0) {
            pointsText.setText("No points");
        } else {
            pointsText.setText(task.getPoints());
        }

        TextView progressText = (TextView) findViewById(R.id.viewTaskProgress);
        if (task.getProgress() == null) {
            progressText.setText("Not started");
        } else {
            progressText.setText(task.getProgress());
        }

        TextView startedText = (TextView) findViewById(R.id.viewTaskStartDate);
        if (task.getStartedDate() == 0) {
            startedText.setText("");
        } else {
            startedText.setText(task.getStartedDate());
        }

        TextView completedText = (TextView) findViewById(R.id.viewTaskCompletedDate);
        if (task.getCompletedDate() == 0) {
            completedText.setText("");
        } else {
            completedText.setText(task.getCompletedDate());
        }

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_task, menu);
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
