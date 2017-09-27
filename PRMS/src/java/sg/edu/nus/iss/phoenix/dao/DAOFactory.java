/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.dao;


/**
 *
 * @author projects
 */
public interface DAOFactory {

	ProgramDAO getProgramDAO();

	RoleDao getRoleDAO();

	UserDao getUserDAO();
        
        // Added for Schedule - By Diva
        ScheduleDAO getScheduleDAO();
	
}
