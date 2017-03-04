package org.cc11001100.web.test.process;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.apache.log4j.Logger;
import org.cc11001100.web.test.domain.ParamPair;
import org.cc11001100.web.test.domain.ResponseDO;
import org.cc11001100.web.test.generator.ParamGenerator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 
 * 对于jetty服务器的参数缺失的处理
 * 
 * 这个jetty是针对eclipse intergration版本的，并不具有普适性，依赖于服务器的特定错误返回。
 * 
 *  这个的类的工作原理是jetty服务器会将参数缺失信息报为400，同时会返回一个400页面（html）提示用户，
 *  这个类做的事情就是解析这个400页面，将其中需要的参数名字和参数类型取出来。
 *  
 * 
 * @author chenjc20326
 *
 */
public class ParamLackProcessor4Jetty implements ParamLackProcessor{

	private Logger logger=Logger.getLogger(ParamLackProcessor4Jetty.class);
	
	/** html中title标签的名字 */
	private static final String TITLE_TAG_NAME="title";
	
	/** 引号 */
	private static final String QUOTATION_MARK="'";
	
	/** 中括号 */
	private static final String BRACKET="[]";
	
	/** 空字符串 */
	private static final String NULL_STRING="";
	
	@Override
	public ParamPair process(ResponseDO responseDO) {

		String resposneContent=responseDO.getResponseContent();
		
		Document document=Jsoup.parse(resposneContent);
		Elements elements=document.getElementsByTag(TITLE_TAG_NAME);
		Element titleElement=elements.get(0);
		
		if(titleElement==null){
			return null;
		}
		
		String titleContent=titleElement.text();
		
		Class<? extends Object> clazz=getTypeByTitleContent(titleContent);
		if(clazz==null){
			return null;
		}
		
		int beginIndex=titleContent.indexOf(QUOTATION_MARK)+1;
		int endIndex=titleContent.lastIndexOf(QUOTATION_MARK);
		if(beginIndex<0 || endIndex<0){
			return null;
		}
		
		String paramName=titleContent.substring(beginIndex, endIndex);
		String paramValue=ParamGenerator.generatorStringParamValue(clazz, paramName);
		
		return new ParamPair(paramName, paramValue);
	}
	
	/**
	 * 这个类只能处理HTTP状态码为400的响应了
	 */
	@Override
	public boolean canProcess(ResponseDO responseDO) {
		if(responseDO==null){
			return false;
		}
		return responseDO.getStatus()==HttpStatus.SC_BAD_REQUEST;
	}
	
	/**
	 * 根据传入的标题内容来分析出服务器需要的参数是什么类型的
	 * @param titleContent
	 * @return
	 */
	private Class<? extends Object> getTypeByTitleContent(String titleContent){
		logger.info(titleContent);		
		titleContent=titleContent.substring("Error 400 Required ".length());
		String className=titleContent.split("\\s+")[0].replace(BRACKET,NULL_STRING);
		
		try {
			return Class.forName("java.lang."+StringUtils.capitalize(className));
		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
			logger.info(e.getMessage());
		}
		
		return null;
	}

}
