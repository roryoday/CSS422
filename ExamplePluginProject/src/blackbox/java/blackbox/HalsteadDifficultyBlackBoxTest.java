package blackbox;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Ignore;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.api.CheckstyleException;

import plugin.HalsteadDifficultyCheck;

public class HalsteadDifficultyBlackBoxTest {
	String filePath = System.getProperty("user.dir") + "\\src\\blackboxtests\\java\\plugin\\HalsteadDifficulty\\";
	
	@Test
	public void test51() throws IOException, CheckstyleException {
		HalsteadDifficultyCheck check = new HalsteadDifficultyCheck(); 
		TestEngine test = new TestEngine(filePath , "test51.java", check); 
		test.RunTestCase(); 
		assertEquals(0.5, check.CalcHalsteadDifficulty(),.01); 
	}
	
	@Test
	public void test52() throws IOException, CheckstyleException {
		HalsteadDifficultyCheck check = new HalsteadDifficultyCheck(); 
		TestEngine test = new TestEngine(filePath , "test52.java", check); 
		test.RunTestCase(); 
		assertEquals(7.87, check.CalcHalsteadDifficulty(),.01); 
	}
	@Test
	public void test53() throws IOException, CheckstyleException {
		HalsteadDifficultyCheck check = new HalsteadDifficultyCheck(); 
		TestEngine test = new TestEngine(filePath , "test53.java", check); 
		test.RunTestCase(); 
		boolean result = Double.isNaN( check.CalcHalsteadDifficulty());
		assertEquals(result,true); 
	}
}
