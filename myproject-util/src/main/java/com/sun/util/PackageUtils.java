package com.sun.util;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
  
/**
 * 包工具类
 * @author firefly
 * @version 1.0
 */
public class PackageUtils {  
	private static final String CLS_FILE_NAME = ".class";
	
    @SuppressWarnings("rawtypes")
	public static void main(String[] args) throws Exception {  
//        String packageName = "com.inq..entity";  
        String packageName = "com.inq.entity";  
        List<Class> ls = getClassName(packageName);
        for (Class c : ls) {
			System.out.println(c.getName());
		}
        
        String packageName1 = "com.inq.person";  
        copyPackageFile(packageName1, "/Users/firefly/aaa");
    }
    
    /**
     * 拷贝包中的文件
     * @param packageDir
     * @param outputPath
     * @throws IOException
     */
    public static void copyPackageFile(String packageDir,String outputPath) throws IOException{
    		packageDir = packageDir.replace(".", "/");
    		if(!packageDir.startsWith("/"))packageDir="/"+packageDir;
    		URL url = PackageUtils.class.getResource(packageDir);//得到classesPatch
		File targetPackage = new File(url.getPath());
		File outputFile = new File(outputPath);
		FileUtils.copyDirectory(targetPackage, outputFile);
    }
    	
    /** 
     * 获取某包下所有类 
     * @param registPackage 包名 
     * @return 类的完整名称 
     */  
    @SuppressWarnings("rawtypes")
	public static List<Class> getClassName(String registPackage) {
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
		List<File> lastPackages = new ArrayList<File>();
		URL url = PackageUtils.class.getResource("/");//得到classesPatch
		File classesPath = new File(url.getPath());
		File dir = classesPath;
		String[] sps = prefix.split("/");
		for (String s : sps) {
			dir = findPackage(dir,s);
			if(dir==null){
				break;
			}
		}
		if(suffix!=null){
			String[] ss = suffix.split("/");
			List<File> dirLs = new ArrayList<File>();
			deepFindPackage(dir, ss[0], dirLs);
			if(ss.length>1){
				for (File file : dirLs) {
					File df = file;
					for (int i = 1; i < ss.length; i++) {
						String packageName = ss[i];
						df = findPackage(df,packageName);
						if(df==null){
							break;
						}
					}
					if(df != null){
						lastPackages.add(df);
					}
				}
			}else{
				lastPackages.addAll(dirLs);
			}
		}else{
			lastPackages.add(dir);
		}
		for (File pk : lastPackages) {
			ls.addAll(findPackageClass(pk, classesPath.getAbsolutePath()));
		}
		return ls;
    }
    
    /**
     * 查找注册的包
     * @param dir
     * @param nextPackage
     * @return
     */
    private static File findPackage(File dir,String nextPackage){
    		File p=null;
    		File[] fs = dir.listFiles();
    		for (File f : fs) {
			if(f.isDirectory() && f.getName().equals(nextPackage)){
				p = f;
				break;
			}
		}
    		return p;
    }

    /**
     * 深度查找包
     * @param dir
     * @param nextPackage
     * @param log
     */
    private static void deepFindPackage(File dir,String nextPackage,List<File> log){
	    	File[] fs = dir.listFiles();
	    	for (File f : fs) {
	    		if(f.isDirectory()){
	    			if(f.getName().equals(nextPackage)){
	    				log.add(f);
	    			}else{
	    				deepFindPackage(f, nextPackage, log);
	    			}
	    		}
	    	}
    }
    
    @SuppressWarnings("rawtypes")
	private static List<Class> findPackageClass(File dir,String classesPath){
    		List<Class> ls = new ArrayList<Class>();
    		File[] fs = dir.listFiles();
    		for (File f : fs) {
    			String filePath = f.getAbsolutePath();
    			if(filePath.endsWith(CLS_FILE_NAME)){
    				String clsPath = StringUtils.removeStart(filePath, classesPath);
    				if(clsPath.startsWith("/"))clsPath = clsPath.substring(1);
    				clsPath = clsPath.replace("/", ".").substring(0, clsPath.length()-CLS_FILE_NAME.length());
    				Class<?> cls =null;
					try {
						cls = Class.forName(clsPath);
						ls.add(cls);
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
    			}
		}
    		return ls;
    }
}