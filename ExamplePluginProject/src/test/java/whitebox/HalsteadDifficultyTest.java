package whitebox;

import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.junit.Assert.assertEquals;


import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import plugin.ExpressionCountCheck;
import plugin.HalsteadDifficultyCheck;




public class HalsteadDifficultyTest {

	Integer[] HalsteadDifficultytokens = { 		
			//unary	
			TokenTypes.POST_INC,TokenTypes.POST_DEC,TokenTypes.DEC,TokenTypes.INC,
			TokenTypes.LNOT,TokenTypes.BNOT,TokenTypes.UNARY_MINUS,TokenTypes.UNARY_PLUS,
			
			//logic
			TokenTypes.LAND,TokenTypes.LOR,
			
			//arithmetic
			TokenTypes.STAR,TokenTypes.DIV,TokenTypes.MOD,TokenTypes.PLUS,TokenTypes.MINUS,
			TokenTypes.BSR,TokenTypes.SR,TokenTypes.SL,
			
			//operator
			TokenTypes.LT,TokenTypes.GT,TokenTypes.LE,TokenTypes.GE,
			TokenTypes.LITERAL_INSTANCEOF,TokenTypes.EQUAL,TokenTypes.NOT_EQUAL,
			
			//bitwise
			TokenTypes.BAND,TokenTypes.BXOR,TokenTypes.BOR,
			
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

	HashSet<Integer> expectedTokens = new HashSet<Integer>(Arrays.asList(HalsteadDifficultytokens));

	@Test
	public void DefaultTokensTest() {
		
		HalsteadDifficultyCheck test = new HalsteadDifficultyCheck();
		List<Integer> tokens =  Arrays.stream(test.getDefaultTokens()).boxed().collect(Collectors.toList());
		HashSet<Integer> actualTokens = new HashSet<Integer>(tokens);
		for (int token : expectedTokens)
			assertTrue(actualTokens.contains(token));
	}

	@Test
	public void GetAcceptableTokensTest() {
		HalsteadDifficultyCheck test = new HalsteadDifficultyCheck();
		List<Integer> tokens = Arrays.stream(test.getAcceptableTokens()).boxed().collect(Collectors.toList());
		HashSet<Integer> actualTokens = new HashSet<Integer>(tokens);
		for (int token : expectedTokens)
			assertTrue(actualTokens.contains(token));
	}

	@Test
	public void GetRequiredTokensTest() {
		HalsteadDifficultyCheck test = new HalsteadDifficultyCheck();
		List<Integer> tokens = Arrays.stream(test.getRequiredTokens()).boxed().collect(Collectors.toList());
		HashSet<Integer> actualTokens = new HashSet<Integer>(tokens);
		for (int token : expectedTokens)
			assertTrue(actualTokens.contains(token));
	}

	
	@Test
	public void CalculateHalsteadDifficultyTest() {
		//(unique operators / 2) * (number of operands / unique operators)
		HalsteadDifficultyCheck test = spy(new HalsteadDifficultyCheck());
		DetailAST ast = mock(DetailAST.class);
		doReturn(10.0).when(test).getUniqueOperators();
		doReturn(10.0).when(test).getOperands();
		doReturn(5.0).when(test).getUniqueOperands();
		assertEquals(10.001, test.CalcHalsteadDifficulty(), 0.1);
		
	}
	

}
