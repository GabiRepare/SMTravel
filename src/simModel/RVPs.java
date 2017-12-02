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
	protected Call call=new Call(model);
    // Data Models - i.e. random veriate generators for distributions
	// are created using Colt classes, define 
	// reference variables here and create the objects in the
	// constructor with seeds


	// Constructor
	protected RVPs(SMTravel model, Seeds sd) 
	{ 
		this.model = model; 
		// Set up distribution functions
		interArrDist = new Exponential(1.0/WMEAN1,  
				                       new MersenneTwister(sd.arr));
		//uServeTime= new Triangular() ;
		//for after service
		giSrvTm = new Uniform(GIAFMIN,GIAFMAX,sd.goldaftstm);
		grSrvTm = new Uniform(GRAFMIN,GRAFMAX,sd.goldaftstm);
		gcSrvTm = new Uniform(GCAFMIN,GCAFMAX,sd.goldaftstm);
		siSrvTm = new Uniform(GIAFMIN,GIAFMAX,sd.sliveraftstm);
		srSrvTm = new Uniform(GRAFMIN,GRAFMAX,sd.sliveraftstm);
		scSrvTm = new Uniform(GCAFMIN,GCAFMAX,sd.sliveraftstm);
		riSrvTm = new Uniform(GIAFMIN,GIAFMAX,sd.regularaftstm);
		rrSrvTm = new Uniform(GRAFMIN,GRAFMAX,sd.regularaftstm);
		rcSrvTm = new Uniform(GCAFMIN,GCAFMAX,sd.regularaftstm);
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
	
	private final double PROPGOLD = 0.32;
	private final double PROPSIL = 0.68;
	private final double PROPINFO=0.16;
	private final double PROPRES=0.76;
	private final double PROPCH=0.08;
	
	MersenneTwister customerTypeRandGen;
	MersenneTwister callTypeRandGen;
	public Call.CustomerType uCardholderType()
	{//TO DETERMINE THE GOLD AND SIVER IN THE CUTSTOMER
		double randNum = customerTypeRandGen.nextDouble();
		Call.CustomerType customerType;
		if(randNum < PROPSIL) customerType = Call.CustomerType.SILVER;
		else customerType = Call.CustomerType.GOLD;
		return(customerType);
	}
	public Call.CallType uCallType()
	{//TO DETERMINE CALL TYPE;
		double randNum = callTypeRandGen.nextDouble();
		Call.CallType callType;
		if(randNum < PROPRES) callType = Call.CallType.RESERVATION;
		else if(randNum < (PROPRES+PROPINFO))callType =  Call.CallType.INFORMATION;
		else callType =  Call.CallType.CHANGE;
		return(callType);
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
	public double uServiceTime(Call.CallType callType, Operators.OperatorType operatorType)
	{
		double uServiceTime = 0;
		if(operatorType ==Operators.OperatorType.GOLD) {uServiceTime = gSrvTm.nextDouble();}
		if(operatorType == Operators.OperatorType.SILVER) { uServiceTime = sSrvTm.nextDouble();}
		if(operatorType == Operators.OperatorType.REGULAR){ uServiceTime = rSrvTm.nextDouble();}
		else System.out.println("rvpuSrvTm - invalid type"+operatorType);		
		return(uServiceTime);
	}	

	public double uuServiceTime(Call.CallType callType, Operators.OperatorType operatorType){
		if(callType.toString()=="INFORMATION" && operatorType.toString()=="REGULAR" ) {
			return ThreadLocalRandom.current().nextDouble(1.2, 3.75);
		//return triangularDistribution(1.2, 2.05, 3.75);
		}
		if(callType.toString()=="RESERVATION" && operatorType.toString()=="REGULAR" ) {
			return ThreadLocalRandom.current().nextDouble(2.25, 8.60);
			//return triangularDistribution(2.25, 2.95, 8.60);
		}
		if(callType.toString()=="CHANGE" && operatorType.toString()=="REGULAR" ) {
			return ThreadLocalRandom.current().nextDouble(1.20, 5.80);
			//return triangularDistribution(1.20, 1.90, 5.80);
			//hi
		}
		if(callType.toString()=="INFORMATION" && operatorType.toString()=="GOLD" ) {
			return ThreadLocalRandom.current().nextDouble(1.056,3.3);
		}
		if(callType.toString()=="RESERVATION" && operatorType.toString()=="GOLD" ) {
			return ThreadLocalRandom.current().nextDouble(1.98,7.568);
		}
		if(callType.toString()=="CHANGE" && operatorType.toString()=="GOLD" ) {
			return ThreadLocalRandom.current().nextDouble(1.056,5.104);
		}
		if(callType.toString()=="INFORMATION" && operatorType.toString()=="SILVER" ) {
			return ThreadLocalRandom.current().nextDouble(1.14,3.5625);
		}
		if(callType.toString()=="RESERVATION" && operatorType.toString()=="SILVER" ) {
			return ThreadLocalRandom.current().nextDouble(2.1375,8.17);
		}
		if(callType.toString()=="CHANGE" && operatorType.toString()=="SILVER" ) {
			return ThreadLocalRandom.current().nextDouble(1.14,5.51);
		}
		return 0;
	}
	public double uAfterCallWorkTime(Call.CustomerType uCustomerType, Call.CallType callType) {
		double afterSrvTm=0;
		if((uCustomerType==Call.CustomerType.GOLD) && (callType==Call.CallType.INFORMATION)) {
			return afterSrvTm = giSrvTm.nextDouble();
		}
		if((uCustomerType==Call.CustomerType.GOLD) && (callType==Call.CallType.RESERVATION)) {
			return afterSrvTm = grSrvTm.nextDouble();
		}
		if((uCustomerType==Call.CustomerType.GOLD) && (callType==Call.CallType.CHANGE)){
			return afterSrvTm = gcSrvTm.nextDouble();
		}
		if((uCustomerType==Call.CustomerType.SILVER) && (callType==Call.CallType.INFORMATION)) {
			return afterSrvTm = siSrvTm.nextDouble();
		}
		if((uCustomerType==Call.CustomerType.SILVER) && (callType==Call.CallType.RESERVATION)) {
			return afterSrvTm = srSrvTm.nextDouble();
		}
		if((uCustomerType==Call.CustomerType.SILVER) && (callType==Call.CallType.CHANGE)){
			return afterSrvTm = scSrvTm.nextDouble();}
		if((uCustomerType==Call.CustomerType.REGULAR) && (callType==Call.CallType.INFORMATION)) {
				return afterSrvTm = riSrvTm.nextDouble();
			}
		if((uCustomerType==Call.CustomerType.REGULAR) && (callType==Call.CallType.RESERVATION)) {
				return afterSrvTm = rrSrvTm.nextDouble();
			}
		if((uCustomerType==Call.CustomerType.REGULAR) && (callType==Call.CallType.CHANGE)){
				return afterSrvTm = rcSrvTm.nextDouble();}
		return 0;
		
	}
	public double uToleratedWaitTime(Call.CustomerType uCustomerType) {
		if(uCustomerType==Call.CustomerType.GOLD) {
			return ThreadLocalRandom.current().nextDouble(8,17);
		}
		if(uCustomerType==Call.CustomerType.SILVER ) {
			return ThreadLocalRandom.current().nextDouble(8,17);
		}
		if(uCustomerType==Call.CustomerType.REGULAR) {
			return ThreadLocalRandom.current().nextDouble(12,30);
		}
		return 0;
	}
 public double enterCardNumerTime(Call.CustomerType uCustomerType) {
	 if(uCustomerType==Call.CustomerType.GOLD||uCustomerType==Call.CustomerType.SILVER  ) {
			return ThreadLocalRandom.current().nextDouble(7,12);
		}
	 return 0;
 }

}
