package comp283;

/**
 * 
 * @author Syed & Alex
 * 
 * This is similar to a viewobserverable, but the main difference is that anything that
 * implements this interface should be related to user input specifically for guess triangles.
 * 
 * The view observable is more generic to the view, and gets more abstract messages. GameInputObservable
 * on the other hand should be very detailed ViewEvents regarding individual triangle changes and related
 * to input rather than other types of events, like showing the solution or drawing latex.
 *
 */
public interface GameInputObservable {
	void notifyObservers( GameViewEvent e );
	
	void addObserver( GameInputObserver observer );
	
	void removeObserver( GameInputObserver observer );
}
