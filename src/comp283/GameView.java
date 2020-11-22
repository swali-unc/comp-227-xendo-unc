/**
 * 
 */
package comp283;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author Syed & Alex
 * 
 * The view contains ONLY information on how to render items. If you want data, check the model.
 * If you want to see how events are handled, check the controller.
 *
 */
public class GameView extends JPanel implements ActionListener, GameViewObservable, GameInputObserver {
	private List<GameViewObserver> _observers; // Anyone that observes our events
	private JComboBox<String> _problemList; // Our problem list for the user to select from
	private JPanel _middlePanel; // Where our example sets go
	private JLabel _outputLabel; // This is whether the user's guess passes or not, the "PASS" or "FAIL" message 
	private GameLatexPanel _solutionPanel; // This is where the solution latex is rendered
	private JButton _showSolutionButton; // Button to show the answer
	private static final String _solutionButtonText = "Show Solution"; // Text of said button

	@Override
	public void actionPerformed(ActionEvent e) {
		if( e.getSource() instanceof JComboBox ) {
			// If it was a combo box, then our only combo box is them changing problems.
			// Notify the controller that we want to change problems, and it can handle that event.
			JComboBox combobox = (JComboBox)e.getSource();
			notifyObservers( new ProblemChangeEvent((String)combobox.getSelectedItem()) );
		} else if( e.getActionCommand() == _solutionButtonText) {
			// They want to see the solution, let controller know.
			notifyObservers( new ShowSolutionEvent() );
		}
	}
	
	@Override
	public void handleInputEvent( GameViewEvent e ) {
		// Let the controller know that input has changed
		notifyObservers( e );
	}
	
	// Adds a problem identifier string to our list. The model will know what the ID is
	// and can retrieve the problem according to the ID
	public void registerProblem( String problemidentifier ) {
		_problemList.addItem(problemidentifier);
	}
	
	public void notifyObservers( GameViewEvent e ) {
		for( GameViewObserver observer : _observers )
			observer.handleViewEvent(e);
	}
	
	public void addObserver( GameViewObserver observer ) {
		_observers.add(observer);
	}
	
	public void removeObserver( GameViewObserver observer ) {
		_observers.remove(observer);
	}

	public GameView() {
		_observers = new ArrayList<GameViewObserver>();
		
		setLayout(new BorderLayout());
		
		// -=-=-=-=-=-=-=-=-=-
		// Create the top part
		// -=-=-=-=-=-=-=-=-=-
		
		JPanel topPanel = new JPanel();
		JLabel problemChangeLabel = new JLabel("Select problem: ");
		_problemList = new JComboBox<String>();
		_problemList.setSelectedItem(GameEnvironment.DEFAULT_STARTING_PROBLEM);
		_problemList.addActionListener(this);
		
		_showSolutionButton = new JButton(_solutionButtonText);
		_showSolutionButton.addActionListener(this);
		_solutionPanel = new GameLatexPanel("Solution");
		_solutionPanel.setPreferredSize(new Dimension(600,40));
		
		topPanel.add(problemChangeLabel);
		topPanel.add(_problemList);
		if( GameEnvironment.SHOW_SOLUTION_BUTTON ) {
			topPanel.add(_showSolutionButton);
			topPanel.add(_solutionPanel);
		}
		
		add(topPanel,BorderLayout.NORTH);
		
		// -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-
		// Create the middle content panel
		// -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-
		
		_middlePanel = new JPanel();
		_middlePanel.setLayout(new GridLayout(0,2));
		// The actual default question will be filled in by the controller calling displayProblem
		
		add(_middlePanel,BorderLayout.CENTER);
		
		// -=-=-=-=-=-=-=-=-=-=-=-=-=-=-
		// Create the bottom input panel
		// -=-=-=-=-=-=-=-=-=-=-=-=-=-=-
		JPanel bottomPanel = new JPanel();
		GameInputPanel inputPanel = new GameInputPanel();
		JPanel answerPanel = new JPanel();
		answerPanel.setLayout(new GridLayout(0,1));
		
		bottomPanel.add(inputPanel);
		inputPanel.addObserver(this);
		
		_outputLabel = new JLabel("----");
		_outputLabel.setPreferredSize(new java.awt.Dimension(100,40));
		_outputLabel.setHorizontalAlignment(JLabel.CENTER);
		_outputLabel.setFont(GameEnvironment.OUTPUT_FONT);
		answerPanel.add(_outputLabel);
		bottomPanel.add(answerPanel);
		
		add(bottomPanel,BorderLayout.SOUTH);
	}
	
	// Give us a problem, and we will display the examples for it in the middle panel.
	public void displayProblem( GameProblem problem ) {
		// Clear the middle panel for new examples.
		_middlePanel.removeAll();
		
		GameTriangle[][] examples = problem.getExamples();
		for( GameTriangle[] example : examples ) {
			GameTriangleSolutionPanel solution = new GameTriangleSolutionPanel(example,problem);
			_middlePanel.add(solution);
		}
		
		_middlePanel.validate();
	}
	
	// The string next to the user's triangles input on whether they passed or failed the nature law
	public void SetOutputString( String text, Color color ) {
		_outputLabel.setText(text);
		_outputLabel.setForeground(color);
		_outputLabel.validate();
	}
	
	// Accepts latex/regular strings and renders it as the solution.
	public void SetSolutionString( String text ) {
		_solutionPanel.setString(text);
	}
}
