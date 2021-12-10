package blackbox;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.DefaultContext;
import com.puppycrawl.tools.checkstyle.JavaParser;
import com.puppycrawl.tools.checkstyle.JavaParser.Options;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.FileText;

public class TestEngine {
	private String filePath;
	private String fileName;
	AbstractCheck check;
	List<Integer> tokens;
	
	public TestEngine(String pathname, String filename,AbstractCheck testingCheck ) {
		filePath = pathname;
		fileName = filename;
		check = testingCheck;
	}
	
	public void RunTestCase() throws IOException, CheckstyleException {
		
		
		File file = new File(filePath + fileName);
		FileText ft = new FileText(file,"UTF-8");
		FileContents fc = new FileContents(ft);
		//set file contents to check
		check.setFileContents(fc);
		//configure check
		check.configure(new DefaultConfiguration("Local"));
		check.contextualize(new DefaultContext());
		
		DetailAST root;
		//parse based on type of check
		if (check.isCommentNodesRequired()) {
			root = JavaParser.parseFile(file, Options.WITH_COMMENTS);
		} else {
			root = JavaParser.parse(fc);
		}
		
		//start tree
		check.beginTree(root);
		
		//get allowed tokens for check
		int[] allowedTokens = check.getAcceptableTokens();
		tokens = new ArrayList<Integer>(allowedTokens.length);
		for (int i : allowedTokens)
		{
			tokens.add(i);
		}
		
		//iterate through every token
		iterateAST(root);
		
		//finish tree
		check.finishTree(root);
		
		
	}
	
	
	private void iterateAST( DetailAST ast) {
		while(ast != null) {
			if(tokens.contains(ast.getType())) {
				check.visitToken(ast);
			}
			iterateAST(ast.getFirstChild());
			ast = ast.getNextSibling();
		}
	}
}
