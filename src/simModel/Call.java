package simModel;

class Call {
    int uType;
    int uSubject;
    double uToleratedWaitTime;
    double startWaitTime;

    @Override
    public String toString() {
        return ("Call: uType = " + Constants.CALL_TYPES[uType]
                    + ", uSubject = " + Constants.CALL_SUBJECTS[uSubject]
                    + ", uToleratedWaitTime = " + uToleratedWaitTime
                    + ", startWaitTime = " + startWaitTime);
    }
}
