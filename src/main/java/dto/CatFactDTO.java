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
public class CatFactDTO {
String _id;
String text;
String createdAt;

    public CatFactDTO(String _id, String text, String createdAt) {
        this._id = _id;
        this.text = text;
        this.createdAt = createdAt;
    }

    public String getId() {
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "CatFactDTO{" + "_id=" + _id + ", text=" + text + ", createdAt=" + createdAt + '}';
    }


    
    
    
    
    
}

