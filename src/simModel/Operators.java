package simModel;
public class Operators {
	// Attributes
	 SMTravel model;
		protected int numFreeOperators;  // Number of free operator 
		protected int addNumOperators;  // Number of operator to add during busy periods, this attribute is a parameter

		enum OperatorType { GOLD
			{@Override
		    public String toString() {
		      return "GOLD";
		    }},SILVER
			{@Override
			    public String toString() {
			      return "SILVER";
			    }},
			REGULAR
			{@Override
				    public String toString() {
				      return "REGULAR";
				    }}};
		OperatorType uOperatorsType; 
		
}
