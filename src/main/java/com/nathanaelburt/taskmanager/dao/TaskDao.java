package com.nathanaelburt.taskmanager.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import com.nathanaelburt.taskmanager.entity.Task;

public class TaskDao {

    private Session currentSession;

    private Transaction currentTransaction;

    public TaskDao() {
    }

    public Session openCurrentSession() {
        currentSession = getSessionFactory().openSession();
        return currentSession;
    }

    public Session openCurrentSessionwithTransaction() {
        currentSession = getSessionFactory().openSession();
        currentTransaction = currentSession.beginTransaction();
        return currentSession;
    }

    public void closeCurrentSession() {
        currentSession.close();
    }

    public void closeCurrentSessionwithTransaction() {
        currentTransaction.commit();
        currentSession.close();
    }

    private static SessionFactory getSessionFactory() {
        Configuration configuration = new Configuration();
        configuration.setProperty("hibernate.connection.url", System.getenv("JDBC_DATABASE_URL"));
        configuration.setProperty("hibernate.connection.username", System.getenv("JDBC_USERNAME"));
        configuration.setProperty("hibernate.connection.password", System.getenv("JDBC_PASSWORD"));
        SessionFactory sessionFactory = configuration.configure().buildSessionFactory();
        return sessionFactory;
    }

    public Session getCurrentSession() {
        return currentSession;
    }

    public void setCurrentSession(Session currentSession) {
        this.currentSession = currentSession;
    }

    public Transaction getCurrentTransaction() {
        return currentTransaction;
    }

    public void setCurrentTransaction(Transaction currentTransaction) {
        this.currentTransaction = currentTransaction;
    }

    public void save(Task entity) {
        getCurrentSession().save(entity);
    }

    public void update(Task entity) {
        getCurrentSession().update(entity);
    }

    public Task findById(Long id) {
        Task Task = (Task) getCurrentSession().get(Task.class, id);
        return Task;
    }

    public void delete(Task entity) {
        getCurrentSession().delete(entity);
    }

    @SuppressWarnings("unchecked")
    public List<Task> findAll() {
        List<Task> tasks = (List<Task>) getCurrentSession().createCriteria(Task.class).list();
        return tasks;
    }

}
