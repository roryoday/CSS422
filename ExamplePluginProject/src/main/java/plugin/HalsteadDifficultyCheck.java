package plugin;

import java.util.ArrayList;


import com.puppycrawl.tools.checkstyle.api.*;

public class HalsteadDifficultyCheck extends AbstractCheck {
	
	private double halsteadDifficulty;
	int[] HalsteadDifficultytokens = { 		
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

	


	private OperandCountCheck operandCount = new OperandCountCheck(); 
	private OperatorCountCheck operatorCount = new OperatorCountCheck();

	private ArrayList<Integer> operandTokens = arrayToList(operandCount.getDefaultTokens());
	private ArrayList<Integer> operatorTokens = arrayToList(operatorCount.getDefaultTokens());

	@Override
	public void beginTree(DetailAST rootAST) {
		operandCount.beginTree(rootAST); 
		operatorCount.beginTree(rootAST);
	}

	@Override // Visit tokens based on type.
	public void visitToken(DetailAST ast) {
		if (operandTokens.contains(ast.getType())) { 
			operandCount.visitToken(ast);
		}
		if (operatorTokens.contains(ast.getType())) { 
			operatorCount.visitToken(ast);
		}
	}

	// 	Halstead Difficulty is (unique operators / 2) * (number of operands / unique operators)
	public double CalcHalsteadDifficulty() {
		return (getUniqueOperators() / 2) *  (getOperands() / getUniqueOperands());
	}
	
	@Override
	public void finishTree(DetailAST rootAST) {
	
		double difficultyResults = CalcHalsteadDifficulty();
		try { 
			log(0, "Halstead Difficulty: " + difficultyResults);
		} catch (Exception e) {
			System.out.println("Unable to walk through tree");
		}
	}


	public double getUniqueOperators() {
		return (double) operatorCount.getOperatorUniqueCount();
	}

	public double getUniqueOperands() {
		return (double) operandCount.getOperandUniqueCount();
	}

	public double getOperands() {
		return (double) operandCount.getOperandCount();
	}

	
	@Override
	public int[] getDefaultTokens() {
		
		return HalsteadDifficultytokens;
	}

	@Override
	public int[] getAcceptableTokens() {
	
		return HalsteadDifficultytokens;
	}

	@Override
	public final int[] getRequiredTokens() {
	
		return HalsteadDifficultytokens;
	}

	
	private ArrayList<Integer> arrayToList(int[] array) {
		ArrayList<Integer> returnList = new ArrayList<Integer>();
		for (int i : array) {
			returnList.add(i);
		}
		return returnList;
	}

}