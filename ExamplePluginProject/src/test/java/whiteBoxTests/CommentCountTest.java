package test.java.whiteBoxTests;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import plugin.CommentCountCheck;
import plugin.HalsteadEffortCheck;

public class CommentCountTest {
	
	int[] commentToken = { TokenTypes.COMMENT_CONTENT };


	@Test
	public void BeginTreeTest() {
		CommentCountCheck test = new CommentCountCheck();
		DetailAST ast = mock(DetailAST.class);
		test.beginTree(ast);
		assertEquals(0, test.getCount());
	}
	
	@Test
	public void FinishTreeTest() {
		CommentCountCheck test = spy(CommentCountCheck.class);
		DetailAST ast = mock(DetailAST.class);
		final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	    System.setOut(new PrintStream(outputStream));
		test.finishTree(ast);
		doThrow(NullPointerException.class).when(test).finishTree(null);
		assertEquals("Error: Unable to walk through tree",outputStream.toString().trim());
	}
	@Test
	public void GetRequiredTokensTest() {
		CommentCountCheck requiredTokensTest = new CommentCountCheck();
		assertArrayEquals(commentToken, requiredTokensTest.getRequiredTokens());
	}
	
	@Test
	public void GetAcceptableTokensTest() {
		CommentCountCheck test = new CommentCountCheck();
		assertArrayEquals(commentToken, test.getAcceptableTokens());
	}
	
	@Test
	public void defaultTokensTest() {
		
		CommentCountCheck test = new CommentCountCheck();
		assertArrayEquals(commentToken, test.getDefaultTokens());
	}
	
	@Test
	public void VisitTokenTest() {
		CommentCountCheck test = new CommentCountCheck();
		DetailAST ast = mock(DetailAST.class);
		test.visitToken(ast);
		test.visitToken(ast);
		test.visitToken(ast);
		test.visitToken(ast);
		test.visitToken(ast);
		assertEquals(5, test.getCount());
	}
	
	@Test
	public void NoCommentTest() {
		CommentCountCheck test = spy(new CommentCountCheck());
		DetailAST ast = mock(DetailAST.class);
		doReturn(false).when(test).isCommentNodesRequired();
		assertEquals(false, test.isCommentNodesRequired());
		assertEquals(0, test.getCount());
	}
	
	
	@Test
	public void SingleCommentTest() {
		CommentCountCheck test = new CommentCountCheck();
		DetailAST ast = mock(DetailAST.class);
		doReturn(TokenTypes.SINGLE_LINE_COMMENT).when(ast).getType();
		test.visitToken(ast);
		assertEquals(1, test.getCount());
	}
	
	@Test
	public void BlockCommentTest() {
		CommentCountCheck test = new CommentCountCheck();
		DetailAST ast = mock(DetailAST.class);
		doReturn(TokenTypes.BLOCK_COMMENT_BEGIN).when(ast).getType();
		test.visitToken(ast);
		assertEquals(1, test.getCount());	
	}

}
