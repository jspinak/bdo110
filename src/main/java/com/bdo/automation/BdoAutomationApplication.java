package com.bdo.automation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.bdo.automation", "io.github.jspinak.brobot"})
public class BdoAutomationApplication {

    public static void main(String[] args) {
        SpringApplication.run(BdoAutomationApplication.class, args);
    }
}