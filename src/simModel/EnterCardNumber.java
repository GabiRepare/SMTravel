package simModel;

import simulationModelling.ConditionalActivity;

public class EnterCardNumber extends ConditionalActivity{
    SMTravel model;
    public EnterCardNumber(SMTravel model) { this.model = model; }
    protected static boolean precondition(SMTravel model) {
        boolean isStatisfied = false;
        if(model.icCall.uCustomerType.getValue() == Constants.GOLD ||
           model.icCall.uCustomerType.getValue() == Constants.SILVER)
        {
            isStatisfied = true;
        }
        return isStatisfied;
    }
    @Override
    public void startingEvent(){


    }
    @Override
    protected double duration(){
        return model.rvp.enterCardNumerTime(7,12);
    }
    @Override
    protected void terminatingEvent(){
        model.icCall.startWaitTime = model.getClock();
        model.qWaitLines[model.icCall.uCustomerType.getValue()].add(model.icCall);
    }
}

