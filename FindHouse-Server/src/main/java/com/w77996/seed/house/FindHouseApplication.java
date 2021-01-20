package com.w77996.seed.house;

//import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author w77996
 */
@SpringBootApplication
@EnableTransactionManagement
@EnableAsync
@EnableScheduling
//@EnableAdminServer
public class FindHouseApplication {

    public static void main(String[] args) {
        SpringApplication.run(FindHouseApplication.class, args);
    }

}
