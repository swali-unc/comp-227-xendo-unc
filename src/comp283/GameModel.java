package comp283;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author Syed & Alex
 * 
 * The model stores all of our game data. We don't do anything special with it.
 * We also contain all of the problems. The problems are created using anonymous classes.
 * See below to see how a problem is implemented.
 *
 */
public class GameModel {
	private Map<String,GameProblem> _problemSet;
	private GameTriangle[] _currentGuess;
	private GameProblem _currentProblem;
	
	public GameProblem getCurrentProblem() {
		return _currentProblem;
	}
	
	public void setCurrentProblem( GameProblem problem ) {
		_currentProblem = problem;
	}
	
	public GameTriangle[] getCurrentGuess() {
		return _currentGuess;
	}
	
	public void setCurrentGuess( GameTriangle[] triangles ) {
		_currentGuess = triangles;
	}
	
	public GameProblem getProblemFromIdentifier( String id ) {
		return _problemSet.get(id);
	}
	
	/**
	 * Each problem has an ID, and that ID is used in the drop-down list or
	 * really however the view wants to render it. But when the view requests a problem,
	 * they need to do it with an ID.
	 * 
	 * Here we just return the IDs of the loaded problems, in sorted order.
	 */
	public List<String> getProblemIdentifiers() {
		List<String> sortedProblemIDs = new ArrayList<String>();
		for( String s : _problemSet.keySet() ) {
			sortedProblemIDs.add(s);
		}
		
		Collections.sort(sortedProblemIDs);
		
		return sortedProblemIDs;
	}
	
	public GameModel() {
		_problemSet = new HashMap<String,GameProblem>();
		_currentGuess = null;
		_currentProblem = null;
		
		addExistentialProblems();
		addForAllProblems();
		addProblemSet1();
		addProblemSet2();
	}
	
