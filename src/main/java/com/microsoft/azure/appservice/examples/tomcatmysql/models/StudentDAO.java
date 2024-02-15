package com.microsoft.azure.appservice.examples.tomcatmysql.models;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.regex.Pattern;

import jakarta.persistence.*;

public class StudentDAO { 

    private static EntityManager getEntityManager() {
        Map<String, String> persistenceMap = new HashMap<String, String>();

        //String azureDbUrl= "jdbc:mysql://cephalin-tomcat-server.mysql.database.azure.com:3306/cephalin-tomcat-database?serverTimezone=UTC&sslmode=required&user=claftxejjs&password=83YF76TKMVO47321$";
        String azureDbUrl= System.getenv("AZURE_MYSQL_CONNECTIONSTRING");
        if (azureDbUrl!=null) {
            persistenceMap.put("jakarta.persistence.jdbc.url", azureDbUrl);
            persistenceMap.put("jakarta.persistence.jdbc.user", azureDbUrl.replaceFirst(".*&user=([^&]*).*", "$1"));
            persistenceMap.put("jakarta.persistence.jdbc.password", azureDbUrl.replaceFirst(".*&password=([^&]*).*", "$1"));
        } else {
            persistenceMap.put("jakarta.persistence.jdbc.url", System.getenv("MYSQL_URL"));
            persistenceMap.put("jakarta.persistence.jdbc.user", System.getenv("MYSQL_USER"));
            persistenceMap.put("jakarta.persistence.jdbc.password", System.getenv("MYSQL_PASSWORD"));
        }

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("defaultpu", persistenceMap);
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