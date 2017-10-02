package sg.edu.nus.iss.phoenix.authenticate.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import sg.edu.nus.iss.phoenix.authenticate.dao.UserDao;
import sg.edu.nus.iss.phoenix.authenticate.entity.Role;
import sg.edu.nus.iss.phoenix.authenticate.entity.User;
import sg.edu.nus.iss.phoenix.core.common.PRMSConstants;
import sg.edu.nus.iss.phoenix.core.dao.DBConstants;
import sg.edu.nus.iss.phoenix.core.helper.UserHelper;

/**
 * @author Divahar Sethuraman
 * This class contains all database handling that
 * is needed to permanently store and retrieve User object instances.
 */
public class UserDaoImpl implements UserDao {

    private static final String DELIMITER = ":";
    private static final Logger logger = Logger.getLogger(UserDaoImpl.class.getName());

    Connection connection;
    
    /*
	 * (non-Javadoc)
	 * 
	 * @see
	 * sg.edu.nus.iss.phoenix.authenticate.dao.impl.UserDao#create(java.sql.
	 * Connection, sg.edu.nus.iss.phoenix.authenticate.entity.User)
         * Modified by Diva - Create User
     */
    /**
     *
     * @param valueObject
     * @return
     * @throws SQLException
     */
    @Override
    public synchronized boolean create(User valueObject) throws SQLException {

        String sql = "";
        PreparedStatement stmt = null;
        boolean isCreated = true;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        openConnection();

        try {
            sql = "INSERT INTO user ( id, password, name, "
                    + "contact,address,dob,doj,isActive) VALUES (?, ?, ?, ?, ?, ?, ?, ?) ";
            stmt = this.connection.prepareStatement(sql);

            stmt.setString(1, valueObject.getId());
            stmt.setString(2, valueObject.getPassword());
            stmt.setString(3, valueObject.getName());
            stmt.setString(4, valueObject.getContact());
            stmt.setString(5, valueObject.getAddress());
            Date d = new java.sql.Date(sdf.parse(valueObject.getDob()).getTime());
            stmt.setDate(6, d);
            d = new java.sql.Date(sdf.parse(valueObject.getDoj()).getTime());
            stmt.setDate(7, d);
            stmt.setBoolean(8, true);

            int rowcount = databaseUpdate(stmt);
            if (rowcount != 1) {
                isCreated = false;
            }

        } catch (ParseException ex) {
            Logger.getLogger(UserDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
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
     * @param user
     * @param role
     * @return
     * @throws SQLException
     */
    @Override
    public List<User> retrieve(String user, String role) throws SQLException {
        List<User> userList = null;
        PreparedStatement stmt = null;
        StringBuilder sql = new StringBuilder();  
        openConnection();
                
        try {
            if (null != user
                    && user.equalsIgnoreCase("all") && role.equalsIgnoreCase("5")) {
                sql.append("SELECT u.*,r.* FROM `user` u,`role` r where "
                        + "u.id = r.userid and u.isActive = true;");
            } else if (null != user
                    && !user.equalsIgnoreCase("all") && role.equalsIgnoreCase("5")) {
                sql.append("SELECT u.*,r.* FROM `user` u,`role` r WHERE (u.name LIKE '%" + user + "%') "
                        + "and u.id = r.userid and u.isActive = true;");
            } else if (null != user
                    && !user.equalsIgnoreCase("all") && !role.equalsIgnoreCase("5")) {

                String roleKey = PRMSConstants.roleMap.get(role);
                sql.append("SELECT u.*,r.* FROM `user` u,`role` r WHERE (u.name LIKE '%" + user + "%') "
                        + "and r.role='" + roleKey + "' and u.id = r.userid and u.isActive = true;");
            } else if (!role.equalsIgnoreCase("5")) {

                String roleKey = PRMSConstants.roleMap.get(role);
                sql.append("SELECT u.*,r.* FROM `user` u,`role` r WHERE u.id = "
                        + "r.userid and r.role='" + roleKey + "' and u.isActive = true;");
            }

            System.out.println(sql.toString());
            stmt = this.connection.prepareStatement(sql.toString());

            userList = listQuery(stmt);

        } finally {
            if (stmt != null) {
                stmt.close();
            }
            closeConnection();
        }

        return userList;
    }

    /**
     *
     * @param user
     * @return
     * @throws SQLException
     */
    @Override
    public synchronized boolean update(User user)
            throws SQLException {

        String sql = "";
        PreparedStatement stmt = null;
        boolean isUpdate = true;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        openConnection();

        try {
            sql = "UPDATE `user` SET `password` = ?, `name` = ?, `contact` = ? , `address` = ? , `dob` = ?, `doj` = ?"
                    + " WHERE (`id` = ?); ";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, user.getPassword());
            stmt.setString(2, user.getName());
            stmt.setString(3, user.getContact());
            stmt.setString(4, user.getAddress());
            Date dob = new java.sql.Date(sdf.parse(user.getDob()).getTime());
            stmt.setDate(5, dob);
            Date doj = new java.sql.Date(sdf.parse(user.getDoj()).getTime());
            stmt.setDate(6, doj);
            stmt.setString(7, user.getId());

            int rowcount = databaseUpdate(stmt);
            if (rowcount == 0) {
                isUpdate = false;
            }
            if (rowcount > 1) {
                isUpdate = false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.getLogger(UserDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
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
     * @param userId
     * @return
     * @throws SQLException
     */
    @Override
    public synchronized boolean delete(String userId)
            throws SQLException {

        boolean isDeleted = true;
        String sql = "";
        PreparedStatement stmt = null;
        openConnection();

        try {
            sql = "update `user` set isActive = false WHERE (`id` = ? ); ";
            stmt = this.connection.prepareStatement(sql);

            stmt.setString(1, userId);

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

    @Override
    public synchronized User validateUser(String userId,
            String password)
            throws SQLException {

        User user = null;
        PreparedStatement stmt = null;
        String sql = "";
        openConnection();

        try {
            if (null != userId
                    && null != password) {
                sql = "SELECT u.*,r.* FROM `user` u,`role` r where u.id = '" + userId + "' and u.password = '" + password + "'"
                        + " and u.id = r.userid and u.isActive = true;";
                
                System.out.println(sql);

                stmt = this.connection.prepareStatement(sql);
                
                List<User> 
                        userList = listQuery(stmt);
                
                if(null!=userList && userList.size()>0)
                    user = userList.get(0);
                    
            }
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            closeConnection();
        }
        
        return user;
    }

    /**
     *
     * @param stmt
     * @return
     * @throws SQLException
     */
    protected List<User> listQuery(PreparedStatement stmt) throws SQLException {

        ResultSet result = null;        
        List<User> userList = null;
        try {
            result = stmt.executeQuery();
            User user = null;
            Role role = null;
            HashMap<String, User> userMap
                    = new HashMap<>();
            HashMap<String, List<Role>> roleMap
                    = new HashMap<>();

            while (result.next()) {

                if (!userMap.
                        containsKey(result.getString("id"))) {
                    user = new User();
                    user.setId(result.getString("id"));
                    user.setName(result.getString("name"));
                    user.setContact(result.getString("contact"));
                    user.setAddress(result.getString("address"));
                    user.setDob(result.getDate("dob").toString());
                    user.setDoj(result.getDate("doj").toString());
                    user.setPassword(result.getString("password"));

                    userMap.put(result.getString("id"),
                            user);
                }

                if (roleMap.containsKey(result.
                        getString("userid"))) {

                    List<Role> roleList = roleMap.get(result.
                            getString("userid"));

                    role = new Role();

                    role.setRole(result.getString("role"));
                    role.setAccessPrivilege(result.getString("accessPrivilege"));

                    roleList.add(role);
                    roleMap.put(result.
                            getString("userid"), roleList);

                } else {
                    List<Role> roleList
                            = new ArrayList<>();
                    role = new Role();

                    role.setRole(result.getString("role"));
                    role.setAccessPrivilege(result.getString("accessPrivilege"));

                    roleList.add(role);

                    roleMap.put(result.
                            getString("userid"), roleList);
                }

            }

            userList = UserHelper.
                    getUserList(userMap, roleMap);

        } finally {
            if (result != null) {
                result.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            closeConnection();
        }

        return userList;
    }

    @Override
    public Map<String, String>
            getNames(String idList) throws SQLException {

        String sql = "";
        PreparedStatement stmt = null;
        openConnection();
        ResultSet result = null;
        Map<String, String> idNameMap
                = new HashMap<>();

        try {

            sql = "select id,name from `user` where `id` in (" + idList + "); ";
            stmt = this.connection.prepareStatement(sql);

            System.out.println(sql);

            result = stmt.executeQuery();

            while (result.next()) {
                idNameMap.put(result.getString("id"),
                        result.getString("name"));
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

        return idNameMap;
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
}
