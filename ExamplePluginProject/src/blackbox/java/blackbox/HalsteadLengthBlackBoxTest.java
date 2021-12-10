package blackbox;
import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.api.CheckstyleException;

import plugin.HalsteadLengthCheck;
public class HalsteadLengthBlackBoxTest {
	
	String filePath = System.getProperty("user.dir") + "\\src\\blackboxtests\\java\\plugin\\HalsteadLength\\";
	
	@Test
	public void test11() throws IOException, CheckstyleException {
		HalsteadLengthCheck check = new HalsteadLengthCheck(); 
		TestEngine test = new TestEngine(filePath , "test11.java", check); 
		test.RunTestCase(); 
		assertEquals(14, check.getOperandCount()); 
	}
	
	@Test
	public void test12() throws IOException, CheckstyleException {
		HalsteadLengthCheck check = new HalsteadLengthCheck(); 
		TestEngine test = new TestEngine(filePath , "test12.java", check); 
		test.RunTestCase(); 
		assertEquals(0, check.getOperandCount()); 
	}
}
