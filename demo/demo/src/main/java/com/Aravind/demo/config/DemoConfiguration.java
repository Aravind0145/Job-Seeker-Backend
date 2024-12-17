package com.Aravind.demo.config;


import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@org.springframework.context.annotation.Configuration
public class DemoConfiguration {





    public SessionFactory sessionFactory() {
        return new Configuration().configure().buildSessionFactory();

    }
}
