package simModel;
public class Operators {
	// Attributes
	SMTravel model;

	protected int numFreeOperators;  // Number of free operator
	//protected int addNumOperators;  // Number of operator to add during busy periods, this attribute is a parameter
	protected int[][] operatorQt; //

	enum OperatorType {
		GOLD(Constants.GOLD),
		SILVER(Constants.SILVER),
		REGULAR(Constants.REGULAR);

		private int value;

		 OperatorType(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}

	enum OperatorShift {
		SHIFT_1(0),
		SHIFT_2(60),
		SHIFT_3(120),
		SHIFT_4(180),
		SHIFT_5(240);
		private int value;

		 OperatorShift(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}

	OperatorType uOperatorsType;
	OperatorShift uOperatorsShift;
}

