package com.sun.application.htmlTable2Excel;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExcelImageDemo {

	public static void main(String[] args) {
		FileOutputStream fileOut = null;
		BufferedImage bufferImg = null;
		// 先把读进来的图片放到一个ByteArrayOutputStream中，以便产生ByteArray
		try {
			ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
			bufferImg = ImageIO.read(new File("D:/ubuntn.jpg"));
			ImageIO.write(bufferImg, "jpg", byteArrayOut);

			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet1 = wb.createSheet("test picture");
			// 画图的顶级管理器，一个sheet只能获取一个（一定要注意这点）
			HSSFPatriarch patriarch = sheet1.createDrawingPatriarch();
			for (int i = 0; i < 3; i++) {
				// anchor主要用于设置图片的属性
				HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 1023, 250, (short) 1, 1+i*10, (short) 5, 8+i*10);
				// 插入图片
				patriarch.createPicture(anchor, wb.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG));
			}
			HSSFSheet sheet2 = wb.createSheet("test picture2");
			//第二个sheet页
			HSSFPatriarch patriarch2 = sheet2.createDrawingPatriarch();
			for (int i = 0; i < 3; i++) {
				// anchor主要用于设置图片的属性
				HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 1023, 250, (short) 1, 1+i*10, (short) 5, 8+i*10);
				// 插入图片
				patriarch2.createPicture(anchor, wb.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG));
			}
			fileOut = new FileOutputStream("Excel.xls");
			// 写入excel文件
			wb.write(fileOut);
			System.out.println("----Excle文件已生成------");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fileOut != null) {
				try {
					fileOut.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	// 关于HSSFClientAnchor(dx1,dy1,dx2,dy2,col1,row1,col2,row2)的参数，有必要在这里说明一下：
	// dx1：起始单元格的x偏移量，
	// dy1：起始单元格的y偏移量，
	// dx2：终止单元格的x偏移量，
	// dy2：终止单元格的y偏移量，
	// col1：起始单元格列序号，从0开始计算；
	// row1：起始单元格行序号，从0开始计算，
	// col2：终止单元格列序号，从0开始计算；
	// row2：终止单元格行序号，从0开始计算，
	//添加多个图片时:多个pic应该share同一个DrawingPatriarch在同一个sheet里面。
}
