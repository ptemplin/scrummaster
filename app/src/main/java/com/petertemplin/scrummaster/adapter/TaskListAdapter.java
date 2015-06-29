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
public class TaskListAdapter extends ArrayAdapter<Task> {

    public TaskListAdapter(Context context, int resourceId) {
        super(context,resourceId);
    }

    public TaskListAdapter(Context context, int resourceId, List<Task> tasks) {
        super(context, resourceId, tasks);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            v = inflater.inflate(R.layout.task_list_item, null);
        }

        Task task = getItem(position);

        if (task != null) {
            TextView title = (TextView) v.findViewById(R.id.name);
            TextView priority = (TextView) v.findViewById(R.id.priority);
            if (title != null) {
                title.setText(task.getId()+" - "+task.getName());
            }
            if (priority != null) {
                priority.setText(Integer.toString(task.getPriority()));
            }
        }

        return v;
    }

}
