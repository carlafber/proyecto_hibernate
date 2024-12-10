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

public class AlumnosCRUD {
    SessionFactory factory = HibernateUtil.getSessionFactory();

    public ArrayList<Alumnos> obtenerAlumnos(){
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


    public Alumnos buscarAlumnoPorExpediente(String expediente) {
        Session session = factory.openSession();  // Asegúrate de abrir la sesión
        Alumnos alumno = null;
        try {
            session.beginTransaction();  // Comienza la transacción
            Query query = session.createQuery("FROM Alumnos WHERE numero_expediente = :numero_expediente");
            query.setParameter("numero_expediente", expediente);
            alumno = (Alumnos) query.uniqueResult();
            session.getTransaction().commit();  // Comete la transacción
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();  // En caso de error, realiza rollback
            }
            Alerta.mensajeError(null, e.getMessage());
        }
        return alumno;
    }


    public void actualizarPuntosAlumno(Alumnos alumno, PartesIncidencia parte, boolean sumar) {
        Transaction transaction = null;
        int nuevosPuntos = 0;
        try(Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            if (alumno != null) {
                if(sumar){ // Sumar los puntos del parte al alumno
                    nuevosPuntos = alumno.getPuntos_acumulados() + parte.getPuntos_parte();
                } else { //cambiar los puntos
                    nuevosPuntos = parte.getPuntos_parte();
                }
                alumno.setPuntos_acumulados(nuevosPuntos);

                session.update(alumno);
            }

            transaction.commit();
        } catch (Exception e) {
            if(transaction != null) {
                transaction.rollback();
            }
        }
    }
}