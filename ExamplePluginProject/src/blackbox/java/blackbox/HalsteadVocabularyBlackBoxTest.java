package blackbox;
import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.api.CheckstyleException;

import plugin.HalsteadVocabularyCheck;
public class HalsteadVocabularyBlackBoxTest {
	
	String filePath = System.getProperty("user.dir") + "\\src\\blackboxtests\\java\\plugin\\HalsteadVocabulary\\";
	
	@Test
	public void test21() throws IOException, CheckstyleException {
		HalsteadVocabularyCheck check = new HalsteadVocabularyCheck(); 
		TestEngine test = new TestEngine(filePath , "test21.java", check); 
		test.RunTestCase(); 
		assertEquals(7, check.calcHalsteadVocabulary()); 
	}
	
	@Test
	public void test22() throws IOException, CheckstyleException {
		HalsteadVocabularyCheck check = new HalsteadVocabularyCheck(); 
		TestEngine test = new TestEngine(filePath , "test22.java", check); 
		test.RunTestCase(); 
		assertEquals(10, check.calcHalsteadVocabulary()); 
	}
	@Test
	public void test23() throws IOException, CheckstyleException {
		HalsteadVocabularyCheck check = new HalsteadVocabularyCheck(); 
		TestEngine test = new TestEngine(filePath , "test23.java", check); 
		test.RunTestCase(); 
		assertEquals(0, check.calcHalsteadVocabulary()); 
	}

}
