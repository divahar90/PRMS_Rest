/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.core.config;

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
        resources.add(sg.edu.nus.iss.phoenix.authenticate.RESTful.AuthenticateRESTService.class);
        resources.add(sg.edu.nus.iss.phoenix.radioprogram.RESTful.ProgramRESTService.class);
        resources.add(sg.edu.nus.iss.phoenix.schedule.RESTful.ScheduleRESTService.class);
        resources.add(sg.edu.nus.iss.phoenix.user.RESTful.UserRESTService.class);
    }
    
}
