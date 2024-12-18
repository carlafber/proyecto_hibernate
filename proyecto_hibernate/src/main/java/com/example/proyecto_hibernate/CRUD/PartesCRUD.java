package com.example.proyecto_hibernate.CRUD;

import com.example.proyecto_hibernate.classes.PartesIncidencia;
import com.example.proyecto_hibernate.util.Alerta;
import com.example.proyecto_hibernate.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class PartesCRUD implements IPartesCRUD {
    SessionFactory factory = HibernateUtil.getSessionFactory();

    @Override
    public ArrayList<PartesIncidencia> obtenerPartes() {
        Transaction transaction = null;
        ArrayList<PartesIncidencia> listaPartes = new ArrayList<>();

        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            List<PartesIncidencia> partesIncidencias = session.createQuery("from PartesIncidencia", PartesIncidencia.class).getResultList();
            listaPartes.addAll(partesIncidencias);
            transaction.commit();
        } catch (Exception e) {
            Alerta.mensajeError(null, e.getMessage());
        }

        return listaPartes;
    }

    @Override
    public boolean crearParte(PartesIncidencia partesIncidencia) {
        Transaction transaction = null;

        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            session.save(partesIncidencia);
            transaction.commit();
            return true;
        } catch (Exception e) {
            Alerta.mensajeError(null, e.getMessage());
            return false;
        }
    }

    @Override
    public List<PartesIncidencia> obtenerPartesAlumno(int id_alumno) {
        Transaction transaction = null;
        List<PartesIncidencia> listaPartes = new ArrayList<>();

        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            Query<PartesIncidencia> query = session.createQuery("FROM PartesIncidencia WHERE alumno.id_alum = :id_alum", PartesIncidencia.class);
            query.setParameter("id_alum", id_alumno);
            listaPartes = query.getResultList();
            transaction.commit();
        } catch (Exception e) {
            Alerta.mensajeError(null, e.getMessage());
        }

        return listaPartes;
    }

    public List<Object[]> obtenerEstadisticasPartesPorColor() {
        Transaction transaction = null;
        List<Object[]> resultados = new ArrayList<>();

        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            Query<Object[]> query = session.createQuery(
                    "SELECT p.color, COUNT(p) FROM PartesIncidencia p GROUP BY p.color", Object[].class);
            resultados = query.getResultList();
            transaction.commit();
        } catch (Exception e) {
            Alerta.mensajeError(null, e.getMessage());
        }

        return resultados;
    }

    public List<Object[]> obtenerEstadisticasPartesPorAlumno(String numeroExpediente) {
        Transaction transaction = null;
        List<Object[]> estadisticas = new ArrayList<>();

        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            String hql = "SELECT p.color, COUNT(p) " +
                    "FROM PartesIncidencia p " +
                    "WHERE p.alumno.numero_expediente = :numeroExpediente " +
                    "GROUP BY p.color";
            estadisticas = session.createQuery(hql, Object[].class)
                    .setParameter("numeroExpediente", numeroExpediente)
                    .getResultList();
            transaction.commit();
        } catch (Exception e) {
            Alerta.mensajeError(null, e.getMessage());
        }

        return estadisticas;
    }
    public List<Object[]> obtenerEstadisticasPartesPorCurso(String curso) {
        List<Object[]> estadisticas = new ArrayList<>();

        if (curso == null || curso.trim().isEmpty()) {
            throw new IllegalArgumentException("El curso no puede ser vacío o nulo.");
        }

        try (Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();

            // Consulta HQL para estadísticas de partes por grupo/curso
            String hql = "SELECT p.color, COUNT(p) " +
                    "FROM PartesIncidencia p " +
                    "WHERE p.alumno.grupo.nombre_grupo = :curso " +
                    "GROUP BY p.color";

            Query<Object[]> query = session.createQuery(hql, Object[].class);
            query.setParameter("curso", curso);
            estadisticas = query.getResultList();

            transaction.commit();
        } catch (Exception e) {
            Alerta.mensajeError("Error al obtener estadísticas por curso", e.getMessage());
            e.printStackTrace();
        }

        return estadisticas;
    }


    @Override
    public boolean actualizarParte(PartesIncidencia parte) {
        Transaction transaction = null;
        boolean cambios = false;

        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            session.update(parte);
            cambios = true;
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            Alerta.mensajeError(null, e.getMessage());
            e.printStackTrace();
        }

        return cambios;
    }
}
