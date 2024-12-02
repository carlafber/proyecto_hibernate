package com.example.proyecto_hibernate.CRUD;

import com.example.proyecto_hibernate.classes.Profesor;
import com.example.proyecto_hibernate.util.Alerta;
import com.example.proyecto_hibernate.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.mindrot.jbcrypt.BCrypt;

import java.util.ArrayList;
import java.util.List;


public class ProfesorCRUD {
    SessionFactory factory = HibernateUtil.getSessionFactory();

    public ArrayList<Profesor> obtenerProfesores(){
        Transaction transaction;
        ArrayList<Profesor> listaProfesores=new ArrayList<>();
        try(Session session = factory.openSession()){
            transaction = session.beginTransaction();
            List<Profesor> profesor = session.createQuery("from Profesor", Profesor.class).getResultList();
            listaProfesores.addAll(profesor);
            transaction.commit();
        }catch (Exception e){
            Alerta.mensajeError(null, e.getMessage());
        }
        return listaProfesores;
    }

    public void crearProfesor(Profesor profesor) {
        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();

            String contrasena_encriptada = BCrypt.hashpw(profesor.getContrasena(), BCrypt.gensalt());
            profesor.setContrasena(contrasena_encriptada);

            session.save(profesor);

            transaction.commit();
        } catch (Exception e) {
            Alerta.mensajeError(null, e.getMessage());
        }
    }
}
