package com.nathanaelburt.taskmanager;

import com.nathanaelburt.taskmanager.entity.Task;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.http.ResponseEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import java.util.List;

@CrossOrigin
@Controller
public class TaskController {

    @RequestMapping(value = "/tasks", produces = "application/json")
    public ResponseEntity index() {
        Configuration configuration = new Configuration();
        configuration = configuration.configure("hibernate.cfg.xml");
        configuration.setProperty("hibernate.connection.url", System.getenv("JDBC_DATABASE_URL"));
        configuration.setProperty("hibernate.connection.username", System.getenv("JDBC_USERNAME"));
        configuration.setProperty("hibernate.connection.password", System.getenv("JDBC_PASSWORD"));

        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<Task> tasks = session.createCriteria(Task.class).list();
        session.close();
        return ResponseEntity.ok(tasks);
    }

    @RequestMapping(value = "/tasks/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity showTask(@PathVariable(value = "id") Long id) {
        Configuration configuration = new Configuration();
        configuration = configuration.configure("hibernate.cfg.xml");
        configuration.setProperty("hibernate.connection.url", System.getenv("JDBC_DATABASE_URL"));
        configuration.setProperty("hibernate.connection.username", System.getenv("JDBC_USERNAME"));
        configuration.setProperty("hibernate.connection.password", System.getenv("JDBC_PASSWORD"));

        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Task task = (Task) session.get(Task.class, id);
        session.close();
        return ResponseEntity.ok(task);
    }

    @RequestMapping(value = "/tasks", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity createTask(@RequestBody Task task) {
        Configuration configuration = new Configuration();
        configuration = configuration.configure("hibernate.cfg.xml");
        configuration.setProperty("hibernate.connection.url", System.getenv("JDBC_DATABASE_URL"));
        configuration.setProperty("hibernate.connection.username", System.getenv("JDBC_USERNAME"));
        configuration.setProperty("hibernate.connection.password", System.getenv("JDBC_PASSWORD"));

        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(task);

        session.getTransaction().commit();
        session.close();
        return ResponseEntity.ok(task);
    }

    @RequestMapping(value ="/tasks/{id}", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity updateTask(@PathVariable(value = "id") Long id, @RequestBody Task updatedTask) {
        Configuration configuration = new Configuration();
        configuration = configuration.configure("hibernate.cfg.xml");
        configuration.setProperty("hibernate.connection.url", System.getenv("JDBC_DATABASE_URL"));
        configuration.setProperty("hibernate.connection.username", System.getenv("JDBC_USERNAME"));
        configuration.setProperty("hibernate.connection.password", System.getenv("JDBC_PASSWORD"));

        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Task task = (Task) session.get(Task.class, id);
        task.setDescription(updatedTask.getDescription());
        task.setName(updatedTask.getName());
        task.setCompleted(updatedTask.getCompleted());

        session.update(task);

        session.getTransaction().commit();
        session.close();
        return ResponseEntity.ok(task);
    }

}
