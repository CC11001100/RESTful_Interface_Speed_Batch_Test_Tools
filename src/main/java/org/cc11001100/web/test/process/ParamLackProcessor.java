package org.cc11001100.web.test.process;

import org.cc11001100.web.test.domain.ParamPair;
import org.cc11001100.web.test.domain.ResponseDO;

/**
 * 发起请求时参数缺失处理器，扩展这个接口支持更多服务器
 * 
 * @author chenjc20326
 *
 */
public interface ParamLackProcessor {
	
	/**
	 * 我是否能够处理这个东西呢
	 * 
	 * @param responseDO
	 * @return
	 */
	boolean canProcess(ResponseDO responseDO);
	
	/**
	 * 将上一次请求失败的返回传进来，然后进行处理得到缺失的参数
	 * 
	 * @param responseDO 上一次进行请求得到的结果
	 * @return 缺失的参数名字
	 */
	ParamPair process(ResponseDO responseDO);
	
}
