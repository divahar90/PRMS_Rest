package sg.edu.nus.iss.phoenix.authenticate.service;

import java.sql.SQLException;
import java.util.logging.*;
import sg.edu.nus.iss.phoenix.authenticate.dao.RoleDao;
import sg.edu.nus.iss.phoenix.core.dao.DAOFactoryImpl;
import sg.edu.nus.iss.phoenix.user.dao.UserDao;
import sg.edu.nus.iss.phoenix.user.entity.User;

/**
 * 
 * This class has all the methods related to Authenticate operation
 *
 * @author Divahar Sethuraman
 */
public class AuthenticateService {

    private static final Logger logger
            = Logger.getLogger(AuthenticateService.class.getName());

    DAOFactoryImpl factory;
    UserDao udao;
    RoleDao rdao;

    /**
     *
     */
    public AuthenticateService() {
        super();
        factory = new DAOFactoryImpl();
        udao = factory.getUserDAO();
        rdao = factory.getRoleDAO();

    }

    /**
     *
     * @param id User ID
     * @param password Password
     * @return
     */
    public User validateUserIdPassword(String id,
            String password) {

        User user = null;
        try {
            user = udao.validateUser(id, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
}
