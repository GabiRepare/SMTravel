package simModel;
import java.util.concurrent.ThreadLocalRandom;
import cern.jet.random.Exponential;
import cern.jet.random.engine.MersenneTwister;
import cern.jet.random.Distributions;
import cern.jet.random.engine.RandomEngine;
import cern.jet.random.Uniform;

class RVPs
{
    SMTravel model; // for accessing the clock
    // Data Models - i.e. random veriate generators for distributions
    // are created using Colt classes, define
    // reference variables here and create the objects in the
    // constructor with seeds


    // Constructor
    protected RVPs(SMTravel model, Seeds sd)
    {
        this.model = model;
        // Set up distribution functions
        interArrDist = new Exponential(1.0/REGULAR1,
                                       new MersenneTwister(sd.arr));
        //uServeTime= new Triangular() ;
        //for after service
        giSrvTm = new Uniform(GIAFMIN,GIAFMAX,sd.goldaftstm);
        grSrvTm = new Uniform(GRAFMIN,GRAFMAX,sd.goldaftstm);
        gcSrvTm = new Uniform(GCAFMIN,GCAFMAX,sd.goldaftstm);
        siSrvTm = new Uniform(SIAFMIN,SIAFMAX,sd.sliveraftstm);
        srSrvTm = new Uniform(SRAFMIN,SRAFMAX,sd.sliveraftstm);
        scSrvTm = new Uniform(SCAFMIN,SCAFMAX,sd.sliveraftstm);
        riSrvTm = new Uniform(RIAFMIN,RIAFMAX,sd.regularaftstm);
        rrSrvTm = new Uniform(RRAFMIN,RRAFMAX,sd.regularaftstm);
        rcSrvTm = new Uniform(RCAFMIN,RCAFMAX,sd.regularaftstm);

        //serTime for
        giSerTM=new Uniform(1.2,3.75,sd.goldstm);
        grSerTM=new Uniform(2.25,8.6,sd.goldstm);
        gcSerTM=new Uniform(1.2,5.8,sd.goldstm);
        siSerTM=new Uniform(1.2,3.75,sd.silverstm);
        srSerTM=new Uniform(2.25,8.6,sd.silverstm);
        scSerTM=new Uniform(1.2,5.8,sd.silverstm);
        riSerTM=new Uniform(1.2,3.75,sd.regularstm);
        rrSerTM=new Uniform(2.25,8.6,sd.regularstm);
        rcSerTM=new Uniform(1.2,5.8,sd.regularstm);

        customerTypeRandGen = new MersenneTwister(sd.custType);
        callTypeRandGen = new MersenneTwister(sd.callType);
    }

    //Customer arrival time
    protected final static double REGULAR1 = 87.0;
    protected final static double REGULAR2 = 165.0;
    protected final static double REGULAR3 = 236.0;
    protected final static double REGULAR4 = 323.0;
    protected final static double REGULAR5 = 277.0;
    protected final static double REGULAR6 = 440.0;
    protected final static double REGULAR7= 269.0;
    protected final static double REGULAR8=342;
    protected final static double REGULAR9=175;
    protected final static double REGULAR10=273;
    protected final static double REGULAR11=115;
    protected final static double REGULAR12=56;
    protected final static double CARDHOLDER1 =89 ;
    protected final static double CARDHOLDER2 =243 ;
    protected final static double CARDHOLDER3 =221 ;
    protected final static double CARDHOLDER4 =180 ;
    protected final static double CARDHOLDER5 =301 ;
    protected final static double CARDHOLDER6 =490 ;
    protected final static double CARDHOLDER7 =394 ;
    protected final static double CARDHOLDER8 =347 ;
    protected final static double CARDHOLDER9 =240 ;
    protected final static double CARDHOLDER10 =269 ;
    protected final static double CARDHOLDER11 = 145;
    protected final static double CARDHOLDER12 =69 ;


