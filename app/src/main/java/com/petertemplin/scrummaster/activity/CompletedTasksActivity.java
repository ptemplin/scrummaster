package com.petertemplin.scrummaster.activity;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.petertemplin.scrummaster.R;
import com.petertemplin.scrummaster.adapter.SprintTaskListAdapter;
import com.petertemplin.scrummaster.adapter.TaskListAdapter;
import com.petertemplin.scrummaster.data.DataUtils;
import com.petertemplin.scrummaster.models.Task;

import java.util.List;

public class CompletedTasksActivity extends ActionBarActivity {

    List<Task> completedTasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_tasks);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Setup the list of tasks
        DataUtils manager = DataUtils.getInstance(this);
        completedTasks = manager.getCompletedTasks();
        SprintTaskListAdapter adapter = new SprintTaskListAdapter(this,
                R.layout.sprint_task_list_item, completedTasks);
        ListView tasks = (ListView) findViewById(R.id.completedTaskList);
        tasks.setAdapter(adapter);
        tasks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startTaskDetails(view);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_completed_tasks, menu);
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
        TextView text = (TextView) view.findViewById(R.id.name);
        Intent intent = new Intent(CompletedTasksActivity.this, ViewTaskActivity.class);
        intent.putExtra(ViewTaskActivity.VIEWING_TASK_TITLE, text.getText().toString());
        startActivity(intent);
    }
}
