package comp283;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * 
 * @author Syed & Alex
 * 
 * This is a single input panel of a triangle, and drop-downs to select size and color.
 *
 */
public class GameTriangleInputPanel extends JPanel implements ActionListener, GameInputObservable {
	private JLabel _triangleIcon;
	private int _triangleColor;
	private int _triangleSize;
	private List<GameInputObserver> _observers;

	@Override
	public void actionPerformed(ActionEvent e) {
		if( e.getSource() instanceof JComboBox ) {
			// We got a combo box event. But we have two combo boxes, so the only way
			// to tell them apart is to see what the new value is. If it is related to size,
			// then we know it was a size change. If it is related to color, then we know
			// it is a color change.
			
			JComboBox combobox = (JComboBox)e.getSource();
			String selectedItem = (String)combobox.getSelectedItem();
			
			Integer index;
			if( (index = GameResources.TRIANGLE_SIZES.get(selectedItem)) != null ) {
				// We picked a new size
				_triangleSize = index;
			} else if( (index = GameResources.TRIANGLE_COLORS.get(selectedItem)) != null ) {
				_triangleColor = index;
			}
			
			_triangleIcon.setText("");
			_triangleIcon.setIcon( GameResources.getInstance().getTriangleIcon( _triangleSize, _triangleColor ) );
			_triangleIcon.validate();
			
			notifyObservers( new GuessInputEvent() );
		}
	}
	
	public GameTriangle getTriangle() {
		return (_triangleSize == -1 || _triangleColor == -1) ? null : new GameTriangle(_triangleSize,_triangleColor);
	}
	
	public GameTriangleInputPanel() {
		// There is a center panel, and a bottom panel in this border layout.
		
		_observers = new ArrayList<GameInputObserver>();
		setLayout(new BorderLayout());
		
		_triangleIcon = new JLabel(GameEnvironment.EMPTY_TRIANGLE_TEXT);
		_triangleIcon.setHorizontalAlignment(JLabel.CENTER);
		
		// In the center panel is where our triangle icon will be. Not very complicated here
		JPanel centerPanel = new JPanel();
		centerPanel.add(_triangleIcon);
		
		// In the bottom panel will be our two drop-downs, for size and color.
		JPanel bottomPanel = new JPanel();
		String[] sizes = new String[GameResources.numSizes];
		String[] colors = new String[GameResources.numColors];
		GameResources.TRIANGLE_SIZES.keySet().toArray(sizes);
		GameResources.TRIANGLE_COLORS.keySet().toArray(colors);
		JComboBox<String> sizeInput = new JComboBox<String>( sizes );
		JComboBox<String> colorInput = new JComboBox<String>( colors );
		sizeInput.addActionListener(this);
		colorInput.addActionListener(this);
		
		bottomPanel.add(sizeInput);
		bottomPanel.add(colorInput);
		
		add(centerPanel,BorderLayout.CENTER);
		add(bottomPanel,BorderLayout.SOUTH);
		
		// We have a default triangle size & color, so make sure those are set.
		_triangleColor = GameResources.TRIANGLE_COLORS.get(GameEnvironment.DEFAULT_TRIANGLE_COLOR);
		colorInput.setSelectedItem(GameEnvironment.DEFAULT_TRIANGLE_COLOR);
		_triangleSize = GameResources.TRIANGLE_SIZES.get(GameEnvironment.DEFAULT_TRIANGLE_SIZE);
		sizeInput.setSelectedItem(GameEnvironment.DEFAULT_TRIANGLE_SIZE);
		
		// This is useful for making the input not look crowded.
		setPreferredSize( new java.awt.Dimension(170,170) );
		setBorder( BorderFactory.createLineBorder(Color.BLACK, 2));
		
		// The "Input" text just makes it more intuitive
		JLabel inputLabel = new JLabel("Input");
		inputLabel.setHorizontalAlignment(JLabel.CENTER);
		add(inputLabel,BorderLayout.NORTH);
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
		_observers.remove(observer);
	}
}
