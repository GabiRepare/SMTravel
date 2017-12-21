import simModel.*;
import cern.jet.random.engine.*;
import outputAnalysis.ConfidenceInterval;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.IntStream;


class Experiment3
{
    private static final int NUMRUNS = 20;
    private static final double CONF_LEVEL = 0.95;
    private static final int LOGGING_FREQUENCY = 5000;//ms
    private static final int NUMBER_OF_THREAD = 4;

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
        randSchedule.next();

        BruteForceState2 state = new BruteForceState2(m, randSchedule, scheduleRange, NUMRUNS,
                startTime, endTime, sds, CONF_LEVEL, LOGGING_FREQUENCY);
        for (int i = 0; i < NUMBER_OF_THREAD; i++) {
            Runnable agent = new RunnableAgent2(state);
            Thread temp = new Thread(agent);
            temp.setName("Agent"+i);
            temp.start();
        }

    }
}

class BruteForceState2 {
    private LcgRandom rand;

    BigInteger m;
    int[][] scheduleRange;
    Parameter bestParameter;
    long lastLog;
    Boolean newBestParameterFound;
    BigInteger n;
    int numberOfRuns;
    double startTime;
    double endTime;
    Seeds[] sds;
    double confLevel;
    int loggingFrequency;
    boolean isWritingLog;

    BruteForceState2(BigInteger m, LcgRandom randGen, int[][] scheduleRange, int numberOfRuns,
                     double startTime, double endTime, Seeds[] sds, double confLevel,
                     int loggingFrequency) {
        this.m = m;
        this.rand = randGen;
        this.scheduleRange = scheduleRange;
        bestParameter = Parameter.MAX_VALUE();
        lastLog = 0;
        newBestParameterFound = false;
        n = BigInteger.valueOf(0);
        this.numberOfRuns = numberOfRuns;
        this.startTime = startTime;
        this.endTime = endTime;
        this.sds = sds;
        this.confLevel = confLevel;
        this.loggingFrequency = loggingFrequency;
        isWritingLog = false;

    }

    synchronized BigInteger getRandBigInt() {
        rand.next();
        n = n.add(BigInteger.ONE);
        return rand.getState();
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

class RunnableAgent2 implements Runnable {
    private BruteForceState2 state;

    RunnableAgent2(BruteForceState2 state){
        this.state = state;
    }

    public void run() {
        //Loop thru all schedule combinations
        while(state.n.compareTo(state.m) < 0) {
            Parameter param = new Parameter(
                    getScheduleFromBigInt(state.getRandBigInt(), state.scheduleRange),
                    50,
                    5);
            List<Parameter> visited = new LinkedList<>();
            int paramScore = getScoreParameter(param);
            boolean hasImproved = true;
            int backoffBudget = 30;

            //Continue to try variations if the still improve
            while(hasImproved) {
                //Keep a list of visited Parameters to reduce loops
                visited.add(param);
                //All the different parameters you can get by changing only one parameter by one increment
                List<Parameter> paramVariations = getParameterVariations(param, state.scheduleRange);
                hasImproved = false;
                //Get all the scores associated with the variations sorted
                PriorityQueue<ScoreResult> results = new PriorityQueue<>();
                for (Parameter x: paramVariations) {
                    results.add(new ScoreResult(x, getScoreParameter(x)));
                }
                //Check to see if the best result is an improvement on the original parameter
                ScoreResult bestResult = results.poll();
                if (bestResult.score < paramScore || backoffBudget > 0) {
                    //Find the least worst backoff parameter that has never been visited
                    if (bestResult.score >= paramScore) {
                        while(visited.contains(bestResult.param) && !results.isEmpty()) {
                            bestResult = results.poll();
                        }
                        if (!visited.contains(bestResult.param)){
                            backoffBudget--;
                            paramScore = bestResult.score;
                            param = bestResult.param;
                            hasImproved = true;
                        }

                    } else { //This happens when an actual improving parameter has been found
                        paramScore = bestResult.score;
                        param = bestResult.param;
                        hasImproved = true;
                    }
//                    if(Thread.currentThread().getName().equals("Agent0")) {
//                        System.out.println(Thread.currentThread().getName() + " get score for " + param + " s="+paramScore);
//                    }
                }
            }
            //Parameter cannot be improved anymore with single increments
            if (paramScore < 100000) { //Check if all requirements passed
                if (param.getCost() < state.bestParameter.getCost()) {
                    state.newBestParameterFound = true;
                    state.bestParameter = param;
                } else if (param.getCost() == state.bestParameter.getCost() &&
                        !state.bestParameter.equals(param)) {
                    state.newBestParameterFound = true;
                    state.bestParameter = param;
                }
            }

            //Logging the results
            if (System.currentTimeMillis() - state.lastLog >= state.loggingFrequency && !state.isWritingLog) {
                state.isWritingLog = true;
                state.lastLog = System.currentTimeMillis();
                String message = "n=" + state.n.toString() + " ";
                if (state.newBestParameterFound) {
                    state.newBestParameterFound = false;
                    message += "New Parameter set found! ";
                    message += state.bestParameter;
                }
                state.log(message);
                state.isWritingLog = false;
            }
        }

        //If this point is ever reached
        state.log("Finished brute-forcing");
    }

    private int[][] getScheduleFromBigInt (BigInteger randInt, int[][] range) {
        int[][] schedule = new int[3][5];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 5; j++) {
                schedule[i][j] = randInt.mod(BigInteger.valueOf(range[i][j])).intValue();
                randInt = randInt.divide(BigInteger.valueOf(range[i][j]));
            }
        }
        return schedule;
    }

