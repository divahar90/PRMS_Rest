/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.core.helper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import org.apache.commons.lang3.Range;

/**
 *
 * @author Divahar Sethuraman 
 * This class contains the helper methods for
 * schedule
 */
public class ScheduleHelper {

    /**
     *
     * @param result
     * @param strtTime
     * @param endTime
     * @param id
     * @return
     * @throws ParseException
     */
    public boolean checkConflicts(ResultSet result,
            int strtTime, int endTime, int id)
            throws ParseException {

        HashMap<Integer, Integer> strtDurMap
                = getStrtEndTime(result, id);

        if (null != strtDurMap
                && strtDurMap.size() > 0) {
            for (int key : strtDurMap.keySet()) {

                System.out.println("start: " + key);
                System.out.println("end: " + strtDurMap.get(key));

                Range<Integer> durRange = Range.between(key,
                        strtDurMap.get(key) - 1);
                if (durRange.contains(strtTime)
                        || durRange.contains(endTime)) {
                    return true;

                }
            }
        }

        return false;
    }

    /**
     *
     * @param result
     * @param id
     * @return
     * @throws ParseException
     */
    private HashMap<Integer, Integer>
            getStrtEndTime(ResultSet result, int id) throws ParseException {

        HashMap<Integer, Integer> startDurationMap
                = new HashMap<>();

        try {
            if (null != result) {
                while (result.next()) {
                    Calendar cal = Calendar.getInstance();

                    System.out.println(result.getTime("startTime").getHours());
                    System.out.println(result.getTime("startTime").getMinutes());

                    String strtTime = String.valueOf(result.getTime("startTime").getHours()) + ":"
                            + String.valueOf(result.getTime("startTime").getMinutes());

                    if (result.getTime("startTime").getMinutes()
                            == 0) {
                        strtTime = strtTime + "0";
                    }

                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                    Date date = sdf.parse(strtTime);
                    cal.setTime(date);

                    int durationInt = (result.getTime("duration").
                            getHours() * 60) + result.getTime("duration").getMinutes();

                    System.out.println(durationInt);

                    cal.add(Calendar.MINUTE, (durationInt - 1));

                    String endTime = sdf.format(cal.getTime());

                    int strtTimeInt = Integer.
                            valueOf(strtTime.replaceAll(":", ""));

                    System.out.println(strtTimeInt);
                    System.out.println(Integer.
                            valueOf(endTime.replaceAll(":", "")));

                    if (id != 0 && id != result.getInt(3)) {
                        startDurationMap.put(strtTimeInt, Integer.
                                valueOf(endTime.replaceAll(":", "")));
                    } else if (id == 0) {
                        startDurationMap.put(strtTimeInt, Integer.
                                valueOf(endTime.replaceAll(":", "")));
                    }

                    System.out.println("DB start: " + strtTime);
                    System.out.println("DB end: " + endTime);
                }
            }
        } catch (SQLException exp) {
            System.out.println(exp.getMessage());
        }
        return startDurationMap;

    }

    public static int getYear(Date dateOfProg) {

        Calendar c = Calendar.getInstance();
        c.setTime(dateOfProg);

        int year = c.get(Calendar.YEAR);

        return year;
    }

    public static Date getStrtDate(Date dateOfProg) {
        Calendar c = Calendar.getInstance();
        c.setTime(dateOfProg);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK) - c.getFirstDayOfWeek();
        c.add(Calendar.DAY_OF_MONTH, -dayOfWeek);

        Date weekStart = c.getTime();

        return weekStart;
    }
}
