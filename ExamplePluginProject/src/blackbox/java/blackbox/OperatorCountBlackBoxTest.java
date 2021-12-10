package blackbox;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.api.CheckstyleException;

import plugin.OperatorCountCheck;

public class OperatorCountBlackBoxTest {
	String filePath = System.getProperty("user.dir") + "\\src\\blackboxtests\\java\\plugin\\OperatorCount\\";
	
	@Test
	public void test101() throws IOException, CheckstyleException {
		OperatorCountCheck check = new OperatorCountCheck(); 
		TestEngine test = new TestEngine(filePath , "test101.java", check); 
		test.RunTestCase(); 
		assertEquals(7, check.getOperatorCount()); 
	}
	
	@Test
	public void test102() throws IOException, CheckstyleException {
		OperatorCountCheck check = new OperatorCountCheck(); 
		TestEngine test = new TestEngine(filePath , "test102.java", check); 
		test.RunTestCase(); 
		assertEquals(5, check.getOperatorCount()); 
	}
	
	@Test
	public void test103() throws IOException, CheckstyleException {
		OperatorCountCheck check = new OperatorCountCheck(); 
		TestEngine test = new TestEngine(filePath , "test103.java", check); 
		test.RunTestCase(); 
		assertEquals(0, check.getOperatorCount()); 
	}
	
}
