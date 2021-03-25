/**
 * Copyright (c) 2016, YCB.COM All Rights Reserved.
*/

package com.sj.base.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * 标题：ParseUtils.java<br>
 * 功能说明：<br>
 * 系统版本：v1.0<br>
 * 开发人员：kinkaid.yu<br>
 * 开发时间：2016年1月15日 上午10:36:43<br>
 * 功能描述：<br>
 */
public class ParseUtils {

	public static Integer parseInteger(String value) {
		Integer ret = null;
		if (value != null && value.length() > 0) {
			try {
				ret = Integer.parseInt(value);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		return ret;
	}

	public static Integer parseInteger(String value, Integer defaultValue) {
		Integer ret = defaultValue;
		if (value != null && value.length() > 0) {
			try {
				ret = Integer.parseInt(value);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		return ret;
	}

	public static boolean parseBoolean(boolean defaultValue, String value) {
		if (StringUtils.isBlank(value))
			return defaultValue;
		return Boolean.valueOf(value);
	}
}
