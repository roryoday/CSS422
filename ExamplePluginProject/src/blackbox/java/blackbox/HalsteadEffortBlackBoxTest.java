package blackbox;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.api.CheckstyleException;

import plugin.HalsteadEffortCheck;

public class HalsteadEffortBlackBoxTest {
	String filePath = System.getProperty("user.dir") + "\\src\\blackboxtests\\java\\plugin\\HalsteadEffort\\";
	
	@Test
	public void test41() throws IOException, CheckstyleException {
		HalsteadEffortCheck check = new HalsteadEffortCheck(); 
		TestEngine test = new TestEngine(filePath , "test41.java", check); 
		test.RunTestCase(); 
		assertEquals(9.83, check.calcHalsteadEffort(),.01); 
	}
	
	@Test
	public void test42() throws IOException, CheckstyleException {
		HalsteadEffortCheck check = new HalsteadEffortCheck(); 
		TestEngine test = new TestEngine(filePath , "test42.java", check); 
		test.RunTestCase(); 
		assertEquals(102.56, check.calcHalsteadEffort(),.01); 
	}
	@Test
	public void test43() throws IOException, CheckstyleException {
		HalsteadEffortCheck check = new HalsteadEffortCheck(); 
		TestEngine test = new TestEngine(filePath , "test43.java", check); 
		test.RunTestCase(); 
		boolean result = Double.isNaN( check.calcHalsteadEffort());
		assertEquals(result,true); 
	}
}
