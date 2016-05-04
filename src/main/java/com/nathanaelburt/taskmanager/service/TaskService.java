package com.nathanaelburt.taskmanager.service;

import java.util.List;
import com.nathanaelburt.taskmanager.dao.TaskDao;
import com.nathanaelburt.taskmanager.entity.Task;

public class TaskService {

    private static TaskDao taskDao;

    public TaskService() {
        taskDao = new TaskDao();
    }

    public void save(Task entity) {
        taskDao.openCurrentSessionwithTransaction();
        taskDao.save(entity);
        taskDao.closeCurrentSessionwithTransaction();
    }

    public void update(Task entity) {
        taskDao.openCurrentSessionwithTransaction();
        taskDao.update(entity);
        taskDao.closeCurrentSessionwithTransaction();
    }

    public Task findById(Long id) {
        taskDao.openCurrentSession();
        Task task = taskDao.findById(id);
        taskDao.closeCurrentSession();
        return task;
    }

    public void delete(Long id) {
        taskDao.openCurrentSessionwithTransaction();
        Task task = taskDao.findById(id);
        taskDao.delete(task);
        taskDao.closeCurrentSessionwithTransaction();
    }

    public List<Task> findAll() {
        taskDao.openCurrentSession();
        List<Task> tasks = taskDao.findAll();
        taskDao.closeCurrentSession();
        return tasks;
    }

    public TaskDao taskDao() {
        return taskDao;
    }

}
