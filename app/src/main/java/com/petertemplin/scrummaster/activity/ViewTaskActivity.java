package com.petertemplin.scrummaster.activity;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.petertemplin.scrummaster.R;
import com.petertemplin.scrummaster.data.DataUtils;
import com.petertemplin.scrummaster.models.Task;
import com.petertemplin.scrummaster.util.DateUtils;


public class ViewTaskActivity extends ActionBarActivity {

    public static final String VIEWING_TASK_TITLE = "taskTitle";

    public Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task);

        String taskTitle = getIntent().getStringExtra(VIEWING_TASK_TITLE);
        int taskId = Task.getIdFromTitle(taskTitle);
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

        Spinner progress = (Spinner) findViewById(R.id.viewTaskProgress);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.task_progress_values, R.layout.spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        // Apply the adapter to the spinner
        progress.setAdapter(adapter);
        progress.setSelection(getPositionOfProgressByName());
        progress.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String choice = (String) parent.getItemAtPosition(position);
                task.setProgress(choice);
                saveTask();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // do nothing
            }
        });

        TextView startedText = (TextView) findViewById(R.id.viewTaskStartDate);
        if (task.getStartedDate() == null || task.getStartedDate() == DateUtils.EMPTY_DATE) {
            startedText.setText("");
        } else {
            startedText.setText(task.getStartedDate());
        }

        TextView completedText = (TextView) findViewById(R.id.viewTaskCompletedDate);
        if (task.getCompletedDate() == null || task.getStartedDate() == DateUtils.EMPTY_DATE) {
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

    private int getPositionOfProgressByName() {
        String progress = task.getProgress();
        if (progress == null || progress.equals("Back Burner")) {
            return 0;
        } else if (progress.equals("Not Started")){
            return 1;
        } else if (progress.equals("Started")) {
            return 2;
        } else if (progress.equals("Testing")) {
            return 3;
        } else if (progress.equals("Complete")) {
            return 4;
        } else {
            return 0;
        }
    }

    public void saveTask() {
        task.save(this);
    }
}
