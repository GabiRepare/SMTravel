package simModel;

import simulationModelling.SequelActivity;

public class AfterCallWorkTime extends SequelActivity{
    SMTravel model;
    private int operatorType;
    private Call call;

    AfterCallWorkTime(SMTravel model, int operatorType, Call call)
    {
        this.model = model;
        this.operatorType = operatorType;
        this.call = call;
    }
    @Override
    public void startingEvent() {
        //Nothing to do
    }

    @Override
    protected double duration() {
        return model.rvp.uAfterCallWorkTime(call.uSubject, operatorType);
    }

    @Override
    protected void terminatingEvent() {
        model.rgOperators[operatorType].numFreeOperators++;
        model.udp.tryMatchCallOperator();
    }
}
