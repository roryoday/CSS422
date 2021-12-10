package blackbox;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.api.CheckstyleException;

import plugin.HalsteadVolumeCheck;

public class HalsteadVolumeBlackBoxTest {
	String filePath = System.getProperty("user.dir") + "\\src\\blackboxtests\\java\\plugin\\HalsteadVolume\\";
	
	@Test
	public void test31() throws IOException, CheckstyleException {
		HalsteadVolumeCheck check = new HalsteadVolumeCheck(); 
		TestEngine test = new TestEngine(filePath , "test31.java", check); 
		test.RunTestCase(); 
		assertEquals(19.65, check.calcHalsteadVolume(),.01); 
	}
	
	@Test
	public void test32() throws IOException, CheckstyleException {
		HalsteadVolumeCheck check = new HalsteadVolumeCheck(); 
		TestEngine test = new TestEngine(filePath , "test32.java", check); 
		test.RunTestCase(); 
		assertEquals(63.11, check.calcHalsteadVolume(),0.01); 
	}
	@Test
	public void test33() throws IOException, CheckstyleException {
		HalsteadVolumeCheck check = new HalsteadVolumeCheck(); 
		TestEngine test = new TestEngine(filePath , "test33.java", check); 
		test.RunTestCase(); 
		boolean result = Double.isNaN( check.calcHalsteadVolume());
		assertEquals(result,true); 
	}
}
