package com.sj.base.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Date Utility Class used to convert Strings to Dates and Timestamps
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a> Modified by
 * <a href="mailto:dan@getrolling.com">Dan Kibler </a> to correct time
 * pattern. Minutes should be mm not MM (MM is month).
 */
public class DateUtil {

  private static Log log = LogFactory.getLog(DateUtil.class);

  private static final String TIME_PATTERN = "HH:mm";

  private static final String DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

  /**
   * Checkstyle rule: utility classes should not have public constructor
   */
  public DateUtil() {
  }

  // Timestamp和String之间转换的函数：
  public static String getTimestampToString(Timestamp obj) {
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 定义格式，不显示毫秒
    String str = df.format(obj);
    return str;
  }

  /*
   * 自定义 转换模式将Timestamp 输出
   */
  public static String getTimestampToString(String formatPattern, Timestamp obj) {
    SimpleDateFormat df = new SimpleDateFormat(formatPattern);
    String str = df.format(obj);
    return str;
  }

  // String转化为Timestamp:
  public static Timestamp getStringToTimestamp(String str) {
    Timestamp ts = Timestamp.valueOf(str);
    return ts;
  }

  public static Date strToDate(String str, String pattern) {
    Date dateTemp = null;
    SimpleDateFormat formater2 = new SimpleDateFormat(pattern);
    try {
      dateTemp = formater2.parse(str);
    } catch (Exception e) {
      log.error("exception in convert string to date!");
    }
    return dateTemp;
  }

  /**
   * Return default datePattern (yyyy-MM-dd)
   *
   * @return a string representing the date pattern on the UI
   */
  public static String getDatePattern() {
    return "yyyy-MM-dd";
  }

  public static String getDateTimePattern() {
    return DateUtil.getDatePattern() + " HH:mm:ss.S";
  }

  /**
   * This method attempts to convert an Oracle-formatted date in the form dd-MMM-yyyy to
   * yyyy-MM-dd.
   *
   * @param aDate date from database as a string
   * @return formatted string for the ui
   */
  public static String getDate(Date aDate) {
    SimpleDateFormat df;
    String returnValue = "";

    if (aDate != null) {
      df = new SimpleDateFormat(getDatePattern());
      returnValue = df.format(aDate);
    }

    return (returnValue);
  }

  public static String getDate() {
    return getDate(new Date());
  }

  /**
   * This method generates a string representation of a date/time in the format you specify on
   * input
   *
   * @param aMask   the date pattern the string is in
   * @param strDate a string representation of a date
   * @return a converted Date object
   * @throws ParseException when String doesn't match the expected format
   * @see SimpleDateFormat
   */
  public static Date convertStringToDate(String aMask, String strDate) throws ParseException {
    SimpleDateFormat df;
    Date date;
    df = new SimpleDateFormat(aMask);

    if (log.isDebugEnabled()) {
      log.debug("converting '" + strDate + "' to date with mask '" + aMask + "'");
    }

    try {
      date = df.parse(strDate);
    } catch (ParseException pe) {
      // log.error("ParseException: " + pe);
      throw new ParseException(pe.getMessage(), pe.getErrorOffset());
    }

    return (date);
  }

  /**
   * This method returns the current date time in the format: yyyy-MM-dd HH:MM a
   *
   * @param theTime the current time
   * @return the current date/time
   */
  public static String getTimeNow(Date theTime) {
    return getDateTime(TIME_PATTERN, theTime);
  }

  /**
   * This method returns the current date in the format: yyyy-MM-dd
   *
   * @return the current date
   * @throws ParseException when String doesn't match the expected format
   */
  public static Calendar getToday() throws ParseException {
    Date today = new Date();
    SimpleDateFormat df = new SimpleDateFormat(getDatePattern());

    // This seems like quite a hack (date -> string -> date),
    // but it works ;-)
    String todayAsString = df.format(today);
    Calendar cal = new GregorianCalendar();
    cal.setTime(convertStringToDate(todayAsString));

    return cal;
  }

  public static String getMonthBegin() {
    Calendar cale = getCurrentDay();
    cale.add(Calendar.MONTH, 0);
    cale.set(Calendar.DAY_OF_MONTH, 1);
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");// 定义格式，不显示毫秒
    return format.format(cale.getTime());
  }

  public static String getMonthEnd() {
    Calendar cale = getCurrentDay();
    cale.add(Calendar.MONTH, 1);
    cale.set(Calendar.DAY_OF_MONTH, 0);
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");// 定义格式，不显示毫秒
    return format.format(cale.getTime());
  }

  public static String getLastMonthBegin() {
    Calendar cale = getCurrentDay();
    cale.add(Calendar.MONTH, -1);
    cale.set(Calendar.DAY_OF_MONTH, 1);
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");// 定义格式，不显示毫秒
    return format.format(cale.getTime());
  }

  public static String getLastMonthEnd() {
    Calendar cale = getCurrentDay();
    cale.set(Calendar.DAY_OF_MONTH, 0);
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");// 定义格式，不显示毫秒
    return format.format(cale.getTime());
  }

