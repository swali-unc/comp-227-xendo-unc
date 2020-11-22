package comp283;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

/**
 * 
 * @author Syed & Alex
 * 
 * This is a panel that has a bunch of triangles and drop-downs for the user to work with
 * to create a guess for the Xendo rule of nature.
 *
 */
public class GameInputPanel extends JPanel implements GameInputObservable, GameInputObserver {
	private List<GameInputObserver> _observers;
	private GameTriangleInputPanel[] _inputs;
	
	@Override
	public void handleInputEvent(GameViewEvent e) {
		if( e.isGuessInputEvent() ) {
			// We got some input from our input panels.
			// Grab our current set of inputs, and notify that our guess has changed.
			notifyObservers( new GuessInputEvent( convertInputsToArray() ) );
		}
	}

	@Override
	public void notifyObservers(GameViewEvent e) {
		for( GameInputObserver observer : _observers ) {
			observer.handleInputEvent(e);
		}
	}

	@Override
	public void addObserver(GameInputObserver observer) {
		_observers.add(observer);
	}

	@Override
	public void removeObserver(GameInputObserver observer) {
		_observers.add(observer);
	}
	
	public GameInputPanel() {
		_observers = new ArrayList<GameInputObserver>();
		_inputs = new GameTriangleInputPanel[GameEnvironment.NUM_INPUTS];
		
		// We create a number of inputs given in our global environment config
		for( int i = 0; i < _inputs.length; ++i ) {
			_inputs[i] = new GameTriangleInputPanel();
			_inputs[i].addObserver(this);
			add( _inputs[i] );
		}
	}
	
	/**
	 * It is much easier to work with an array of GameTriangle objects when working with
	 * rules. So here we simply ask all of our individual input panels what their current
	 * input is, and reassemble it into an array.
	 * 
	 */
	private GameTriangle[] convertInputsToArray() {
		GameTriangle[] triangles = new GameTriangle[GameEnvironment.NUM_INPUTS];
		
		for( int i = 0; i < triangles.length; ++i ) {
			triangles[i] = _inputs[i].getTriangle();
		}
		
		return triangles;
	}
}
