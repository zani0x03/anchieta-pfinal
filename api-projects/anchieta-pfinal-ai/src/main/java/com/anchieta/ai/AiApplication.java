package com.anchieta.ai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AiApplication {
    public static void main(String[] args) {
        SpringApplication.run(AiApplication.class, args);
        
        String version = AiApplication.class.getPackage().getImplementationVersion();
        if (version == null) version = "Development";
        
        System.out.println("===========================================");
        System.out.println("   AI API Started - Version: " + version);
        System.out.println("===========================================");
    }
}
