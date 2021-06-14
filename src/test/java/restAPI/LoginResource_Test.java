/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restAPI;

import entities.User;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import java.net.URI;
import entities.Role;
import static io.restassured.RestAssured.given;
import java.util.Arrays;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import javax.persistence.EntityManagerFactory;
import utils.*;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import static org.glassfish.jersey.internal.guava.Predicates.equalTo;
import org.glassfish.jersey.server.ResourceConfig;
import static org.hamcrest.Matchers.arrayWithSize;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasItemInArray;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.Matchers.hasValue;
import static org.hamcrest.Matchers.is;
import org.hamcrest.collection.IsArray;

/**
 *
 * @author zarpy
 */
public class LoginResource_Test {
    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";
    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;
    
    public LoginResource_Test() {
    }
    
    @BeforeAll
    public static void setUpClass() {
        emf = EntityManagerCreator.CreateEntityManagerTest();


        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }
    
    static HttpServer startServer(){
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }
            
    
    @AfterAll
    public static void tearDownClass() {

        httpServer.shutdownNow();   
    }
    
    @BeforeEach
    public void setUp() {
         System.out.println("####################################");
        System.out.println(">START TEST...");
                EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            //Delete existing users and roles to get a "fresh" database
            em.createQuery("delete from User").executeUpdate();
            em.createQuery("delete from Role").executeUpdate();

            Role userRole = new Role("User");
            Role adminRole = new Role ("Admin");        
            User user = new User("user", "test");
            user.addRole(userRole);
            User admin = new User("admin", "test");
            admin.addRole(adminRole);
            User both = new User("user_admin", "test");
            both.addRole(userRole);
            both.addRole(adminRole);
            em.persist(userRole);
            em.persist(adminRole);
            em.persist(user);
            em.persist(admin);
            em.persist(both);
            //System.out.println("Saved test data to database");
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
    
    @AfterEach
    public void tearDown() {
    }
@Test
public void test(){

      System.out.println(">Testing API STATUS");
      given().when().get("/dummyAPI/getStatus").then().statusCode(200);
      System.out.println(">>API ONLINE IT SEEMS LUL");

  
}    
@Test
public void test2(){

      System.out.println(">Testing API STATUS 2");

         given().contentType("application/json").get("/dummyAPI/getStatus").prettyPeek()
                 .then().assertThat().statusCode(200)
                 .and().assertThat().body("msg", is("SC SC ONLINE"));
         System.out.println("BASIC API TEST DONE");
            System.out.println("####################################");

  
}    




}
