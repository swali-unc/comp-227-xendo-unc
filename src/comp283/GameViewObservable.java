package comp283;

/**
 * 
 * @author Syed & Alex
 * 
 * This interface describes a class that has overall view events that need to be shared.
 * It propagates view info after fully compiling "what is going on" into one event.
 *
 */
public interface GameViewObservable {
	void notifyObservers( GameViewEvent e );
	
	void addObserver( GameViewObserver observer );
	
	void removeObserver( GameViewObserver observer );
}
