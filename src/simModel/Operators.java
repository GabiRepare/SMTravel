package simModel;
class Operators {
    // Attributes

    int numFreeOperators;  // Number of free operator
    int[] schedule; //Number of operators coming for every shift (parameter)

    Operators(int[] schedule){
        this.schedule = schedule;
    }
}