	private void addProblemSet1() {
		// Problem 1.1: There exists a green
		_problemSet.put( "Problem 1.1", new GameProblem() {
			public boolean doesSetPass(GameTriangle triangles[]) {
				for( int i = 0; i < triangles.length; ++i ) {
					if( triangles[i].getColor() == GameResources.TRIANGLE_GREEN )
						return true;
				}
				
				return false;
			}
			
			public String getSolution() { return "$$\\exists_{x\\in S} isgreen(x)$$"; }
			
			public GameTriangle[][] getExamples() {
				return new GameTriangle[][] { {GameResources.smPink,GameResources.lgGreen,GameResources.smRed},
					{GameResources.smPink,GameResources.smViolet,GameResources.mdGreen},
					{GameResources.mdViolet, GameResources.mdBlue, GameResources.mdYellow},
					{GameResources.smPink, GameResources.mdPink, GameResources.lgBlue} };
			}
		});
		
		// Problem 1.2: All triangles are green
		_problemSet.put( "Problem 1.2", new GameProblem() {
			public boolean doesSetPass(GameTriangle triangles[]) {
				for( int i = 0; i < triangles.length; ++i ) {
					if( triangles[i].getColor() != GameResources.TRIANGLE_GREEN )
						return false;
				}
				
				return true;
			}
			
			public String getSolution() { return "$$\\forall_{x\\in S} isgreen(x)$$"; }
			
			public GameTriangle[][] getExamples() {
				return new GameTriangle[][] { {GameResources.smGreen,GameResources.lgGreen,GameResources.mdGreen},
					{GameResources.lgGreen,GameResources.lgGreen,GameResources.lgGreen},
					{GameResources.lgYellow, GameResources.smRed, GameResources.smYellow},
					{GameResources.mdViolet, GameResources.mdPink, GameResources.smBlue} };
			}
		});
		
		// Problem 1.3: All triangles are green and large
		_problemSet.put( "Problem 1.3", new GameProblem() {
			public boolean doesSetPass(GameTriangle triangles[]) {
				for( int i = 0; i < triangles.length; ++i ) {
					if( triangles[i].getColor() != GameResources.TRIANGLE_GREEN ||
						triangles[i].getSize() != GameResources.TRIANGLE_LARGE )
						return false;
				}
				
				return true;
			}
			
			public String getSolution() { return "$$\\forall_{x\\in S} isgreen(x)\\land islarge(x)$$"; }
			
			public GameTriangle[][] getExamples() {
				return new GameTriangle[][] { {GameResources.smGreen,GameResources.lgGreen,GameResources.mdGreen},
					{GameResources.lgGreen,GameResources.lgGreen,GameResources.lgGreen},
					{GameResources.lgYellow, GameResources.smRed, GameResources.smYellow},
					{GameResources.mdViolet, GameResources.mdPink, GameResources.smBlue} };
			}
		});
		
		// Problem 1.4: All green triangles are large
		_problemSet.put( "Problem 1.4", new GameProblem() {
			public boolean doesSetPass(GameTriangle triangles[]) {
				for( int i = 0; i < triangles.length; ++i ) {
					if( triangles[i].getColor() == GameResources.TRIANGLE_GREEN &&
						triangles[i].getSize() != GameResources.TRIANGLE_LARGE )
						return false;
				}
				
				return true;
			}
			
			public String getSolution() { return "$$\\forall_{x\\in S} isgreen(x)\\rightarrow islarge(x)$$"; }
			
			public GameTriangle[][] getExamples() {
				return new GameTriangle[][] { {GameResources.mdPink,GameResources.lgViolet,GameResources.lgRed},
					{GameResources.lgGreen,GameResources.lgBlue,GameResources.lgRed},
					{GameResources.lgYellow, GameResources.smRed, GameResources.smGreen},
					{GameResources.mdViolet, GameResources.mdGreen, GameResources.smBlue} };
			}
		});
		
		// Problem 1.5: There is a triangle smaller than every green
		_problemSet.put( "Problem 1.5", new GameProblem() {
			public boolean doesSetPass(GameTriangle triangles[]) {
				for( int i = 0; i < triangles.length; ++i ) {
					// Is triangle i smaller than every green?
					boolean bFoundViolation = false;
					for( int j = 0; j < triangles.length; ++j ) {
						if( triangles[j].getColor() == GameResources.TRIANGLE_GREEN &&
							triangles[i].getSize() >= triangles[j].getSize() ) {
							bFoundViolation = true;
							break;
						}
					}
					
					// Did we find a triangle that is indeed smaller than every green?
					// Note: we can be vacuously true for no green triangles
					if( !bFoundViolation )
						return true;
				}
				
				return false;
			}
			
			public String getSolution() { return "$$\\exists_{x\\in S}\\forall_{y\\in S} isgreen(y)\\rightarrow (size(y) > size(x))$$"; }
			
			public GameTriangle[][] getExamples() {
				return new GameTriangle[][] { {GameResources.mdPink,GameResources.lgViolet,GameResources.lgRed},
					{GameResources.lgRed,GameResources.mdBlue,GameResources.lgGreen},
					{GameResources.lgGreen, GameResources.mdGreen, GameResources.smGreen},
					{GameResources.mdGreen, GameResources.mdYellow, GameResources.lgGreen} };
			}
		});
		
		// Problem 1.6: There is a yellow triangle smaller than every green
		/* _problemSet.put( "Problem 1.6", new GameProblem() {
			public boolean doesSetPass(GameTriangle triangles[]) {
				for( int i = 0; i < triangles.length; ++i ) {
					// Is triangle i smaller than every green?
					// If we are, then we must also be yellow
					boolean bFoundViolation = false;
					for( int j = 0; j < triangles.length; ++j ) {
						if( triangles[j].getColor() == GameResources.TRIANGLE_GREEN
							&& triangles[i].getColor() != GameResources.TRIANGLE_YELLOW 
							&& triangles[i].getSize() >= triangles[j].getSize() ) {
							bFoundViolation = true;
							break;
						}
					}
					
					// Did we find a triangle that is indeed smaller than every green?
					// Note: we can be vacuously true for no green triangles
					if( !bFoundViolation )
						return true;
				}
				
				return false;
			}
			
			public String getSolution() { return "$$\\exists_{x\\in S}\\forall_{y\\in S} isgreen(y)\\rightarrow (isyellow(x)\\land size(y) > size(x))$$"; }
			
			public GameTriangle[][] getExamples() {
				return new GameTriangle[][] { {GameResources.mdYellow,GameResources.lgRed,GameResources.lgYellow},
					{GameResources.lgGreen,GameResources.mdYellow,GameResources.lgRed},
					{GameResources.lgGreen, GameResources.mdGreen, GameResources.smGreen},
					{GameResources.smViolet, GameResources.mdGreen, GameResources.lgGreen} };
			}
		}); */
		
		// Problem 1.7: There is a yellow triangle and it is smaller than every green
		_problemSet.put( "Problem 1.7", new GameProblem() {
			public boolean doesSetPass(GameTriangle triangles[]) {
				for( int i = 0; i < triangles.length; ++i ) {
					// Are we a yellow triangle?
					if( triangles[i].getColor() == GameResources.TRIANGLE_YELLOW ) {
						// Are we smaller than every green?
						boolean foundViolation = false;
						for( int j = 0; j < triangles.length; ++j ) {
							if( triangles[j].getColor() == GameResources.TRIANGLE_GREEN
								&& triangles[j].getSize() <= triangles[i].getSize() ) {
								foundViolation = true;
								break;
							}
						}
						
						// This yellow triangle is smaller than all greens
						if( !foundViolation )
							return true;
					}
				}
				
				return false;
			}
			
			public String getSolution() { return "$$\\exists_{x\\in S}\\forall_{y\\in S} isyellow(x)\\land (isgreen(y)\\rightarrow (size(x) < size(y)) )$$"; }
			
			public GameTriangle[][] getExamples() {
				return new GameTriangle[][] { {GameResources.mdYellow,GameResources.lgRed,GameResources.lgYellow},
					{GameResources.lgGreen,GameResources.mdYellow,GameResources.lgRed},
					{GameResources.lgGreen, GameResources.mdGreen, GameResources.smGreen},
					{GameResources.smViolet, GameResources.mdGreen, GameResources.lgGreen} };
			}
		});
	}
	
