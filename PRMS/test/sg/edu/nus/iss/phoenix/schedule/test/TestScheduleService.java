/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.schedule.test;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import org.junit.Test;
import sg.edu.nus.iss.phoenix.schedule.entity.ProgramSlot;
import sg.edu.nus.iss.phoenix.schedule.service.ScheduleService;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

/**
 * This is a test class to check all the service methods of Schedule
 * 
 * @author Divahar Sethuraman
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestScheduleService {

    static ScheduleService service
            = null;
    static ProgramSlot slot = null;

    /**
     *
     */
    @BeforeClass
    public static void setUp() {
        service
                = new ScheduleService();
        slot = new ProgramSlot();
    }

    /**
     *
     */
    @AfterClass
    public static void tearDown() {
        service
                = null;
        slot = null;
    }

    /**
     *
     * @throws ParseException
     */
    @Test
    public void method1_create() throws ParseException {

        assignData();

        boolean isCreate
                = service.processCreate(slot);

        assertThat(isCreate, equalTo(true));

    }

    /**
     *
     */
    @Test
    public void method2_retrieve() {

        Calendar cal = Calendar.getInstance();
        cal.clear(Calendar.HOUR_OF_DAY);
        cal.clear(Calendar.AM_PM);
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);
        cal.set(2017, 00, 01);

        Date dop = cal.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println("The date is: " + sdf.format(dop));

        Calendar startTime = Calendar.getInstance();
        startTime.set(Calendar.HOUR_OF_DAY, 11);
        startTime.set(Calendar.MINUTE, 0);
        startTime.set(Calendar.SECOND, 0);

        Calendar duration = Calendar.getInstance();
        duration.set(Calendar.HOUR_OF_DAY, 1);
        duration.set(Calendar.MINUTE, 0);
        duration.set(Calendar.SECOND, 0);

        java.sql.Date sqlDate = new java.sql.Date(cal.getTime().getTime());

        List<ProgramSlot> progSlots
                = service.processRetrieve("2017-01-01");

        slot = progSlots.get(0);

        assertThat(slot.
                getPresenterId(), equalTo("dilbert"));
        assertThat(slot.
                getPresenterId(), equalTo("dilbert"));
        assertThat(slot.
                getProgramName(), equalTo("test"));
        assertThat(String.valueOf(slot.getDuration()),
                is(equalTo(String.valueOf(new Time(duration.getTime().getTime())))));
        assertThat(String.valueOf(slot.getStartTime()),
                is(equalTo(String.valueOf(new Time(startTime.getTime().getTime())))));
        assertThat(String.valueOf(sdf.format(dop)),
                is(equalTo("2017-01-01")));
    }

    /**
     *
     */
    @Test
    public void method3_update() {

        slot.setPresenterId("wally");
        slot.setProducerId("wally");

        boolean isUpdate
                = service.processUpdate(slot);

        assertThat(isUpdate, equalTo(true));

    }

    /**
     *
     */
    @Test
    public void method4_checkConflicts() {

        boolean isConflict
                = service.checkConflicts(slot.getDateOfProgram(),
                        1100, 1200, 0);

        assertThat(isConflict, equalTo(true));

    }

    /**
     *
     */
    @Test
    public void method5_delete() {

        boolean isDelete
                = service.processDelete("2017-01-01",
                        "11:00:00");

        assertThat(isDelete, equalTo(true));

    }

    private void assignData() {

        Calendar cal = Calendar.getInstance();
        cal.clear(Calendar.HOUR_OF_DAY);
        cal.clear(Calendar.AM_PM);
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);
        cal.set(2017, 00, 01);

        java.sql.Date sqlDate = new java.sql.Date(cal.getTime().getTime());

        Calendar startTime = Calendar.getInstance();
        startTime.set(Calendar.HOUR_OF_DAY, 11);
        startTime.set(Calendar.MINUTE, 0);
        startTime.set(Calendar.SECOND, 0);
        System.out.println(startTime.getTime());

        Calendar duration = Calendar.getInstance();
        duration.set(Calendar.HOUR_OF_DAY, 1);
        duration.set(Calendar.MINUTE, 0);
        duration.set(Calendar.SECOND, 0);
        System.out.println(duration.getTime());

        slot.setPresenterId("dilbert");
        slot.setProducerId("dilbert");
        slot.setProgramName("test");
        slot.setStartTime(new Time(startTime.getTime().getTime()));
        slot.setDuration(new Time(duration.getTime()
                .getTime()));
        slot.setDateOfProgram(sqlDate);
    }

}
