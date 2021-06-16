/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restAPI;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import entities.Developer;
import entities.Project;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import java.net.URI;
import entities.Role;
import static io.restassured.RestAssured.given;
import io.restassured.response.Response;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
import static org.hamcrest.Matchers.hasProperty;
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
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    
    public LoginResource_Test() {
    }
    
    @BeforeAll
    public static void setUpClass() {
        emf = EntityManagerCreator.CreateEntityManagerTest();
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

        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }
    
    static HttpServer startServer(){
        System.out.println("SERVER START");
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }
            
    
    @AfterAll
    public static void tearDownClass() {

        httpServer.shutdownNow();   
    }
    
  @BeforeEach
    public void setUp() {

    }
    

public static Response test(){

      System.out.println(">TESTING LOGIN");
    String json  = "{\n" +" \"username\": \"dev_1\",\n" +"  \"password\": \"dev_1\"\n" +" }";
    JsonObject convertedObject = new Gson().fromJson(json, JsonObject.class);
      System.out.println(GSON.toJson(convertedObject));
    return  given().contentType("application/json").body(convertedObject).when().post("/login").then().assertThat().statusCode(200)
      .assertThat().body("username", is("dev_1")).assertThat().extract().response();

  

  
}    
//@Test
public void test2(){
   Response r = test();
  
    String token = r.jsonPath().getString("token");
      System.out.println(">TEST GET REST POINT AND API");
    
          given().contentType("application/json").header("x-access-token", token).when().get("/projects/all").then()
                 .assertThat()
                 .statusCode(200);
   
   
  
}    
@Test
public void test3(){
   Response r = test();
  
    String token = r.jsonPath().getString("token");
      System.out.println(">TEST POST REST POINT AND API");
    String Json = "{\n" +"    \"Developer\": \"dev_2\",\n" +"    \"Project\": \"SolidMusic\"\n" +"}";
        JsonObject convertedObject = new Gson().fromJson(Json, JsonObject.class);

          given().contentType("application/json").header("x-access-token", token).body(convertedObject)
                  .when().post("/projects/addDev").then()
                  .assertThat().statusCode(200);
              
        
    }
@Test
public void test4(){
   Response r = test();
  
    String token = r.jsonPath().getString("token");
      System.out.println(">TEST POST REST POINT AND API");
    String Json = "{\n" +" \"Project\": \"SolidMusic\"\n" +" }";
        JsonObject convertedObject = new Gson().fromJson(Json, JsonObject.class);

          given().contentType("application/json").header("x-access-token", token).body(convertedObject)
                  .when().post("/projects/GetHours").then()
                  .assertThat().statusCode(200);
              
        
    }
   
     
  
  



}
