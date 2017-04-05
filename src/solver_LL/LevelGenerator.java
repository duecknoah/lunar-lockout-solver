package solver_LL;

import java.util.ArrayList;

// Will generate a random level that can be done by using the solver to test it
public class LevelGenerator {
	private Solution solution = new Solution();
	private Board board = new Board();
	private int totalBots; // number of bots in the level
	private int minSteps = 5; // minimum amount of steps in a board
	private int maxSteps = 10; // max steps in a board
	
	public LevelGenerator(int totalBots, int minSteps, int maxSteps) {
		this.minSteps = minSteps;
		this.maxSteps = maxSteps;
		this.totalBots = totalBots;
	}
	
	public LevelGenerator(int totalBots) {
		this.totalBots = totalBots;
	}
	
	public LevelGenerator() {}
	
	// Generates a random level, stores it in board
	public void generate() {
		Board genBoard = null;
		Solver solver = new Solver();
		Solution sol = null;
		boolean foundSol = false;
		
		System.out.println("Generating Solveable level:\nTotal bots: " + 
				totalBots + "\n" + "Minimum Steps: " + minSteps + "\nMaximum Steps: " + maxSteps + "\n"
				+ "...");
		
		// Keep looping and generating 
		// until found a random solution
		while (!foundSol) {
			genBoard = genRandBoard();
			sol = solver.solve(genBoard, maxSteps, false);
			// If solution found, break out of loop!
			if (sol != null) {
				if (sol.getTotalMoveCount() >= minSteps && sol.getTotalMoveCount() <= maxSteps) {
					foundSol = true;
				}
			}
		}
		board = genBoard;
		solution = sol;
	}
	
	// Returns a random generated board, however a solution is likely
	// not possible as no checks are made in terms of a solution. This
	// is used in generate()
	public Board genRandBoard() {
		ArrayList<Robot> r = new ArrayList<Robot>();
		for (int i = 0; i < totalBots; i ++) {
			boolean foundFreePos = false; // found free pos for robot?
			boolean isPlayer = (i == 0) ? true : false; // make robot player if on index 0
			Robot rNew = new Robot(0, 0, isPlayer, ""); // new robot

			rNew.setColor(Robot.intToColor(i));
			
			// Search for a random free position
			while (!foundFreePos) {
				foundFreePos = true;
				// Get a random coordinate point between 1-xSize and 1-ySize
				int randX = 1, randY = 1;
				randX = (int) (Math.random() * (board.getXSize() - 1) + 1);
				randY = (int) (Math.random() * (board.getYSize() - 1) + 1);
				rNew.getPos().setPos(randX, randY);
				// Check that the player does not start on the finish
				if (rNew.isPlayer()) {
					if (board.positionOnFin(rNew.getPos())) {
						foundFreePos = false;
						continue; // restart loop
					}
				}
				// Now check for collisions with other robots
				for (Robot rOther : r) {
					// If place is taken by another robot, look for another pos 
					if (rNew.getPos().positionMeeting(rOther.getPos())) {
						foundFreePos = false;
					}
				}
			}
			// Add newly placed robot into array
			r.add(rNew);
		}
		// Return a new board with robots placed in it
		return new Board(r);
	}
	
	public void setSolution(Solution solution) {
		this.solution = solution;
	}
	
	public void setBoard(Board board) {
		this.board = board;
	}
	
	public Solution getSolution() {
		return solution;
	}

	public int getTotalBots() {
		return totalBots;
	}

	public void setTotalBots(int totalBots) {
		this.totalBots = totalBots;
	}

	public int getMinSteps() {
		return minSteps;
	}

	public void setMinSteps(int minSteps) {
		this.minSteps = minSteps;
	}

	public int getMaxSteps() {
		return maxSteps;
	}

	public void setMaxSteps(int maxSteps) {
		this.maxSteps = maxSteps;
	}

	public Board getBoard() {
		return board;
	}
}
