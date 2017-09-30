package sg.edu.nus.iss.phoenix.authenticate.RESTful;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.text.ParseException;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import org.json.JSONObject;
import sg.edu.nus.iss.phoenix.authenticate.entity.Role;
import sg.edu.nus.iss.phoenix.authenticate.entity.User;
import sg.edu.nus.iss.phoenix.authenticate.service.RoleService;
import sg.edu.nus.iss.phoenix.authenticate.service.UserService;
import sg.edu.nus.iss.phoenix.schedule.service.ScheduleService;

/**
 *
 * @author Divahar Sethuraman 
 * This class has all the methods/operations related
 * to User
 *
 */
@Path("user")
@RequestScoped
public class UserRESTService {

    @Context
    private UriInfo context;

    private UserService userService;

    private RoleService roleService;

    private ScheduleService schService;

    /**
     * Creates a new instance of UserRESTService
     */
    public UserRESTService() {
        userService = new UserService();
        roleService = new RoleService();
        schService = new ScheduleService();
    }

    /**
     *
     * @param user
     * @return
     * @throws ParseException
     */
    @PUT
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    public String createUser(User user) throws ParseException {

        boolean isCreate = false;
        JSONObject obj = new JSONObject();
        try {
            isCreate = userService.processCreate(user);
            System.out.println("isCreate: " + isCreate);
            if (isCreate) {
                if (null != user && null != user.getRoles()
                        && user.getRoles().size() > 0) {
                    for (Role role : user.getRoles()) {
                        boolean isRoleCreated = roleService.
                                processCreate(role, user.getId());
                        if (!isRoleCreated) {
                            isCreate = false;
                            obj.put("status", isCreate);
                            obj.put("message", "Role cannot be created for the user");
                            break;
                        }
                    }
                }
            } else {
                obj.put("status", isCreate);
                obj.put("message", "User already exists");
            }
        } catch (Exception exp) {
            exp.printStackTrace();
        }
        return obj.toString();
    }

    /**
     * GET method for retrieving program slots
     *
     * @param user
     * @param role
     * @return
     * @throws java.text.ParseException
     */
    @GET
    @Path("/retrieve")
    @Consumes(MediaType.APPLICATION_JSON)
    public Users retrieveUsers(@QueryParam("user") String user, @QueryParam("role") String role)
            throws ParseException {

        Users users = new Users();
        try {
            if (null != user) {
                List<User> userList
                        = userService.processRetrieve(user, role);
                users.setUsers(userList);
            }
        } catch (Exception exp) {
            exp.printStackTrace();
        }
        return users;
    }

    /**
     * POST method for updating a program slot
     *
     * @param user
     * @return
     * @throws java.text.ParseException
     */
    @POST
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    public String updateUser(User user) throws ParseException {

        boolean isUpdate = false;
        JSONObject obj = new JSONObject();
        try {
            isUpdate
                    = userService.processUpdate(user);
            if (isUpdate
                    && null != user.getRoles() && user.getRoles().size() > 0) {
                boolean isDeleted
                        = roleService.processDelete(user.getId());
                if (isDeleted) {
                    for (Role role : user.getRoles()) {
                        boolean isRoleCreated = roleService.
                                processCreate(role, user.getId());
                    }
                }

                obj.put("status", isUpdate);
                obj.put("message", "User updated successfully");

            } else {
                obj.put("status", isUpdate);
                obj.put("message", "User update operation failed");
            }
        } catch (Exception exp) {
            exp.printStackTrace();
        }

        return obj.toString();
    }

    /**
     * DELETE method for deleting an instance of resource
     *
     * @param userId
     * @return
     */
    @DELETE
    @Path("/delete/{userId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public String deleteUser(@PathParam("userId") String userId) {

        boolean isDeleted = false;
        JSONObject obj = new JSONObject();

        try {
            int slotCount = schService.isAssigned(userId);
            if (slotCount == 0) {
                isDeleted = userService.processDelete(userId);
            }

            if (isDeleted) {
                obj.put("status", isDeleted);
                obj.put("message", "User deleted successfully");
            } else {
                obj.put("status", isDeleted);
                obj.put("message", "User cannot be deleted. Please delete the program slots assigned to the user and proceed");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return obj.toString();
    }
}
