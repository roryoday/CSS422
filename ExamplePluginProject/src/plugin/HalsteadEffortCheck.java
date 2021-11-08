package plugin;
import java.util.ArrayList;
import com.puppycrawl.tools.checkstyle.api.*;

public class HalsteadEffortCheck  extends AbstractCheck{
	
	private double halsteadEffort;
	
     int[] halsteadEffortTokens = { 
			
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

	


	private HalsteadDifficultyCheck halsteadDifficulty = new HalsteadDifficultyCheck();
	private HalsteadVolumeCheck halsteadVolume = new HalsteadVolumeCheck();

	private ArrayList<Integer> difficultyTokens = arrayToList(halsteadDifficulty.getDefaultTokens());
	private ArrayList<Integer> volumeTokens = arrayToList(halsteadVolume.getDefaultTokens());

	@Override
	public void beginTree(DetailAST rootAST) {
		halsteadDifficulty.beginTree(rootAST);
		halsteadVolume.beginTree(rootAST);
	}

	@Override
	public void visitToken(DetailAST ast) {
		if (difficultyTokens.contains(ast.getType())) { 
			halsteadDifficulty.visitToken(ast);
		}
		if (volumeTokens.contains(ast.getType())) { 
			halsteadVolume.visitToken(ast);
		}
	}

	
	public double calcHalsteadEffort() {		
		return getHalsteadDifficulty() * getHalsteadVolume();
	}
	@Override
	public void finishTree(DetailAST rootAST) {
		halsteadDifficulty.finishTree(rootAST);
		halsteadVolume.finishTree(rootAST);
		try { 
			log(0, "Halstead Effort: " + calcHalsteadEffort() );
		} catch (Exception e) {
			System.out.println("Error: Unable to walk through tree");
		}
	}
	public double getHalsteadEffort() {
		return halsteadEffort;
	}
	
	public double getHalsteadVolume() {
		return halsteadVolume.calcHalsteadVolume();
	}

	public double getHalsteadDifficulty() {
		return halsteadDifficulty.CalcHalsteadDifficulty();
	}

	@Override
	public int[] getDefaultTokens() {
		return halsteadEffortTokens;
	}

	@Override
	public int[] getAcceptableTokens() {
		return halsteadEffortTokens;
	}

	@Override
	public final int[] getRequiredTokens() {
		return halsteadEffortTokens;
	}

	private ArrayList<Integer> arrayToList(int[] array) {
		ArrayList<Integer> returnList = new ArrayList<Integer>();
		for (int i : array) {
			returnList.add(i);
		}
		return returnList;
	}

}
