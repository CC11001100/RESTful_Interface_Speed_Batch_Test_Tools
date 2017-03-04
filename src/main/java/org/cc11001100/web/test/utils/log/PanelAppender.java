package org.cc11001100.web.test.utils.log;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;

/**
 * 
 * 自定义的log4j输出类，用于将日志信息输出到界面上供用户看
 * 
 * @author chenjc20326
 *
 */
public class PanelAppender extends AppenderSkeleton {

	/** 当前控制台显示的信息，直接保存在内存中，一定要记得清理啊不然就囧了。。。  */
	private StringBuffer mesgBuffer=new StringBuffer(30*100);
	
	/**
	 * 当前的行数
	 */
	private Integer currentLineCount=0;

	/**
	 * 设定控制台上可以显示的日志行数，当到达最大行数时必须释放掉部分之前的部分以避免OOM
	 */
	private final Integer MAX_LINE_COUNT=1000;
	
	@Override
	public void close() {
		if(this.closed){
			return ;
		}
		this.closed=true;
	}

	@Override
	public boolean requiresLayout() {
		return false;
	}

	@Override
	protected void append(LoggingEvent event) {
		
		//存储此条消息
		String currentMesg=event.getRenderedMessage();
		System.err.println(currentMesg);
		checkMesgBuffer(currentMesg);
		
		//从MainFrame中取出面板更新其内容即可
//		MainFrame.logDisplayTextArea.setText(mesgBuffer.toString());
		
	}
	
	/**
	 * 存储此条消息
	 * @param mesg
	 */
	private void checkMesgBuffer(String mesg){
		currentLineCount++;
		mesgBuffer.append(mesg);
		if(currentLineCount>=MAX_LINE_COUNT){
			int firstIndex=mesgBuffer.indexOf("\n");
			//我靠这一句好坑
			mesgBuffer=new StringBuffer(mesgBuffer.substring(firstIndex));
		}
	}

}
