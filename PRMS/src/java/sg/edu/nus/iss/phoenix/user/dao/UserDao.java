package sg.edu.nus.iss.phoenix.user.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import sg.edu.nus.iss.phoenix.user.entity.User;

/**
 * This interface contains all methods for User DAO
 * @author Divahar Sethuraman
 */
public interface UserDao {

    /**
     *
     * @param valueObject User object
     * @return
     * @throws SQLException
     */
    public abstract boolean create(User valueObject)
            throws SQLException;

    /**
     *
     * @param user user id
     * @param role role 
     * @return
     * @throws SQLException
     */
    public abstract List<User>
            retrieve(String user, String role) throws SQLException;

    /**
     *
     * @param user user object
     * @return
     * @throws SQLException
     */
    public abstract boolean update(User user) throws SQLException;

    /**
     *
     * @param userId user id
     * @return
     * @throws SQLException
     */
    public abstract boolean delete(String userId) throws SQLException;

    /**
     *
     * @param idList list of user Id
     * @return
     * @throws SQLException
     */
    public abstract Map<String, String> getNames(String idList)
            throws SQLException;

    /**
     *
     * @param id user Id
     * @param password password
     * @return
     * @throws SQLException
     */
    public abstract User validateUser(String id,
            String password) throws SQLException;
}
