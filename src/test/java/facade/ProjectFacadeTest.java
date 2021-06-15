/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import dto.ProjectDTO;
import entities.Developer;
import entities.Project;
import entities.Role;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import utils.EntityManagerCreator;

/**
 *
 * @author mathi
 */
public class ProjectFacadeTest {
   private static EntityManagerFactory emf;

    public ProjectFacadeTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    emf = EntityManagerCreator.CreateEntityManagerTest();
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
         System.out.println("####################################");
        System.out.println(">START TEST...");
                EntityManager em = emf.createEntityManager();
        try {
                   em.getTransaction().begin();

              em.createQuery("delete from Developer").executeUpdate();
            em.createQuery("delete from Role").executeUpdate();
             em.createQuery("delete from Project").executeUpdate();
           
          Developer dev_devRole = new Developer("dev_1", "53384818", 12.5, "dev_1");
        Developer dev_AdminRole = new Developer("dev_2", "21231412", 44.5, "dev_2");
        List<Project> projets = new ArrayList<>();
        List<Developer> devs = new ArrayList<>();
        devs.add(dev_devRole);
        devs.add(dev_AdminRole);
        Project newproject = new Project("SolidMusic", "an epic music app that does nothing :p");
        projets.add(newproject);
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
        
        } finally {
            em.close();
        }
    }
 

    @Test
    public void testGetAllProjects() throws Exception {
        EntityManager em = emf.createEntityManager();
         List<ProjectDTO> projects = new ArrayList<>();
     
                         TypedQuery<Project> query = em.createQuery("SELECT r FROM Project r", Project.class);
                         List<Project> rms = query.getResultList();
                          for (Project p : rms) {
                              assertTrue(p.getProjectName().matches("SolidMusic"));
  
                          }
                          
             
        
    }
    
    @Test void testcreateProject() {
     EntityManager em = emf.createEntityManager();
     String projectName = "MarioPizza";
     Project newProject = new Project (projectName,"Best Pizza in Town");
     assertFalse(newProject.getProjectName().matches("SolidMusic"));
     assertFalse(newProject.getDescription().isEmpty());
     em.getTransaction().begin();
            em.persist(newProject);
        em.getTransaction().commit();
        
       Project CheckProject = em.find(Project.class, projectName);
       assertTrue(CheckProject.getProjectName().matches(projectName));
       
     
    }

 
    
}
