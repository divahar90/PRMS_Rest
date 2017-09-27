package sg.edu.nus.iss.phoenix.service;


import java.sql.SQLException;
import java.util.ArrayList;
import sg.edu.nus.iss.phoenix.dao.DAOFactoryImpl;
import sg.edu.nus.iss.phoenix.dao.ProgramDAO;
import sg.edu.nus.iss.phoenix.entity.RadioProgram;
import sg.edu.nus.iss.phoenix.exceptions.NotFoundException;

/**
 *
 * @author divah
 */
public class ProgramService {
	DAOFactoryImpl factory;
	ProgramDAO rpdao;

    /**
     *
     */
    public ProgramService() {
		super();
		// Sorry. This implementation is wrong. To be fixed.
		factory = new DAOFactoryImpl();
		rpdao = factory.getProgramDAO();
	}

    /**
     *
     * @param rpso
     * @return
     */
    public ArrayList<RadioProgram> searchPrograms(RadioProgram rpso) {
		ArrayList<RadioProgram> list = new ArrayList<RadioProgram>();
		try {
			list = (ArrayList<RadioProgram>) rpdao.searchMatching(rpso);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

    /**
     *
     * @param rp
     * @return
     */
    public ArrayList<RadioProgram> findRPByCriteria(RadioProgram rp) {
		ArrayList<RadioProgram> currentList = new ArrayList<RadioProgram>();

		try {
			currentList = (ArrayList<RadioProgram>) rpdao.searchMatching(rp);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return currentList;

	}

    /**
     *
     * @param rpName
     * @return
     */
    public RadioProgram findRP(String rpName) {
		RadioProgram currentrp = new RadioProgram();
		currentrp.setName(rpName);
		try {
			currentrp = ((ArrayList<RadioProgram>) rpdao
					.searchMatching(currentrp)).get(0);
			return currentrp;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return currentrp;

	}

    /**
     *
     * @return
     */
    public ArrayList<RadioProgram> findAllRP() {
		ArrayList<RadioProgram> currentList = new ArrayList<RadioProgram>();
		try {
			currentList = (ArrayList<RadioProgram>) rpdao.loadAll();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return currentList;

	}
        
    /**
     *
     * @param rp
     */
    public void processCreate(RadioProgram rp) {
		try {
			rpdao.create(rp);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

    /**
     *
     * @param rp
     */
    public void processModify(RadioProgram rp) {
		
			try {
				rpdao.save(rp);
			} catch (NotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

    /**
     *
     * @param name
     */
    public void processDelete(String name) {

            try {
                RadioProgram rp = new RadioProgram(name);
                rpdao.delete(rp);
            } catch (NotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
	}

}
