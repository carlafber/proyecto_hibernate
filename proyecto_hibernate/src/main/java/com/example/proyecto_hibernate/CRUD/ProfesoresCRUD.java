package com.example.proyecto_hibernate.CRUD;

import com.example.proyecto_hibernate.classes.*;
import com.example.proyecto_hibernate.util.*;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.mindrot.jbcrypt.BCrypt;

import java.util.*;


//Clase que implementa la interfaz IProfesoresCRUD para gestionar las operaciones CRUD relacionadas con los Profesores utilizando Hibernate
public class ProfesoresCRUD implements IProfesoresCRUD{
    SessionFactory factory = HibernateUtil.getSessionFactory();

    @Override
    public ArrayList<Profesores> obtenerProfesores(){
        Transaction transaction;

        //lista para almacenar los profesores almacenados en la BD
        ArrayList<Profesores> listaProfesores = new ArrayList<>();

        try(Session session = factory.openSession()){
            transaction = session.beginTransaction();

            //consulta para obtener todos los profesores de la BD --> se guardan en una lista
            List<Profesores> profesores = session.createQuery("from Profesores", Profesores.class).getResultList();

            //se añaden todos los profesores obtenidos de la consulta en la lista
            listaProfesores.addAll(profesores);

            transaction.commit();
        }catch (Exception e){
            Alerta.mensajeError(null, e.getMessage());
        }//try-catch

        return listaProfesores;
    }//obtenerProfesores

    @Override
    public boolean crearProfesor(Profesores profesores) {
        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();

            // La contraseña del profesor se encripta utilizando bcrypt y se almacena
            String contrasena_encriptada = BCrypt.hashpw(profesores.getContrasena(), BCrypt.gensalt());
            profesores.setContrasena(contrasena_encriptada);

            // Se guarda el nuevo profesor en la base de datos
            session.save(profesores);

            transaction.commit();
            return true;
        } catch (Exception e) {
            Alerta.mensajeError("Error al crear profesor", e.getMessage());
            return false;
        }//try-catch
    }//crearProfesor
}//class
