/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.Properties;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author mathi
 */
public class EntityManagerCreator {
    
    
    public static EntityManagerFactory CreateEntityManager(){
        return createEntityManagerFactory(false);
    }
       public static EntityManagerFactory CreateEntityManagerTest(){
        return createEntityManagerFactory(true);
    }
    
        private static EntityManagerFactory createEntityManagerFactory(boolean isTest) {

        boolean deploy = (System.getenv("DEPLOYED") != null);
            System.out.println("#####################################################");
      System.out.println("# SCSC 1.0 EMF CREATOR > deployment status :  "+ deploy + " #");

        if (deploy) {
            // TODO ADD CONTEXT XML FILE for deployment file 
            System.out.println("DEPLOYED");
            String user = System.getenv("USER");
            String pw = System.getenv("PW");
            String connection_str = System.getenv("CONNECTION_STR");
            System.out.println("SCSC 1.0 > SYS ENV VARS:");
            System.out.println("TEST TRUE/FALSE : "+ isTest);
            System.out.println("USER : "+ user);
            System.out.println("- - - - - - - -");
            System.out.println("PASSWORD : "+ pw);
            System.out.println("- - - - - - - -");
            System.out.println("CONNECTION: : "+ connection_str);
            System.out.println("- - - - - - - -");
             Properties props = new Properties();
            props.setProperty("javax.persistence.jdbc.user", user);
            props.setProperty("javax.persistence.jdbc.password", pw);
            props.setProperty("javax.persistence.jdbc.url", connection_str);
            props.setProperty("javax.persistence.jdbc.driver", "com.mysql.cj.jdbc.Driver");
            
            //Sets the production log-level to show only potential problems
            props.setProperty("eclipselink.logging.level","WARNING");
            props.setProperty("eclipselink.logging.level.sql","WARNING");
            return Persistence.createEntityManagerFactory("pu", props);
           
        }
     
       String puName = isTest || System.getProperty("IS_INTEGRATION_TEST_WITH_DB") != null ? "puTest" : "pu"; //Only legal names
             System.out.println( "#> Persistance.xml , = "+puName+" <                 #");
       if (puName.equals("puTest")) {
            System.out.println("#>> Using the TEST DB...                             #");
        } else {
            System.out.println("#>> Using the DEV PU DB                              #");
        }
               System.out.println("#####################################################");

       EntityManagerFactory emf = null;
       try {
         emf =  Persistence.createEntityManagerFactory(puName, null);
       } catch (javax.persistence.PersistenceException e){
           System.out.println("!!! > EntityManagerCreator E R R O R:");
           System.out.println(e.toString());
           throw e;
       }
return emf;
}
}
