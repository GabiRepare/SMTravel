package simModel;

import simulationModelling.ScheduledAction;

class ArrivalsCardholder extends ScheduledAction {
    SMTravel model; ///access to model:SMTravel

    ArrivalsCardholder(SMTravel model) {
        this.model = model;
    }

    @Override
    //time sequence of Cardholder arrivals
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
        CallHangUp hgUp = new CallHangUp(model, icCall);
        model.scheduleAction(hgUp);
        EnterCardNumber cardAct = new EnterCardNumber(model, icCall);
        model.spStart(cardAct);
    }
}