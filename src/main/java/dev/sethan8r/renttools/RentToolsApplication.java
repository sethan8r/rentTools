package dev.sethan8r.renttools;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class RentToolsApplication {

    public static void main(String[] args) {
        SpringApplication.run(RentToolsApplication.class, args);
    }

}
