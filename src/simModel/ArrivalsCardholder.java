package simModel;

import simulationModelling.ScheduledAction;

class ArrivalsCardholder extends ScheduledAction {
    SMTravel model; // To access the complete model

    ArrivalsCardholder(SMTravel model) {
        this.model = model;
    }

    @Override
    public double timeSequence() {
        return (model.rvp.duCardholder());
    }

    @Override
    protected void actionEvent() {
        // Arrival Action Sequence SCS
        Call icCall = new Call();
        icCall.uType = model.rvp.uCardholderType();
        icCall.uSubject = model.rvp.uCallSubject();
        icCall.uToleratedWaitTime = model.rvp.uToleratedWaitTime(icCall.uType);
        model.output.numCallReceivedCardholder++;
        EnterCardNumber cardAct = new EnterCardNumber(model, icCall);
        model.spStart(cardAct);
    }
}