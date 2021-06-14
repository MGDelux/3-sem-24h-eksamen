/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import entities.DummyEntity;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author melo-
 */
public class DummyDto {
    
    private String dtoName;

    public String getDtoName() {
        return dtoName;
    }

    public void setDtoName(String dtoName) {
        this.dtoName = dtoName;
    }

    @Override
    public String toString() {
        return "DummyDto{" + "dtoName=" + dtoName + '}';
    }

    public DummyDto(DummyEntity dtoName) {
        this.dtoName = dtoName.getName();
    }
    
public static List<DummyDto> getDtos(List<DummyEntity> rms){
        List<DummyDto> rmdtos = new ArrayList();
        rms.forEach(rm->rmdtos.add(new DummyDto(rm)));
        return rmdtos;
    }

}

