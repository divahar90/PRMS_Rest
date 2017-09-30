/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.schedule.entity;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;

/**
 *
 * @author Divahar Sethuraman
 * This class contains all methods to do operations with program-slot table
 */

public class ProgramSlot implements Cloneable, Serializable{
    
    private int id;
    private Time duration;
    private Date dateOfProgram;
    private Time startTime;
    private String programName;
    private String presenterId;
    private String producerId;

    public Time getDuration() {
        return duration;
    }

    public void setDuration(Time duration) {
        this.duration = duration;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }
        

    /**
     *
     */
    public ProgramSlot(){
        
    }

    /**
     *
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }
       
    

    /**
     *
     * @return
     */
    public Date getDateOfProgram() {
        return dateOfProgram;
    }

    /**
     *
     * @param dateOfProgram
     */
    public void setDateOfProgram(Date dateOfProgram) {
        this.dateOfProgram = dateOfProgram;
    }
    
    
    /**
     *
     * @return
     */
    public String getProgramName() {
        return programName;
    }

    /**
     *
     * @param programName
     */
    public void setProgramName(String programName) {
        this.programName = programName;
    }

    /**
     *
     * @return
     */
    public String getPresenterId() {
        return presenterId;
    }

    /**
     *
     * @param presenterId
     */
    public void setPresenterId(String presenterId) {
        this.presenterId = presenterId;
    }

    /**
     *
     * @return
     */
    public String getProducerId() {
        return producerId;
    }

    /**
     *
     * @param producerId
     */
    public void setProducerId(String producerId) {
        this.producerId = producerId;
    }
 
}
