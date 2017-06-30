package com.sun.util.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import com.sun.util.ClassUtils;


/**
 * excel工具类
 * @author firefly
 *
 */
public class ExcelUtils {
	/**
	 * 转换excel内容到对象
	 * 注：不支持合并行列、仅第一行表头与类属性建立关系
	 * @param xlsFile
	 * @param sheetIndex
	 * @param toCls
	 * @param fieldRelation 字段关系（key=首列,val=属性）
	 * @param rc
	 * @return
	 */
	public static <T> List<T> convert(File xlsFile,int sheetIndex,Class<T> toCls,Map<String,String> fieldRelation,RowConvert<T> rc, ICellConvert cellConvert){
		List<T> ls = new ArrayList<T>();
		try {
			HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(xlsFile));
			HSSFSheet sheet = wb.getSheetAt(sheetIndex);
			//1、初始化关系 init cellIndexFieldRelation
			Map<Integer,BeanFieldMeta> cellIndexFieldRelation = new HashMap<Integer, BeanFieldMeta>();
			Row relationRow = sheet.getRow(0);
			if(relationRow==null){
				//DEBUG 无内容
				return ls;
			}
			for (int i = 0; i < relationRow.getLastCellNum(); i++) {
				String cellVal = getStringCellVal(relationRow.getCell(i),cellConvert);
				if(fieldRelation.containsKey(cellVal)){//对应上
					Method fMethod = ClassUtils.getGetterMethod(toCls, fieldRelation.get(cellVal));
					if(fMethod != null){
						Class<?> fType = fMethod.getReturnType();
						BeanFieldMeta bfMeta = new BeanFieldMeta();
						bfMeta.cellIndex=i;
						bfMeta.cellTitle=cellVal;
						bfMeta.fieldName=fieldRelation.get(cellVal);
						bfMeta.fieldType=fType;
						cellIndexFieldRelation.put(i, bfMeta);
					}
				}
			}
			
			//2、转换内容
			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				Row t = sheet.getRow(i);
				Map<String, String> rowMap = null;
				if(rc != null){
					rowMap = convertRowMap(relationRow,t,cellConvert);
				}
				T bean = toCls.newInstance();
				for (Iterator<Integer> indexI = cellIndexFieldRelation.keySet().iterator(); indexI.hasNext();) {
					Integer cellIndex = indexI.next();
					BeanFieldMeta bfMeta = cellIndexFieldRelation.get(cellIndex);
					String cellVal = getStringCellVal(t.getCell(cellIndex),cellConvert);
					Object v = cellVal;
					if(rc != null){
						v = rc.cellProcess(rowMap,bfMeta,cellVal);
					}
					v = cellConvert.convert(v, bfMeta.fieldType);
					PropertyUtils.setProperty(bean, bfMeta.fieldName, v);
				}
				if(rc != null){
					bean = rc.rowProcess(rowMap,bean);
				}
				ls.add(bean);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ls;
	}
	/**
	 * 行信息转入到map（包含所有列）
	 * @param relationRow
	 * @param r
	 * @return
	 */
	public static Map<String,String> convertRowMap(Row relationRow,Row r, ICellConvert cellConvert){
		Map<String,String> rowMap = new HashMap<String, String>();
		for (int i = 0; i < relationRow.getLastCellNum(); i++) {
			Cell c = r.getCell(i);
			if(c == null){
				rowMap.put(getStringCellVal(relationRow.getCell(i),cellConvert), null);
			}else{
				rowMap.put(getStringCellVal(relationRow.getCell(i),cellConvert), getStringCellVal(c,cellConvert));
			}
		}
		return rowMap;
	}
	
	/**
	 * 获得单元格的值
	 * @param c
	 * @return
	 */
	public static String getStringCellVal(Cell c, ICellConvert cellConvert){
		if(c==null)return null;
		if(Cell.CELL_TYPE_STRING == c.getCellType()){
			return c.getStringCellValue();
		}else if(Cell.CELL_TYPE_NUMERIC == c.getCellType()){
			if(HSSFDateUtil.isCellDateFormatted(c)){
				Date d = c.getDateCellValue();
				if(d!=null)return cellConvert.convert(d, String.class);
			}else{
				return String.valueOf(c.getNumericCellValue());
			}
		}else if(Cell.CELL_TYPE_BOOLEAN == c.getCellType()){
			return c.getBooleanCellValue()?"是":"否";
		}
		return null;
	}
	
	
	/**
	 * 自定义的行、列转换规则
	 * @author firefly
	 *
	 * @param <T>
	 */
	public interface RowConvert<T>{
		public Object cellProcess(Map<String, String> rowMap, BeanFieldMeta bfMeta, String cellVal);
		public T rowProcess(Map<String, String> rowMap,T row);
	}
	
	
//	public static void main(String[] args) {
//		File f =new File("/Users/firefly/Desktop/a.xls");
//		Map<String,String> fieldRelation = new HashMap<String, String>();
//		fieldRelation.put("书名", "name");
//		fieldRelation.put("isbn", "isbn");
//		
//		ExcelUtils.convert(f, 0, Book.class, fieldRelation, null);
//	}
	
}
