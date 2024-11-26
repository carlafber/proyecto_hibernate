package org.example.ad_entrega8_proyectofinalpartescolores.DAO;

import javafx.scene.control.Alert;
import org.example.ad_entrega8_proyectofinalpartescolores.Model.ParteIncidencia;
import org.example.ad_entrega8_proyectofinalpartescolores.util.ComprobacionesYcambioEscena;
import org.example.ad_entrega8_proyectofinalpartescolores.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class ParteIncidenciasDAO {
    SessionFactory factory = HibernateUtil.getSessionFactory();

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