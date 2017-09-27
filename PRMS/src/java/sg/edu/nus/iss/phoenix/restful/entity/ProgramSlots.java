package sg.edu.nus.iss.phoenix.restful.entity;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.List;
import sg.edu.nus.iss.phoenix.entity.ProgramSlot;


/**
 *
 * @author Divahar Sethuraman
 * This class contains list of program slots to be sent to Android
 */
public class ProgramSlots {
    
    private List<ProgramSlot> programSlots;

    /**
     *
     * @return
     */
    public List<ProgramSlot> getProgramSlots() {
        return programSlots;
    }

    /**
     *
     * @param programSlots
     */
    public void setProgramSlots(List<ProgramSlot> programSlots) {
        this.programSlots = programSlots;
    }
    
}