    protected Exponential interArrDist;  // Customer Inter Arr. Times
    protected double duCardholder()  // for getting next value of uW(t)
    {
      double nxtArrival;
      double mean;
      if(0 <= model.getClock()&&model.getClock() < 60) mean = CARDHOLDER1;
      else if (60<=model.getClock()&&model.getClock() < 120) mean = CARDHOLDER2;
      else if(120<=model.getClock()&&model.getClock() < 180) mean = CARDHOLDER3;
      else if (180<=model.getClock()&&model.getClock() < 240)mean = CARDHOLDER4;
      else if (240<=model.getClock()&&model.getClock() < 300)mean = CARDHOLDER5;
      else if (300<=model.getClock()&&model.getClock() < 360)mean = CARDHOLDER6;
      else if (360<=model.getClock()&&model.getClock() < 420)mean = CARDHOLDER7;
      else if (420<=model.getClock()&&model.getClock() < 480)mean = CARDHOLDER8;
      else if (480<=model.getClock()&&model.getClock() < 540)mean = CARDHOLDER9;
      else if (540<=model.getClock()&&model.getClock() < 600)mean = CARDHOLDER10;
      else if (600<=model.getClock()&& model.getClock() < 660)mean = CARDHOLDER11;
      else mean = CARDHOLDER12;
      nxtArrival = model.getClock()+interArrDist.nextDouble(1.0/mean);
      if(nxtArrival > model.closingTime) nxtArrival = -1.0;  // Ends time sequence
      return(nxtArrival);
    }
    protected double duRegular()  // for getting next value of uW(t)
    {
      double nxtRegularArrival;
      double mean;
      if(0<=model.getClock()&&model.getClock() < 60) mean = REGULAR1;
      else if (60<=model.getClock()&&model.getClock() < 120) mean = REGULAR2;
      else if(120<=model.getClock()&&model.getClock() < 180) mean = REGULAR3;
      else if (180<=model.getClock()&&model.getClock() < 240)mean = REGULAR4;
      else if (240<=model.getClock()&&model.getClock() < 300)mean = REGULAR5;
      else if (300<=model.getClock()&&model.getClock() < 360)mean = REGULAR6;
      else if (360<=model.getClock()&&model.getClock() < 420)mean = REGULAR7;
      else if (420<=model.getClock()&&model.getClock() < 480)mean = REGULAR8;
      else if (480<=model.getClock()&&model.getClock() < 540)mean = REGULAR9;
      else if (540<=model.getClock()&&model.getClock() < 600)mean = REGULAR10;
      else if (600<=model.getClock()&&model.getClock() < 660)mean = REGULAR11;
      else mean = REGULAR12;
      nxtRegularArrival = model.getClock()+interArrDist.nextDouble(1.0/mean);
      if(nxtRegularArrival > model.closingTime) nxtRegularArrival = -1.0;  // Ends time sequence
      return(nxtRegularArrival);
    }


    private final double PROPGOLD = 0.32;
    private final double PROPSIL = 0.68;
    private final double PROPINFO=0.16;
    private final double PROPRES=0.76;
    private final double PROPCH=0.08;

    MersenneTwister customerTypeRandGen;
    MersenneTwister callTypeRandGen;
    public int uCardholderType()
    {//TO DETERMINE THE TYPE OF CARDHOLDER
        double randNum = customerTypeRandGen.nextDouble();
        int type;
        if(randNum < PROPSIL) type = Constants.SILVER;
        else type = Constants.GOLD;
        return type;
    }
    public int uCallSubject()
    {//TO DETERMINE CALL SUBJECT;
        double randNum = callTypeRandGen.nextDouble();
        int callSubject;
        if(randNum < PROPRES) callSubject = Constants.RESERVATION;
        else if(randNum < (PROPRES+PROPINFO)) callSubject =  Constants.INFORMATION;
        else callSubject =  Constants.CHANGE;
        return callSubject;
    }
    // Min and Max for GOLD service times WITH INFORMATION, RESERVATION, CHANGES
    private final double GIAFMIN = 0.044;
    private final double GIAFMAX = 0.088;
    private final double GRAFMIN = 0.44;
    private final double GRAFMAX =  0.704;
    private final double GCAFMIN = 0.352;
    private final double GCAFMAX = 0.528;
    private final double SIAFMIN = 0.0475;
    private final double SIAFMAX = 0.095;
    private final double SRAFMIN = 0.475;
    private final double SRAFMAX = 0.76;
    private final double SCAFMIN = 0.38;
    private final double SCAFMAX = 0.57;
    private final double RIAFMIN =0.05;
    private final double RIAFMAX =0.10;
    private final double RRAFMIN = 0.50;
    private final double RRAFMAX = 0.80;
    private final double RCAFMIN =0.40;
    private final double RCAFMAX = 0.60;


