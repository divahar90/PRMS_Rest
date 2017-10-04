/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.schedule.entity;

import java.sql.Date;

/**
 * This class contains all methods to do operations with Weekly schedule
 * @author Divahar Sethuraman
 * 
 */

public class WeeklySchedule {
    
    private Date strtDate;

    /**
     * Getter for start date of week
     * 
     * @return
     */
    public Date getStrtDate() {
        return strtDate;
    }

    /**
     *
     * @param strtDate
     */
    public void setStrtDate(Date strtDate) {
        this.strtDate = strtDate;
    }

}
