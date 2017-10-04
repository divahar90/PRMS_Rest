/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.schedule.service;

import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import sg.edu.nus.iss.phoenix.core.dao.DAOFactoryImpl;
import sg.edu.nus.iss.phoenix.core.helper.ScheduleHelper;
import sg.edu.nus.iss.phoenix.schedule.dao.ScheduleDAO;
import sg.edu.nus.iss.phoenix.schedule.entity.ProgramSlot;

/**
 * This is a service class that creates the schedule dao object and call the
 * methods appropriately
 *
 * @author Divahar Sethuraman
 *
 */
public class ScheduleService {

    DAOFactoryImpl factory;
    ScheduleDAO scheduleDAO;

    /**
     * Constructor for Schedule Service
     */
    public ScheduleService() {
        factory = new DAOFactoryImpl();
        scheduleDAO = factory.getScheduleDAO();
    }

    /**
     * Method to create a program slot
     * 
     * @param ps program slot object
     * @return
     * @throws java.text.ParseException
     */
    public boolean processCreate(ProgramSlot ps) throws ParseException {
        boolean isCreate = false;
        boolean isInAnnual = false;
        boolean isInWeekly = false;
        boolean conflictFlag = false;

        try {

            Calendar cal = Calendar.getInstance();
            String strtTime = String.valueOf(ps.getStartTime().getHours()) + ":"
                    + String.valueOf(ps.getStartTime().getMinutes());

            if (ps.getStartTime().getMinutes() == 0) {
                strtTime = strtTime + "0";
            }

            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            java.util.Date date = sdf.parse(strtTime);
            cal.setTime(date);

            int durationInt = (ps.getDuration().
                    getHours() * 60) + ps.getDuration().getMinutes();

            cal.add(Calendar.MINUTE, (durationInt - 1));

            String endTime = sdf.format(cal.getTime());

            int strtTimeInt = Integer.
                    valueOf(strtTime.replaceAll(":", ""));

            int schYear = ScheduleHelper.
                    getYear(ps.getDateOfProgram()); // Get the year of program slot

            System.out.println("schYear: " + schYear);

            isInAnnual = chkProgYear(schYear);

            java.util.Date strtDay = ScheduleHelper.
                    getStrtDate(ps.getDateOfProgram());

            java.sql.Date sqlDate
                    = new java.sql.Date(strtDay.getTime());

            if (!isInAnnual) {
                createAnnualSch(schYear);
            }

            isInWeekly
                    = chkProgSchWeek(new java.sql.Date(strtDay
                            .getTime()));

            if (!isInWeekly) {
                createWeeklySch(sqlDate);
            } else {
                conflictFlag
                        = checkConflicts(ps.getDateOfProgram(), strtTimeInt,
                                Integer.valueOf(endTime.replaceAll(":", "")), 0);
            }

            System.out.println(conflictFlag);

            if (!conflictFlag) {
                isCreate = scheduleDAO.create(ps);
                isCreate = true;
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return isCreate;
    }

    /**
     * Method to update a program slot
     *  
     * @param ps program slot object
     * @return boolean
     */
    public boolean processUpdate(ProgramSlot ps) {
        boolean isUpdate = false;
        boolean isInAnnual = false;
        boolean isInWeekly = false;
        boolean conflictFlag = false;

        try {
            Calendar cal = Calendar.getInstance();
            String strtTime = String.valueOf(ps.getStartTime().getHours()) + ":"
                    + String.valueOf(ps.getStartTime().getMinutes());
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            java.util.Date date = sdf.parse(strtTime);
            cal.setTime(date);
            if (ps.getStartTime().getMinutes() == 0) {
                strtTime = strtTime + "0";
            }
            int durationInt = (ps.getDuration().
                    getHours() * 60) + ps.getDuration().getMinutes();

            System.out.println(durationInt);

            cal.add(Calendar.MINUTE, (durationInt - 1));

            String endTime = sdf.format(cal.getTime());

            int strtTimeInt = Integer.
                    valueOf(strtTime.replaceAll(":", ""));

            int schYear = ScheduleHelper.
                    getYear(ps.getDateOfProgram()); // Get the year of program slot

            System.out.println("schYear: " + schYear);

            isInAnnual = chkProgYear(schYear);

            java.util.Date strtDay = ScheduleHelper.
                    getStrtDate(ps.getDateOfProgram());

            java.sql.Date sqlDate
                    = new java.sql.Date(strtDay.getTime());

            System.out.println("sqlDate: " + sqlDate);

            if (!isInAnnual) {
                createAnnualSch(schYear);
            }

            isInWeekly
                    = chkProgSchWeek(new java.sql.Date(strtDay
                            .getTime()));

            if (!isInWeekly) {
                createWeeklySch(sqlDate);
            } else {
                conflictFlag
                        = checkConflicts(ps.getDateOfProgram(), strtTimeInt,
                                Integer.valueOf(endTime.replaceAll(":", "")), ps.getId());
            }

            System.out.println(conflictFlag);

            if (!conflictFlag) {
                isUpdate = scheduleDAO.update(ps);
                isUpdate = true;
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return isUpdate;
    }

    /**
     * Method to delete a program slot
     * 
     * @param dateOfProg Date of Program slot
     * @param strtTime Start time of program slot
     * @return boolean
     */
    public boolean processDelete(String dateOfProg,
            String strtTime) {
        boolean isDeleted = true;
        try {
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date date = sdf1.parse(dateOfProg);
            java.sql.Date dateProg = new java.sql.Date(date.getTime());

            isDeleted = scheduleDAO.delete(dateProg, strtTime);
        } catch (Exception exp) {

        }

        return isDeleted;
    }

    /**
     * Method to retrieve program slots
     * 
     * @param dateOfProg Date of Program
     * @return list of program slots
     */
    public List<ProgramSlot> processRetrieve(String dateOfProg) {
        List<ProgramSlot> slots = null;

        try {
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date date = sdf1.parse(dateOfProg);
            java.sql.Date dateProg = new java.sql.Date(date.getTime());

            slots = scheduleDAO.retrieve(dateProg);
        } catch (Exception exp) {

        }
        return slots;
    }

    /**
     * Method to check conflicts for a program slot
     * 
     * @param dateOfProg Date of Program slot
     * @param strt Start time
     * @param end End time
     * @param id slot id
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
     * Method to whether a program has a schedule
     * 
     * @param progName Program name
     * @return int
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
     * Method to check if a user is assigned to a slot
     * 
     * @param userId User ID
     * @return int
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

    /**
     * Method to check annual schedule
     * 
     * @param year year to be checked
     * @return boolean
     */
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

    /**
     * Method to check a weekly schedule
     * 
     * @param dateOfProg Date of Program
     * @return boolean
     */
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

    /**
     * Method to create annual schedule
     * 
     * @param year Year to be created
     * @return
     */
    public boolean createAnnualSch(int year) {
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

    /**
     * Method to create a weekly schedule
     * 
     * @param strtDate Start date of the week
     * @return
     */
    public boolean createWeeklySch(Date strtDate) {
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
