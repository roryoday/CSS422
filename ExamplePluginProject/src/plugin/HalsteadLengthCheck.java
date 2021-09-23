package plugin;
import com.puppycrawl.tools.checkstyle.api.*;

public class HalsteadLengthCheck extends AbstractCheck {
	
	  int count = 0;
	
	

	int[] operators = {
		//logical
		TokenTypes.LAND,TokenTypes.LOR,
		//unary
		TokenTypes.POST_INC,TokenTypes.POST_DEC,TokenTypes.DEC,TokenTypes.INC,
		TokenTypes.LNOT,TokenTypes.LNOT,
		//arithmetic
		TokenTypes.STAR,TokenTypes.DIV,TokenTypes.MOD,TokenTypes.PLUS,TokenTypes.MINUS,
		TokenTypes.BSR,TokenTypes.SR,TokenTypes.SL,
		//relational
		TokenTypes.LT,TokenTypes.GT,TokenTypes.LE,TokenTypes.GE,
		TokenTypes.LITERAL_INSTANCEOF,TokenTypes.EQUAL,TokenTypes.NOT_EQUAL,
		//ternary
		TokenTypes.QUESTION,TokenTypes.EOF,
		//assignment
		TokenTypes.ASSIGN,TokenTypes.BAND_ASSIGN,TokenTypes.BOR_ASSIGN,
		TokenTypes.BSR_ASSIGN,TokenTypes.BXOR_ASSIGN,TokenTypes.DIV_ASSIGN,
		TokenTypes.MINUS_ASSIGN,TokenTypes.MOD_ASSIGN,TokenTypes.PLUS_ASSIGN,
		TokenTypes.SL_ASSIGN,TokenTypes.SR_ASSIGN,TokenTypes.STAR_ASSIGN,
		//bitwise
		TokenTypes.BAND,TokenTypes.BXOR,TokenTypes.LOR,
		//operand
		TokenTypes.NUM_FLOAT,TokenTypes.NUM_LONG,TokenTypes.NUM_DOUBLE,TokenTypes.IDENT,
		TokenTypes.NUM_INT
	   };	
	
		
	
	
	  @Override
	    public void beginTree(DetailAST rootAST) {
	    	count = 0;
	    }

	
	 @Override
	    public int[] getDefaultTokens() {
	        return operators;
	    }
	  
	 @Override
		public int[] getRequiredTokens() {
			return  operators;
		}
	 
	 @Override
	    public void visitToken(DetailAST aAST) {
		  count++;
	    }
	 
	 @Override
		public int[] getAcceptableTokens() {
			return  operators; 
		}
	 
	 @Override
	    public void finishTree(DetailAST rootAST) {
		 log(rootAST.getLineNo(),count+"");
	    }

	

}
