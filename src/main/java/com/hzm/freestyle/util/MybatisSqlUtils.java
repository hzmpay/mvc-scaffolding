package com.hzm.freestyle.util;

import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 生成MybatisSql工具类
 * 
 * @author Hezeming
 * @version 1.0
 * @date 2018年7月21日
 */
public class MybatisSqlUtils {

	private static Pattern humpPattern = Pattern.compile("[A-Z]");
	private static String classForName = "com.hymn.dto.";

	public static void main(String[] args) {
		getSelectSql("t_wechat_info");
	}

	/**
	 * 根据实体类和表名自动生成update语句在控制台
	 *
	 * @param clazz
	 * @param tableName
	 * @author Hezeming
	 */
	public static void getUpdateSql(String tableName) {
		try {
			String className = tableName.replaceFirst("t_", "");
			Class<?> clazz = Class.forName(classForName.concat(getTransStr(className)));
			System.out.println("UPDATE");
			System.out.println("	" + tableName);
			System.out.println("SET");
			Field[] fields = clazz.getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				Field field = fields[i];
				String fieldName = field.getName();
				if ("serialVersionUID".equals(fieldName)) {
					continue;
				}
				String sqlName = change_(fieldName);
				System.out.println("	" + sqlName + " = #{" + fieldName + "}" + (i == fields.length - 1 ? "" : ","));
			}
			System.out.println("WHERE ");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据实体类和表名自动生成select语句在控制台（实体类）
	 *
	 * @param clazz
	 * @param tableName
	 * @author Hezeming
	 */
	public static void getSelectSql(String tableName) {
		try {
			String className = tableName.replaceFirst("t_", "");
			Class<?> clazz = Class.forName(classForName.concat(getTransStr(className)));
			System.out.println("SELECT");
			Field[] fields = clazz.getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				Field field = fields[i];
				String fieldName = field.getName();
				if ("serialVersionUID".equals(fieldName)) {
					continue;
				}
				String sqlName = change_(fieldName);
				System.out.println("	" + sqlName + " AS " + fieldName + (i == fields.length - 1 ? "" : ","));
			}
			System.out.println("FROM ");
			System.out.println("	" + tableName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据实体类和表名自动生成select语句在控制台（别名）
	 *
	 * @param clazz
	 * @param tableName
	 * @author Hezeming
	 */
	public static void getSelectSql2(String tableName) {
		try {
			String className = tableName.replaceFirst("t_", "");
			Class<?> clazz = Class.forName(classForName.concat(getTransStr(className)));
			String name = clazz.getSimpleName();
			char[] charArray = name.toCharArray();
			StringBuilder builder = new StringBuilder();
			for (int i = 0; i < charArray.length; i++) {
				char c = charArray[i];
				if (c >= 'A' && c <= 'Z') {
					builder.append(c);
				}
			}
			String prefix = builder.toString().toLowerCase();
			//
			System.out.println("SELECT");
			Field[] fields = clazz.getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				Field field = fields[i];
				String fieldName = field.getName();
				if ("serialVersionUID".equals(fieldName)) {
					continue;
				}
				String sqlName = change_(fieldName);
				System.out.println("	" + prefix + "." + sqlName + " AS " + prefix + "_" + sqlName + (i == fields.length - 1 ? "" : ","));
			}
			System.out.println("FROM ");
			System.out.println("	" + tableName + " " + prefix);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据实体类和表名自动生成insert语句在控制台
	 *
	 * @param clazz
	 * @param tableName
	 * @author Hezeming
	 */
	public static void getInsertSql(String tableName) {
		try {
			String className = tableName.replaceFirst("t_", "");
			Class<?> clazz = Class.forName(classForName.concat(getTransStr(className)));
			System.out.println("<insert id=\"\" parameterType=\"" + clazz.getCanonicalName() + "\">");
			System.out.println("INSERT INTO " + tableName + " (");
			Field[] fields = clazz.getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				Field field = fields[i];
				String fieldName = field.getName();
				if ("serialVersionUID".equals(fieldName)) {
					continue;
				}
				fieldName = change_(fieldName);
				System.out.println("	" + fieldName + (i == fields.length - 1 ? "" : ","));
			}
			System.out.println(") VALUES (");
			for (int i = 0; i < fields.length; i++) {
				Field field = fields[i];
				String fieldName = field.getName();
				if ("serialVersionUID".equals(fieldName)) {
					continue;
				}
				field.setAccessible(true);
				System.out.println("	#{" + fieldName + "}" + (i == fields.length - 1 ? "" : ","));
			}
			System.out.println(")");
			System.out.println("</insert>");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 生成ResultMap
	 * 
	 * @param clazz 实体类的Class
	 * @return String
	 */
	@SuppressWarnings("unused")
	public static void getResultMap(Class<?> clazz) {
		Object obj = null;
		try {
			obj = clazz.newInstance();
		} catch (Exception e) {
			System.out.println("#Exception.反射生成实体异常#");
		}
		String clazzName = clazz.getSimpleName();
		String resultMapId = Character.toLowerCase(clazzName.charAt(0)) + clazzName.substring(1) + "Map";
		String pkgName = clazz.getName();
		StringBuilder resultMap = new StringBuilder();
		resultMap.append("<resultMap id=\"");
		resultMap.append(resultMapId);
		resultMap.append("\" type=\"");
		resultMap.append(pkgName);
		resultMap.append("\">\n");
		Field[] fields = clazz.getDeclaredFields();
		for (Field f : fields) {
			String property = f.getName();
			String javaType = f.getType().getName();
			if ("serialVersionUID".equals(property)) {
				continue;// 忽略掉这个属性
			}
			String jdbcType = javaType2jdbcType(javaType.toLowerCase());
			String str = jdbcType == null ? "association" : "result";
			resultMap.append("    <");
			resultMap.append(str);
			resultMap.append(" column=\"");
			resultMap.append(property2Column(property));
			resultMap.append("\" " + (jdbcType == null ? " javaType" : " jdbcType") + "=\"");
			resultMap.append(jdbcType == null ? f.getType().getName() : jdbcType);
			resultMap.append("\" property=\"");
			resultMap.append(property);
			resultMap.append("\" />\n");
		}
		resultMap.append("</resultMap>");
		System.out.println(resultMap.toString());
	}

	private static String property2Column(String property) {
		Matcher matcher = humpPattern.matcher(property);
		StringBuffer sb = new StringBuffer();
		while (matcher.find()) {
			matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
		}
		matcher.appendTail(sb);
		return sb.toString();
	}

	private static String javaType2jdbcType(String javaType) {
		if (javaType.contains("string")) {
			return "VARCHAR";
		} else if (javaType.contains("boolean")) {
			return "BIT";
		} else if (javaType.contains("byte")) {
			return "TINYINT";
		} else if (javaType.contains("short")) {
			return "SMALLINT";
		} else if (javaType.contains("int")) {
			return "INTEGER";
		} else if (javaType.contains("long")) {
			return "BIGINT";
		} else if (javaType.contains("double")) {
			return "DOUBLE";
		} else if (javaType.contains("float")) {
			return "REAL";
		} else if (javaType.contains("date") || javaType.contains("timestamp")) {
			return "TIMESTAMP";
		} else if (javaType.contains("time")) {
			return "TIME";
		} else if (javaType.contains("bigdecimal")) {
			return "DECIMAL";
		} else {
			return null;
		}
	}

	public static String change_(String str) {
		StringBuilder builder = new StringBuilder();
		char[] arr = str.toCharArray();
		for (char c : arr) {
			if (Character.isUpperCase(c)) {
				builder.append("_" + Character.toLowerCase(c));
			} else {
				builder.append(c);
			}
		}
		return builder.toString();
	}

	private static String getTransStr(String before) {
		return getTransStr(before, true);
	}

	/**
	* @description 将mysql中表名和字段名转换成驼峰形式
	*/
	private static String getTransStr(String before, boolean firstChar2Upper) {
		// 不带"_"的字符串,则直接首字母大写后返回
		if (!before.contains("_")) {
			return firstChar2Upper ? initCap(before) : before;
		}
		String[] strs = before.split("_");
		StringBuffer after = null;
		if (firstChar2Upper) {
			after = new StringBuffer(initCap(strs[0]));
		} else {
			after = new StringBuffer(strs[0]);
		}
		if (strs.length > 1) {
			for (int i = 1; i < strs.length; i++)
				after.append(initCap(strs[i]));
		}
		return after.toString();
	}

	/**
	 * @param str 传入字符串
	 * @description 将传入字符串的首字母转成大写
	 */
	private static String initCap(String str) {
		char[] ch = str.toCharArray();
		if (ch[0] >= 'a' && ch[0] <= 'z')
			ch[0] = (char) (ch[0] - 32);
		return new String(ch);
	}
}
