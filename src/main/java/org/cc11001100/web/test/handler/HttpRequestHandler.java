package org.cc11001100.web.test.handler;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.cc11001100.web.test.domain.ParamPair;
import org.cc11001100.web.test.domain.ResponseDO;
import org.cc11001100.web.test.process.ParamLackProcessor;
import org.cc11001100.web.test.process.ParamLackProcessor4Jetty;

/**
 * 用来处理http请求的工具类
 * 
 * @author chenjc20326
 *
 */
public class HttpRequestHandler implements RequestHandler {

	private Logger logger=Logger.getLogger(HttpRequestHandler.class);
	
	/** 当请求不是200时的最大尝试次数 */
	private Integer LACK_PARAM_MAX_TRY=100;

	/** 要使用到的一些参数缺失处理器，可以是一个集合，使用的时候会遍历直到找到一个能够处理的来使用  */
	private List<ParamLackProcessor> paramLackProcessors=new ArrayList<>();
	
	public HttpRequestHandler() {
		//装载要使用的参数处理器 
		paramLackProcessors.add(new ParamLackProcessor4Jetty());
	}

	/**
	 * 传入url和参数，发起一次请求，使用post方式
	 * 
	 * @param url
	 * @param params
	 */
	private ResponseDO post(String url, Map<String, String> params) {
		
		CloseableHttpClient httpclient=HttpClients.createDefault();
		HttpPost httppost=new HttpPost(url);
		
		//设置参数
		List<NameValuePair> formParams=new ArrayList<>();
		for(Iterator<Entry<String, String>> iterator=params.entrySet().iterator();iterator.hasNext();){
			Entry<String, String> entry=iterator.next();
			formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}
		
		try {
			UrlEncodedFormEntity uefEntity=new UrlEncodedFormEntity(formParams, "UTF-8");
			httppost.setEntity(uefEntity);
			
			// 缓存去死！！！
			httppost.setHeader("Cache-Control", "no-cache");
			httppost.setHeader("Pragma", "no-cache");
			httppost.setHeader("Cache-Control", "no-store");
			
			//用于收集所需返回数据
			ResponseDO res=new ResponseDO(url);
			
			//请求所使用的参数
			res.setParams(params);
			
			//统计请求花费的时间，因为是单线程会产生阻塞的
			long start=System.currentTimeMillis();
			CloseableHttpResponse response=httpclient.execute(httppost);
			long spend=System.currentTimeMillis()-start;
			res.setSpend(spend);
			
			//收集返回的状态码
			HttpEntity entity=response.getEntity();
			res.setStatus(response.getStatusLine().getStatusCode());
			
			if (entity != null) {
				String responseContent=EntityUtils.toString(entity, "UTF-8");
				res.setResponseContent(responseContent);
			}
			
			// log
			StringBuilder mesg=new StringBuilder();
			mesg.append("URL [").append(url).append("] request done.");
			logger.info(mesg.toString());
			
			return res;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关闭连接,释放资源
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public ResponseDO handle(String url, Map<String, String> params) {
		
		ResponseDO responseDO=null;
		for(int i=0;i<LACK_PARAM_MAX_TRY;i++){
			
			responseDO=post(url, params);
			if(responseDO==null){
				continue;
			}else if(responseDO.getStatus()==HttpStatus.SC_OK){
				return responseDO;
			}else if(!processorLackedParam(responseDO)){
				//调用缺失参数处理器来处理，当所有的都不能解析的时候直接结束掉尝试，反正已经不能搞到了
				break;
			}
			
		}
		
		return responseDO;
	}
	
	/**
	 * 使用缺失参数处理器来解析服务器的返回信息尝试得到一个该有的返回
	 * 
	 * @param responseDO
	 * @return
	 */
	private boolean processorLackedParam(ResponseDO responseDO){
		for(ParamLackProcessor paramLackProcessor : paramLackProcessors){
			if(paramLackProcessor.canProcess(responseDO)){
				ParamPair paramPair=paramLackProcessor.process(responseDO);
				if(paramPair!=null){
					responseDO.getParams().put(paramPair.getName(), paramPair.getValue());
					return true;
				}
			}
		}
		return false;
	}
	
	// 异步去死!!!
//	/**
//	 * 传入url和参数，发起一次请求，使用post方式
//	 * 
//	 * @param url
//	 * @param params
//	 */
//	public ResponseDO post(String url, Map<String, String> params) {
//		
//		System.out.println(Thread.currentThread().getId());
//		
//		AsyncHttpClient asyncHttpClient=new AsyncHttpClient();
//		BoundRequestBuilder boundRequestBuilder=asyncHttpClient.preparePost(url);
//		
//		//设置参数
//		List<Param> formParams=new ArrayList<>();
//		for(Iterator<Entry<String, String>> iterator=params.entrySet().iterator();iterator.hasNext();){
//			Entry<String, String> entry=iterator.next();
//			formParams.add(new Param(entry.getKey(), entry.getValue()));
//		}
//		boundRequestBuilder.setFormParams(formParams);
//		
//		try {
//			// 缓存去死！！！
//			boundRequestBuilder.setHeader("Cache-Control", "no-cache");
//			boundRequestBuilder.setHeader("Pragma", "no-cache");
//			boundRequestBuilder.setHeader("Cache-Control", "no-store");
//			
//			long start=new Date().getTime();
//			ListenableFuture<Response> listenableFuture=boundRequestBuilder.execute();
//			while(!listenableFuture.isDone()){
//				TimeUnit.MILLISECONDS.sleep(1);
//			}
//			long spend=new Date().getTime()-start;
//			
//			Response response=listenableFuture.get();
//			
//			//用于收集所需返回数据
//			ResponseDO res=new ResponseDO(url);
//			res.setSpend(spend);
//			res.setStatus(response.getStatusCode());
//			res.setResponseContent(response.getResponseBody());
//			
//			return res;
//		} catch (ClientProtocolException e) {
//			e.printStackTrace();
//		} catch (UnsupportedEncodingException e1) {
//			e1.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (ExecutionException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return null;
//	}
	
}
