/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.service;

import java.sql.SQLException;
import java.util.List;
import sg.edu.nus.iss.phoenix.dao.DAOFactoryImpl;
import sg.edu.nus.iss.phoenix.dao.RoleDao;
import sg.edu.nus.iss.phoenix.entity.Role;

/**
 *
 * @author Divahar Sethuraman 
 * This class is used to handle the operations
 * related to user role
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
     * @param role
     * @param id
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
