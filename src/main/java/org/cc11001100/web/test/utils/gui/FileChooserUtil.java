package org.cc11001100.web.test.utils.gui;

import java.io.File;

import javax.swing.JFileChooser;

/**
 * 
 * 文件选择工具类，用来进行文件选择的
 * 
 * @author chenjc20326
 *
 */
public class FileChooserUtil {

	/**
	 * 选择文件的目的
	 * 
	 * @author chenjc20326
	 *
	 */
	public enum CHOOSE_TYPE{
		
		/** 表示打开url操作 */
		OPEN_URL,
		
		/** 表示打开全局参数操作 */
		OPEN_GLOBAL_PARAM,
		
		/** 表示保存到excel的操作 */
		SAVE_RESULT_EXCEL
		
	}
	
	/**
	 * 打开一个文件选择窗口选择文件
	 * @param chooseType
	 * @return
	 */
	public static File open(CHOOSE_TYPE chooseType){
		
		JFileChooser jFileChooser = new JFileChooser(); 
		
		// 打开url文件
		if(chooseType==CHOOSE_TYPE.OPEN_URL || chooseType==CHOOSE_TYPE.OPEN_GLOBAL_PARAM){
			jFileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);  
			jFileChooser.setCurrentDirectory(new File("d:/test/test/"));
			jFileChooser.showDialog(null,null);  
		}else if(chooseType==CHOOSE_TYPE.SAVE_RESULT_EXCEL){
			jFileChooser.setFileSelectionMode(JFileChooser.SAVE_DIALOG);
			jFileChooser.setCurrentDirectory(new File("d:/test/test/"));
			jFileChooser.showSaveDialog(null);
		}

		return jFileChooser.getSelectedFile();  
	}
	
}
