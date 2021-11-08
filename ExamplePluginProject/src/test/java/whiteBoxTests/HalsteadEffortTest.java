package test.java.whiteBoxTests;

import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import plugin.HalsteadDifficultyCheck;
import plugin.HalsteadEffortCheck;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;


public class HalsteadEffortTest {


Integer[] tokens = { 
			
		//unary
		TokenTypes.POST_INC,TokenTypes.POST_DEC,TokenTypes.DEC,TokenTypes.INC,
		TokenTypes.LNOT,TokenTypes.BNOT,TokenTypes.UNARY_MINUS,TokenTypes.UNARY_PLUS,
		
		//arithmetic
		TokenTypes.STAR,TokenTypes.DIV,TokenTypes.MOD,TokenTypes.PLUS,TokenTypes.MINUS,
		TokenTypes.BSR,TokenTypes.SR,TokenTypes.SL,
		
		//relational
		TokenTypes.LT,TokenTypes.GT,TokenTypes.LE,TokenTypes.GE,
		TokenTypes.LITERAL_INSTANCEOF,TokenTypes.EQUAL,TokenTypes.NOT_EQUAL,
		
		//bitwise
		TokenTypes.BAND,TokenTypes.BXOR,TokenTypes.BOR,
		
		//logical
		TokenTypes.LAND,TokenTypes.LOR,
		
		//ternary
		TokenTypes.QUESTION,TokenTypes.EOF,
		
		//assignment
		TokenTypes.ASSIGN,TokenTypes.BAND_ASSIGN,TokenTypes.BOR_ASSIGN,
		TokenTypes.BSR_ASSIGN,TokenTypes.BXOR_ASSIGN,TokenTypes.DIV_ASSIGN,
		TokenTypes.MINUS_ASSIGN,TokenTypes.MOD_ASSIGN,TokenTypes.PLUS_ASSIGN,
		TokenTypes.SL_ASSIGN,TokenTypes.SR_ASSIGN,TokenTypes.STAR_ASSIGN,
		
		//operands 
		TokenTypes.IDENT, 
		TokenTypes.NUM_DOUBLE,
		TokenTypes.NUM_FLOAT,
		TokenTypes.NUM_INT,
		TokenTypes.NUM_LONG 
			
	};

	HashSet<Integer> expectedTokens = new HashSet<Integer>(Arrays.asList(tokens));

	@Test
	public void defaultTokensTest() {
		
		HalsteadEffortCheck test = new HalsteadEffortCheck();
		List<Integer> tokens = Arrays.stream(test.getDefaultTokens()).boxed().collect(Collectors.toList());
		HashSet<Integer> actualTokens = new HashSet<Integer>(tokens);
		for (int token : expectedTokens)
			assertTrue(actualTokens.contains(token));
	}

	@Test
	public void getAcceptableTokensTest() {
		HalsteadEffortCheck test = new HalsteadEffortCheck();
		
		List<Integer> tokens = Arrays.stream(test.getAcceptableTokens()).boxed().collect(Collectors.toList());
		HashSet<Integer> actualTokens = new HashSet<Integer>(tokens);

		for (int token : expectedTokens)
			assertTrue(actualTokens.contains(token));
	}

	@Test
	public void testGetRequiredTokens() {
		HalsteadEffortCheck test = new HalsteadEffortCheck();
		List<Integer> tokens = Arrays.stream(test.getRequiredTokens()).boxed().collect(Collectors.toList());
		HashSet<Integer> actualTokens = new HashSet<Integer>(tokens);
		for (int token : expectedTokens)
			assertTrue(actualTokens.contains(token));
	}

	
	@Test
	public void calcHalsteadEffortTest() {
		HalsteadEffortCheck test = spy(new HalsteadEffortCheck());
		DetailAST ast = mock(DetailAST.class);
		doReturn(10.0).when(test).getHalsteadVolume();
		doReturn(10.0).when(test).getHalsteadDifficulty();
		assertEquals(100, test.calcHalsteadEffort(), 0.1);
		
	}
	
	@Test
	public void FinishTreeTest() {
		HalsteadEffortCheck test = spy(HalsteadEffortCheck.class);
		DetailAST ast = mock(DetailAST.class);
		final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	    System.setOut(new PrintStream(outputStream));
		test.finishTree(ast);
		doThrow(NullPointerException.class).when(test).finishTree(null);
		assertEquals("Error: Unable to walk through tree",outputStream.toString().trim());
	}
	

}
