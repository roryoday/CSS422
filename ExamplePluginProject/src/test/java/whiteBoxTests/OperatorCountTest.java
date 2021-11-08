package test.java.whiteBoxTests;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import plugin.OperatorCountCheck;



public class OperatorCountTest {
	
	int[] operatorTokens = {
			//unary	
			TokenTypes.POST_INC,
			TokenTypes.POST_DEC,
			TokenTypes.DEC,
			TokenTypes.INC,
			TokenTypes.LNOT,
			TokenTypes.BNOT,
			TokenTypes.UNARY_MINUS,
			TokenTypes.UNARY_PLUS,
			
			//logical
			TokenTypes.LAND,
			TokenTypes.LOR,
			
			//arithmetic
			TokenTypes.STAR,
			TokenTypes.DIV,
			TokenTypes.MOD,
			TokenTypes.PLUS,
			TokenTypes.MINUS,
			TokenTypes.BSR,
			TokenTypes.SR,
			TokenTypes.SL,
			
			//relational
			TokenTypes.LT,
			TokenTypes.GT,
			TokenTypes.LE,
			TokenTypes.GE,
			TokenTypes.LITERAL_INSTANCEOF,
			TokenTypes.EQUAL,
			TokenTypes.NOT_EQUAL,
			
			//bitwise
			TokenTypes.BAND,
			TokenTypes.BXOR,
			TokenTypes.BOR,
			
			//ternary
			TokenTypes.QUESTION,
			TokenTypes.EOF,
			
			//assignment
			TokenTypes.ASSIGN,
			TokenTypes.BAND_ASSIGN,
			TokenTypes.BOR_ASSIGN,
			TokenTypes.BSR_ASSIGN,
			TokenTypes.BXOR_ASSIGN,
			TokenTypes.DIV_ASSIGN,
			TokenTypes.MINUS_ASSIGN,
			TokenTypes.MOD_ASSIGN,
			TokenTypes.PLUS_ASSIGN,
			TokenTypes.SL_ASSIGN,
			TokenTypes.SR_ASSIGN,
			TokenTypes.STAR_ASSIGN,
		   };	

	
	@Test
	public void GetDefaultTokensTest() {
		OperatorCountCheck test = new OperatorCountCheck();
		assertArrayEquals(operatorTokens, test.getDefaultTokens());
	}

	@Test
	public void GetAcceptableTokensTest() {
		OperatorCountCheck test = new OperatorCountCheck();
		assertArrayEquals(operatorTokens, test.getAcceptableTokens());
	}

	@Test
	public void GetRequiredTokensTest() {
		OperatorCountCheck test = new OperatorCountCheck();
		assertArrayEquals(operatorTokens, test.getRequiredTokens());
	}

	@Test
	public void GetOperatorCountTest() { 
		OperatorCountCheck test = new OperatorCountCheck();
		DetailAST ast = mock(DetailAST.class);
		doReturn(operatorTokens[0]).when(ast).getType();
		doReturn("operand").when(ast).getText();
		test.visitToken(ast);
		assertEquals(1, test.getOperatorCount());
	}

	@Test
	public void UniqueOperatorCountTest() {
		OperatorCountCheck test = new OperatorCountCheck();
		DetailAST ast = mock(DetailAST.class);
		doReturn(operatorTokens[1]).when(ast).getType();
		doReturn("operator").when(ast).getText();
		for (int i = 0; i < 10; i++) { 
			test.visitToken(ast);
		}
		assertEquals(1, test.getOperatorUniqueCount());
		
	}


	@Test 
	public void NoOperatorTest() {
		OperatorCountCheck test = new OperatorCountCheck();
		DetailAST ast = mock(DetailAST.class);
		test.beginTree(ast); 
		assertEquals(0, test.getOperatorCount());
	}
	
	
	@Test
	public void FinishTreeTest() {
		OperatorCountCheck test = spy(OperatorCountCheck.class);
		DetailAST ast = mock(DetailAST.class);
	    final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	    System.setOut(new PrintStream(outputStream));
		test.finishTree(ast);
		doThrow(NullPointerException.class).when(test).finishTree(null);
		assertEquals("Error: Unable to walk through tree",outputStream.toString().trim());
		
	}
  }

