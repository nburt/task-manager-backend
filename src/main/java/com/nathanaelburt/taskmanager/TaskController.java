package com.nathanaelburt.taskmanager;

import com.nathanaelburt.taskmanager.entity.Task;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.ResponseEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import java.util.List;


@Controller
public class TaskController {

    @RequestMapping(value = "/tasks", produces = "application/json")
    public ResponseEntity<List<Task>> index() {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<Task> tasks = session.createCriteria(Task.class).list();
        session.close();
        return ResponseEntity.ok(tasks);
    }

    @RequestMapping(value = "/tasks/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Task> showTask(@PathVariable(value="id") String id) {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Task task = (Task) session.get(Task.class, Long.parseLong(id));
        session.close();
        return ResponseEntity.ok(task);
    }

    @RequestMapping(value = "/tasks", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Task> createTask(@ModelAttribute Task task) {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.save(task);

        session.getTransaction().commit();
        session.close();
        return ResponseEntity.ok(task);
    }

}