    /* Internal Data Models */
    private Uniform giSrvTm;  // GOLD info after service
    private Uniform grSrvTm;  // reservation
    private Uniform gcSrvTm; //change
    private Uniform siSrvTm;  // GOLD info after service
    private Uniform srSrvTm;  // reservation
    private Uniform scSrvTm; //change
    private Uniform riSrvTm;  // GOLD info after service
    private Uniform rrSrvTm;  // reservation
    private Uniform rcSrvTm; //change
    // Method
    public double triangularDistribution(double a, double b, double c, Uniform serTm) {
        double rand=serTm.nextDouble();
        double F = (c - a) / (b - a);
        if (rand < F) {
            return a + Math.sqrt(rand * (b - a) * (c - a));
        } else {
            return b - Math.sqrt((1 - rand) * (b - a) * (b - c));
        }
    }
    private Uniform giSerTM;  // GOLD info  service
    private Uniform grSerTM;  // reservation
    private Uniform gcSerTM; //change
    private Uniform siSerTM;  // GOLD info  service
    private Uniform srSerTM;  // reservation
    private Uniform scSerTM; //change
    private Uniform riSerTM;  // GOLD info service
    private Uniform rrSerTM;  // reservation
    private Uniform rcSerTM; //change
    public double uServiceTime(int subject, int operatorType){
        if(subject == Constants.INFORMATION && operatorType == Constants.REGULAR ) {
        return triangularDistribution(1.2, 2.05, 3.75,riSerTM);
        }
        if(subject == Constants.RESERVATION && operatorType == Constants.REGULAR ) {
            return triangularDistribution(2.25, 2.95, 8.60,rrSerTM);
        }
        if(subject == Constants.CHANGE && operatorType == Constants.REGULAR ) {
            return triangularDistribution(1.20, 1.90, 5.80,rcSerTM);
        }
        if(subject == Constants.INFORMATION && operatorType == Constants.GOLD ) {
            return triangularDistribution(1.056, 1.804, 3.3,giSerTM);
        }
        if(subject == Constants.RESERVATION && operatorType == Constants.GOLD ) {
            return triangularDistribution(1.98, 2.596, 7.568,grSerTM);
        }
        if(subject == Constants.CHANGE && operatorType == Constants.GOLD ) {
            return triangularDistribution(1.056,1.672, 5.104,gcSerTM);
        }
        if(subject == Constants.INFORMATION && operatorType == Constants.SILVER ) {
            return triangularDistribution(1.14,1.9475, 3.5625,siSerTM);
        }
        if(subject == Constants.RESERVATION && operatorType == Constants.SILVER ) {
            return triangularDistribution(2.1375,2.8025, 8.17,srSerTM);
        }
        if(subject == Constants.CHANGE && operatorType == Constants.SILVER) {
            return triangularDistribution(1.14,1.805, 5.51,scSerTM);
        }
        return 0;
    }
    public double uAfterCallWorkTime(int operatorType, int callType) {
        double afterSrvTm=0;
        if((operatorType == Constants.GOLD) && (callType == Constants.INFORMATION)) {
            return afterSrvTm = giSrvTm.nextDouble();
        }
        if((operatorType == Constants.GOLD) && (callType == Constants.RESERVATION)) {
            return afterSrvTm = grSrvTm.nextDouble();
        }
        if((operatorType == Constants.GOLD) && (callType == Constants.CHANGE)){
            return afterSrvTm = gcSrvTm.nextDouble();
        }
        if((operatorType == Constants.SILVER) && (callType == Constants.INFORMATION)) {
            return afterSrvTm = siSrvTm.nextDouble();
        }
        if((operatorType == Constants.SILVER) && (callType == Constants.RESERVATION)) {
            return afterSrvTm = srSrvTm.nextDouble();
        }
        if((operatorType == Constants.SILVER) && (callType == Constants.CHANGE)){
            return afterSrvTm = scSrvTm.nextDouble();}
        if((operatorType == Constants.REGULAR) && (callType == Constants.INFORMATION)) {
                return afterSrvTm = riSrvTm.nextDouble();
            }
        if((operatorType == Constants.REGULAR) && (callType == Constants.RESERVATION)) {
                return afterSrvTm = rrSrvTm.nextDouble();
            }
        if((operatorType == Constants.REGULAR) && (callType == Constants.CHANGE)){
                return afterSrvTm = rcSrvTm.nextDouble();}
        return afterSrvTm;

    }
    public double uToleratedWaitTime(int type) {
        if(type == Constants.REGULAR) {
            return ThreadLocalRandom.current().nextDouble(12,30);
        } else { //CARDHOLDER
            return ThreadLocalRandom.current().nextDouble(8,17);
        }
    }
 public double enterCardNumerTime(int minWait, int maxWait) {

     return ThreadLocalRandom.current().nextDouble(minWait,maxWait);
 }

}
