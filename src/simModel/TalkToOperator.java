package simModel;

import simulationModelling.ConditionalActivity;

class TalkToOperator extends ConditionalActivity {
    SMTravel model;
    //Customer Type is definied in Call.java, double check

    protected Call.CallType callType;
    protected  Operators.OperatorType operatorType;
    protected Call.CustomerType uCustomerType;

    protected TrunkLines trunkLines=new TrunkLines(model);
    public TalkToOperator(SMTravel model) { this.model = model; }

    protected static boolean precondition(SMTravel simModel){
        if(call.uCostmerType == Constant.GOLD)
        {
            if(simModel.Operator[Constants.GOLD].numFreeOperator>0 ||
                    simModel.Operator[Constants.SILVER].numFreeOperator>0 ||
                    simModel.Operator[Constants.REGULAR].numFreeOperator>0)
            {
                return true;
            }
        }
        else if (call.uCostmerType == Constant.SILVER || Constant.REGULAR)
        {
            if(simModel.Operator[Constants.SILVER].numFreeOperator>0 ||
               simModel.Operator[Constants.REGULAR].numFreeOperator>0
            {
                return true;
            }
        }
        return false;
    }

    public void startingEvent(){
        model.udp.processTalkToOperator(operatorType);
    }

    protected double duration(){
        return (model.rvp.uServiceTime(callType,operatorType));
    }

    protected void secondaryEvent(){
    	trunkLines.numTrunkLineInUse--;
        ssov.numServed++;
    }

    protected double secondaryDuration(){
        return (model.rvp.uAfterCallWorkTime(uCustomerType, callType));
    }

    protected void terminatingEvent(){
        simModel.rgOperator.numFreeOperators++;
    }
}