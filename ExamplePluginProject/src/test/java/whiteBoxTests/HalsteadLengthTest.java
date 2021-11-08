package test.java.whiteBoxTests;

import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import plugin.HalsteadEffortCheck;
import plugin.HalsteadLengthCheck;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

// Halstead Length is the sum of the total number of operators and operand.

public class HalsteadLengthTest {
	
	Integer[] tokens = { 
			
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

			
			// operands 
			TokenTypes.IDENT, 
			TokenTypes.NUM_DOUBLE,
			TokenTypes.NUM_FLOAT,
			TokenTypes.NUM_INT,
			TokenTypes.NUM_LONG 
			
	};

	HashSet<Integer> expectedTokens = new HashSet<Integer>(Arrays.asList(tokens));

	@Test
	public void DefaultTokensTest() {
		
		HalsteadLengthCheck test = new HalsteadLengthCheck();
		List<Integer> tokens = Arrays.stream(test.getDefaultTokens()).boxed().collect(Collectors.toList());
		HashSet<Integer> actualTokens = new HashSet<Integer>(tokens);

		for (int token : expectedTokens)
			assertTrue(actualTokens.contains(token));
	}

	@Test
	public void GetAcceptableTokensTest() {
		HalsteadLengthCheck test = new HalsteadLengthCheck();
		
		List<Integer> tokens = Arrays.stream(test.getAcceptableTokens()).boxed().collect(Collectors.toList());
		HashSet<Integer> actualTokens = new HashSet<Integer>(tokens);

		for (int token : expectedTokens)
			assertTrue(actualTokens.contains(token));
	}

	@Test
	public void GetRequiredTokensTest() {
		HalsteadLengthCheck test = new HalsteadLengthCheck();
		List<Integer> tokens = Arrays.stream(test.getRequiredTokens()).boxed().collect(Collectors.toList());
		HashSet<Integer> actualTokens = new HashSet<Integer>(tokens);
		for (int token : expectedTokens)
			assertTrue(actualTokens.contains(token));
	}

	@Test 
	public void CalcHalsteadLengthTest() {
		
		HalsteadLengthCheck test = spy(new HalsteadLengthCheck());
		DetailAST ast = mock(DetailAST.class);
		doReturn(3).when(test).getOperandCount(); 
		doReturn(3).when(test).getOperatorCount(); 
		assertEquals(6, test.calcHalsteadLength());
	}

	
	@Test
	public void CalcHalsteadLengthSingleOperatorTest() { 
		HalsteadLengthCheck test = spy(new HalsteadLengthCheck());
		DetailAST ast = mock(DetailAST.class);
		// Test 10 operands 
		doReturn(TokenTypes.NUM_DOUBLE).when(ast).getType(); 
		for (int i = 0; i < 5; i++) { 
			test.visitToken(ast);
		}
		doReturn(TokenTypes.LNOT).when(ast).getType(); 
		test.visitToken(ast);
		assertEquals(6, test.calcHalsteadLength());
	}

	@Test 
	public void CalcHalsteadLengthSingleOperandTest() { 
		HalsteadLengthCheck test = spy(new HalsteadLengthCheck());
		DetailAST ast = mock(DetailAST.class);
		doReturn(TokenTypes.NUM_DOUBLE).when(ast).getType(); 
		test.visitToken(ast);
		doReturn(TokenTypes.LNOT).when(ast).getType(); 
		for (int i = 0; i < 10; i++) { 
		 test.visitToken(ast);
		}
		assertEquals(11, test.calcHalsteadLength());
	}
	
	@Test
	public void NoLengthTest() {
		HalsteadLengthCheck test = spy(new HalsteadLengthCheck());
		DetailAST ast = mock(DetailAST.class);
		assertEquals(0, test.calcHalsteadLength());
	}
	
	@Test
	public void FinishTreeTest() {
		HalsteadLengthCheck test = spy(HalsteadLengthCheck.class);
		DetailAST ast = mock(DetailAST.class);
		final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	    System.setOut(new PrintStream(outputStream));
		test.finishTree(ast);
		doThrow(NullPointerException.class).when(test).finishTree(null);
		assertEquals("Error: Unable to walk through tree",outputStream.toString().trim());
	}
	
}
