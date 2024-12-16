package com.example.proyecto_hibernate.CRUD;

import com.example.proyecto_hibernate.classes.*;
import com.example.proyecto_hibernate.util.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.*;


//Clase que implementa la interfaz IPartesCRUD para gestionar las operaciones CRUD relacionadas con los Partes utilizando Hibernate
public class PartesCRUD implements IPartesCRUD {
    SessionFactory factory = HibernateUtil.getSessionFactory();

    @Override
    public ArrayList<PartesIncidencia> obtenerPartes(){
        Transaction transaction = null;
        //lista para almacenar los partes de incidencia obtenidos almacenados en la BD
        ArrayList<PartesIncidencia> listaPartes = new ArrayList<>();
        try(Session session = factory.openSession()){
            transaction = session.beginTransaction();
            //consulta para obtener todos los partes de incidencia de la BD --> se guardan en una lista
            List<PartesIncidencia> partesIncidencias = session.createQuery("from PartesIncidencia", PartesIncidencia.class).getResultList();

            //se añaden todos los partes obtenidos de la consulta en la lista
            listaPartes.addAll(partesIncidencias);

            transaction.commit();
        }catch (Exception e){
            Alerta.mensajeError(null, e.getMessage());
        }
        return listaPartes;
    }//obtenerPartes


    @Override
    public boolean crearParte(PartesIncidencia partesIncidencia) {
        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            session.save(partesIncidencia); //se guarda el nuevo parte en la BD
            transaction.commit();
            return true;
        } catch (Exception e) {
            Alerta.mensajeError(null, e.getMessage());
            return false;
        }//try-catch
    } //crearParte


    @Override
    public List<PartesIncidencia> obtenerPartesAlumno(int id_alumno){
        Transaction transaction = null;
        //lista vacía para almacenar los partes de un alumno obtenidos de la BD
        List listaPartes = new ArrayList<>();

        try(Session session = factory.openSession()){
            transaction = session.beginTransaction();

            //consulta para obtener los partes que tiene un alumno por su id
            Query query = session.createQuery("FROM PartesIncidencia WHERE alumno.id_alum = :id_alum", PartesIncidencia.class);

            //asigna el id del alumno como parámetro de la consulta
            query.setParameter("id_alum", id_alumno);

            //almacenar los partes en la lista
            listaPartes = query.getResultList();
            transaction.commit();
        }catch (Exception e){
            Alerta.mensajeError(null, e.getMessage());
        }//try-catch

        return listaPartes;
    }//obtenerPartesAlumno


    @Override
    public boolean actualizarParte(PartesIncidencia parte) {
        Transaction transaction = null;

        //variable que controla los cambios
        boolean cambios = false;

        try(Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            session.update(parte); //actualiza el parte de incidencia en la base de datos
            cambios = true; //si la operación funciona, se actualiza a 'true' la variable controladora
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback(); //en caso de error, realiza un rollback
            }
            Alerta.mensajeError(null, e.getMessage());
        }//try-catch

        return cambios;
    }//actualizarParte
}//class
