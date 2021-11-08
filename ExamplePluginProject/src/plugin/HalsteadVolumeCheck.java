package plugin;
import com.puppycrawl.tools.checkstyle.api.*;
import java.util.ArrayList;

public class HalsteadVolumeCheck extends AbstractCheck{

	 int[] halsteadVolumeTokens = { 
				
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
		
	 

	private HalsteadLengthCheck halsteadLength = new HalsteadLengthCheck();
	private HalsteadVocabularyCheck halsteadVocabulary = new HalsteadVocabularyCheck();

	private ArrayList<Integer> halsteadLengthTokens = arrayToList(halsteadLength.getDefaultTokens());
	private ArrayList<Integer> halsteadVocabularyTokens = arrayToList(halsteadVocabulary.getDefaultTokens());

	@Override
	public void beginTree(DetailAST rootAST) {
		halsteadLength.beginTree(rootAST);
		halsteadVocabulary.beginTree(rootAST);
	}

	@Override
	public void visitToken(DetailAST ast) {
		
		if ( halsteadLengthTokens.contains(ast.getType())) {
			halsteadLength.visitToken(ast);
		}
		if (halsteadVocabularyTokens.contains(ast.getType())) {
			halsteadVocabulary.visitToken(ast);
		}
	}

	public double calcHalsteadVolume() {
		
		int halLength = getHalsteadLength();
	    int halVocabulary = getHalsteadVocabulary();
	    
	    double vocabLog = Math.log(halVocabulary) / Math.log(2);
	    return halLength * vocabLog;
	}
	
	@Override
	public void finishTree(DetailAST rootAST) {
		halsteadLength.finishTree(rootAST);
		halsteadVocabulary.finishTree(rootAST);

		try {
			log(0, "Halstead Volume: " + calcHalsteadVolume());
		} catch (Exception e) {
			System.out.println("Error: Unable to walk through tree");
		}
	}
	
	public double getHalsteadVolume() {
		return calcHalsteadVolume();
	}
	
	public int getHalsteadLength() {
		return halsteadLength.calcHalsteadLength();
	}
	
	public int getHalsteadVocabulary() {
		return halsteadVocabulary.calcHalsteadVocabulary();
	}

	@Override
	public int[] getDefaultTokens() {
		return  halsteadVolumeTokens;
	}

	@Override
	public int[] getAcceptableTokens() {
		return  halsteadVolumeTokens;
	}

	@Override
	public final int[] getRequiredTokens() {
		return  halsteadVolumeTokens;
	}

	
	//convert list to ArrayList
	private ArrayList<Integer> arrayToList(int[] array) {
		ArrayList<Integer> returnList = new ArrayList<Integer>();
		for (int i : array) {
			returnList.add(i);
		}
		return returnList;
	}
}
