/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import dto.ProjectDTO;
import entities.Developer;
import entities.Project;
import entities.ProjectHours;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.ws.rs.WebApplicationException;
import static utils.SetupUsers.emf;

/**
 *
 * @author mathi
 */
public class ProjectFacade {

    private static EntityManagerFactory emf;
    private static ProjectFacade instance;

    public ProjectFacade() {
    }

    public static ProjectFacade getFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new ProjectFacade();
        }
        return instance;
    }

    public Project createProject(String projectName, String projectDesc) {
        Project newProject;
        EntityManager em = emf.createEntityManager();

        try {
            newProject = em.find(Project.class, projectName);
            if (newProject != null) {
                throw new WebApplicationException("Project already exist!");
            }
            if (projectName != null && projectDesc != null) {
                newProject = new Project(projectName, projectDesc);
            } else {
                throw new WebApplicationException("Cannot create a new project: Missing infomation");
            }

        } catch (WebApplicationException e) {
            throw new WebApplicationException("ERROR IN CREATING PROJECT: " + e);
        }
        try {
            em.getTransaction().begin();
            em.persist(newProject);
            em.getTransaction().commit();

        } catch (Exception e) {
            throw new WebApplicationException("ERROR IN CREATING PROJECT: " + e);
        }
        return  newProject;
    }

    public ProjectDTO addDevToProject(String devName, String projectName) {
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
            for (Developer d : devs) {
                if (d.getName().equals(devName)) {
                    throw new WebApplicationException(d.getName() + " is already a memember if this project.");
                }

            }
            devs.add(dev);
            em.getTransaction().begin();
            project.setDevs(devs);
            em.merge(project);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new WebApplicationException("Something went horribly wrong adding " + dev.getName() + " to " + project.getProjectName() + " error: " + e);
        }
        return new ProjectDTO(project);
    }

    public ProjectDTO deleteProject(String projectname) {
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
            throw new WebApplicationException("error in removal: " + e);

        }
        return new ProjectDTO(project);
    }

   

    public Project getProject(String projectName) {
        EntityManager em = emf.createEntityManager();
        Project project;
        try {
            project = em.find(Project.class, projectName);

        } catch (Exception e) {
            throw new WebApplicationException("Cannot find a projet with the name " + projectName + " " + e);
        }
        return project;
    }

    public List<ProjectDTO> getAllProjects() throws Exception {
        EntityManager em = emf.createEntityManager();
          List<ProjectDTO> projects = new ArrayList<>();
             try{
                         TypedQuery<Project> query = em.createQuery("SELECT r FROM Project r", Project.class);
                         List<Project> rms = query.getResultList();
                         for (Project p : rms) {
                           ProjectDTO dto = new ProjectDTO(p);
                            dto.setProjectInfo(dto.getProjectInfo());
                           projects.add(dto);
                 }
                         
                        
             }catch (Exception e){
                 throw new Exception("FUCK" +e );
             }
             return projects;
    }
    
    public ProjectDTO getInvoice(String projectName){
          EntityManager em = emf.createEntityManager();
        Project project;
        List<Double> bills = new ArrayList<>();
         List<Double> hours = new ArrayList<>();
       double totalPrice = 0;
        try {
            project = em.find(Project.class, projectName);
        } catch (Exception e) {
            throw new WebApplicationException("Cannot find a projet with the name " + projectName + " " + e);
        }
        double hoursspendt =0;
        int userStories = project.getProjectInfo().size();
        for (ProjectHours p: project.getProjectInfo() ) {
           hoursspendt = hoursspendt + p.getHoursSpendt();
        }
       
        for (int i = 0; i < userStories; i++) {
        bills.add(project.getProjectInfo().get(i).getDev().getBillingPrHour());
        hours.add(project.getProjectInfo().get(i).getHoursSpendt());
            }
        
         System.out.println("total user stories "+userStories);
        System.out.println("total hours spendt on project: "+ hoursspendt);
        System.out.println("bills" + bills.toString());
        System.out.println("hours"+ hours.toString());
        for (int i = 0; i < bills.size(); i++) {
            totalPrice = totalPrice + bills.get(i) * hours.get(i);
        }
        System.out.println("Total price for project" + totalPrice );
        project.setTotalPrice(totalPrice);
      em.getTransaction().begin();
      em.merge(project);
      em.getTransaction().commit();

        return new ProjectDTO(project);
    }

}
