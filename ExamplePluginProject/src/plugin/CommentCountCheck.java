package plugin;

import com.puppycrawl.tools.checkstyle.api.*;


public class CommentCountCheck extends AbstractCheck {
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
		try {
			log(rootAST.getLineNo(),count+" R.O.");
		}
	    catch (Exception e) {
	    	System.out.println("Error: Unable to walk through tree");
		}
		
	}
	
	@Override
	public int[] getAcceptableTokens() {
		return new int[] {TokenTypes.COMMENT_CONTENT}; 
	}

	 public int  getCount() {
		return count;
	 }
	
		
	  
}