package simModel;

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
		//TODO need to clear queue line and group
		//System Initialisation
		model.output.setNumCallProcessedCardholder(0);
		model.output.setNumCallProcessedRegular(0);
		model.output.setNumLongWaitGold(0);
		model.output.setNumLongWaitSilver(0);
		model.output.setNumLongWaitRegular(0);
		model.output.setNumBusySignalCarholder(0);
		model.output.setNumBusySignalRegular(0);
		model.output.setNumOfArrival(0);
		model.output.setNumOfArrivalCardholder(0);
	}
	

}
