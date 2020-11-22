package comp283;

import javax.swing.JFrame;

/**
 * 
 * @author Syed & Alex
 * 
 * Not much going on here, just the main entrypoint.
 *
 */
public class Main {

	public static void main(String[] args) {
		// Nothing happens without data, create that model first
		GameModel model = new GameModel();
		// We need the user to be able to see stuff, make our view
		GameView view = new GameView();
		// We need to handle events and update our model, and use our model to carve
		// out appropriate UI changes.
		GameController controller = new GameController( view, model );
		
		// Create our main window, and when it exits, we exit
		JFrame mainFrame = new JFrame();
		mainFrame.setTitle(GameEnvironment.GAME_WINDOW_TITLE);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Set the content pane to the view
		mainFrame.setContentPane(view);
		
		// Pack, and make it visible
		mainFrame.pack();
		mainFrame.setVisible(true);
	}

}
