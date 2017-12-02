package simModel;
public class Operators {
	// Attributes
	 SMTravel model;
		protected int numFreeOperators;  // Number of free operator 
		protected int addNumOperators;  // Number of operator to add during busy periods, this attribute is a parameter

		enum OperatorType
		{
			GOLD(Constants.GOLD),
			SILVER(Constant.SILVER),
			REGULAR(Constant.REGULAR);

			private int value;

			private OperatorType(int value)
			{
				this.value = value;
			}
			public int getValue()
			{
				return value;
			}
		}
		OperatorType uOperatorsType; 
}
