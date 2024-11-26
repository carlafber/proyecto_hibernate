package com.example.proyectopartepablo.utils;

import com.example.proyectopartepablo.clases.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    static SessionFactory factory = null;
    static {
        Configuration cfg = new Configuration();
        cfg.configure("configuration/hibernate.cfg.xml");

        cfg.addAnnotatedClass(Alumnos.class);
        cfg.addAnnotatedClass(Grupos.class);
        cfg.addAnnotatedClass(ParteIncidencia.class);
        cfg.addAnnotatedClass(Profesor.class);
        cfg.addAnnotatedClass(PuntuacionPartes.class);
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