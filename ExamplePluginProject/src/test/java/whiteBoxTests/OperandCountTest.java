package test.java.whiteBoxTests;



import org.junit.Test;


import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;


import static org.mockito.Mockito.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import plugin.OperandCountCheck;



public class OperandCountTest {

	int[] operatorTypes = { 
			TokenTypes.IDENT, 
			TokenTypes.NUM_DOUBLE,
			TokenTypes.NUM_FLOAT,
			TokenTypes.NUM_INT,
			TokenTypes.NUM_LONG 
			};

	@Test
	public void GetDefaultTokensTest() {
		OperandCountCheck test = new OperandCountCheck();
		assertArrayEquals(operatorTypes, test.getDefaultTokens());
	}

	@Test
	public void GetAcceptableTokensTest() {
		OperandCountCheck test = new OperandCountCheck();
		assertArrayEquals(operatorTypes, test.getAcceptableTokens());
	}

	@Test
	public void GetRequiredTokensTest() {
		OperandCountCheck test = new OperandCountCheck();
		assertArrayEquals(operatorTypes, test.getRequiredTokens());
	}

	@Test 
	public void GetSingleOperandCountTest() { 
		OperandCountCheck test = new OperandCountCheck();
		DetailAST ast = mock(DetailAST.class);
		doReturn(operatorTypes[0]).when(ast).getType();
		doReturn("operand").when(ast).getText();
		test.visitToken(ast);
		assertEquals(1, test.getOperandCount());
		assertEquals(1, test.getOperandUniqueCount());
	}

	@Test
	public void GetUniqueOperandCountTest() {
		OperandCountCheck test = new OperandCountCheck();
		DetailAST ast = mock(DetailAST.class);


		doReturn(operatorTypes[0]).when(ast).getType(); 
		doReturn("operand").when(ast).getText();
		for (int i = 0; i < 5; i++) { 
			test.visitToken(ast);
		}

		assertEquals(1, test.getOperandUniqueCount());
	}

	@Test
	public void GetOperandCountTest() {
		OperandCountCheck test = new OperandCountCheck();
		DetailAST ast = mock(DetailAST.class);
		doReturn(operatorTypes[0]).when(ast).getType();
		doReturn("operand").when(ast).getText();
		for (int i = 0; i < 10; i++) { 
			test.visitToken(ast);
		}
		doReturn(operatorTypes[1]).when(ast).getType();
		doReturn("operand1").when(ast).getText();
		test.visitToken(ast);
		assertEquals(11, test.getOperandCount());
	}
	
	@Test
	public void NoOperandTest() {
		OperandCountCheck test = new OperandCountCheck();
		DetailAST ast = mock(DetailAST.class);
		assertEquals(0, test.getOperandCount());
		assertEquals(0, test.getOperandUniqueCount());
		
	}
	
	@Test
	public void FinishTreeTest() {
		
		OperandCountCheck test = spy(OperandCountCheck.class);
		DetailAST ast = mock(DetailAST.class);
	    final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	    System.setOut(new PrintStream(outputStream));
		test.finishTree(ast);
		doThrow(NullPointerException.class).when(test).finishTree(null);
		assertEquals("Error: Unable to walk through tree",outputStream.toString().trim());
	
	}
}
