package simModel;

import simulationModelling.ConditionalActivity;

class TalkToOperator extends ConditionalActivity {
    SMTravel model;
    //Customer Type is definied in Call.java, double check
    Call call = new Call(model);
    protected Call.CallType callType;
    protected static Operators.OperatorType operatorType;
    protected Call.CustomerType uCustomerType;
    protected TrunkLines trunkLines=new TrunkLines(model);

    public TalkToOperator(SMTravel model) { this.model = model; }

    protected static boolean precondition(SMTravel model){
        if(model.icCall.uCustomerType == Call.CustomerType.GOLD)
        {
            if(model.rgOperators[Constants.GOLD].numFreeOperators >0 ||
                    model.rgOperators[Constants.SILVER].numFreeOperators>0 ||
                    model.rgOperators[Constants.REGULAR].numFreeOperators>0)
            {
                operatorType = Operators.OperatorType.values()[Constants.GOLD];
                return true;
            }
        }
        else if (model.icCall.uCustomerType == Call.CustomerType.values()[Constants.SILVER] ||
                model.icCall.uCustomerType == Call.CustomerType.values()[Constants.REGULAR])
        {
            if(model.rgOperators[Constants.SILVER].numFreeOperators>0 ||
                    model.rgOperators[Constants.REGULAR].numFreeOperators>0)
            {
                if(model.rgOperators[Constants.SILVER].numFreeOperators>0)
                {
                    operatorType = Operators.OperatorType.values()[Constants.SILVER];
                }
                else if(model.rgOperators[Constants.SILVER].numFreeOperators>0)
                {
                    operatorType = Operators.OperatorType.values()[Constants.REGULAR];
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
        if(call.uCustomerType.getValue() == Constants.CARDHOLDER) {
            model.output.numCallProcessedCardholder++;
        }
        else
        {
            model.output.numCallProcessedRegular++;
        }


    }

    protected void terminatingEvent(){

        model.rgOperators[operatorType.getValue()].numFreeOperators++;
        AfterCallWorkTime afterCallWor = new AfterCallWorkTime(model.rgOperators[operatorType.getValue()], call, model);
        model.spStart(afterCallWor);
    }
}
