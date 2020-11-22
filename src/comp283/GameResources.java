package comp283;

import java.awt.Image;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

/**
 * 
 * @author Syed & Alex
 * 
 * GameResources are pre-loaded assets that can be used easily. There are also many constants
 * here that allow one to code to the assets rather than hard-coding, or lots of intense querying.
 *
 */
public class GameResources {
	// Number of sizes, (SMALL must be lowest value)
	public static final int numSizes = 3;
	public static final int TRIANGLE_SMALL = 0;
	public static final int TRIANGLE_MEDIUM = 1;
	public static final int TRIANGLE_LARGE = 2;
	
	// Number of colors, the values don't mean much
	public static final int numColors = 6;
	public static final int TRIANGLE_BLUE = 0;
	public static final int TRIANGLE_GREEN = 1;
	public static final int TRIANGLE_PINK = 2;
	public static final int TRIANGLE_RED = 3;
	public static final int TRIANGLE_VIOLET = 4;
	public static final int TRIANGLE_YELLOW = 5;
	
	// Converts the string to a size value.
	public static final Map<String,Integer> TRIANGLE_SIZES;
	static {
		Map<String,Integer> sizemap = new HashMap<String,Integer>();
		sizemap.put("Small", TRIANGLE_SMALL);
		sizemap.put("Medium", TRIANGLE_MEDIUM);
		sizemap.put("Large", TRIANGLE_LARGE);
		TRIANGLE_SIZES = Collections.unmodifiableMap(sizemap);
	}
	
	// Converts the string to a color value.
	public static final Map<String,Integer> TRIANGLE_COLORS;
	static {
		Map<String,Integer> colormap = new HashMap<String,Integer>();
		colormap.put("Blue",TRIANGLE_BLUE);
		colormap.put("Green",TRIANGLE_GREEN);
		colormap.put("Pink",TRIANGLE_PINK);
		colormap.put("Red",TRIANGLE_RED);
		colormap.put("Violet",TRIANGLE_VIOLET);
		colormap.put("Yellow",TRIANGLE_YELLOW);
		TRIANGLE_COLORS = Collections.unmodifiableMap(colormap);
	}

	// These variables just make it easier to reference triangles in our problem sets
	public static final GameTriangle smBlue = new GameTriangle(GameResources.TRIANGLE_SMALL, GameResources.TRIANGLE_BLUE);
	public static final GameTriangle smGreen = new GameTriangle(GameResources.TRIANGLE_SMALL, GameResources.TRIANGLE_GREEN);
	public static final GameTriangle smPink = new GameTriangle(GameResources.TRIANGLE_SMALL, GameResources.TRIANGLE_PINK);
	public static final GameTriangle smRed = new GameTriangle(GameResources.TRIANGLE_SMALL, GameResources.TRIANGLE_RED);
	public static final GameTriangle smViolet = new GameTriangle(GameResources.TRIANGLE_SMALL, GameResources.TRIANGLE_VIOLET);
	public static final GameTriangle smYellow = new GameTriangle(GameResources.TRIANGLE_SMALL, GameResources.TRIANGLE_YELLOW);
	public static final GameTriangle mdBlue = new GameTriangle(GameResources.TRIANGLE_MEDIUM, GameResources.TRIANGLE_BLUE);
	public static final GameTriangle mdGreen = new GameTriangle(GameResources.TRIANGLE_MEDIUM, GameResources.TRIANGLE_GREEN);
	public static final GameTriangle mdPink = new GameTriangle(GameResources.TRIANGLE_MEDIUM, GameResources.TRIANGLE_PINK);
	public static final GameTriangle mdRed = new GameTriangle(GameResources.TRIANGLE_MEDIUM, GameResources.TRIANGLE_RED);
	public static final GameTriangle mdViolet = new GameTriangle(GameResources.TRIANGLE_MEDIUM, GameResources.TRIANGLE_VIOLET);
	public static final GameTriangle mdYellow = new GameTriangle(GameResources.TRIANGLE_MEDIUM, GameResources.TRIANGLE_YELLOW);
	public static final GameTriangle lgBlue = new GameTriangle(GameResources.TRIANGLE_LARGE, GameResources.TRIANGLE_BLUE);
	public static final GameTriangle lgGreen = new GameTriangle(GameResources.TRIANGLE_LARGE, GameResources.TRIANGLE_GREEN);
	public static final GameTriangle lgPink = new GameTriangle(GameResources.TRIANGLE_LARGE, GameResources.TRIANGLE_PINK);
	public static final GameTriangle lgRed = new GameTriangle(GameResources.TRIANGLE_LARGE, GameResources.TRIANGLE_RED);
	public static final GameTriangle lgViolet = new GameTriangle(GameResources.TRIANGLE_LARGE, GameResources.TRIANGLE_VIOLET);
	public static final GameTriangle lgYellow = new GameTriangle(GameResources.TRIANGLE_LARGE, GameResources.TRIANGLE_YELLOW);
	
	private static GameResources _instance = null;
	private ImageIcon _triangles[][];
	
	/**
	 * Returns the requested ImageIcon for the specified triangle.
	 * If the parameters are invalid, null is returned.
	 * 
	 * @param size
	 * @param colorCode
	 * @return
	 */
	public ImageIcon getTriangleIcon( int size, int colorCode ) {
		if( size < 0 || size >= numSizes ) return null;
		if( colorCode < 0 || colorCode >= numColors ) return null;
		return _triangles[size][colorCode];
	}
	
	/**
	 * Returns the requested ImageIcon for the specified triangle.
	 * If the passed object is null, then null is returned as well.
	 * 
	 * @param triangle
	 * @return
	 */
	public ImageIcon getTriangleIcon( GameTriangle triangle ) {
		// You get what you deserve
		if( triangle == null ) return null;
		
		// Here we do not need to check the size and color because
		// it is already checked by the GameTriangle object.
		return _triangles[triangle.getSize()][triangle.getColor()];
	}
	
	private GameResources() {
		_triangles = new ImageIcon[numSizes][numColors];
		
		int w = 50;
		int h = 60;
		for( int i = 0; i < numSizes; ++i ) {
			_triangles[i][TRIANGLE_BLUE] = getScaledIcon( "bt.png", w, h );
			_triangles[i][TRIANGLE_GREEN] = getScaledIcon( "gt.png", w, h );
			_triangles[i][TRIANGLE_PINK] = getScaledIcon( "pt.png", w, h );
			_triangles[i][TRIANGLE_RED] = getScaledIcon( "rt.png", w, h );
			_triangles[i][TRIANGLE_VIOLET] = getScaledIcon( "vt.png", w, h );
			_triangles[i][TRIANGLE_YELLOW] = getScaledIcon( "yt.png", w, h );
			
			w += 5;
			h += 26;
		}
	}
	
	public static GameResources getInstance() {
		if( _instance == null )
			_instance = new GameResources();
		return _instance;
	}
	
	/**
	 * Sometimes we don't just want an asset, but we want an asset of a specific size.
	 * So we can just scale the icon to the requested size rather than having duplicate assets.
	 * It's a time-space tradeoff.
	 */
	private ImageIcon getScaledIcon( String url, int w, int h  ) {
		ImageIcon ic = new ImageIcon( this.getClass().getResource(url) );
		Image img = ic.getImage();
		Image newimg = img.getScaledInstance(w, h, Image.SCALE_SMOOTH);
		return new ImageIcon(newimg);
	}
}

