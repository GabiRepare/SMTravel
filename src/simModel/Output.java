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
	private int numLongWait;
	private int numServed;
	private int numBusySignal;
	private int numofArrival;

	public int getNumLongWait() {
		return numLongWait;
	}
	public void setNumLongWait(int numLongWait) {
		this.numLongWait = numLongWait;
	}
	public int getNumServed() {
		return numServed;
	}
	public void setNumServed(int numServed) {
		this.numServed = numServed;
	}
	public int getNumBusySignal() {
		return numBusySignal;
	}
	public void setNumBusySignal(int numBusySignal) {
		this.numBusySignal = numBusySignal;
	}
	public int getNumofArrival() {
		return numofArrival;
	}
	public void setNumofArrival(int numofArrival) {
		this.numofArrival = numofArrival;
	}
	
    // SSOVs
}
