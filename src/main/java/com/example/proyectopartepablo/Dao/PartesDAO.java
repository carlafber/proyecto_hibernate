package com.example.proyectopartepablo.Dao;


import com.example.proyectopartepablo.clases.ParteIncidencia;
import com.example.proyectopartepablo.utils.Alerta;
import com.example.proyectopartepablo.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class PartesDAO {
    SessionFactory factory = HibernateUtil.getSessionFactory();

    public ArrayList<ParteIncidencia> getPartesHibernate(){
        Transaction transaction;
        ArrayList<ParteIncidencia> listaPartes=new ArrayList<>();
        try(Session session = factory.openSession()){
            transaction = session.beginTransaction();
            List<ParteIncidencia> parteIncidencias =session.createQuery("from ParteIncidencia", ParteIncidencia.class).getResultList();
            listaPartes.addAll(parteIncidencias);
            transaction.commit();
        }catch (Exception e){
            Alerta.alertaError(e.getMessage());
            e.printStackTrace();
        }
        return listaPartes;
    }
}
