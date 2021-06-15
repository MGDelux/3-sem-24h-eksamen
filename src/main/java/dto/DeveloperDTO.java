/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import entities.Developer;
import entities.Project;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mathi
 */
public class DeveloperDTO {
    private String name;
    private String phoneNr;
    private double billingPrHour;
    private List<Project> projects = new ArrayList<>();  

    public DeveloperDTO(Developer dev) {
        this.name = dev.getName();
        this.phoneNr = dev.getPhoneNr();
        this.billingPrHour = dev.getBillingPrHour();
        this.projects  = dev.getProjects();
    }
     public static List<DeveloperDTO> getDtos(List<Developer> rms){
        List<DeveloperDTO> rmdtos = new ArrayList();
        rms.forEach(rm->rmdtos.add(new DeveloperDTO(rm)));
        return rmdtos;
    }
    
    

    public DeveloperDTO(String name, String phoneNr, double billingPrHour) {
        this.name = name;
        this.phoneNr = phoneNr;
        this.billingPrHour = billingPrHour;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNr() {
        return phoneNr;
    }

    public void setPhoneNr(String phoneNr) {
        this.phoneNr = phoneNr;
    }

    public double getBillingPrHour() {
        return billingPrHour;
    }

    public void setBillingPrHour(double billingPrHour) {
        this.billingPrHour = billingPrHour;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    @Override
    public String toString() {
        return "DeveloperDTO{" + "name=" + name + ", phoneNr=" + phoneNr + ", billingPrHour=" + billingPrHour + ", projects=" + projects + '}';
    }

    
    
}
