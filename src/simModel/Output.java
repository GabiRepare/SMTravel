g

class Output 
{
	SMTravel model;
	protected Output(SMTravel md) { model = md; }

    // SSOVs
	protected static int numLongWait[Constants.GOLD];
	protected static int numLongWait[Constants.SILVER];
	protected static int numLongWait[Constants.REGULAR];
	protected static int numLongWaitTotal = numLongWait[Constants.GOLD] + numLongWait[Constants.SILVER] + numLongWait[Constants.REGULAR];
	protected static int numCallProcessed[Constants.GOLD];
	protected static int numCallProcessed[Constants.SILVER];
	protected static int numCallProcessed[Constants.REGULAR];
	protected static int numBusySignal[Constants.GOLD];
	protected static int numBusySignal[Constants.SILVER];
	protected static int numBusySignal[Constants.REGULAR];
	protected static int maxTrunkLineUsed;

	public void initNumLongWait(){
		numLongWait[Constants.GOLD] = 0;
		numLongWait[Constants.SILVER] = 0;
		numLongWait[Constants.REGULAR] = 0;
		numLongWaitTotal = 0;
	}

	public void initNumCallProcessed(){
		numCallProcessed[Constants.GOLD] = 0;
		numCallProcessed[Constants.SILVER] = 0;
		numCallProcessed[Constants.REGULAR] = 0;
	}

	public void initNumBusySignal(){
		numBusySignal[Constants.GOLD] = 0;
		numBusySignal[Constants.SILVER] = 0;
		numBusySignal[Constants.REGULAR] = 0;
	}

	public void initMaxTrunkLineUsed(){
		maxTrunkLineUsed = 0;
	}
}
