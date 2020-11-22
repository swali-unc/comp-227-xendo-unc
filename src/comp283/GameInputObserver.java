package comp283;

/**
 * 
 * @author Syed & Alex
 * 
 * Anything that observers game input events. Note this is specifically related
 * to input events regarding guess triangles, not other input events like buttons.
 *
 */
public interface GameInputObserver {
	void handleInputEvent( GameViewEvent e );
}
