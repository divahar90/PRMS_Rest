/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.schedule.test;

import java.sql.Time;
import java.util.Calendar;
import java.util.List;
import static org.hamcrest.CoreMatchers.equalTo;
import org.junit.Test;
import sg.edu.nus.iss.phoenix.schedule.entity.ProgramSlot;
import sg.edu.nus.iss.phoenix.schedule.service.ScheduleService;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

/**
 *
 * @author Divahar Sethuraman
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestScheduleService {

    static ScheduleService service
            = null;
    static ProgramSlot slot = null;

    @BeforeClass
    public static void setUp() {
        service
                = new ScheduleService();
        slot = new ProgramSlot();
    }

    @AfterClass
    public static void tearDown() {
        service
                = null;
        slot = null;
    }

    @Test
    public void method1_create() {

        assignData();

        boolean isCreate
                = service.processCreate(slot);

        assertThat(isCreate, equalTo(true));

    }

    @Test
    public void method2_retrieve() {

        Calendar cal = Calendar.getInstance();
        cal.clear(Calendar.HOUR_OF_DAY);
        cal.clear(Calendar.AM_PM);
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);
        cal.set(2017, 01, 01);

        java.sql.Date sqlDate = new java.sql.Date(cal.getTime().getTime());

        List<ProgramSlot> progSlots
                = service.retrieve(sqlDate);

        slot = progSlots.get(0);

        System.out.println(slot.
                getDateOfProgram());
        System.out.println(sqlDate);

        assertThat(slot.
                getPresenterId(), equalTo("dilbert"));
        assertThat(slot.
                getPresenterId(), equalTo("dilbert"));
        assertThat(slot.
                getProgramName(), equalTo("test"));
        assertThat(slot.getDuration(), equalTo(new Time(180000)));
        assertThat(slot.getStartTime(), equalTo(new Time(360000)));

    }

    @Test
    public void method3_update() {

        slot.setPresenterId("wally");
        slot.setProducerId("wally");

        boolean isUpdate
                = service.processUpdate(slot);

        assertThat(isUpdate, equalTo(true));

    }

    @Test
    public void method4_checkConflicts() {

        boolean isConflict
                = service.checkConflicts(slot.getDateOfProgram(),
                        0730, 1930, 0);

        assertThat(isConflict, equalTo(true));

    }

    @Test
    public void method5_delete() {

        boolean isDelete
                = service.processDelete(slot.getDateOfProgram(),
                        slot.getStartTime().toString());

        assertThat(isDelete, equalTo(true));

    }

    private void assignData() {

        Calendar cal = Calendar.getInstance();
        cal.clear(Calendar.HOUR_OF_DAY);
        cal.clear(Calendar.AM_PM);
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);
        cal.set(2017, 01, 01);

        java.sql.Date sqlDate = new 
                    java.sql.Date(cal.getTime().getTime());

        slot.setPresenterId("dilbert");
        slot.setProducerId("dilbert");
        slot.setProgramName("test");
        slot.setStartTime(new Time(360000));
        slot.setDuration(new Time(180000));
        slot.setDateOfProgram(sqlDate);
    }

}
