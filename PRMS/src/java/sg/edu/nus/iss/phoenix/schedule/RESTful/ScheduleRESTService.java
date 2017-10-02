/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.schedule.RESTful;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import sg.edu.nus.iss.phoenix.authenticate.service.UserService;
import sg.edu.nus.iss.phoenix.core.helper.ScheduleHelper;
import sg.edu.nus.iss.phoenix.schedule.entity.ProgramSlot;
import sg.edu.nus.iss.phoenix.schedule.service.ScheduleService;

/**
 *
 * @author Divahar Sethuraman This class contains the rest methods related to
 * schedule
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
     * POST method for creating an instance of resource
     *
     * @param ps
     * @return
     * @throws java.text.ParseException
     */
    @PUT
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    public String createProgramSlot(ProgramSlot ps) throws ParseException {

        boolean isCreate = false;
        boolean isInAnnual = false;
        boolean isInWeekly = false;
        boolean conflictFlag = false;

        JSONObject obj = new JSONObject();
        try {

            System.out.println(ps.getStartTime());
            System.out.println(ps.getDuration());
            System.out.println(ps.getDateOfProgram());

            Calendar cal = Calendar.getInstance();
            String strtTime = String.valueOf(ps.getStartTime().getHours()) + ":"
                    + String.valueOf(ps.getStartTime().getMinutes());

            if (ps.getStartTime().getMinutes() == 0) {
                strtTime = strtTime + "0";
            }

            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            Date date = sdf.parse(strtTime);
            cal.setTime(date);

            int durationInt = (ps.getDuration().
                    getHours() * 60) + ps.getDuration().getMinutes();

            cal.add(Calendar.MINUTE, (durationInt - 1));

            String endTime = sdf.format(cal.getTime());

            int strtTimeInt = Integer.
                    valueOf(strtTime.replaceAll(":", ""));

            int schYear = ScheduleHelper.
                    getYear(ps.getDateOfProgram()); // Get the year of program slot

            System.out.println("schYear: " + schYear);

            isInAnnual = service.chkProgYear(schYear);

            Date strtDay = ScheduleHelper.
                    getStrtDate(ps.getDateOfProgram());

            java.sql.Date sqlDate
                    = new java.sql.Date(strtDay.getTime());

            System.out.println("sqlDate: " + sqlDate);

            if (!isInAnnual) {
                service.createAnnualSch(schYear, endTime);
            }

            isInWeekly = service.
                    chkProgSchWeek(new java.sql.Date(strtDay
                            .getTime()));

            if (!isInWeekly) {
                service.createWeeklySch(sqlDate, endTime);
            } else {
                conflictFlag = service.
                        checkConflicts(ps.getDateOfProgram(), strtTimeInt,
                                Integer.valueOf(endTime.replaceAll(":", "")), 0);
            }

            System.out.println(conflictFlag);

            if (!conflictFlag) {
                isCreate = service.processCreate(ps);
            }

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
     * DELETE method for deleting an instance of resource
     *
     * @param dateOfProgram and Start Time of the resource
     * @param startTime
     * @return
     */
    @DELETE
    @Path("/delete/{dateOfProgram}/{startTime}")
    @Consumes(MediaType.APPLICATION_JSON)
    public String deleteProgramSlot(@PathParam("dateOfProgram") String dateOfProgram,
            @PathParam("startTime") String startTime) {

        boolean isDeleted = false;
        JSONObject obj = new JSONObject();

        try {
            System.out.println(dateOfProgram);
            System.out.println(startTime);

            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date date = sdf1.parse(dateOfProgram);
            java.sql.Date dateProg = new java.sql.Date(date.getTime());

            isDeleted
                    = service.processDelete(dateProg, startTime);

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
     * @param ps
     * @return
     * @throws java.text.ParseException
     */
    @POST
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    public String updateProgramSlot(ProgramSlot ps) throws ParseException {

        boolean isUpdate = false;
        JSONObject obj = new JSONObject();
        boolean isInAnnual = false;
        boolean isInWeekly = false;
        boolean conflictFlag = false;

        try {
            Calendar cal = Calendar.getInstance();
            String strtTime = String.valueOf(ps.getStartTime().getHours()) + ":"
                    + String.valueOf(ps.getStartTime().getMinutes());
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            Date date = sdf.parse(strtTime);
            cal.setTime(date);
            if (ps.getStartTime().getMinutes() == 0) {
                strtTime = strtTime + "0";
            }
            int durationInt = (ps.getDuration().
                    getHours() * 60) + ps.getDuration().getMinutes();

            System.out.println(durationInt);

            cal.add(Calendar.MINUTE, (durationInt - 1));

            String endTime = sdf.format(cal.getTime());

            int strtTimeInt = Integer.
                    valueOf(strtTime.replaceAll(":", ""));

            int schYear = ScheduleHelper.
                    getYear(ps.getDateOfProgram()); // Get the year of program slot

            System.out.println("schYear: " + schYear);

            isInAnnual = service.chkProgYear(schYear);

            Date strtDay = ScheduleHelper.
                    getStrtDate(ps.getDateOfProgram());

            java.sql.Date sqlDate
                    = new java.sql.Date(strtDay.getTime());

            System.out.println("sqlDate: " + sqlDate);

            if (!isInAnnual) {
                service.createAnnualSch(schYear, endTime);
            }

            isInWeekly = service.
                    chkProgSchWeek(new java.sql.Date(strtDay
                            .getTime()));

            if (!isInWeekly) {
                service.createWeeklySch(sqlDate, endTime);
            } else {
                conflictFlag = service.
                        checkConflicts(ps.getDateOfProgram(), strtTimeInt,
                                Integer.valueOf(endTime.replaceAll(":", "")), ps.getId());
            }

            System.out.println(conflictFlag);

            if (!conflictFlag) {
                isUpdate = service.processUpdate(ps);
            }

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
        List<String> idList = new ArrayList<>();
        StringBuilder idBuilder = new StringBuilder();
        String idStr = "";
        Map<String, String> userIdMap = new HashMap<>();

        try {
            if (null != dateOfProg) {
                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date date = sdf1.parse(dateOfProg);
                java.sql.Date dateProg = new java.sql.Date(date.getTime());

                List<ProgramSlot> slots = service.retrieve(dateProg);
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
