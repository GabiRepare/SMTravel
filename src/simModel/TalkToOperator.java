package simModel;

import simulationModelling.SequelActivity;
/*
    process of calling withing call system, customer will be served when existing availaible/free opertors 
    duration would be service time corresponding to different operator type
    when this activty ends, after call work will start
*/
class TalkToOperator extends SequelActivity {
    SMTravel model;//access to model:SMTravel
    private Call call;
    private int callType;
    private int operatorType;
    
    TalkToOperator(SMTravel model, int callType, int operatorType) {
        this.model = model;
        this.callType = callType;
        this.operatorType = operatorType;
    }
    
    public void startingEvent(){
        call = (Call)model.qWaitLines[callType].poll();
        model.udp.CheckForLongWait(call);
        model.rgOperators[operatorType].numFreeOperators--;
    }

    protected double duration(){
        return (model.rvp.uServiceTime(call.uSubject,operatorType));
    }

    protected void terminatingEvent(){
        model.rgTrunkLines.numTrunkLineInUse--;
        AfterCallWorkTime afterCallWork = new AfterCallWorkTime(model, operatorType, call);
        model.spStart(afterCallWork);
    }
}
