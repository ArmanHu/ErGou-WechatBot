package io.uouo.wechatbot.common.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {

	/**
	 * 获取本地当日日期
	 * @return
	 */
	public static String getDateToday(){
		LocalDate localDate = LocalDate.now();
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("YYYY-MM-dd");
		String date = localDate.format(dateTimeFormatter);
		return date;
	}
	public static void main(String[] args) {
		System.out.println(getDateToday());
	}
}
