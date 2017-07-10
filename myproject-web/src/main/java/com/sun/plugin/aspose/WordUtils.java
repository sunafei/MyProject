package com.sun.plugin.aspose;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.aspose.words.ConvertUtil;
import com.aspose.words.Document;
import com.aspose.words.ImportFormatMode;
import com.aspose.words.License;
import com.aspose.words.SaveFormat;
/**
 * Word操作工具类
 *
 */
public class WordUtils {
	private static final Logger LOG = LoggerFactory.getLogger(WordUtils.class);
	private final static Pattern REGEX_PROPERTY = Pattern.compile("\\^\\{(.*?)\\}");
	/**
	 * license
	 * @return
	 */
	static{
		InputStream is = ConvertUtil.class.getResourceAsStream("/resource/aspose/license.xml"); 
		License aposeLic = new License();
		try {
			aposeLic.setLicense(is);
		} catch (Exception e) {
			LOG.error("WordUtils初始化失败");
		}
	}

	/**
	 * word转PDF
	 * @param src 要转成PDF的文件
	 * @param result 目标文件
	 * @throws FileNotFoundException
	 * @throws Exception
	 */
	public static void word2Pdf(File src,File result) throws FileNotFoundException, Exception {
		Document doc = new Document(new FileInputStream(src));
		doc.save(result.getAbsolutePath(),SaveFormat.PDF);
	}

	
	/**
	 * 将doc2合并到doc1
	 * @param doc1 Document1
	 * @param doc2 Document2
	 */
	public static Document mergeDoc(Document doc1, Document doc2){
		doc1.appendDocument(doc2, ImportFormatMode.KEEP_SOURCE_FORMATTING);
		return doc1;
	}
	
	/**
	 * 转RTF
	 * @param src 要转成RTF的文件
	 * @param result 目标文件
	 * @throws FileNotFoundException
	 * @throws Exception
	 */
	public static void word2Rtf(File src,File result) throws FileNotFoundException, Exception {
		Document doc = new Document(new FileInputStream(src));
		doc.save(result.getAbsolutePath(),SaveFormat.RTF);
	}
	
	/**
	 * 替换文件内容
	 * @param src 要替换文字的文件
	 * @param regex 要替换的文字
	 * @param replacement 替换成什么
	 * @return doc
	 * @throws Exception
	 */
	public static Document replaceStr(File src,String regex,String replacement) throws Exception{
		Assert.notNull(src);
		Document doc = new Document(new FileInputStream(src));
		return replaceStr(doc, regex, replacement);
	}
	
	
	
	/**
	 * 查找匹配字符
	 * @param doc Document
	 * @param o Object
	 * @param df 数据格式化工具类
	 * @return
	 * @throws Exception 
	 */
	public static Document replaceStr4Obj(Document doc,final Object o,final DataFormat df) throws Exception{
		Assert.notNull(doc);
		Assert.notNull(o);
		Assert.notNull(df);
		try {
			ReplacingCallback rc = new ReplacingCallback(o);
			doc.getRange().replace(REGEX_PROPERTY, rc , false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return doc;
	}
	
	/**
	 * 查找匹配字符
	 * @param doc Document
	 * @param o Object
	 * @return
	 * @throws Exception 
	 */
	public static Document replaceStr4Obj(Document doc,final Object o) throws Exception{
		Assert.notNull(doc);
		Assert.notNull(o);
		return replaceStr4Obj(doc, o, new DataFormat());
	}
	
	/**
	 * 替换文字内容
	 * @param doc Document
	 * @param regex 要替换的文字
	 * @param replacement 替换成什么
	 * @return doc
	 * @throws Exception
	 */
	public static Document replaceStr(Document doc,String regex,String replacement) throws Exception{
		Assert.notNull(doc);
		Assert.notNull(regex);
		Assert.notNull(replacement);
		Pattern p = Pattern.compile(regex);
		doc.getRange().replace(p, replacement);
		return doc;
	}
	
	/**
	 * 获得要操作的word文档对象
	 * @param src 要操作的word文件
	 * @return
	 * @throws Exception
	 */
	public static Document getDoc(File src) throws Exception{
		Assert.isTrue(src.exists());
		return new Document(new FileInputStream(src));
	}
}

