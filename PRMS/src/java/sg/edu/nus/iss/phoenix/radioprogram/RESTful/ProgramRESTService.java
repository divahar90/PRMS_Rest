/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.radioprogram.RESTful;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import org.json.JSONObject;
import sg.edu.nus.iss.phoenix.radioprogram.entity.RadioProgram;
import sg.edu.nus.iss.phoenix.radioprogram.service.ProgramService;
import sg.edu.nus.iss.phoenix.schedule.service.ScheduleService;

/**
 * REST Web Service
 *
 * @author User
 */
@Path("radioprogram")
@RequestScoped
public class ProgramRESTService {

    @Context
    private UriInfo context;

    private ProgramService service;

    private ScheduleService schService;

    /**
     * Creates a new instance of RadioProgramRESTService
     */
    public ProgramRESTService() {
        service = new ProgramService();
        schService = new ScheduleService();
    }

    /**
     * Retrieves representation of an instance of resource
     *
     * @return an instance of resource
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public RadioProgram getRadioProgram() {
        throw new UnsupportedOperationException();
    }

    /**
     *
     * @return
     */
    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public RadioPrograms getAllRadioPrograms() {
        ArrayList<RadioProgram> rplist = service.findAllRP();
        RadioPrograms rpsList = new RadioPrograms();
        rpsList.setRpList(new ArrayList<RadioProgram>());

        for (int i = 0; i < rplist.size(); i++) {
            rpsList.getRpList().add(
                    new RadioProgram(rplist.get(i).getName(),
                            rplist.get(i).getDescription(),
                            rplist.get(i).getTypicalDuration()));
        }

        return rpsList;
    }

    /**
     * POST method for updating or creating an instance of resource
     *
     * @param rp
     */
    @POST
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    public String updateRadioProgram(RadioProgram rp) {
        JSONObject obj = new JSONObject();
        service.processModify(rp);
        obj.put("status", true);
        obj.put("message", "Radio Program updated successfully");
        return obj.toString();

    }

    /**
     * POST method for creating an instance of resource
     *
     * @param rp
     */
    @PUT
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    public String createRadioProgram(RadioProgram rp) {
        JSONObject obj = new JSONObject();
        System.out.println(rp.getTypicalDuration());
        System.out.println(rp.toString());
        service.processCreate(rp);
        obj.put("status", true);
        obj.put("message", "Radio Program created successfully");
        return obj.toString();
    }

    /**
     * DELETE method for deleting an instance of resource
     *
     * @param name name of radio program
     * @return
     */
    @DELETE
    @Path("/delete/{rpname}")
    @Consumes(MediaType.APPLICATION_JSON)
    public String deleteRadioProgram(@PathParam("rpname") String name) {
        String name2 = null;
        JSONObject obj = new JSONObject();
        boolean isDel = false;

        try {
            name2 = URLDecoder.decode(name, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();

        }

        int rows = schService.checkProgHasSch(name);

        if (rows == 0) {
            service.processDelete(name2);
            isDel = true;
        }

        if (isDel) {
            obj.put("status", isDel);
            obj.put("message", "Program deleted Successfully");
        } else {
            obj.put("status", isDel);
            obj.put("message", "Program has assigned slots in future. Please delete those and proceed");
        }

        return obj.toString();
    }
}
