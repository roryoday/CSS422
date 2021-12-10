package whitebox;



import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import plugin.HalsteadVocabularyCheck;
import plugin.HalsteadVolumeCheck;
import plugin.LoopCountCheck;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/* Halstead Volume is the program length (N) times the log2 of the 
 * program vocabulary (n) [1,2] : 
 * Volume = N log2 n 
 */

	

public class HalsteadVolumeTest {
	 
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
	public void GetDefaultTokensTest() {
		HalsteadVolumeCheck test = new HalsteadVolumeCheck();
		List<Integer> tokens = Arrays.stream(test.getDefaultTokens()).boxed().collect(Collectors.toList());
		HashSet<Integer> actualTokens = new HashSet<Integer>(tokens);
		for (int token : expectedTokens)
			assertTrue(actualTokens.contains(token));
	}

	@Test
	public void GetAcceptableTokensTest() {
		HalsteadVolumeCheck test = new HalsteadVolumeCheck();
		List<Integer> tokens = Arrays.stream(test.getAcceptableTokens()).boxed().collect(Collectors.toList());
		HashSet<Integer> actualTokens = new HashSet<Integer>(tokens);
		for (int token : expectedTokens)
			assertTrue(actualTokens.contains(token));
	}

	@Test
	public void GetRequiredTokensTest() {
		HalsteadVolumeCheck test = new HalsteadVolumeCheck();
		List<Integer> tokens = Arrays.stream(test.getRequiredTokens()).boxed().collect(Collectors.toList());
		HashSet<Integer> actualTokens = new HashSet<Integer>(tokens);
		for (int token : expectedTokens)
			assertTrue(actualTokens.contains(token));
	}

	@Test //test with known values
	public void CalcHalsteadVolumeTest() { 
		HalsteadVolumeCheck test = spy(new HalsteadVolumeCheck());
		DetailAST ast = mock(DetailAST.class);
		doReturn(50).when(test).getHalsteadLength();
		doReturn(16).when(test).getHalsteadVocabulary();
		test.beginTree(ast);
		test.finishTree(ast);
		//  volume = 50 * lg(16) = 200
		assertEquals(200, test.calcHalsteadVolume(), 0.01);
	}

	@Test
	public void VisitTokensTest() {
		HalsteadVolumeCheck test = spy(new HalsteadVolumeCheck());
		DetailAST ast = mock(DetailAST.class);		
		doReturn(TokenTypes.INC).when(ast).getType(); 
		doReturn("++").when(ast).getText();
		test.visitToken(ast);
		assertEquals(1, test.getHalsteadLength());
		
	}

	
}
