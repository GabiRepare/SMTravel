package simModel;

import simulationModelling.ScheduledAction;

class ArrivalsCardholder extends ScheduledAction {
    SMTravel model; // To access the complete model

    public ArrivalsCardholder(SMTravel model) {
        this.model = model;
    }

    @Override
    public double timeSequence() {
        return (model.rvp.duC());
    }

    @Override
    protected void actionEvent() {
        // Arrival Action Sequence SCS
        Call icCall = new Call(model);
        icCall.uCustomerType = model.rvp.uCardholderType();
        icCall.uCallType = model.rvp.uCallType();
        icCall.startWaitTime = model.getClock();
        icCall.uToleratedWaitTime = model.rvp.uToleratedWaitTime(icCall.uCustomerType);
        model.udp.CallRegistration(icCall);
        model.output.numServed++;
    }
}