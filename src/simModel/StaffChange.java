package simModel;

class StaffChange{
    SMTravel model;
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

    protected void actionEvent(int shift) {
        //Staff Change action sequence

        ProcessingStaffChange(shift);
    }
}