  public static String getBeforeSeven() {
    Calendar cale = getCurrentDay();
    cale.add(Calendar.MONTH, 0);
    cale.add(Calendar.DAY_OF_MONTH, -6);
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");// 定义格式，不显示毫秒
    return format.format(cale.getTime());
  }

  public static Calendar getCurrentDay() {
    Calendar cal = Calendar.getInstance();
    return cal;

  }

  /**
   * This method generates a string representation of a date's date/time in the format you specify
   * on input
   *
   * @param aMask the date pattern the string is in
   * @param aDate a date object
   * @return a formatted string representation of the date
   * @see SimpleDateFormat
   */
  public static String getDateTime(String aMask, Date aDate) {
    SimpleDateFormat df = null;
    String returnValue = "";

    if (aDate == null) {
      log.error("aDate is null!");
    } else {
      df = new SimpleDateFormat(aMask);
      returnValue = df.format(aDate);
    }

    return (returnValue);
  }

  public static String getDateTime(Date aDate) {
    SimpleDateFormat df = null;
    String returnValue = "";

    if (aDate == null) {
      log.error("aDate is null!");
    } else {
      df = new SimpleDateFormat(DATETIME_PATTERN);
      returnValue = df.format(aDate);
    }

    return (returnValue);
  }

  public static String getDateTime() {
    return getDateTime(new Date());
  }

  /**
   * This method generates a string representation of a date based on the System Property
   * 'dateFormat' in the format you specify on input
   *
   * @param aDate A date to convert
   * @return a string representation of the date
   */
  public static String convertDateToString(Date aDate) {
    return getDateTime(getDatePattern(), aDate);
  }

  /**
   * This method converts a String to a date using the datePattern
   *
   * @param strDate the date to convert (in format yyyy-MM-dd)
   * @return a date object
   * @throws ParseException when String doesn't match the expected format
   */
  public static Date convertStringToDate(String strDate) throws ParseException {
    Date aDate = null;

    try {
      if (log.isDebugEnabled()) {
        log.debug("converting date with pattern: " + getDatePattern());
      }

      aDate = convertStringToDate(getDatePattern(), strDate);
    } catch (ParseException pe) {
      log.error("Could not convert '" + strDate + "' to a date, throwing exception");
      log.error(pe);
      throw new ParseException(pe.getMessage(), pe.getErrorOffset());
    }

    return aDate;
  }

  /**
   * @param aDate
   * @return
   */
  public static String convertDateToString(String pattern, Date aDate) {
    return getDateTime(pattern, aDate);
  }

  /**
   * 取得从startDate开始的前(正)/后(负)day天
   *
   * @param startDate
   * @param day
   * @return
   */
  public static Date getRelativeDate(Date startDate, int day) {
    Calendar calendar = Calendar.getInstance();
    try {
      calendar.setTime(startDate);
      calendar.add(Calendar.DAY_OF_MONTH, day);
      return calendar.getTime();
    } catch (Exception e) {
      log.error(e);
      return startDate;
    }
  }

  /**
   * 根据日期获取星期几
   *
   * @param date java.util.Date对象,不能为null
   * @return
   */
  public static int getDay(Date date) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    return cal.get(Calendar.DAY_OF_WEEK) - 1;
  }

  /**
   * 统计两个时间差，返回的是天数(即24小时算一天，少于24小时就为0，用这个的时候最好把小时、分钟等去掉)
   *
   * @param beginStr 开始时间
   * @param endStr
   * @return
   */
  public static int countDays(String beginStr, String endStr, String Foramt) {
    Date end = strToDate(endStr, Foramt);
    Date begin = strToDate(beginStr, Foramt);
    long times = end.getTime() - begin.getTime();
    return (int) (times / 60 / 60 / 1000 / 24);
  }

  public static int getCurrTime() {
    Calendar calendar = Calendar.getInstance();
    int hour = calendar.get(Calendar.HOUR_OF_DAY);
    int minute = calendar.get(Calendar.MINUTE);
    int second = calendar.get(Calendar.SECOND);
    int currTime = hour * 10000 + minute * 100 + second;
    return currTime;
  }

  public static int getCurrDate() {
    Calendar calendar = Calendar.getInstance();
    int year = calendar.get(Calendar.YEAR);
    int month = calendar.get(Calendar.MONTH) + 1;
    int day = calendar.get(Calendar.DAY_OF_MONTH);
    int currDate = year * 10000 + month * 100 + day;
    return currDate;
  }

  public static long getCurrDateTime() {
    Calendar calendar = Calendar.getInstance();
    int hour = calendar.get(Calendar.HOUR_OF_DAY);
    int minute = calendar.get(Calendar.MINUTE);
    int second = calendar.get(Calendar.SECOND);
    long currTime = hour * 10000 + minute * 100 + second;
    int year = calendar.get(Calendar.YEAR);
    int month = calendar.get(Calendar.MONTH) + 1;
    int day = calendar.get(Calendar.DAY_OF_MONTH);
    long currDate = year * 10000 + month * 100 + day;
    return currDate * 1000000 + currTime;
  }

  public static void main(String[] args) {
    System.out.println(getLastMonthEnd());
  }
}
