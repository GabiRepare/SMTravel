
package simModel;

class TrunkLines{
    int numTrunkLineInUse; //Number of available trunk lines
    int numReservedLine; //Number of reserved trunk lines
    int numTrunkLine; //Total number of trunk lines

    TrunkLines(int numTrunkLine, int numReservedLine){
        this.numTrunkLine = numTrunkLine;
        this.numReservedLine = numReservedLine;
        numTrunkLineInUse = 0;
    }
}
