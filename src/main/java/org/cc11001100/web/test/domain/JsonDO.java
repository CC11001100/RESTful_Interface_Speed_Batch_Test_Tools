package org.cc11001100.web.test.domain;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

//import com.alibaba.fastjson.JSONObject;

/**
 * 返回给客户端json数据对象
 *
 */
public class JsonDO implements Serializable, Cloneable {
	
	/***
	 * 处理成功返回的errorCode值
	 */
	public static final Integer ERRORCODE_SUCCEED=0;
	
	/***
	 * 处理失败返回的errorCode默认值
	 *	
	 * 1.该code已经被泛用为业务异常     commet by wangbo15820 2015-11-18
	 * 
	 */
	public static final Integer ERRORCODE_FAILD=-1;
	
	
	/**
	 * 系统异常code，划定code范围 [1000,2000)
	 * @author wangbo15820
	 *
	 */
	public static interface SysErrorCode{
		public static final Integer DEFAULT = 1000;
	}
	
	/**
	 * 权限异常code，划定code范围 [2000,3000)
	 * @author wangbo15820
	 *
	 */
	public static interface PrivalegeErrorCode{
		public static final Integer UN_LOGGIN=2000;//未登录
		public static final Integer KITED=2001;//被踢下线
		public static final Integer NO_PRIVALEGE=2002;//已登录，无操作权限
		public static final Integer SESSION_TIMEOUT=2003;//会话超时
		public static final Integer UP_SUPPORT=2004;//不支持的请求
		public static final Integer UN_AUTHEN=2005;//未实名认证
		public static final Integer UN_JOIN_ORG=2006;//未加入机构
		public static final Integer NO_M0280=2280;//没有接包权限
	}
	
	/***
	 * 禁止new默认构造对象
	 */
	public JsonDO(){
		
	}
	
	/***
	 * 私有调用构造对象方法,仅限类内调用
	 * @param errorCode
	 * @param errorMsg
	 * @param data
	 */
	public JsonDO(Integer errorCode, String errorMsg, Object data){
		this.errorCode=errorCode;
		this.errorMsg=errorMsg;
		this.data=data;
	}
	
	/***
	 * 异常返回:仅传入errorMsg
	 * errorCode为-1,data为null
	 * @param errorMsg
	 * @return
	 */
	public static JsonDO error(String errorMsg){
		return new JsonDO(JsonDO.ERRORCODE_FAILD,errorMsg,null);
	}
	
	/***
	 * 异常返回:传入errorCode,errorMsg
	 * data为null
	 * @param errorCode
	 * @param errorMsg
	 * @return
	 */
	public static JsonDO error(Integer errorCode,String errorMsg){
		return new JsonDO(errorCode,errorMsg,null);
	}
	
	/***
	 * 正常返回:data
	 * errorCode为0,errorMsg为null
	 * @param data
	 * @return
	 */
	public static JsonDO data(Object data){
		return new JsonDO(JsonDO.ERRORCODE_SUCCEED,null,data);
	}
	
	/***
	 * 正常返回:输入入key,value,key,value... 对象串。
	 * @param objects
	 * @return
	 */
	public static JsonDO keyValueSerial(Object...objects){
		Map<Object,Object> map=new HashMap<Object,Object>();
		int i=1;
		Object key=null;
		Object value=null;
		for(Object oneObject : objects){
			if(i%2==1){ //key
				key=oneObject;
			}else{ //value
				value=oneObject;
			}
			if(i%2==0){
				if(key==null){
					throw new RuntimeException("key不能为空");
				}
				map.put(key, value);
				key=null;
				value=null;
			}
			i++;
		}
		return new JsonDO(JsonDO.ERRORCODE_SUCCEED,null,map);
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1187725213325674108L;

	/***
	 * 0:正常返回,其它都是异常返回
	 */
	private Integer errorCode;
	
	/***
	 * 异常信息
	 */
	private String errorMsg;
	
	/***
	 * 正常返回数据对象
	 */
	private Object data;
	
	public Integer getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
//	@SuppressWarnings("rawtypes")
//	public static void main(String[] args){
//		JsonDO jsonDO=JsonDO.keyValueSerial("a","b","c","d","e","f","g");
//		@SuppressWarnings("unchecked")
//		JSONObject jsonObject=new JSONObject((Map)jsonDO.getData());
//		System.out.println(jsonObject.toJSONString());
//		
//	}
}
