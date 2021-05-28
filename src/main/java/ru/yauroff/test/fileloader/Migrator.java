package ru.yauroff.test.fileloader;

import org.flywaydb.core.Flyway;

class Migrator {
    public static void main(String[] args) throws Exception {
        Flyway flyway = new Flyway();
        if (System.getenv("JDBC_DATABASE_URL") != null &&
                System.getenv("JDBC_DATABASE_USER") != null &&
                System.getenv("JDBC_DATABASE_PASSWORD") != null) {
            flyway.setDataSource(System.getenv("JDBC_DATABASE_URL"), System.getenv("JDBC_DATABASE_USER"),
                    System.getenv("JDBC_DATABASE_PASSWORD"));
            flyway.migrate();
        }
    }
}
