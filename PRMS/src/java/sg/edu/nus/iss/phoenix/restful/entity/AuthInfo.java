package sg.edu.nus.iss.phoenix.restful.entity;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import sg.edu.nus.iss.phoenix.restful.*;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author divah
 */
@XmlRootElement
/**
 *
 * @author User
 */
public class AuthInfo {
   private String username;
    private boolean authStatus;
    private String role;
    
    /**
     *
     * @return
     */
    public String getUsername() {
        return username;
    }

    /**
     *
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
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

    /**
     *
     * @return
     */
    public String getRole() {
        return role;
    }

    /**
     *
     * @param role
     */
    public void setRole(String role) {
        this.role = role;
    }
  
}
