package simModel;

public class Call {
   SMTravel model; //reference to model object
	//TODO fix this constant thing
	enum CustomerType { GOLD
		{@Override
	    public String toString() {
	      return "GOLD";
	    }},
		SILVER
		{@Override
		    public String toString() {
		      return "SILVER";
		    }},
		REGULAR
		{@Override
			    public String toString() {
			      return "REGULAR";
			    }}};//
	enum CallType{INFORMATION
		{@Override
	    public String toString() {
	      return "INFORMATION";
	    }},
		RESERVATION
		{@Override
		    public String toString() {
		      return "RESERVATION";
		    }},
		CHANGE
		{@Override
			    public String toString() {
			      return "CHANGE";
			    }}};
	CustomerType uCustomerType;
	CallType uCallType;// Type of call
	private double uServiceTime;
	private double uAfterCallWorkTime;
	private double uToleratedWaitTime;
	private double startWaitTime;
   protected Call( SMTravel model){ this.model = model; }
	//double startWaitTime;  // Time a customer enters a line



}
