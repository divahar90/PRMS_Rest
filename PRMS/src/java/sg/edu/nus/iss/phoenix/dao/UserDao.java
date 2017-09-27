package sg.edu.nus.iss.phoenix.dao;

import java.sql.SQLException;
import java.util.List;
import sg.edu.nus.iss.phoenix.entity.User;

/**
 *
 * @author divah
 */
public interface UserDao {

    /**
     *
     * @param valueObject
     * @return
     * @throws SQLException
     */
    public abstract boolean create(User valueObject)
            throws SQLException;
    
    /**
     *
     * @param user
     * @param role
     * @return
     * @throws SQLException
     */
    public abstract List<User> 
        retrieve(String user,String role) throws SQLException;
        
    /**
     *
     * @param user
     * @return
     * @throws SQLException
     */
    public abstract boolean update(User user) throws SQLException;
    
    /**
     *
     * @param userId
     * @return
     * @throws SQLException
     */
    public abstract boolean delete(String userId) throws SQLException;    
}
