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
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author mathi
 */
public class ProjectHoursFacade {
    
        private static EntityManagerFactory emf;
    private static ProjectHoursFacade instance;

    public ProjectHoursFacade() {
    }
   
    public static ProjectHoursFacade getFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new ProjectHoursFacade();
        }
        return instance;
    }
    
    
    public double getHoursFromSpecificProject(String developer,String Project ){
        EntityManager em = emf.createEntityManager();
        Project project;
        try{
         project= em.find(Project.class, Project);
        }catch (Exception e) {
            throw new WebApplicationException("Error in finding a project with the name " + Project);
        }
        double hours = 0;
        for (ProjectHours ph: project.getProjectInfo() ) {
            if(ph.getDev().getName().equals(developer)){
               hours= hours + ph.getHoursSpendt();
            }
        }
return hours;
    }

   public ProjectHours AdjustProjectHours(int id,ProjectHours edit ){
               EntityManager em = emf.createEntityManager();
                ProjectHours projectEdit ;
               try{
                   projectEdit = em.find(ProjectHours.class, id);
               }catch (Exception e) {
            throw new WebApplicationException("Error in finding a project with the id " + id);
        }
     if(!edit.getDescription().isEmpty()){
       projectEdit.setDescription(edit.getDescription());
     }
      if(edit.getHoursSpendt() != projectEdit.getHoursSpendt() && edit.getHoursSpendt() != 0   ){
       projectEdit.setHoursSpendt(edit.getHoursSpendt());
     }
        if(edit.getUserStory()!= null  ){
       projectEdit.setUserStory(edit.getUserStory());
     }
        try{
           em.getTransaction().begin();
            em.merge(projectEdit);
            em.getTransaction().commit();

        }catch (Exception e) {
            throw new WebApplicationException("Error" + e);
   }
        return projectEdit;
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



    
    
}