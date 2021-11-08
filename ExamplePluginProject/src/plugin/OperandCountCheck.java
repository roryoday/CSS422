package plugin;

import java.util.HashSet;
import com.puppycrawl.tools.checkstyle.api.*;

public class OperandCountCheck extends AbstractCheck {

	int[] operandTokens = {
		TokenTypes.IDENT, 
		TokenTypes.NUM_DOUBLE,
		TokenTypes.NUM_FLOAT,
		TokenTypes.NUM_INT,
		TokenTypes.NUM_LONG 
	};
	int operandCount = 0;
	HashSet<String> uniqueOperand = new HashSet<String>();
	
	public int getOperandCount() {
		return operandCount;
	}

	public int getOperandUniqueCount() {
		return uniqueOperand.size();
	}

	@Override
	public void beginTree(DetailAST rootAST) {
		operandCount = 0; 
		uniqueOperand = new HashSet<String>(); 
	}

	@Override
	public void visitToken(DetailAST aAST) {
		operandCount++;
		uniqueOperand.add(aAST.getText()); 
	}

	@Override
	public void finishTree(DetailAST rootAST) {
		try {
			log(0, "{0} unique operands that appear {1} times.", uniqueOperand.size(), operandCount);
		}
		catch (Exception e) {
			System.out.println("Error: Unable to walk through tree");
		}
		
	}

	@Override
	public int[] getDefaultTokens() {
		return operandTokens;
	}

	@Override
	public int[] getAcceptableTokens() {
		return operandTokens;
	}

	@Override
	public int[] getRequiredTokens() {
		return operandTokens;
	}
}