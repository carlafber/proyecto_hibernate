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

public class PartesCRUD {
    SessionFactory factory = HibernateUtil.getSessionFactory();

    public ArrayList<PartesIncidencia> obtenerPartes(){
        Transaction transaction = null;
        ArrayList<PartesIncidencia> listaPartes = new ArrayList<>();
        try(Session session = factory.openSession()){
            transaction = session.beginTransaction();
            List<PartesIncidencia> partesIncidencias = session.createQuery("from PartesIncidencia", PartesIncidencia.class).getResultList();
            listaPartes.addAll(partesIncidencias);
            transaction.commit();
        }catch (Exception e){
            Alerta.mensajeError(null, e.getMessage());
            e.printStackTrace();
        }
        return listaPartes;
    }


    public void crearParte(PartesIncidencia partesIncidencia) {
        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            session.save(partesIncidencia);
            transaction.commit();
        } catch (Exception e) {
            Alerta.mensajeError(null, e.getMessage());
        }
    }//insertarParte


    public List<PartesIncidencia> obtenerPartesAlumno(int id_alumno){
        Transaction transaction = null;
        List listaPartes = new ArrayList<>();
        try(Session session = factory.openSession()){
            transaction = session.beginTransaction();
            Query query = session.createQuery("FROM PartesIncidencia WHERE alumno.id_alum = :id_alum", PartesIncidencia.class);
            query.setParameter("id_alum", id_alumno);
            listaPartes = query.getResultList();
            transaction.commit();
        }catch (Exception e){
            Alerta.mensajeError(null, e.getMessage());
            e.printStackTrace();
        }
        return listaPartes;
    }


    public boolean actualizarParte(PartesIncidencia parte) {
        Transaction transaction = null;
        boolean cambios = false;
        try(Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            session.update(parte);
            cambios = true;
            transaction.commit();
        } catch (Exception e) {
            if(transaction != null) {
                transaction.rollback();
            }
        }
        return cambios;
    }
}
