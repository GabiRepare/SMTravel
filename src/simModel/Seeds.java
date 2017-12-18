package simModel;

import cern.jet.random.engine.RandomSeedGenerator;

public class Seeds
{
    int[] arrRegular;
    int[] arrCardholder;
    int cardholderType;
    int[] serviceTime;
    int[] afterCallTime;
    int[] toleratedWaitTime;
    int typingTime;
    int callSubject;


    public Seeds(RandomSeedGenerator rsg)
    {
        arrRegular = new int[12];
        arrCardholder = new int[12];
        for (int i = 0; i < 12; i++){
            arrRegular[i] = rsg.nextSeed();
            arrCardholder[i] = rsg.nextSeed();
        }
        cardholderType = rsg.nextSeed();
        serviceTime = new int[]{rsg.nextSeed(), rsg.nextSeed(), rsg.nextSeed()};
        afterCallTime = new int[]{rsg.nextSeed(), rsg.nextSeed(), rsg.nextSeed()};
        toleratedWaitTime = new int[]{rsg.nextSeed(), rsg.nextSeed()};
        typingTime = rsg.nextSeed();
        callSubject = rsg.nextSeed();
    }
}
