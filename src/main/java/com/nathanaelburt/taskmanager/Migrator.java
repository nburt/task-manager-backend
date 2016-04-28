package com.nathanaelburt.taskmanager;

import org.flywaydb.core.Flyway;

class Migrator {
    public static void main(String[] args) throws Exception {
        Flyway flyway = new Flyway();
        flyway.setDataSource(System.getenv("JDBC_DATABASE_URL"), System.getenv("JDBC_USERNAME"), System.getenv("JDBC_PASSWORD"));
        flyway.migrate();
    }
}