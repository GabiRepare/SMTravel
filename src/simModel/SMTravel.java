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
    private boolean traceFlag;
    // Constants available from Constants class
    /* Parameter */
        // Define the parameters
    /*-------------Entity Data Structures-------------------*/
    /* Group and Queue Entities */
    TrunkLines rgTrunkLines;
    Operators[] rgOperators = new Operators[3];
    Queue[] qWaitLines = new Queue[3];

    /* Input Variables */
    // Define any Independent Input Varaibles here

    // References to RVP and DVP objects
    RVPs rvp;  // Reference to rvp object - object created in constructor
    UDPs udp = new UDPs(this);
    // Output object
    Output output = new Output();
    //call getters in output you can get any values you want
    // Output values - define the public methods that return values
    // required for experimentation.

    public SMTravel(double t0time, double tftime, int[][] schedule, int numTrunkLine,
                    int numReservedLine, Seeds sd, boolean traceFlag)
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

        this.traceFlag = traceFlag;
    }

    public SMTravel(double t0time, double tftime, int[][] schedule, int numTrunkLine,
                    int numReservedLine, Seeds sd) {
        this(t0time, tftime, schedule, numTrunkLine, numReservedLine, sd, false);
    }

    /************  Implementation of Data Modules***********/

    protected void testPreconditions(Behaviour behObj)
    {
        reschedule (behObj);
    }

    public void eventOccured()
    {
        if(traceFlag) {
            System.out.printf("Clock: %-9.3f RG.TrunkLines.numTrunkLineInUse: %d\n",
                    getClock(), rgTrunkLines.numTrunkLineInUse);
            System.out.printf("Q.WaitLines[REGULAR].n: %d Q.WaitLines[SILVER].n: %d Q.WaitLines[GOLD].n %d\n",
                    qWaitLines[Constants.REGULAR].size(),
                    qWaitLines[Constants.SILVER].size(),
                    qWaitLines[Constants.GOLD].size());
            System.out.printf("RG.Operators[REGULAR].numFreeOperators: %d\n",
                    rgOperators[Constants.REGULAR].numFreeOperators);
            System.out.printf("RG.Operators[SILVER].numFreeOperators:  %d\n",
                    rgOperators[Constants.SILVER].numFreeOperators);
            System.out.printf("RG.Operators[GOLD].numFreeOperators:    %d\n",
                    rgOperators[Constants.GOLD].numFreeOperators);
            this.showSBL();
        }
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

    public int getMaxTrunkLineUsed() { return output.maxTrunkLineUsed;}
}
