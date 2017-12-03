package simModel;

import simulationModelling.SequelActivity;

public class AfterCallWorkTime extends SequelActivity{
    Operators operators;
    Call call;
    SMTravel model;
    public AfterCallWorkTime(Operators operators, Call call, SMTravel model)
    {
        this.operators = operators;
        this.call = call;
        this.model = model;
    }
    @Override
    public void startingEvent() {

    }

    @Override
    protected double duration() {

        return model.rvp.uAfterCallWorkTime(operators.uOperatorsType, call.uCallType);
    }

    @Override
    protected void terminatingEvent() {
        operators.numFreeOperators++;
    }
}
