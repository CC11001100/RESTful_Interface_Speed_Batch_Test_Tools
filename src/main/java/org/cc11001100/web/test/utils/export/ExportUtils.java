package org.cc11001100.web.test.utils.export;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.cc11001100.web.test.domain.ResponseDO;

/**
 * 导出工具
 * 
 * 
 * @author chenjc20326
 *
 */
public class ExportUtils {

	private static Logger logger=Logger.getLogger(ExportUtils.class);
	
	/**
	 * 将结果导出为excel表格
	 * @param list
	 * @param path
	 */
	public static void export2Excel(List<ResponseDO> list, String path){
		 // 声明一个工作薄
	      HSSFWorkbook workbook = new HSSFWorkbook();
	      // 生成一个表格
	      HSSFSheet sheet = workbook.createSheet("扫描结果");
	      // 设置表格默认列宽度为15个字节
	      sheet.setDefaultColumnWidth((short) 15);
	      // 生成一个样式
	      HSSFCellStyle style = workbook.createCellStyle();
	      // 设置这些样式
	      style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
	      style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	      style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
	      style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
	      style.setBorderRight(HSSFCellStyle.BORDER_THIN);
	      style.setBorderTop(HSSFCellStyle.BORDER_THIN);
	      style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	      
	      // 生成一个字体
	      HSSFFont font = workbook.createFont();
	      font.setColor(HSSFColor.VIOLET.index);
	      font.setFontHeightInPoints((short) 12);
	      font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	      // 把字体应用到当前的样式
	      style.setFont(font);
	      
	      // 生成并设置另一个样式
	      HSSFCellStyle style2 = workbook.createCellStyle();
	      style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
	      style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	      style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
	      style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
	      style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
	      style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
	      style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	      style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
	      
	      // 生成另一个字体
	      HSSFFont font2 = workbook.createFont();
	      font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
	      // 把字体应用到当前的样式
	      style2.setFont(font2);
	     
	      // 声明一个画图的顶级管理器
	      HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
	      // 定义注释的大小和位置,详见文档
//	      HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0, 0, 0, 0, (short) 4, 2, (short) 6, 5));
//	      // 设置注释内容
//	      comment.setString(new HSSFRichTextString("可以在POI中添加注释！"));
//	      // 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.
//	      comment.setAuthor("CC11001100");
	 
	      
	      String[] headers=new String[]{"响应时间(ms)", "响应码", "请求url","返回内容"};
	      //产生表格标题行
	      HSSFRow row = sheet.createRow(0);
	      for (short i = 0; i < headers.length; i++) {
	         HSSFCell cell = row.createCell(i);
	         cell.setCellStyle(style);
	         HSSFRichTextString text = new HSSFRichTextString(headers[i]);
	         cell.setCellValue(text);
	      }
	 
	      
	      for(int i=0;i<list.size();i++){
	    	  
	    	  ResponseDO responseDO=list.get(i);
	    	  row = sheet.createRow(i+1);
	    	  
	    	  //响应时间
	    	  HSSFCell cell = row.createCell(0);
	    	  cell.setCellStyle(style2);
	    	  HSSFRichTextString richString = new HSSFRichTextString(responseDO.getSpend().toString());
              HSSFFont font3 = workbook.createFont();
              font3.setColor(HSSFColor.BLUE.index);
              richString.applyFont(font3);
              cell.setCellValue(richString);
              sheet.setColumnWidth(0, 15*255);
              
              //响应码
	    	  cell = row.createCell(1);
	    	  cell.setCellStyle(style2);
	    	  richString = new HSSFRichTextString(responseDO.getStatus().toString());
              font3 = workbook.createFont();
              font3.setColor(HSSFColor.BLUE.index);
              richString.applyFont(font3);
              cell.setCellValue(richString);
              sheet.setColumnWidth(1, 15*255);
              
              //请求url
	    	  cell = row.createCell(2);
	    	  cell.setCellStyle(style2);
	    	  StringBuilder url=new StringBuilder(responseDO.getUrl());
	    	  Map<String, String> params=responseDO.getParams();
	    	  boolean isFirst=true;
	    	  for(Iterator<Entry<String,String>> iterator=params.entrySet().iterator();iterator.hasNext();){
	    		  Entry<String,String> entry=iterator.next();
	    		  StringBuilder sb=new StringBuilder();
	    		  sb.append(entry.getKey()).append("=").append(entry.getValue());
	    		  if(isFirst){
	    			  url.append("?");
	    			  isFirst=false;
	    		  }else{
	    			  url.append("&");
	    		  }
	    		  url.append(sb.toString());
	    	  }
	    	  richString = new HSSFRichTextString(url.toString());
              font3 = workbook.createFont();
              font3.setColor(HSSFColor.BLUE.index);
              richString.applyFont(font3);
              cell.setCellValue(richString);
              sheet.setColumnWidth(2, 78*255);
              
              //返回内容
	    	  cell = row.createCell(3);
	    	  cell.setCellStyle(style2);
	    	  String responseContent=responseDO.getResponseContent();
	    	  if(responseContent.length()>32765){
	    		  responseContent=responseContent.substring(0,32700);
	    		  responseContent+="(AFTER DATA BECAUSE TOO LONG CANT STORE!!!)";
	    	  }
	    	  richString = new HSSFRichTextString(responseContent);
              font3 = workbook.createFont();
              font3.setColor(HSSFColor.BLUE.index);
              richString.applyFont(font3);
              cell.setCellValue(richString);
              sheet.setColumnWidth(3,78*255);
              
              
              logger.info("Export ["+responseDO.getUrl()+"] to excel.");
              
	      }
	      
	      logger.info("Export all urls to excel done.");
//	      
//	      //遍历集合数据，产生数据行
//	      Iterator<T> it = list.iterator();
//	      int index = 0;
//	      while (it.hasNext()) {
//	         index++;
//	         row = sheet.createRow(index);
//	         T t = (T) it.next();
//	         //利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
//	         Field[] fields = t.getClass().getDeclaredFields();
//	         for (short i = 0; i < fields.length; i++) {
//	            HSSFCell cell = row.createCell(i);
//	            cell.setCellStyle(style2);
//	            Field field = fields[i];
//	            String fieldName = field.getName();
//	            String getMethodName = "get"
//	                   + fieldName.substring(0, 1).toUpperCase()
//	                   + fieldName.substring(1);
//	            try {
//	                Class tCls = t.getClass();
//	                Method getMethod = tCls.getMethod(getMethodName,
//	                      new Class[] {});
//	                Object value = getMethod.invoke(t, new Object[] {});
//	                //判断值的类型后进行强制类型转换
//	                String textValue = null;
//	                //如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
//	                if(textValue!=null){
//	                    HSSFRichTextString richString = new HSSFRichTextString(textValue);
//	                    HSSFFont font3 = workbook.createFont();
//	                    font3.setColor(HSSFColor.BLUE.index);
//	                    richString.applyFont(font3);
//	                    cell.setCellValue(richString);
//	                }
//	            } catch (SecurityException e) {
//	                // TODO Auto-generated catch block
//	                e.printStackTrace();
//	            } catch (NoSuchMethodException e) {
//	                // TODO Auto-generated catch block
//	                e.printStackTrace();
//	            } catch (IllegalArgumentException e) {
//	                // TODO Auto-generated catch block
//	                e.printStackTrace();
//	            } catch (IllegalAccessException e) {
//	                // TODO Auto-generated catch block
//	                e.printStackTrace();
//	            } catch (InvocationTargetException e) {
//	                // TODO Auto-generated catch block
//	                e.printStackTrace();
//	            } finally {
//	                //清理资源
//	            }
//	         }
//	 
//	      }
	      try {
	    	 path=processFileExtension(path);
	         workbook.write(new FileOutputStream(new File(path)));
	      } catch (IOException e) {
	         // TODO Auto-generated catch block
	         e.printStackTrace();
	      }finally{
	    	  try {
	    		  //如果不释放资源的话，程序运行期间这个excel文件是不能被点击的。
				workbook.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	      }
	 
	}
	
	/**
	 * 对文件扩展名做一下处理，如果没有传入扩展名的话就给它补充上一个吧
	 * 
	 * @param filename
	 * @return
	 */
	private static String processFileExtension(String filename){
		if(StringUtils.containsIgnoreCase(filename, "xls") ||
				StringUtils.containsIgnoreCase(filename, "xlsx")){
			return filename;
		}
		
		return new StringBuilder(filename).append(".xls").toString();
	}
	
}
