/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import dto.DummyDto;
import entities.DummyEntity;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.ws.rs.WebApplicationException;

/**
 *
 * @author mathi
 */
public class testFacade {
        private static EntityManagerFactory emf;
        private static testFacade instance;
        
            private testFacade() {}


    public static testFacade getDummyFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new testFacade();
        }
        return instance;
    }
        public DummyEntity createPerson(String name){
        EntityManager em = emf.createEntityManager();
         DummyEntity df1 = new DummyEntity(name);

        try{
            em.getTransaction().begin();
            em.persist(df1);
            em.getTransaction().commit(); 
        }finally {
            em.close();
        }
         return new DummyEntity(name.toString());
        }
        
        
        public List<DummyDto> getAllPersons(){
        
        EntityManager em = emf.createEntityManager();
          List<DummyEntity> rms;
        try{
        TypedQuery<DummyEntity> query = em.createQuery("SELECT p FROM DummyEntity p", DummyEntity.class);
            System.out.println(query);
        rms = query.getResultList();
        }catch(Exception e){    
     throw new WebApplicationException("Internal Server Problem. We are sorry for the inconvenience " + e.toString(),500);
    }
        return DummyDto.getDtos(rms);
    }

    public DummyDto edit(int id, String dtoName) {
    EntityManager em = emf.createEntityManager();
    DummyEntity personToEdit;
    try{
        personToEdit = (em.find(DummyEntity.class, id));
        personToEdit.setName(dtoName);
        em.getTransaction().begin();
        em.merge(personToEdit);
        em.getTransaction().commit();
    }catch(Exception e){
        throw new WebApplicationException(e.toString());
    }
    return new DummyDto(personToEdit);
    }

    public DummyDto delete(int id) {
        EntityManager em = emf.createEntityManager();
    DummyEntity personToDelete;
    try{
        personToDelete = (em.find(DummyEntity.class, id));
        em.getTransaction().begin();
        em.remove(personToDelete);
        em.getTransaction().commit();
    }catch(Exception e){
        throw new WebApplicationException(e.toString());
    }
    return new DummyDto(personToDelete);
    }
    
        
        
}

