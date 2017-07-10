package com.sun.abilities.io;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import org.junit.Test;

public class IoExample {
	/**
	 * 用字节流写文件
	 */
	@Test
	public void writeByteToFile() {
		FileOutputStream out = null;
		try {
			out = new FileOutputStream("src/main/java/com/sun/abilities/io/hello.txt");
			out.write("aaaa".getBytes());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 用字节流读文件
	 */
	@Test
	public void readByteFromFile() {
		FileInputStream in = null;
		try {
			byte[] b = new byte[1024];
			in = new FileInputStream("src/main/java/com/sun/abilities/io/hello.txt");
			in.read(b);
			System.out.println(new String(b));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 逐个字节读取 read方法读取的是字节流（每次读取一个字节），如果是中文，就是两个字节，就会出现乱码的
	 */
	@Test
	public void readByteFromFile2() {
		FileInputStream in = null;
		try {
			byte[] b = new byte[1024];
			in = new FileInputStream("src/main/java/com/sun/abilities/io/hello.txt");
			int index = 0;
			while ((index = in.read()) > 0) {
				System.out.print((char) index);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 字符流写文件
	 */
	@Test
	public void writeCharToFile() {
		FileWriter writer = null;
		try {
			writer = new FileWriter("src/main/java/com/sun/abilities/io/hello.txt");
			writer.write("123123123123");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 字符流读文件
	 */
	@Test
	public void readCharFromFile() {
		FileReader reader;
		try {
			char[] b = new char[1024];
			reader = new FileReader("src/main/java/com/sun/abilities/io/hello.txt");
			reader.read(b);
			System.out.println(new String(b));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 字节流转换为字符流
	 * 
	 * @throws IOException
	 */
	public void convertByteToChar() throws IOException {
		FileInputStream is = new FileInputStream("");
		// 把字节流转换为字符流，其实就是把字符流和字节流组合的结果。
		InputStreamReader reader = new InputStreamReader(is);
		char[] byteArray = new char[21];
		int size = reader.read(byteArray);
		System.out.println("大小:" + size + ";内容:" + new String(byteArray));
		is.close();
		reader.close();
	}

	/**
	 * 用缓冲流读文件
	 */
	@Test
	public static void readByBufferedInputStream() {
		BufferedInputStream is = null;
		try {
			File file = new File("d:/test.txt");
			byte[] byteArray = new byte[(int) file.length()];
			// 可以在构造参数中传入buffer大小
			is = new BufferedInputStream(new FileInputStream(file), 2 * 1024);
			int size = is.read(byteArray);
			System.out.println("大小:" + size + ";内容:" + new String(byteArray));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 
	 */
	public void readByBufferedReader() {
		BufferedReader reader = null;
		try {
			File file = new File("d:/test.txt");
			// 在字符流基础上用buffer流包装，也可以指定buffer的大小
			reader = new BufferedReader(new FileReader(file), 2 * 1024);
			char[] byteArray = new char[(int) file.length()];
			int size = reader.read(byteArray);
			System.out.println("大小:" + size + ";内容:" + new String(byteArray));
		} catch (Exception e) {
			try {
				reader.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	@Test
	public void fileInputStream() {
		FileInputStream in = null;
		InputStreamReader ir = null;
		BufferedReader br = null;
		try {
			in = new FileInputStream("src/main/java/com/sun/io/hello.txt");
			ir = new InputStreamReader(in);
			br = new BufferedReader(ir);
			String a = new String();
			while ((a = br.readLine()) != null) {
				System.out.println(a);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
				ir.close();
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Test
	public void fileOutStream() {
		FileOutputStream fos = null;
		OutputStreamWriter osw = null;
		BufferedWriter bw = null;
		try {
			fos = new FileOutputStream("src/main/java/com/sun/io/hello.txt");
			osw = new OutputStreamWriter(fos);
			bw = new BufferedWriter(osw);
			bw.write("sssssssssssssssssss\naaaaaaa");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				// 注意顺序 从后往前
				bw.close();
				osw.close();
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	@Test
	public void testNioRead() {
		RandomAccessFile aFile = null;
		try {
			aFile = new RandomAccessFile("src/main/java/com/sun/io/hello.txt", "rw");
			FileChannel fileChannel = aFile.getChannel();
			ByteBuffer buf = ByteBuffer.allocate(20);
			int bytesRead = fileChannel.read(buf);
			while (bytesRead != -1) {
				buf.flip();
				while (buf.hasRemaining()) {
					// System.out.print((char) buf.get());
					System.out.println((char) buf.get() + "    " + buf.position() + "   " + buf.remaining());
				}
				buf.compact();
				bytesRead = fileChannel.read(buf);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (aFile != null) {
					aFile.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Test
	public void testNioWriter() {
		RandomAccessFile aFile = null;
		try {
			aFile = new RandomAccessFile("src/main/java/com/sun/io/hello.txt", "rw");
			FileChannel fileChannel = aFile.getChannel();
			ByteBuffer buf = ByteBuffer.allocate(50);
			buf.clear();
			// buf.position(10);
			buf.put("ssssssssssssdddddddddddddddssssssss".getBytes());
			buf.flip();
			while (buf.hasRemaining()) {
				fileChannel.write(buf);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (aFile != null) {
					aFile.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
