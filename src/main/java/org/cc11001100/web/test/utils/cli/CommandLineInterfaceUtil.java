package org.cc11001100.web.test.utils.cli;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.springframework.stereotype.Component;

/**
 * 
 * 处理命令行输入参数的工具类
 * 
 * @author chenjc20326
 *
 */
@Component
public class CommandLineInterfaceUtil {

	/**
	 * 根据传入的参数进行命令行处理
	 * 
	 * @param args
	 */
	public void process(String[] args) {
		

		
		
	}
	
	private void processArgs(String[] args){
		
		Options options=new Options();
		
		CommandLineParser parser=new DefaultParser();
		CommandLine cmd=parser.parse(options, args);
		
		
	}
	
	/**
	 * 初始化选项
	 * 
	 * @return
	 */
	private Options initOptions(){
		
		Options options=new Options();

		//urls映射 (必选)
		Option urls=Option.builder("u").longOpt("urls").hasArg().argName("urls")
					.desc("Will test url set in a file line by line.").required().build();
		
		//全局参数(可选)
		Option globalParams=Option.builder("g").longOpt("globalParams").hasArg().argName("globalParams")
				.desc("Every http request will use this params.").build();
		
		//输出路径(必选)
		Option output=Option.builder("o").longOpt("output").hasArg().argName("outputPath")
				.desc("Result export to where?").required().build();
		
		//主机名(可选)
		Option host=Option.builder("h").longOpt("host").hasArg().argName("host").desc("Host").build();
		
		//端口(可选)
		Option post=Option.builder("p").longOpt("post").hasArg().argName("post").desc("Post").required().build();
		
		//帮助(可选)
		Option help=Option.builder("h").longOpt("help").desc("Get help.").build();
		
		options.addOption(urls);
		options.addOption(globalParams);
		options.addOption(output);
		options.addOption(host);
		options.addOption(post);
		options.addOption(help);
		
		return options;
	}
	
}
