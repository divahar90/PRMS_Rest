/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.core.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import sg.edu.nus.iss.phoenix.authenticate.entity.Role;
import sg.edu.nus.iss.phoenix.authenticate.entity.User;

/**
 *
 * @author Divahar Sethuraman 
 * This class contains the helper methods for user
 */
public class UserHelper {

    /**
     *
     * @param userMap
     * @param roleMap
     * @return
     */
    public static List<User>
            getUserList(HashMap<String, User> userMap,
                    HashMap<String, List<Role>> roleMap) {

        List<User> userList
                = new ArrayList<>();

        if (null != userMap
                && null != roleMap) {
            for (String key : userMap.keySet()) {
                User user = userMap.get(key);

                user.setRoles((ArrayList) roleMap.get(key));

                userList.add(user);
            }
        }

        return userList;
    }
}