	private void addProblemSet2() {
		// Problem 2.1: There exists a yellow
		_problemSet.put( "Problem 2.1", new GameProblem() {
			public boolean doesSetPass(GameTriangle triangles[]) {
				for( int i = 0; i < triangles.length; ++i ) {
					if( triangles[i].getColor() == GameResources.TRIANGLE_YELLOW )
						return true;
				}
				
				return false;
			}
			
			public String getSolution() { return "$$\\exists_{x\\in S} isyellow(x)$$"; }
			
			public GameTriangle[][] getExamples() {
				return new GameTriangle[][] { {GameResources.smPink,GameResources.lgYellow,GameResources.smRed},
					{GameResources.smPink,GameResources.smYellow,GameResources.mdGreen},
					{GameResources.mdViolet, GameResources.mdBlue, GameResources.mdGreen},
					{GameResources.smPink, GameResources.mdPink, GameResources.lgBlue} };
			}
		});

		// Problem 2.2: There exists a yellow and a blue
		_problemSet.put( "Problem 2.2", new GameProblem() {
			public boolean doesSetPass(GameTriangle triangles[]) {
				boolean foundYellow = false;
				boolean foundBlue = false;
				
				for( int i = 0; i < triangles.length; ++i ) {
					if( triangles[i].getColor() == GameResources.TRIANGLE_YELLOW )
						foundYellow = true;
					else if( triangles[i].getColor() == GameResources.TRIANGLE_BLUE )
						foundBlue = true;
				}
				
				return foundYellow && foundBlue;
			}
			
			public String getSolution() { return "$$\\exists_{x,y\\in S} isyellow(x)\\land isblue(y)$$"; }
			
			public GameTriangle[][] getExamples() {
				return new GameTriangle[][] { {GameResources.smViolet,GameResources.smBlue,GameResources.mdYellow},
					{GameResources.smYellow,GameResources.smYellow,GameResources.smBlue},
					{GameResources.mdViolet, GameResources.mdYellow, GameResources.lgYellow},
					{GameResources.mdBlue, GameResources.smGreen, GameResources.lgPink} };
			}
		});
		
		// Problem 2.3: There exists a yellow and a blue that are the same size
		_problemSet.put( "Problem 2.3", new GameProblem() {
			public boolean doesSetPass(GameTriangle triangles[]) {
				
				for( int i = 0; i < triangles.length; ++i ) {
					// Found a yellow or blue?
					if( triangles[i].getColor() == GameResources.TRIANGLE_YELLOW ) {
						// Find a blue the same size as us
						for( int j = 0; j < triangles.length; ++j ) {
							if( triangles[j].getColor() == GameResources.TRIANGLE_BLUE
								&& triangles[j].getSize() == triangles[i].getSize() )
								return true;
						}
					}
					else if( triangles[i].getColor() == GameResources.TRIANGLE_BLUE ) {
						// Find a yellow the same size as us
						for( int j = 0; j < triangles.length; ++j ) {
							if( triangles[j].getColor() == GameResources.TRIANGLE_YELLOW
								&& triangles[j].getSize() == triangles[i].getSize() )
								return true;
						}
					}
				}
				
				return false;
			}
			
			public String getSolution() { return "$$\\exists_{x,y\\in S} isyellow(x)\\land isblue(y)\\land (size(x) = size(y))$$"; }
			
			public GameTriangle[][] getExamples() {
				return new GameTriangle[][] { {GameResources.smViolet,GameResources.mdBlue,GameResources.mdYellow},
					{GameResources.smYellow,GameResources.mdYellow,GameResources.smBlue},
					{GameResources.smYellow, GameResources.mdBlue, GameResources.lgYellow},
					{GameResources.mdBlue, GameResources.lgYellow, GameResources.smViolet} };
			}
		});
		
		// Problem 2.4: ALL yellow and blue triangles are the same size
		_problemSet.put( "Problem 2.4", new GameProblem() {
			public boolean doesSetPass(GameTriangle triangles[]) {
				
				for( int i = 0; i < triangles.length; ++i ) {
					// Found a yellow or blue?
					if( triangles[i].getColor() == GameResources.TRIANGLE_YELLOW ) {
						// Find a blue NOT same size as us
						for( int j = 0; j < triangles.length; ++j ) {
							if( triangles[j].getColor() == GameResources.TRIANGLE_BLUE
								&& triangles[j].getSize() != triangles[i].getSize() )
								return false;
						}
					}
					else if( triangles[i].getColor() == GameResources.TRIANGLE_BLUE ) {
						// Find a yellow NOT the same size as us
						for( int j = 0; j < triangles.length; ++j ) {
							if( triangles[j].getColor() == GameResources.TRIANGLE_YELLOW
								&& triangles[j].getSize() != triangles[i].getSize() )
								return false;
						}
					}
				}
				
				return true;
			}
			
			public String getSolution() { return "$$\\forall_{x,y\\in S} (isyellow(x)\\land isblue(y))\\rightarrow (size(x) = size(y))$$"; }
			
			public GameTriangle[][] getExamples() {
				return new GameTriangle[][] { {GameResources.lgYellow,GameResources.lgPink,GameResources.lgBlue},
					{GameResources.mdBlue,GameResources.lgRed,GameResources.mdYellow},
					{GameResources.smYellow,GameResources.mdYellow,GameResources.smBlue},
					{GameResources.mdBlue, GameResources.lgYellow, GameResources.smViolet} };
			}
		});
		
		// Problem 2.5: Every blue matches ONE yellow size
		_problemSet.put( "Problem 2.5", new GameProblem() {
			public boolean doesSetPass(GameTriangle triangles[]) {
				// Check for vacuously true no blues
				boolean blueFound = false;
				for( int i = 0; i < triangles.length; ++i ) {
					if( triangles[i].getColor() == GameResources.TRIANGLE_BLUE ) {
						blueFound = true;
						break;
					}
				}
				
				if( !blueFound )
					return true;
				
				// If there are blues, then find the one yellow triangle that is the same
				// size as all blues
				for( int i = 0; i < triangles.length; ++i ) {
					if( triangles[i].getColor() == GameResources.TRIANGLE_YELLOW ) {
						// Are we the same as all blues?
						boolean foundViolation = false;
						for( int j = 0; j < triangles.length; ++j ) {
							if( triangles[j].getColor() == GameResources.TRIANGLE_BLUE
								&& triangles[j].getSize() != triangles[i].getSize() ) {
								foundViolation = true;
								break;
							}
						}
						
						// We were the same size as all blues
						if( !foundViolation )
							return true;
					}
				}
				
				return false;
			}
			
			public String getSolution() { return "$$\\exists_{x\\in S}\\forall_{y\\in S} isblue(y)\\rightarrow (isyellow(x)\\land (size(y)=size(x)) )$$"; }
			
			public GameTriangle[][] getExamples() {
				return new GameTriangle[][] { {GameResources.lgYellow,GameResources.lgPink,GameResources.lgBlue},
					{GameResources.mdGreen,GameResources.lgRed,GameResources.mdYellow},
					{GameResources.smYellow,GameResources.mdYellow,GameResources.smBlue},
					{GameResources.mdBlue, GameResources.lgBlue, GameResources.mdYellow} };
			}
		});
		
		// Problem 2.6: Every blue has a matching yellow
		_problemSet.put( "Problem 2.6", new GameProblem() {
			public boolean doesSetPass(GameTriangle triangles[]) {
				for( int i = 0; i < triangles.length; ++i ) {
					// If we're blue, find a matching yellow
					if( triangles[i].getColor() == GameResources.TRIANGLE_BLUE ) {
						// We need to find a yellow the same size as us
						boolean bFound = false;
						for( int j = 0; j < triangles.length; ++j ) {
							if( triangles[j].getColor() == GameResources.TRIANGLE_YELLOW
								&& triangles[j].getSize() == triangles[i].getSize() ) {
								// Found our matching triangle
								bFound = true;
								break;
							}
						}
						
						// This blue does not have a matching yellow
						if( !bFound )
							return false;
					}
				}
				
				return true;
			}
			
			public String getSolution() { return "$$\\forall_{y\\in S}\\exists_{x\\in S} isblue(y)\\rightarrow (isyellow(x)\\land (size(y)=size(x)) )$$"; }
			
			public GameTriangle[][] getExamples() {
				return new GameTriangle[][] { {GameResources.lgYellow,GameResources.lgPink,GameResources.lgBlue},
					{GameResources.mdGreen,GameResources.lgRed,GameResources.mdYellow},
					{GameResources.smYellow,GameResources.mdYellow,GameResources.smBlue},
					{GameResources.mdBlue, GameResources.lgBlue, GameResources.mdYellow} };
			}
		});
	}
	
