package comp283;

// The base abstract class
public abstract class GameViewEvent {
	public GameViewEvent() {
	}
	
	public boolean isProblemChange() {
		return false;
	}
	
	public boolean isGuessInputEvent() {
		return false;
	}
	
	public boolean isShowSolutionEvent() {
		return false;
	}
}

// When a problem changes
class ProblemChangeEvent extends GameViewEvent {
	private String _problemString;
	
	public ProblemChangeEvent( String problemstr ) {
		_problemString = problemstr;
	}
	
	@Override
	public boolean isProblemChange() {
		return true;
	}
	
	public String getProblem() {
		return _problemString;
	}
}

// When the user changes one of their guesses
class GuessInputEvent extends GameViewEvent {
	private GameTriangle[] _currentInput;
	
	@Override
	public boolean isGuessInputEvent() {
		return true;
	}
	
	public GameTriangle[] getTriangles() {
		return _currentInput;
	}
	
	public GuessInputEvent() {
		_currentInput = null;
	}
	
	public GuessInputEvent( GameTriangle triangles[] ) {
		_currentInput = triangles.clone();
	}
}

// User wants the solution
class ShowSolutionEvent extends GameViewEvent {
	@Override
	public boolean isShowSolutionEvent() {
		return true;
	}
}