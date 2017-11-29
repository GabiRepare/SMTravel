package simModel;

public class Call {
    SMTravel model; //reference to model object
    Call( SMTravel model){ this.model = model; }
	//double startWaitTime;  // Time a customer enters a line
	enum CustomerType { GOLD,SILVER,REGULAR};//
	enum Type{INFORMATION, RESERVATION, CHANGE};
	CustomerType uCustomerType;
	Type uCallType;// Type of call
	double uServiceTime;
	double uAfterCallWorkTime;
	double uToleratedWaitTime;
	double startWaitTime;
	 
}
