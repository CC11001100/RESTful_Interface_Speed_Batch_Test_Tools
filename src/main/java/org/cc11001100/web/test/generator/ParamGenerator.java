package org.cc11001100.web.test.generator;

import java.util.Date;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

/**
 * 参数生成器，根据传入的参数名生成对应的值
 * 
 * 
 * 为什么会有这个？
 * 		传入正确的参数值能提高测试的精准度，还有更好的办法吗  = = 		
 * 
 * 
 * @author chenjc20326
 *
 */
public class ParamGenerator {
	
	/**
	 * 根据传入的参数生成对应的值，将生成的参数值统统转为String类型的，因为在网络上传输的参数类型都是String类型的
	 * 
	 * @param <T>
	 * 
	 * @param paramName
	 * @return
	 */
	public static <T> String generatorStringParamValue(Class<T> clazz, String paramName){

		if(StringUtils.isEmpty(paramName)){
			return null;
		}
		paramName=paramName.toLowerCase();
		
		if(clazz==String.class){
			return processString(paramName);
		}
		
		if(clazz==Integer.class){
			return processInteger(paramName);
		}
		
		if(clazz==Date.class){
			return processDate(paramName);
		}
		
		if(clazz==Long.class){
			return processLong(paramName);
		}
		
		if(clazz==Double.class){
			return processDouble(paramName);
		}
		
		if(clazz==Float.class){
			return processFloat(paramName);
		}
		
		if(clazz==Boolean.class){
			return processBoolean(paramName);
		}
		
		if(clazz==Short.class){
			return processShort(paramName);
		}
		
		if(clazz==Byte.class){
			return processByte(paramName);
		}
		
		if(clazz==Character.class){
			return processCharacter(paramName);
		}
		
		return null;
	}
	
	/**
	 * 处理生成日期类型的数据
	 * @param paramName
	 * @return
	 */
	private static String processDate(String paramName) {
		
		DateTime datetime=new DateTime();
		// 生成一个今天之前很久的日期  这个参数可能是列表查询条件的起始日期
		if(paramName.contains("start") || paramName.contains("begin")){
			datetime.minusMonths(new Random().nextInt(5)+1);
		}else {
			//生成一个今天之后的日期，这个参数可能是列表查询条件的结束日期
			datetime.plusMonths(new Random().nextInt(5)+1);
		}
		
		return datetime.toString("yyyy-MM-dd HH:mm:ss");
	}

	/** String类型默认的返回值 */
	private static final String DEFAULT_STRING="fooooobar";
	
	/**
	 * 内部对生成String类型的处理
	 * 
	 * @param paramName
	 * @return
	 */
	private static String processString(String paramName){
		
		//判断可能是String类型的Date
		if(paramName.contains("time") || paramName.contains("date") || paramName.contains("datetime")){
			return processDate(paramName);
		}
		
		//可能是String类型的Integer
		if(paramName.contains("num") || paramName.contains("number") || paramName.contains("count") || paramName.contains("id")){
			return processInteger(paramName);
		}
		
		return DEFAULT_STRING;
	}
	
	/**
	 * 对Integer类型的参数做处理
	 * 
	 * @param paramName
	 * @return
	 */
	public static String processInteger(String paramName){
		Integer res=new Random().nextInt(Integer.MAX_VALUE);
		return res.toString();
	}
	
	/**
	 * 对Long类型的参数做处理
	 * 
	 * @param paramName
	 * @return
	 */
	public static String processLong(String paramName){
		return processInteger(paramName);
	}
	
	/**
	 * 对Character类型的参数做处理
	 * @param paramName
	 * @return
	 */
	private static String processCharacter(String paramName) {
		Character c=DEFAULT_STRING.charAt(new Random().nextInt(DEFAULT_STRING.length()));
		return c.toString();
	}

	/**
	 * 对Byte类型做处理
	 * @param paramName
	 * @return
	 */
	private static String processByte(String paramName) {
		return new Byte("1").toString();
	}

	/**
	 * 对Short类型的数据做处理
	 * @param paramName
	 * @return
	 */
	private static String processShort(String paramName) {
		return "17";
	}

	/**
	 * 对Boolean类型的数据做处理
	 * @param paramName
	 * @return
	 */
	private static String processBoolean(String paramName) {
		Boolean res=new Random().nextBoolean();
		return res.toString();
	}

	/**
	 * 对Float类型的数据做处理
	 * @param paramName
	 * @return
	 */
	private static String processFloat(String paramName) {
		Float res=new Random().nextFloat();
		return res.toString();
	}

	/**
	 * 对Double类型的数据做处理
	 * @param paramName
	 * @return
	 */
	private static String processDouble(String paramName) {
		Double res=new Random().nextDouble();
		return res.toString();
	}

}
