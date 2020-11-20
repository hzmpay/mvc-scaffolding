package com.hzm.freestyle.util;

import org.apache.commons.lang3.time.DateUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.*;
import java.util.Calendar;
import java.util.Date;

/**
 * 说明：日期处理
 * 创建人：zou
 * 修改时间：2015年11月24日
 */
public class DateUtil {

    /**
     * yyyy
     */
    private static final ThreadLocal<SimpleDateFormat> LOCAL_YEAR = new ThreadLocal<SimpleDateFormat>() {

        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy");
        }

    };
    /**
     * yyyy-MM-dd
     */
    private static final ThreadLocal<SimpleDateFormat> LOCAL_DAY = new ThreadLocal<SimpleDateFormat>() {

        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }

    };
    /**
     * yyyyMMdd
     */
    private static final ThreadLocal<SimpleDateFormat> LOCAL_DAYS = new ThreadLocal<SimpleDateFormat>() {

        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyyMMdd");
        }

    };
    /**
     * MMDD
     */
    private static final ThreadLocal<SimpleDateFormat> LOCAL_MONTH = new ThreadLocal<SimpleDateFormat>() {

        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("MMdd");
        }

    };
    /**
     * yyyy-MM-dd HH:mm:ss
     */
    private static final ThreadLocal<SimpleDateFormat> LOCAL_TIME = new ThreadLocal<SimpleDateFormat>() {

        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }

    };

    /**
     * yyyy-MM-dd HH:mm
     */
    private static final ThreadLocal<SimpleDateFormat> LOCAL_TIME_M = new ThreadLocal<SimpleDateFormat>() {

        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm");
        }
    };

    /**
     * yyyyMMddHHmmssSSS
     */
    private static final ThreadLocal<SimpleDateFormat> LOCAL_TIME_NO = new ThreadLocal<SimpleDateFormat>() {

        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyyMMddHHmmssSSS");
        }

    };


    /**
     * yyyy-MM-dd HH:mm:ss 正则
     */
    private static final String TIME_REGEX = "^((([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29))\\s([0-1]?[0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$";

    /**
     * 小时对应毫秒值
     */
    public static final int HONUR_FOR_MS = 60 * 60 * 1000;

    /**
     * 小时对应毫秒值
     */
    public static final BigDecimal HONUR_FOR_MS_BIGDECIMAL = new BigDecimal(HONUR_FOR_MS);

    /**
     * 获取YYYY格式
     *
     * @return
     */
    public static String getYear(Date date) {
        return LOCAL_YEAR.get().format(date);
    }

    public static String getYear() {
        return getYear(new Date());
    }

    /**
     * 获取YYYY-MM-DD格式
     *
     * @return
     */
    public static String getDay(Date date) {
        return LOCAL_DAY.get().format(date);
    }

    public static String getDay() {
        return getDay(new Date());
    }

    /**
     * 获取YYYYMMDD格式
     *
     * @return
     */
    public static String getDays(Date date) {
        return LOCAL_DAYS.get().format(date);
    }

    public static String getDays() {
        return getDays(new Date());
    }

    /**
     * 获取MMDD格式
     *
     * @return
     */
    public static String getMonth(Date date) {
        return LOCAL_MONTH.get().format(date);
    }

    public static String getMonth() {
        return getDays(new Date());
    }

    /**
     * 获取YYYY-MM-DD HH:mm:ss格式
     *
     * @return
     */
    public static String getTime(Date date) {
        return LOCAL_TIME.get().format(date);
    }

    public static String getTime() {
        return getTime(new Date());
    }

    /**
     * 获取YYYY-MM-DD HH:mm格式
     *
     * @return
     */
    public static String getTimeM(Date date) {
        return LOCAL_TIME_M.get().format(date);
    }

    public static String getTimeM() {
        return getTime(new Date());
    }

    public static boolean matchTimeStr(String str) {
        return str.matches(TIME_REGEX);
    }

    /**
     * 获取yyyyMMddHHmmssSSS格式(时间编号)
     *
     * @return
     */
    public static String getTimeNo(Date date) {
        return LOCAL_TIME_NO.get().format(date);
    }

    public static String getTimeNo() {
        return getTimeNo(new Date());
    }

    /**
     * @param s
     * @param e
     * @return boolean
     * @throws
     * @Title: compareDate
     * @Description: TODO(日期比较 ， 如果s > = e 返回true 否则返回false)
     * @author mans
     */
    public static boolean compareDate(String s, String e) throws ParseException {
        if (fomatDate(s) == null || fomatDate(e) == null) {
            return false;
        }
        return parseTime(s).getTime() >= parseTime(e).getTime();
    }

    /**
     * 格式化日期
     *
     * @return
     */
    public static Date fomatDate(String date) {
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return fmt.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 格式化日期
     *
     * @return
     */
    public static Date parseDays(String date) {
        try {
            return LOCAL_DAYS.get().parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 格式化日期
     *
     * @return
     */
    public static Date parseDay(String date) {
        try {
            return LOCAL_DAY.get().parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 格式化时间为yyyy-MM-dd HH:mm:ss
     *
     * @param date
     * @return
     * @throws ParseException
     * @author Hezeming
     */
    public static Date parseTime(String date) throws ParseException {
        return LOCAL_TIME.get().parse(date);
    }

    /**
     * 格式化时间为yyyy-MM-dd HH:mm
     *
     * @param date
     * @return
     * @throws ParseException
     * @author Hezeming
     */
    public static Date parseTimeM(String date) throws ParseException {
        return LOCAL_TIME_M.get().parse(date);
    }

    /**
     * 校验日期是否合法
     *
     * @return
     */
    public static boolean isValidDate(String s) {
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        try {
            fmt.parse(s);
            return true;
        } catch (Exception e) {
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            return false;
        }
    }

    /**
     * @param startTime
     * @param endTime
     * @return
     */
    public static int getDiffYear(String startTime, String endTime) {
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        try {
            // long aa=0;
            int years = (int) (((fmt.parse(endTime).getTime() - fmt.parse(startTime).getTime()) / (1000 * 60 * 60 * 24)) / 365);
            return years;
        } catch (Exception e) {
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            return 0;
        }
    }

    /**
     * @param startTime
     * @param endTime
     * @return
     */
    public static int getDiffDay(String startTime, String endTime) {
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        try {
            // long aa=0;
            int day = (int) (((fmt.parse(endTime).getTime() - fmt.parse(startTime).getTime()) / (1000 * 60 * 60 * 24)));
            return day;
        } catch (Exception e) {
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            return 0;
        }
    }

    /**
     * <li>功能描述：时间相减得到天数
     *
     * @param beginDateStr
     * @param endDateStr
     * @return long
     * @author Administrator
     */
    public static long getDaySub(String beginDateStr, String endDateStr) {
        long day = 0;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date beginDate = null;
        Date endDate = null;
        try {
            beginDate = format.parse(beginDateStr);
            endDate = format.parse(endDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        day = (endDate.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000);
        // System.out.println("相隔的天数="+day);
        return day;
    }

    /**
     * 得到n天之后的日期
     *
     * @param days
     * @return
     */
    public static String getAfterDayDate(String days) {
        int daysInt = Integer.parseInt(days);
        Calendar canlendar = Calendar.getInstance(); // java.util包
        canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
        Date date = canlendar.getTime();
        SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = sdfd.format(date);
        return dateStr;
    }

    /**
     * 得到n天之后是周几
     *
     * @param days
     * @return
     */
    public static String getAfterDayWeek(String days) {
        int daysInt = Integer.parseInt(days);
        Calendar canlendar = Calendar.getInstance(); // java.util包
        canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
        Date date = canlendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("E");
        String dateStr = sdf.format(date);
        return dateStr;
    }

    /**
     * time + " 00:00:00"
     *
     * @param days
     * @return
     * @throws ParseException
     */
    public static String getStartTimeStr(String time) throws ParseException {
        return getStartTimeStr(parseTime(time));
    }

    /**
     * time + " 00:00:00"
     *
     * @param days
     * @return
     */
    public static String getStartTimeStr(Date date) {
        return getTime(getStartTime(date));
    }

    /**
     * time + " 00:00:00"
     *
     * @param date
     * @return
     */
    public static Date getStartTime(Date date) {
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        instance.set(Calendar.HOUR_OF_DAY, 0);
        instance.set(Calendar.MINUTE, 0);
        instance.set(Calendar.SECOND, 0);
        instance.set(Calendar.MILLISECOND, 0);
        return instance.getTime();
    }
    /**
     * time + " 00:00:00"
     *
     * @param date
     * @return
     */
    public static Date getStartTime(String time) throws ParseException {
        return getStartTime(parseTime(time));
    }


    /**
     * time + " 23:59:59"
     *
     * @param days
     * @return
     * @throws ParseException
     */
    public static String getEndTimeStr(String time) throws ParseException {
        return getEndTimeStr(parseTime(time));
    }

    /**
     * time + " 23:59:59"
     *
     * @param days
     * @return
     */
    public static String getEndTimeStr(Date date) {
        return getTime(getEndTime(date));
    }

    /**
     * time + " 23:59:59"
     *
     * @param days
     * @return
     */
    public static Date getEndTime(Date date) {
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        instance.set(Calendar.HOUR_OF_DAY, 23);
        instance.set(Calendar.MINUTE, 59);
        instance.set(Calendar.SECOND, 59);
        instance.set(Calendar.MILLISECOND, 999);
        return instance.getTime();
    }

    public static Date builder(int year, int month, int day) {
        Calendar instance = Calendar.getInstance();
        instance.set(year, month - 1, day);
        return instance.getTime();
    }

    public static Date builder(int year, int month, int day, int hour, int minute, int second) {
        Calendar instance = Calendar.getInstance();
        instance.set(year, month - 1, day, hour, minute, second);
        return instance.getTime();
    }

    /**
     * 对小时进行前进或退后，仅支持到小数点后一位
     *
     * @param date
     * @param amount
     * @return java.util.Date
     * @author Hezeming
     */
    public static Date addHours(Date date, BigDecimal amount) {
        return new Date(date.getTime() + amount.multiply(DateUtil.HONUR_FOR_MS_BIGDECIMAL).intValue());
    }

    public static void main(String[] args) {

        System.out.println(getTime(parseDay("2019-04-25")));

    }
}
