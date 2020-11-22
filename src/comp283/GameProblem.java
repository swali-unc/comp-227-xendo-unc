package comp283;

/**
 * 
 * @author Syed & Alex
 * 
 * A GameProblem must implement these functions so that we have examples
 * and that we have a function that can process a set to see if it passes the rule or not.
 * Lastly, a problem without a solution is no fun at all, so we need that too.
 *
 */
public interface GameProblem {
	boolean doesSetPass( GameTriangle triangles[] );
	String getSolution();
	GameTriangle[][] getExamples();
}
