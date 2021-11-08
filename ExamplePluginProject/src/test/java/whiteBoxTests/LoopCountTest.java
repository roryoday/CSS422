package test.java.whiteBoxTests;


import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import plugin.LoopCountCheck;

public class LoopCountTest {

	int[] expectedTokens = { 
			TokenTypes.LITERAL_FOR, 
			TokenTypes.LITERAL_WHILE, 
			TokenTypes.LITERAL_DO 
			};

	
	
	@Test
	public void DefaultTokenTest() {
		LoopCountCheck test = new LoopCountCheck();
		assertArrayEquals(expectedTokens, test.getDefaultTokens());
	}

	@Test
	public void AcceptableTokensTest() {
		LoopCountCheck test = new LoopCountCheck();
		assertArrayEquals(expectedTokens, test.getAcceptableTokens());
	}

	@Test
	public void RequiredTokensTest() {
		LoopCountCheck test = new LoopCountCheck();
		assertArrayEquals(expectedTokens, test.getRequiredTokens());
	}

	@Test
	public void NoLoopsTest() { 
		LoopCountCheck test = new LoopCountCheck();
		DetailAST ast = mock(DetailAST.class);
		when(ast.getType()).thenReturn(TokenTypes.NUM_INT);
		test.visitToken(ast);
		assertEquals(0, test.getLoopCount());
	}

	@Test
	public void ForLoopTest() { 
		LoopCountCheck test = new LoopCountCheck();
		DetailAST ast = mock(DetailAST.class);
		when(ast.getType()).thenReturn(TokenTypes.LITERAL_FOR);
		test.visitToken(ast);
		assertEquals(1, test.getLoopCount());
	}

	@Test
	public void WhileLoopTest() { 
		LoopCountCheck test = new LoopCountCheck();
		DetailAST ast = mock(DetailAST.class);
		doReturn(TokenTypes.LITERAL_WHILE).when(ast).getType();
		test.visitToken(ast);
		assertEquals(1, test.getLoopCount());
	}
	
	@Test
	public void DoWhileLoopTest() {
		LoopCountCheck test = new LoopCountCheck();
		DetailAST ast = mock(DetailAST.class);
		doReturn(TokenTypes.LITERAL_DO).when(ast).getType();
		test.visitToken(ast);
		test.finishTree(ast);
		assertEquals(1, test.getLoopCount());
	}
	
	@Test
	public void MultiLoopTest() { 
		LoopCountCheck test = new LoopCountCheck();
		DetailAST ast = mock(DetailAST.class);
		
		doReturn(TokenTypes.LITERAL_FOR).when(ast).getType();
		for (int i = 0; i < 5; i++) { 
			test.visitToken(ast);
		}
		doReturn(TokenTypes.LITERAL_DO).when(ast).getType();
		for (int i = 0; i < 3; i++) {
			test.visitToken(ast);
		}
		doReturn(TokenTypes.LITERAL_WHILE).when(ast).getType();
		for (int i = 0; i < 2; i++) { 
			test.visitToken(ast);
		}
		test.finishTree(ast);
		assertEquals(10, test.getLoopCount());
	}
	
	@Test
	public void FinishTreeTest() {
		
		LoopCountCheck test = spy(LoopCountCheck.class);
		DetailAST ast = mock(DetailAST.class);
		
	     final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	     System.setOut(new PrintStream(outputStream));
		
		test.beginTree(ast); 
		test.finishTree(ast);
			
		doThrow(NullPointerException.class).when(test).finishTree(null);
		assertEquals("Error: Unable to walk through tree",outputStream.toString().trim());
		
	}
}
