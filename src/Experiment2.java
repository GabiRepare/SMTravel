import simModel.*;
import cern.jet.random.engine.*;
import outputAnalysis.ConfidenceInterval;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.IntStream;


class Experiment2
{
    private static final int NUMRUNS = 20;
    private static final double CONF_LEVEL = 0.95;
    private static final int LOGGING_FREQUENCY = 5000;//ms
    private static final int FAIL_LIST_SIZE = 10000;
    private static final int NUMBER_OF_THREAD = 22;

    public static void main(String[] args)
    {

        double startTime=0.0, endTime=720.0;
        Seeds[] sds = new Seeds[NUMRUNS];

        // Lets get a set of uncorrelated seeds
        RandomSeedGenerator rsg = new RandomSeedGenerator();
        for(int i=0 ; i<NUMRUNS ; i++) sds[i] = new Seeds(rsg);

        //The range  of values from zero to test for each parameter of the schedule
        //Note: all tested values are strictly smaller than the specified range value
        int[][] scheduleRange = {
                {30,30,30,30,30}, //REGULAR
                {12,12,12,12,12}, //SILVER
                {12,12,12,12,12}  //GOLD
        };
        //This is 6046617600000 possibilities

        //Closest prime is 6046617600013
        BigInteger m = BigInteger.valueOf(1504591950643200031L);
        BigInteger a = BigInteger.valueOf(1504591950623200007L); //Random prime in range
        BigInteger b = BigInteger.valueOf(0);
        BigInteger seed = BigInteger.valueOf(1);

        //Get a linear congruential generator
        LcgRandom randSchedule = new LcgRandom(a,b,m,seed);

        final Object obj = new Object(); //Used for thread synchronisation

        BruteForceState state = new BruteForceState(m, randSchedule, scheduleRange, NUMRUNS,
                startTime, endTime, sds, CONF_LEVEL, FAIL_LIST_SIZE, LOGGING_FREQUENCY);
        for (int i = 0; i < NUMBER_OF_THREAD; i++) {
            Runnable agent = new RunnableAgent(state, obj);
            new Thread(agent).start();
        }

    }
}

class BruteForceState {
    private int failListSize;
    private LcgRandom rand;

    BigInteger m;
    int[][] scheduleRange;
    Schedule bestSchedule;
    TreeSet<Schedule> failingSchedules;
    long lastLog;
    Boolean newBestScheduleFound;
    int numberFilteredOut;
    BigInteger n;
    int numberOfRuns;
    double startTime;
    double endTime;
    Seeds[] sds;
    double confLevel;
    int loggingFrequency;
    boolean isWritingLog;
    boolean isReadingFailList;
    boolean isWritingFailList;

    BruteForceState(BigInteger m, LcgRandom randGen, int[][] scheduleRange, int numberOfRuns,
                    double startTime, double endTime, Seeds[] sds, double confLevel,
                    int failListSize, int loggingFrequency) {
        this.m = m;
        this.rand = randGen;
        this.scheduleRange = scheduleRange;
        bestSchedule = Schedule.MAX_VALUE();
        failingSchedules = new TreeSet<>();
        lastLog = 0;
        newBestScheduleFound = false;
        numberFilteredOut = 0;
        n = BigInteger.valueOf(0);
        this.numberOfRuns = numberOfRuns;
        this.startTime = startTime;
        this.endTime = endTime;
        this.sds = sds;
        this.confLevel = confLevel;
        this.failListSize = failListSize;
        this.loggingFrequency = loggingFrequency;
        isWritingLog = false;
        isReadingFailList = false;
        isWritingFailList = false;

    }

    synchronized BigInteger getRandBigInt() {
        rand.next();
        n = n.add(BigInteger.ONE);
        return rand.getState();
    }

    synchronized void addFailingSchedule(Schedule failingSchedule) {
        failingSchedules.add(failingSchedule);
        if (failingSchedules.size() > failListSize) {
            failingSchedules.pollFirst();
        }
    }

