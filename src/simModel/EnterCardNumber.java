package simModel;

import simulationModelling.SequelActivity;

public class EnterCardNumber extends SequelActivity{
    SMTravel model;//access to model:SMTravel
    private Call call;//access call and its constructors
    EnterCardNumber(SMTravel model, Call call) {
        this.model = model;
        this.call = call;
    }

    @Override
    public void startingEvent(){
        //Nothing to do
    }
    //duration of cardholdrer to enter card number when accessing the call system 
    @Override
    protected double duration(){
        return model.rvp.cardNumberTypingTime();
    }
    @Override
    //terminating event 
    protected void terminatingEvent(){
        model.udp.CallRegistration(call);
    }
}

