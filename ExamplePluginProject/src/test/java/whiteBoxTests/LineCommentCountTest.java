package test.java.whiteBoxTests;


import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import plugin.LineCommentCountCheck;



public class LineCommentCountTest {

	int[] expectedTokens = {
			TokenTypes.SINGLE_LINE_COMMENT, 
			TokenTypes.BLOCK_COMMENT_BEGIN 
			};

	@Test
	public void GetDefaultTokensTest() {
		LineCommentCountCheck test = new LineCommentCountCheck();
		assertArrayEquals(expectedTokens, test.getDefaultTokens());
	}

	@Test
	public void GetAcceptableTokensTest() {
		LineCommentCountCheck test = new LineCommentCountCheck();
		assertArrayEquals(expectedTokens, test.getAcceptableTokens());
	}

	@Test
	public void GetRequiredTokensTest() {
		LineCommentCountCheck test = new LineCommentCountCheck();
		assertArrayEquals(expectedTokens, test.getRequiredTokens());
	}

	@Test 
	public void NoCommentTest() { 
		LineCommentCountCheck test = new LineCommentCountCheck();
		DetailAST ast = mock(DetailAST.class);
		assertEquals(0, test.getCount());
	}
	
	@Test 
	public void SingleLineCommentTest() {
		LineCommentCountCheck test = new LineCommentCountCheck();
		DetailAST ast = mock(DetailAST.class);
		doReturn(TokenTypes.SINGLE_LINE_COMMENT).when(ast).getType();
		test.visitToken(ast);
		assertEquals(1, test.getCount());
	}
	@Test
	public void BlockCountTest() { 
		
		LineCommentCountCheck test = new LineCommentCountCheck();
		DetailAST blockBegin = mock(DetailAST.class);
		DetailAST blockEnd = mock(DetailAST.class);
		test.beginTree(blockBegin); 
		doReturn(TokenTypes.BLOCK_COMMENT_BEGIN).when(blockBegin).getType(); 
		doReturn(TokenTypes.BLOCK_COMMENT_END).when(blockEnd).getType();
		doReturn(blockEnd).when(blockBegin).findFirstToken(TokenTypes.BLOCK_COMMENT_END);
		doReturn(1).when(blockBegin).getLineNo();
		doReturn(3).when(blockEnd).getLineNo();
		test.visitToken(blockBegin);
		assertEquals(3, test.getCount());
	}
	
	@Test
	public void MultiCountTest() {
		
		LineCommentCountCheck test = new LineCommentCountCheck();
		
		DetailAST blockBegin = mock(DetailAST.class);
		DetailAST blockEnd = mock(DetailAST.class); 
		test.beginTree(blockBegin); 		
		doReturn(TokenTypes.SINGLE_LINE_COMMENT).when(blockBegin).getType();
		for (int i = 0; i < 10; i++) { 
			test.visitToken(blockBegin);
		}
		doReturn(TokenTypes.BLOCK_COMMENT_BEGIN).when(blockBegin).getType(); // block begin
		doReturn(TokenTypes.BLOCK_COMMENT_END).when(blockEnd).getType(); // block end 
		doReturn(blockEnd).when(blockBegin).findFirstToken(TokenTypes.BLOCK_COMMENT_END);
		doReturn(1).when(blockBegin).getLineNo();
		doReturn(3).when(blockEnd).getLineNo();
		
		//5 comments size 3, 3*5=15
		for (int i = 0; i < 5; i++) { 
			test.visitToken(blockBegin);
		}

		assertEquals(15, test.getCount());
	}

	@Test // Test for required coment function.
	public void NoCommentCountTest() {
		LineCommentCountCheck test = mock(LineCommentCountCheck.class);
		DetailAST ast = mock(DetailAST.class);
		test.beginTree(ast); 
		doReturn(false).when(test).isCommentNodesRequired();
		assertEquals(false,test.isCommentNodesRequired());
	}
	

	@Test
	public void FinishTreeTest() {
		
		LineCommentCountCheck test = spy(LineCommentCountCheck.class);
		DetailAST ast = mock(DetailAST.class);
	    final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	    System.setOut(new PrintStream(outputStream));
		test.beginTree(ast); 
		test.finishTree(ast);
		doThrow(NullPointerException.class).when(test).finishTree(null);
		assertEquals("Error: Unable to walk through tree",outputStream.toString().trim());
		
	}
	
	
}
