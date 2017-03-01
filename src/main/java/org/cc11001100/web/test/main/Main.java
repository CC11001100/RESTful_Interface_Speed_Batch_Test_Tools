package org.cc11001100.web.test.main;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.cc11001100.web.test.domain.ResponseDO;
import org.cc11001100.web.test.utils.InputUtil;
import org.cc11001100.web.test.utils.UrlScheduler;
import org.cc11001100.web.test.utils.export.ExportUtils;

/**
 * 
 * 程序入口
 * 
 * 
 * @author chenjc20326
 *
 */
public class Main {

	public static void main(String ...args) {
		
//		Set<String> urls=InputUtil.readUrlFromFiles("http://localhost",8080,new File("D:/test/不能正常访问的接口.txt"));
		Set<String> urls=InputUtil.readUrlFromFiles("http://localhost",8080,new File("D:/test/handlerMapping_人力招募接口_只单纯取映射url_共437个.txt"));
//		Set<String> urls=InputUtil.readUrlFromFiles(new File("D:/test/400.txt"));
		
		Map<String,String> params=new HashMap<String,String>();
		params.put("token", "09fd7e9c472e44bfae5d2e69f2cc9ff4");
		
		List<ResponseDO> list=UrlScheduler.stupidBlockQueueScheduler(urls, params);
		
		Collections.sort(list);
		
//		OutputUtil.process(list);
		
		ExportUtils.export2Excel(list, "d:/test/aaa.xls");
		
//		for(ResponseDO o:list){
//			System.out.printf("%sms %d %s \n",o.getSpend(), o.getStatus(), o.getUrl());
//		}
		
	}
	
}
