package simModel;
public class Operators {
	// Attributes
		protected int numFreeOperators;  // Number of free operator 
		protected int addNumOperators;  // Number of operator to add during busy periods, this attribute is a parameter

		enum OperatorType { GOLD,SILVER,REGULAR};
		OperatorType uOperatorsType; 
}