	private void addForAllProblems() {
		// Universal Problem 1: All triangles are pink
		_problemSet.put( "ForAll 1", new GameProblem() {
			public boolean doesSetPass(GameTriangle triangles[]) {
				for( int i = 0; i < triangles.length; ++i ) {
					if( triangles[i].getColor() != GameResources.TRIANGLE_PINK )
						return false;
				}
				
				return true;
			}
			
			public String getSolution() { return "$$\\forall_{x\\in S} ispink(x)$$"; }
			
			public GameTriangle[][] getExamples() {
				return new GameTriangle[][] { {GameResources.smPink,GameResources.lgPink,GameResources.mdPink},
					{GameResources.smPink,GameResources.smPink,GameResources.lgPink},
					{GameResources.mdViolet, GameResources.mdBlue, GameResources.mdGreen},
					{GameResources.smPink, GameResources.mdPink, GameResources.lgBlue} };
			}
		});
		
		// Universal Problem 2: All triangles are small
		_problemSet.put( "ForAll 2", new GameProblem() {
			public boolean doesSetPass(GameTriangle triangles[]) {
				for( int i = 0; i < triangles.length; ++i ) {
					if( triangles[i].getSize() != GameResources.TRIANGLE_SMALL )
						return false;
				}
				
				return true;
			}
			
			public String getSolution() { return "$$\\forall_{x\\in S} issmall(x)$$"; }
			
			public GameTriangle[][] getExamples() {
				return new GameTriangle[][] { {GameResources.smPink,GameResources.smBlue,GameResources.smGreen},
					{GameResources.smPink,GameResources.smPink,GameResources.smPink},
					{GameResources.smViolet, GameResources.mdBlue, GameResources.smGreen},
					{GameResources.smPink, GameResources.mdPink, GameResources.lgBlue} };
			}
		});
		
		// Universal Problem 3: All triangles are large and green
		_problemSet.put( "ForAll 3", new GameProblem() {
			public boolean doesSetPass(GameTriangle triangles[]) {
				for( int i = 0; i < triangles.length; ++i ) {
					if( triangles[i].getColor() != GameResources.TRIANGLE_GREEN
						|| triangles[i].getSize() != GameResources.TRIANGLE_LARGE )
						return false;
				}
				
				return true;
			}
			
			public String getSolution() { return "$$\\forall_{x\\in S} islarge(x)\\land isgreen(x)$$"; }
			
			public GameTriangle[][] getExamples() {
				return new GameTriangle[][] { {GameResources.lgGreen,GameResources.lgGreen,GameResources.lgGreen},
					{GameResources.smPink,GameResources.smPink,GameResources.smPink},
					{GameResources.smViolet, GameResources.mdBlue, GameResources.smGreen},
					{GameResources.mdGreen, GameResources.smGreen, GameResources.lgGreen} };
			}
		});
		
		// Universal Problem 4: All violet triangles are large
		_problemSet.put( "ForAll 4", new GameProblem() {
			public boolean doesSetPass(GameTriangle triangles[]) {
				for( int i = 0; i < triangles.length; ++i ) {
					if( triangles[i].getColor() == GameResources.TRIANGLE_VIOLET
						&& triangles[i].getSize() != GameResources.TRIANGLE_LARGE )
						return false;
				}
				
				return true;
			}
			
			public String getSolution() { return "$$\\forall_{x\\in S} isviolet(x)\\rightarrow islarge(x)$$"; }
			
			public GameTriangle[][] getExamples() {
				return new GameTriangle[][] { {GameResources.smPink,GameResources.lgViolet,GameResources.lgGreen},
					{GameResources.smPink,GameResources.mdBlue,GameResources.lgGreen},
					{GameResources.smViolet, GameResources.mdBlue, GameResources.smGreen},
					{GameResources.mdGreen, GameResources.smGreen, GameResources.mdViolet} };
			}
		});
		
		// Universal Problem 5: All violet triangles are not large
		_problemSet.put( "ForAll 5", new GameProblem() {
			public boolean doesSetPass(GameTriangle triangles[]) {
				for( int i = 0; i < triangles.length; ++i ) {
					if( triangles[i].getColor() == GameResources.TRIANGLE_VIOLET
						&& triangles[i].getSize() == GameResources.TRIANGLE_LARGE )
						return false;
				}
				
				return true;
			}
			
			// This intentionally modifies the logic from the previous problem just to get people thinking
			public String getSolution() { return "$$\\forall_{x\\in S} \\neg isviolet(x)\\lor \\neg islarge(x)$$"; }
			
			public GameTriangle[][] getExamples() {
				return new GameTriangle[][] { {GameResources.smPink,GameResources.mdViolet,GameResources.lgGreen},
					{GameResources.smPink,GameResources.mdBlue,GameResources.lgGreen},
					{GameResources.lgViolet, GameResources.mdBlue, GameResources.smGreen},
					{GameResources.mdGreen, GameResources.smGreen, GameResources.lgViolet} };
			}
		});
	}
	