    private boolean getPassFail(ConfidenceInterval ci, double maxAcceptedValue) {
        return (ci.getCfMax() <= maxAcceptedValue);
    }

    private List<Parameter> getParameterVariations(Parameter param, int[][] scheduleRange) {
        List<Parameter> paramVariations = new LinkedList<>();
        for (int i = 0; i < 30; i++) {
            if (i % 2 == 1 && param.schedule[(i/2)%3][((i/2)/3)%5] < scheduleRange[(i/2)%3][((i/2)/3)%5] - 1) {
                Parameter temp = param.clone();
                temp.schedule[(i/2)%3][((i/2)/3)%5]++;
                paramVariations.add(temp);
            } else if (param.schedule[(i/2)%3][((i/2)/3)%5] > 0){
                Parameter temp = param.clone();
                temp.schedule[(i/2)%3][((i/2)/3)%5]--;
                paramVariations.add(temp);
            }
        }
        Parameter temp = param.clone();
        temp.numTrunkLine += 5;
        paramVariations.add(temp);
        if (param.numTrunkLine > 50) {
            temp = param.clone();
            temp.numTrunkLine -= 5;
            paramVariations.add(temp);
        }
        if (param.numReservedLine < param.numTrunkLine) {
            temp = param.clone();
            temp.numReservedLine++;
            paramVariations.add(temp);
        }
        if (param.numReservedLine > 0) {
            temp = param.clone();
            temp.numReservedLine--;
            paramVariations.add(temp);
        }
        return paramVariations;
    }

