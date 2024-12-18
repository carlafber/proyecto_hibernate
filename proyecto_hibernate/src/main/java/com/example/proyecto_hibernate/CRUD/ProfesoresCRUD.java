package com.example.proyecto_hibernate.CRUD;

import com.example.proyecto_hibernate.classes.*;
import com.example.proyecto_hibernate.util.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.mindrot.jbcrypt.BCrypt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

            // La contraseña del profesor se encripta utilizando SHA-256
            String contrasena_encriptada = encriptarSHA256(profesores.getContrasena());
            profesores.setContrasena(contrasena_encriptada);

            // Se guarda el nuevo profesor en la base de datos
            session.save(profesores);

            transaction.commit();
            return true;
        } catch (Exception e) {
            Alerta.mensajeError("Error al crear profesor", e.getMessage());
            return false;
        } //try-catch
    } //crearProfesor

    //Método auxiliar para encriptar una cadena de texto utilizando SHA-256.

     public String encriptarSHA256(String contrasena) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(contrasena.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al encriptar la contraseña", e);
        }
    }
}//class
