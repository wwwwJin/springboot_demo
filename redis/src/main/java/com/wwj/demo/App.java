package com.wwj.demo;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;



@SpringBootApplication
public class App {
    public static void main(String[] args) {
        ApplicationContext c = SpringApplication.run(App.class, args);
    }
}
