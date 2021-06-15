/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import entities.Developer;
import entities.Project;
import entities.ProjectHours;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mathi
 */
public class ProjectHoursDTO {
    private double hoursSpendt;
    private String userStory;
    private String description;
    private Developer dev;
    private Project projectName;
    
    
       public static List<ProjectHoursDTO> getDtos(List<ProjectHours> rms){
        List<ProjectHoursDTO> rmdtos = new ArrayList();
        rms.forEach(rm->rmdtos.add(new ProjectHoursDTO(rm)));
        return rmdtos;
    }

    public ProjectHoursDTO(double hoursSpendt, String userStory, String description, Developer dev, Project projectName) {
        this.hoursSpendt = hoursSpendt;
        this.userStory = userStory;
        this.description = description;
        this.dev = dev;
        this.projectName = projectName;
    }

    public ProjectHoursDTO(ProjectHours ph) {
          this.hoursSpendt = ph.getHoursSpendt();
        this.userStory = ph.getUserStory();
        this.description = ph.getDescription();
        this.dev = ph.getDev();
        this.projectName =  ph.getProjectName();
        
    }

    
    
    public double getHoursSpendt() {
        return hoursSpendt;
    }

    public void setHoursSpendt(double hoursSpendt) {
        this.hoursSpendt = hoursSpendt;
    }

    public String getUserStory() {
        return userStory;
    }

    public void setUserStory(String userStory) {
        this.userStory = userStory;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Developer getDev() {
        return dev;
    }

    public void setDev(Developer dev) {
        this.dev = dev;
    }

    public Project getProjectName() {
        return projectName;
    }

    public void setProjectName(Project projectName) {
        this.projectName = projectName;
    }

    @Override
    public String toString() {
        return "ProjectHoursDTO{" + "hoursSpendt=" + hoursSpendt + ", userStory=" + userStory + ", description=" + description + ", dev=" + dev + ", projectName=" + projectName + '}';
    }
    
    
    
    
}
