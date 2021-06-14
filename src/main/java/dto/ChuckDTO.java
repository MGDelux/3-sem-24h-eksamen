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
public class ChuckDTO {
    private String value;
    private String url;
    private String id;

    public ChuckDTO(String value, String url, String id) {
        this.value = value ;
        this.url = url;
        this.id = id;
    }

  

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return  "JokeDTO{" + "Joke=" + value + " FROM=" + url + ", id=" + id + '}';
    }

   


 
    
    
}
