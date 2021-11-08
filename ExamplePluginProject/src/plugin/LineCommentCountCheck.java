package plugin;
import com.puppycrawl.tools.checkstyle.api.*;

public class LineCommentCountCheck extends AbstractCheck {

	int lineCount = 0;
	
	public void beginTree(DetailAST rootAST) {
		lineCount = 0;
	}
	
	@Override
	public void visitToken(DetailAST ast) {

		switch (ast.getType()) {
		case TokenTypes.SINGLE_LINE_COMMENT:
			lineCount++;
			break;
			
		case TokenTypes.BLOCK_COMMENT_BEGIN:
			lineCount++;
			//subtract end - start
			lineCount += ast.findFirstToken(TokenTypes.BLOCK_COMMENT_END).getLineNo() - ast.getLineNo();
			break;
			
		
		}
	}
	
	@Override
	public int[] getAcceptableTokens() {
		return getRequiredTokens();
	}

	@Override
	public boolean isCommentNodesRequired() {
		return true;
	}

	@Override
	public int[] getRequiredTokens() {
		return new int[] { TokenTypes.SINGLE_LINE_COMMENT, TokenTypes.BLOCK_COMMENT_BEGIN, };
	}

	@Override
	public int[] getDefaultTokens() {
		return new int[] { TokenTypes.SINGLE_LINE_COMMENT, TokenTypes.BLOCK_COMMENT_BEGIN };
	}

	public int getCount() {
		return this.lineCount;
	}
	
	@Override
    public void finishTree(DetailAST rootAST) {
       try {
	       log(rootAST.getLineNo(),lineCount+"");
		  } catch (NullPointerException e) {
				System.out.println("Error: Unable to walk through tree");
			}
    }
}
