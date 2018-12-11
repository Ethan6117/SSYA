package com.empl.mgr.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class DateTimeUtil {

	/*
	 * 获取当前时间
	 * . [K]
	 */
	public static Date getCurrentTime() {
		return new Date();
	}

	/*
	 * 转换时间
	 * . [K]
	 */
	public static String conversionTime(Date date, String format) {
		if (CompareUtil.isEmpty(date) || StringUtils.isEmpty(format))
			return "";
		return new SimpleDateFormat(format).format(date);
	}
	
	/*
	 *字符串转时间 
	 */
	public static Date stringToDate(String dateStr,String format){
		try {
			return new SimpleDateFormat(format).parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
}
