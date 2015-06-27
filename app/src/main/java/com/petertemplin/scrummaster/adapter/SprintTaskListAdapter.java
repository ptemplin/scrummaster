package com.petertemplin.scrummaster.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.petertemplin.scrummaster.R;
import com.petertemplin.scrummaster.models.Task;

import java.util.List;

/**
 * Created by Me on 2015-06-24.
 */
public class SprintTaskListAdapter extends ArrayAdapter<Task> {

    public SprintTaskListAdapter(Context context, int resourceId) {
        super(context,resourceId);
    }

    public SprintTaskListAdapter(Context context, int resourceId, List<Task> tasks) {
        super(context, resourceId, tasks);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            v = inflater.inflate(R.layout.sprint_task_list_item, null);
        }

        Task task = getItem(position);

        if (task != null) {
            TextView title = (TextView) v.findViewById(R.id.name);
            TextView priority = (TextView) v.findViewById(R.id.priority);
            View progress = (View) v.findViewById(R.id.progress);

            if (title != null) {
                title.setText(task.getId()+"-"+task.getName());
            }
            if (priority != null) {
                priority.setText("Priority: " + task.getPriority());
            }
            if (progress != null && task.getProgress() != null) {
                switch(task.getProgress()) {
                    case "Back Burner":
                    case "Not Started":
                        progress.setBackgroundColor(0xff0000ff);
                        break;
                    case "Started":
                        progress.setBackgroundColor(0xffbb8833);
                        break;
                    case "Testing":
                        progress.setBackgroundColor(0xffaa0066);
                        break;
                    case "Complete":
                        progress.setBackgroundColor(0xff00ff00);
                        break;
                    default:
                        progress.setBackgroundColor(0x00000000);
                }
            }
        }

        return v;
    }

}
