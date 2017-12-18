package simModel;

import simulationModelling.SequelActivity;

public class AfterCallWorkTime extends SequelActivity{
    SMTravel model;//access to model:SMTravel
    private int operatorType;
    private Call call;
    
    //after call-work time built corresponds to model, operator type and call
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
    //duration of after call-work time
    protected double duration() {
        return model.rvp.uAfterCallWorkTime(call.uSubject);
    }

    @Override
   //terminating event of after call-work tume
    protected void terminatingEvent() {
        model.rgOperators[operatorType].numFreeOperators++;
        model.udp.TryMatchCallOperator();
    }
}
