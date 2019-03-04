package com.carry.util;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 
 * 日期处理工具类
 * @author yangguoxiang
 *
 */
public class DateUtil {
	public static void main(String[] args) throws ParseException {
//		getNextNoteBianMa("012-001-");
	}

	/**
	 * 获取当前时间字符串
	 *
	 * @param date
	 *            时间对象
	 * @param formatStr
	 *            时间格式字符串
	 * @return
	 */
	public static String getDateFormatStr(Date date, String formatStr) {
		SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
		return sdf.format(date);
	}

	/**
	 * 计算指定时间加上指定天数的时间
	 *
	 * @param day
	 * @return
	 */
	public static Date getNewDateByAddDay(Date date, double day) {
		Date newDate = null;
		try {
			int dayInt = (int) day;
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.DATE, dayInt);
			newDate = cal.getTime();
		} catch (RuntimeException e) {
			throw e;
		}
		return newDate;
	}

	/**
	 * 获取时间对象
	 *
	 * @param dateStr
	 *            时间字符串
	 * @param formatStr
	 *            时间格式字符串 yyyy-MM-dd
	 * @return
	 * @throws ParseException
	 */
	public static Date getDateByFormat(String dateStr, String formatStr) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
		return sdf.parse(dateStr);
	}

	public static boolean isAfterDate(Date d){
		boolean bool = false;
		Date date = new Date();
		if(date.after(d)){
			bool = true;
		}
		return bool;
	}

	public static String getCurrentTimeMillis(){
		long timeMill = System.currentTimeMillis();
		String time = null;
		time = Long.toString(timeMill);
		return time;
	}
}
