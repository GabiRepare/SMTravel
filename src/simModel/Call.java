package simModel;

public class Call {
   SMTravel model; //reference to model object
	//TODO fix this constant thing
	enum CustomerType
	{
		GOLD(Constants.GOLD),
		SILVER(Constants.SILVER),
		REGULAR(Constants.REGULAR);

		private int value;

		CustomerType(int value)
		{
			this.value = value;
		}
		public int getValue()
		{
			return value;
		}
	}

	enum CallType
	{
		INFORMATION(Constants.INFORMATION),
		RESERVATION(Constants.RESERVATION),
		CHANGE(Constants.CHANGE);
		private int value;

		private CallType(int value)
		{
			this.value = value;
		}
		public int getValue()
		{
			return value;
		}
	}
	public CustomerType uCustomerType;
	CallType uCallType;// Type of call
	protected double uServiceTime;
	protected double uAfterCallWorkTime;
	protected double uToleratedWaitTime;
	protected double startWaitTime;
    protected Call( SMTravel model){ this.model = model; }
	//double startWaitTime;  // Time a customer enters a line



}
