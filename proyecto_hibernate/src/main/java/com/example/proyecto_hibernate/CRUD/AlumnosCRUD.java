package com.example.proyecto_hibernate.CRUD;

import com.example.proyecto_hibernate.classes.*;
import com.example.proyecto_hibernate.util.*;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.*;


//Clase que implementa la interfaz IAlumnosCRUD para gestionar las operaciones CRUD relacionadas con los Alumnos utilizando Hibernate
public class AlumnosCRUD implements IAlumnosCRUD{
    SessionFactory factory = HibernateUtil.getSessionFactory();

    @Override
    public ArrayList<Alumnos> obtenerAlumnos() {
        Transaction transaction = null;

        //lista para almacenar los alumnos almacenados en la BD
        ArrayList<Alumnos> listaAlumnos = new ArrayList<>();

        try (Session session = factory.openSession()) {
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


    @Override
    public Alumnos buscarAlumnoPorExpediente(String expediente) {
        Alumnos alumno = null;
        try(Session session = factory.openSession()) {
            session.beginTransaction();  //comienza la transacción

            //consulta para buscar un alumno por su número de expediente
            Query query = session.createQuery("FROM Alumnos WHERE numero_expediente = :numero_expediente");

            //asigna el parámetro del expediente a la consulta
            query.setParameter("numero_expediente", expediente);

            //resultado único de la consulta ************************
            alumno = (Alumnos) query.uniqueResult();

            session.getTransaction().commit();  //guarda la transacción
        } catch (Exception e) {
            Alerta.mensajeError(null, e.getMessage());
        }//try-catch

        return alumno;
    }//buscarAlumnoPorExpediente


    @Override
    public void actualizarPuntosAlumno(Alumnos alumno, PartesIncidencia parte, boolean sumar) {
        Transaction transaction = null;

        //variable para calcular los nuevos puntos
        int nuevosPuntos = 0;

        try(Session session = factory.openSession()) {
            transaction = session.beginTransaction();

            if (alumno != null) { //si el alumno existe
                if(sumar){ //si la variable sumar es 'true', se suman los puntos del parte al alumno
                    nuevosPuntos = alumno.getPuntos_acumulados() + parte.getPuntos_parte();
                } else { //si no, se asigna directamente los puntos del parte
                    nuevosPuntos = parte.getPuntos_parte();
                }
                //se asignan los nuevos puntos al alumno
                alumno.setPuntos_acumulados(nuevosPuntos);

                //actualiza los datos del alumno
                session.update(alumno);
            }//if

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback(); //en caso de error, realiza un rollback
            }
            Alerta.mensajeError(null, e.getMessage());
        }//try-catch
    }//actualizarPuntosAlumno
}//class
