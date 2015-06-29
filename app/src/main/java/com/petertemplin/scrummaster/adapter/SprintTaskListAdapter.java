package com.petertemplin.scrummaster.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
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
            ImageView priority = (ImageView) v.findViewById(R.id.priority);
            View progress = (View) v.findViewById(R.id.progress);

            if (title != null) {
                title.setText(task.getId()+" - "+task.getName());
            }
            if (priority != null) {
                int resId = 0;
                switch(task.getPriority()) {
                    case 0:
                        resId = R.drawable.priority_explosion_lowest;
                        break;
                    case 1:
                        resId = R.drawable.priority_explosion_low;
                        break;
                    case 2:
                        resId = R.drawable.priority_explosion_medium;
                        break;
                    case 3:
                        resId = R.drawable.priority_explosion_high;
                        break;
                    case 4:
                    case 5:
                        resId = R.drawable.priority_explosion_highest;
                        break;
                }
                if (resId != 0) {
                    priority.setBackgroundResource(resId);
                }
            }
            if (progress != null && task.getProgress() != null) {
                switch(task.getProgress()) {
                    case "Started":
                    case "Testing":
                        progress.setBackgroundColor(getContext().getResources().getColor(R.color.lightGreen));
                        break;
                    case "Complete":
                        progress.setBackgroundColor(getContext().getResources().getColor(R.color.scrumGreen));
                        break;
                    default:
                        progress.setBackgroundColor(0x00000000);
                }
            }
        }

        return v;
    }

}
