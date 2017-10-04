package sg.edu.nus.iss.phoenix.authenticate.RESTful;

import sg.edu.nus.iss.phoenix.user.entity.User;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author divah
 */


public class AuthInfo {

    private User user;
    private boolean authStatus;

    /**
     *
     * @return
     */
    public User getUser() {
        return user;
    }

    /**
     *
     * @param user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     *
     * @return
     */
    public boolean isAuthStatus() {
        return authStatus;
    }

    /**
     *
     * @param authStatus
     */
    public void setAuthStatus(boolean authStatus) {
        this.authStatus = authStatus;
    }

}
