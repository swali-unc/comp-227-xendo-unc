package comp283;

/**
 * 
 * @author Syed & Alex
 * 
 * This interface describes a class that wants to see view events.
 *
 */
public interface GameViewObserver {
	void handleViewEvent( GameViewEvent event );
}
