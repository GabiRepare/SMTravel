package simModel;

import simulationModelling.ScheduledAction;

class StaffChange extends ScheduledAction {

    SMTravel model; // To access the complete model

    public StaffChange(SMTravel model) { this.model = model; }

    // Implementation of timeSequence
    private double[] staffChangeTimeSeq = {0,60,120,180,240,480,540,600,660};
    private int sctIx = 0;

    public double timeSequence()
    {
        double nxtTime = staffChangeTimeSeq[sctIx];
        sctIx++;
        return(nxtTime);
    }

    protected void actionEvent(/*TODO fix this ->double shift*/) {
        //Staff Change action sequence
       if (Operators.OperatorShift.SHIFT_1.getValue() == (int)model.getClock() ||
           Operators.OperatorShift.SHIFT_2.getValue() == (int)model.getClock() ||
           Operators.OperatorShift.SHIFT_3.getValue() == (int)model.getClock() ||
           Operators.OperatorShift.SHIFT_4.getValue() == (int)model.getClock() ||
           Operators.OperatorShift.SHIFT_5.getValue() == (int)model.getClock())
       {
           model.udp.ProcessingStaffChange((int)model.getClock());
       }



    }
}
