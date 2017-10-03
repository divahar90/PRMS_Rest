/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.user.test;

import java.util.Calendar;
import java.util.List;
import static org.hamcrest.CoreMatchers.equalTo;
import org.junit.Test;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import sg.edu.nus.iss.phoenix.authenticate.entity.Role;
import sg.edu.nus.iss.phoenix.authenticate.entity.User;
import sg.edu.nus.iss.phoenix.authenticate.service.RoleService;
import sg.edu.nus.iss.phoenix.authenticate.service.UserService;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 *
 * @author Divahar Sethuraman
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestUserRoleService {

    static UserService userService
            = null;
    static RoleService roleService
            = null;
    static User user = null;
    static Role role = null;

    @BeforeClass
    public static void setUp() {
        userService
                = new UserService();
        roleService = 
                new RoleService();
        user = new User();
        role = new Role();
    }

    @AfterClass
    public static void tearDown() {
        userService = null;
        roleService = null;
        user = null;
        role = null;
    }

    @Test
    public void method1_create() {

        assignUserData();

        boolean isCreate
                = userService.processCreate(user);

        assertThat(isCreate, equalTo(true));

    }

    @Test
    public void method2_create() {

        assignRoleData();

        boolean isCreate
                = roleService.processCreate(role, "diva90");

        assertThat(isCreate, equalTo(true));

    }

    @Test
    public void method3_retrieve() {

        List<Role> roleList
                = roleService.processRetrieve("diva90");

        role = roleList.get(0);

        assertThat(role.getAccessPrivilege(), equalTo("4"));
        assertThat(role.getRole(), equalTo("admin"));

    }

    @Test
    public void method4_retrieve() {

        Calendar cal = Calendar.getInstance();
        cal.clear(Calendar.HOUR_OF_DAY);
        cal.clear(Calendar.AM_PM);
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);
        cal.set(1990, 11, 17);

        java.sql.Date dob = new java.sql.Date(cal.getTime().getTime());

        List<User> userList
                = userService.processRetrieve("div", "5");

        user = userList.get(0);

        assertThat(user.getId(), equalTo("diva90"));
        assertThat(user.getAddress(), equalTo("SG 120715"));
        assertThat(user.getContact(), equalTo("90857452"));
        assertThat(user.getName(), equalTo("Divahar S"));
        assertThat(user.getPassword(), equalTo("123456"));        

    }

    @Test
    public void method5_update() {

        user.setAddress("SG-120715");
        user.setContact("90857454");

        boolean isUpdate
                = userService.processUpdate(user);

        assertThat(isUpdate, equalTo(true));

    }

    @Test
    public void method7_delete() {

        boolean isDelete
                = roleService.processDelete("diva90");

        assertThat(isDelete, equalTo(true));

    }

    @Test
    public void method8_delete() {

        boolean isDelete
                = userService.processDelete("diva90");

        assertThat(isDelete, equalTo(true));

    }

    private void assignUserData() {

        Calendar cal = Calendar.getInstance();
        cal.clear(Calendar.HOUR_OF_DAY);
        cal.clear(Calendar.AM_PM);
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);
        cal.set(1990, 11, 17);

        java.sql.Date dob = new java.sql.Date(cal.getTime().getTime());

        cal.set(2017, 01, 01);

        java.sql.Date doj = new java.sql.Date(cal.getTime().getTime());

        user.setAddress("SG 120715");
        user.setContact("90857452");
        user.setDob(dob);
        user.setDoj(doj);
        user.setName("Divahar S");
        user.setId("diva90");
        user.setPassword("123456");

    }

    private void assignRoleData() {

        role.setAccessPrivilege("4");
        role.setRole("admin");

    }

}
