
package simModel;

class TrunkLines{
	SMTravel model;
    public TrunkLines(SMTravel model) { this.model = model; }
    protected int numTrunkLineInUse; //Number of available trunk lines
    protected int numReservedLine; //Number of reserved trunk lines
    protected int numTrunkLine; //Total number of trunk lines
    protected int numEmptyTrunkLine;

    
}