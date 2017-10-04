/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.user.RESTful;
import java.util.List;
import sg.edu.nus.iss.phoenix.user.entity.User;

/**
 * This class is used to send the user details to Android
 * @author Divahar Sethuraman
 * 
 */
public class Users {
    
    private List<User> users;

    /**
     *
     * @return
     */
    public List<User> getUsers() {
        return users;
    }

    /**
     *
     * @param users list of users
     */
    public void setUsers(List<User> users) {
        this.users = users;
    }
}
