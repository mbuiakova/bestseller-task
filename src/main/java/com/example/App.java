package com.example;

//import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
//public class App implements CommandLineRunner {
public class App {

//    @Autowired
//    private Flyway flyway;

    //@Override
    //public void run(String... args) {
//        flyway.migrate();
    //}

//    public static void main(String[] args) {
//        new SpringApplicationBuilder(App.class)
//                .web(WebApplicationType.NONE)
//                .run(args);
//    }

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
