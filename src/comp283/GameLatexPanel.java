package comp283;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;

/**
 * 
 * @author Syed & Alex
 * 
 * This panel renders latex using jlatexmath
 *
 */
public class GameLatexPanel extends JPanel {
	private String _latexString;
	private BufferedImage _image; // This will be the image of the latex, precalculated prior to painting
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.drawImage(_image, 0, 0, null);
	}
	
	/**
	 * Will convert a string to a latex image for rendering.
	 */
	public void setString( String str ) {
		_latexString = str;
		
		// Grab the formula and icon
		TeXFormula formula = new TeXFormula(_latexString);
		TeXIcon icon = formula.createTeXIcon(TeXConstants.STYLE_DISPLAY, 20);
		
		// Give it some insets so it isnt' in the corner.
		icon.setInsets(new Insets(5,5,5,5));
		
		// Create our image, and store it for when we need to paint.
		BufferedImage image = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = image.createGraphics();
		icon.paintIcon(this, g, 0, 0);
		
		_image = image;
		
		repaint();
	}
	
	// We need a string!
	public GameLatexPanel( String text ) {
		setString(text);
	}
}
