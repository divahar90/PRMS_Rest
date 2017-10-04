/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.radioprogram.RESTful;

import java.util.List;
import sg.edu.nus.iss.phoenix.radioprogram.entity.RadioProgram;

/**
 *
 * @author User
 */
public class RadioPrograms {

    private List<RadioProgram> rpList;

    /**
     *
     * @return
     */
    public List<RadioProgram> getRpList() {
        return rpList;
    }

    /**
     *
     * @param rpList
     */
    public void setRpList(List<RadioProgram> rpList) {
        this.rpList = rpList;
    }

}
