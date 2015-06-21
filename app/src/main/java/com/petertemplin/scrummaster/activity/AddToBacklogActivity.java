package com.petertemplin.scrummaster.activity;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.petertemplin.scrummaster.R;
import com.petertemplin.scrummaster.data.DataUtils;
import com.petertemplin.scrummaster.models.Task;


public class AddToBacklogActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_back_log);

        Button submitButton = (Button) findViewById(R.id.addToBacklogSubmit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });
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
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean validate() {

        EditText editPriority = (EditText) findViewById(R.id.editPriority);
        if (editPriority != null &&
                (editPriority.getText() == null || editPriority.getText().toString().equals(""))) {
            return false;
        }
        return true;
    }

    private void submit() {
        boolean validated = validate();
        if (!validated) {
            return;
        }
        EditText editName = (EditText) findViewById(R.id.editName);
        String name = editName.getText().toString();
        EditText editDesc = (EditText) findViewById(R.id.editDescription);
        String description = editDesc.getText().toString();
        EditText editPriority = (EditText) findViewById(R.id.editPriority);
        Integer priority = Integer.parseInt(editPriority.getText().toString());

        Task task = new Task(1, name);
        task.setDescription(description);
        task.setPriority(priority);
        DataUtils.getInstance(this).addTask(task);

        Intent intent = new Intent(this, ViewBacklogActivity.class);
        startActivity(intent);
    }
}
