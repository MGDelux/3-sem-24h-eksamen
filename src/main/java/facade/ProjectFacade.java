/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entities.Developer;
import entities.Project;
import entities.ProjectHours;
import java.util.List;
import javax.persistence.EntityManager;
import javax.ws.rs.WebApplicationException;
import static utils.SetupUsers.emf;

/**
 *
 * @author mathi
 */
public class ProjectFacade {

    public Project createProject(String projectName, String projectDesc) {
        Project newProject;
        try {
            if (projectName != null && projectDesc != null) {
                newProject = new Project(projectName, projectDesc);
            } else {
                throw new WebApplicationException("Cannot create a new project: Missing infomation");
            }

        } catch (WebApplicationException e) {
            throw new WebApplicationException("ERROR IN CREATING PROJECT: " + e);
        }
        try {
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            em.persist(newProject);
            em.getTransaction().commit();

        } catch (Exception e) {
            throw new WebApplicationException("ERROR IN CREATING PROJECT: " + e);
        }
        return newProject; //add dto if time.
    }

    public Project addDevToProject(String devName, String projectName) {
        EntityManager em = emf.createEntityManager();
        Developer dev;
        Project project;
        try {
            dev = em.find(Developer.class, devName);
            project = em.find(Project.class, projectName);
        } catch (WebApplicationException e) {
            throw new WebApplicationException("Cannot find Dev or Project: " + e);
        }
        try {
            List<Developer> devs = project.getDevs();
            devs.add(dev);
            em.getTransaction().begin();
            project.setDevs(devs);
            em.merge(project);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new WebApplicationException("Something went horribly wrong add " + dev.getName() + " to " + project.getProjectName() + " error: " + e);
        }
        return project;
    }

    public Project deleteProject(String projectname) {
        EntityManager em = emf.createEntityManager();
        Project project;
        try {
            project = em.find(Project.class, projectname);

        } catch (Exception e) {
            throw new WebApplicationException(e);

        }
           try {
        em.getTransaction().begin();
        em.remove(project);
        em.getTransaction().commit();
           } catch (Exception e) {
            throw new WebApplicationException("error in removal: "+e);

        }
       return project;
    }

    public Project newProjectHours(Developer dev, Project project, ProjectHours hours){
           List<ProjectHours> projecth;
           EntityManager em = emf.createEntityManager();

     try{
         projecth = project.getProjectInfo();
         projecth.add(hours);
         hours.setDev(dev);
         project.setProjectInfo(projecth);
         
     }catch (Exception e){
         throw new WebApplicationException("ERROR in adding project infomation " + e);
     }
     try{
        em.getTransaction().begin();
       em.merge(project);
       em.getTransaction().commit();
     }catch (Exception e){
         throw new WebApplicationException("SQL/EM ERROR in adding project infomation " + e);
     }
     return project;
    }
    
    public Project getProject(String projectName){
     EntityManager em = emf.createEntityManager();
     Project project;
     try{
       project = em.find(Project.class, projectName);

     }catch (Exception e){
         throw new WebApplicationException("Cannot find a projet with the name "+ projectName + " " + e);
     }
 return project;
    }
    
}
