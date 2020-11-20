package com.hzm.freestyle.util;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.apache.commons.lang3.StringUtils;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 二维码生成工具类（阿里云OSS版）
 * 
 * @author Hezeming
 * @version 1.0
 * @date 2018年8月6日
 */
public class QRCodeUtil {

	private static final String CHARSET = "utf-8";
	/** 图片格式 */
	private static final String FORMAT_NAME = "JPG";
	/** 二维码尺寸 */
	private static final int QRCODE_SIZE = 300;
	/** LOGO宽度 */
	private static final int WIDTH = 60;
	/** LOGO高度 */
	private static final int HEIGHT = 60;
	/** 容错率 */
	private static final ErrorCorrectionLevel ERROR_CORRECTION_LEVEL = ErrorCorrectionLevel.L;

	/**
	 * 生成二维码图片流
	 *
	 * @param content 二维码内容
	 * @return
	 * @throws Exception
	 * @author Hezeming
	 */
	public static BufferedImage createImage(String content) throws Exception {
		return createImage(content, null, false);
	}

	/**
	 * 生成二维码文件输入流
	 *
	 * @param content 二维码内容
	 * @return
	 * @throws Exception
	 * @author Hezeming
	 */
	public static InputStream createImageInputStream(String content) throws Exception {
		BufferedImage image = createImage(content, null, false);
		if (image == null) {
			return null;
		}
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		ImageOutputStream imageOutputStream = ImageIO.createImageOutputStream(byteArrayOutputStream);
		ImageIO.write(image, FORMAT_NAME, imageOutputStream);
		return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
	}

	/**
	 * 生成二维码流（默认logo经过压缩）
	 *
	 * @param content 二维码内容}
	 * @param imgPath logo图片地址，为null则生成不带logo的二维码流
	 * @return
	 * @throws Exception
	 * @author Hezeming
	 */
	public static BufferedImage createImage(String content, String imgPath) throws Exception {
		return createImage(content, imgPath, true);
	}

