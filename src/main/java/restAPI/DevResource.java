/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restAPI;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.DeveloperDTO;
import dto.ProjectDTO;
import facade.DevFacade;
import facade.ProjectFacade;
import facade.ProjectHoursFacade;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import utils.EntityManagerCreator;

/**
 *
 * @author mathi
 */

@Path("dev")
public class DevResource {
    
      @Context
    private UriInfo context;

    @Context
    SecurityContext securityContext;

    private static final EntityManagerFactory EMF = EntityManagerCreator.CreateEntityManager();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final DevFacade devFacade = DevFacade.getFacade(EMF);
            
           
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @RolesAllowed({"dev", "admin"})
    public String getAllProjects() throws Exception {
        String thisuser = securityContext.getUserPrincipal().getName();
        List<DeveloperDTO> dto = devFacade.getALlDevelopers();
   
        System.out.println(thisuser + " req get all devs"); //DELETE
        System.out.println(dto);
        return GSON.toJson(dto);
    }  
    
}
