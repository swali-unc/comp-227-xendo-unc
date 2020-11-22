package comp283;

/**
 * 
 * @author Syed & Alex
 * 
 * A GameTriangle is pretty simple, it has a size and a color.
 * 
 * If the object is created successfully,
 * then we know the object has valid values, and thus it reduces error checking by just using a GameTriangle
 * object.
 *
 */
public class GameTriangle {
	private int _size;
	private int _color;
	
	public GameTriangle( int size, int color ) throws IllegalArgumentException {
		if( size >= GameResources.numSizes )
			throw new IllegalArgumentException(String.format("Specified size was invalid: %d", size));
		if( color >= GameResources.numColors )
			throw new IllegalArgumentException(String.format("Specified color was invalid: %d", color));
		this._size = size;
		this._color = color;
	}
	
	public int getSize() {
		return _size;
	}
	
	public int getColor() {
		return _color;
	}
}
