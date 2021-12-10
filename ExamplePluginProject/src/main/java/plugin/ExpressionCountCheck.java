package plugin;


import com.puppycrawl.tools.checkstyle.api.*;

public class ExpressionCountCheck extends AbstractCheck {

	private int expressionCount;
	public int getCount() { return expressionCount;}


	@Override
	public int[] getDefaultTokens() {
		return new int[] { TokenTypes.CLASS_DEF, TokenTypes.INTERFACE_DEF };
	}

	@Override
	public int[] getAcceptableTokens() {
		return new int[] {TokenTypes.CLASS_DEF, TokenTypes.INTERFACE_DEF  };
	}


	@Override
	public void visitToken(DetailAST aAST) {
		countExpressionTokens(aAST);
	}

	@Override
	public int[] getRequiredTokens() {
		return new int[0];
	}

	public void beginTree(DetailAST rootAST) {
		expressionCount = 0;
	}

	public void finishTree(DetailAST rootAST) {
		try {
			log(rootAST, "Expression Count :" + expressionCount);
		} catch (Exception e) {
			System.out.println("Error: Unable to walk through tree");
		}
	}

	private void countExpressionTokens(DetailAST ast) {
		if (ast.getChildCount() > 0) {
			expressionCount += ast.getChildCount(TokenTypes.EXPR);
			DetailAST child = ast.getFirstChild();
			// recursively call children
			while (child != null) {
				countExpressionTokens(child);
				//iterate through siblings
				child = child.getNextSibling();
			}
		}
	}
	
	
}