    synchronized void log(String message) {
        String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        System.out.println(timestamp + ": " + message);
        try {
            FileWriter fw = new FileWriter("log", true);
            BufferedWriter writer = new BufferedWriter(fw);
            writer.write(timestamp + ": " + message);
            writer.newLine();
            writer.close();
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class RunnableAgent implements Runnable {
    private final Object obj;
    private BruteForceState state;

    RunnableAgent(BruteForceState state, Object obj){
        this.state = state;
        this.obj = obj;
    }

    public void run() {
        //Loop thru all schedule combinations
        while(state.n.compareTo(state.m) < 0) {
            Schedule schedule = getScheduleFromBigInt(state.getRandBigInt(), state.scheduleRange);

            if (schedule.cost < state.bestSchedule.cost && !knownToFail(schedule, state.failingSchedules)) {
                //Initialize output variables
                double[] propLongWaitRegular = new double[state.numberOfRuns];
                double[] propLongWaitSilver = new double[state.numberOfRuns];
                double[] propLongWaitGold = new double[state.numberOfRuns];
                double[] propBusySignalRegular = new double[state.numberOfRuns];
                double[] propBusySignalCardholder = new double[state.numberOfRuns];

                // Loop for NUMRUN simulation runs for each case
                // Case 1
                for (int i = 0; i < state.numberOfRuns; i++) {
                    int numTrunkLine = 10000;
                    int numReservedLine = 0;
                    SMTravel mname = new SMTravel(state.startTime, state.endTime, schedule.schedule, numTrunkLine,
                            numReservedLine, state.sds[i]);
                    mname.runSimulation();

                    //Collect output
                    propLongWaitRegular[i] = mname.getPropLongWait()[0];
                    propLongWaitSilver[i] = mname.getPropLongWait()[1];
                    propLongWaitGold[i] = mname.getPropLongWait()[2];
                    propBusySignalRegular[i] = mname.getPropBusySignalRegular();
                    propBusySignalCardholder[i] = mname.getPropBusySignalCardholder();
                }

                //Do the stats
                ConfidenceInterval ciPropLongWaitRegular = new ConfidenceInterval(propLongWaitRegular, state.confLevel);
                ConfidenceInterval ciPropLongWaitSilver = new ConfidenceInterval(propLongWaitSilver, state.confLevel);
                ConfidenceInterval ciPropLongWaitGold = new ConfidenceInterval(propLongWaitGold, state.confLevel);
                ConfidenceInterval ciPropBusyLineSignalRegular = new ConfidenceInterval(propBusySignalRegular, state.confLevel);
                ConfidenceInterval ciPropBusySignalCardholder = new ConfidenceInterval(propBusySignalCardholder, state.confLevel);

                if (getPassFail(ciPropLongWaitRegular, 0.15)
                        && getPassFail(ciPropLongWaitSilver, 0.05)
                        && getPassFail(ciPropLongWaitGold, 0.02)
                        && getPassFail(ciPropBusyLineSignalRegular, 0.2)
                        && getPassFail(ciPropBusySignalCardholder, 0.02)) {

                    state.newBestScheduleFound = true;
                    state.bestSchedule = schedule;
                }
                else {
                    synchronized (obj) {
                        try {
                            if (state.isReadingFailList) {
                                try{obj.wait();} catch (Exception e) {e.printStackTrace();}
                            }
                            state.isWritingFailList = true;
                            state.addFailingSchedule(schedule);
                            state.isWritingFailList = false;
                            obj.notifyAll();
                        } catch (Exception e) {e.printStackTrace();}
                    }
                }
            } else {
                state.numberFilteredOut++;
            }

            //Logging the results
            if (System.currentTimeMillis() - state.lastLog >= state.loggingFrequency && !state.isWritingLog) {
                state.isWritingLog = true;
                state.lastLog = System.currentTimeMillis();
                String message = "n=" + state.n.toString() + " (";
                message += state.n.multiply(BigInteger.valueOf(100)).divide(state.m).toString() + "%) ";
                message += "nFilteredOut=" + state.numberFilteredOut + " ";
                if (state.newBestScheduleFound) {
                    state.newBestScheduleFound = false;
                    message += "New schedule found! ";
                    message += "schedule = " + state.bestSchedule + " ";
                    message += "cost = " + state.bestSchedule.cost + "$";
                }
                state.log(message);
                state.numberFilteredOut = 0;
                state.isWritingLog = false;
            }
        }

        //If this point is ever reached
        state.log("Finished brute-forcing");
    }

    private Schedule getScheduleFromBigInt (BigInteger randInt, int[][] range) {
        int[][] schedule = new int[3][5];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 5; j++) {
                schedule[i][j] = randInt.mod(BigInteger.valueOf(range[i][j])).intValue();
                randInt = randInt.divide(BigInteger.valueOf(range[i][j]));
            }
        }
        return new Schedule(schedule);
    }

    private boolean knownToFail(Schedule schedule, TreeSet<Schedule> failingSchedules) {
        synchronized (obj) {
            try {
                if (state.isWritingFailList) {
                    obj.wait();
                }
                state.isReadingFailList = true;
                boolean willFail = false;
                Iterator<Schedule> it = failingSchedules.descendingIterator();
                while (it.hasNext() && !willFail) {
                    boolean allSmaller = true;
                    Schedule curr = it.next();
                    for (int j = 0; j < 3; j++) {
                        for (int k = 0; k < 5; k++) {
                            if (schedule.schedule[j][k] > curr.schedule[j][k]) {
                                allSmaller = false;
                            }
                        }
                    }
                    if (allSmaller) {
                        willFail = true;
                    }
                }
                state.isReadingFailList = false;
                obj.notifyAll();
                return willFail;
            } catch (Exception e) {e.printStackTrace();}
        }
        return false;
    }

    private boolean getPassFail(ConfidenceInterval ci, double maxAcceptedValue) {
        return (ci.getCfMax() <= maxAcceptedValue);
    }
}

class Schedule implements Comparable{
    int[][] schedule;
    int cost;

    static Schedule MAX_VALUE (){
        Schedule temp = new Schedule(new int[3][5]);
        for(int i = 0; i < 3; i++) {
            for (int j = 0; j < 5; j++) {
                temp.schedule[i][j] = Integer.MAX_VALUE;
            }
        }
        temp.cost = Integer.MAX_VALUE;
        return temp;
    }

    Schedule (int[][] schedule) {
            this.schedule = schedule;
            cost = getCostSchedule(schedule);
    }

    private int getCostSchedule (int[][] schedule) {
        int sumRegular = IntStream.of(schedule[0]).sum();
        int sumSilver = IntStream.of(schedule[1]).sum();
        int sumGold = IntStream.of(schedule[2]).sum();
        return sumRegular * 16 * 8 + sumSilver * 20 * 8 + sumGold * 23 * 8;
    }

    public int compareTo(Object a) throws ClassCastException {
        if (!(a instanceof Schedule)) {
            throw new ClassCastException("A Schedule object expected.");
        }
        return this.cost - ((Schedule) a).cost;
    }

    public String toString(){
        return "{"+Arrays.toString(schedule[0])+", "+Arrays.toString(schedule[1])
                +", "+Arrays.toString(schedule[2])+"}";
    }
}

