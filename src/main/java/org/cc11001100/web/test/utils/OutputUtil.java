package org.cc11001100.web.test.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.cc11001100.web.test.domain.ResponseDO;

/**
 * 
 * 将处理结果保存的工具类
 * 
 * @author chenjc20326
 *
 */
public abstract class OutputUtil {

	public static void process(List<ResponseDO> list){

		List<ResponseDO> res=new ArrayList<>();
		
		for(ResponseDO responseDO : list){
			
			if(responseDO.getStatus()!=400){
				
//				JsonDO jsonDO=(JsonDO) JSON.parse(responseDO.getResponseContent());
				
				res.add(responseDO);
//				System.err.println("---------------------------------------------------");
//				System.err.println(responseDO.getResponseContent());
//				System.out.println(responseDO.getStatus());
			}
			
		}
		
		Collections.sort(res);
		
		writeToFile(res,new File("d:/test/不能正常访问的接口_其它状态码.txt"));
	}
	
	public static void writeToFile(List<ResponseDO> list, File file){
		StringBuilder sb=new StringBuilder();
		for(ResponseDO responseDO : list){
			sb.append(responseDO.getSpend()).append(" ").append(responseDO.getUrl()).append("\r\n");
		}
		try {
			FileUtils.writeStringToFile(file, sb.toString(), "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
