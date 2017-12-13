package simModel;

import simulationModelling.SequelActivity;

public class EnterCardNumber extends SequelActivity{
    SMTravel model;
    private Call call;
    EnterCardNumber(SMTravel model, Call call) {
        this.model = model;
        this.call = call;
    }

    @Override
    public void startingEvent(){
        //Nothing to do
    }
    @Override
    protected double duration(){
        return model.rvp.cardNumberTypingTime();
    }
    @Override
    protected void terminatingEvent(){
        model.udp.CallRegistration(call);
    }
}

