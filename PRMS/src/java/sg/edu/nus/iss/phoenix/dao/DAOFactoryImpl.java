package sg.edu.nus.iss.phoenix.dao;

import sg.edu.nus.iss.phoenix.dao.impl.ProgramDAOImpl;
import sg.edu.nus.iss.phoenix.dao.impl.RoleDaoImpl;
import sg.edu.nus.iss.phoenix.dao.impl.ScheduleDAOImpl;
import sg.edu.nus.iss.phoenix.dao.impl.UserDaoImpl;


public class DAOFactoryImpl implements DAOFactory {
	private UserDao userDAO = new UserDaoImpl();
	private RoleDao roleDAO = new RoleDaoImpl();
	private ProgramDAO rpdao = new ProgramDAOImpl();
        
        // Added for Schedule - By Diva
        private ScheduleDAO scheduleDAO = new ScheduleDAOImpl();        

	@Override
	public UserDao getUserDAO() {
		// TODO Auto-generated method stub
		return userDAO;
	}

	@Override
	public RoleDao getRoleDAO() {
		// TODO Auto-generated method stub
		return roleDAO;
	}

	@Override
	public ProgramDAO getProgramDAO() {
		// TODO Auto-generated method stub
		return rpdao;
	}
        
        @Override
	public ScheduleDAO getScheduleDAO() {
		return scheduleDAO;
	}

}
