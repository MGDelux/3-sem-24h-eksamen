/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import dto.DeveloperDTO;
import dto.ProjectDTO;
import entities.Developer;
import entities.Project;
import entities.Role;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import secuirty.errorhandling.AuthenticationException;
import static utils.SetupUsers.emf;

/**
 *
 * @author mathi
 */
public class DevFacade {
       private static EntityManagerFactory emf;
    private static DevFacade instance;

    public DevFacade() {
    }

    public static DevFacade getFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new DevFacade();
        }
        return instance;
    }

    
    public List<DeveloperDTO> getALlDevelopers() throws Exception {
        EntityManager em = emf.createEntityManager();
          List<DeveloperDTO> devs = new ArrayList<>();
             try{
                         TypedQuery<Developer> query = em.createQuery("SELECT d FROM Developer d", Developer.class);
                         List<Developer> rms = query.getResultList();
                         for (Developer d : rms) {
                           DeveloperDTO dto = new DeveloperDTO(d);
                           devs.add(dto);
                 }
                         
                        
             }catch (Exception e){
                 throw new Exception("errour" +e );
             }
             return devs;
    }
    public Developer getVeryfiedUser(String username, String password) throws AuthenticationException {
        EntityManager em = emf.createEntityManager();
        Developer user;
        try {
            user = em.find(Developer.class, username);
            if (user == null || !user.verifyPassword(password)) {
                throw new AuthenticationException("Invalid user name or password");
            }
        } finally {
            em.close();
        }
        return user;
    }

    public void createDev(String name, String role) {
                EntityManager em = emf.createEntityManager();
                Role userRole = new Role(role);
             Developer user = new Developer(name,"53123123",125.0,name+"_1");
                     em.getTransaction().begin();
                     user.addRole(userRole);
                     em.persist(userRole);
                     em.persist(user);
               em.getTransaction().commit();


             
    }
    
}
