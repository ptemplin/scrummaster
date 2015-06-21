package com.petertemplin.scrummaster.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Me on 2015-06-21.
 */
public class AbstractTaskList {

    List<Task> tasks = new ArrayList<>();

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public void addTask(Task task) {
        if (!tasks.contains(task)) {
            tasks.add(task);
        }
    }

    public void removeTask(int id) {
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getId() == id) {
                tasks.remove(tasks.get(i));
            }
        }
    }

    public void removeTask(Task task) {
        removeTask(task.getId());
    }

    public Task[] toArray() {
        Task[] taskArray = new Task[tasks.size()];
        tasks.toArray(taskArray);
        return taskArray;
    }
}
