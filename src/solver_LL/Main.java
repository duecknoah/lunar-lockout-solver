package solver_LL;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

import javax.swing.JFileChooser;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
	
	public static void main(String[] args) {
		// Command line parameter variables
		// Generation
		boolean doGen = false; // generate a random solve-able board?
		int totalBots = 6;
		int minSteps = 5; // minimum steps for solving
		int maxSteps = 10; // maximum generation for solving
		// Solving
		boolean doSolve = false; // solve a board?
		boolean doInputJSON = false; // take JSON data for the board? If false, it will take user input
		File inJSONFilePath = null; // the input file path for the json file
		boolean printJSONOutput = false; // print the board/solution json in the System.out
		boolean doOutFile = false; // do output json or solution?
		File outFilePath = null; // the output file path for the solution string and or board json file
		// Printing
		boolean verbose = false; // print lots of extra processing data?
		String helpScreen = "";
		helpScreen += "Usage: java -jar LLSolver -g <-t TOTALROBOTS> <-min MINIMUM_STEPS> <-max MAXIMUM_STEPS> <-out FILEPATH/JSONFILEPATH> <-p> <-verbose>\n"
				+ "or java -jar LLSolver -s <-in JSONFILE> <-min MINIMUM_STEPS> <-max MAXIMUM_STEPS> <-out FILEPATH/JSONFILEPATH> <-p> <-verbose>\n"
				+ "or java -jar LLSolver -g -s <-t TOTALROBOTS> <-min MINIMUM_STEPS> <-max MAXIMUM_STEPS> <-out FILEPATH/JSONFILEPATH> <-p> <-verbose>\n\n";
		helpScreen += "What is Lunar Lockout?:\n"
				+ "Lunar Lockout is a game played on a 5x5 board with up to 6 space robots. The objective "
				+ "of the game is to move the player robot (red) to the center (the motherships landing grid) at (3, 3).\n\n"
				+ "Rules:\n"
				+ "- All robots can only move Up, Right, Down, or Left\n"
				+ "- All robots can only move by moving straight into another robot\n"
				+ "- The walls of the board do not count as something Robots can move into\n"
				+ "\n\n"
				+ "How this program works:\n"
				+ "This program attempts to generate and/or solve any Lunar Lockout level. Using the parameters "
				+ "below, it can read off a JSON input file, the generated level, or user input. It can then output "
				+ "that solution, board data into a JSON or regular file.\n\n"
				+ "Parameters:\n"
				+ " -g			        generate a random board\n"
				+ " -s			    	solves provided board or the generated board\n"
				+ " -t TOTAL_BOTS		total bots on the board, must be less than 10 (default max 6 in actual game)\n"
				+ " -min MINIMUM_STEPS	the minimum steps required to solve or generate the solution\n"
				+ " -max MAXIMUM_STEPS	the maximum steps required to solve or generate the solution\n"
				+ " -in JSONFILE		indicates JSON file/filepath of board data to be solved\n"
				+ " -out FILE/JSONFILE	indicates file/filepath of the solution and or board JSON to be outputted to after\n"
				+ " -p					prints the json of the solved or generated board\n"
				+ " -verbose			Shows all processing console output\n";
		if (args.length == 0) {
			System.out.println("\n" + helpScreen);
			return;
		}
		else if (args[0].equals("--help")) {
			System.out.println("\n" + helpScreen);
			return;
		}
		// Retrieve arguments
		for (int i = 0; i < args.length; i ++) {
			// Generates a random level
			if (args[i].equals("-g")) {
				doGen = true;
			}
			// solves a specified level
			else if (args[i].equals("-s")) {
				doSolve = true;
			}
			else if (args[i].equals("-t")) {
				totalBots = Integer.valueOf(args[i + 1]);
				if (totalBots > 6) {
					System.out.println("There must be no more than 6 bots!");
					return;
				}
				if (totalBots < 2) {
					System.out.println("There cannot be less than 2 bots!");
					return;
				}
				i ++;
			}
			else if (args[i].equals("-min")) {
				minSteps = Integer.valueOf(args[i + 1]);
				i ++;
			}
			else if (args[i].equals("-max")) {
				maxSteps = Integer.valueOf(args[i + 1]);
				i ++;
			}
			else if (args[i].equals("-in")) {
				doInputJSON = true;
				inJSONFilePath = new File(args[i + 1]);
				i ++;
			}
			else if (args[i].equals("-out")) {
				doOutFile = true;
				outFilePath = new File(args[i + 1]);
				i ++;
			}
			else if (args[i].equals("-p")) {
				printJSONOutput = true;
			}
			else if (args[i].equals("-verbose")) {
				verbose = true;
			}
		}
		
		if (doSolve == false && doGen == false) {
			System.out.println("Please provide -g to gen or -s to solve ... Type --help for help");
			return;
		}
		
		// TODO: Improve all code below
		// Generating
		LevelGenerator level = null; // this can also be used in doSolve after generation to show solution (-g -s)
		
		if (doGen) {
			level = new LevelGenerator(totalBots, minSteps, maxSteps);
			level.generate();
			// Printing JSON of board
			String jsonString = level.getBoard().toJSON().toJSONString();
			if (printJSONOutput) {
				System.out.println(jsonString + "\n");
			}
			level.getBoard().printBoard();
		}
		// Solving
		if (doSolve) {
			if (level == null) {
				// if user wants to solve a board off of a boards JSON data in a file
				if (doInputJSON) {
					try (BufferedReader jFileBR = new BufferedReader(new FileReader(inJSONFilePath))) {
						// First read parse input json file
						JSONArray levelJSON;
						String line;
						if ((line = jFileBR.readLine()) != null) {
							// process line
							levelJSON = (JSONArray) new JSONParser().parse(line);
						}
						else {
							// No data found here!
							throw new IllegalArgumentException("No JSON input data found in: " + inJSONFilePath.toString() + "\n"
									+ "Note that it must be on line 1 within the file!");
						}
						
						jFileBR.close();
						// Parse JSON and print board layout
						level = new LevelGenerator();
						level.getBoard().parseJSON(levelJSON);
					}
					catch (ParseException e) {
						System.out.println("Error parsing JSON file: " + inJSONFilePath.toString());
						return;
					}
					catch (IOException e) {
						System.out.println("File input error. Check that the file path is correct");
						return;
					}
				}
				else {
				// Else get users input to input board data manually
					Scanner userInput = new Scanner(System.in);
					boolean isDone = false;
					int currentRobot = 0;
					Board boardToSolve = new Board();
					
					System.out.println("Total robots: " + totalBots);
					System.out.print("Player Robot (" + Robot.intToColor(currentRobot) + ") coords (x y): ");
					// Loop through robots to enter in coordinate data
					while (currentRobot < totalBots) {
						if (userInput.hasNextLine()) {
							if (userInput.hasNextInt()) {
								Robot r = new Robot();
								String inputX = userInput.next();
								String inputY = userInput.next();
								
								// If valid input integers (coordinates)
								if (inputX != null && inputY != null) {
									try {
										BoardPos b = new BoardPos(Integer.valueOf(inputX), Integer.valueOf(inputY));
										r.setPos(b);
										r.setColor(Robot.intToColor(currentRobot));
										// The first robot is always the player
										if (currentRobot == 0) {
											r.setPlayer(true);
										}
										boardToSolve.addRobot(r);
										currentRobot ++;
										// Print the line for the next input and clear any other user input
										userInput.nextLine();
										// If still going to loop next time around
										if (currentRobot < totalBots) {
											System.out.print(Robot.intToColor(currentRobot) + " Robot coords (x y): ");
										}
									}
									catch (NumberFormatException e) {
										System.out.println("Please enter two numbers for the coordinates.");
									}
									catch (IllegalArgumentException e) {
										System.out.println(e.getMessage());
									}
								}
							}
							else {
								System.out.println("Please enter two numbers for the coordinates.");
								userInput.next();
							}
						}
					}
					// Set the board to be created based off user's inputted robots
					level = new LevelGenerator(totalBots, minSteps, maxSteps);
					level.setBoard(boardToSolve);
				}
			}
			try {
			level.getBoard().validateRobotPositions();
			}
			catch (IllegalArgumentException invalidPosE) {
				System.out.println(invalidPosE.getMessage() + "\nCancelled.");
				return;
			}
			level.getBoard().printBoard();
			// Solve 
			Solver solver = new Solver();
			level.setSolution(solver.solve(level.getBoard(), maxSteps, true));
			
		}
		// Final file output handling
		String sol = (level.getSolution() == null) ? "No solution!" : level.getSolution().getSolutionString();
		// Now check if the user wants to output the map layout and solution to it in a file
		if (doOutFile) {
			try (FileWriter sFile = new FileWriter(outFilePath)) {
				String otherData = level.getBoard().getBoardStringData();
				if (doSolve) {
					otherData += "\nSolution:\n" + sol;
				}
				sFile.write(level.getBoard().toJSON().toJSONString() + "\n");
				sFile.write(otherData);
				sFile.close();
				System.out.println("File outputted to: " + outFilePath.toString());
			}
			catch (IOException e) {
				System.out.println("File output error. Check the file path is correct");
			}
		}
	}
}
