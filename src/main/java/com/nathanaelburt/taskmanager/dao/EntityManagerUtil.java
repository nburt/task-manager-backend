package com.nathanaelburt.taskmanager.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Map;
import java.util.HashMap;

public class EntityManagerUtil {

    private static final EntityManagerFactory entityManagerFactory;
    static {
        try {
            Map<String, String> properties = new HashMap<String, String>();
            properties.put("javax.persistence.jdbc.url", System.getenv("JDBC_DATABASE_URL"));
            properties.put("javax.persistence.jdbc.user", System.getenv("JDBC_USERNAME"));
            properties.put("javax.persistence.jdbc.password", System.getenv("JDBC_PASSWORD"));
            entityManagerFactory = Persistence.createEntityManagerFactory("taskmanager", properties);
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();

    }
}
