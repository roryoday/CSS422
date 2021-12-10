package blackbox;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.api.CheckstyleException;

import plugin.OperandCountCheck;

public class OperandCountBlackBoxTest {
	String filePath = System.getProperty("user.dir") + "\\src\\blackboxtests\\java\\plugin\\OperandCount\\";
	
	@Test
	public void test91() throws IOException, CheckstyleException {
		OperandCountCheck check = new OperandCountCheck(); 
		TestEngine test = new TestEngine(filePath , "test91.java", check); 
		test.RunTestCase(); 
		assertEquals(6, check.getOperandCount()); 
	}
	
	@Test
	public void test92() throws IOException, CheckstyleException {
		OperandCountCheck check = new OperandCountCheck(); 
		TestEngine test = new TestEngine(filePath , "test92.java", check); 
		test.RunTestCase(); 
		assertEquals(12, check.getOperandCount()); 
	}
	
	@Test
	public void test93() throws IOException, CheckstyleException {
		OperandCountCheck check = new OperandCountCheck(); 
		TestEngine test = new TestEngine(filePath , "test93.java", check); 
		test.RunTestCase(); 
		assertEquals(0, check.getOperandCount()); 
	}
}
