import simModel.*;
import cern.jet.random.engine.*;
import outputAnalysis.ConfidenceInterval;

// Main Method: Experiments
// 
class Experiment
{
    private static final int NUMRUNS = 30;
    private static final double CONF_LEVEL = 0.95;

    public static void main(String[] args)
    {

        double startTime=0.0, endTime=720.0;
        Seeds[] sds = new Seeds[NUMRUNS];
        SMTravel mname;  // Simulation object

        //Output storage variables
        double[] propLongWaitRegular;
        double[] propLongWaitSilver;
        double[] propLongWaitGold;
        double[] propBusySignalRegular;
        double[] propBusySignalCardholder;
        double[] maxTrunkLineUsed;


        // Lets get a set of uncorrelated seeds
        RandomSeedGenerator rsg = new RandomSeedGenerator();
        for(int i=0 ; i<NUMRUNS ; i++) sds[i] = new Seeds(rsg);

        //Initialize output variables
        propLongWaitRegular = new double[NUMRUNS];
        propLongWaitSilver = new double[NUMRUNS];
        propLongWaitGold = new double[NUMRUNS];
        propBusySignalRegular = new double[NUMRUNS];
        propBusySignalCardholder = new double[NUMRUNS];
        maxTrunkLineUsed = new double[NUMRUNS];

        // Loop for NUMRUN simulation runs for each case
        // Case 1
        System.out.println(" Experiment 1");
        for(int i=0 ; i < NUMRUNS ; i++)
        {
            int[][] schedule = {
                    {6,6,6,6,6}, //REGULAR
                    {3,3,3,3,3}, //SILVER
                    {2,2,2,2,2}  //GOLD
            };
            int numTrunkLine = 60;
            int numReservedLine = 4;
            mname = new SMTravel(startTime, endTime, schedule, numTrunkLine,
                    numReservedLine,sds[i]);
            mname.runSimulation();

            //Collect output
            propLongWaitRegular[i] = mname.getPropLongWait()[0];
            propLongWaitSilver[i] = mname.getPropLongWait()[1];
            propLongWaitGold[i] = mname.getPropLongWait()[2];
            propBusySignalRegular[i] = mname.getPropBusySignalRegular();
            propBusySignalCardholder[i] = mname.getPropBusySignalCardholder();
            maxTrunkLineUsed[i] = (double)mname.getMaxTrunkLineUsed();
        }

        //Do the stats
        ConfidenceInterval ciPropLongWaitRegular = new ConfidenceInterval(propLongWaitRegular, CONF_LEVEL);
        ConfidenceInterval ciPropLongWaitSilver = new ConfidenceInterval(propLongWaitSilver, CONF_LEVEL);
        ConfidenceInterval ciPropLongWaitGold = new ConfidenceInterval(propLongWaitGold, CONF_LEVEL);
        ConfidenceInterval ciPropBusyLineSignalRegular = new ConfidenceInterval(propBusySignalRegular, CONF_LEVEL);
        ConfidenceInterval ciPropBusySignalCardholder = new ConfidenceInterval(propBusySignalCardholder, CONF_LEVEL);
        ConfidenceInterval ciMaxTrunkLineUsed = new ConfidenceInterval(maxTrunkLineUsed, CONF_LEVEL);

        //Print results
        String leftAlignFormat = "| %-28s | %8.4f | %8.4f | %8.4f | %8.4f | %6s |%n";
        System.out.format("+------------------------------+----------+----------+----------+----------+--------+%n");
        System.out.format("| OUTPUT NAME                  |   CI Min |   CI Max |      AVG |   STDDEV | STATUS |%n");
        System.out.format("+------------------------------+----------+----------+----------+----------+--------+%n");
        System.out.format(leftAlignFormat, "PropLongWaitRegular", ciPropLongWaitRegular.getCfMin(),
                ciPropLongWaitRegular.getCfMax(), ciPropLongWaitRegular.getPointEstimate(),
                ciPropLongWaitRegular.getStdDev(), getPassFail(ciPropLongWaitRegular,0.15));
        System.out.format("+------------------------------+----------+----------+----------+----------+--------+%n");
        System.out.format(leftAlignFormat, "PropLongWaitSilver", ciPropLongWaitSilver.getCfMin(),
                ciPropLongWaitSilver.getCfMax(), ciPropLongWaitSilver.getPointEstimate(),
                ciPropLongWaitSilver.getStdDev(), getPassFail(ciPropLongWaitSilver,0.05));
        System.out.format("+------------------------------+----------+----------+----------+----------+--------+%n");
        System.out.format(leftAlignFormat, "PropLongWaitGold", ciPropLongWaitGold.getCfMin(),
                ciPropLongWaitGold.getCfMax(), ciPropLongWaitGold.getPointEstimate(),
                ciPropLongWaitGold.getStdDev(), getPassFail(ciPropLongWaitGold,0.02));
        System.out.format("+------------------------------+----------+----------+----------+----------+--------+%n");
        System.out.format(leftAlignFormat, "PropBusyLineSignalRegular", ciPropBusyLineSignalRegular.getCfMin(),
                ciPropBusyLineSignalRegular.getCfMax(), ciPropBusyLineSignalRegular.getPointEstimate(),
                ciPropBusyLineSignalRegular.getStdDev(), getPassFail(ciPropBusyLineSignalRegular,0.2));
        System.out.format("+------------------------------+----------+----------+----------+----------+--------+%n");
        System.out.format(leftAlignFormat, "PropBusySignalCardholder", ciPropBusySignalCardholder.getCfMin(),
                ciPropBusySignalCardholder.getCfMax(), ciPropBusySignalCardholder.getPointEstimate(),
                ciPropBusySignalCardholder.getStdDev(), getPassFail(ciPropBusySignalCardholder,0.02));
        System.out.format("+------------------------------+----------+----------+----------+----------+--------+%n");
        System.out.format(leftAlignFormat, "MaxTrunkLineUsed", ciMaxTrunkLineUsed.getCfMin(),
                ciMaxTrunkLineUsed.getCfMax(), ciMaxTrunkLineUsed.getPointEstimate(),
                ciMaxTrunkLineUsed.getStdDev(), "NA");
        System.out.format("+------------------------------+----------+----------+----------+----------+--------+%n");
   }

   private static String getPassFail(ConfidenceInterval ci, double maxAcceptedValue) {
        if (ci.getCfMax() > maxAcceptedValue){
            return "FAIL";
        }
        return "PASS";
   }
}
