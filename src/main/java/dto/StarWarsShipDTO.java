/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import java.util.List;


/**
 *
 * @author mathi
 */
public class StarWarsShipDTO {
    private String name;
    private String model;
    private String manufacturer;
    private String cost_in_credits;
    private String length;
    private String crew;
    private String hyperdrive_rating;
    private List<String> pilots;

    public StarWarsShipDTO(String name, String model, String manufacturer, String cost_in_credits, String length, String crew, String hyperdrive_rating, List<String> pilots) {
        this.name = name;
        this.model = model;
        this.manufacturer = manufacturer;
        this.cost_in_credits = cost_in_credits;
        this.length = length;
        this.crew = crew;
        this.hyperdrive_rating = hyperdrive_rating;
        this.pilots = pilots;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getCost_in_credits() {
        return cost_in_credits;
    }

    public void setCost_in_credits(String cost_in_credits) {
        this.cost_in_credits = cost_in_credits;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getCrew() {
        return crew;
    }

    public void setCrew(String crew) {
        this.crew = crew;
    }

    public String getHyperdrive_rating() {
        return hyperdrive_rating;
    }

    public void setHyperdrive_rating(String hyperdrive_rating) {
        this.hyperdrive_rating = hyperdrive_rating;
    }

    public List<String> getPilots() {
        return pilots;
    }

    public void setPilots(List<String> pilots) {
        this.pilots = pilots;
    }

    @Override
    public String toString() {
        return "StarWarsShipDTO{" + "name=" + name + ", model=" + model + ", manufacturer=" + manufacturer + ", cost_in_credits=" + cost_in_credits + ", length=" + length + ", crew=" + crew + ", hyperdrive_rating=" + hyperdrive_rating + ", pilots=" + pilots + '}';
    }
    
    

  

  

   


 
    
    
}
