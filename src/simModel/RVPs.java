package simModel;

class RVPs
{
    SMTravel model; // for accessing the clock
    private DistributionData distData;
    // Data Models - i.e. random veriate generators for distributions
    // are created using Colt classes, define
    // reference variables here and create the objects in the
    // constructor with seeds

    /* Internal Data Models */


    // Constructor
    RVPs(SMTravel model, DistributionData distData)
    {
        this.model = model;
        this.distData = distData;
    }

    //next arrival of cardholder 
    double duCardholder()
    {
        
        int time = (int)model.getClock() / 60;
        return model.getClock() + distData.itCallCardholderInterArrivals[time].next();
    }

    //next arrival of regular customer 
    double duRegular()
    {
        int time = (int) model.getClock() / 60;
        return model.getClock() + distData.itCallRegularInterArrivals[time].next();
    }

    
    int uCardholderType() {
        double randNum = distData.itCardholderType.next();
        int type;
        if(randNum < Constants.PROPORTION_SILVER_CARDHOLDER){
            type = Constants.SILVER;
        } else {
            type = Constants.GOLD;
        }
        return type;
    }
    //get service time according to the operator type
    double uServiceTime(int subject, int operatorType){
        double serviceTime = distData.itServiceTime[subject].next();
        if (operatorType == Constants.SILVER){
            serviceTime *= Constants.SILVER_OPERATOR_REDUCTION;
        } else if (operatorType == Constants.GOLD) {
            serviceTime *= Constants.GOLD_OPERATOR_REDUCTION;
        }
        return serviceTime;
    }
    //get after call-work time corresponding to call type
    double uAfterCallWorkTime(int callType) {
        return distData.itAfterCallTime[callType].next();
    }
    //get utolerated wait time corresponding to call type
    double uToleratedWaitTime(int callType) {
        if(callType == Constants.REGULAR) {
            return distData.itToleratedWaitTime[Constants.REGULAR].next();
        } else { //CARDHOLDER
            return distData.itToleratedWaitTime[Constants.CARDHOLDER].next();
        }
    }
    
    double cardNumberTypingTime() {
        return distData.itTypingTime.next();
    }
    //get random call type from proportion of INFORMATION, RESERVATION AND CHANGES
    int uCallSubject()
    {
        double randNum = distData.itCallSubject.next();
        int callSubject;
        if(randNum < Constants.PROPORTION_SUBJECT[Constants.INFORMATION]){
            callSubject = Constants.INFORMATION;
        } else if (randNum < Constants.PROPORTION_SUBJECT[Constants.INFORMATION] +
                Constants.PROPORTION_SUBJECT[Constants.RESERVATION]) {
            callSubject = Constants.RESERVATION;
        } else {
            callSubject = Constants.CHANGE;
        }
        return callSubject;
    }
}
