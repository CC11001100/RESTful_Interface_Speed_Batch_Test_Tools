package org.cc11001100.web.test.domain;

import java.util.Map;

/**
 * 表示响应的DO
 * 
 * 
 * @author chenjc20326
 *
 */
public class ResponseDO implements Comparable<ResponseDO> {

	/** 本次请求花费时间，单位为毫秒 */
	private Long spend;

	/** 本次请求的url */
	private String url;

	/** 请求返回的状态码 */
	private Integer status;

	/** 请求返回内容 */
	private String responseContent;

	private Map<String, String> params;

	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}

	public Long getSpend() {
		return spend;
	}

	public void setSpend(Long spend) {
		this.spend = spend;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getResponseContent() {
		return responseContent;
	}

	public void setResponseContent(String responseContent) {
		this.responseContent = responseContent;
	}

	public ResponseDO() {
		// TODO Auto-generated constructor stub
	}

	public ResponseDO(String url) {
		super();
		this.url = url;
	}

	/**
	 * 按响应时间降序排列
	 */
	@Override
	public int compareTo(ResponseDO o) {

		if (o.spend != this.spend) {
			return this.spend > o.spend ? -1 : 1;
		} else {
			return 0;
		}

	}

}
