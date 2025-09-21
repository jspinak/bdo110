package com.bdo.automation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
@ComponentScan(basePackages = {"com.bdo.automation", "io.github.jspinak.brobot"})
public class BdoAutomationApplication {

    public static void main(String[] args) {
        log.info("Starting BdoAutomationApplication main method...");

        ConfigurableApplicationContext context = SpringApplication.run(BdoAutomationApplication.class, args);

        log.info("Spring context initialized, getting BdoAutomationRunner bean...");

        try {
            BdoAutomationRunner runner = context.getBean(BdoAutomationRunner.class);
            log.info("Got BdoAutomationRunner bean, executing run method...");
            runner.run(args);
        } catch (Exception e) {
            log.error("Error executing BdoAutomationRunner", e);
            context.close();
            System.exit(1);
        }
    }
}