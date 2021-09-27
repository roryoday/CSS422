package plugin;
import com.puppycrawl.tools.checkstyle.api.*;

import java.util.ArrayList;


public class HalsteadLengthCheck extends AbstractCheck {
	
		public int halsteadLength;
		
		int[] halsteadTokens = { 
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
		
		
		private OperandCountCheck operandCount = new OperandCountCheck();
		private OperatorCountCheck operatorCount = new OperatorCountCheck();
		
		private int[] operandTokens = operandCount.getDefaultTokens();
		private int[] operatorTokens = operatorCount.getDefaultTokens();
		
		// Calculates Halstead length.
		public int calcHalsteadLength() {
			return operandCount.getOperandCount() +  operatorCount.getOperatorCount();
		}


		@Override
		public void beginTree(DetailAST rootAST) {
			operandCount.beginTree(rootAST);
			operatorCount.beginTree(rootAST);
		}
		
		@Override
		public int[] getDefaultTokens() {
			return halsteadTokens;
		}

		@Override
		public int[] getAcceptableTokens() {
			return halsteadTokens;		
		}

		@Override
		public final int[] getRequiredTokens() {
			return halsteadTokens;
		}


		@Override
		public void visitToken(DetailAST ast) {
			
			if (arrayContains(operatorTokens, ast.getType())) {
				operatorCount.visitToken(ast);
			}
			if (arrayContains(operandTokens, ast.getType())) {
				operandCount.visitToken(ast);
			}
		}
		
		//returns true if list of ints contains specified int
		 private boolean arrayContains(int[] arr, int t) {
			 for(int i =0;i<arr.length;i++) {
				 if(arr[i]==t) {
					 return true;
				 }	  
			 }
			 return false;
		  }
		 
		@Override
		public void finishTree(DetailAST rootAST) {
			log(rootAST.getLineNo(),calcHalsteadLength()+" R.O.");
		}
	  
}