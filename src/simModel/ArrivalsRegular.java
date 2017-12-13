package simModel;

import simulationModelling.ScheduledAction;

class ArrivalsRegular extends ScheduledAction
{
    SMTravel model; // To access the complete model

    ArrivalsRegular(SMTravel model) { this.model = model; }

    @Override
    public double timeSequence()
    {
        return(model.rvp.duRegular());
    }

    @Override
    protected void actionEvent() {
        // Arrival Action Sequence SCS
        Call icCall  = new Call();
        icCall.uType = Constants.REGULAR;
        icCall.uSubject = model.rvp.uCallSubject();
        icCall.uToleratedWaitTime = model.rvp.uToleratedWaitTime(Constants.REGULAR);
        model.output.numCallReceivedRegular++;
        model.udp.CallRegistration(icCall);
    }

}
