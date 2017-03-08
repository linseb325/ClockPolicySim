// The page class is used to represent a page in memory.
// It is ultimately stored/manipulated in a Simulator object's array of pages.
// Created by Brennan Linse on 12/4/16.

public class Page {
	
	private int pageNumber;		// The randomly generated page number.
	private boolean useBit;		// The page's use bit. 'true' represents a 1; 'false' represents a 0
	
	public Page(int pageNum, boolean useBit) {
		this.pageNumber = pageNum;
		this.useBit = useBit;
	}
	
	// Toggles the page's use bit from true to false or vice versa.
	public void toggleUseBit() {
		if(this.getUseBit()) {
			this.setUseBit(false);
		} else {
			this.setUseBit(true);
		}
	}
	
	// Returns the integer 1 if the use bit field is set to true, and the integer 0 otherwise.
	public int getUseBitAsInt() {
		if(this.getUseBit()) {
			return 1;
		} else {
			return 0;
		}
	}
	
	// Getter & setter for page number
 	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	
	// Getter & setter for use bit
	public boolean getUseBit() {
		return useBit;
	}
	public void setUseBit(boolean useBit) {
		this.useBit = useBit;
	}
}
