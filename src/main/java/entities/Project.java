/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

/**
 *
 * @author mathi
 */
@Entity
public class Project implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long projectId;
    private String projectName;
    private String Description;
    
    
    @ManyToMany(mappedBy = "projects", fetch=FetchType.EAGER)
    private List<Developer> devs;
     @OneToMany(targetEntity = ProjectHours.class,cascade = CascadeType.PERSIST,fetch=FetchType.EAGER)
    private List<ProjectHours> projectInfo;
    
    public Project() {
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
    
    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
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
    

    
}
