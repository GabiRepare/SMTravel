package simModel;

import simulationModelling.ScheduledAction;

class Arrivals extends ScheduledAction
{
    Arrivals model; // To access the complete model

    public Arrivals(SMTravel model) { this.model = model; }

    @Override
    public double timeSequence()
    {
        return(model.rvp.duCall());
    }

    @Override
    public void actionEvent() {
        // Arrival Action Sequence SCS
        icCall = new Call();
        icCall.uCustomerType = model.rvp.uCustomerType();
        icCall.uCallType = model.rvp.uCallType();
        icCall.uStartWaitTime = model.getClock();
        icCall.uToleratedWaitTime = model.rvp.uToleratedWaitTime(icCall.uCustomerType)
        CallRegistration(iC.Call.uCustomerType);
        SSOV.numCallProcessed++;
    }

}