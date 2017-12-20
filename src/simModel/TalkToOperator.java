package simModel;

import simulationModelling.SequelActivity;

class TalkToOperator extends SequelActivity {
    SMTravel model;
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
        model.udp.checkForLongWait(call);
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
