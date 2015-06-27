package com.petertemplin.scrummaster.activity;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.petertemplin.scrummaster.R;
import com.petertemplin.scrummaster.data.DataUtils;


public class HomeActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button addToBacklogButton = (Button) findViewById(R.id.addToBacklogButton);
        addToBacklogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, AddToBacklogActivity.class);
                startActivity(intent);
            }
        });
        Button viewBacklogButton = (Button) findViewById(R.id.viewBacklogButton);
        viewBacklogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ViewBacklogActivity.class);
                startActivity(intent);
            }
        });
        Button currentSprintButton = (Button) findViewById(R.id.currentSprintButton);
        currentSprintButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, SprintActivity.class);
                startActivity(intent);
            }
        });
        Button buildSprintButton = (Button) findViewById(R.id.buildSprintButton);
        buildSprintButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ViewBacklogActivity.class);
                intent.putExtra(ViewBacklogActivity.BUILDING_SPRINT, true);
                startActivity(intent);
            }
        });
        Button completedTasksButton = (Button) findViewById(R.id.completedTasksButton);
        completedTasksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, CompletedTasksActivity.class);
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
        }

        return super.onOptionsItemSelected(item);
    }

    public void resetDatabase() {
        DataUtils.getInstance(this).resetDatabase(this);
    }
}
