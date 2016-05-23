package com.enjoyor.healthhouse.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherUtil {
	
	/**
	 * 判断是否是手机号
	 * @param mobiles
	 * @return
	 */
	public static boolean isMobileNumber(String mobiles) {
		if(StringUtils.isBlank(mobiles))
			return false;
		Pattern p = Pattern
				.compile("^((13[0-9])|(15[^4,\\D])|(17[0-9])|(18[0-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}

	
	public static boolean isEmail(String email) {
		String str = "^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(email);
		return m.matches();
	}

	/**
	 * 判断是否是固定电话号码
	 * @param phoneNumber
	 * @return
	 */
	public static boolean isPhoneNumber(String phoneNumber) {
		boolean isValid = false;
		/*
		 * 可接受的电话格式有：
		 */
		// String expression = "^\\(?(\\d{4})\\)?[- ]?(\\d{3})[- ]?(\\d{5})$";
		String expression = "^\\(?(\\d{4})\\)?[- ]?(\\d{8})$";
		String expression2 = "^\\(?(\\d{4})\\)?[- ]?(\\d{7})$";
		/*
		 * 可接受的电话格式有：
		 */
		// String expression2 = "^\\(?(\\d{4})\\)?[- ]?(\\d{4})[- ]?(\\d{4})$";
		CharSequence inputStr = phoneNumber;
		Pattern pattern = Pattern.compile(expression);
		Matcher matcher = pattern.matcher(inputStr);

		Pattern pattern2 = Pattern.compile(expression2);
		Matcher matcher2 = pattern2.matcher(inputStr);
		if (matcher.matches() || matcher2.matches()) {
			isValid = true;
		}
		return isValid;
	}
}