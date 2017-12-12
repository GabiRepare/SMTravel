// File: Experiment.java
// Description:

import simModel.*;
import cern.jet.random.engine.*;

// Main Method: Experiments
// 
class Experiment
{
   public static void main(String[] args)
   {
       int i, NUMRUNS = 30; 
       double startTime=0.0, endTime=720.0;
       Seeds[] sds = new Seeds[NUMRUNS];
       SMTravel mname;  // Simulation object

       // Lets get a set of uncorrelated seeds
       RandomSeedGenerator rsg = new RandomSeedGenerator();
       for(i=0 ; i<NUMRUNS ; i++) sds[i] = new Seeds(rsg);
       
       // Loop for NUMRUN simulation runs for each case
       // Case 1
       System.out.println(" Case 1");
       for(i=0 ; i < NUMRUNS ; i++)
       {
           int[][] schedule = {
                   {3,3,3,3,3}, //GOLD
                   {4,4,4,4,4}, //SILVER
                   {10,10,10,10,10} //REGULAR
           };
           int numTrunkLine = 50;
           int numReservedLine = 8;
           mname = new SMTravel(startTime, endTime, schedule, numTrunkLine,
                   numReservedLine,sds[i]);
           mname.runSimulation();
           // See examples for hints on collecting output
           // and developping code for analysis
       }
   }
}
