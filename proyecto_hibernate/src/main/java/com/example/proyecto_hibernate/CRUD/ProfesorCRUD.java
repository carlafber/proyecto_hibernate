package com.example.proyecto_hibernate.CRUD;

import com.example.proyecto_hibernate.classes.Profesores;
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

    public ArrayList<Profesores> obtenerProfesores(){
        Transaction transaction;
        ArrayList<Profesores> listaProfesores = new ArrayList<>();
        try(Session session = factory.openSession()){
            transaction = session.beginTransaction();
            List<Profesores> profesores = session.createQuery("from Profesores", Profesores.class).getResultList();
            listaProfesores.addAll(profesores);
            transaction.commit();
        }catch (Exception e){
            Alerta.mensajeError(null, e.getMessage());
        }
        return listaProfesores;
    }


    public boolean crearProfesor(Profesores profesores) {
        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();

            String contrasena_encriptada = BCrypt.hashpw(profesores.getContrasena(), BCrypt.gensalt());
            profesores.setContrasena(contrasena_encriptada);

            session.save(profesores);

            transaction.commit();
            return true;
        } catch (Exception e) {
            //Alerta.mensajeError("null", e.getMessage());
            return false;
        }
    }
}
