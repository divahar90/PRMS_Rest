/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.schedule.service;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import sg.edu.nus.iss.phoenix.core.dao.DAOFactoryImpl;
import sg.edu.nus.iss.phoenix.schedule.dao.ScheduleDAO;
import sg.edu.nus.iss.phoenix.schedule.entity.ProgramSlot;

/**
 *
 * @author Divahar Sethuraman 
 * This is a service class that creates the schedule
 * dao object and call the methods appropriately
 */
public class ScheduleService {

    DAOFactoryImpl factory;
    ScheduleDAO scheduleDAO;

    /**
     *
     */
    public ScheduleService() {
        factory = new DAOFactoryImpl();
        scheduleDAO = factory.getScheduleDAO();
    }

    /**
     *
     * @param ps
     * @return
     */
    public boolean processCreate(ProgramSlot ps) {
        boolean isCreate = true;
        try {
            isCreate = scheduleDAO.create(ps);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return isCreate;
    }

    /**
     *
     * @param ps
     * @return
     */
    public boolean processUpdate(ProgramSlot ps) {
        boolean isUpdate = true;

        try {
            isUpdate = scheduleDAO.update(ps);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return isUpdate;
    }

    /**
     *
     * @param dateOfProg
     * @param strtTime
     * @return
     */
    public boolean processDelete(Date dateOfProg, String strtTime) {
        boolean isDeleted = true;
        try {
            isDeleted = scheduleDAO.delete(dateOfProg, strtTime);
        } catch (Exception exp) {

        }

        return isDeleted;
    }

    /**
     *
     * @param dateOfProg
     * @return
     */
    public List<ProgramSlot> retrieve(Date dateOfProg) {
        List<ProgramSlot> slots = null;
        try {
            slots = scheduleDAO.retrieve(dateOfProg);
        } catch (Exception exp) {

        }
        return slots;
    }

    /**
     *
     * @param dateOfProg
     * @param strt
     * @param end
     * @param id
     * @return
     */
    public boolean checkConflicts(Date dateOfProg,
            int strt, int end, int id) {
        boolean conflict = false;
        try {
            conflict = scheduleDAO.checkConflicts(dateOfProg,
                    strt, end, id);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return conflict;
    }

    /**
     *
     * @param progName
     * @return
     */
    public int checkProgHasSch(String progName) {
        int nbrRows = 0;
        try {
            nbrRows = scheduleDAO.checkProgHasSch(progName);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return nbrRows;
    }

    /**
     *
     * @param userId
     * @return
     */
    public int isAssigned(String userId) {
        int slotCount = 0;
        try {
            slotCount = scheduleDAO.
                    getSlotCount(userId);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return slotCount;
    }

    public boolean chkProgYear(int year) {
        boolean isAvail = false;
        try {
            isAvail = scheduleDAO.
                    checkAnnual(year);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return isAvail;
    }

    public boolean chkProgSchWeek(Date dateOfProg) {
        boolean isAvail = false;
        try {
            isAvail = scheduleDAO.
                    checkWeekly(dateOfProg);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return isAvail;
    }

    public boolean createAnnualSch(int year,
            String assignedBy) {
        boolean isCreated = false;
        try {
            isCreated = scheduleDAO.
                    createAnnualSch(year);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return isCreated;
    }

    public boolean createWeeklySch(Date strtDate,
            String assignedBy) {
        boolean isCreated = false;
        try {
            isCreated = scheduleDAO.
                    createWeeklySch(strtDate);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return isCreated;
    }

}
