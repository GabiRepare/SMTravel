package simModel;

import cern.jet.random.AbstractContinousDistribution;
import cern.jet.random.Exponential;
import cern.jet.random.Uniform;
import cern.jet.random.engine.MersenneTwister;
import cern.jet.random.engine.RandomSeedGenerator;
import dataModelling.TriangularVariate;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

public class DistributionData {
    private List<Double>[] dataCallRegularInterArrivals;
    private List<Double>[] dataCallCardholderInterArrivals;
    private List<Double> dataCardholderType;
    private List<Double>[] dataServiceTime;
    private List<Double>[] dataAfterCallTime;
    private List<Double>[] dataToleratedWaitTime;
    private List<Double> dataTypingTime;
    private List<Double> dataCallSubject;

    Iterator<Double>[] itCallRegularInterArrivals;
    Iterator<Double>[] itCallCardholderInterArrivals;
    Iterator<Double> itCardholderType;
    Iterator<Double>[] itServiceTime;
    Iterator<Double>[] itAfterCallTime;
    Iterator<Double>[] itToleratedWaitTime;
    Iterator<Double> itTypingTime;
    Iterator<Double> itCallSubject;
    
    public DistributionData (RandomSeedGenerator rsg) {
        int[] seedArrRegular;
        int[] seedArrCardholder;
        int seedCardholderType;
        int[] seedServiceTime;
        int[] seedAfterCallTime;
        int[] seedToleratedWaitTime;
        int seedTypingTime;
        int seedCallSubject;

        seedArrRegular = new int[12];
        seedArrCardholder = new int[12];
        for (int i = 0; i < 12; i++){
            seedArrRegular[i] = rsg.nextSeed();
            seedArrCardholder[i] = rsg.nextSeed();
        }
        seedCardholderType = rsg.nextSeed();
        seedServiceTime = new int[]{rsg.nextSeed(), rsg.nextSeed(), rsg.nextSeed()};
        seedAfterCallTime = new int[]{rsg.nextSeed(), rsg.nextSeed(), rsg.nextSeed()};
        seedToleratedWaitTime = new int[]{rsg.nextSeed(), rsg.nextSeed()};
        seedTypingTime = rsg.nextSeed();
        seedCallSubject = rsg.nextSeed();

        Exponential[] dmCallRegularInterArrivals = new Exponential[12];
        Exponential[] dmCallCardholderInterArrivals = new Exponential[12];
        MersenneTwister dmCardholderType;
        TriangularVariate[] dmServiceTime = new TriangularVariate[3];
        Uniform[] dmAfterCallTime = new Uniform[3];
        Uniform[] dmToleratedWaitTime = new Uniform[2];
        Uniform dmTypingTime;
        MersenneTwister dmCallSubject;

        dataCallRegularInterArrivals = new List[12];
        dataCallCardholderInterArrivals = new List[12];
        for (int i = 0; i < 12; i++) {
            dmCallRegularInterArrivals[i] = new Exponential(Constants.REGULAR_ARRIVAL_RATE[i],
                    new MersenneTwister(seedArrRegular[i]));
            dmCallCardholderInterArrivals[i] = new Exponential(Constants.CARDHOLDER_ARRIVAL_RATE[i],
                    new MersenneTwister(seedArrCardholder[i]));
            dataCallRegularInterArrivals[i] = generateDistData(dmCallRegularInterArrivals[i], 700);
            dataCallCardholderInterArrivals[i] = generateDistData(dmCallCardholderInterArrivals[i], 700);
        }

        dmCardholderType = new MersenneTwister(seedCardholderType);
        dataCardholderType = generateDistData(dmCardholderType, 4000);
        dataServiceTime = new List[3];
        dataAfterCallTime = new List[3];
        for (int i = 0; i < 3; i++) {
            dmServiceTime[i] = new TriangularVariate(
                    Constants.SERVICE_TIME[i][Constants.MIN],
                    Constants.SERVICE_TIME[i][Constants.MEAN],
                    Constants.SERVICE_TIME[i][Constants.MAX],
                    new MersenneTwister(seedServiceTime[i])
            );
            dmAfterCallTime[i] = new Uniform(
                    Constants.AFTER_CALL_TIME[i][Constants.MIN],
                    Constants.AFTER_CALL_TIME[i][Constants.MAX],
                    new MersenneTwister(seedAfterCallTime[i])
            );
            dataServiceTime[i] = generateDistData(dmServiceTime[i], 4000);
            dataAfterCallTime[i] = generateDistData(dmAfterCallTime[i], 4000);
        }
        dataToleratedWaitTime = new List[2];
        for (int i = 0; i < 2; i++) {
            dmToleratedWaitTime[i] = new Uniform(
                    Constants.TOLERATED_WAIT_TIME[i][Constants.MIN],
                    Constants.TOLERATED_WAIT_TIME[i][Constants.MAX],
                    new MersenneTwister(seedToleratedWaitTime[i])
            );
            dataToleratedWaitTime[i] = generateDistData(dmToleratedWaitTime[i], 4000);
        }
        dmTypingTime = new Uniform(
                Constants.TYPING_TIME[Constants.MIN],
                Constants.TYPING_TIME[Constants.MAX],
                new MersenneTwister(seedTypingTime));
        dataTypingTime = generateDistData(dmTypingTime, 4000);
        dmCallSubject = new MersenneTwister(seedCallSubject);
        dataCallSubject = generateDistData(dmCallSubject, 8000);
    }
    
    public void initialiseIterators (){
        itCallRegularInterArrivals = new Iterator[12];
        itCallCardholderInterArrivals = new Iterator[12];
        for (int i = 0; i < 12; i++) {
            itCallRegularInterArrivals[i] = dataCallRegularInterArrivals[i].iterator();
            itCallCardholderInterArrivals[i] = dataCallCardholderInterArrivals[i].iterator();
        }
        itCardholderType = dataCardholderType.iterator();
        itServiceTime = new Iterator[3];
        itAfterCallTime = new Iterator[3];
        for (int i = 0; i < 3; i++) {
            itServiceTime[i] = dataServiceTime[i].iterator();
            itAfterCallTime[i] = dataAfterCallTime[i].iterator();
        }
        itToleratedWaitTime = new Iterator[2];
        for (int i = 0; i < 2; i++) {
            itToleratedWaitTime[i] = dataToleratedWaitTime[i].iterator();
        }
        itTypingTime = dataTypingTime.iterator();
        itCallSubject = dataCallSubject.iterator();
    }

    private ArrayList<Double>  generateDistData(AbstractContinousDistribution dist, int n) {
        ArrayList<Double> data = new ArrayList<>();
        for (int i = 0; i < n; i ++) {
            data.add(dist.nextDouble());
        }
        return data;
    }

    private ArrayList<Double>  generateDistData(MersenneTwister dist, int n) {
        ArrayList<Double> data = new ArrayList<>();
        for (int i = 0; i < n; i ++) {
            data.add(dist.nextDouble());
        }
        return data;
    }

    private ArrayList<Double> generateDistData(TriangularVariate dist, int n) {
        ArrayList<Double> data = new ArrayList<>();
        for (int i = 0; i < n; i ++) {
            data.add(dist.next());
        }
        return data;
    }
}
