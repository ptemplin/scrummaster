package com.petertemplin.scrummaster.activity;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

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

        String taskTitle = savedInstanceState.getString(VIEWING_TASK_ID);
        taskTitle = taskTitle.substring(0, taskTitle.indexOf("-"));
        int taskId = 0;
        try {
            taskId = Integer.parseInt(taskTitle);
        } catch (Exception e) {
            taskId = 1;
        }
        task = DataUtils.getInstance(this).findTaskById(taskId);

        
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
