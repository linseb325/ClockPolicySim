// The simulator class is the "brains" of the program.
// It sets up the data structure(s) for the simulation and retrieves/processes user input.
// Created by Brennan Linse on 12/4/16.

import java.util.*;

public class Simulator {
	
	private Page[] thePages;			// Contains the simulated memory pages.
	private Random randNumGenerator;	// Generates random page numbers and random boolean values.
	private int[] usedAlready;			// Contains the page numbers currently in use. We wouldn't want duplicate page numbers!
	private int nextPagePointer;		// Points to the page which will be tested for replacement next.
	private Scanner input;				// Retrieves user input from the console.
	
	public Simulator() {
		this.randNumGenerator = new Random();
		this.usedAlready = new int[10];
		this.thePages = this.filledPageArray();
		this.nextPagePointer = 0;
		this.input = new Scanner(System.in);
	}
	
	// Begins the clock policy simulation.
	public void beginSimulation() {
		this.displayFrameStatus();
		this.showPrompt(true);
	}
	
	// Shows the user information about each memory frame and the page it holds.
	private void displayFrameStatus() {
		System.out.println("\nFRAME STATUS:");
		for(int i = 0; i < 10; i++) {
			// Piece together a string containing information about the frame/page, possibly including the next frame pointer.
			String pageData = "Frame " + i + ":  Page # " + this.thePages[i].getPageNumber() + " - Use bit: " + this.thePages[i].getUseBitAsInt();
			if(i == this.nextPagePointer) {
				pageData += "  <----------[Next Page]";
			}
			System.out.println(pageData);
		}
	}
	
	// Prompts the user to submit a command, then eventually processes the command.
	// The parameter determines whether to skip a line before displaying the prompt.
	private void showPrompt(boolean skipLine) {
		String prompt = "Replace a page, exit, or display frame status? ('replace'/'exit'/'status')";
		if(skipLine) {
			prompt = "\n" + prompt;
		}
		System.out.println(prompt);
		System.out.print("Type your command here -> ");
		
		// Get input from the user.
		String userInput = this.input.nextLine();
		
		if(userInput.equalsIgnoreCase("replace")) {
			// The user has chosen to replace a page.
			
			while(this.thePages[this.nextPagePointer].getUseBit()) {
				// When the use bit is 1 (true), toggle it to 0 (false) and advance the pointer to the next page.
				this.thePages[this.nextPagePointer].toggleUseBit();
				this.incrementPointer();
			}
			
			// Now, the pointer is pointing at a page whose use bit is 0 (false).
			Page newPage = this.generateNewPage();
			this.usedAlready[this.nextPagePointer] = newPage.getPageNumber();
			this.thePages[this.nextPagePointer] = newPage;
			int replacedAtIndex = this.nextPagePointer;
			this.incrementPointer();
			
			// Show the user which page replaced the old page, and in which frame the replacement took place.
			// Also, show the user the new status of the frames, and prompt for another instruction.
			System.out.println("\n* * * * *   Replaced the page in frame " + replacedAtIndex + " with page # " + newPage.getPageNumber() + "!   * * * * *");
			this.displayFrameStatus();
			this.showPrompt(true);
			
		} else if(userInput.equalsIgnoreCase("exit")) {
			// The user has chosen to exit the simulation.
			System.out.println("Simulation terminated.");
			// No more console output/prompts, effectively ending the simulation.
			
		} else if(userInput.equalsIgnoreCase("status")) {
			// The user wants to see the frame status displayed again.
			this.displayFrameStatus();
			this.showPrompt(true);
			
		} else {
			// Invalid command; tell the user and request another command.
			System.out.println("\nINVALID COMMAND! Possible commands: \"replace\", \"exit\", or \"status\"...");
			this.showPrompt(false);
		}
	}
	
	// Returns an array of page objects with randomly selected page numbers and randomly set use bits.
	private Page[] filledPageArray() {
		Page[] toReturn = new Page[10];
		for(int i = 0; i < 10; i++) {
			Page pageToAdd = this.generateNewPage();
			this.usedAlready[i] = pageToAdd.getPageNumber();			// Keep track of the page numbers in use.
			pageToAdd.setUseBit(this.randNumGenerator.nextBoolean());	// Randomize the use bit before adding the page.
			toReturn[i] = pageToAdd;
		}
		return toReturn;
	}
	
	// Returns true if the parameter page number is not already in use and false otherwise.
	private boolean canUsePageNumber(int pageNumToTest) {
		for(int aNumBeingUsed : this.usedAlready) {
			// Iterate through each of the numbers which have already been used, checking whether each matches the parameter integer.
			if(pageNumToTest == aNumBeingUsed) {
				return false;
			}
		}
		return true;
	}
	
	// Increments the value of the next page pointer. If the pointer reaches the last frame, it is reset to frame 0.
	private void incrementPointer() {
		if(this.nextPagePointer == 9) {
			this.nextPagePointer = 0;
		} else {
			this.nextPagePointer++;
		}
	}
	
	// Returns a new page object with a unique page number and a use bit of 0 (false).
	private Page generateNewPage() {
		int aRandomNum = this.randNumGenerator.nextInt(90);
		aRandomNum += 10;
		
		while(!this.canUsePageNumber(aRandomNum)) {
			// If the page number is already being used, try another.
			aRandomNum = this.randNumGenerator.nextInt(90);
			aRandomNum += 10;
		}
		
		Page theNewPage = new Page(aRandomNum, true);
		return theNewPage;
	}
}
