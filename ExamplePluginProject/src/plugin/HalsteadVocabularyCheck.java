package plugin;
import java.util.ArrayList;
import com.puppycrawl.tools.checkstyle.api.*;

public class HalsteadVocabularyCheck extends AbstractCheck {
	
	
	  int[] halsteadVocabularyTokens = { 
				
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
	

	private OperandCountCheck operandCount = new OperandCountCheck();
	private OperatorCountCheck operatorCount = new OperatorCountCheck();

	private ArrayList<Integer> operandTokens = arrayToList(operandCount.getDefaultTokens());
	private ArrayList<Integer> operatorTokens = arrayToList(operatorCount.getDefaultTokens());

	@Override
	public void beginTree(DetailAST rootAST) {
		
		operandCount.beginTree(rootAST);
		operatorCount.beginTree(rootAST);
	}

	@Override
	public void visitToken(DetailAST ast) {
		
		if (operandTokens.contains(ast.getType())) {
			operandCount.visitToken(ast);
		}
		
		if (operatorTokens.contains(ast.getType())) {
			operatorCount.visitToken(ast);
		}
	}

	public int calcHalsteadVocabulary() {
		return getUniqueOperandCount() + getUniqueOperatorCount();
	}
	
	public int getUniqueOperandCount() {
		return operandCount.getOperandUniqueCount();
	}
	
	public int getUniqueOperatorCount() {
		return operatorCount.getOperatorUniqueCount();
	}
	
	@Override
	public void finishTree(DetailAST rootAST) {
		
		calcHalsteadVocabulary();
		
		try {
			log(0, "Halstead Vocabulary: " + calcHalsteadVocabulary());
		} catch (Exception e) {
			System.out.println("Error: Unable to walk through tree");
		}
	}


	@Override
	public int[] getDefaultTokens() {
		
		return halsteadVocabularyTokens;
	}

	@Override
	public int[] getAcceptableTokens() {
		
		return halsteadVocabularyTokens;
	}

	@Override
	public final int[] getRequiredTokens() {
		
		return halsteadVocabularyTokens;
	}

	//convert to ArrayList
	private ArrayList<Integer> arrayToList(int[] array) {
		ArrayList<Integer> returnList = new ArrayList<Integer>();
		for (int i : array) {
			returnList.add(i);
		}
		return returnList;
	}
}
