package sg.edu.nus.iss.phoenix.user.service;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import sg.edu.nus.iss.phoenix.authenticate.entity.Role;
import sg.edu.nus.iss.phoenix.authenticate.service.RoleService;
import sg.edu.nus.iss.phoenix.core.dao.DAOFactoryImpl;
import sg.edu.nus.iss.phoenix.user.dao.UserDao;
import sg.edu.nus.iss.phoenix.user.entity.User;

/**
 *This class is used to handle the operations related to user
 * @author Divahar Sethuraman 
 */
public class UserService {

    DAOFactoryImpl factory;
    UserDao userDao;
    private RoleService roleService
            = null;

    /**
     *
     */
    public UserService() {
        factory = new DAOFactoryImpl();
        userDao = factory.getUserDAO();
        roleService = new RoleService();

    }

    /**
     *
     * @param user User object
     * @return
     */
    public boolean processCreate(User user) {
        boolean isCreate = true;
        try {
            isCreate = userDao.create(user);
            if (isCreate && null != user && null != user.getRoles()
                    && user.getRoles().size() > 0) {
                for (Role role : user.getRoles()) {
                    boolean isRoleCreated = roleService.
                            processCreate(role, user.getId());
                    if (!isRoleCreated) {
                        isCreate = false;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            isCreate = false;
        }

        return isCreate;
    }

    /**
     *
     * @param user user name
     * @param role role
     * @return
     */
    public List<User> processRetrieve(String user, String role) {
        List<User> userList = null;
        try {
            userList = userDao.retrieve(user, role);
        } catch (SQLException e) {
            e.printStackTrace();
            userList = null;
        }

        return userList;
    }

    /**
     *
     * @param user User object
     * @return boolean
     */
    public boolean processUpdate(User user) {
        boolean isUpdate = true;
        try {
            isUpdate = userDao.update(user);

            if (isUpdate
                    && null != user.getRoles() && user.getRoles().size() > 0) {
                boolean isDeleted
                        = roleService.processDelete(user.getId());
                if (isDeleted) {
                    for (Role role : user.getRoles()) {
                        boolean isRoleCreated = roleService.
                                processCreate(role, user.getId());
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            isUpdate = false;
        }

        return isUpdate;
    }

    /**
     *
     * @param userId user Id 
     * @return boolean
     */
    public boolean processDelete(String userId) {
        boolean isDeleted = true;
        try {
            isDeleted = userDao.delete(userId);
        } catch (SQLException e) {
            e.printStackTrace();
            isDeleted = false;
        }

        return isDeleted;
    }

    /**
     *
     * @param idList List of user id
     * @return Map of user id and name
     */
    public Map<String, String> getUserNames(String idList) {
        Map<String, String> userIdMap = null;
        try {
            userIdMap = userDao.getNames(idList);
        } catch (Exception exp) {
            exp.printStackTrace();
        }

        return userIdMap;
    }

}
