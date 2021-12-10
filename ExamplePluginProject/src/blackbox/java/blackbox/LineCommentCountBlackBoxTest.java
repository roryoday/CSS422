package blackbox;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.api.CheckstyleException;

import plugin.LineCommentCountCheck;

public class LineCommentCountBlackBoxTest {
	String filePath = System.getProperty("user.dir") + "\\src\\blackboxtests\\java\\plugin\\LineCommentCount\\";
	
	@Test
	public void test71() throws IOException, CheckstyleException {
		LineCommentCountCheck check = new LineCommentCountCheck(); 
		TestEngine test = new TestEngine(filePath , "test71.java", check); 
		test.RunTestCase(); 
		assertEquals(3, check.getCount()); 
	}
	
	@Test
	public void test72() throws IOException, CheckstyleException {
		LineCommentCountCheck check = new LineCommentCountCheck(); 
		TestEngine test = new TestEngine(filePath , "test72.java", check); 
		test.RunTestCase(); 
		assertEquals(0, check.getCount()); 
	}
	
	@Test
	public void test73() throws IOException, CheckstyleException {
		LineCommentCountCheck check = new LineCommentCountCheck(); 
		TestEngine test = new TestEngine(filePath , "test73.java", check); 
		test.RunTestCase(); 
		assertEquals(1, check.getCount()); 
	}
	
	@Test
	public void test74() throws IOException, CheckstyleException {
		LineCommentCountCheck check = new LineCommentCountCheck(); 
		TestEngine test = new TestEngine(filePath , "test74.java", check); 
		test.RunTestCase(); 
		assertEquals(3, check.getCount()); 
	}
}