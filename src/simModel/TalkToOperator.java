package simModel;

import simulationModelling.ConditionalActivity;

class TalkToOperator extends ConditionalActivity {
    SMTravel model;
    //Customer Type is definied in Call.java, double check
    Call call = new Call(model);
    protected Call.CallType callType;
    protected  Operators.OperatorType operatorType;
    protected Call.CustomerType uCustomerType;
    protected TrunkLines trunkLines=new TrunkLines(model);
    public TalkToOperator(SMTravel model) { this.model = model; }

    protected boolean precondition(SMTravel simModel){
        if(call.uCustomerType == Call.CustomerType.GOLD)
        {
            if(model.rgOperators[Constants.GOLD].numFreeOperators >0 ||
                    model.rgOperators[Constants.SILVER].numFreeOperators>0 ||
                    model.rgOperators[Constants.REGULAR].numFreeOperators>0)
            {
                //TODO Need to user constants instead
                operatorType = Operators.OperatorType.GOLD;
                return true;
            }
        }
        else if (call.uCustomerType == Call.CustomerType.SILVER || call.uCustomerType == Call.CustomerType.REGULAR)
        {
            if(model.rgOperators[Constants.SILVER].numFreeOperators>0 ||
                    model.rgOperators[Constants.REGULAR].numFreeOperators>0)
            {
                if(model.rgOperators[Constants.SILVER].numFreeOperators>0)
                {
                    operatorType = Operators.OperatorType.SILVER;
                }
                else if(model.rgOperators[Constants.SILVER].numFreeOperators>0)
                {
                    operatorType = Operators.OperatorType.REGULAR;
                }
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
        model.output.numServed++;
    }

    protected double secondaryDuration(){
        return (model.rvp.uAfterCallWorkTime(uCustomerType, callType));
    }

    protected void terminatingEvent(){
        model.rgOperators[operatorType].numFreeOperators++;
    }
}