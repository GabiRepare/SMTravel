package simModel;

import simulationModelling.ScheduledAction;

class Initialise extends ScheduledAction
{
    SMTravel model;//access to model:SMTravel

    // Constructor
    Initialise(SMTravel model) { this.model = model; }

    private double [] ts = { 0.0, -1.0 }; // -1.0 ends scheduling
    private int tsix = 0;  // set index to first entry.
    protected double timeSequence()
    {
        return ts[tsix++];  // only invoked at t=0
    }

    protected void actionEvent()
    {
        //System Initialisation for waiting lines queue and resource group of trunkline and operators
        model.qWaitLines[Constants.GOLD].clear();
        model.qWaitLines[Constants.SILVER].clear();
        model.qWaitLines[Constants.REGULAR].clear();
        model.rgTrunkLines.numTrunkLineInUse = 0;
        model.rgOperators[Constants.GOLD].numFreeOperators = 0;
        model.rgOperators[Constants.SILVER].numFreeOperators = 0;
        model.rgOperators[Constants.REGULAR].numFreeOperators = 0;
        
        // Initialise the output variables for ouputs which will be used to measure efficiency of the call system
        model.output.numLongWait = new int[3];//Number of calls that waited longer than threshold
        model.output.numWait = new int[3];//The number of calls of the given type that got into its respective waiting queue.
        model.output.numBusySignalCardholder = 0;//accumulate how many busy signal released corresponding to different type of customer
        model.output.numBusySignalRegular = 0;
        model.output.numCallReceivedCardholder = 0;//accumulate how many customer being served
        model.output.numCallReceivedRegular = 0;
        model.output.maxTrunkLineUsed = 0;//The maximum number of Trunk Lines used.
    }


}
