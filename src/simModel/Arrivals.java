package simModel;

import simulationModelling.ScheduledAction;

class Arrivals extends ScheduledAction
{
    SMTravel model; // To access the complete model

    public Arrivals(SMTravel model) { this.model = model; }

    @Override
    public double timeSequence()
    {
        return(model.rvp.duR());
    }

    @Override
    protected void actionEvent() {
        // Arrival Action Sequence SCS
        Call icCall  = new Call(model);
        icCall.uCustomerType = Call.CustomerType.values()[Constants.REGULAR];
        icCall.uCallType = model.rvp.uCallType();
        icCall.startWaitTime = model.getClock();
        icCall.uToleratedWaitTime = model.rvp.uToleratedWaitTime(icCall.uCustomerType);
        model.udp.CallRegistration(icCall);
        model.output.numCallProcessedRegular++;
    }

}
