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
import com.google.gson.JsonSyntaxException;
import dto.ProjectDTO;
import dto.ProjectHoursDTO;
import entities.Project;
import entities.ProjectHours;
import facade.ProjectFacade;
import facade.ProjectHoursFacade;
import java.util.ArrayList;
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
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import utils.EntityManagerCreator;
import utils.SetupUsers;

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
    private static final ProjectFacade projectFacade = ProjectFacade.getFacade(EMF);
    private static final ProjectHoursFacade PHoursFacade = ProjectHoursFacade.getFacade(EMF);

     @Path("4test")
    @GET
    @Produces({MediaType.APPLICATION_JSON})

    public void testrun() throws Exception {
        try{
            SetupUsers.run();
        }catch (Exception e){
                        throw new WebApplicationException("ERROR " + e);

        }
        
    }
    @Path("all")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getAllProjects() throws Exception {
        String thisuser = securityContext.getUserPrincipal().getName();
        List<ProjectDTO> dto = projectFacade.getAllProjects();
   
        System.out.println(thisuser + " req get all projects"); //DELETE
        System.out.println(dto);
        return GSON.toJson(dto);
    }

    @Path("new")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON})
    @RolesAllowed({"dev", "admin"}) //remove dev
    public String createNewProject(String input) {
        String ProjectName = "";
        String ProjectDesc = "";
        try {
            JsonObject json = JsonParser.parseString(input).getAsJsonObject();
            ProjectName = json.get("ProjectName").getAsString();
            ProjectDesc = json.get("ProjectDescription").getAsString();

            projectFacade.createProject(ProjectName, ProjectDesc);
        } catch (JsonSyntaxException e) {
            throw new WebApplicationException("ERROR " + e);
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

            projectFacade.addDevToProject(developer, Project);
        } catch (Exception e) {
            throw new WebApplicationException("ERROR " + e);
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
            hoursSpendt = json.get("Hours").getAsDouble();
            userStory = json.get("UserStory").getAsString();
            description = json.get("Description").getAsString();

            PHoursFacade.newProjectHours(developer, Project, hoursSpendt, userStory, description);
        } catch (Exception e) {
            throw new WebApplicationException("ERROR could not be found " + e);
        }

        return GSON.toJson("Infomation sucessfully added to project: " + Project);
    }

      @Path("DeleteProjectHours")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON})
    @RolesAllowed({"dev", "admin"}) //remove dev
    public String deleteSpecificProjectHours(String input) {
       int id;
       ProjectHours projectHours;
       String project;
        try {
            JsonObject json = JsonParser.parseString(input).getAsJsonObject();
           id = json.get("Id").getAsInt();
           project = json.get("Project").getAsString();
           
        //  PHoursFacade.deleteProjectHours(id,project);
        } catch (Exception e) {
            throw new WebApplicationException("ERROR " + e);
        }
      return GSON.toJson("goner");
    }
     @Path("GetHours")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON})
    @RolesAllowed({"dev", "admin"}) //remove dev
    public String getHoursSpendOnProject(String input) {

        String developer = securityContext.getUserPrincipal().getName();
        String Project;
        double hours;
        try {
            JsonObject json = JsonParser.parseString(input).getAsJsonObject();
           Project = json.get("Project").getAsString();
          hours = PHoursFacade.getHoursFromSpecificProject(developer, Project);
        } catch (Exception e) {
            throw new WebApplicationException("ERROR " + e);
        }
      return GSON.toJson( developer+ " total hours spendt on: "+ Project + " is: " + hours );
    }
    
    
    @Path("GetInvoice")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON})
    @RolesAllowed({"dev", "admin"}) //remove dev
    public String GetInvoiceForProject(String input) {

        String Project;
        ProjectDTO reurnProject;
       
        try {
            JsonObject json = JsonParser.parseString(input).getAsJsonObject();
           Project = json.get("Project").getAsString();
         reurnProject =  projectFacade.getInvoice(Project);
        } catch (Exception e) {
            throw new WebApplicationException("ERROR " + e);
        }
      return GSON.toJson(reurnProject);
    }

    
    @Path("EditHours")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON})
    @RolesAllowed({"dev", "admin"}) //remove dev
    public String adjustHoursInfomation(String input) {
        String developer = securityContext.getUserPrincipal().getName();
        int id;
        double hoursSpendt;
        String userStory;
        String description;
        ProjectHours projectToEdit;
        ProjectHoursDTO edited_project;
        try {
            JsonObject json = JsonParser.parseString(input).getAsJsonObject();
            id = json.get("Id").getAsInt();
            hoursSpendt = json.get("Hours").getAsDouble();
            userStory = json.get("UserStory").getAsString();
            description = json.get("Description").getAsString();
             projectToEdit = new ProjectHours(hoursSpendt,userStory,description);
             System.out.println(projectToEdit);
          edited_project =  PHoursFacade.AdjustProjectHours(id, projectToEdit);
        } catch (Exception e) {
            throw new WebApplicationException("ERROR " + e);
        }

        return GSON.toJson("Infomation sucessfully editied: [" + edited_project.getUserStory() + "," + edited_project.getDescription() + "," + edited_project.getHoursSpendt() +"," + edited_project.getDev().getName() + ", TBA ]");
    }

}
