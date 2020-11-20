package com.hzm.freestyle.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

/**
 * Excel工具类（3.17或较新版本可直接使用，一些老版本直接把报错下面的 //注释放开即可）
 * 
 * @author Hezeming
 * @version 1.1 
 * @date 2018年1月10日
 */
public class ExcelUtil {

	//
	private static final Logger LOG = LoggerFactory.getLogger(ExcelUtil.class);
	/**
	 * 默认行高
	 * 
	 * @see Row#setHeightInPoints(float)
	 */
	public static int DEFAULT_ROW_HEIGHT = 20;
	/**
	 * 默认的最大列宽
	 * 
	 * @see Sheet#setColumnWidth(int, int)
	 */
	public static int DEFAULT_MAX_COLUMN_WIDTH = 20 * 256;

	public static void main(String[] args) {
		String[] titles = new String[] { "姓名", "年龄", "性别"};
		List<Object[]> list = new ArrayList<Object[]>();
		for (int i = 0; i < 10; i++) {
			Object[] obj = new Object[4];
			obj[0] = "何泽铭";
			obj[1] = "18";
			obj[2] = "男";
			list.add(obj);
		}
		ExcelUtil.export(list, titles, "d://a.xls");
	}

	/**
	 * 本地导出工具类使用
	 *
	 * @param contents
	 * @param titles
	 * @param fileName
	 * @author Hezeming
	 */
	public static void export(List<Object[]> contents, String[] titles, String fileName) {
		export(contents, titles, fileName, null, null);
	}

