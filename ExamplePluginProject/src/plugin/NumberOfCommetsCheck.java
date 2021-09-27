package plugin;

import com.puppycrawl.tools.checkstyle.api.*;


public class NumberOfCommetsCheck extends AbstractCheck {
	int count = 0;
	
	@Override
	public void beginTree(DetailAST rootAST) {
	 	count = 0;
	}
	
	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.COMMENT_CONTENT};
	}
	  
	@Override
	public int[] getRequiredTokens() {
		return new int[0];
	}
	
	@Override
	public void visitToken(DetailAST aAST) {
		count++;   
	}
	
	@Override
	public boolean isCommentNodesRequired() {
		return true;
	}
	
	@Override
	public void finishTree(DetailAST rootAST) {
		log(rootAST.getLineNo(),count+" R.O.");
	}
	
	@Override
	public int[] getAcceptableTokens() {
		return new int[] {TokenTypes.COMMENT_CONTENT}; 
	}

	
		
	  
}