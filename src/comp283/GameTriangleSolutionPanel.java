package comp283;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * 
 * @author Syed & Alex
 * 
 * This is the solution panel, so this is used for examples primarily. We render a set of triangles
 * and also a string on whether this set passes the xendo nature law or not.
 *
 */
public class GameTriangleSolutionPanel extends JPanel {
	public GameTriangleSolutionPanel( GameTriangle triangles[], GameProblem problem ) {
		setLayout(new FlowLayout()); // Simple flow layout
		
		GameResources res = GameResources.getInstance();
		
		// Add triangle icon labels for every triangle
		for( int i = 0; i < triangles.length; ++i ) {
			JLabel icon_label = new JLabel();
			icon_label.setHorizontalAlignment(JLabel.CENTER);
			icon_label.setIcon(res.getTriangleIcon(triangles[i]));
			icon_label.setHorizontalTextPosition(JLabel.CENTER);
			icon_label.setVerticalTextPosition(JLabel.BOTTOM);
			//icon_label.setFont(GameEnvironment.OUTPUT_FONT);
			
			String[] sizeLabels = {"S","M","L"};
			icon_label.setText(sizeLabels[triangles[i].getSize()]);
			
			add(icon_label);
		}
		
		// And finally add the answer all the way on the right.
		JLabel answer;
		if( problem.doesSetPass(triangles) ) {
			answer = new JLabel(GameEnvironment.VALID_GUESS_TEXT, JLabel.CENTER);
			answer.setForeground(GameEnvironment.VALID_GUESS_COLOR);
		} else {
			answer = new JLabel(GameEnvironment.INVALID_GUESS_TEXT, JLabel.CENTER);
			answer.setForeground(GameEnvironment.INVALID_GUESS_COLOR);
		}
		answer.setFont(GameEnvironment.OUTPUT_FONT);
		add(answer);
	}
}
