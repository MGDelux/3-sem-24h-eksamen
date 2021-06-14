/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import entities.Role;
import entities.User;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author mathi
 */

public class SetupUsers {
    public static EntityManagerFactory emf;
    public static void main(String[] args) {
        boolean test = false;
        System.out.println("ADDING  (DUMMY) USERS TO DB");
        if (test == true){
                emf = EntityManagerCreator.CreateEntityManagerTest();
                System.out.println("USING TEST DB");
        }else{
                emf = EntityManagerCreator.CreateEntityManager();
                                System.out.println("USING DEV DB");

        }
    EntityManager em = emf.createEntityManager();
      User user = new User("user", "userpw");
      User admin = new User("admin", "adminpw");
          em.getTransaction().begin();

      Role userRole = new Role("user");
      Role adminRole = new Role("admin");
      user.addRole(userRole);
      admin.addRole(adminRole);
       em.persist(userRole);
       em.persist(adminRole);
      em.persist(user);
       em.persist(admin);
        em.getTransaction().commit();
         System.out.println("PW1: " + user.getUserPass());
         System.out.println("PW2: " + admin.getUserPass());
          System.out.println("Testing user PW " + user.verifyPassword("userpw"));
          System.out.println("Testing admin PW: " + admin.verifyPassword("adminpw"));

    }
}
