/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.schedule.dao;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import sg.edu.nus.iss.phoenix.core.exceptions.NotFoundException;
import sg.edu.nus.iss.phoenix.schedule.entity.ProgramSlot;

/**
 * This interface has all the methods to perform operations on program-slot
 * table
 *
 * @author Divahar Sethuraman
 *
 */
public interface ScheduleDAO {

    /**
     * Method for creating a program slot
     *
     * @param progSlot
     * @return
     * @throws SQLException
     */
    public abstract boolean create(ProgramSlot progSlot) throws SQLException;

    /**
     * Method for checking conflicts
     *
     * @param dateOfProg Date of Program
     * @param strt Start time
     * @param end End time
     * @param id program slot id, which is used during updating
     * @return
     * @throws SQLException
     */
    public abstract boolean checkConflicts(Date dateOfProg,
            int strt, int end, int id) throws SQLException;

    /**
     * Method for deleting a program slot
     *
     * @param dateOfProg Date of Program
     * @param strtTime Start time
     * @return
     * @throws NotFoundException
     * @throws SQLException
     */
    public abstract boolean delete(Date dateOfProg, String strtTime)
            throws NotFoundException, SQLException;

    /**
     * Method for updating a program slot
     *
     * @param progSlot Program slot object
     * @return
     * @throws NotFoundException
     * @throws SQLException
     */
    public abstract boolean update(ProgramSlot progSlot)
            throws NotFoundException, SQLException;

    /**
     * Method for retrieving list of program slots
     *
     * @param dateOfProg Date of Program
     * @return
     * @throws SQLException
     */
    public abstract List<ProgramSlot>
            retrieve(Date dateOfProg) throws SQLException;

    /**
     * Method for checking whether program has schedule
     *
     * @param progName Program Name
     * @return
     * @throws SQLException
     */
    public abstract int checkProgHasSch(String progName)
            throws SQLException;

    /**
     * Method for getting slots assigned for user
     *
     * @param userId User ID
     * @return
     * @throws SQLException
     */
    public abstract int getSlotCount(String userId)
            throws SQLException;

    /**
     * Method for checking an annual schedule
     *
     * @param year Year to be checked
     * @return
     * @throws SQLException
     */
    public abstract boolean checkAnnual(int year) throws SQLException;

    /**
     * Method for checking a weekly schedule
     *
     * @param progDate Week start date of program slot
     * @return
     * @throws SQLException
     */
    public abstract boolean checkWeekly(Date progDate)
            throws SQLException;

    /**
     * Method for creating an annual schedule
     *
     * @param year Year to be created
     * @return
     * @throws SQLException
     */
    public abstract boolean createAnnualSch(int year)
            throws SQLException;

    /**
     * Method for creating a weekly schedule
     *
     * @param weekDate Start date of the week for a program slot
     * @return
     * @throws SQLException
     */
    public abstract boolean createWeeklySch(Date weekDate)
            throws SQLException;
}
