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
	
	/* Random Variate Procedure for Arrivals */
	private Exponential interArrDist;  // Exponential distribution for interarrival times
	private final double WMEAN1=10.0;
	protected double duInput()  // for getting next value of duInput
	{
	    double nxtInterArr;

        nxtInterArr = interArrDist.nextDouble();
	    // Note that interarrival time is added to current
	    // clock value to get the next arrival time.
	    return(nxtInterArr+model.getClock());
	}
	//we can choose 2 options here 
	public double triangularDistribution(double a, double b, double c) {
	    double F = (c - a) / (b - a);
	    double rand = Math.random();
	    if (rand < F) {
	        return a + Math.sqrt(rand * (b - a) * (c - a));
	    } else {
	        return b - Math.sqrt((1 - rand) * (b - a) * (b - c));
	    }
	}
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

}
