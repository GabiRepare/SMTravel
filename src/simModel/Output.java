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
    protected int[] numLongWait = new int[3];
    protected int[] numWait = new int[3];
    protected int numBusySignalCardholder;
    protected int numBusySignalRegular;
    protected int numCallReceivedCardholder;
    protected int numCallReceivedRegular;
    protected int maxTrunkLineUsed;

    public double[] getPropLongWait(){
        double[] propLongWait = new double[3];
        for (int i = 0; i < 3; i++){
            propLongWait[i] = (double)numLongWait[i]/numWait[i];
        }
        return propLongWait;
    }

    public double getPropBusySignalCardholder(){
        return (double)numBusySignalCardholder / numCallReceivedCardholder;
    }

    public double getPropBusySignalRegular(){
        return (double)numBusySignalRegular / numCallReceivedRegular;
    }
}
