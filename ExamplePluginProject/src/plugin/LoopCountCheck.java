package plugin;
import com.puppycrawl.tools.checkstyle.api.*;
public class LoopCountCheck extends AbstractCheck {
	public int loopCount;
	 int[] loopTokens = { 
				TokenTypes.LITERAL_FOR, 
				TokenTypes.LITERAL_WHILE, 
				TokenTypes.LITERAL_DO, 
				
		};
		
	public void beginTree(DetailAST rootAST) {
		loopCount = 0;
	}
	@Override
	public int[] getAcceptableTokens() {
		return loopTokens;
	}
	@Override
	public int[] getRequiredTokens() {
		return loopTokens;
	}

	@Override
	public int[] getDefaultTokens() {
		return loopTokens;
	}

	
	@Override
	public void visitToken(DetailAST ast) {
		if(ast.getType() == TokenTypes.LITERAL_FOR || ast.getType() == TokenTypes.LITERAL_FOR || ast.getType() == TokenTypes.LITERAL_DO)
			loopCount++;
	}

	public int getLoopCount() {		
		return loopCount;
	}
	
	public void finishTree(DetailAST rootAST) {
		try {
			log(0, " loop count: {0}. Exceeded maximum number of loops", loopCount);		
		} 
		catch (Exception e) {
			System.out.println("Error: Unable to walk through tree");
		}
	}	
}
