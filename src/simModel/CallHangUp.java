package simModel;

import simulationModelling.ScheduledAction;

class CallHangUp extends ScheduledAction{

    SMTravel model; //reference to model object
    private Call call;
    private boolean firstTime;

    CallHangUp(SMTravel model, Call call){
        this.model = model;
        this.call = call;
        firstTime = true;
    }
    public double timeSequence(){
        if (firstTime){
            firstTime = false;
            return model.getClock() + call.uToleratedWaitTime;
        } else {return -1.0;}
    }

    public void actionEvent()
    {
        if (model.qWaitLines[call.uType].contains(call)) {
            //Call Hangup action sequence
            model.udp.checkForLongWait(call);
            model.qWaitLines[call.uType].remove(call);
            model.rgTrunkLines.numTrunkLineInUse--;
        }
    }

}