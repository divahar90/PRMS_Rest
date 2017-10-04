/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.schedule.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import sg.edu.nus.iss.phoenix.core.dao.DBConstants;
import sg.edu.nus.iss.phoenix.core.helper.ScheduleHelper;
import sg.edu.nus.iss.phoenix.core.exceptions.NotFoundException;
import sg.edu.nus.iss.phoenix.schedule.dao.ScheduleDAO;
import sg.edu.nus.iss.phoenix.schedule.entity.ProgramSlot;
import java.sql.Time;

/**
 * This class contains all methods to do operations with program-slot table
 * 
 * @author Divahar Sethuraman 
 * 
 */
public class ScheduleDAOImpl implements ScheduleDAO {

    Connection connection;

    /**
     * Method for creating a program slot
     * 
     * @param valueObject Program slot object
     * @return boolean
     * @throws SQLException
     */
    @Override
    public synchronized boolean create(ProgramSlot valueObject)
            throws SQLException {

        String sql = "";
        PreparedStatement stmt = null;
        boolean isCreate = true;
        openConnection();
        try {
            sql = "INSERT INTO `program-slot` (`duration`, `dateOfProgram`, `startTime`, `program-name`,"
                    + "`presenterId`,`producerId`) VALUES (?,?,?,?,?,?); ";
            stmt = connection.prepareStatement(sql);
            stmt.setTime(1, valueObject.getDuration());
            stmt.setDate(2, valueObject.getDateOfProgram());
            stmt.setTime(3, valueObject.getStartTime());
            stmt.setString(4, valueObject.getProgramName());
            stmt.setString(5, valueObject.getPresenterId());
            stmt.setString(6, valueObject.getProducerId());

            int rowcount = databaseUpdate(stmt);
            if (rowcount != 1) {
                isCreate = false;
            }

        } finally {
            if (stmt != null) {
                stmt.close();
            }
            closeConnection();
        }

        return isCreate;

    }

    /**
     * Method for updating a program slot
     *  
     * @param valueObject Program slot object
     * @return boolean
     * @throws SQLException
     */
    @Override
    public synchronized boolean update(ProgramSlot valueObject)
            throws SQLException {

        String sql = "";
        PreparedStatement stmt = null;
        boolean isUpdate = true;
        openConnection();
        try {
            sql = "UPDATE `program-slot` SET `dateOfProgram` = ?, `startTime` = ?, `duration` = ? , `presenterId` = ?, `producerId` = ? "
                    + "WHERE (`program-name` = ? and `id` = ?); ";
            stmt = connection.prepareStatement(sql);
            stmt.setDate(1, valueObject.getDateOfProgram());
            stmt.setTime(2, valueObject.getStartTime());
            stmt.setTime(3, valueObject.getDuration());
            stmt.setString(4, valueObject.getPresenterId());
            stmt.setString(5, valueObject.getProducerId());

            stmt.setString(6, valueObject.getProgramName());
            stmt.setInt(7, valueObject.getId());

            int rowcount = databaseUpdate(stmt);
            if (rowcount == 0) {
                isUpdate = false;
            }
            if (rowcount > 1) {
                isUpdate = false;
            }
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            closeConnection();
        }

        return isUpdate;
    }

    /**
     * Method for deleting a program slot
     * 
     * @param dateOfProg Date of Program slot
     * @param strtTime Start time of program slot
     * @return
     * @throws NotFoundException
     * @throws SQLException
     */
    @Override
    public boolean delete(Date dateOfProg, String strtTime) throws NotFoundException,
            SQLException {

        boolean isDeleted = true;
        
        System.out.println("Date of prog - Del:"+ dateOfProg);
        System.out.println("strtTime - Del:"+ strtTime);

        if (null == dateOfProg && null == strtTime) {
            throw new NotFoundException("Can not delete without Primary-Key!");
        }

        String sql = "DELETE FROM `program-slot` WHERE (`dateOfProgram` = ? and `startTime` = ?); ";
        PreparedStatement stmt = null;
        openConnection();
        try {
            stmt = connection.prepareStatement(sql);
            stmt.setDate(1, dateOfProg);
            stmt.setString(2, strtTime);

            System.out.println(stmt);

            int rowcount = databaseUpdate(stmt);
            if (rowcount == 0) {
                isDeleted = false;
            }
            if (rowcount > 1) {
                isDeleted = false;
            }
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            closeConnection();
        }

        return isDeleted;
    }

