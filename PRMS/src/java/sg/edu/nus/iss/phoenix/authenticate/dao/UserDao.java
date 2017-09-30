package sg.edu.nus.iss.phoenix.authenticate.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import sg.edu.nus.iss.phoenix.authenticate.entity.User;

/**
 *
 * @author Divahar Sethuraman
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
            retrieve(String user, String role) throws SQLException;

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

    public abstract Map<String, String> getNames(String idList)
            throws SQLException;

    public abstract User validateUser(String id,
            String password) throws SQLException;
}
