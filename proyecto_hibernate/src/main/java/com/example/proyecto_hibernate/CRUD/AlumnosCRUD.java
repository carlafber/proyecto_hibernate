package com.example.proyecto_hibernate.CRUD;

import com.example.proyecto_hibernate.classes.PartesIncidencia;
import com.example.proyecto_hibernate.util.Alerta;
import com.example.proyecto_hibernate.classes.Alumnos;
import com.example.proyecto_hibernate.util.HibernateUtil;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

//Esta clase contiene métodos crud para gestionar los Alumnos
public class AlumnosCRUD {
    SessionFactory factory = HibernateUtil.getSessionFactory();

    //método para obtener una lista de todos los alumnos de la BD
    public ArrayList<Alumnos> obtenerAlumnos() {
        Session session = factory.openSession();

        Transaction transaction;

        //lista para almacenar los alumnos almacenados en la BD
        ArrayList<Alumnos> listaAlumnos = new ArrayList<>();

        try {
            session.beginTransaction();

            transaction = session.beginTransaction();

            //consulta para obtener todos los alumnos de la BD --> se guardan en una lista
            List<Alumnos> alumnos = session.createQuery("from Alumnos", Alumnos.class).getResultList();

            //se añaden todos los alumnos obtenidos de la consulta en la lista
            listaAlumnos.addAll(alumnos);

            transaction.commit();
        } catch (Exception e) {
            Alerta.mensajeError(null, e.getMessage());
        }//try-catch

        return listaAlumnos;
    }//obtenerAlumnos


    //método para buscar un alumno por su número de expediente
    public Alumnos buscarAlumnoPorExpediente(String expediente) {
        Session session = factory.openSession();

        Alumnos alumno = null;

        try {
            session.beginTransaction();

            //consulta para buscar un alumno por su número de expediente
            Query query = session.createQuery("FROM Alumnos WHERE numero_expediente = :numero_expediente");

            //asigna el parámetro del expediente a la consulta
            query.setParameter("numero_expediente", expediente);

            //resultado único de la consulta ************************
            alumno = (Alumnos) query.uniqueResult();

            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            Alerta.mensajeError(null, e.getMessage());
        }//try-catch

        return alumno;
    }//buscarAlumnoPorExpediente


    //método para actualizar los puntos acumulados de un alumno según un parte de incidencia
    public void actualizarPuntosAlumno(Alumnos alumno, PartesIncidencia parte, boolean sumar) {
        Session session = factory.openSession();

        Transaction transaction = null;

        //variable para calcular los nuevos puntos
        int nuevosPuntos = 0;

        try {
            session.beginTransaction();

            transaction = session.beginTransaction();

            //si el alumno no es null
            if (alumno != null) {
                //si sumar es true, suma los puntos del parte al alumno
                if (sumar) {
                    nuevosPuntos = alumno.getPuntos_acumulados() + parte.getPuntos_parte();
                } else {
                    //si no, asigna directamente los puntos del parte
                    nuevosPuntos = parte.getPuntos_parte();
                }

                //actualiza los puntos acumulados del alumno
                alumno.setPuntos_acumulados(nuevosPuntos);

                session.update(alumno);
            }//if

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            Alerta.mensajeError(null, e.getMessage());
        }//try-catch
    }//actualizarPuntosAlumno
}//class