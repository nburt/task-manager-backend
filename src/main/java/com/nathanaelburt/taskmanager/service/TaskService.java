package com.nathanaelburt.taskmanager.service;

import java.util.List;
import com.nathanaelburt.taskmanager.dao.TaskDao;
import com.nathanaelburt.taskmanager.entity.Task;

public class TaskService {

    private static TaskDao taskDao;

    public TaskService() {
        taskDao = new TaskDao();
    }

    public void save(Task task) {
        taskDao.save(task);
    }

    public void update(Task task) {
        taskDao.update(task);
    }

    public Task findById(Long id) {
        Task task = taskDao.findById(id);
        return task;
    }

    public void delete(Long id) {
        Task task = taskDao.findById(id);
        taskDao.delete(task);
    }

    public List<Task> findAll() {
        List<Task> tasks = taskDao.findAll();
        return tasks;
    }

    public TaskDao taskDao() {
        return taskDao;
    }

}
