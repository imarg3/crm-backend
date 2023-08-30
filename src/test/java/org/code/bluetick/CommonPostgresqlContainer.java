/*
package org.code.bluetick;

import org.testcontainers.containers.PostgreSQLContainer;

public class CommonPostgresqlContainer extends PostgreSQLContainer<CommonPostgresqlContainer> {
    private static final String IMAGE_VERSION = "postgres:12.9";
    private static CommonPostgresqlContainer container;
    private CommonPostgresqlContainer() {
        super(IMAGE_VERSION);
    }

    public static CommonPostgresqlContainer getInstance() {
        if (container == null) {
            container = new CommonPostgresqlContainer().withInitScript("init_db.sql").withReuse(true);
        }
        return container;
    }

    @Override
    public void start() {
        super.start();
        System.setProperty("spring.datasource.url", container.getJdbcUrl());
        System.setProperty("spring.datasource.username", container.getUsername());
        System.setProperty("spring.datasource.password", container.getPassword());
    }

    @Override
    public void stop() {
        //do nothing, JVM handles shut down
    }
}
*/