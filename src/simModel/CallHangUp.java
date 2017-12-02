package simModel;

import simulationModelling.ScheduledAction;

class CallHangUp extends ScheduledAction{

    :wsimModel model; //reference to model object
    CallHangUp(simModel caller){ this.model = model; }

    public double timeSequence(){
        //needs other classes
    }

    public void actionEvent(/*TODO doesnt want parameter->Call uCallType*/ ){

        //Call Hangup action sequence

        //needs to be fixed based on other classes
        model.qWaitLine.remove(uCallType);
    }

}