	/**
	 * 读取Excel模板文件并导出
	 * 
	 * @param contents
	 * @param file
	 * @param fileName
	 * @param request
	 * @param response
	 */
	public static void readOrExport(List<Object[]> contents, File file, String fileName, HttpServletRequest request, HttpServletResponse response) {
		final int size = contents.size();
		OutputStream fos = null;
		HSSFWorkbook workbook = null;
		FastDateFormat dateFotmat = FastDateFormat.getInstance("yyyyMMddHHmmssSSS");
		String dateStr = dateFotmat.format(new Date());
		try {
			// 读取文本
			workbook = new HSSFWorkbook(new FileInputStream(file));
			// 读取表头
			String sheetName = "Sheet1";
			HSSFSheet sheet = workbook.getSheet(sheetName);
			// 表格内容单元格样式
			HSSFCellStyle contentStyle = createCellStyle(workbook);
			// 数字类型向右对齐
			HSSFCellStyle numberStyle = createCellStyle(workbook);
			// numberStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
			numberStyle.setAlignment(HorizontalAlignment.RIGHT); // 向右对齐
			// 将内容填入excel表格
			if (contents != null) {
				for (int i = 0; i < size; i++) {
					// 5.创建一行
					Row tableRow = sheet.createRow(i + 1); // 第一行是表头，+1
					tableRow.setHeightInPoints(DEFAULT_ROW_HEIGHT);
					Object[] objects = contents.get(i);
					for (int j = 0; j < objects.length; j++) { // 列
						Cell cell = tableRow.createCell(j);
						Object value = objects[j];
						String valueStr;
						HSSFCellStyle cellStyle = contentStyle;
						if (value instanceof Number) { // 如果表格值是数字，则向右对齐
							valueStr = value.toString();
							cellStyle = numberStyle;
						} else if (value instanceof Date) {
							valueStr = dateFotmat.format((Date) value);
						} else {
							valueStr = value == null ? "" : value.toString();
						}
						cell.setCellValue(valueStr);
						cell.setCellStyle(cellStyle);
					}
				}
			}
			// 将excel导出到文件中
			if (response != null) { // 作为对外部HTTP请求的响应输出
				fileName = StringUtils.isBlank(fileName) ? sheetName : fileName + ".xls";
				String destFileName = fileName;
				int dotPos = destFileName.lastIndexOf('.');
				if (dotPos != -1) {
					destFileName = destFileName.substring(0, dotPos);
				}
				destFileName = destFileName + '-' + dateStr + (dotPos > 0 ? fileName.substring(dotPos) : "");
				responseHeaderForDownload(request, response, destFileName);
				fos = response.getOutputStream();
			} else {
				fos = new FileOutputStream(fileName);
			}
			workbook.write(fos);
		} catch (Exception e) {
			LOG.error("导出文件:{}异常", fileName, e);
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					LOG.error("关闭Excel输出流时出现异常", e);
				}
			}
			/*if (workbook != null) {
				try {
					workbook.close();
				} catch (IOException e) {
					LOG.error("关闭workBook出现异常", e);
				}
			}*/
		}
	}

	/**
	 * Excel导出，默认文件名不加时间后缀，如需加上时间后缀，请在重载方法传所需参数：{@link #export(List, String[], String, HttpServletRequest, HttpServletResponse, boolean)}
	 *
	 * @param contents 表格内容
	 * @param titles 表格头部
	 * @param fileName 文件名 （response为null时，前面加上全路径）
	 * @param request 是否作为对外部HTTP请求的响应输出
	 * @param response 不为null：作为对外部HTTP请求的响应输出，为null：fileName为导出路径
	 * @author Hezeming
	 */
	public static void export(List<Object[]> contents, String[] titles, String fileName, HttpServletRequest request, HttpServletResponse response) {
		export(contents, titles, fileName, request, response, false);
	}
	
	/**
	 * Excel导出
	 *
	 * @param contents 表格内容
	 * @param titles 表格头部
	 * @param fileName 文件名 （response为null时，前面加上全路径）
	 * @param request 是否作为对外部HTTP请求的响应输出
	 * @param response 不为null：作为对外部HTTP请求的响应输出，为null：fileName为导出路径
	 * @param isAdditionalTime 是否为文件名追加时间后缀"日期格式化 + 时间戳"，如 fileName="报表" ==》  "报表-201806281530172853826"
	 * @author Hezeming
	 */
	public static void export(List<Object[]> contents, String[] titles, String fileName, HttpServletRequest request, HttpServletResponse response, boolean isAdditionalTime) {
		final int size = contents.size();
		final int heardsSize = titles.length;
		OutputStream fos = null;
		HSSFWorkbook workbook = null;
		FastDateFormat dateFotmat = FastDateFormat.getInstance("yyyyMMddHHmmssSSS");
		String dateStr = dateFotmat.format(new Date());
		try {
			// 创建文本
			workbook = new HSSFWorkbook();
			// 创建表
			String sheetName = "Sheet1";
			HSSFSheet sheet = workbook.createSheet(sheetName);
			int w = sheetName.length() * 768;
			int maxColumnWidth = DEFAULT_MAX_COLUMN_WIDTH;
			sheet.setColumnWidth(1, w > maxColumnWidth ? maxColumnWidth : w);
			// 创建表头: 创建一行
			HSSFCellStyle headerStyle = createCellStyle(workbook); // 标题样式
			HSSFFont headerFont = workbook.createFont(); // 标题字体
			// headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			headerFont.setBold(true);
			headerFont.setFontHeightInPoints((short) 11);
			headerStyle.setFont(headerFont);
			Row headerRow = sheet.createRow(0);
			// 存储每一列内容最大长度，后续适应每一列宽
			final int[] contentLength = new int[heardsSize];
			for (int i = 0; i < heardsSize; i++) {
				// 创建一个单元格
				Cell headerCell = headerRow.createCell(i);
				// 设置headerCell的值
				String title = titles[i];
				headerCell.setCellValue(title);
				headerCell.setCellStyle(headerStyle);
				contentLength[i] = title.length();
			}
			headerRow.setHeightInPoints(DEFAULT_ROW_HEIGHT);
			// 表格内容单元格样式
			HSSFCellStyle contentStyle = createCellStyle(workbook);
			// 数字类型向右对齐
			HSSFCellStyle numberStyle = createCellStyle(workbook);
			// numberStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
			numberStyle.setAlignment(HorizontalAlignment.RIGHT); // 向右对齐
			// 将内容填入excel表格
			if (contents != null) {
				for (int i = 0; i < size; i++) {
					// 5.创建一行
					Row tableRow = sheet.createRow(i + 1); // 第一行是表头，+1
					tableRow.setHeightInPoints(DEFAULT_ROW_HEIGHT);
					Object[] objects = contents.get(i);
					for (int j = 0; j < objects.length; j++) { // 列
						Cell cell = tableRow.createCell(j);
						Object value = objects[j];
						String valueStr;
						HSSFCellStyle cellStyle = contentStyle;
						if (value instanceof Number) { // 如果表格值是数字，则向右对齐
							valueStr = value.toString();
							cellStyle = numberStyle;
						} else if (value instanceof Date) {
							valueStr = dateFotmat.format((Date) value);
						} else {
							valueStr = value == null ? "" : value.toString();
						}
						cell.setCellValue(valueStr);
						cell.setCellStyle(cellStyle);
						int length = valueStr.length();
						if (length > contentLength[j]) {
							contentLength[j] = length;
						}
					}
				}
			}
			for (int i = 0; i < heardsSize; i++) { // 自动调整每一列宽
				// sheet.autoSizeColumn(i);
				int columnSize = contentLength[i] * 768;
				sheet.setColumnWidth(i, (columnSize >= 255 * 256) ? 8448: columnSize);
				// System.out.println(sheet.getColumnWidth(i));
			}
			// 将excel导出到文件中
			if (response != null) { // 作为对外部HTTP请求的响应输出
				fileName = StringUtils.isBlank(fileName) ? sheetName : fileName + ".xls";
				Long currentTimeMillis = System.currentTimeMillis();
				String destFileName = isAdditionalTime ? fileName.concat(new SimpleDateFormat("yyyyMMdd").format(currentTimeMillis)).concat(currentTimeMillis.toString()) : fileName;
				int dotPos = destFileName.lastIndexOf('.');
				if (dotPos != -1) {
					destFileName = destFileName.substring(0, dotPos);
				}
				destFileName = destFileName + '-' + dateStr + (dotPos > 0 ? fileName.substring(dotPos) : "");
				responseHeaderForDownload(request, response, destFileName);
				fos = response.getOutputStream();
//				System.out.println("表格已成功导出到：" + destFileName);
			} else {
				fos = new FileOutputStream(fileName);
			}
			workbook.write(fos);
		} catch (Exception e) {
			LOG.error("导出文件:{}异常", fileName, e);
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					LOG.error("关闭Excel输出流时出现异常", e);
				}
			}
			/*if (workbook != null) {
				try {
					workbook.close();
				} catch (IOException e) {
					LOG.error("关闭workBook出现异常", e);
				}
			}*/
		}
	}
	
	/**
	 * Excel导出(带合并单元格)
	 *
	 * @param contents 表格内容
	 * @param titles 表格头部
	 * @param mergedMap  Map<List<Integer>, List<Integer>>     第二个List是需要合并的列   第一个List是合并的条件     
	 * @param fileName 文件名 （response为null时，前面加上全路径）
	 * @param request 是否作为对外部HTTP请求的响应输出
	 * @param response 不为null：作为对外部HTTP请求的响应输出，为null：fileName为导出路径
	 * @param isAdditionalTime 是否为文件名追加时间后缀"日期格式化 + 时间戳"，如 fileName="报表" ==》  "报表-201806281530172853826"
	 * @author Hezeming
	 */
	public static void export(List<Object[]> contents, String[] titles, Map<List<Integer>, List<Integer>> mergedMap, String fileName, HttpServletRequest request, HttpServletResponse response, boolean isAdditionalTime) {
		final int size = contents.size();
		final int heardsSize = titles.length;
		OutputStream fos = null;
		HSSFWorkbook workbook = null;
		FastDateFormat dateFotmat = FastDateFormat.getInstance("yyyyMMddHHmmssSSS");
		String dateStr = dateFotmat.format(new Date());
		try {
			// 创建文本
			workbook = new HSSFWorkbook();
			// 创建表
			String sheetName = "Sheet1";
			HSSFSheet sheet = workbook.createSheet(sheetName);
			int w = sheetName.length() * 768;
			int maxColumnWidth = ExcelUtil.DEFAULT_MAX_COLUMN_WIDTH;
			sheet.setColumnWidth(1, w > maxColumnWidth ? maxColumnWidth : w);
			// 创建表头: 创建一行
			HSSFCellStyle headerStyle = ExcelUtil.createCellStyle(workbook); // 标题样式
			HSSFFont headerFont = workbook.createFont(); // 标题字体
			headerFont.setBold(true);
			headerFont.setFontHeightInPoints((short) 11);
			headerStyle.setFont(headerFont);
			Row headerRow = sheet.createRow(0);
			// 存储每一列内容最大长度，后续适应每一列宽
			final int[] contentLength = new int[heardsSize];
			for (int i = 0; i < heardsSize; i++) {
				// 创建一个单元格
				Cell headerCell = headerRow.createCell(i);
				// 设置headerCell的值
				String title = titles[i];
				headerCell.setCellValue(title);
				headerCell.setCellStyle(headerStyle);
				contentLength[i] = title.length();
			}
			headerRow.setHeightInPoints(ExcelUtil.DEFAULT_ROW_HEIGHT);
			// 表格内容单元格样式
			HSSFCellStyle contentStyle = ExcelUtil.createCellStyle(workbook);
			// 数字类型向右对齐
			HSSFCellStyle numberStyle = ExcelUtil.createCellStyle(workbook);

			numberStyle.setAlignment(HorizontalAlignment.RIGHT); // 向右对齐
			
			Map<List<Integer>, Object[]> diffObjMap = new HashMap<List<Integer>, Object[]>();
			Map<List<Integer>, Integer> firstRow = new HashMap<List<Integer>, Integer>();
			
			// 将内容填入excel表格
			if (contents != null) {
				for (int i = 0; i < size; i++) {
					// 5.创建一行
					Row tableRow = sheet.createRow(i + 1); // 第一行是表头，+1
					tableRow.setHeightInPoints(ExcelUtil.DEFAULT_ROW_HEIGHT);
					Object[] objects = contents.get(i);
					for (int j = 0; j < objects.length; j++) { // 列
						Cell cell = tableRow.createCell(j);
						Object value = objects[j];
						String valueStr;
						HSSFCellStyle cellStyle = contentStyle;
						if (value instanceof Number) { // 如果表格值是数字，则向右对齐
							valueStr = value.toString();
							cellStyle = numberStyle;
						} else if (value instanceof Date) {
							valueStr = dateFotmat.format((Date) value);
						} else {
							valueStr = value == null ? "" : value.toString();
						}
						cell.setCellValue(valueStr);
						cell.setCellStyle(cellStyle);
						int length = valueStr.length();
						if (length > contentLength[j]) {
							contentLength[j] = length;
						}
					}
					for (Entry<List<Integer>, List<Integer>> entrySet : mergedMap.entrySet()) {
						List<Integer> keyList = entrySet.getKey();
						int keyIndex = keyList.size();
						String[] cellValueArr = new String[keyIndex];
						for (int j = 0; j < keyIndex; j++) {
							cellValueArr[j] = tableRow.getCell(keyList.get(j)).getRichStringCellValue().getString();
						}
						if(diffObjMap.containsKey(keyList)) {
							Object[] diffObj = diffObjMap.get(keyList);
							int finalIndex = i + 1;
							boolean flag = Arrays.equals(diffObj,cellValueArr);
							if(!flag || finalIndex == size) {
								diffObj = cellValueArr;
								diffObjMap.put(keyList, cellValueArr);
								List<Integer> valueList = entrySet.getValue();
								Integer cellIndex = 0;
								Integer mergedIndex = firstRow.get(keyList);
								if(mergedIndex != i) {
									for (int j = 0; j < valueList.size(); j++) {
										cellIndex = valueList.get(j);
										if(i != 1) {
											sheet.addMergedRegion(new CellRangeAddress(mergedIndex, (flag && finalIndex == size) ? finalIndex : i, cellIndex, cellIndex));
										}
									}
								}
								firstRow.put(keyList, finalIndex);
								
							}
						} else {
							diffObjMap.put(keyList, cellValueArr);
							firstRow.put(keyList, 1);
						}
					}
				}
			}
			for (int i = 0; i < heardsSize; i++) { // 自动调整每一列宽
				sheet.setColumnWidth(i, contentLength[i] * 768);
			}
			// 将excel导出到文件中
			if (response != null) { // 作为对外部HTTP请求的响应输出
				fileName = StringUtils.isBlank(fileName) ? sheetName : fileName + ".xls";
				Long currentTimeMillis = System.currentTimeMillis();
				String destFileName = isAdditionalTime ? fileName.concat(new SimpleDateFormat("yyyyMMdd").format(currentTimeMillis)).concat(currentTimeMillis.toString()) : fileName;
				int dotPos = destFileName.lastIndexOf('.');
				if (dotPos != -1) {
					destFileName = destFileName.substring(0, dotPos);
				}
				destFileName = destFileName + '-' + dateStr + (dotPos > 0 ? fileName.substring(dotPos) : "");
				ExcelUtil.responseHeaderForDownload(request, response, destFileName);
				fos = response.getOutputStream();
			} else {
				fos = new FileOutputStream(fileName);
			}
			workbook.write(fos);
		} catch (Exception e) {
			LOG.error("导出文件:{}异常", fileName, e);
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					LOG.error("关闭Excel输出流时出现异常", e);
				}
			}
		}
	}

	/**
	 * 创建单元格样式，默认：水平居中 & 垂直居中
	 *
	 * @param workbook
	 * @return
	 * @author Hezeming
	 */
	public static HSSFCellStyle createCellStyle(final HSSFWorkbook workbook) {
		HSSFCellStyle style = workbook.createCellStyle();
		// style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 居中
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		return style;
	}

	/**
	 * 设置用于下载文件的响应头信息（兼容所有浏览器）
	 * 
	 * @param request
	 * @param response
	 * @param fileName
	 */
	public static final void responseHeaderForDownload(HttpServletRequest request, HttpServletResponse response, String fileName) {
		// see https://tools.ietf.org/html/rfc5987
		// see http://stackoverflow.com/questions/93551/how-to-encode-the-filename-parameter-of-content-disposition-header-in-http
		String userAgent = request.getHeader("User-Agent");
		response.setContentType("application/octet-stream; charset=UTF-8");
		String contentDisposition = null;
		if (StringUtils.isNotEmpty(userAgent)) {
			int pos;
			if ((pos = userAgent.indexOf("MSIE")) != -1) { // 如果是 IE
				char version = userAgent.charAt(pos + 5); // "MSIE x.0" 如果是 IE 7.0 或 8.0
				if (version == '7' || version == '8') {
					contentDisposition = "attachment; filename=" + encodeURL(fileName);
				}
			} else if (userAgent.toLowerCase().contains("android")) { // 如果是Android浏览器
				contentDisposition = "attachment; filename=\"" + makeAndroidSafeFileName(fileName) + "\"";
			}
		}
		if (contentDisposition == null) { // default
			contentDisposition = "attachment; filename=\"" + encodeURL(fileName) + "\"; filename*=UTF-8''" + encodeURL(fileName);
		}
		response.setHeader("Content-Disposition", contentDisposition);
	}

	private static final String makeAndroidSafeFileName(String fileName) {
		// Android 浏览器仅支持 "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ._-+,@£$€!½§~'=()[]{}0123456789";
		final char[] newFileName = fileName.toCharArray();
		boolean changed = false;
		for (int i = 0; i < newFileName.length; i++) {
			final char c = newFileName[i];
			if ((c >= 'A' && c <= 'Z') // 65 - 90
					|| (c >= 'a' && c <= 'z') // 97 - 122
					|| (c >= '0' && c <= '9') // 48 - 57
					|| "._-+,@£$€!½§~'=()[]{}".indexOf(c) != -1) { // other chars
			} else {
				newFileName[i] = '_';
				changed = true;
			}
		}
		return changed ? new String(newFileName) : fileName;
	}

	/**
	 * 将指定的URL基于"UTF-8"进行编码
	 *
	 * @param str
	 * @return
	 */
	public static final String encodeURL(String str) {
		return encodeURL(str, "UTF-8");
	}

	/**
	 * 将指定的URL进行编码
	 * 
	 * @param str
	 * @param encoding
	 * @return
	 */
	public static final String encodeURL(String str, String encoding) {
		try {
			return URLEncoder.encode(str, encoding);
		} catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException(e);
		}
	}
}
