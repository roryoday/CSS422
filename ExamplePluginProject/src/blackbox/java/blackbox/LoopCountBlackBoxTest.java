package blackbox;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.api.CheckstyleException;

import plugin.LoopCountCheck;

public class LoopCountBlackBoxTest {
	String filePath = System.getProperty("user.dir") + "\\src\\blackboxtests\\java\\plugin\\LoopCount\\";
	
	@Test
	public void test81() throws IOException, CheckstyleException {
		LoopCountCheck check = new LoopCountCheck(); 
		TestEngine test = new TestEngine(filePath , "test81.java", check); 
		test.RunTestCase(); 
		assertEquals(1, check.getLoopCount()); 
	}
	
	@Test
	public void test82() throws IOException, CheckstyleException {
		LoopCountCheck check = new LoopCountCheck(); 
		TestEngine test = new TestEngine(filePath , "test82.java", check); 
		test.RunTestCase(); 
		assertEquals(3, check.getLoopCount()); 
	}
	
	@Test
	public void test83() throws IOException, CheckstyleException {
		LoopCountCheck check = new LoopCountCheck(); 
		TestEngine test = new TestEngine(filePath , "test83.java", check); 
		test.RunTestCase(); 
		assertEquals(0, check.getLoopCount()); 
	}
	

}
