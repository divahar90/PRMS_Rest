package sg.edu.nus.iss.phoenix.schedule.RESTful;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.List;
import java.util.Map;
import sg.edu.nus.iss.phoenix.schedule.entity.ProgramSlot;


/**
 * This class contains list of program slots to be sent to Android
 * @author Divahar Sethuraman
 * 
 */
public class ProgramSlots {
    
    private List<ProgramSlot> programSlots;
    private Map<String,String> userIdMap;

    /**
     * Getter for userIDMap
     * 
     * @return Map of user Id and name
     */
    public Map<String, String> getUserIdMap() {
        return userIdMap;
    }

    /**
     * Setter for userIDMap
     *  
     * @param userIdMap User Id and Name map
     */
    public void setUserIdMap(Map<String, String> userIdMap) {
        this.userIdMap = userIdMap;
    }
    
    /**
     * Getter for program slots
     * 
     * @return List of Program slots
     */
    public List<ProgramSlot> getProgramSlots() {
        return programSlots;
    }

    /**
     * Setter for program slots
     * 
     * @param programSlots Program slot list
     */
    public void setProgramSlots(List<ProgramSlot> programSlots) {
        this.programSlots = programSlots;
    }
    
}