	private void addExistentialProblems() {
		// Existential Problem 1: There exists a yellow triangle
		_problemSet.put( "Existential 1", new GameProblem() {
			public boolean doesSetPass(GameTriangle triangles[]) {
				for( int i = 0; i < triangles.length; ++i ) {
					if( triangles[i].getColor() == GameResources.TRIANGLE_YELLOW )
						return true;
				}
				
				return false;
			}
			
			public String getSolution() { return "$$\\exists_{x\\in S} isyellow(x)$$"; }
			
			public GameTriangle[][] getExamples() {
				return new GameTriangle[][] { {GameResources.smYellow,GameResources.lgGreen,GameResources.mdRed},
					{GameResources.smYellow,GameResources.smYellow,GameResources.mdYellow,GameResources.lgYellow},
					{GameResources.mdViolet, GameResources.mdBlue, GameResources.mdGreen},
					{GameResources.smPink, GameResources.mdPink, GameResources.lgPink} };
			}
		} );
		
		// Existential Problem 2: There exists a red triangle
		_problemSet.put( "Existential 2", new GameProblem() {
			public boolean doesSetPass(GameTriangle triangles[]) {
				for( int i = 0; i < triangles.length; ++i ) {
					if( triangles[i].getColor() == GameResources.TRIANGLE_RED )
						return true;
				}
				
				return false;
			}
			
			public String getSolution() { return "$$\\exists_{x\\in S} isred(x)$$"; }
			
			public GameTriangle[][] getExamples() {
				return new GameTriangle[][] { {GameResources.smRed,GameResources.mdBlue,GameResources.mdRed},
					{GameResources.mdYellow,GameResources.lgRed,GameResources.lgYellow},
					{GameResources.mdViolet, GameResources.mdBlue, GameResources.mdGreen},
					{GameResources.smBlue, GameResources.mdPink, GameResources.lgYellow} };
			}
		} );
		
		// Existential Problem 3: There exists a medium triangle
		_problemSet.put( "Existential 3", new GameProblem() {
			public boolean doesSetPass(GameTriangle triangles[]) {
				for( int i = 0; i < triangles.length; ++i ) {
					if( triangles[i].getSize() == GameResources.TRIANGLE_MEDIUM )
						return true;
				}
				
				return false;
			}
			
			public String getSolution() { return "$$\\exists_{x\\in S} ismedium(x)$$"; }
			
			public GameTriangle[][] getExamples() {
				return new GameTriangle[][] { {GameResources.smYellow,GameResources.lgGreen,GameResources.mdRed},
					{GameResources.smYellow,GameResources.mdYellow,GameResources.lgYellow},
					{GameResources.smViolet, GameResources.smBlue, GameResources.smGreen},
					{GameResources.smPink, GameResources.lgPink, GameResources.lgPink} };
			}
		} );
		
		// Existential Problem 4: There exists a blue triangle and a large triangle
		_problemSet.put( "Existential 4", new GameProblem() {
			public boolean doesSetPass(GameTriangle triangles[]) {
				boolean hasBlue = false;
				boolean hasLarge = false;
				
				for( int i = 0; i < triangles.length; ++i ) {
					if( triangles[i].getSize() == GameResources.TRIANGLE_LARGE )
						hasLarge = true;
					if( triangles[i].getColor() == GameResources.TRIANGLE_BLUE )
						hasBlue = true;
				}
				
				return hasLarge && hasBlue;
			}
			
			public String getSolution() { return "$$\\exists_{x,y\\in S} isblue(x)\\land islarge(y)$$"; }
			
			public GameTriangle[][] getExamples() {
				return new GameTriangle[][] { {GameResources.smYellow,GameResources.lgGreen,GameResources.mdBlue},
					{GameResources.smBlue,GameResources.mdYellow,GameResources.lgYellow},
					{GameResources.mdViolet, GameResources.mdBlue, GameResources.mdGreen},
					{GameResources.smPink, GameResources.mdPink, GameResources.lgPink} };
			}
		} );
	}
}