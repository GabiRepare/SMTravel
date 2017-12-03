package simModel;

import java.util.ArrayList;

import com.sun.tools.javac.comp.Enter;
import simulationModelling.AOSimulationModel;
import simulationModelling.Behaviour;
import simulationModelling.SequelActivity;

//
// The Simulation model Class
public class SMTravel extends AOSimulationModel
{
	// Constants available from Constants class
	/* Parameter */
        // Define the parameters
	/*-------------Entity Data Structures-------------------*/
	/* Group and Queue Entities */
	//TODO: make sure it works with TrunkLines class
	protected double closingTime;
	protected TrunkLines rgTrunkLines = new TrunkLines(this);
	protected Operators[] rgOperators = new Operators[3];
	protected ArrayList<Call>[] qWaitLines = new ArrayList[3];


	// Define the reference variables to the various 
	// entities with scope Set and Unary
	// Objects can be created here or in the Initialise Action

	/* Input Variables */
	// Define any Independent Input Varaibles here
	
	// References to RVP and DVP objects
	protected RVPs rvp;  // Reference to rvp object - object created in constructor
	//protected DVPs dvp = new DVPs(this);  // Reference to dvp object
	protected UDPs udp = new UDPs(this);
	protected Call icCall=new Call(this);
	// Output object
	protected Output output = new Output(this);
	//call getters in output you can get any values you want
	// Output values - define the public methods that return values
	// required for experimentation.

	// TODO: Constructor
	public SMTravel(double t0time, double tftime, /*define other args,*/ Seeds sd)
	{
		// Initialise parameters here
		
		// Create RVP object with given seed
		rvp = new RVPs(this,sd);

		// rgCounter and qCustLine objects created in Initalise Action
		
		// Initialise the simulation model
		initAOSimulModel(t0time,tftime);   

		     // Schedule the first arrivals and employee scheduling
		Initialise init = new Initialise(this);
		scheduleAction(init);  // Should always be first one scheduled.
		Arrivals arr = new Arrivals(this);
		scheduleAction(arr);
		ArrivalsCardholder arrCardholder = new ArrivalsCardholder(this);
		scheduleAction(arrCardholder);
		// Schedule other scheduled actions and acitvities here
		qWaitLines[0] = new ArrayList<Call>();
		qWaitLines[1] = new ArrayList<Call>();
		qWaitLines[2] = new ArrayList<Call>();

		rgOperators[Constants.GOLD] = new Operators();
		rgOperators[Constants.SILVER] = new Operators();
		rgOperators[Constants.REGULAR] = new Operators();
	}

	/************  Implementation of Data Modules***********/	
	/*
	 * TODO: Testing preconditions
	 */
	protected void testPreconditions(Behaviour behObj)
	{
		reschedule (behObj);
		if(EnterCardNumber.precondition(this) == true)
		{
			EnterCardNumber enterAccount = new EnterCardNumber(this);
			enterAccount.startingEvent();
			scheduleActivity(enterAccount);
		}
		else
		{
			qWaitLines[Constants.REGULAR].add(icCall);
		}
		// Check preconditions of Conditional Activities
		if (TalkToOperator.precondition(this) == true)
		{
			TalkToOperator serviceCall = new TalkToOperator(this);
			serviceCall.startingEvent();
			scheduleActivity(serviceCall);
		}
		// Check preconditions of Interruptions in Extended Activities
	}
	
	public void eventOccured()
	{
		//this.showSBL();
		// Can add other debug code to monitor the status of the system
		// See examples for suggestions on setup logging

		// Setup an updateTrjSequences() method in the Output class
		// and call here if you have Trajectory Sets
		// updateTrjSequences() 
	}

	// Standard Procedure to start Sequel Activities with no parameters
	protected void spStart(SequelActivity seqAct)
	{
		seqAct.startingEvent();
		scheduleActivity(seqAct);
	}	


}