package com.example.proyecto_hibernate.util;

import com.example.proyecto_hibernate.classes.Alumnos;
import com.example.proyecto_hibernate.classes.Grupos;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    static SessionFactory factory = null;
    static {
        Configuration cfg = new Configuration();
        cfg.configure("util/hibernate.cfg.xml");

        cfg.addAnnotatedClass(Alumnos.class);
        cfg.addAnnotatedClass(Grupos.class);
        System.out.println("conectado hibernate");
        factory = cfg.buildSessionFactory();
    }
    public static SessionFactory getSessionFactory() {
        return factory;
    }

    public static Session getSession() {
        return factory.openSession();
    }
}