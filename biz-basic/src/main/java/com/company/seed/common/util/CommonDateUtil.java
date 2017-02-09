package com.company.seed.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间控制相关的工具类
 * Created by yoara on 2016/3/3.
 */
public class CommonDateUtil {
    private static final String DEFAULT = "yyyy-MM-dd";

    /**
     * 获取指定日期零点 00:00:00
     *
     * @return Date
     * @date 2014-9-4 上午09:30:12
     */
    public static Date getZeroOfDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 获得当天剩下的秒数
     **/
    public static long getTodayLeftSeconds() {
        Date now = new Date();
        return secondBetween(new Date(),getZeroOfDay(addDay(now,1)));
    }

    public static Date getFirstDayOfMonth(int amount) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.add(Calendar.MONTH, amount);
        return cal.getTime();
    }

    /**
     * 时间转换成字符串
     * @param date
     * @param format
     * @return
     */
    public static String getDateFormat(Date date, String format) {
        if (date == null) {
            return "";
        }
        if (format == null || "".equals(format)) {
            format = DEFAULT;
        }
        return new SimpleDateFormat(format).format(date);
    }

    /**
     * 字符串转时间
     *
     * @param format
     * @param str_time
     * @return
     */
    public static Date strToDateByFormat(String format, String str_time) {
        if (format == null || "".equals(format)) {
            format = DEFAULT;
        }
        try {
            return new SimpleDateFormat(format).parse(str_time);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取前后N年年份
     *
     * @param year (-为前N年份 ,0为当前年份)
     * @return
     */
    public static int getYear(int year) {
        Calendar now = Calendar.getInstance();
        now.add(Calendar.YEAR, year);
        return now.get(Calendar.YEAR);
    }

    /**
     * 获取前后N个月时间
     * @param month (-为前N个月时间 )
     * @return
     */
    public static int getMonth(int month) {
        Calendar now = Calendar.getInstance();
        now.add(Calendar.MONTH, month);
        return now.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取前后N个年时间Date
     * @param date
     * @param year
     * @return Date
     */
    public static Date addYear(Date date, Integer year) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.YEAR, year);
        date = c.getTime();
        return date;
    }

    /**
     * 获取前后N个月时间Date
     * @param date
     * @param month
     * @return Date
     */
    public static Date addMonth(Date date, Integer month) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, month);
        date = c.getTime();
        return date;
    }

    /**
     * 获取前后N天时间Date
     *
     * @param date
     * @param day
     * @return Date
     */
    public static Date addDay(Date date, Integer day) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, day);
        date = c.getTime();
        return date;
    }

    /**
     * 计算两个日期相差天数
     *
     * @param startDate 开始日期
     * @param endDate  结束日期
     * @return
     * @throws ParseException
     */
    public static int daysBetween(Date startDate, Date endDate) throws ParseException {
        LocalDate sDate = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate eDate = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Period period = Period.between(eDate,sDate);
        return period.getDays();
    }

    /**
     * 功能说明：计算两个日期相差的秒
     *
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return
     * @date 2014-12-5
     */
    public static long secondBetween(Date startTime,Date endTime) {
        LocalTime sTime = startTime.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
        LocalTime eTime = endTime.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
        Duration duration = Duration.between(eTime,sTime);
        return duration.getSeconds();
    }
}
