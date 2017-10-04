package sg.edu.nus.iss.phoenix.authenticate.dao;

import java.sql.SQLException;
import java.util.List;
import sg.edu.nus.iss.phoenix.authenticate.entity.Role;

/**
 * This class contains all the DAO operations related to Role
 * @author Divahar Sethuraman
 */
public interface RoleDao {

    /**
     *
     * @param valueObject
     * @param id
     * @return
     * @throws SQLException
     */
    public abstract boolean create(Role valueObject,
            String id)
            throws SQLException;

    /**
     *
     * @param userId
     * @return
     * @throws SQLException
     */
    public abstract List<Role> retrieve(String userId)
            throws SQLException;

    /**
     *
     * @param userId
     * @return
     * @throws SQLException
     */
    public abstract boolean delete(String userId)
            throws SQLException;
}
