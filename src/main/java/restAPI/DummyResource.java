/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restAPI;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.ChuckDTO;
import dto.CombinedDTO;
import dto.StarWarsShipDTO;
import dto.JokeDTO;
import dto.CatFactDTO;
import dto.DummyDto;
import entities.DummyEntity;
import facade.testFacade;
import java.io.IOException;
import static java.lang.String.format;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import utils.EntityManagerCreator;
import utils.HTTPFetch;

/**
 *
 * @author mathi
 */

@Path("dummyAPI")
public class DummyResource {
    
    @Path("/getStatus")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
              public String demo() {
        return "{\"msg\":\"SC SC ONLINE\"}";
    }
                  private static final EntityManagerFactory EMF = EntityManagerCreator.CreateEntityManager();
            private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
                private static final testFacade facade = testFacade.getDummyFacade(EMF);

    @Path("/all")          
    @GET
    @Produces({MediaType.APPLICATION_JSON})
      public String getFromDB(){
         List<DummyDto> list = new ArrayList();
        list.addAll(facade.getAllPersons());
        return GSON.toJson(list);
      }
      
      @Path("/add")
      @POST
    @Produces({MediaType.APPLICATION_JSON})
     @Consumes(MediaType.APPLICATION_JSON)
        public void addNewPerson(DummyEntity p){
            System.out.println(p);
            try {
                 if(p.getName()== null){
                   throw new WebApplicationException(Response
          .status(BAD_REQUEST)
          .type(MediaType.APPLICATION_JSON)
          .entity(format("Missing info please check %s", p.toString()))
          .build());
            }
        }
      catch(Exception e){
                  throw new WebApplicationException("Internal Server Problem. We are sorry for the inconvenience",501);
    }finally{
              facade.createPerson(p.getName());
        }
    }
        
        
       @Path("/edit/{id}")
    @PUT
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes(MediaType.APPLICATION_JSON)
    public DummyDto edit(@PathParam("id") int id, String person) throws Exception{
    DummyDto DTO = GSON.fromJson(person, DummyDto.class);
    facade.edit(id, DTO.getDtoName());
     return DTO;

    }
        
     @Path("/delete/{id}")
    @DELETE
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes(MediaType.APPLICATION_JSON)
    public DummyDto edit(@PathParam("id") int id) throws Exception{
    DummyDto p = facade.delete(id);
     return p;

    }    
        
        
        @Path("/5endPoints")
        @GET
        @Consumes(MediaType.APPLICATION_JSON)
        @Produces({MediaType.APPLICATION_JSON})
        public String fetch() throws IOException{
            Gson gson = new Gson();
//todo use multithreading 
       List<CombinedDTO> list = new ArrayList<>();
        String chuck = HTTPFetch.fetchData("https://api.chucknorris.io/jokes/random");
        String joke = HTTPFetch.fetchData("https://icanhazdadjoke.com/");
        String SWShip = HTTPFetch.fetchData("https://swapi.dev/api/starships/12/");
        String catfact = HTTPFetch.fetchData("https://cat-fact.herokuapp.com/facts/random");
        StarWarsShipDTO ship = gson.fromJson(SWShip, StarWarsShipDTO.class);
        CatFactDTO cat =  gson.fromJson(catfact, CatFactDTO.class);
            System.out.println(catfact);
                   System.out.println(cat);
       ChuckDTO dto = gson.fromJson(chuck, ChuckDTO.class);
        JokeDTO jokedto = gson.fromJson(joke, JokeDTO.class);
       CombinedDTO cw = new CombinedDTO(dto,jokedto,ship,cat);
            System.out.println(cw);
         list.add(cw);
        return gson.toJson(list);
        }

    


}
