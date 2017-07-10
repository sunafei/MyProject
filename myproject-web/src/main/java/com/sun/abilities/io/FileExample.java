package com.sun.abilities.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.RandomAccess;

import org.junit.Test;

public class FileExample {
	@Test
	public void fileOperation() {
		try {
			// 检查文件是否存在
			File file = new File("D:\\test.txt");
			boolean fileExists = file.exists();
			System.out.println(fileExists);
			// 创建文件目录,若父目录不存在则返回false 注意这里只创建文件夹
			File file1 = new File("d:/demo/ss.txt");
			boolean dirCreated = file1.mkdir();
			System.out.println(dirCreated);
			// 创建文件目录,若父目录不存则连同父目录一起创建
			File file2 = new File("d:/demo/ss");
			boolean dirCreated2 = file2.mkdirs();
			System.out.println(dirCreated2);

			File file4 = new File("d:/demo.txt");
			// 判断长度
			long length = file4.length();
			System.out.println(length);
			// 重命名文件
			boolean isRenamed = file4.renameTo(new File("d:/demo2.txt"));
			// 删除文件
			boolean isDeleted = file4.delete();
			File file5 = new File("d:/Program Files");
			// 是否是目录
			boolean isDirectory = file5.isDirectory();
			// 列出文件名
			String[] fileNames = file5.list();
			for (String string : fileNames) {
				System.out.println(string);
			}
			// 列出目录
			File[] files = file4.listFiles();
		} catch (Exception e) {

		}
	}

	/**
	 * 创建file
	 */
	@Test
	public void createFile() {

	}

	/**
	 * 随机读取文件
	 */
	@Test
	public void randomAccessFileRead() {
		// 创建一个RandomAccessFile对象
		RandomAccessFile file = null;
		try {
			file = new RandomAccessFile("D:/demo2.txt", "rw");
			// 通过seek方法来移动读写位置的指针
			file.seek(10);
			// 获取当前指针
			long pointerBegin = file.getFilePointer();
			// 从当前指针开始读
			byte[] contents = new byte[1024];
			file.read(contents);
			long pointerEnd = file.getFilePointer();
			System.out.println("pointerBegin:" + pointerBegin + "\n" + "pointerEnd:" + pointerEnd + "\n" + new String(contents));

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				file.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 从自定位置插入数据
	 */
	@Test
	public void randomAccessFileWrite() {
		RandomAccessFile file = null;
		try {
			// 创建一个RandomAccessFile对象
			file = new RandomAccessFile("d:/demo2.txt", "rw");
			// 通过seek方法来移动读写位置的指针
			//file.seek(file.length());
			file.seek(5);
			// 获取当前指针
			long pointerBegin = file.getFilePointer();
			// 从当前指针位置开始写
			file.write("HELLO WORD".getBytes());
			long pointerEnd = file.getFilePointer();
			System.out.println("pointerBegin:" + pointerBegin + "\n" + "pointerEnd:" + pointerEnd + "\n");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				file.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
