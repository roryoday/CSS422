package whitebox;


import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import plugin.CommentCountCheck;
import plugin.ExpressionCountCheck;


public class ExpressionCountTest {
	
	
	

	@Test
	public void DefaultTokensTest() {
		ExpressionCountCheck expressionCheck = new ExpressionCountCheck();
		assertArrayEquals(new int[] { TokenTypes.CLASS_DEF, TokenTypes.INTERFACE_DEF }, expressionCheck.getDefaultTokens());
	}

	@Test
	public void AcceptableTokensTest() {
		ExpressionCountCheck expressionCheck = new ExpressionCountCheck();
		assertArrayEquals(new int[] { TokenTypes.CLASS_DEF, TokenTypes.INTERFACE_DEF }, expressionCheck.getAcceptableTokens());
	}

	@Test
	public void RequiredTokensTest() {
		ExpressionCountCheck expressionCheck = new ExpressionCountCheck();
		assertArrayEquals(new int[0], expressionCheck.getRequiredTokens());
	}

	@Test
	public void SingleExpressionTest() {
		ExpressionCountCheck expressionCheck = new ExpressionCountCheck();
		DetailAST ast = mock(DetailAST.class);
		expressionCheck.beginTree(ast);
		doReturn(TokenTypes.EXPR).when(ast).getType();
		expressionCheck.visitToken(ast);
		assertEquals(0, expressionCheck.getCount());
	}
	
	@Test
	public void NestedExpressionTest() {
		ExpressionCountCheck expressionCheck = new ExpressionCountCheck();
		DetailAST ast = mock(DetailAST.class);
		doReturn(TokenTypes.EXPR).when(ast).getType();
		doReturn(1).when(ast).getChildCount();
		doReturn(1).when(ast).getChildCount(TokenTypes.EXPR);
		for (int i = 0; i < 10; i++) { 
			expressionCheck.visitToken(ast);  // 10 
			
			for (int J = 0; J < 10; J++) { 
				expressionCheck.visitToken(ast); // 100
			}
		}

		assertEquals(110, expressionCheck.getCount());
	}
	
	@Test
	public void NoExpressionTest() {
		ExpressionCountCheck expressionCheck = new ExpressionCountCheck();
		DetailAST ast = mock(DetailAST.class);
		expressionCheck.beginTree(ast);
		doReturn(0).when(ast).getChildCount();
		assertEquals(0, expressionCheck.getCount());
	}

	@Test
	public void ExpressionTest() {
		
		ExpressionCountCheck test = spy(ExpressionCountCheck.class);
		DetailAST ast = mock(DetailAST.class);
	    final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	    System.setOut(new PrintStream(outputStream));
		test.finishTree(ast);	
		doThrow(NullPointerException.class).when(test).finishTree(null);
		assertEquals("Error: Unable to walk through tree",outputStream.toString().trim());
		
	}
	
	@Test 
	public void BeginTreeTest() {
		ExpressionCountCheck expressionCheck = new ExpressionCountCheck();
		DetailAST ast = mock(DetailAST.class);
		expressionCheck.beginTree(ast);
		doReturn(0).when(ast).getChildCount();
		assertEquals(0, expressionCheck.getCount());
	}
	
	@Test
	public void FinishTreeTest() {
		ExpressionCountCheck test = spy(ExpressionCountCheck.class);
		DetailAST ast = mock(DetailAST.class);
		final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	    System.setOut(new PrintStream(outputStream));
		test.finishTree(ast);
		doThrow(NullPointerException.class).when(test).finishTree(null);
		assertEquals("Error: Unable to walk through tree",outputStream.toString().trim());
	}
}
