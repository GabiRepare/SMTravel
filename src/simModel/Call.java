package simModel;

public class Call {
   SMTravel model; //reference to model object
	//TODO fix this constant thing
	enum CustomerType { GOLD(0),SILVER(1),REGULAR(2);
		private int value;

		private CustomerType(int value)
		{
			this.value = value;
		}
		public int getValue()
		{
			return value;
		}};

	enum CallType{INFORMATION(0),
		RESERVATION(1),
		CHANGE(2);
		private int value;

		private CallType(int value)
		{
			this.value = value;
		}
		public int getValue()
		{
			return value;
		}
	};
	CustomerType uCustomerType;
	CallType uCallType;// Type of call
	private double uServiceTime;
	private double uAfterCallWorkTime;
	private double uToleratedWaitTime;
	private double startWaitTime;
   protected Call( SMTravel model){ this.model = model; }
	//double startWaitTime;  // Time a customer enters a line



}
