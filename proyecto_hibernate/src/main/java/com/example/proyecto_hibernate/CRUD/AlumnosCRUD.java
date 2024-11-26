package com.example.proyecto_hibernate.CRUD;


import com.example.proyecto_hibernate.util.Alerta;
import com.example.proyecto_hibernate.classes.Alumnos;
import com.example.proyecto_hibernate.util.HibernateUtil;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class AlumnosCRUD {
    SessionFactory factory = HibernateUtil.getSessionFactory();

    public ArrayList<Alumnos> getAlumnos(){
        Transaction transaction;
        ArrayList<Alumnos> listaAlumnos=new ArrayList<>();
        try(Session session = factory.openSession()){
            transaction = session.beginTransaction();
            List<Alumnos> alumnos =session.createQuery("from Alumnos", Alumnos.class).getResultList();
            listaAlumnos.addAll(alumnos);
            transaction.commit();
        }catch (Exception e){
            Alerta.mensajeError(null, e.getMessage());
        }
        return listaAlumnos;
    }
}