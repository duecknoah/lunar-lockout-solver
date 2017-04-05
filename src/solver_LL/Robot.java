package solver_LL;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Robot {
	private BoardPos pos; // position on the board
	private BoardPos posPrev; // the position previous on the board, used to prevent repeating moves
	private String color; // color of the robot
	private boolean isPlayer; // is this robot the player robot?
	
	Robot(int x, int y, boolean isPlayer, String color) {
		this.pos = new BoardPos(x, y);
		this.posPrev = new BoardPos(x, y);
		this.isPlayer = isPlayer;
		this.color = color;
	}
	
	Robot(BoardPos pos, boolean isPlayer, String color) {
		this.pos = pos;
		this.isPlayer = isPlayer;
		this.color = color;
	}
	
	Robot(int x, int y, String color) {
		this.pos = new BoardPos(x, y);
		this.isPlayer = false;
		this.color = color;
	}
	
	Robot(BoardPos pos, String color) {
		this.pos = pos;
		isPlayer = false;
		this.color = color;
	}
	
	// Copy constructor
	Robot(Robot other) {
		// copy board positions
		this.pos = new BoardPos(other.getPos());
		this.posPrev = new BoardPos(other.getPosPrev());
		// and other data
		this.color = other.getColor();
		this.isPlayer = other.isPlayer();
	}
	
	Robot() {
		this.pos = new BoardPos();
		this.posPrev = new BoardPos();
	}
	
	public BoardPos getPos() {
		return pos;
	}

	public BoardPos getPosPrev() {
		return pos;
	}
	
	public void setPos(BoardPos pos) { 
		this.posPrev = new BoardPos(pos); // copy pos before change
		this.pos = pos;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public boolean isPlayer() {
		return isPlayer;
	}

	public void setPlayer(boolean isPlayer) {
		this.isPlayer = isPlayer;
	}
	
	public JSONObject toJSON() {
		JSONObject data = new JSONObject();
		data.put("pos", pos.toJSON());
		data.put("posPrev", getPosPrev().toJSON());
		data.put("color", getColor());
		data.put("isPlayer", isPlayer());
		return data;
	}
	
	// Parses json data and inputs it into the boards variables
	public void parseJSON(JSONObject data) {
		pos.parseJSON((JSONObject) data.get("pos"));
		posPrev.parseJSON((JSONObject) data.get("posPrev"));
		setColor((String) data.get("color"));
		setPlayer((boolean) data.get("isPlayer"));
	}
	
}
