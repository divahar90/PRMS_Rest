/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.restful;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import sg.edu.nus.iss.phoenix.entity.ProgramSlot;
import sg.edu.nus.iss.phoenix.service.ScheduleService;
import sg.edu.nus.iss.phoenix.restful.entity.ProgramSlots;


/**
 *
 * @author Divahar Sethuraman
 * This class contains the rest methods related to schedule
 */

@Path("schedule")
@RequestScoped
public class ScheduleRESTService {

    @Context
    private UriInfo context;

    private ScheduleService service;

    /**
     * Creates a new instance of ScheduleRESTService
     */
    public ScheduleRESTService() {
        service = new ScheduleService();
    }

    /**
     * POST method for creating an instance of resource
     *
     * @param ps
     * @return
     * @throws java.text.ParseException
     */
    @PUT
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    public boolean createProgramSlot(ProgramSlot ps) throws ParseException {

        boolean isCreate = false;
        try{
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Date date = sdf.parse(ps.getStartTime());
        cal.setTime(date);
        cal.add(Calendar.MINUTE, (int) (ps.getDuration() - 1));

        String endTime = sdf.format(cal.getTime());

        int strtTime = Integer.
                valueOf(ps.getStartTime().replaceAll(":", ""));

        boolean conflictFlag = service.
                checkConflicts(ps.getDateOfProgram(), strtTime,
                        Integer.valueOf(endTime.replaceAll(":", "")),0);

        System.out.println(conflictFlag);

        if (!conflictFlag) {
            isCreate = service.processCreate(ps);
        }
        }
        catch(Exception exp){
            exp.printStackTrace();
        }

        return isCreate;
    }

    /**
     * DELETE method for deleting an instance of resource
     *
     * @param dateOfProgram and Start Time of the resource
     * @param startTime
     * @return 
     */
    @DELETE
    @Path("/delete/{dateOfProgram}/{startTime}")
    @Consumes(MediaType.APPLICATION_JSON)
    public boolean deleteProgramSlot(@PathParam("dateOfProgram") String dateOfProgram,
            @PathParam("startTime") String startTime) {

        boolean isDeleted = false;
        try {
            System.out.println(dateOfProgram);
            System.out.println(startTime);

            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date date = sdf1.parse(dateOfProgram);
            java.sql.Date dateProg = new java.sql.Date(date.getTime());

           isDeleted
                    = service.processDelete(dateProg, startTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return isDeleted;
    }

    /**
     * PUT method for updating a program slot
     *
     * @param ps
     * @return
     * @throws java.text.ParseException
     */
    @PUT
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    public boolean updateProgramSlot(ProgramSlot ps) throws ParseException {

        boolean isUpdate = false;
        try{
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Date date = sdf.parse(ps.getStartTime());
        cal.setTime(date);
        cal.add(Calendar.MINUTE, (int) (ps.getDuration() - 1));

        String endTime = sdf.format(cal.getTime());

        int strtTime = Integer.
                valueOf(ps.getStartTime().replaceAll(":", ""));

        boolean conflictFlag = service.
                checkConflicts(ps.getDateOfProgram(), strtTime,
                        Integer.valueOf(endTime.replaceAll(":", "")),ps.getId());

        System.out.println(conflictFlag);

        if (!conflictFlag) {
            isUpdate = service.processUpdate(ps);
        }
        }
        catch(Exception exp){
            exp.printStackTrace();
        }

        return isUpdate;
    }

    /**
     * GET method for retrieving program slots
     *
     * @param dateOfProg
     * @return
     * @throws java.text.ParseException
     */
    @GET
    @Path("/retrieve")
    @Consumes(MediaType.APPLICATION_JSON)
    public ProgramSlots retrieveProgramSlots(@QueryParam("dateOfProgram") String dateOfProg)
            throws ParseException {

        ProgramSlots progSlots = null;
        try{
        if (null != dateOfProg) {
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date date = sdf1.parse(dateOfProg);
            java.sql.Date dateProg = new java.sql.Date(date.getTime());

            List<ProgramSlot> slots = service.retrieve(dateProg);
            progSlots = new ProgramSlots();
            progSlots.setProgramSlots(new ArrayList<>());

            for (ProgramSlot slot : slots) {                
                progSlots.getProgramSlots().add(slot);
            }
        }
        }
        catch(Exception exp){
            exp.printStackTrace();
        }
        return progSlots;
    }
}
