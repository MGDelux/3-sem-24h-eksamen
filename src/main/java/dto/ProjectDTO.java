/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import entities.Project;
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
private String Description;
private double getTotalTime;
private double totalPrice;
  public double getTotalPrice() {
        return totalPrice;
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
        this.Description = Description;
    }

    public ProjectDTO(Project project) {
           this.projectName = project.getProjectName();
        this.Description = project.getDescription();
        this.getTotalTime = project.getTotalTime();
        this.totalPrice = project.getTotalPrice();
        
    }

    public double getGetTotalTime() {
        return getTotalTime;
    }


    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    @Override
    public String toString() {
        return "ProjectDTO{" + "projectName=" + projectName + ", Description=" + Description + ", getTotalTime=" + getTotalTime + ", totalPrice=" + totalPrice + '}';
    }

 
    
}
