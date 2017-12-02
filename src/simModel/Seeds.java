package simModel;

import cern.jet.random.engine.RandomSeedGenerator;

public class Seeds 
{
	//not sure if any other input will be needed

	int arr;   // customer arrival
	int custType;   // customer type
	int goldstm;   // gold customer service time
	int silverstm;   // silver customer service time
	int regularstm; //regular customer service time
	int callType; //call type
	int goldaftstm;
	int sliveraftstm;
	int regularaftstm;


	public Seeds(RandomSeedGenerator rsg)
	{
		arr=rsg.nextSeed();
		custType=rsg.nextSeed();
		goldstm=rsg.nextSeed();
		silverstm=rsg.nextSeed();
		regularstm=rsg.nextSeed();
		callType=rsg.nextSeed();
		goldaftstm=rsg.nextSeed();
		sliveraftstm=rsg.nextSeed();
		regularaftstm=rsg.nextSeed();
		
	}
}