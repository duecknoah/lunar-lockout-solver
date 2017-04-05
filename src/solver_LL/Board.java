package solver_LL;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.lang.IllegalArgumentException;

// The game board for all the robots
public class Board {
	public static final int xSize = 5; // the xSize/width of the board
	public static final int ySize = 5; // the ySize/height of the board
	public static final BoardPos fin = new BoardPos(3, 3); // the finishing coordinates for the player to be to win
	private ArrayList<Robot> robots = new ArrayList<>();
	
	Board(ArrayList<Robot> robots) {
		this.robots = robots;
		validateRobotPositions();
	}
	
	// Copy constructor
	Board(Board other) {
		// Add copies of others robots to this
		for (Robot rCopy : other.getRobots()) {
			robots.add(new Robot(rCopy));
		}
	}
	
	Board() {};
	
	// Returns the robots on the board
	public ArrayList<Robot> getRobots() {
		return robots;
	}
	
	// Attempts to add the robot to the board
	public void addRobot(Robot r) {
		robots.add(r);
		validateRobotPositions();
	}
	
	// Attempts to remove an existing robot from
	// the board
	public void removeRobot(Robot r) {
		getRobots().remove(r);
	}
	
	public int getXSize() {
		return xSize;
	}

	public int getYSize() {
		return ySize;
	}

	public void validateRobotPositions() {
		ArrayList<BoardPos> usedPos = new ArrayList<>();
		for (Robot r : robots) {
			for (BoardPos u : usedPos) {
				// If this current robot is in another used robots position
				if (r.getPos().positionMeeting(u)) {
					// undo the overlap before throwing the error
					robots.remove(r);
					throw new IllegalArgumentException("Invalid pos! Robots are overlapping!");
				}
			}
			// Else if position is not used, add it to the used positions array
			usedPos.add(r.getPos());
		}
	}
	
	// Returns true if the specified position is on the board
	// More info on board positions in boardSketch.txt
	public boolean positionOnBoard(BoardPos pos) {
		if (pos.getX() > 0 && pos.getX() <= xSize
		|| pos.getY() > 0 && pos.getY() <= ySize) {
				return true;
		}
		return false;
	}
	
	// Returns true if BoardPos specified is on the fin spot (finish spot)
	// Returns false otherwise
	public boolean positionOnFin(BoardPos pos) {
		if (pos.positionMeeting(fin)) {
			return true;
		}
		return false;
	}
	
	// Is the specified robot in this board able to move in the specified direction?
	// If so, return the BoardPos of where that move would lead you, else return null if no moves found
	// Lunar lockout works by checking if this robot will collide with another robot when
	// going this direction. If it doesn't, than it is not able to move that direction.
	// direction codes:
	// 0 - UP
	// 1 - RIGHT
	// 2 - DOWN
	// 3 - LEFT
	// RETURNS BoardPos of position if move was made
	// RETURNS null if move not possible or pointless
	public BoardPos ableMove(Robot r, int dir) {
		if (!robots.contains(r)) {
			throw new IllegalArgumentException("The robot specified does not exist on this gameboard!");
		}
		BoardPos posChecker = new BoardPos(r.getPos());
		
		switch (dir) {
			case 0: // Up
				// Don't check own position, so check one step ahead
				//posChecker.setY(posChecker.getY() - 1);
				// check until edge of board for another robot
				for (int i = posChecker.getY() - 1; i > 0; i --) {
					posChecker.setY(i);
					for (Robot rOther : robots) {
						// Don't include self in check
						if (rOther == r)
							continue;
						// If meeting position checker found a robot at its position
						if (posChecker.positionMeeting(rOther.getPos())) {
							// If the robot found is not directly touching this robot already
							if (r.getPos().distTo(rOther.getPos()) > 1) {
								// set the position to goto to be one before the detected
								// robot
								posChecker.setY(posChecker.getY() + 1);
								// Lastly check if the robot was not just at this position, this is
								// done to prevent looping back and forth between two points as a beginning 
								// of a solution
								if (!r.getPosPrev().positionMeeting(posChecker)) {
									return posChecker; // return coordinates of here
								}
								else {
									return null;
								}
							}
							else {
								return null; // no move possible in this direction
							}
						}
					}
				}
			return null;
			case 1: // right
				// Don't check own position, so check one step ahead
				//posChecker.setX(posChecker.getX() + 1);
				// check until edge of board for another robot
				
				for (int i = posChecker.getX() + 1; i <= xSize; i ++) {
					posChecker.setX(i);
					for (Robot rOther : robots) {
						// Don't include self in check
						if (rOther == r)
							continue;
						// If meeting position checker found a robot at its position
						if (posChecker.positionMeeting(rOther.getPos())) {
							// If the robot found is not directly touching this robot already
							if (r.getPos().distTo(rOther.getPos()) > 1) {
								// set the position to goto to be one before the detected
								// robot
								posChecker.setX(posChecker.getX() - 1);
								// Lastly check if the robot was not just at this position, this is
								// done to prevent looping back and forth between two points as a beginning 
								// of a solution
								if (!r.getPosPrev().positionMeeting(posChecker)) {
									return posChecker; // return coordinates of here
								}
								else {
									return null;
								}
							}
							else {
								return null; // no move possible in this direction
							}
						}
					}
				}
			return null;
			case 2: // down
				// Don't check own position, so check one step ahead
				//posChecker.setY(posChecker.getY() + 1);
				// check until edge of board for another robot
				for (int i = posChecker.getY() + 1; i <= ySize; i ++) {
					posChecker.setY(i);
					for (Robot rOther : robots) {
						// Don't include self in check
						if (rOther == r)
							continue;
						// If meeting position checker found a robot at its position
						if (posChecker.positionMeeting(rOther.getPos())) {
							// If the robot found is not directly touching this robot already
							if (r.getPos().distTo(rOther.getPos()) > 1) {
								// set the position to goto to be one before the detected
								// robot
								posChecker.setY(posChecker.getY() - 1);
								// Lastly check if the robot was not just at this position, this is
								// done to prevent looping back and forth between two points as a beginning 
								// of a solution
								if (!r.getPosPrev().positionMeeting(posChecker)) {
									return posChecker; // return coordinates of here
								}
								else {
									return null;
								}
							}
							else {
								return null; // no move possible in this direction
							}
						}
					}
				}
			return null;
			case 3: // left
				// Don't check own position, so check one step ahead
				//posChecker.setX(posChecker.getX() - 1);
				// check until edge of board for another robot
				for (int i = posChecker.getX() - 1; i > 0; i --) {
					posChecker.setX(i);
					for (Robot rOther : robots) {
						// Don't include self in check
						if (rOther == r)
							continue;
						// If meeting position checker found a robot at its position
						if (posChecker.positionMeeting(rOther.getPos())) {
							// If the robot found is not directly touching this robot already
							if (r.getPos().distTo(rOther.getPos()) > 1) {
								// set the position to goto to be one before the detected
								// robot
								posChecker.setX(posChecker.getX() + 1);
								// Lastly check if the robot was not just at this position, this is
								// done to prevent looping back and forth between two points as a beginning 
								// of a solution
								if (!r.getPosPrev().positionMeeting(posChecker)) {
									return posChecker; // return coordinates of here
								}
								else {
									return null;
								}
							}
							else {
								return null; // no move possible in this direction
							}
						}
					}
				}
			return null;
			default: throw new IllegalArgumentException("Illegal direction: " + dir + ", must be a value between and including 0-4");
		}
	}
	
