package com.example.proyectopartepablo.Dao;

import com.example.proyectopartepablo.utils.Alerta;
import com.example.proyectopartepablo.clases.Alumnos;

import com.example.proyectopartepablo.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class AlumnosDAO {
    SessionFactory factory = HibernateUtil.getSessionFactory();

    public ArrayList<Alumnos> getAlumnosHibernate(){
        Transaction transaction;
        ArrayList<Alumnos> listaAlumnos=new ArrayList<>();
        try(Session session = factory.openSession()){
            transaction = session.beginTransaction();
            List<Alumnos> alumnos =session.createQuery("from Alumnos", Alumnos.class).getResultList();
            listaAlumnos.addAll(alumnos);
            transaction.commit();
        }catch (Exception e){
            Alerta.alertaError(e.getMessage());
        }
        return listaAlumnos;
    }
}