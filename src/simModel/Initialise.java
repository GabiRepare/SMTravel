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
		//need to clear queue line and group 
		// System Initialisation
                // Add initilisation instructions 
		model.output.setNumServed(0);
		model.output.setNumLongWait(0);
		model.output.setNumBusySignal(0);
		model.output.setNumofArrival(0);

	}
	

}
