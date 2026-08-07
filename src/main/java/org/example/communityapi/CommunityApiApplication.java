package org.example.communityapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CommunityApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommunityApiApplication.class, args);
    }

}
