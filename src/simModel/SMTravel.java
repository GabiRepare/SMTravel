package simModel;

import java.util.LinkedList;
import java.util.Queue;

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
    TrunkLines rgTrunkLines;
    Operators[] rgOperators = new Operators[3];
    Queue[] qWaitLines = new Queue[3];


    // Define the reference variables to the various
    // entities with scope Set and Unary
    // Objects can be created here or in the Initialise Action

    /* Input Variables */
    // Define any Independent Input Varaibles here

    // References to RVP and DVP objects
    RVPs rvp;  // Reference to rvp object - object created in constructor
    //protected DVPs dvp = new DVPs(this);  // Reference to dvp object
    UDPs udp = new UDPs(this);
    // Output object
    Output output = new Output();
    //call getters in output you can get any values you want
    // Output values - define the public methods that return values
    // required for experimentation.

    public SMTravel(double t0time, double tftime, int[][] schedule, int numTrunkLine,
                    int numReservedLine, Seeds sd)
    {
        // Initialise parameters here
        qWaitLines[0] = new LinkedList<Call>();
        qWaitLines[1] = new LinkedList<Call>();
        qWaitLines[2] = new LinkedList<Call>();

        rgTrunkLines = new TrunkLines(numTrunkLine, numReservedLine);

        rgOperators[Constants.REGULAR] = new Operators(schedule[Constants.REGULAR]);
        rgOperators[Constants.SILVER] = new Operators(schedule[Constants.SILVER]);
        rgOperators[Constants.GOLD] = new Operators(schedule[Constants.GOLD]);

        // Create RVP object with given seed
        rvp = new RVPs(this,sd);

        // Initialise the simulation model
        initAOSimulModel(t0time,tftime);

        // Schedule the first arrivals and employee scheduling
        Initialise init = new Initialise(this);
        scheduleAction(init);  // Should always be first one scheduled.
        StaffChange change = new StaffChange(this);
        scheduleAction(change);
        ArrivalsRegular arrRegular = new ArrivalsRegular(this);
        scheduleAction(arrRegular);
        ArrivalsCardholder arrCardholder = new ArrivalsCardholder(this);
        scheduleAction(arrCardholder);
        // Schedule other scheduled actions and acitvities here

    }

    /************  Implementation of Data Modules***********/
    /*
     * TODO: Testing preconditions
     */
    protected void testPreconditions(Behaviour behObj)
    {
        reschedule (behObj);
//        if(EnterCardNumber.precondition(this) == true)
//        {
//            EnterCardNumber enterAccount = new EnterCardNumber(this);
//            enterAccount.startingEvent();
//            scheduleActivity(enterAccount);
//        }
//        else
//        {
//            qWaitLines[Constants.REGULAR].add(icCall);
//        }
//        // Check preconditions of Conditional Activities
//        if (TalkToOperator.precondition(this) == true)
//        {
//            TalkToOperator serviceCall = new TalkToOperator(this);
//            serviceCall.startingEvent();
//            scheduleActivity(serviceCall);
//        }
//        // Check preconditions of Interruptions in Extended Activities
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
    void spStart(SequelActivity seqAct)
    {
        seqAct.startingEvent();
        scheduleActivity(seqAct);
    }

    public double[] getPropLongWait(){
        return output.getPropLongWait();
    }

    public double getPropBusySignalCardholder(){
        return output.getPropBusySignalCardholder();
    }

    public double getPropBusySignalRegular(){
        return output.getPropBusySignalRegular();
    }
}
