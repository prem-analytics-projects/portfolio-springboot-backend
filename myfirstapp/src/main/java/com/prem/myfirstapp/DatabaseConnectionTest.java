package com.prem.myfirstapp;
import java.util.List;
import java.util.Map; // Stores data in key-value pairs.

import org.springframework.boot.CommandLineRunner; //Executes code when Spring Boot starts.
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate; //Helps execute SQL queries from Java.
import org.springframework.stereotype.Component; //Tells Spring to manage this class.


// An annotation provides additional information to Java or the Spring Framework.
// @Component tells Spring:
// "Please detect this class and manage its lifecycle."
// Spring will create an object of this class automatically.
// This is called a Spring Bean.

// In Java, implements is used when a class agrees to follow the rules defined by an interface.

@Component
public class DatabaseConnectionTest implements CommandLineRunner {

    private final JdbcTemplate jdbcTemplate;

    public DatabaseConnectionTest(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
// Spring Boot automatically calls run() after the application context has been initialized.
    @Override
    public void run(String... args) { //"I want to execute some code when the application starts."

        String sql = """
                SELECT
                    content_id,
                    profile_id,
                    section_title,
                    description,
                    display_order,
                    is_active,
                    created_at
                FROM raw_data.about_content
                ORDER BY display_order
                """;

        try {

            List<Map<String, Object>> records =
                    jdbcTemplate.queryForList(sql);

            System.out.println("Database query successful!");

            for (Map<String, Object> record : records) {
                System.out.println("--------------------------");
                System.out.println("Content ID: " + record.get("content_id"));
                System.out.println("Profile ID: " + record.get("profile_id"));
                System.out.println("Section Title: " + record.get("section_title"));
                System.out.println("Description: " + record.get("description"));
            }

            System.out.println("Total records: " + records.size());

        } catch (DataAccessException e) {

            System.out.println("Database query failed!");
            System.out.println("Error: " + e.getMessage());

        }
    
    }
}