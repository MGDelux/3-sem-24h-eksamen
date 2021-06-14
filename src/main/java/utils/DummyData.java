/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import entities.DummyEntity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author mathi
 */
public class DummyData {
            private static final EntityManagerFactory EMF = EntityManagerCreator.CreateEntityManagerTest();

    public static void main(String[] args) {
        
        try {
        System.out.println("> SETTING UP DUMMY DATA POGGERS");
        EntityManager em = EMF.createEntityManager();
        DummyEntity df1 = new DummyEntity("Person1");
        DummyEntity df2 = new DummyEntity("Person2");
        em.getTransaction().begin();
        em.persist(df1);
        em.persist(df2);
        em.getTransaction().commit();
        System.out.println("> DUMMY DATA DONE");
        System.out.println("> > DUMMY DATA:");
        System.out.println(df1.getId() + " " + df1.getName() + " " + df1.getDate());
        System.out.println(df2.getId() + " " + df2.getName() + " " + df2.getDate());
        } catch(Exception  e){
            System.out.println("ERROR : " + e.toString());
        }
        System.out.println("##########");
        System.out.println("> !! RUN TEST FOR API ApiTest.Java in Test packages. " );
    }

}
