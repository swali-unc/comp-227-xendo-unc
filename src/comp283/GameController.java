package comp283;

/**
 * 
 * @author Syed & Alex
 * 
 * The controller serves as an arbiter between the view and the model.
 * We take messages from the view, and store it in our model. We retrieve data
 * from our model, and pass it in a format that the view can use to easily render
 * data inside of our model.
 *
 */
public class GameController implements GameViewObserver {
	private GameView _view;
	private GameModel _model;
	
	/**
	 * This will handle any messages coming from the view
	 */
	@Override
	public void handleViewEvent(GameViewEvent event) {
		if( event.isProblemChange() ) {
			// They changed problems, so grab the new problem from the model
			ProblemChangeEvent e = (ProblemChangeEvent)event;
			GameProblem new_problem = _model.getProblemFromIdentifier(e.getProblem());
			
			_view.displayProblem(new_problem);
			_model.setCurrentProblem( new_problem );
			
			GameTriangle[] current_guess = _model.getCurrentGuess();
			if( current_guess != null ) {
				// We need to update the output
				SetOutputForGuess();
			}
			_view.SetSolutionString(""); // remove the previous solution, if shown
		} else if( event.isGuessInputEvent() ) {
			// The student changed one of their inputs, so we should re-evaluate their
			// current input and determine if it passes or fails the law.
			GuessInputEvent e = (GuessInputEvent)event;
			_model.setCurrentGuess(e.getTriangles());
			SetOutputForGuess();
		} else if( event.isShowSolutionEvent() ) {
			// They want to see the answer, grab it from the model, and send it to our view.
			_view.SetSolutionString(_model.getCurrentProblem().getSolution());
		}
	}
	
	public GameController( GameView view, GameModel model ) {
		this._view = view;
		this._model = model;
		
		// We need to observe events in the view
		_view.addObserver(this);
		
		// Add all of the problems in our model to the dropdown menu
		for( String s : _model.getProblemIdentifiers() )
			_view.registerProblem(s);
		
		// And display the default problem
		GameProblem default_problem = model.getProblemFromIdentifier(GameEnvironment.DEFAULT_STARTING_PROBLEM);
		_view.displayProblem(default_problem);
		_model.setCurrentProblem(default_problem);
	}
	
	/**
	 * This method simply sets the PASS/FAIL output for whatever the current input is.
	 * We work only off of data in the model, any callee should update that before calling us.
	 */
	private void SetOutputForGuess() {
		GameTriangle[] guess = _model.getCurrentGuess();
		GameProblem problem = _model.getCurrentProblem();
		
		if( problem.doesSetPass( guess ) )
			_view.SetOutputString(GameEnvironment.VALID_GUESS_TEXT, GameEnvironment.VALID_GUESS_COLOR);
		else
			_view.SetOutputString(GameEnvironment.INVALID_GUESS_TEXT, GameEnvironment.INVALID_GUESS_COLOR);
	}
}
