package simModel;

import simulationModelling.ConditionalActivity;

class TalkToOperator extends ConditionalActivity {
    SMTravel model;
    //Customer Type is definied in Call.java, double check
    private Call uCustomerType;

    public TalkToOperator(SMTravel model) { this.model = model; }

    protected static boolean precondition(SMTravel simModel){
        boolean returnValue = false;
        //this may need to be modified based on other classes
        if(simModel.rgOperator.type == simModel.n && simModel.n != 0){
            returnValue = true;
        }
        return(returnValue);
    }

    public void startingEvent(){
        processTalkToOperator(customerType);
    }

    protected double duration(){
        return (model.rvp.uServiceTime(uCustomerType));
    }

    protected void secondaryEvent(){
        numTrunkLineInUse--;
        ssov.numServed++;
    }

    protected double secondaryDuration(){
        return (uAfterCallWorkTime(uCustomerType));
    }

    protected void terminatingEvent(){
        simModel.rgOperator.numFreeOperators++;
    }
}