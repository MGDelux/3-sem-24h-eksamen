/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import facade.testFacade;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author mathi
 */
public class test {
           public static void main(String[] args) {
         EntityManagerFactory emf = EntityManagerCreator.CreateEntityManagerTest();
         testFacade f = testFacade.getDummyFacade(emf);
    }

}
