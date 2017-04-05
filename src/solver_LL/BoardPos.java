package solver_LL;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import static java.lang.Math.toIntExact;

// Used as a class to combine x and y integer positions for anything on the board
public class BoardPos {
	private int x;
	private int y;
	
	BoardPos (int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	// Copy constructor
	BoardPos (BoardPos other) {
		this.x = other.getX();
		this.y = other.getY();
	}
	
	BoardPos () {
		this.x = 0;
		this.y = 0;
	}
	
	// Sets the position of this
	public void setPos(BoardPos pos) {
		this.x = pos.x;
		this.y = pos.y;
	}
	
	// Sets the position of this using two integers for x and y
	public void setPos(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	// Checks if this position equals another position on the board
	// returns true if so
	// returns false if not
	public boolean positionMeeting(BoardPos otherPos) {
		if (otherPos.x == this.x && otherPos.y == this.y) {
			return true;
		}
		return false;
	}
	
	// Checks to see if the specified position is on the
	// board
	public boolean isOnBoard(BoardPos pos) {
		if (pos.getX() > 0 && pos.getX() <= Board.xSize
				&& pos.getY() > 0 && pos.getY() <= Board.ySize) {
			return true;
		}
		return false;
	}
	
	// returns the distance to another position from this position
	public int distTo(BoardPos otherPos) {
		return Math.abs((x - otherPos.getX()) + (y - otherPos.getY()));
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public JSONObject toJSON() {
		JSONObject data = new JSONObject();
		data.put("x", getX());
		data.put("y", getY());
		return data;
	}
	
	// Parses json data and inputs it into the boards variables
	public void parseJSON(JSONObject data) {
		setX(toIntExact((long) data.get("x")));
		setY(toIntExact((long) data.get("y")));
	}
}
