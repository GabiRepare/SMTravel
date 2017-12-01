package simModel;

class Arrivals
{
    Arrivals model; // To access the complete model

    public Arrivals(SMTravel model) { this.model = model; }

    @Override
    public double timeSequence()
    {
        return(model.rvp.duCall());
    }

    @Override
    public void actionEvent() {
        // Arrival Action Sequence SCS
        icCall = new Call();
        icCall.uCustomerType = model.rvp.uCustomerType();
        icCall.uCallType = model.rvp.uCallType();
        CallRegistration(iC.Call.uCustomerType);
        SSOV.numCallProcessed++;
        icCall.uStartWaitTime = t;
    }

}