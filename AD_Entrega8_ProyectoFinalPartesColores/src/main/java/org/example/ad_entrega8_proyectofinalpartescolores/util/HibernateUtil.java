package org.example.ad_entrega8_proyectofinalpartescolores.util;

import org.example.ad_entrega8_proyectofinalpartescolores.Model.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    public static SessionFactory factory = null;

    static {
        try {
            Configuration cfg = new Configuration();
            cfg.configure("Configuration/hibernate.cfg.xml");
            cfg.addAnnotatedClass(Alumnos.class);
            cfg.addAnnotatedClass(Grupos.class);
            cfg.addAnnotatedClass(ParteIncidencia.class);
            cfg.addAnnotatedClass(Profesor.class);
            cfg.addAnnotatedClass(PuntuacionPartes.class);

            factory = cfg.buildSessionFactory();
        } catch (Throwable ex) {
            //si algo sale mal, lanzar una excepción
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return factory;
    }

    public static Session getSession() {
        return factory.openSession();
    }
}//class