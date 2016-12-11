package com.petertemplin.scrummaster.activity;

import android.app.Activity;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.petertemplin.scrummaster.R;
import com.petertemplin.scrummaster.adapter.TaskListAdapter;
import com.petertemplin.scrummaster.data.DataUtils;
import com.petertemplin.scrummaster.models.Backlog;
import com.petertemplin.scrummaster.models.Sprint;
import com.petertemplin.scrummaster.models.Task;
import com.petertemplin.scrummaster.util.StringUtils;


public class BacklogActivity extends Activity {

    public static final String BUILDING_SPRINT = "buildingSprint";

    Boolean buildSprintActionMode = false;

    Sprint sprint;
    Backlog backlog;

    Button newTaskButton;
    Button storeSprintButton;
    Button startSprintButton;
    Button cancelSprintButton;
    ListView backlogTaskList;
    ListView sprintTaskList;

    String newSprintTitle;
    String newSprintDescription;
    String newSprintWeeks;
    String newSprintDays;
    String newSprintHours;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backlog);

        // setup models
        backlog = new Backlog();
        sprint = new Sprint();

        //handle the new item button
        newTaskButton = (Button) findViewById(R.id.addToBacklogButton);
        newTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BacklogActivity.this, AddToBacklogActivity.class);
                startActivity(intent);
            }
        });

        // retrieve the building sprint state
        if (savedInstanceState != null) {
            buildSprintActionMode = savedInstanceState.getBoolean(BUILDING_SPRINT);
        }
        if (getIntent().getBooleanExtra(BUILDING_SPRINT, false)) {
            buildSprintActionMode = true;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        // Setup the list of tasks
        backlogTaskList = (ListView) findViewById(R.id.backlogTaskList);
        DataUtils manager = DataUtils.getInstance(this);

        backlog.setTasks(manager.getTasksFromBacklog());

        updateListViews();

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
        getMenuInflater().inflate(R.menu.menu_backlog, menu);
        setActionBarTitle();
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
        } else if (id == R.id.createNewSprintAction) {
            onStartBuildingSprint();
        }

        return super.onOptionsItemSelected(item);
    }

    public void onStartBuildingSprint() {
        buildSprintActionMode = true;
        newTaskButton.setVisibility(View.INVISIBLE);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(getLayoutInflater().inflate(R.layout.create_sprint_dialog, null))
                .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        newSprintTitle = ((EditText) (((AlertDialog) dialog).findViewById(R.id.createSprintTitle)))
                                .getText().toString();
                        newSprintDescription = ((EditText) (((AlertDialog) dialog).findViewById(R.id.createSprintDescription)))
                                .getText().toString();
                        newSprintWeeks = ((EditText) (((AlertDialog) dialog).findViewById(R.id.createSprintDurationWeeks)))
                                .getText().toString();
                        newSprintDays = ((EditText) (((AlertDialog) dialog).findViewById(R.id.createSprintDurationDays)))
                                .getText().toString();
                        newSprintHours = ((EditText) (((AlertDialog) dialog).findViewById(R.id.createSprintDurationHours)))
                                .getText().toString();
                        if (validateSprintCreation()) {
                            sprint = new Sprint();
                            sprint.setName(newSprintTitle);
                            sprint.setDescription(newSprintDescription);
                            sprint.setDuration(newSprintWeeks + " " + newSprintDays +
                                    " " + newSprintHours);
                            buildSprintActionMode = true;
                            setActionBarTitle();
                            dialog.dismiss();
                        }
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onSprintClosed();
                dialog.cancel();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                onSprintClosed();
            }
        });
        dialog.show();

        updateListViews();
    }

    public void onFinishBuildingSprint(boolean started) {
        if (started) {
            sprint.start();
        }
        saveSprint();
        onSprintClosed();

        Intent intent = new Intent(BacklogActivity.this, SprintActivity.class);
        startActivity(intent);
    }

    public void onSprintItemAdded(String title) {
        int taskId = Task.getIdFromTitle(title);
        Task task = DataUtils.getInstance(BacklogActivity.this).findTaskById(taskId);
        sprint.addTask(task);
        backlog.removeTask(task);
        updateListViews();
    }

    public void onSprintItemRemoved(String title) {
        int taskId = Task.getIdFromTitle(title);
        Task task = DataUtils.getInstance(BacklogActivity.this).findTaskById(taskId);
        sprint.removeTask(task);
        backlog.addTask(task);
        updateListViews();
    }

    public void onSprintCancelled() {
        for (Task task : sprint.getTasks()) {
            backlog.addTask(task);
        }
        onSprintClosed();
    }

    public void onSprintClosed() {
        buildSprintActionMode = false;

        sprint = new Sprint();

        newTaskButton.setVisibility(View.VISIBLE);

        // reset the backlog's onClickListener
        updateListViews();

        setActionBarTitle();
    }

    public boolean validateSprintCreation() {
        if (newSprintTitle == null || StringUtils.isEmpty(newSprintTitle)) {
            newSprintTitle = Sprint.DEFAULT_NAME;
        }
        if (newSprintDescription == null || StringUtils.isEmpty(newSprintDescription)) {
            newSprintDescription = Sprint.DEFAULT_DESC;
        }
        newSprintWeeks = StringUtils.validateInt(newSprintWeeks, "0");
        newSprintDays = StringUtils.validateInt(newSprintDays, "0");
        newSprintHours = StringUtils.validateInt(newSprintHours, "0");

        return true;
    }

    public void updateListViews() {
        updateSprintListView();
        updateBacklogListView();
    }

    public void updateSprintListView() {
        if (buildSprintActionMode) {
            // make the views visible
            sprintTaskList = (ListView) findViewById(R.id.sprintTaskList);
            sprintTaskList.setVisibility(View.VISIBLE);
            LinearLayout buttons = (LinearLayout) findViewById(R.id.sprintButtons);
            buttons.setVisibility(View.VISIBLE);
            // set the button's onClicks
            storeSprintButton = (Button) findViewById(R.id.buildSprintStoreButton);
            storeSprintButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onFinishBuildingSprint(false);
                }
            });
            startSprintButton = (Button) findViewById(R.id.buildSprintStartButton);
            startSprintButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onFinishBuildingSprint(true);
                }
            });
            cancelSprintButton = (Button) findViewById(R.id.buildSprintCancelButton);
            cancelSprintButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onSprintCancelled();
                }
            });

            TaskListAdapter sprintAdapter = new TaskListAdapter(this, R.layout.task_list_item, sprint.getTasks());
            sprintTaskList.setAdapter(sprintAdapter);
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
        } else {
            // hide the listview and save button
            if (sprintTaskList != null) {
                sprintTaskList.setVisibility(View.GONE);
            }
            LinearLayout buttons = (LinearLayout) findViewById(R.id.sprintButtons);
            if (buttons != null) {
                buttons.setVisibility(View.GONE);
            }
        }
    }

    public void updateBacklogListView() {
        TaskListAdapter backlogAdapter = new TaskListAdapter(this, R.layout.task_list_item, backlog.getTasks());
        backlogTaskList.setAdapter(backlogAdapter);
        if (buildSprintActionMode) {
            // set the backloglist's onClick and onLongClick
            backlogTaskList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    onSprintItemAdded(((TextView) view.findViewById(R.id.name)).getText().toString());
                }
            });
            backlogTaskList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    startTaskDetails(view);
                    return true;
                }
            });
        } else {
            backlogTaskList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    startTaskDetails(view);
                }
            });
            backlogTaskList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    removeTask((Task)parent.getItemAtPosition(position));
                    return true;
                }
            });
        }
    }

    public void setActionBarTitle() {
        ActionBar actionBar = BacklogActivity.this.getActionBar();
        if (actionBar == null) {
            return;
        }

        if (buildSprintActionMode && newSprintTitle != null) {
            actionBar.setTitle(newSprintTitle);
        } else {
            actionBar.setTitle(R.string.title_activity_backlog);
        }
    }

    public void startTaskDetails(View view) {
        TextView text = (TextView) view.findViewById(R.id.name);
        Intent intent = new Intent(BacklogActivity.this, ViewTaskActivity.class);
        intent.putExtra(ViewTaskActivity.VIEWING_TASK_TITLE, text.getText().toString());
        startActivity(intent);
    }

    public void saveSprint() {
        DataUtils.getInstance(this).addNewSprint(sprint);
    }

    public void removeTask(Task task) {
        DataUtils.getInstance(this).removeTask(task.getId());
        backlog.removeTask(task.getId());
        updateBacklogListView();
    }
}
