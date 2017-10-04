/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.schedule.RESTful;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import sg.edu.nus.iss.phoenix.schedule.entity.ProgramSlot;
import sg.edu.nus.iss.phoenix.schedule.service.ScheduleService;
import sg.edu.nus.iss.phoenix.user.service.UserService;

/**
 * This class contains the rest methods related to schedule 
 * 
 * @author Divahar Sethuraman 
 * 
 */
@Path("schedule")
@RequestScoped
public class ScheduleRESTService {

    @Context
    private UriInfo context;

    private ScheduleService service;

    private UserService userService;

    /**
     * Creates a new instance of ScheduleRESTService
     */
    public ScheduleRESTService() {
        service = new ScheduleService();
        userService = new UserService();
    }

    /**
     * POST method for creating a program slot
     *
     * @param ps program slot object
     * @return String - Json object with status and message
     */
    @PUT
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    public String createProgramSlot(ProgramSlot ps) {

        JSONObject obj = new JSONObject();
        boolean isCreate = false;
        try {

            isCreate = service.processCreate(ps);

            if (isCreate) {
                obj.put("status", isCreate);
                obj.put("message", "Schedule created Successfully");
            } else {
                obj.put("status", isCreate);
                obj.put("message", "Conflicts exist");
            }
        } catch (Exception exp) {
            exp.printStackTrace();
        }

        return obj.toString();
    }

    /**
     * DELETE method for deleting a program slot
     *
     * @param dateOfProgram Date of the program slot
     * @param startTime Start time of the program slot
     * @return String - Json object with status and message
     */
    @DELETE
    @Path("/delete/{dateOfProgram}/{startTime}")
    @Consumes(MediaType.APPLICATION_JSON)
    public String deleteProgramSlot(@PathParam("dateOfProgram") String dateOfProgram,
            @PathParam("startTime") String startTime) {

        boolean isDeleted = false;
        JSONObject obj = new JSONObject();

        try {

            isDeleted
                    = service.processDelete(dateOfProgram,
                            startTime);

            if (isDeleted) {
                obj.put("status", isDeleted);
                obj.put("message", "Schedule deleted successfully");
            } else {
                obj.put("status", isDeleted);
                obj.put("message", "Error deleting the schedule");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return obj.toString();
    }

    /**
     * PUT method for updating a program slot
     *
     * @param ps Program slot object
     * @return String - Json object with status and message
     */
    @POST
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    public String updateProgramSlot(ProgramSlot ps) {
        boolean isUpdate = false;
        JSONObject obj = new JSONObject();

        try {

            isUpdate = service.processUpdate(ps);

            if (isUpdate) {
                obj.put("status", isUpdate);
                obj.put("message", "Schedule updated successfully");
            } else {
                obj.put("status", isUpdate);
                obj.put("message", "Conflicts exist");
            }

        } catch (Exception exp) {
            exp.printStackTrace();
        }

        return obj.toString();
    }

    /**
     * GET method for retrieving program slots
     *
     * @param dateOfProg Date of program slot to be deleted
     * @return ProgramSlots - Program slots of the particular day
     */
    
    @GET
    @Path("/retrieve")
    @Consumes(MediaType.APPLICATION_JSON)
    public ProgramSlots retrieveProgramSlots
        (@QueryParam("dateOfProgram") String dateOfProg){

        ProgramSlots progSlots = null;
        List<String> idList = new ArrayList<>();
        StringBuilder idBuilder = new StringBuilder();
        String idStr = "";
        Map<String, String> userIdMap = new HashMap<>();

        try {
            if (null != dateOfProg) {

                List<ProgramSlot> slots = service.
                        processRetrieve(dateOfProg);
                progSlots = new ProgramSlots();
                progSlots.setProgramSlots(new ArrayList<>());

                for (ProgramSlot slot : slots) {
                    progSlots.getProgramSlots().add(slot);
                    if (null != slot.getPresenterId()) {
                        idBuilder.append("'").
                                append(slot.getPresenterId()).append("'").append(",");
                    }
                    if (null != slot.getProducerId()) {
                        idBuilder.append("'").
                                append(slot.getProducerId()).append("'").append(",");
                    }
                }

                if (idBuilder.length() > 0) {
                    idStr = idBuilder.toString().
                            substring(0, idBuilder.length() - 1);

                    userIdMap = userService.
                            getUserNames(idStr);
                }

                progSlots.setUserIdMap(userIdMap);
            }
        } catch (Exception exp) {
            exp.printStackTrace();
        }
        return progSlots;
    }
}
