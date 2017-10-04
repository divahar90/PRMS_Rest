/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.authenticate.RESTful;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import sg.edu.nus.iss.phoenix.authenticate.service.AuthenticateService;
import sg.edu.nus.iss.phoenix.user.entity.User;

/**
 * REST Web Service
 *
 * @author User
 */
@Path("/login")
@RequestScoped
public class AuthenticateRESTService {

    @Context
    private UriInfo context;

    private AuthenticateService service;

    /**
     *
     */
    public AuthenticateRESTService() {
        service = new AuthenticateService();
    }

    /**
     * Checks and validates the user name and password
     *
     * @param uname User name
     * @param pwd Password
     * @return an instance of java.lang.String
     */
    @GET
    // Path: http://localhost/<appln-folder-name>/login/dologin
    @Path("/authenticate")
    @Produces(MediaType.APPLICATION_JSON)
    public AuthInfo doLogin(@QueryParam("username") String uname,
            @QueryParam("password") String pwd) {
        AuthInfo response = new AuthInfo();
        User user = service.validateUserIdPassword(uname, pwd);

        if (null != user
                && null != user.getId()) {
            response.setAuthStatus(true);
            response.setUser(user);
        } else {
            response.setAuthStatus(false);
            response.setUser(null);
        }

        return response;
    }  
}
