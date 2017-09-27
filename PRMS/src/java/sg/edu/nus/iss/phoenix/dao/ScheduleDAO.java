/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.dao;


import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import sg.edu.nus.iss.phoenix.entity.ProgramSlot;
import sg.edu.nus.iss.phoenix.exceptions.NotFoundException;


/**
 *
 * @author Divahar Sethuraman
 * This interface has all the methods to perform operations on program-slot table
 */
public interface ScheduleDAO {
    
    /**
     *
     * @param progSlot
     * @return
     * @throws SQLException
     */
    public abstract boolean create(ProgramSlot progSlot) throws SQLException;
    
    /**
     *
     * @param dateOfProg
     * @param strt
     * @param end
     * @param id
     * @return
     * @throws SQLException
     */
    public abstract boolean checkConflicts(Date dateOfProg,
            int strt, int end, int id) throws SQLException;
    
    /**
     *
     * @param dateOfProg
     * @param strtTime
     * @return
     * @throws NotFoundException
     * @throws SQLException
     */
    public abstract boolean delete(Date dateOfProg, String strtTime) 
            throws NotFoundException,SQLException;
    
    /**
     *
     * @param progSlot
     * @return
     * @throws NotFoundException
     * @throws SQLException
     */
    public abstract boolean update (ProgramSlot progSlot) 
            throws NotFoundException,SQLException;
    
    /**
     *
     * @param dateOfProg
     * @return
     * @throws SQLException
     */
    public abstract List<ProgramSlot> 
        retrieve(Date dateOfProg) throws SQLException;
        
    /**
     *
     * @param progName
     * @return
     * @throws SQLException
     */
    public abstract int checkProgHasSch(String progName)
                throws SQLException;
    
    
    
    public abstract int getSlotCount(String userId) throws SQLException;
}
