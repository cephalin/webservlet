package com.microsoft.azure.appservice.examples.tomcatmysql.models;

import java.util.List;

import jakarta.persistence.*;

public class StudentDAO { 

    private static EntityManager getEntityManager() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("defaultpu");
        return emf.createEntityManager();
    }
    
    public static List<Student> getAll() {
        List<Student> results = null;
        
        try (EntityManager em = getEntityManager()) {
            em.getTransaction().begin();
            results = em.createQuery("SELECT a FROM Student a", Student.class).getResultList();
        } catch (Exception e) {
            throw e;
        }

        return results;
    }

    public static Student getById(Long id) {
        Student result = null;
        try (EntityManager em = getEntityManager()) {
            em.getTransaction().begin();
            result = em.find(Student.class, id);
        } catch (Exception e) {
            throw e;
        }

        return result;
    }

    public static void create(String name, int std) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Student student = new Student();
            student.setName(name);
            student.setStd(std);
            em.persist(student);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        }
    }

    public static void update(Long id, String name, int std) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Student student = em.find(Student.class, id);
            if(student != null) {
                student.setName(name);
                student.setStd(std);
                em.getTransaction().commit();
            }
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        }
    }

    public static void delete(Long id) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Student student = em.find(Student.class, id);
            if(student != null) {
                em.remove(student);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        }

    }

}