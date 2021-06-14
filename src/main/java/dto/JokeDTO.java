/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;


/**
 *
 * @author mathi
 */
public class JokeDTO {
    private String id;
    private String joke;

    public JokeDTO( String id, String joke) {
      
        this.id = id;
        this.joke = joke;
    }

    public String getJoke() {
        return joke;
    }

    public void setJoke(String joke) {
        this.joke = joke;
    }
  
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "JokeDTO{" + "id=" + id + ", joke=" + joke + '}';
    }


  

   


 
    
    
}
