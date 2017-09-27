/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.common;

import java.util.HashMap;

/**
 *
 * @author Divahar Sethuraman
 */
public class PRMSConstants {
    
    /**
     *
     */
    public static final 
            HashMap<String,String> roleMap = new HashMap<>();
    
    static{
        roleMap.put("1", "presenter");
        roleMap.put("2", "producer");
        roleMap.put("3", "manager");
        roleMap.put("4", "admin");
        roleMap.put("5", "all");
    }
    
}
