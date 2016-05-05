package com.nathanaelburt.taskmanager.dao;

import java.util.List;
import java.util.ArrayList;
import javax.persistence.EntityManager;
import com.nathanaelburt.taskmanager.entity.Task;

public class TaskDao {

    public TaskDao() {
    }

    public void save(Task task) {
        EntityManager entityManager;

        try {
            entityManager = EntityManagerUtil.getEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(task);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(Task task) {
        EntityManager entityManager;

        try {
            entityManager = EntityManagerUtil.getEntityManager();
            entityManager.getTransaction().begin();
            entityManager.merge(task);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Task findById(Long id) {
        EntityManager entityManager;
        Task task = null;

        try {
            entityManager = EntityManagerUtil.getEntityManager();
            task = entityManager.find(Task.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return task;
    }

    public void delete(Task task) {
        EntityManager entityManager;

        try {
            entityManager = EntityManagerUtil.getEntityManager();
            entityManager.getTransaction().begin();
            entityManager.remove(task);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public List<Task> findAll() {
        EntityManager entityManager;
        List<Task> tasks = new ArrayList();

        try {
            entityManager= EntityManagerUtil.getEntityManager();
            entityManager.getTransaction().begin();
            tasks = entityManager.createQuery("Select t from Task t").getResultList();
            entityManager.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
        }

        return tasks;
    }

}
