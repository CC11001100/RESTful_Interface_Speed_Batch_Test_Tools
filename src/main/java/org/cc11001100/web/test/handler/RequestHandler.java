package org.cc11001100.web.test.handler;

import java.util.Map;

import org.cc11001100.web.test.domain.ResponseDO;

/**
 * 
 * 要对一次请求负责
 * 
 * @author chenjc20326
 *
 */
public interface RequestHandler {

	/**
	 * 用来处理一次请求
	 * 
	 * @param url
	 * @param params
	 * @return
	 */
	ResponseDO handle(String url, Map<String,String> params);
	
}
