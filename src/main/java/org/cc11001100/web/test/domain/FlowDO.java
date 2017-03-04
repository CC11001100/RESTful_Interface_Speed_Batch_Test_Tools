package org.cc11001100.web.test.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 进行一次完整的需要进行的设置
 * 
 * @author chenjc20326
 *
 */
public class FlowDO {

	/** 要请求的url链接  */
	private Set<String> urls;
	
	/** 全局参数 */
	private Map<String, String> globalParams;
	
	/** 结果输出位置 */
	private String resultSavePath;

	public FlowDO() {
		//可选项，为了避免空指针就初始化吧
		globalParams=new HashMap<>();
	}
	
	public Set<String> getUrls() {
		return urls;
	}

	public void setUrls(Set<String> urls) {
		this.urls = urls;
	}

	public String getResultSavePath() {
		return resultSavePath;
	}

	public void setResultSavePath(String resultSavePath) {
		this.resultSavePath = resultSavePath;
	}

	public Map<String, String> getGlobalParams() {
		return globalParams;
	}

	public void setGlobalParams(Map<String, String> globalParams) {
		this.globalParams = globalParams;
	}
	
}
