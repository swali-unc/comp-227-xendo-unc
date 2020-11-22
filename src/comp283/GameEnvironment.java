package comp283;

import java.awt.Color;
import java.awt.Font;

/**
 * 
 * @author Syed & Alex
 *
 * Here are constant variables that are options that we want in our game.
 * Modify stuff here so you don't have to hunt down parts of code.
 */
public final class GameEnvironment {
	// This is the window title for our main window
	public static final String GAME_WINDOW_TITLE = "COMP283: Quantifiers Practice";
	
	// This is the problem that we want to show up initially
	public static final String DEFAULT_STARTING_PROBLEM = "Existential 1";
	
	// Do we want to allow a "Show Solution" button to appear?
	// Note: this is NOT secure, a motivated person could just modify the jar file
	public static final boolean SHOW_SOLUTION_BUTTON = true;
	
	// How many slots of input does the user get?
	public static final int NUM_INPUTS = 5;
	
	// What should an empty input slot say? This can also be empty string
	public static final String EMPTY_TRIANGLE_TEXT = "Empty";
	
	// What is the default triangle in our input
	public static final String DEFAULT_TRIANGLE_COLOR = "Blue";
	public static final String DEFAULT_TRIANGLE_SIZE = "Large";
	
	// What is the color for valid guesses?
	public static final Color VALID_GUESS_COLOR = new Color(10,170,30); // Color.GREEN is way too bright
	
	// What is the color for invalid guesses?
	public static final Color INVALID_GUESS_COLOR = Color.RED;
	
	// What is the text for a valid guess?
	public static final String VALID_GUESS_TEXT = "PASS";
	
	// What is the text for an invalid guess?
	public static final String INVALID_GUESS_TEXT = "FAIL";
	
	// What font should the text be?
	public static final Font OUTPUT_FONT = new Font("Serif", Font.BOLD, 32);
}