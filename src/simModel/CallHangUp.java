package simModel;

import simulationModelling.ScheduledAction;

class CallHangUp extends ScheduledAction{

    SMTravel model; //reference to model object
    CallHangUp(SMTravel model){ this.model = model; }
    Call call = new Call(model);
    public double timeSequence()
    {
        /* TODO needs other classes */
        return 0.0;
    }

    public void actionEvent()
    {

        //Call Hangup action sequence
        if(call.uCustomerType == Call.CustomerType.REGULAR)
        {
            model.qWaitLines[Constants.REGULAR].remove(call);
        }
        else if(call.uCustomerType == Call.CustomerType.GOLD)
        {
            model.qWaitLines[Constants.GOLD].remove(call);
        }
        else
        {
            model.qWaitLines[Constants.SILVER].remove(call);
        }
    }

}