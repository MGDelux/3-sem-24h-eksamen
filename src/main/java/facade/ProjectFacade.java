/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

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
            throw new WebApplicationException("error in removal: " + e);

        }
        return project;
    }

    public Project newProjectHours(String developer, String Project, double hoursSpendt, String userStory, String description) {
        List<ProjectHours> projecth;
        EntityManager em = emf.createEntityManager();
        Project Findproject;
        Developer findDev;
        ProjectHours projectInfo;
        try{
         findDev=em.find(Developer.class, developer);
         Findproject = em.find(Project.class, Project);
         projectInfo = new ProjectHours(hoursSpendt,userStory,description);
        }catch (Exception e) {
            throw new WebApplicationException("Error in infomation please provide the correct infomation [" + developer + "," +Project + ","+hoursSpendt+"-"+userStory+"-"+description+"+]" );
        }

        try {
            projectInfo.setProjectName(Findproject);
            projecth = Findproject.getProjectInfo();
            projecth.add(projectInfo);
            projectInfo.setDev(findDev);
            Findproject.setProjectInfo(projecth);

        } catch (Exception e) {
            throw new WebApplicationException("ERROR in adding project infomation " + e);
        }
        try {
            em.getTransaction().begin();
            em.merge(Findproject);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new WebApplicationException("SQL/EM ERROR in adding project infomation " + e);
        }
        return Findproject;
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

    public List<Project> getAllProjects() {
        EntityManager em = emf.createEntityManager();
        List<Project> allProjetsList = new ArrayList<>();
        try {
            TypedQuery<Project> query = em.createQuery("SELECT r FROM Project r", Project.class);
            List<Project> rms = query.getResultList();
            allProjetsList.addAll(rms);
            em.close();
        } catch (Exception e) {
            throw new WebApplicationException("ERROR IN GETTING PROJECTS! " + e);
        }
        return allProjetsList;
    }

}
