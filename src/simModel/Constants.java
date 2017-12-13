package simModel;

class Constants
{
    /* Constants */
    // Example: protected final static double realConstant = 8.0;
    // Define constants as static
    final static int REGULAR = 0;
    final static int SILVER = 1;
    final static int GOLD = 2;
    final static int CARDHOLDER = 1;
    final static int INFORMATION = 0;
    final static int RESERVATION = 1;
    final static int CHANGE = 2;
    final static String[] CALL_TYPES = {"REGULAR", "SILVER", "GOLD"};
    final static String[] CALL_SUBJECTS = {"INFORMATION", "RESERVATION", "CHANGE"};

    final static double[] LONG_WAIT_THRESHOLD = {
            15,  //REGULAR
            3,   //SILVER
            1.5  //GOLD
    };

    final static int MIN = 0;
    final static int MAX = 1;
    final static int MEAN = 2;
    final static double[][] SERVICE_TIME = {
            {1.2,  3.75, 2.05}, //INFORMATION
            {2.25, 8.6,  2.95}, //RESERVATION
            {1.2,  5.8,  1.9 }  //CHANGE
    };
    final static double[][] AFTER_CALL_TIME = {
            {0.05, 0.10}, //INFORMATION
            {0.5,  0.8 }, //RESERVATION
            {0.4,  0.6 }  //CHANGE
    };
    final static double[] REGULAR_ARRIVAL_RATE = {
            87.0  / 60.0, // 7 AM -  8 AM
            165.0 / 60.0, // 8 AM -  9 AM
            236.0 / 60.0, // 9 AM - 10 AM
            323.0 / 60.0, //10 AM - 11 AM
            277.0 / 60.0, //11 AM - 12 PM
            440.0 / 60.0, //12 PM -  1 PM
            269.0 / 60.0, // 1 PM -  2 PM
            342.0 / 60.0, // 2 PM -  3 PM
            175.0 / 60.0, // 3 PM -  4 PM
            273.0 / 60.0, // 4 PM -  5 PM
            115.0 / 60.0, // 5 PM -  6 PM
            56.0  / 60.0, // 6 PM -  7 PM
    };
    final static double[] CARDHOLDER_ARRIVAL_RATE = {
            89.0  / 60.0, // 7 AM -  8 AM
            243.0 / 60.0, // 8 AM -  9 AM
            221.0 / 60.0, // 9 AM - 10 AM
            180.0 / 60.0, //10 AM - 11 AM
            301.0 / 60.0, //11 AM - 12 PM
            490.0 / 60.0, //12 PM -  1 PM
            394.0 / 60.0, // 1 PM -  2 PM
            347.0 / 60.0, // 2 PM -  3 PM
            240.0 / 60.0, // 3 PM -  4 PM
            269.0 / 60.0, // 4 PM -  5 PM
            145.0 / 60.0, // 5 PM -  6 PM
            69.0  / 60.0, // 6 PM -  7 PM
    };
    final static double[][] TOLERATED_WAIT_TIME = {
            {12.0, 30.0}, //REGULAR
            {8.0,  17.0}  //CARDHOLDER
    };
    final static double[] TYPING_TIME = {7.0 / 60.0, 16.0 / 60.0};
    final static double PROPORTION_SILVER_CARDHOLDER = 0.68;
                      //PROPORTION_GOLD_CARDHOLDER = 0.32
    final static double SILVER_OPERATOR_REDUCTION = 0.95; //95% of the time needed by a REGULAR operator
    final static double GOLD_OPERATOR_REDUCTION = 0.88;   //88% of the time needed by a REGULAR operator
    final static double[] PROPORTION_SUBJECT = {
            0.16, //INFORMATION
            0.76, //RESERVATION
            0.08  //CHANGE
    };
    final static double[] STAFF_CHANGE_TIME_SEQ = {
            0.0,   //7  AM
            60.0,  //8  AM
            120.0, //9  AM
            180.0, //10 AM
            240.0, //11 AM
            480.0, //3  PM
            540.0, //4  PM
            600.0, //5  PM
            660.0, //6  PM
            -1.0   //END
    };

}
