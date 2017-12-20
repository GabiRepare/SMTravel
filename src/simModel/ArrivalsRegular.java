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
        model.output.numCallReceivedRegular++;
        if(model.udp.callReceived(Constants.REGULAR)) {
            Call icCall = new Call();
            icCall.uType = Constants.REGULAR;
            icCall.uSubject = model.rvp.uCallSubject();
            icCall.uToleratedWaitTime = model.rvp.uToleratedWaitTime(Constants.REGULAR);
            CallHangUp hgUp = new CallHangUp(model, icCall);
            model.scheduleAction(hgUp);
            model.udp.enqueueCall(icCall);
        }
    }

}
