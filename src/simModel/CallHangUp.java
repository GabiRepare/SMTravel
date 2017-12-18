package simModel;

import simulationModelling.ScheduledAction;

class CallHangUp extends ScheduledAction{

    SMTravel model; //access to model:SMTravel
    private Call call;
    private boolean firstTime;

    
    CallHangUp(SMTravel model, Call call){
        this.model = model;
        this.call = call;
        firstTime = true;
    }
    //time sequence of call hang up action
    public double timeSequence(){
        if (firstTime){
            firstTime = false;
            return model.getClock() + call.uToleratedWaitTime;
        } else {return -1.0;}
    }
    //call hang up action event sequence
    public void actionEvent()
    {
        if (model.qWaitLines[call.uType].contains(call)) {
          
            model.udp.CheckForLongWait(call);
            model.qWaitLines[call.uType].remove(call);
            model.rgTrunkLines.numTrunkLineInUse--;
        }
    }

}