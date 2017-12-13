package simModel;

import simulationModelling.ScheduledAction;

class StaffChange extends ScheduledAction {

    SMTravel model; // To access the complete model

    public StaffChange(SMTravel model) { this.model = model; }

    // Implementation of timeSequence
    private int sctIx = 0;

    public double timeSequence()
    {
        return Constants.STAFF_CHANGE_TIME_SEQ[sctIx++];
    }

    protected void actionEvent() {
        model.udp.ProcessingStaffChange();
    }
}
