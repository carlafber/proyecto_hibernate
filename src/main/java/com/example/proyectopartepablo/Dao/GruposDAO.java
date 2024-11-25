package com.example.proyectopartepablo.Dao;

import com.example.proyectopartepablo.clases.Alumnos;
import com.example.proyectopartepablo.clases.Grupos;
import com.example.proyectopartepablo.utils.Alerta;
import com.example.proyectopartepablo.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class GruposDAO {
    SessionFactory factory = HibernateUtil.getSessionFactory();

    public ArrayList<Grupos> getGruposHibernate(){
        Transaction transaction;
        ArrayList<Grupos> listaGrupos=new ArrayList<>();
        try(Session session = factory.openSession()){
            transaction = session.beginTransaction();
            List<Grupos> grupos =session.createQuery("from Grupos", Grupos.class).getResultList();
            listaGrupos.addAll(grupos);
            transaction.commit();
        }catch (Exception e){
            Alerta.alertaError(e.getMessage());
        }
        return listaGrupos;
    }
}
