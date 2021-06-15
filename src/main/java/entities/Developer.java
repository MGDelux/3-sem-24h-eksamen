/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author mathi
 */
@Entity
public class Developer implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
  @Basic(optional = false)
  @NotNull
      @Column(name = "user_name", length = 25)
    private String name;
    private String phoneNr;
    private String password;

    private double billingPrHour;
    @JoinTable(name = "user_roles", joinColumns = {
    @JoinColumn(name = "user_name", referencedColumnName = "user_name")}, inverseJoinColumns = {
    @JoinColumn(name = "role_name", referencedColumnName = "role_name")})
  @ManyToMany
  private List<Role> roleList = new ArrayList<>();
    
   @JoinTable(name = "dev_project", joinColumns =  {
   @JoinColumn(name = "user_name", referencedColumnName = "user_name")},inverseJoinColumns = {
    @JoinColumn(name = "projectName", referencedColumnName = "projectName")})     
  @ManyToMany
  private List<Project> projects = new ArrayList<>();  

    public Developer() {
    }

    public Developer(String name, String phoneNr, double billingPrHour, String password) {
        this.name = name;
        this.phoneNr = phoneNr;
        this.billingPrHour = billingPrHour;
        this.password =  BCrypt.hashpw(password, BCrypt.gensalt(10));
    }
    
     public boolean verifyPassword(String pw){
        return(BCrypt.checkpw(pw,password));
    }
    
      public List<String> getRolesAsStrings() {
    if (roleList.isEmpty()) {
      return null;
    }
    List<String> rolesAsStrings = new ArrayList<>();
    roleList.forEach((role) -> {
        rolesAsStrings.add(role.getRoleName());
      });
    return rolesAsStrings;
  }
      public List<Role> getRoleList() {
    return roleList;
  }
    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }
   
  public void setRoleList(List<Role> roleList) {
    this.roleList = roleList;
  }

  public void addRole(Role userRole) {
    roleList.add(userRole);
  }
     public String getUserPass() {
    return this.password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Developer{" + "name=" + name + ", phoneNr=" + phoneNr + ", password=" + password + ", billingPrHour=" + billingPrHour + ", roleList=" + roleList + ", projects=" + projects + '}';
    }

  
   

 

    
}
