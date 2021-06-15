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
import entities.Project;
import facade.ProjectFacade;
import facade.testFacade;
import static java.lang.String.format;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import utils.EntityManagerCreator;

/**
 *
 * @author mathi
 */
@Path("projects")

public class ProjectResource {

    @Context
    private UriInfo context;

    @Context
    SecurityContext securityContext;

    private static final EntityManagerFactory EMF = EntityManagerCreator.CreateEntityManager();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final ProjectFacade facade = ProjectFacade.getFacade(EMF);

    @Path("all")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @RolesAllowed({"dev", "admin"})
    public String getAllProjects() {
        List<Project> projects;
        String thisuser = securityContext.getUserPrincipal().getName();
        projects = facade.getAllProjects();
        System.out.println(thisuser + " req get all projects"); //DELETE
        return GSON.toJson(projects);
    }

    @Path("new")
    @POST
        @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON})
    @RolesAllowed({"dev", "admin"}) //remove dev
    public String createNewProject(String input) {
        String ProjectName;
        String ProjectDesc;
        try {

            JsonObject json = JsonParser.parseString(input).getAsJsonObject();
            ProjectName = json.get("ProjectName").getAsString();
            ProjectDesc = json.get("ProjectDescription").getAsString();
           

            facade.createProject(ProjectName, ProjectDesc);
        } catch (Exception e) {
            throw new WebApplicationException("ERROR " +e );
        }

        return GSON.toJson(ProjectName + " Created Sucessfully");
    }

    @Path("addDev")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON})
    @RolesAllowed({"dev", "admin"}) //remove dev
    public String addDevToProject(String input) {
        String developer;
        String Project;
        try {

            JsonObject json = JsonParser.parseString(input).getAsJsonObject();
            developer = json.get("Developer").getAsString();
            Project = json.get("Project").getAsString();
          
            facade.addDevToProject(developer, Project);
        } catch (Exception e) {
            throw new WebApplicationException("ERROR " +e );
        }

        return GSON.toJson(developer + " added to project " + Project);
    }
    
    
  @Path("AddProjectHours")
    @POST
     @Consumes(MediaType.APPLICATION_JSON)

    @Produces({MediaType.APPLICATION_JSON})
    @RolesAllowed({"dev", "admin"}) //remove dev
    public String addProjectInfomationToPrjoect(String input) {
        String developer = securityContext.getUserPrincipal().getName();
        String Project;
        double hoursSpendt;
        String userStory;
        String description;
        try {
            JsonObject json = JsonParser.parseString(input).getAsJsonObject();
            Project = json.get("Project").getAsString();
            hoursSpendt =  json.get("Hours").getAsDouble();
            userStory =  json.get("UserStory").getAsString();
            description =  json.get("Description").getAsString();
          
            facade.newProjectHours(developer, Project, hoursSpendt, userStory, description);
        } catch (Exception e) {
            throw new WebApplicationException("ERROR " +e );
        }

        return GSON.toJson("Infomation sucessfully added to project: "+Project );
    }

}
