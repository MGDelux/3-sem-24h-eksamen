/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import entities.Developer;
import entities.Project;
import entities.Role;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author mathi
 */
public class SetupUsers {

    public static EntityManagerFactory emf;
    public static void main(String[] args) {
        run();
    }

public static void run(){
        boolean test = true;
        System.out.println("ADDING  (DUMMY) USERS TO DB");
        if (test == true) {
            emf = EntityManagerCreator.CreateEntityManagerTest();
            System.out.println("USING TEST DB");
        } else {
            emf = EntityManagerCreator.CreateEntityManager();
            System.out.println("USING DEV DB");

        }
        EntityManager em = emf.createEntityManager();
        Developer dev_devRole = new Developer("dev_1", "53384818", 12.5, "dev_1");
        Developer dev_AdminRole = new Developer("dev_2", "21231412", 44.5, "dev_2");
        List<Project> projets = new ArrayList<>();
        List<Developer> devs = new ArrayList<>();
        devs.add(dev_devRole);
        devs.add(dev_AdminRole);
        Project newproject = new Project("SolidMusic", "an epic music app that does nothing :p");
        projets.add(newproject);
        em.getTransaction().begin();

        Role userRole = new Role("dev");
        Role adminRole = new Role("admin");
        dev_devRole.addRole(userRole);
        dev_devRole.setProjects(projets);
        dev_AdminRole.addRole(adminRole);
        newproject.setDevs(devs);
        em.persist(newproject);
        em.persist(userRole);
        em.persist(adminRole);
        em.persist(dev_devRole);
        em.persist(dev_AdminRole);
        em.getTransaction().commit();
        System.out.println("PW1 crypt: " + dev_devRole.getUserPass());
        System.out.println("PW2 crypt: " + dev_AdminRole.getUserPass());
        System.out.println("Testing dev PW " + dev_devRole.verifyPassword("dev_1"));
        System.out.println("Testing dev_admin PW: " + dev_AdminRole.verifyPassword("dev_2"));
        System.out.println(dev_devRole.toString() + "  " + dev_AdminRole.toString());

    }
}
