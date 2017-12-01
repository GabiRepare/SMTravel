package simModel;

import simulationModelling.ConditionalActivity;

class TalkToOperator extends ConditionalActivity {
    SMTravel model;
    //Customer Type is definied in Call.java, double check
    private Call call = new Call(model);

    public TalkToOperator(SMTravel model) { this.model = model; }

    protected static boolean precondition(SMTravel simModel){
        //this may need to be modified based on other classes
        if(call.uCostmerType == Constant.GOLD)
        {
            if(simModel.Operator[GOLD].numFreeOperator>0 ||
               simModel.Operator[SILVER].numFreeOperator>0 ||
               simModel.Operator[REGULAR].numFreeOperator>0)
            {
                return true;
            }
        }
        else if (call.uCostmerType == Constant.SILVER || Constant.REGULAR)
        {
            if(simModel.Operator[SILVER].numFreeOperator>0 ||
               simModel.Operator[REGULAR].numFreeOperator>0
            {
                return true;
            }
        }
        return false;
    }

    public void startingEvent(){
        processTalkToOperator();
    }

    protected double duration(){
        return (simModel.rvp.uServiceTime(call.CallType, simModel.Operator.uOperatorType));
    }

    protected void secondaryEvent(){
        numTrunkLineInUse--;
        ssov.numServed++;
    }

    protected double secondaryDuration(){
        return (uAfterCallWorkTime(call.uCustomerType));
    }

    protected void terminatingEvent(){
        simModel.rgOperator.numFreeOperators++;
    }
}