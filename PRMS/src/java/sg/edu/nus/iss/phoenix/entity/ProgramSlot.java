/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.entity;

import java.io.Serializable;
import java.sql.Date;

/**
 *
 * @author Divahar Sethuraman
 * This class contains all methods to do operations with program-slot table
 */

public class ProgramSlot implements Cloneable, Serializable{
    
    private int id;
    private float duration;
    private Date dateOfProgram;
    private String startTime;
    private String programName;
    private String presenterId;
    private String producerId;

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
    public float getDuration() {
        return duration;
    }

    /**
     *
     * @param duration
     */
    public void setDuration(float duration) {
        this.duration = duration;
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
    public String getStartTime() {
        return startTime;
    }

    /**
     *
     * @param startTime
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
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
