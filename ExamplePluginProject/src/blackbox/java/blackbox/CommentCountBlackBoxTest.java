package blackbox;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.api.CheckstyleException;

import plugin.CommentCountCheck;

public class CommentCountBlackBoxTest {
	String filePath = System.getProperty("user.dir") + "\\src\\blackboxtests\\java\\plugin\\CommentCount\\";
	
	@Test
	public void test61() throws IOException, CheckstyleException {
		CommentCountCheck check = new CommentCountCheck(); 
		TestEngine test = new TestEngine(filePath , "test61.java", check); 
		test.RunTestCase(); 
		assertEquals(1, check.getCount()); 
	}
	
	@Test
	public void test62() throws IOException, CheckstyleException {
		CommentCountCheck check = new CommentCountCheck(); 
		TestEngine test = new TestEngine(filePath , "test62.java", check); 
		test.RunTestCase(); 
		assertEquals(1, check.getCount()); 
	}
	
	@Test
	public void test63() throws IOException, CheckstyleException {
		CommentCountCheck check = new CommentCountCheck(); 
		TestEngine test = new TestEngine(filePath , "test63.java", check); 
		test.RunTestCase(); 
		assertEquals(0, check.getCount()); 
	}
	
	@Test
	public void test64() throws IOException, CheckstyleException {
		CommentCountCheck check = new CommentCountCheck(); 
		TestEngine test = new TestEngine(filePath , "test64.java", check); 
		test.RunTestCase(); 
		assertEquals(1, check.getCount()); 
	}
}
