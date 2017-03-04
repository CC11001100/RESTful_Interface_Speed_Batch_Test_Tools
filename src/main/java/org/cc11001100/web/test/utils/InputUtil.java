package org.cc11001100.web.test.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * 
 * 用来处理输入数据的工具类
 * 
 * 
 * @author chenjc20326
 *
 */
@Component
public class InputUtil {
	
	private static Logger logger=Logger.getLogger(InputUtil.class);

	/**
	 * 从一大串文件中读入url
	 * 
	 * @param files
	 * @return
	 */
	public static Set<String> readUrlFromFiles(File ...files){
		List<File> recursionFile=new ArrayList<>();
		for(File f:files){
			recursionFile.addAll(recursionListDirectory(f));
		}
		
		Set<String> urls=new HashSet<>();
		for(File file:recursionFile){
			try {
				// 改为line by line 
				@SuppressWarnings("unchecked")
				List<String> list=FileUtils.readLines(file, "UTF-8");
				for(String s:list){
					if(!StringUtils.isEmpty(s) && !isComment(s)){
						urls.add(s);
					}
				}
			} catch (IOException e) {
//				e.printStackTrace();
				// 请不要扩大try catch的范围，以免一个文件的失败影响到整个程序
				StringBuilder mesg=new StringBuilder().append("Read file [").append(file.getName()).append("] occur IOException!");
				logger.error(mesg);
			}
		}
		return urls;
	}
	
	/**
	 * 从一大串文件中读入url
	 * 
	 * @param files
	 * @return
	 */
	public static Set<String> readUrlFromFiles(String ...filesPath){
		File[] files=new File[filesPath.length];
		for(int i=0;i<filesPath.length;i++){
			files[i]=new File(filesPath[i]);
		}
		return readUrlFromFiles(files);
	}
	
	/**
	 * 从单个文件中读取
	 * 
	 * @param file
	 * @return
	 */
	public static Set<String> readUrlFromFile(File file){
		return readUrlFromFiles(file);
	}
	
	/**
	 * 从单个文件中读取
	 * 
	 * @param filePath
	 * @return
	 */
	public static Set<String> readUrlFromFile(String filePath){
		return readUrlFromFiles(new File(filePath));
	}
	
	/**
	 * 读取url时候指定主机名和端口
	 * 
	 * @param host
	 * @param post
	 * @param files
	 * @return
	 */
	public static Set<String> readUrlFromFiles(String host,Integer post, File ...files){
		Set<String> urls=readUrlFromFiles(files);
		Set<String> newUrls=new HashSet<>();
		for(String url : urls){
			String u=new StringBuilder().append(host).append(":").append(post).append(url).toString();
			newUrls.add(u);
		}
		return newUrls;
	}
	
	/**
	 * 以此子串开始的行是注释行
	 */
	private static final String COMMENT_PREFIX="#";
	
	/**
	 * 这一行是不是注释呢
	 * 
	 * @param lineContent
	 * @return
	 */
	private static boolean isComment(String lineContent){
		if(!StringUtils.isEmpty(lineContent)){
			return lineContent.trim().startsWith(COMMENT_PREFIX);
		}
		return false;
	}
	
	/**
	 * 递归列出目录中的文件
	 * 
	 * @param dir
	 * @return
	 */
	private static List<File> recursionListDirectory(File dir){
		
		List<File> res=new ArrayList<>();
		
		if(!dir.isDirectory()){
			res.add(dir);
			return res;
		}
		
		File[] files=dir.listFiles();
		for(File f:files){
			if(f.isDirectory()){
				res.addAll(recursionListDirectory(f));
			}else{
				res.add(f);
			}
		}
		
		return res;
	}
	
	/**
	 * 将Set中的每一行解析为一个参数
	 * 
	 * @param lines
	 * @return
	 */
	public static Map<String, String> linesToParamMap(Set<String> lines){
		Map<String,String> params=new HashMap<>();
		for(String s:lines){
			if(StringUtils.isEmpty(s)){
				continue;
			}
			String[] ss=s.split("=");
			if(ss.length!=2){
				continue;
			}
			params.put(ss[0], ss[1]);
			
			StringBuilder mesg=new StringBuilder();
			mesg.append("Parse global param [").append(ss[0]).append(",").append(ss[1]).append("] done.");
			logger.info(mesg.toString());
		}
		return params;
	}
	
}
