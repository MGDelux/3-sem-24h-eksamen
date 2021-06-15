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
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

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
    
    
}
