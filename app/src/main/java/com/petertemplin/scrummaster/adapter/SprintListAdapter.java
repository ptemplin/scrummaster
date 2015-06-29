package com.petertemplin.scrummaster.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.petertemplin.scrummaster.R;
import com.petertemplin.scrummaster.models.Sprint;
import com.petertemplin.scrummaster.models.Task;

import java.util.List;

/**
 * Created by Me on 2015-06-24.
 */
public class SprintListAdapter extends ArrayAdapter<Sprint> {

    public SprintListAdapter(Context context, int resourceId) {
        super(context,resourceId);
    }

    public SprintListAdapter(Context context, int resourceId, List<Sprint> sprints) {
        super(context, resourceId, sprints);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            v = inflater.inflate(R.layout.sprint_list_item, null);
        }

        Sprint sprint = getItem(position);

        if (sprint != null) {
            TextView count = (TextView) v.findViewById(R.id.itemsCount);
            TextView title = (TextView) v.findViewById(R.id.name);
            TextView remainingView = (TextView) v.findViewById(R.id.timeRemaining);
            if (count != null) {
                count.setText(Integer.toString(sprint.findSizeLazy(getContext())));
            }
            if (title != null) {
                title.setText(sprint.getName());
            }
            if (remainingView != null) {
                remainingView.setText(sprint.getTimeRemaining());
            }
        }

        return v;
    }

}
