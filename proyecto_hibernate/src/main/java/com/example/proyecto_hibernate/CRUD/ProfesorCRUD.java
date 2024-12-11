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

//Esta clase contiene métodos crud para gestionar los profesores
public class ProfesorCRUD {
    SessionFactory factory = HibernateUtil.getSessionFactory();

    //método para obtener todos los profesores de la BD
    public ArrayList<Profesores> obtenerProfesores() {
        Session session = factory.openSession();

        Transaction transaction;

        //lista para almacenar los profesores almacenados en la BD
        ArrayList<Profesores> listaProfesores = new ArrayList<>();

        try {
            session.beginTransaction();

            transaction = session.beginTransaction();

            //consulta para obtener todos los profesores de la BD --> se guardan en una lista
            List<Profesores> profesores = session.createQuery("from Profesores", Profesores.class).getResultList();

            //se añaden todos los profesores obtenidos de la consulta en la lista
            listaProfesores.addAll(profesores);

            //confirma la transacción
            transaction.commit();
        } catch (Exception e) {
            Alerta.mensajeError(null, e.getMessage());
        }//try-catch

        return listaProfesores;
    }//obtenerProfesores


    //método para crear un nuevo profesor en la base de datos
    public boolean crearProfesor(Profesores profesores) {
        Session session = factory.openSession();

        Transaction transaction = null;

        try {
            session.beginTransaction();

            transaction = session.beginTransaction();

            //la contraseña del profesor se encripta utilizando bcrypt y se almacena
            String contrasena_encriptada = BCrypt.hashpw(profesores.getContrasena(), BCrypt.gensalt());
            profesores.setContrasena(contrasena_encriptada);

            //se guarda el nuevo profesor en la base de datos
            session.save(profesores);

            transaction.commit();
            return true;
        } catch (Exception e) {
            return false;
        }//crearProfesor
    }//crearProfesor
}//class