    /**
     * Method for retrieving program slots
     * 
     * @param dateOfProgram Date of program slot
     * @return
     * @throws SQLException
     */
    @Override
    public synchronized List<ProgramSlot>
            retrieve(Date dateOfProgram) throws SQLException {

        String sql = "SELECT * FROM `program-slot` WHERE (`dateOfProgram` = ? ) order by startTime asc; ";
        PreparedStatement stmt = null;
        ResultSet result = null;
        boolean conflictFlag = false;
        List<ProgramSlot> progSlots = null;

        openConnection();
        try {
            stmt = connection.prepareStatement(sql);
            stmt.setDate(1, dateOfProgram);

            progSlots = listQuery(stmt);

        } finally {
            if (result != null) {
                result.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            closeConnection();
        }

        return progSlots;
    }

    /**
     * Method for checking conflicts
     * 
     * @param dateOfProgram Date of program slot
     * @param strtTime Start Time of program slot
     * @param endTime End time of slot
     * @param id slot id
     * @return
     * @throws SQLException
     */
    @Override
    public synchronized boolean checkConflicts(Date dateOfProgram,
            int strtTime, int endTime, int id) throws SQLException {

        String sql = "SELECT startTime, duration, id FROM `program-slot` WHERE (`dateOfProgram` = ? ); ";
        PreparedStatement stmt = null;
        ResultSet result = null;
        boolean conflictFlag = false;
        openConnection();
        try {
            stmt = connection.prepareStatement(sql);
            stmt.setDate(1, dateOfProgram);

            result = stmt.executeQuery();

            ScheduleHelper helper = new ScheduleHelper();

            conflictFlag = helper.checkConflicts(result,
                    strtTime, endTime, id);

        } catch (ParseException ex) {
            Logger.getLogger(ScheduleDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (result != null) {
                result.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            closeConnection();
        }

        return conflictFlag;
    }

    /**
     * Method for checking whether program has schedule
     * 
     * @param progName Name of the program
     * 
     * @return @throws SQLException
     */
    @Override
    public int checkProgHasSch(String progName) throws SQLException {

        String sql = "SELECT count(*) FROM `program-slot` WHERE `program-name` = ?"
                + " and `dateOfProgram` >= CURDATE();";
        PreparedStatement stmt = null;
        ResultSet result = null;
        int allRows = 0;
        openConnection();
        try {

            stmt = connection.prepareStatement(sql);
            stmt.setString(1, progName);
            
            System.out.println("Check prog has sch:"+sql);

            result = stmt.executeQuery();

            if (result.next()) {
                allRows = result.getInt(1);
            }
        } finally {
            if (result != null) {
                result.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            closeConnection();
        }
        return allRows;
    }

    /**
     * Method for getting slots assigned for user
     * 
     * @param userId User ID
     * @return int
     * @throws SQLException
     */
    @Override
    public int
            getSlotCount(String userId) throws SQLException {

        String sql = "SELECT count(*) FROM `program-slot` WHERE "
                + "(`producerId` = ? or `presenterId` = ?) and `dateOfProgram` >= CURDATE();";
        PreparedStatement stmt = null;
        ResultSet result = null;
        int allRows = 0;
        openConnection();
        
        System.out.println(userId);
        
        try {

            stmt = connection.prepareStatement(sql);
            stmt.setString(1, userId);
            stmt.setString(2, userId);

            result = stmt.executeQuery();
            
            System.out.println("Del user: "+sql);

            if (result.next()) {
                allRows = result.getInt(1);
            }
        } finally {
            if (result != null) {
                result.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            closeConnection();
        }
        
        System.out.println(allRows);
        return allRows;
    }

    /**
     * Method for checking an annual schedule
     * 
     * @param year Year to be checked
     * @return boolean
     * @throws SQLException
     */
    @Override
    public boolean checkAnnual(int year) throws SQLException {

        String sql = "SELECT count(*) FROM `annual-schedule` WHERE year = " + year + "";
        PreparedStatement stmt = null;
        ResultSet result = null;
        boolean isAvail = false;
        int allRows = 0;

        openConnection();
        try {

            stmt = connection.prepareStatement(sql);
            result = stmt.executeQuery();

            if (result.next()) {
                allRows = result.getInt(1);
            }

            if (allRows > 0) {
                isAvail = true;
            }

        } finally {
            if (result != null) {
                result.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            closeConnection();
        }
        return isAvail;
    }

    /**
     * Method for checking a weekly schedule
     * 
     * @param dateOfProg Date of Program
     * @return boolean
     * @throws SQLException
     */
    @Override
    public boolean checkWeekly(Date dateOfProg) throws SQLException {
        String sql = "SELECT count(*) FROM `weekly-schedule` WHERE startDate = '" + dateOfProg + "';";
        PreparedStatement stmt = null;
        ResultSet result = null;
        boolean isAvail = false;
        int allRows = 0;

        openConnection();
        try {

            stmt = connection.prepareStatement(sql);
            result = stmt.executeQuery();

            if (result.next()) {
                allRows = result.getInt(1);
            }

            if (allRows > 0) {
                isAvail = true;
            }

        } finally {
            if (result != null) {
                result.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            closeConnection();
        }
        return isAvail;
    }

    /**
     * Method for creating an annual schedule
     * 
     * @param year Year to be created
     * @return boolean
     * @throws SQLException
     */
    @Override
    public boolean createAnnualSch(int year)
            throws SQLException {
        String sql = "INSERT INTO `annual-schedule` (`year`) VALUES (?);";
        PreparedStatement stmt = null;
        ResultSet result = null;
        boolean isCreate = false;

        openConnection();
        try {

            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, year);
            
            int rowcount = databaseUpdate(stmt);
            if (rowcount != 1) {
                isCreate = false;
            }

        } finally {
            if (result != null) {
                result.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            closeConnection();
        }
        return isCreate;
    }
    
    /**
     * Method for creating a weekly schedule
     * 
     * @param dateOfWeek Start date of week
     * @return boolean
     * @throws SQLException
     */
    @Override
    public boolean createWeeklySch(Date dateOfWeek)
            throws SQLException {
        String sql = "INSERT INTO `weekly-schedule` (`startDate`) VALUES (?);";
        PreparedStatement stmt = null;
        ResultSet result = null;
        boolean isCreate = false;

        openConnection();
        try {

            stmt = connection.prepareStatement(sql);
            stmt.setDate(1, dateOfWeek);
            
            int rowcount = databaseUpdate(stmt);
            if (rowcount != 1) {
                isCreate = false;
            }

        } finally {
            if (result != null) {
                result.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            closeConnection();
        }
        return isCreate;
    }

    /**
     * Method to process and return list of program slots
     * 
     * @param stmt Prepared statement
     * @return List of program slots
     * @throws SQLException
     */
    protected List<ProgramSlot> listQuery(PreparedStatement stmt) throws SQLException {

        ArrayList<ProgramSlot> searchResults = new ArrayList<>();
        ResultSet result = null;
        openConnection();
        try {
            result = stmt.executeQuery();
            ProgramSlot slot = null;

            while (result.next()) {

                System.out.println("In list query");

                slot = new ProgramSlot();

                slot.setId(result.getInt("id"));
                slot.setDuration(result.getTime("duration"));
                slot.setDateOfProgram(result.getDate("dateOfProgram"));
                slot.setProgramName(result.getString("program-name"));

                //Date myDate=new Date(Time.getTime());     
                System.out.println(result.
                        getTime("startTime").toString());

                System.out.println(
                        Time.valueOf(result.
                                getTime("startTime").toString()));

                slot.setStartTime(result.getTime("startTime"));
                slot.setPresenterId(result.getString("presenterId"));
                slot.setProducerId(result.getString("producerId"));

                searchResults.add(slot);
            }

        } finally {
            if (result != null) {
                result.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            closeConnection();
        }

        return searchResults;
    }

    private void openConnection() {
        try {
            Class.forName(DBConstants.COM_MYSQL_JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        try {
            this.connection = DriverManager.getConnection(DBConstants.dbUrl,
                    DBConstants.dbUserName, DBConstants.dbPassword);
            System.out.println("Connection opened");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private void closeConnection() {
        try {
            this.connection.close();
            System.out.println("Connection closed");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Method to execute DB update
     * 
     * @param stmt Prepared statement
     * @return
     * @throws SQLException
     */
    protected int databaseUpdate(PreparedStatement stmt) throws SQLException {

        int result = stmt.executeUpdate();

        return result;

    }

}