	/**
	 * 生成二维码流
	 *
	 * @param content 二维码内容
	 * @param imgPath logo图片地址，为null则生成不带logo的二维码流
	 * @param needCompress logo是否需要压缩
	 * @return
	 * @throws Exception
	 * @author Hezeming
	 */
	public static BufferedImage createImage(String content, String imgPath,
			boolean needCompress) throws Exception {
		// 开始生成二维码
		Map<EncodeHintType, Object> hints = new ConcurrentHashMap<EncodeHintType, Object>();
		hints.put(EncodeHintType.ERROR_CORRECTION, ERROR_CORRECTION_LEVEL);
		hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
		// margin 边框设置
		hints.put(EncodeHintType.MARGIN, 0);
		BitMatrix bitMatrix = new MultiFormatWriter().encode(content,
				BarcodeFormat.QR_CODE, QRCODE_SIZE, QRCODE_SIZE, hints);
		int width = bitMatrix.getWidth();
		int height = bitMatrix.getHeight();
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) { // 填充，可设置颜色 颜色的取值为后6位
				image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000
						: 0xFFFFFFFF);
			}
		}
		if (StringUtils.isNotBlank(imgPath)) {
			// 插入logo图片
			QRCodeUtil.insertLogoImage(image, imgPath, needCompress);
		}
		return image;
	}

	/**
	 * 给二维码插入logo
	 *
	 * @param source 二维码流文件
	 * @param imgPath 生成路径
	 * @param needCompress logo是否需要压缩
	 * @throws Exception
	 * @author Hezeming
	 */
	private static void insertLogoImage(BufferedImage source, String imgPath,
			boolean needCompress) throws Exception {
		File file = new File(imgPath);
		if (!file.exists()) {
			throw new FileNotFoundException(imgPath + "<========   该文件不存在！");
		}
		Image src = read(imgPath, true);
		int width = src.getWidth(null);
		int height = src.getHeight(null);
		if (needCompress) { // 压缩LOGO
			if (width > WIDTH) {
				width = WIDTH;
			}
			if (height > HEIGHT) {
				height = HEIGHT;
			}
			Image image = src.getScaledInstance(width, height,
					Image.SCALE_SMOOTH);
			BufferedImage tag = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);
			Graphics g = tag.getGraphics();
			g.drawImage(image, 0, 0, null); // 绘制缩小后的图
			g.dispose();
			src = image;
		}
		// 插入LOGO
		Graphics2D graph = source.createGraphics();
		int x = (QRCODE_SIZE - width) / 2; // 设置logo的插入位置
		int y = (QRCODE_SIZE - height) / 2;
		graph.drawImage(src, x, y, width, height, null);
		Shape shape = new RoundRectangle2D.Float(x, y, width, width, 6, 6); // 后面两个参数是设置周边圆角，数值越大圆角越大
		graph.setStroke(new BasicStroke(3f));
		graph.draw(shape);
		graph.dispose();
	}
	/**
	 * 生成二维码文件("普通文件上传形式")
	 *
	 * @param content
	 * @param imgPath
	 * @param destPath
	 * @param needCompress
	 * @throws Exception
	 * @author Hezeming
	 */
	/*public static boolean encode(String content, String imgPath, String destPath,
			boolean needCompress) throws Exception {
		BufferedImage image = QRCodeUtil.createImage(content, imgPath,
				needCompress);
		mkdirs(destPath);
		String file = new Random().nextInt(99999999) + ".jpg";
		return write(image, FORMAT_NAME, destPath + "/" + file);
	}
	
	public static void encode(String content, String imgPath, String destPath)
			throws Exception {
		QRCodeUtil.encode(content, imgPath, destPath, false);
	}
	
	public static void encode(String content, String destPath,
			boolean needCompress) throws Exception {
		QRCodeUtil.encode(content, null, destPath, needCompress);
	}
	
	public static void encode(String content, String destPath) throws Exception {
		QRCodeUtil.encode(content, null, destPath, false);
	}*/

	//
	/**
	 * 解析二维码文件扫描后的内容
	 *
	 * @param file 二维码文件
	 * @return 二维码内容
	 * @throws Exception
	 * @author Hezeming
	 */
	public static String decode(File file) throws Exception {
		BufferedImage image;
		image = ImageIO.read(file);
		if (image == null) {
			return null;
		}
		BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(image);
		BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
		Result result;
		Map<DecodeHintType, Object> hints = new ConcurrentHashMap<DecodeHintType, Object>();
		hints.put(DecodeHintType.CHARACTER_SET, CHARSET);
		result = new MultiFormatReader().decode(bitmap, hints);
		String resultStr = result.getText();
		return resultStr;
	}

	/**
	 * 解析二维码文件扫描后的内容
	 *
	 * @param filePath 二维码文件
	 * @param isNet 是否是网络文件
	 * @return 二维码内容
	 * @throws Exception
	 * @author Hezeming
	 */
	public static String decode(String filePath, boolean isNet) throws Exception {
		if (StringUtils.isBlank(filePath)) {
			throw new NullPointerException("文件路径不能为空");
		}
		BufferedImage image;
		image = read(filePath, isNet);
		if (image == null) {
			return null;
		}
		BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(image);
		BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
		Result result;
		Map<DecodeHintType, Object> hints = new ConcurrentHashMap<DecodeHintType, Object>();
		hints.put(DecodeHintType.CHARACTER_SET, CHARSET);
		result = new MultiFormatReader().decode(bitmap, hints);
		String resultStr = result.getText();
		return resultStr;
	}

	/*private static void mkdirs(String destPath) {
		File file = new File(destPath);
		// 当文件夹不存在时，mkdirs会自动创建多层目录，区别于mkdir．(mkdir如果父目录不存在则会抛出异常)
		if (!file.exists() && !file.isDirectory()) {
			file.mkdirs();
		}
	}*/
	/**
	 * 读文件
	 *
	 * @param imgPath 图片路径
	 * @param isNet 是否是网络路径
	 * @return
	 * @throws Exception
	 * @author Hezeming
	 */
	private static BufferedImage read(String imgPath, boolean isNet) throws Exception {
		if (isNet) {
			URL url = new URL(imgPath);
			return ImageIO.read(url);
		} else {
			return ImageIO.read(new File(imgPath));
		}
	}

	/**
	 * 写文件
	 *
	 * @param image
	 * @param formatName
	 * @param filePath
	 * @return
	 * @throws Exception
	 * @author Hezeming
	 */
	private static boolean write(BufferedImage image, String formatName, String filePath) throws Exception {
		return ImageIO.write(image, formatName, new File(filePath));
	}
	public static void main(String[] args) throws Exception {
        String imgPath = "https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p56339.webp";
        URL url = new URL(imgPath);
        final BufferedImage bufferedImage = ImageIO.read(url);

        write(bufferedImage, FORMAT_NAME, "D://");
    }
}
