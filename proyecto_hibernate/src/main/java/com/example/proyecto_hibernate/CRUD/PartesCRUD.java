package com.example.proyecto_hibernate.CRUD;

import com.example.proyecto_hibernate.classes.PartesIncidencia;
import com.example.proyecto_hibernate.util.Alerta;
import com.example.proyecto_hibernate.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

//Esta clase contiene métodos crud para gestionar los partes de incidencia
public class PartesCRUD {
    SessionFactory factory = HibernateUtil.getSessionFactory();

    //método para obtener una lista de todos los partes de incidencia de la BD
    public ArrayList<PartesIncidencia> obtenerPartes() {
        Session session = factory.openSession();

        Transaction transaction = null;

        //lista para almacenar los partes de incidencia obtenidos almacenados en la BD
        ArrayList<PartesIncidencia> listaPartes = new ArrayList<>();

        try {
            session.beginTransaction();

            transaction = session.beginTransaction();

            //consulta para obtener todos los partes de incidencia de la BD --> se guardan en una lista
            List<PartesIncidencia> partesIncidencias = session.createQuery("from PartesIncidencia", PartesIncidencia.class).getResultList();

            //se añaden todos los partes obtenidos de la consulta en la lista
            listaPartes.addAll(partesIncidencias);

            transaction.commit();
        } catch (Exception e) {
            Alerta.mensajeError(null, e.getMessage());
            e.printStackTrace();
        }
        return listaPartes;
    }//obtenerPartes


    //método para crear un nuevo parte de incidencia en la BD
    public void crearParte(PartesIncidencia partesIncidencia) {
        Session session = factory.openSession();

        Transaction transaction = null;

        try {
            session.beginTransaction();

            transaction = session.beginTransaction();

            //guardar el nuevo parte en la BD
            session.save(partesIncidencia);

            transaction.commit();
        } catch (Exception e) {
            Alerta.mensajeError(null, e.getMessage());
        }//try-catch
    } //crearParte



    //método para obtener los partes de incidencia asociados a un alumno, a través de su id
    public List<PartesIncidencia> obtenerPartesAlumno(int id_alumno) {
        Session session = factory.openSession();

        Transaction transaction = null;

        //lista vacía para almacenar los partes de un alumno obtenidos de la BD
        List listaPartes = new ArrayList<>();

        //bloque try-with-resources para abrir una sesión
        try {
            session.beginTransaction();

            transaction = session.beginTransaction();

            //consulta para obtener los partes que tiene un alumno por su id
            Query query = session.createQuery("FROM PartesIncidencia WHERE alumno.id_alum = :id_alum", PartesIncidencia.class);

            //asigna el id del alumno como parámetro de la consulta
            query.setParameter("id_alum", id_alumno);

            //almacenar los partes en la lista
            listaPartes = query.getResultList();

            transaction.commit();
        } catch (Exception e) {
            Alerta.mensajeError(null, e.getMessage());
            e.printStackTrace();
        }//try-catch

        return listaPartes;
    }//obtenerPartesAlumno


    //método para actualizar un parte de incidencia existente en la BD
    public boolean actualizarParte(PartesIncidencia parte) {
        Session session = factory.openSession();

        Transaction transaction = null;

        //semáforo para ver si ha habido cambios o no
        boolean cambios = false;

        try {
            session.beginTransaction();

            transaction = session.beginTransaction();

            //actualiza el parte de incidencia en la base de datos
            session.update(parte);

            //al haber cambios, el semáforo cambia a true
            cambios = true;

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }//try-catch

        return cambios;
    }//actualizarParte
}//class