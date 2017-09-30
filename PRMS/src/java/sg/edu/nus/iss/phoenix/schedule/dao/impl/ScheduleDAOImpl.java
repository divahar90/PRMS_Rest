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
 *
 * @author Divahar Sethuraman 
 * This class contains all methods to do operations
 * with program-slot table
 */
public class ScheduleDAOImpl implements ScheduleDAO {

    Connection connection;

    /**
     *
     * @param valueObject
     * @return
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
     *
     * @param valueObject
     * @return
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
     *
     * @param dateOfProg
     * @param strtTime
     * @return
     * @throws NotFoundException
     * @throws SQLException
     */
    @Override
    public boolean delete(Date dateOfProg, String strtTime) throws NotFoundException,
            SQLException {

        boolean isDeleted = true;

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
     *
     * @param dateOfProgram
     * @return
     * @throws SQLException
     */
    @Override
    public synchronized List<ProgramSlot>
            retrieve(Date dateOfProgram) throws SQLException {

        String sql = "SELECT * FROM `program-slot` WHERE (`dateOfProgram` = ? ); ";
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
     *
     * @param dateOfProgram
     * @param strtTime
     * @param endTime
     * @param id
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
     *
     * @param userId
     * @return
     * @throws SQLException
     */
    @Override
    public int
            getSlotCount(String userId) throws SQLException {

        String sql = "SELECT count(*) FROM `program-slot` WHERE "
                + "(`producerId` = ? or `presenterId` = ? and `dateOfProgram` >= CURDATE());";
        PreparedStatement stmt = null;
        ResultSet result = null;
        int allRows = 0;
        openConnection();
        try {

            stmt = connection.prepareStatement(sql);
            stmt.setString(1, userId);
            stmt.setString(2, userId);

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
     *
     * @param stmt
     * @return
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
     *
     * @param stmt
     * @return
     * @throws SQLException
     */
    protected int databaseUpdate(PreparedStatement stmt) throws SQLException {

        int result = stmt.executeUpdate();

        return result;

    }

}
