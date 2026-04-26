package com.anchieta.login;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LoginApplication {

    public static void main(String[] args) {
        System.setProperty("java.net.preferIPv4Stack" , "true");
        SpringApplication.run(LoginApplication.class, args);
        
        String version = LoginApplication.class.getPackage().getImplementationVersion();
        if (version == null) version = "Development";
        
        System.out.println("===========================================");
        System.out.println("   Login API Started - Version: " + version);
        System.out.println("===========================================");
    }
}
