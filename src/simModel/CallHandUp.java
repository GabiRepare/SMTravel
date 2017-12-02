package simModel;


class CallHangUp extends ScheduledAction{

    SMTravel model; //reference to model object
    CallHangUp( SMTravel caller){ this.model = model; }

    public double timeSequence(){
        //needs other classes
    }

    public void actionEvent(Call uCallType ){

        //Call Hangup action sequence

        //needs to be fixed based on other classes
        model.qWaitLines.remove(uCallType);
    }

}