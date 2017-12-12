package simModel;

import com.sun.xml.internal.xsom.impl.Const;
import simulationModelling.ScheduledAction;

class Initialise extends ScheduledAction
{
    SMTravel model;

    // Constructor
    protected Initialise(SMTravel model) { this.model = model; }

    double [] ts = { 0.0, -1.0 }; // -1.0 ends scheduling
    int tsix = 0;  // set index to first entry.
    protected double timeSequence()
    {
        return ts[tsix++];  // only invoked at t=0
    }

    protected void actionEvent()
    {
        //System Initialisation
        model.qWaitLines[Constants.GOLD].clear();
        model.qWaitLines[Constants.SILVER].clear();
        model.qWaitLines[Constants.REGULAR].clear();
        model.rgTrunkLines.numTrunkLineInUse = 0;
        model.rgOperators[Constants.GOLD].numFreeOperators = 0;
        model.rgOperators[Constants.SILVER].numFreeOperators = 0;
        model.rgOperators[Constants.REGULAR].numFreeOperators = 0;
        // Initialise the output variables
        model.output.numLongWait = new int[3];
        model.output.numWait = new int[3];
        model.output.numBusySignalCardholder = 0;
        model.output.numBusySignalRegular = 0;
        model.output.numCallReceivedCardholder = 0;
        model.output.numCallReceivedRegular = 0;
        model.output.maxTrunkLineUsed = 0;
    }


}
