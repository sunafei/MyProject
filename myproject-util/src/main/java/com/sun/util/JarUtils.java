package com.sun.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.commons.lang.StringUtils;

/**
 * jar包工具类
 * @author firefly
 * @version 1.0
 */
public class JarUtils {
	private static final String CLS_FILE_NAME = ".class";
	
//	public static void main(String[] args) {
//		String jarPath= "/Users/firefly/workspace/lib/soulUtils/libs/log4j-1.2.11.jar";
//		String registPackage	= "org.apache.log4j.config";
//		String registPackage2 = "org.apache..config";
//		try {
//			List<Class> ls = getPackageCls(jarPath, registPackage2);
//			for (Class cls : ls) {
//				System.out.println(cls.getName());
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		try {
//			String unzipPk = "org.apache.log4j.lf5";
//			unzipPackageFile(jarPath, unzipPk, "/Users/firefly/aaa");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
	/**
	 * 获得jar里面的文件
	 * @param jarPath
	 * @param filePath
	 * @return
	 * @throws IOException 
	 */
	public static InputStream getFile(String jarPath,String filePath) throws IOException{
		@SuppressWarnings("resource")
		JarFile jarFile = new JarFile(jarPath);  
		InputStream input = null;
		Enumeration<JarEntry> entrys = jarFile.entries();
		if(filePath.startsWith("/"))filePath = filePath.substring(1);
		while (entrys.hasMoreElements()) {
			JarEntry jarEntry = entrys.nextElement();
			if(jarEntry.getName().equals(filePath)){
				input = jarFile.getInputStream(jarEntry);// 根据实体创建输入流  
		        break;
			}
		}
		return input;
	}
	
	/**
	 * 解压包里的文件
	 * @param jarPath
	 * @param registPackage
	 * @return
	 * @throws IOException
	 */
	public static void unzipPackageFile(String jarPath,String packageDir,String outputPath) throws IOException{
		@SuppressWarnings("resource")
		JarFile jarFile = new JarFile(jarPath);  
		Enumeration<JarEntry> entrys = jarFile.entries();
		packageDir = packageDir.replace(".", "/");
		if(!packageDir.endsWith("/"))packageDir+="/";
		while (entrys.hasMoreElements()) {
			JarEntry jarEntry = entrys.nextElement();
			String entryName = jarEntry.getName();
			if(entryName.startsWith(packageDir)){
				String outFile = StringUtils.removeStart(entryName,packageDir);
				if(StringUtils.isBlank(outFile))continue;
				if(jarEntry.isDirectory())continue;
				File outF = null;
				if(outFile.indexOf("/")!=-1){
					String[] allF = outFile.split("/");
					outF = new File(outputPath);
					for (int i = 0; i < allF.length; i++) {
						outF = new File(outF,allF[i]);
						if(i == allF.length-1)break;
						if(!outF.exists() || !outF.isDirectory()){
							outF.mkdir();
						}
					}
				}else{
					outF = new File(outputPath,outFile);
				}
				writeFile(jarFile.getInputStream(jarEntry), outF);
			}
		}
	}
	
	private static void writeFile(InputStream ips, File outputFile) throws IOException{  
        OutputStream ops = new BufferedOutputStream(new FileOutputStream(outputFile));  
        try{   
            byte[] buffer = new byte[1024];   
            int nBytes = 0;   
            while ((nBytes = ips.read(buffer)) > 0){   
                ops.write(buffer, 0, nBytes);   
            }   
        }catch (IOException ioe){   
            throw ioe;   
        } finally {   
            try {   
                if (null != ops){   
                    ops.flush();   
                    ops.close();   
                }   
            } catch (IOException ioe){   
                throw ioe;   
            } finally{   
                if (null != ips){   
                    ips.close();   
                }   
            }   
        }  
    }
	
	/**
	 * 获得包里的类
	 * @param jarPath
	 * @param registPackage
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings("rawtypes")
	public static List<Class> getPackageCls(String jarPath,String registPackage) throws IOException, ClassNotFoundException{
		List<Class> ls = new ArrayList<Class>();
		String prefix = null;
		String suffix = null;
		int f = registPackage.indexOf("..");
		if(f!=-1){
			prefix = registPackage.substring(0, f).replace(".", "/");
			suffix = registPackage.substring(f+"..".length()).replace(".", "/");
		}else{
			prefix = registPackage.replace(".", "/");
		}
		JarFile jarFile = new JarFile(jarPath);  
		try {
			Enumeration<JarEntry> entrys = jarFile.entries();
			while (entrys.hasMoreElements()) {
				JarEntry jarEntry = entrys.nextElement();
				String entryName = jarEntry.getName();
				if (!entryName.endsWith(CLS_FILE_NAME))
					continue;
				if (jarEntry.getName().startsWith(prefix)) {
					String tmpName = entryName.substring(0, entryName.lastIndexOf("/"));
					if ((suffix != null && tmpName.endsWith(suffix)) || suffix == null) {
						String className = entryName.substring(0, entryName.length() - CLS_FILE_NAME.length());
						Class cls = Class.forName(className.replace("/", "."));
						ls.add(cls);
					}
				}
			}
		}finally{
			jarFile.close();
		}
		return ls;
	}
}
