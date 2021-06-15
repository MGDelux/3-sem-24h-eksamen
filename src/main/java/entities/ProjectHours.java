/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author mathias
 */
@Entity
public class ProjectHours implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private double hoursSpendt;
    private String userStory;
    private String description;
    private Developer dev;

    public ProjectHours() {
    }

    public ProjectHours(double hoursSpendt, String userStory, String description) {
        this.hoursSpendt = hoursSpendt;
        this.userStory = userStory;
        this.description = description;
    }

    
    
    public Developer getDev() {
        return dev;
    }

    public void setDev(Developer dev) {
        this.dev = dev;
    }
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "ProjectHours{" + "hoursSpendt=" + hoursSpendt + ", userStory=" + userStory + ", description=" + description + ", dev=" + dev + '}';
    }
    
    
    
    

}
