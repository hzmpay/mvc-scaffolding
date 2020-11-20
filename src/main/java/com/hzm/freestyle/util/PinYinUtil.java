package com.hzm.freestyle.util;

import org.apache.commons.lang3.StringUtils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.*;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class PinYinUtil {

	public enum Type {
		/** 全部大写 */
		UPPERCASE,
		/** 全部小写 */
		LOWERCASE,
		/** 首字母大写 */
		FIRSTUPPER
	}

	private static HanyuPinyinOutputFormat buildHanyuPinyinOutputFormat() {
		HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
		format.setCaseType(HanyuPinyinCaseType.UPPERCASE);
		format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		return format;
	}

	/**
	 * 将汉字转换成拼音首字母大写
	 * 
	 * @param str 字符串
	 * @return str为'我爱编程' ,return获取到的是'WABC'
	 * @throws BadHanyuPinyinOutputFormatCombination
	 */
	public static String toPinYinUppercase(String str) throws BadHanyuPinyinOutputFormatCombination {
		return toPinYin(str, "", Type.UPPERCASE);
	}

	/**
	 * 将汉字转换成拼音首字母大写,可设置字母间的间隔符
	 * 
	 * @param str 字符串
	 * @param spera 转换字母间隔加的字符串,如果不需要为""
	 * @return str为'我爱编程' ,spera为'@' return获取到的是'W@A@B@C'
	 * @throws BadHanyuPinyinOutputFormatCombination
	 */
	public static String toPinYinUppercase(String str, String spera) throws BadHanyuPinyinOutputFormatCombination {
		return toPinYin(str, spera, Type.UPPERCASE);
	}

	/**
	 * 将汉字转换成拼音首字母小写
	 * 
	 * @param str 字符串
	 * @return str为'我爱编程' ,return获取到的是'wabc'
	 * @throws BadHanyuPinyinOutputFormatCombination
	 */
	public static String toPinYinLowercase(String str) throws BadHanyuPinyinOutputFormatCombination {
		return toPinYin(str, "", Type.LOWERCASE);
	}

	/**
	 * 将汉字转换成拼音首字母小写,可设置字母间的间隔符
	 * 
	 * @param str 字符串
	 * @param spera 转换字母间隔加的字符串,如果不需要为""
	 * @return str为'我爱编程',spera为'@' return获取到的是'w@a@b@c'
	 * @throws BadHanyuPinyinOutputFormatCombination
	 */
	public static String toPinYinLowercase(String str, String spera) throws BadHanyuPinyinOutputFormatCombination {
		return toPinYin(str, spera, Type.LOWERCASE);
	}

	/**
	 * 获取拼音首字母大写
	 * 
	 * @param str 字符串
	 * @return str为'我爱编程' ,return获取到的是'W'
	 * @throws BadHanyuPinyinOutputFormatCombination 异常信息
	 */
	public static String toPinYinUppercaseInitials(String str) throws BadHanyuPinyinOutputFormatCombination {
		if (StringUtils.isBlank(str)) {
			return null;
		}
		if (str.length() != 1) {
			str = str.substring(0, 1);
		}
		return toPinYinUppercase(str).trim();
	}

	/**
	 * 获取拼音首字母小写
	 * 
	 * @param str 字符串
	 * @return str为'我爱编程' ,return获取到的是'w'
	 * @throws BadHanyuPinyinOutputFormatCombination 异常信息
	 */
	public static String toPinYinLowercaseInitials(String str) throws BadHanyuPinyinOutputFormatCombination {
		if (StringUtils.isBlank(str)) {
			return null;
		}
		if (str.length() != 1) {
			str = str.substring(0, 1);
		}
		return toPinYinLowercase(str).trim();
	}

	/**
	 * 将str转换成拼音，如果不是汉字或者没有对应的拼音，则不作转换
	 * 
	 * @param str 字符串
	 * @param spera 默认,可为""
	 * @param type 转换格式
	 * @return 按照转换格式转换成字符串
	 * @throws BadHanyuPinyinOutputFormatCombination 异常信息
	 */
	public static String toPinYin(String str, String spera, Type type) throws BadHanyuPinyinOutputFormatCombination {
		if (str == null || str.trim().length() == 0) {
			return "";
		}
		final HanyuPinyinOutputFormat format = buildHanyuPinyinOutputFormat();
		if (type == Type.UPPERCASE) {
			format.setCaseType(HanyuPinyinCaseType.UPPERCASE);
		} else {
			format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		}
		String py = "";
		String temp = "";
		String[] result;
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if ((int) c <= 128) {
				py += c;
			} else {
				result = PinyinHelper.toHanyuPinyinStringArray(c, format);
				if (result == null) {
					py += c;
				} else {
					temp = result[0];
					if (type == Type.FIRSTUPPER) {
						temp = result[0].toUpperCase().charAt(0) + temp.substring(1);
					}
					if (temp.length() >= 1) {
						temp = temp.substring(0, 1);
					}
					py += temp + (i == str.length() - 1 ? "" : spera);
				}
			}
		}
		return py.trim();
	}

	public static void main(String[] args) throws Exception {
		String str = "C";
		String ss = toPinYinUppercaseInitials(str);
		System.out.println(ss);


//		String[] t = PinyinHelper.toHanyuPinyinStringArray(c, format);
	}
}
