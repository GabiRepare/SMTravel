package simModel;

class Output 
{
	ModelName model;
	
	protected Output(ModelName md) { model = md; }
    // Use OutputSequence class to define Trajectory and Sample Sequences
    // Trajectory Sequences

    // Sample Sequences

    // DSOVs available in the OutputSequence objects
    // If seperate methods required to process Trajectory or Sample
    // Sequences - add them here
	private int numLongWait;
	private int numServed;
	private int numBusySignal;

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
	
    // SSOVs
}
