package blackbox;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.api.CheckstyleException;

import plugin.ExpressionCountCheck;

public class ExpressionCountBlackBoxTest {
	String filePath = System.getProperty("user.dir") + "\\src\\blackboxtests\\java\\plugin\\ExpressionCount\\";
	
	@Test
	public void test111() throws IOException, CheckstyleException {
		ExpressionCountCheck check = new ExpressionCountCheck(); 
		TestEngine test = new TestEngine(filePath , "test111.java", check); 
		test.RunTestCase(); 
		assertEquals(1, check.getCount()); 
	}
	
	@Test
	public void test112() throws IOException, CheckstyleException {
		ExpressionCountCheck check = new ExpressionCountCheck(); 
		TestEngine test = new TestEngine(filePath , "test112.java", check); 
		test.RunTestCase(); 
		assertEquals(0, check.getCount()); 
	}
	
	@Test
	public void test113() throws IOException, CheckstyleException {
		ExpressionCountCheck check = new ExpressionCountCheck(); 
		TestEngine test = new TestEngine(filePath , "test113.java", check); 
		test.RunTestCase(); 
		assertEquals(4, check.getCount()); 
	}
	
	public void test114() throws IOException, CheckstyleException {
		ExpressionCountCheck check = new ExpressionCountCheck(); 
		TestEngine test = new TestEngine(filePath , "test114.java", check); 
		test.RunTestCase(); 
		assertEquals(0, check.getCount()); 
	}
}
