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
import com.petertemplin.scrummaster.models.Backlog;
import com.petertemplin.scrummaster.models.Sprint;
import com.petertemplin.scrummaster.models.Task;

import java.util.ArrayList;
import java.util.List;


public class ViewBacklogActivity extends ActionBarActivity {

    public static final String BUILDING_SPRINT = "buildingSprint";

    Boolean buildSprintActionMode = false;

    Sprint sprint;
    Backlog backlog;

    Button saveSprintButton;
    ListView backlogTaskList;
    ListView sprintTaskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_backlog);

        // setup models
        backlog = new Backlog();
        sprint = new Sprint();

        // Setup the list of tasks
        backlogTaskList = (ListView) findViewById(R.id.backlogTaskList);
        DataUtils manager = DataUtils.getInstance(this);

        backlog.setTasks(manager.getTasksFromBacklog());
        ArrayAdapter<Task> adapter = new ArrayAdapter<>(this, R.layout.task_list_item, backlog.toArray());
        backlogTaskList.setAdapter(adapter);
        backlogTaskList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startTaskDetails(view);
            }
        });

        // retrieve the building sprint state
        if (savedInstanceState != null) {
            buildSprintActionMode = savedInstanceState.getBoolean(BUILDING_SPRINT);
        }
        if (getIntent().getBooleanExtra(BUILDING_SPRINT, false)) {
            buildSprintActionMode = true;
        }

        if(buildSprintActionMode != null && buildSprintActionMode) {
            onStartBuildingSprint();
        }

    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        bundle.putBoolean(BUILDING_SPRINT, buildSprintActionMode);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_backlog, menu);
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
        } else if (id == R.id.createNewSprintAction) {
            onStartBuildingSprint();
        }

        return super.onOptionsItemSelected(item);
    }

    public void onStartBuildingSprint() {
        buildSprintActionMode = true;

        // make the views visible
        sprintTaskList = (ListView) findViewById(R.id.sprintTaskList);
        sprintTaskList.setVisibility(View.VISIBLE);
        saveSprintButton = (Button) findViewById(R.id.buildSprintSaveButton);
        saveSprintButton.setVisibility(View.VISIBLE);
        saveSprintButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFinishBuildingSprint();
            }
        });

        // for testing, use all backlog tasks
        ArrayAdapter<Task> adapter = new ArrayAdapter<>(this, R.layout.sprint_task_list_item, sprint.toArray());
        sprintTaskList.setAdapter(adapter);
        sprintTaskList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onSprintItemRemoved(((TextView) view).getText().toString());
            }
        });
        sprintTaskList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                startTaskDetails(view);
                return true;
            }
        });

        // set the backloglist's onClick and onLongClick
        backlogTaskList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onSprintItemAdded(((TextView) view).getText().toString());
            }
        });
        backlogTaskList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                startTaskDetails(view);
                return true;
            }
        });
    }

    public void onFinishBuildingSprint() {
        buildSprintActionMode = false;

        // hide the listview and save button
        if (sprintTaskList != null) {
            sprintTaskList.setVisibility(View.GONE);
        }
        if (saveSprintButton != null) {
            saveSprintButton.setVisibility(View.GONE);
        }

        saveSprint();

        sprint = new Sprint();

        // reset the backlog's onClickListener
        backlogTaskList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startTaskDetails(view);
            }
        });
    }

    public void onSprintItemAdded(String title) {
        int taskId = Task.getIdFromTitle(title);
        Task task = DataUtils.getInstance(ViewBacklogActivity.this).findTaskById(taskId);
        sprint.addTask(task);
        backlog.removeTask(task);
        updateListViews();
    }

    public void onSprintItemRemoved(String title) {
        int taskId = Task.getIdFromTitle(title);
        Task task = DataUtils.getInstance(ViewBacklogActivity.this).findTaskById(taskId);
        sprint.removeTask(task);
        backlog.addTask(task);
        updateListViews();
    }

    public void updateListViews() {
        ArrayAdapter<Task> sprintAdapter = new ArrayAdapter<>(this, R.layout.sprint_task_list_item, sprint.toArray());
        sprintTaskList.setAdapter(sprintAdapter);
        ArrayAdapter<Task> backlogAdapter = new ArrayAdapter<>(this, R.layout.task_list_item, backlog.toArray());
        backlogTaskList.setAdapter(backlogAdapter);
    }

    public void startTaskDetails(View view) {
        Intent intent = new Intent(ViewBacklogActivity.this, ViewTaskActivity.class);
        intent.putExtra(ViewTaskActivity.VIEWING_TASK_TITLE, ((TextView)view).getText().toString());
        startActivity(intent);
    }

    public void saveSprint() {
        DataUtils.getInstance(this).addNewSprint(sprint);
    }
}
