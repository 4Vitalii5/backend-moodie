package org.cyberrealm.tech.muvio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class MuvioApplication {

    public static void main(String[] args) {
        SpringApplication.run(MuvioApplication.class, args);
    }

}
