package sg.edu.nus.iss.phoenix.authenticate.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import sg.edu.nus.iss.phoenix.authenticate.dao.RoleDao;
import sg.edu.nus.iss.phoenix.authenticate.entity.Role;
import sg.edu.nus.iss.phoenix.core.dao.DBConstants;

/**
 * @author Divahar Sethuraman
 * This class contains all database handling that
 * is needed to permanently store and retrieve Role object instances.
 */
public class RoleDaoImpl implements RoleDao {

    private static final Logger logger = Logger.getLogger(RoleDaoImpl.class.getName());

    Connection connection;

    /**
     *
     */
    public RoleDaoImpl() {
        super();
        // TODO Auto-generated constructor stub
        connection = openConnection();
    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see
	 * sg.edu.nus.iss.phoenix.authenticate.dao.impl.RoleDao#create(java.sql.
	 * Connection, sg.edu.nus.iss.phoenix.authenticate.entity.Role)
         * Modified by Diva - Create Role
     */
    /**
     *
     * @param valueObject
     * @param id
     * @return
     * @throws SQLException
     */
    @Override
    public synchronized boolean create(Role valueObject,
            String id) throws SQLException {

        String sql = "";
        PreparedStatement stmt = null;
        connection = openConnection();
        boolean isCreated = true;
        try {
            sql = "INSERT INTO role ( role, accessPrivilege, userId) "
                    + "VALUES (?, ?, ?) ";
            stmt = this.connection.prepareStatement(sql);

            stmt.setString(1, valueObject.getRole());
            stmt.setString(2, valueObject.getAccessPrivilege());
            stmt.setString(3, id);

            int rowcount = databaseUpdate(stmt);
            if (rowcount != 1) {
                isCreated = false;
            }

        } finally {
            if (stmt != null) {
                stmt.close();
            }
            closeConnection();
        }

        return isCreated;
    }

    /**
     *
     * @param userId
     * @return
     * @throws SQLException
     */
    @Override
    public List<Role> retrieve(String userId) throws SQLException {
        List<Role> roleList = null;
        String sql = "";
        PreparedStatement stmt = null;
        connection = openConnection();

        try {
            sql = "SELECT * FROM `role` WHERE (`userid` = ? ); ";
            stmt = this.connection.prepareStatement(sql);

            stmt.setString(1, userId);

            roleList = listQuery(stmt);

        } finally {
            if (stmt != null) {
                stmt.close();
            }
            closeConnection();
        }
        return roleList;
    }

    /**
     *
     * @param userId
     * @return
     * @throws SQLException
     */
    @Override
    public boolean delete(String userId) throws SQLException {

        String sql = "";
        PreparedStatement stmt = null;
        connection = openConnection();
        boolean isDeleted = true;

        try {
            sql = "delete FROM `role` WHERE (`userid` = ? ); ";
            stmt = this.connection.prepareStatement(sql);

            stmt.setString(1, userId);

            int rowcount = databaseUpdate(stmt);
            if (rowcount == 0) {
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
     * @param stmt
     * @return
     * @throws SQLException
     */
    protected List<Role> listQuery(PreparedStatement stmt) throws SQLException {

        ArrayList<Role> searchResults = new ArrayList<>();
        ResultSet result = null;
        openConnection();
        try {
            result = stmt.executeQuery();
            Role role = null;

            while (result.next()) {
                role = new Role();

                role.setRole(result.getString("role"));
                role.setAccessPrivilege(result.getString("accessPrivilege"));

                searchResults.add(role);
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

    /**
     * databaseUpdate-method. This method is a helper method for internal use.
     * It will execute all database handling that will change the information in
     * tables. SELECT queries will not be executed here however. The return
     * value indicates how many rows were affected. This method will also make
     * sure that if cache is used, it will reset when data changes.
     *
     * @param stmt This parameter contains the SQL statement to be excuted.
     * @return
     * @throws java.sql.SQLException
     */
    protected int databaseUpdate(PreparedStatement stmt) throws SQLException {

        int result = stmt.executeUpdate();

        return result;
    }

    private Connection openConnection() {
        Connection conn = null;

        try {
            Class.forName(DBConstants.COM_MYSQL_JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
        }

        try {
            conn = DriverManager.getConnection(DBConstants.dbUrl,
                    DBConstants.dbUserName, DBConstants.dbPassword);
        } catch (SQLException e) {
        }
        return conn;
    }

    private void closeConnection() {
        try {
            this.connection.close();
        } catch (SQLException e) {
        }
    }

}
