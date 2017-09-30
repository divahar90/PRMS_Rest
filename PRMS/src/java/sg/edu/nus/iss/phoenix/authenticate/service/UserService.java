package sg.edu.nus.iss.phoenix.authenticate.service;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import sg.edu.nus.iss.phoenix.authenticate.dao.UserDao;
import sg.edu.nus.iss.phoenix.authenticate.entity.User;
import sg.edu.nus.iss.phoenix.core.dao.DAOFactoryImpl;

/**
 *
 * @author Divahar Sethuraman 
 * This class is used to handle the operations
 * related to user
 */
public class UserService {

    DAOFactoryImpl factory;
    UserDao userDao;

    /**
     *
     */
    public UserService() {
        factory = new DAOFactoryImpl();
        userDao = factory.getUserDAO();
    }

    /**
     *
     * @param user
     * @return
     */
    public boolean processCreate(User user) {
        boolean isCreate = true;
        try {
            isCreate = userDao.create(user);
        } catch (SQLException e) {
            e.printStackTrace();
            isCreate = false;
        }

        return isCreate;
    }

    /**
     *
     * @param user
     * @param role
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
     * @param user
     * @return
     */
    public boolean processUpdate(User user) {
        boolean isUpdate = true;
        try {
            isUpdate = userDao.update(user);
        } catch (SQLException e) {
            e.printStackTrace();
            isUpdate = false;
        }

        return isUpdate;
    }

    /**
     *
     * @param userId
     * @return
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
    
    
    public Map<String, String> getUserNames(String idList){
        Map<String, String> userIdMap = null;
        try{
            userIdMap = userDao.getNames(idList);
        }
        catch(Exception exp){
            exp.printStackTrace();
        }
        
        return userIdMap;
    }

}