	// Returns true if the player completed the level
	// The player completes the level by being at the center position
	// (fin)
	public boolean isComplete() {
		for (Robot r : robots) {
			if (r.isPlayer()) {
				if (r.getPos().positionMeeting(fin)) {
					return true;
				}
			}
		}
		return false;
	}
	
	/* Returns the difference between two boards.
	 *  The difference will always be either one 
	 *   Robot that moved, or none moved:
	 *   
	 *   Returns the index of the Robot that moved if one did 
	 *   Returns -1 if no robots moved
	 *   
	 *   Note: That there will be exceptions thrown if the two
	 *   boards have a different amount of robots or the board
	 *   sizes are different
	*/
	public int getBoardDif(Board other) {
		// First check if the two board sizes are different
		if (this.getRobots().size() != other.getRobots().size()) {
			throw new IllegalArgumentException("The two boards have a different amount of robots! They be equal!");
		}
		// Next check that the two boards are the same size
		if (this.getXSize() != other.getXSize()
			&& this.getYSize() != other.getYSize()) {
			throw new IllegalArgumentException("The two boards have different sizes! They must be equal!");
		}
		
		// Search through both of the boards for differences
		// in the robot positions
		for (int i = 0; i < this.getRobots().size(); i ++) {
			Robot r = this.getRobots().get(i);
			Robot rOther = other.getRobots().get(i);
			
			// If the two places are NOT meeting, difference was found
			if (!r.getPos().positionMeeting(rOther.getPos())) {
				return i;
			}
		}
		// No difference found
		return -1;
	}
	
	public void printBoard() {
		System.out.println(getBoardStringData());
	}
	
	public String getBoardStringData() {
		String strData = "";
		for (int i = 0; i < robots.size(); i ++) {
			Robot r = robots.get(i);
			strData += (r.isPlayer()) ? "Player Robot" : "Robot";
			strData += " (" + r.getColor() + ") at position (" + r.getPos().getX() + ", " + r.getPos().getY() + ")";
			strData += '\n';
		}
		return strData;
	}
	
	/* Converts board data to JSON, note that many variables are not
	 *  included (ex. xSize, ySize, and fin (final position to win).
	 *  the reason of this is because the game would be changed if these
	 *  were changed. However, if you want to mess around with it, you can
	 *  simply change the code to do so :)
	 */
	
	public JSONArray toJSON() {
		JSONArray robotData = new JSONArray();
		// Put robot JSON objects in a separate JSONArray
		for (int i = 0; i < getRobots().size(); i ++) {
			Robot r = getRobots().get(i);
			robotData.add(r.toJSON());
		}
		return robotData;
	}
	
	// Parses json data and inputs it into the boards variables
	public void parseJSON(JSONArray data) {
		// get robot JSON data in JSONArray and set that data for the robots
		for (int i = 0; i < data.size(); i ++) {
			Robot r = new Robot();
			r.parseJSON((JSONObject) data.get(i));
			robots.add(r);
		}
	}
}
