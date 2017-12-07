
package simModel;

class TrunkLines{
	/*TODO Do we need this?
	SMTravel model;
    public TrunkLines(SMTravel model) { this.model = model; }
    */
    protected int numTrunkLineInUse; //Number of available trunk lines
    protected  int numReservedLine; //Number of reserved trunk lines
    protected int numTrunkLine; //Total number of trunk lines

    public TrunkLines(int numTrunkLine, int numReservedLine){
        this.numTrunkLine = numTrunkLine;
        this.numReservedLine = numReservedLine;
        numTrunkLineInUse = 0;
    }
}