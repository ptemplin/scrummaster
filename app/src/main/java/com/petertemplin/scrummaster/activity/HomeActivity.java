package com.petertemplin.scrummaster.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.petertemplin.scrummaster.R;
import com.petertemplin.scrummaster.adapter.SprintListAdapter;
import com.petertemplin.scrummaster.adapter.TaskListAdapter;
import com.petertemplin.scrummaster.data.DataUtils;
import com.petertemplin.scrummaster.models.Sprint;

import java.util.List;


public class HomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button addToBacklogButton = (Button) findViewById(R.id.addItemToBacklogButton);
        addToBacklogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, AddToBacklogActivity.class);
                startActivity(intent);
            }
        });
        RelativeLayout viewBacklog = (RelativeLayout) findViewById(R.id.backlogDetails);
        viewBacklog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, BacklogActivity.class);
                startActivity(intent);
            }
        });
        RelativeLayout buildSprintButton = (RelativeLayout) findViewById(R.id.buildSprintButton);
        buildSprintButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, BacklogActivity.class);
                intent.putExtra(BacklogActivity.BUILDING_SPRINT, true);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        TextView backlogCount = (TextView) findViewById(R.id.backlogItemsCount);
        String count = Integer.toString(DataUtils.getInstance(this).getSizeOfBacklog());
        backlogCount.setText(count);

        // reset the sprint list
        ListView sprintList = (ListView) findViewById(R.id.sprintList);
        List<Sprint> sprints = DataUtils.getInstance(this).getAllSprints();
        SprintListAdapter sprintAdapter = new SprintListAdapter(this, R.layout.task_list_item, sprints);
        sprintList.setAdapter(sprintAdapter);
        sprintList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(HomeActivity.this, SprintActivity.class);
                intent.putExtra(SprintActivity.SPRINT_ID,
                        ((Sprint)parent.getItemAtPosition(position)).getId());
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
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
        } else if (id == R.id.action_reset_database) {
            resetDatabase();
        } else if (id == R.id.action_search_tasks) {
            Intent intent = new Intent(HomeActivity.this, TaskListActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    public void resetDatabase() {
        DataUtils.getInstance(this).resetDatabase(this);
    }
}
