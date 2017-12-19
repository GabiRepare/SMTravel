package simModel;
import cern.jet.random.Exponential;
import cern.jet.random.engine.MersenneTwister;
import cern.jet.random.Uniform;
import dataModelling.TriangularVariate;

class RVPs
{
    SMTravel model; // for accessing the clock
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
        dmCardholderType = new MersenneTwister(sd.cardholderType);
        dmServiceTime = new TriangularVariate[3];
        dmAfterCallTime = new Uniform[3];
        for (int i = 0; i < 3; i++){
            dmServiceTime[i] = new TriangularVariate(
                    Constants.SERVICE_TIME[i][Constants.MIN],
                    Constants.SERVICE_TIME[i][Constants.MEAN],
                    Constants.SERVICE_TIME[i][Constants.MAX],
                    new MersenneTwister(sd.serviceTime[i])
            );
            dmAfterCallTime[i] = new Uniform(
                    Constants.AFTER_CALL_TIME[i][Constants.MIN],
                    Constants.AFTER_CALL_TIME[i][Constants.MAX],
                    new MersenneTwister(sd.afterCallTime[i])
            );
        }
        dmToleratedWaitTime = new Uniform[2];
        for (int i = 0; i < 2; i++) {
            dmToleratedWaitTime[i] = new Uniform(
                    Constants.TOLERATED_WAIT_TIME[i][Constants.MIN],
                    Constants.TOLERATED_WAIT_TIME[i][Constants.MAX],
                    new MersenneTwister(sd.toleratedWaitTime[i])
            );
        }
        dmTypingTime = new Uniform(
                Constants.TYPING_TIME[Constants.MIN],
                Constants.TYPING_TIME[Constants.MAX],
                new MersenneTwister(sd.typingTime));
        dmCallSubject = new MersenneTwister(sd.callSubject);
    }


    double duCardholder()
    {
        int time = (int)model.getClock() / 60;
        return model.getClock() + dmCallCardholderInterArrivals[time].nextDouble();
    }


    double duRegular()
    {
        int time = (int) model.getClock() / 60;
        return model.getClock() + dmCallRegularInterArrivals[time].nextDouble();
    }

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

    double uServiceTime(int callSubject, int operatorType){
        double serviceTime = dmServiceTime[callSubject].next();
        if (operatorType == Constants.SILVER){
            serviceTime *= Constants.SILVER_OPERATOR_REDUCTION;
        } else if (operatorType == Constants.GOLD) {
            serviceTime *= Constants.GOLD_OPERATOR_REDUCTION;
        }
        return serviceTime;
    }

    double uAfterCallWorkTime(int callSubject, int operatorType) {
        double afterCallTime = dmAfterCallTime[callSubject].nextDouble();
        if (operatorType == Constants.SILVER){
            afterCallTime *= Constants.SILVER_OPERATOR_REDUCTION;
        } else if (operatorType == Constants.GOLD) {
            afterCallTime *= Constants.GOLD_OPERATOR_REDUCTION;
        }
        return afterCallTime;
    }

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