    private int getScoreParameter (Parameter param) {
        //Initialize output variables
        double[] propLongWaitRegular = new double[state.numberOfRuns];
        double[] propLongWaitSilver = new double[state.numberOfRuns];
        double[] propLongWaitGold = new double[state.numberOfRuns];
        double[] propBusySignalRegular = new double[state.numberOfRuns];
        double[] propBusySignalCardholder = new double[state.numberOfRuns];

        // Loop for NUMRUN simulation runs for each case
        // Case 1
        for (int i = 0; i < state.numberOfRuns; i++) {
            SMTravel mname = new SMTravel(state.startTime, state.endTime, param.schedule,
                    param.numTrunkLine, param.numReservedLine, state.sds[i]);
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

        //Heuristic score function
        int score = 0;
        if (!getPassFail(ciPropLongWaitRegular, 0.15)) {
            score += 100000;
            score += (int)((ciPropLongWaitRegular.getCfMax()-0.15)/0.15*10000.0);
        }
        if (!getPassFail(ciPropLongWaitSilver, 0.05)) {
            score += 100000;
            score += (int)((ciPropLongWaitSilver.getCfMax()-0.05)/0.05*10000.0);
        }
        if (!getPassFail(ciPropLongWaitGold, 0.02)) {
            score += 100000;
            score += (int)((ciPropLongWaitGold.getCfMax()-0.02)/0.02*10000.0);
        }
        if (!getPassFail(ciPropBusyLineSignalRegular, 0.2)) {
            score += 100000;
            score += (int)((ciPropBusyLineSignalRegular.getCfMax()-0.2)/0.2*10000.0);
        }
        if (!getPassFail(ciPropBusySignalCardholder, 0.02)) {
            score += 100000;
            score += (int)((ciPropBusySignalCardholder.getCfMax()-0.02)/0.02*10000.0);
        }
        return score + param.getCost();
    }
}

class Parameter implements Comparable{
    int[][] schedule;
    int numTrunkLine;
    int numReservedLine;


    static Parameter MAX_VALUE (){
        Parameter temp = new Parameter(new int[3][5],Integer.MAX_VALUE, Integer.MAX_VALUE);
        for(int i = 0; i < 3; i++) {
            for (int j = 0; j < 5; j++) {
                temp.schedule[i][j] = Integer.MAX_VALUE;
            }
        }
        return temp;
    }

    Parameter (int[][] schedule,int numTrunkLine, int numReservedLine) {
        this.schedule = schedule;
        this.numTrunkLine = numTrunkLine;
        this.numReservedLine = numReservedLine;
    }

    int getCost () {
        if (numTrunkLine == Integer.MAX_VALUE) {
            return Integer.MAX_VALUE;
        }
        int sumRegular = IntStream.of(schedule[0]).sum();
        int sumSilver = IntStream.of(schedule[1]).sum();
        int sumGold = IntStream.of(schedule[2]).sum();
        int cost = sumRegular * 16 * 8 + sumSilver * 20 * 8 + sumGold * 23 * 8;
        cost += (numTrunkLine - 50) * 170;
        return cost;
    }

    public Parameter clone() {
        int[][] scheduleCopy = new int[3][5];
        for (int i = 0; i < 3; i++) {
            scheduleCopy[i] = schedule[i].clone();
        }
        return new Parameter(scheduleCopy, numTrunkLine, numReservedLine);
    }

    public int compareTo(Object a) throws ClassCastException {
        if (!(a instanceof Parameter)) {
            throw new ClassCastException("A Parameter object expected.");
        }
        return this.getCost() - ((Parameter) a).getCost();
    }

    public boolean equals(Object a) {
        if (!(a instanceof Parameter)){
            return false;
        }
        Parameter b = (Parameter) a;
        boolean isEqual = true;
        for(int i = 0; i < 3; i++) {
            if(!Arrays.equals(schedule[i], b.schedule[i])){
                isEqual = false;
            }
        }
        if (numTrunkLine != b.numTrunkLine || numReservedLine != b.numReservedLine) {
            isEqual = false;
        }
        return isEqual;
    }

    public String toString(){
        return "schedule={"+Arrays.toString(schedule[0])+", "+Arrays.toString(schedule[1])
                +", "+Arrays.toString(schedule[2])+"}, numTrunkLine="+numTrunkLine
                +", numReservedLine="+numReservedLine+", cost="+getCost();
    }
}

class ScoreResult implements Comparable<ScoreResult> {
    Parameter param;
    int score;

    ScoreResult(Parameter param, int score){
        this.param = param;
        this.score = score;
    }

    public int compareTo(ScoreResult a) {
        return score - a.score;
    }
}
