/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.schedule.entity;

import java.sql.Date;

/**
 *
 * @author Divahar Sethuraman
 * This class contains all methods to do operations with Weekly schedule
 */

public class WeeklySchedule {
    
    private Date strtDate;
    private String assignedBy;

    public Date getStrtDate() {
        return strtDate;
    }

    public void setStrtDate(Date strtDate) {
        this.strtDate = strtDate;
    }

    public String getAssignedBy() {
        return assignedBy;
    }

    public void setAssignedBy(String assignedBy) {
        this.assignedBy = assignedBy;
    }
    
    
}
