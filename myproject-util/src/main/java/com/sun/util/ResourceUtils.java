package com.sun.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 资源获取
 * @author firefly
 * @version 1.0
 */
public class ResourceUtils {
	/** from Spring ResourceUtils, Pseudo URL prefix for loading from the class path: "classpath:" */
	public static final String CLASSPATH_URL_PREFIX = "classpath:";
	/** from webRoot */
	public static final String WEBROOT_URL_PREFIX = "webroot:";
	/** from Spring ResourceUtils, URL prefix for loading from the file system: "file:" */
	public static final String FILE_URL_PREFIX = "file:";
	/** from Spring ResourceUtils, URL protocol for a file in the file system: "file" */
	public static final String URL_PROTOCOL_FILE = "file";
	private static Log LOG = LogFactory.getLog(ResourceUtils.class);
 
	/**
	 * 获取资源（从classes下和jar下获取）
	 * @param cls 当前类
	 * @param path 相对路径
	 * @return
	 */
	public static InputStream getResource(Class<?> cls,String path){
		String absPath = path;//绝对路径
		if(path.startsWith("./")){//相对路径
			absPath = "/"+cls.getPackage().getName().replace(".", "/")+path.substring(1);
		}else if(!path.startsWith("/") || path.indexOf("/")==-1){//相对路径的当前文件
			absPath = "/"+cls.getPackage().getName().replace(".", "/")+"/"+path;
		}
		InputStream r = cls.getResourceAsStream(absPath);
		if(LOG.isDebugEnabled()){
			LOG.debug("class absPath:"+absPath);
			if(cls.getResource(absPath)!=null){
				LOG.debug("all absPath:"+cls.getResource(absPath).getPath());
			}
		}
		return  r;
	}
	
	/**
	 * 获取资源的URL
	 * from Spring ResourceUtils
	 * @param resourceLocation
	 * @return
	 * @throws FileNotFoundException
	 */
	public static URL getURL(String resourceLocation) throws FileNotFoundException {
		if (resourceLocation.startsWith(CLASSPATH_URL_PREFIX)) {
			String path = resourceLocation.substring(CLASSPATH_URL_PREFIX.length());
			URL url = getDefaultClassLoader().getResource(path);
			if (url == null) {
				String description = "class path resource [" + path + "]";
				throw new FileNotFoundException(
						description + " cannot be resolved to URL because it does not exist");
			}
			return url;
		} else if (resourceLocation.startsWith(WEBROOT_URL_PREFIX)){
			File resourcePath = getFile(resourceLocation);
			try {
				return resourcePath.toURL();
			} catch (MalformedURLException e) {
				throw new FileNotFoundException("Resource location [" + resourceLocation +
						"] is neither a URL not a well-formed file path");
			}
		}
		try {
			// try URL
			return new URL(resourceLocation);
		}
		catch (MalformedURLException ex) {
			// no URL -> treat as file path
			try {
				return new URL(FILE_URL_PREFIX + resourceLocation);
			}
			catch (MalformedURLException ex2) {
				throw new FileNotFoundException("Resource location [" + resourceLocation +
						"] is neither a URL not a well-formed file path");
			}
		}
	}
	
	/**
	 * 获取资源的URL
	 * @return
	 */
	private static ClassLoader getDefaultClassLoader() {
		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		if (cl == null) {
			// No thread context class loader -> use class loader of this class.
			cl = ClassUtils.class.getClassLoader();
		}
		return cl;
	}
	
	/**
	 * 获得文件
	 * from Spring ResourceUtils
	 * @param resourceLocation the resource location to resolve: either a
	 * "classpath:" pseudo URL, a "file:" URL, or a plain file path
	 * @return a corresponding File object
	 * @throws FileNotFoundException if the resource cannot be resolved to
	 * a file in the file system
	 */
	public static File getFile(String resourceLocation) throws FileNotFoundException {
		if (resourceLocation.startsWith(CLASSPATH_URL_PREFIX)) {
			String path = resourceLocation.substring(CLASSPATH_URL_PREFIX.length());
			String description = "class path resource [" + path + "]";
			URL url = getDefaultClassLoader().getResource(path);
			if (url == null) {
				throw new FileNotFoundException(
						description + " cannot be resolved to absolute file path " +
						"because it does not reside in the file system");
			}
			return getFile(url, description);
		} else if (resourceLocation.startsWith(WEBROOT_URL_PREFIX)){
			String path = resourceLocation.substring(WEBROOT_URL_PREFIX.length());
			File classPath = getFile(CLASSPATH_URL_PREFIX+"/");
			File rootPath = classPath.getParentFile().getParentFile();
			File resourcePath = new File(rootPath, path);
			if(resourcePath.exists()){
				return resourcePath;
			}else{
				throw new FileNotFoundException(
						path + " cannot be resolved to absolute file path " +
						"because it does not reside in the file system");
			}
		}
		try {
			// try URL
			return getFile(new URL(resourceLocation));
		}
		catch (MalformedURLException ex) {
			// no URL -> treat as file path
			return new File(resourceLocation);
		}
	}

	/**
	 * from Spring ResourceUtils
	 * @param resourceUrl the resource URL to resolve
	 * @return a corresponding File object
	 * @throws FileNotFoundException if the URL cannot be resolved to
	 * a file in the file system
	 */
	public static File getFile(URL resourceUrl) throws FileNotFoundException {
		return getFile(resourceUrl, "URL");
	}

	/**
	 * from Spring ResourceUtils
	 * @param resourceUrl the resource URL to resolve
	 * @param description a description of the original resource that
	 * the URL was created for (for example, a class path location)
	 * @return a corresponding File object
	 * @throws FileNotFoundException if the URL cannot be resolved to
	 * a file in the file system
	 */
	public static File getFile(URL resourceUrl, String description) throws FileNotFoundException {
		if (!URL_PROTOCOL_FILE.equals(resourceUrl.getProtocol())) {
			throw new FileNotFoundException(
					description + " cannot be resolved to absolute file path " +
					"because it does not reside in the file system: " + resourceUrl);
		}
		return new File(URLDecoder.decode(resourceUrl.getFile()));
	}
	
}
