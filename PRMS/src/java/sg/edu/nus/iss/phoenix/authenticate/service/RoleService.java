package sg.edu.nus.iss.phoenix.authenticate.service;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.SQLException;
import java.util.List;
import sg.edu.nus.iss.phoenix.authenticate.dao.RoleDao;
import sg.edu.nus.iss.phoenix.authenticate.entity.Role;
import sg.edu.nus.iss.phoenix.core.dao.DAOFactoryImpl;


/**
 *This class is used to handle the operations related to user role
 * 
 * @author Divahar Sethuraman 
 * 
 */
public class RoleService {

    DAOFactoryImpl factory;
    RoleDao roleDao;

    /**
     *
     */
    public RoleService() {
        factory = new DAOFactoryImpl();
        roleDao = factory.getRoleDAO();
    }

    /**
     *
     * @param role Role Object
     * @param id User ID
     * @return
     */
    public boolean processCreate(Role role, String id) {
        boolean isCreate = true;
        try {
            isCreate = roleDao.create(role, id);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return isCreate;
    }

    /**
     *
     * @param userId
     * @return
     */
    public List<Role> processRetrieve(String userId) {
        List<Role> roleList = null;
        try {
            roleList = roleDao.retrieve(userId);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return roleList;
    }

    /**
     *
     * @param userId
     * @return
     */
    public boolean processDelete(String userId) {
        boolean isDeleted = true;
        try {
            isDeleted = roleDao.delete(userId);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return isDeleted;
    }
}
