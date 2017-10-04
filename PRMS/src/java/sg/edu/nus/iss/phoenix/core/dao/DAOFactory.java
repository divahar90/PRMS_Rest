/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.core.dao;

import sg.edu.nus.iss.phoenix.authenticate.dao.RoleDao;
import sg.edu.nus.iss.phoenix.radioprogram.dao.ProgramDAO;
import sg.edu.nus.iss.phoenix.schedule.dao.ScheduleDAO;
import sg.edu.nus.iss.phoenix.user.dao.UserDao;



/**
 * This interface contains all the DAO references of PRMS
 *  
 * @author User
 */
public interface DAOFactory {

    /**
     *
     * @return
     */
    ProgramDAO getProgramDAO();

    /**
     *
     * @return
     */
    
    RoleDao getRoleDAO();

    /**
     *
     * @return
     */
    UserDao getUserDAO();
        
        // Added for Schedule - By Diva

    /**
     *
     * @return
     */
        ScheduleDAO getScheduleDAO();
	
}
