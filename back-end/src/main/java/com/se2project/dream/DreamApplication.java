package com.se2project.dream;

import com.se2project.dream.entity.Farmer;
import com.se2project.dream.service.FarmerService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class DreamApplication {

    /**
     * Main method.
     *
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(DreamApplication.class, args);
    }


    //TODO JAVADOC
    @Bean
    BCryptPasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }




}
