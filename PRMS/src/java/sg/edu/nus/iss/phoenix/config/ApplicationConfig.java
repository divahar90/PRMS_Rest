/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.config;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author User
 */
@javax.ws.rs.ApplicationPath("rest")
public class ApplicationConfig extends Application {

    /**
     *
     * @return
     */
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(sg.edu.nus.iss.phoenix.restful.AuthenticateRESTService.class);
        resources.add(sg.edu.nus.iss.phoenix.restful.ProgramRESTService.class);
        resources.add(sg.edu.nus.iss.phoenix.restful.ScheduleRESTService.class);
        resources.add(sg.edu.nus.iss.phoenix.restful.UserRESTService.class);
    }
    
}