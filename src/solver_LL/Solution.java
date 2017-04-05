package solver_LL;

import java.util.ArrayList;
import java.util.LinkedList;

public class Solution {
	private LinkedList<Board> boardStates; //LIFO
	
	Solution() {
		boardStates = new LinkedList<>();
	}
	
	// Prepends a single board state to the queue
	public void prependToSolution(Board boardState) {
		this.boardStates.addLast(boardState);
	}
	
	// Appends an already existing solution to be part of this one. This
	// is done when returning solutions and combining them as it returns
	// up the stack in Solver.solve(...)
	public void prependToSolution(Solution otherSolution) {
		// Cycle through other board states and append it to
		// the start of this one (LIFO){
		boardStates.addAll(0, otherSolution.getBoardStates());
	}
	
	public LinkedList<Board> getBoardStates() {
		return boardStates;
	}
	
	// returns the total moves to solve
	public int getTotalMoveCount() {
		return getBoardStates().size() - 1;
	}
	
	public String getSolutionString() {
		String sol = "";
		
		System.out.println("Total moves: " + (boardStates.size() - 1));
		// Loop starts from the end and works its way to index 0. Note that although the for loop
		// only goes down to 1, the bNext (next board) always has the index of (iBoardState - 1)
		for (int iBoardState = boardStates.size() - 1; iBoardState > 0; iBoardState --) {
			Board b = boardStates.get(iBoardState);
			Board bNext = boardStates.get(iBoardState - 1);
			ArrayList<Robot> robotsInBoard = b.getRobots();
			
			// First find what robot moved
			int rMovedIndex = b.getBoardDif(bNext);
			Robot rBeforeMove = b.getRobots().get(rMovedIndex);
			Robot rAfterMove = bNext.getRobots().get(rMovedIndex);
			
			int xDiff = rAfterMove.getPos().getX() - rBeforeMove.getPos().getX();
			int yDiff = rAfterMove.getPos().getY() - rBeforeMove.getPos().getY();
			
			sol += (boardStates.size() - iBoardState) + ": ";
			
			if (xDiff < 0) {
				sol += rBeforeMove.getColor() + " left\n";
			}
			if (xDiff > 0) {
				sol += rBeforeMove.getColor() + " right\n";
			}
			if (yDiff < 0) {
				sol += rBeforeMove.getColor() + " up\n";
			}
			if (yDiff > 0) {
				sol += rBeforeMove.getColor() + " down\n";
			}
		}
		return sol;
	}
}
