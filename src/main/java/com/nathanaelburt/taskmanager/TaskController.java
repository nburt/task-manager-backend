package com.nathanaelburt.taskmanager;

import com.nathanaelburt.taskmanager.entity.Task;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import java.util.List;
import java.net.URI;
import java.net.URISyntaxException;

@Controller
public class TaskController {

    @RequestMapping(value = "/tasks", produces = "application/json")
    public ResponseEntity index() {
        Configuration configuration = new Configuration();
        configuration = configuration.configure("hibernate.cfg.xml");
        URI dbUri;
        try {
            dbUri = new URI(System.getenv("DATABASE_URL"));
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("");
        }

        String username = dbUri.getUserInfo().split(":")[0];
        String password = dbUri.getUserInfo().split(":", 2)[1];
        int port = dbUri.getPort();
        if (port <= 0) port = 5432;
        String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + port + dbUri.getPath();

        configuration.setProperty("hibernate.connection.url", dbUrl);
        configuration.setProperty("hibernate.connection.username", username);
        configuration.setProperty("hibernate.connection.password", password);

        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<Task> tasks = session.createCriteria(Task.class).list();
        session.close();
        return ResponseEntity.ok(tasks);
    }

    @RequestMapping(value = "/tasks/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity showTask(@PathVariable(value = "id") String id) {
        Configuration configuration = new Configuration();
        configuration = configuration.configure("hibernate.cfg.xml");
        URI dbUri;
        try {
            dbUri = new URI(System.getenv("DATABASE_URL"));
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("");
        }

        String username = dbUri.getUserInfo().split(":")[0];
        String password = dbUri.getUserInfo().split(":", 2)[1];
        int port = dbUri.getPort();
        if (port <= 0) port = 5432;
        String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + port + dbUri.getPath();

        configuration.setProperty("hibernate.connection.url", dbUrl);
        configuration.setProperty("hibernate.connection.username", username);
        configuration.setProperty("hibernate.connection.password", password);

        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Task task = (Task) session.get(Task.class, Long.parseLong(id));
        session.close();
        return ResponseEntity.ok(task);
    }

    @RequestMapping(value = "/tasks", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity createTask(@ModelAttribute Task task) {
        Configuration configuration = new Configuration();
        configuration = configuration.configure("hibernate.cfg.xml");
        URI dbUri;
        try {
            dbUri = new URI(System.getenv("DATABASE_URL"));
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("");
        }

        String username = dbUri.getUserInfo().split(":")[0];
        String password = dbUri.getUserInfo().split(":", 2)[1];
        int port = dbUri.getPort();
        if (port <= 0) port = 5432;
        String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + port + dbUri.getPath();

        configuration.setProperty("hibernate.connection.url", dbUrl);
        configuration.setProperty("hibernate.connection.username", username);
        configuration.setProperty("hibernate.connection.password", password);

        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.save(task);

        session.getTransaction().commit();
        session.close();
        return ResponseEntity.ok(task);
    }

}
