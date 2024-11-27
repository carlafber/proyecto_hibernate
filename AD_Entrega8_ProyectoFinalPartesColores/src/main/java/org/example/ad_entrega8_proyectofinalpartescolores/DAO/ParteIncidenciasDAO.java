package org.example.ad_entrega8_proyectofinalpartescolores.DAO;

import javafx.scene.control.Alert;
import org.example.ad_entrega8_proyectofinalpartescolores.Model.Alumnos;
import org.example.ad_entrega8_proyectofinalpartescolores.Model.ParteIncidencia;
import org.example.ad_entrega8_proyectofinalpartescolores.util.ComprobacionesYcambioEscena;
import org.example.ad_entrega8_proyectofinalpartescolores.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import static org.example.ad_entrega8_proyectofinalpartescolores.util.HibernateUtil.factory;

public class ParteIncidenciasDAO {
    SessionFactory factory = HibernateUtil.getSessionFactory();


    //metodo para buscar un Alumno por expediente
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
            e.printStackTrace();
        } finally {
            session.close();  // Siempre cierra la sesión al final
        }
        return alumno;
    }



    //método para insertar un nuevo parte en la BD
    public void insertarParte(ParteIncidencia parteIncidencia) {
        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();

            session.save(parteIncidencia);

            transaction.commit();
        } catch (Exception e) {
            ComprobacionesYcambioEscena.mostrarAlerta(Alert.AlertType.ERROR, "Error al ñadir el parte. Inténtelo de nuevo.");
        }
    }//insertarParte

}//class