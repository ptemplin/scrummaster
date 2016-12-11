package com.petertemplin.scrummaster.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.petertemplin.scrummaster.R;
import com.petertemplin.scrummaster.adapter.TaskListAdapter;
import com.petertemplin.scrummaster.data.DataUtils;
import com.petertemplin.scrummaster.models.Task;
import com.petertemplin.scrummaster.util.DateUtils;

import java.util.List;

public class TaskListActivity extends Activity {

    List<Task> tasks;
    String progressChoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);

        progressChoice = "Back Burner";

        Spinner progress = (Spinner) findViewById(R.id.progressSearch);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.task_progress_values, R.layout.spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        // Apply the adapter to the spinner
        progress.setAdapter(adapter);
        progress.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String choice = (String) parent.getItemAtPosition(position);
                onSearchChanged(choice);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // do nothing
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_task_list, menu);
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
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    public void startTaskDetails(View view) {
        TextView text = (TextView) view.findViewById(R.id.name);
        Intent intent = new Intent(TaskListActivity.this, ViewTaskActivity.class);
        intent.putExtra(ViewTaskActivity.VIEWING_TASK_TITLE, text.getText().toString());
        startActivity(intent);
    }

    public void onSearchChanged(String choice) {
        progressChoice = choice;
        updateList();
    }

    public void updateList() {
        // Setup the list of tasks
        DataUtils manager = DataUtils.getInstance(this);
        tasks = manager.getTasksByProgress(progressChoice);
        TaskListAdapter adapter = new TaskListAdapter(TaskListActivity.this,
                R.layout.task_list_item, tasks);
        ListView tasksView = (ListView) findViewById(R.id.completedTaskList);
        tasksView.setAdapter(adapter);
        tasksView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startTaskDetails(view);
            }
        });
    }

}