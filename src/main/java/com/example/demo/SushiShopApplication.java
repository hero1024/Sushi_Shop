package com.example.demo;

import com.example.demo.service.ChefExecuteService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author songpeijiang
 */
@EnableAsync(proxyTargetClass = true)
@SpringBootApplication
public class SushiShopApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(SushiShopApplication.class, args);
        configurableApplicationContext.getBean(ChefExecuteService.class).worker();
    }

}
