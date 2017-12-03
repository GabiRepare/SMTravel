package simModel;

class Output 
{
	SMTravel model;
	
	protected Output(SMTravel md) { model = md; }
    // Use OutputSequence class to define Trajectory and Sample Sequences
    // Trajectory Sequences

    // Sample Sequences

    // DSOVs available in the OutputSequence objects
    // If seperate methods required to process Trajectory or Sample
    // Sequences - add them here
	protected int numLongWaitGold;
	protected int numLongWaitSilver;
	protected int numLongWaitRegular;
	protected int numLongWaitTotal;
	protected int numCallProcessedCardholder;
	protected int numCallProcessedRegular;
	protected int numBusySignalCardholder;
	protected int numBusySignalRegular;
	protected int maxTrunkLineInUse;
	protected int numOfArrival;
	protected int numOfArrivalCardholder;

	//Getter
	public int getNumLongWaitTotal()
	{
		numLongWaitTotal = numLongWaitGold + numLongWaitSilver + numLongWaitRegular;
		return numLongWaitTotal;
	}
	public int getNumLongWaitGold() { return numLongWaitGold; }
	public int getNumLongWaitSilver() { return numLongWaitSilver; }
	public int getNumLongWaitRegular() { return numLongWaitRegular; }
	public int getNumCallProcessedCardholder() {
		return numCallProcessedCardholder;
	}
	public int getNumCallProcessedRegular() {
		return numCallProcessedRegular;
	}
	public int getNumBusySignalCardholder() {
		return numBusySignalCardholder;
	}
	public int getNumBusySignalRegular() {
		return numBusySignalRegular;
	}
	public int getNumOfArrival() {
		return numOfArrival;
	}
	public int getNumOfArrivalCardholder() {
		return numOfArrivalCardholder;
	}
	public int getMaxTrunkLineInUse() {return maxTrunkLineInUse; }

    // Setter
	public void setNumCallProcessedCardholder(int numServed) {
		this.numCallProcessedCardholder = numServed;
	}
	public void setNumCallProcessedRegular(int numServed) {
		this.numCallProcessedRegular = numServed;
	}
	public void setNumOfArrivalCardholder(int numofArrival) {
		this.numOfArrivalCardholder = numofArrival;
	}
	public void setNumOfArrival(int numofArrival) {
		this.numOfArrival = numofArrival;
	}
	public void setNumLongWaitGold(int numLongWait) {
		this.numLongWaitGold = numLongWait;
	}
	public void setNumLongWaitSilver(int numLongWait) {
		this.numLongWaitSilver = numLongWait;
	}
	public void setNumLongWaitRegular(int numLongWait) {
		this.numLongWaitRegular = numLongWait;
	}
	public void setNumBusySignalCarholder(int numBusySignal) {
		this.numBusySignalCardholder = numBusySignal;
	}
	public void setNumBusySignalRegular(int numBusySignal) {
		this.numBusySignalRegular = numBusySignal;
	}

	
    // SSOVs
	/*TODO whats that->protected static int*/
}
