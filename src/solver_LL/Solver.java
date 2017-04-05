package solver_LL;

public class Solver {	
	Solver() {};
	
	// initiates the private solve solution method. This is done
	// As the int stepNum should not be tinkered with and should
	// always intially be 0.
	// Returns Solution if solution found
	// Returns null otherwise
	public Solution solve(Board board, int maxSteps, boolean doPrintActions) {
		if (doPrintActions) {
			System.out.println("Solving a board of " + board.getRobots().size() + " robots");
			System.out.println("Max Steps: " + maxSteps);
			System.out.println("...");
		}
		int i = 1;
		Solution finalSol = null;
		// This is done to prevent using repeat moves to eventually get
		// the end solution. Doing this finds the minimum amount of moves
		// required to complete the level
		while (finalSol == null && i <= maxSteps) {
			finalSol = solve(board, 0, i);
			if (finalSol != null) {
				if (doPrintActions) {
					System.out.println(finalSol.getSolutionString());
				}
				return finalSol;
			}
			i ++;
		}
		// If still no solution
		if (doPrintActions) {
			System.out.println("No solution!");
		}
		return finalSol; // null
	}
	
	// Returns the solution using recursion
	// Pseudo code can be found
	private Solution solve(Board board, int stepNum, int maxSteps) {
		// Don't search for more than the total amount of steps allowed
		if (stepNum > maxSteps) {
			return null;
		}
		Solution sol = null;
		
		for (Robot r : board.getRobots()) {
			// First check if Solution was found!
			// We want to see if a solution was already passed back to us
			// in this recursive method, if has, then sol would not be null
			if (sol == null) {
				if (board.isComplete()) {
					// Found solution!!
					sol = new Solution();
					sol.prependToSolution(board);
					return sol;
				}
			}
			else {
				if (sol.getBoardStates().getFirst().isComplete()) {
					sol.prependToSolution(board);
					return sol;
				}
			}
			// if possible move for robot i that does not undo last move (would create a loop otherwise) 
			// we want to search up, right, down, left
			for (int iDir = 0; iDir < 4; iDir ++) {
				//System.out.println("testing possible moves (Robot: " + r.toString() + ", " + "dir: " + iDir + ")");
				BoardPos move = board.ableMove(r, iDir);
				if (move != null) {
					Board boardCopy = new Board(board);
					int rToMoveIndex = board.getRobots().indexOf(r);
					Robot rCopy = boardCopy.getRobots().get(rToMoveIndex);
					rCopy.setPos(move);
					//System.out.println("Move detected! Moved Robot[" + rToMoveIndex + "](" + rCopy.getColor() + ") to: " + move.getX() + ", " + move.getY() + " via dir: " + iDir);
					Solution possibleSol = solve(boardCopy, stepNum + 1, maxSteps);
					if (possibleSol != null) {
						sol = new Solution();
						sol.prependToSolution(board);
						sol.prependToSolution(possibleSol);
						return sol;
					}
				}
			}
		}
		// If went through all possibilties
		if (stepNum == 0) {
			//System.out.println("No solution found");
		}
		return null; // return empty solution if none found
	}
}