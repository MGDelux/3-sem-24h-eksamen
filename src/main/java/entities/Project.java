/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

/**
 *
 * @author mathi
 */
@Entity
public class Project implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
     @Basic(optional = false)
  @NotNull
    private String projectName;
    private String Description;
    @ManyToMany(mappedBy = "projects", fetch=FetchType.EAGER)
    private List<Developer> devs;
     @OneToMany(targetEntity = ProjectHours.class,cascade = CascadeType.PERSIST,fetch=FetchType.EAGER)
    private List<ProjectHours> projectInfo;
    private double totalTime;
    public Project() {
    }

    public Project(String projectName, String Description) {
        this.projectName = projectName;
        this.Description = Description;
    }

    public double getTotalTime() {
        double totalTime= 0;
       for(ProjectHours p : projectInfo ){
         totalTime = totalTime +  p.getHoursSpendt();
       }
       return totalTime;
    }
    
    

    public List<ProjectHours> getProjectInfo() {
        return projectInfo;
    }

    public void setProjectInfo(List<ProjectHours> projectInfo) {
        this.projectInfo = projectInfo;
    }
    
    
    
    public List<Developer> getDevs() {
        return devs;
    }

    public void setDevs(List<Developer> devs) {
        this.devs = devs;
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
        return "Project{" + "projectName=" + projectName + ", Description=" + Description + ", projectInfo=" + projectInfo + ", totalTime=" + totalTime + '}';
    }

  


    

    
}
