package simModel;

public class Call {
	//double startWaitTime;  // Time a customer enters a line
	enum CustomerType { GOLD,SILVER,REGULAR};//
	enum Type{INFORMATION, RESERVATION, CHANGE};
	CustomerType uCustomerType;
	Type uType;// Type of call
	double uServiceTime;
	double uAfterCallWorkTime;
	double uWaitTimeTolerance;
}
