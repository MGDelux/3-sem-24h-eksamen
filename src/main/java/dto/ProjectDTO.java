/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import entities.Developer;
import entities.Project;
import entities.ProjectHours;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author mathi
 */
public class ProjectDTO  {
private String projectName;
private String projectDescription;
private double totalTime;
private double totalPrice;
private List<DeveloperDTO> devs;
   List<ProjectHoursDTO> projectInfo;

 
    public double getTotalPrice() {
        return totalPrice;
    }



   public double getTotalTime() {
        double totalTime= 0;
       for(ProjectHoursDTO p : projectInfo ){
         totalTime = totalTime +  p.getHoursSpendt();
       }
       return totalTime;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

   public static List<ProjectDTO> getDtos(List<Project> rms){
        List<ProjectDTO> rmdtos = new ArrayList();
        rms.forEach(rm->rmdtos.add(new ProjectDTO(rm)));
        return rmdtos;
    }

    public ProjectDTO(String projectName, String Description) {
        this.projectName = projectName;
        this.projectDescription = Description;
    }

    public ProjectDTO(Project project) {
           this.projectName = project.getProjectName();
        this.projectDescription = project.getDescription();
        this.totalTime = project.getTotalTime();
        this.totalPrice = project.getTotalPrice();
        this.projectInfo = ProjectHoursDTO.getDtos(project.getProjectInfo());
        this.devs = DeveloperDTO.getDtos(project.getDevs());
        
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public List<DeveloperDTO> getDevs() {
        return devs;
    }

    public void setDevs(List<DeveloperDTO> devs) {
        this.devs = devs;
    }
    

    public List<ProjectHoursDTO> getProjectInfo() {
        return projectInfo;
    }

    public void setProjectInfo(List<ProjectHoursDTO> projectInfo) {
        this.projectInfo = projectInfo;
    }

    
    
    public double getGetTotalTime() {
        return totalTime;
    }


    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getDescription() {
        return projectDescription;
    }

    public void setDescription(String Description) {
        this.projectDescription = Description;
    }

    @Override
    public String toString() {
        return "ProjectDTO{" + "projectName=" + projectName + ", projectDescription=" + projectDescription + ", totalTime=" + totalTime + ", totalPrice=" + totalPrice + ", devs=" + devs + ", projectInfo=" + projectInfo + '}';
    }

 
   


 
    
}
