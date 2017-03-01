package org.cc11001100.web.test.utils;

import static org.junit.Assert.*;

import java.io.File;
import java.util.Set;

import org.cc11001100.web.test.utils.InputUtil;
import org.junit.Ignore;
import org.junit.Test;

/**
 * ≤‚ ‘ ‰»Î¿‡
 * 
 * @author chenjc20326
 *
 */
public class TestInputUtil {
	
	@Ignore
	@Test
	public void testReadUrlFromFile() {
		
		Set<String> urls=InputUtil.readUrlFromFiles(new File("D:/test/handlerMapping.txt"));
		
		for(String s:urls){
			System.out.println(s);
		}
		System.out.println(urls.size());
		
	}
	
	@Test
	public void test_002(){
		
//		Class<String> clazz=String.class;
		
		
	}

}
