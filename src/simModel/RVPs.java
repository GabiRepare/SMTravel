package simModel;
import java.util.concurrent.ThreadLocalRandom;
import cern.jet.random.Exponential;
import cern.jet.random.engine.MersenneTwister;

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

	
	public double uServiceTime(Call.CallType callType, Operators.OperatorType operatorType){
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
	public double uAfterCallWorkTime(Call.CustomerType uCustomerType) {
		if(uCustomerType.toString()=="GOLD" ) {
			return ThreadLocalRandom.current().nextDouble(0.05, 0.10);
		}
		if(uCustomerType.toString()=="SILVER" ) {
			return ThreadLocalRandom.current().nextDouble(0.05, 0.80);
		}
		if(uCustomerType.toString()=="REGULAR" ) {
			return ThreadLocalRandom.current().nextDouble(0.40, 0.60);
		}
		return 0;
		
	}
	public double uToleratedWaitTime(Call.CustomerType uCustomerType) {
		if(uCustomerType.toString()=="GOLD" ) {
			return ThreadLocalRandom.current().nextDouble(8,17);
		}
		if(uCustomerType.toString()=="SILVER" ) {
			return ThreadLocalRandom.current().nextDouble(8,17);
		}
		if(uCustomerType.toString()=="REGULAR" ) {
			return ThreadLocalRandom.current().nextDouble(12,30);
		}
		return 0;
	}


}
