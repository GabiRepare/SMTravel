package simModel;
import cern.jet.random.Exponential;
import cern.jet.random.engine.MersenneTwister;
import cern.jet.random.Uniform;
import dataModelling.TriangularVariate;

class RVPs
{   //access to model:SMTravel
    SMTravel model; 
    // for accessing the clock
    // Data Models - i.e. random veriate generators for distributions
    // are created using Colt classes, define
    // reference variables here and create the objects in the
    // constructor with seeds

    /* Internal Data Models */
    private Exponential[] dmCallRegularInterArrivals;
    private Exponential[] dmCallCardholderInterArrivals;
    private MersenneTwister dmCardholderType;
    private TriangularVariate[] dmServiceTime;
    private Uniform[] dmAfterCallTime;
    private Uniform[] dmToleratedWaitTime;
    private Uniform dmTypingTime;
    private MersenneTwister dmCallSubject;


    // Constructor
    RVPs(SMTravel model, Seeds sd)
    {
        this.model = model;
        // Set up distribution functions
        dmCallRegularInterArrivals = new Exponential[12];
        dmCallCardholderInterArrivals = new Exponential[12];
        for (int i = 0; i < 12; i++){
            dmCallRegularInterArrivals[i] = new Exponential(Constants.REGULAR_ARRIVAL_RATE[i],
                    new MersenneTwister(sd.arrRegular[i]));
            dmCallCardholderInterArrivals[i] = new Exponential(Constants.CARDHOLDER_ARRIVAL_RATE[i],
                    new MersenneTwister(sd.arrCardholder[i]));
        }
        //using random generator in libary
        dmCardholderType = new MersenneTwister(sd.cardholderType);
        dmServiceTime = new TriangularVariate[3];
        dmAfterCallTime = new Uniform[3];
        //get random service time
        for (int i = 0; i < 3; i++){
            dmServiceTime[i] = new TriangularVariate(
                    Constants.SERVICE_TIME[i][Constants.MIN],
                    Constants.SERVICE_TIME[i][Constants.MEAN],
                    Constants.SERVICE_TIME[i][Constants.MAX],
                    new MersenneTwister(sd.serviceTime[i])
            );
         //get random after call work time from uniform distribution generator
            dmAfterCallTime[i] = new Uniform(
                    Constants.AFTER_CALL_TIME[i][Constants.MIN],
                    Constants.AFTER_CALL_TIME[i][Constants.MAX],
                    new MersenneTwister(sd.afterCallTime[i])
            );
        }
        //get random tolerate wait time from uniform distribution generator
        dmToleratedWaitTime = new Uniform[2];
        for (int i = 0; i < 2; i++) {
            dmToleratedWaitTime[i] = new Uniform(
                    Constants.TOLERATED_WAIT_TIME[i][Constants.MIN],
                    Constants.TOLERATED_WAIT_TIME[i][Constants.MAX],
                    new MersenneTwister(sd.toleratedWaitTime[i])
            );
        }
        //get random typeing type for customer from uniform distribution generator
        dmTypingTime = new Uniform(
                Constants.TYPING_TIME[Constants.MIN],
                Constants.TYPING_TIME[Constants.MAX],
                new MersenneTwister(sd.typingTime));
       //get random call type/subject from random generator
        dmCallSubject = new MersenneTwister(sd.callSubject);
    }

    //arrivals for cardholders
    double duCardholder()
    {
        int time = (int)model.getClock() / 60;
        return model.getClock() + dmCallCardholderInterArrivals[time].nextDouble();
    }

    //arrivals for regular custmer
    double duRegular()
    {
        int time = (int) model.getClock() / 60;
        return model.getClock() + dmCallRegularInterArrivals[time].nextDouble();
    }
    //Provides the type of a cardholder customer, return GOLD or SILVER
    int uCardholderType() {
        double randNum = dmCardholderType.nextDouble();
        int type;
        if(randNum < Constants.PROPORTION_SILVER_CARDHOLDER){
            type = Constants.SILVER;
        } else {
            type = Constants.GOLD;
        }
        return type;
    }
    //Provides the service time corrspoding to call type/subject and operator type
    double uServiceTime(int subject, int operatorType){
        double serviceTime = dmServiceTime[subject].next();
        if (operatorType == Constants.SILVER){
            serviceTime *= Constants.SILVER_OPERATOR_REDUCTION;
        } else if (operatorType == Constants.GOLD) {
            serviceTime *= Constants.GOLD_OPERATOR_REDUCTION;
        }
        return serviceTime;
    }
    //return after call work type corresponding to call type
    double uAfterCallWorkTime(int callType) {
        return dmAfterCallTime[callType].nextDouble();
    }
    //return tolerated wait time corresponding to call type
    double uToleratedWaitTime(int callType) {
        if(callType == Constants.REGULAR) {
            return dmToleratedWaitTime[Constants.REGULAR].nextDouble();
        } else { //CARDHOLDER
            return dmToleratedWaitTime[Constants.CARDHOLDER].nextDouble();
        }
    }
    
    double cardNumberTypingTime() {
        return dmTypingTime.nextDouble();
    }
    //return call type/subject
    int uCallSubject()
    {
        double randNum = dmCallSubject.nextDouble();
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
