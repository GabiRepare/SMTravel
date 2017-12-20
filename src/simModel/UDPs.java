package simModel;

import java.lang.Math;

class UDPs {
    SMTravel model;  // for accessing the clock

    // Constructor
    UDPs(SMTravel model) {
        this.model = model;
    }


    // Translate User Defined Procedures into methods
    private int trunkLineReadyToAcceptCall(int callType) {
        if (callType == Constants.REGULAR) {
            return Math.max(
                    (model.rgTrunkLines.numTrunkLine
                            - model.rgTrunkLines.numTrunkLineInUse
                            - model.rgTrunkLines.numReservedLine),
                    0);
        } else //CARDHOLDER
            return model.rgTrunkLines.numTrunkLine - model.rgTrunkLines.numTrunkLineInUse;
    }

    boolean callReceived(int callType) {
        int numAvailableTrunkLine = trunkLineReadyToAcceptCall(callType);
        if (numAvailableTrunkLine > 0)
        {
            model.rgTrunkLines.numTrunkLineInUse++;
            if (model.rgTrunkLines.numTrunkLineInUse > model.output.maxTrunkLineUsed){
                model.output.maxTrunkLineUsed = model.rgTrunkLines.numTrunkLineInUse;
            }
            return true;
        } else {
            if (callType == Constants.REGULAR) {
                model.output.numBusySignalRegular++;
            } else { //CARDHOLDER
                model.output.numBusySignalCardholder++;
            }
            return false;
        }
    }

    void enqueueCall (Call call) {
        call.startWaitTime = model.getClock();
        model.qWaitLines[call.uType].add(call);
        model.output.numWait[call.uType]++;
        tryMatchCallOperator();
    }

    void processingStaffChange() {
        for (int i = 0; i < 9; i++){
            if (model.getClock() == Constants.STAFF_CHANGE_TIME_SEQ[i]){
                int shift = i % 5;
                if (i < 5) { //Shift beginning
                    for (int opType = 0; opType < 3; opType++){
                        model.rgOperators[opType].numFreeOperators += model.rgOperators[opType].schedule[shift];
                        tryMatchCallOperator();
                    }
                } else { //Shift ending
                    for (int opType = 0; opType < 3; opType++){
                        model.rgOperators[opType].numFreeOperators -= model.rgOperators[opType].schedule[shift];
                    }
                }
            }
        }
    }

    void checkForLongWait(Call call) {
        if (model.getClock() - call.startWaitTime > Constants.LONG_WAIT_THRESHOLD[call.uType]){
            model.output.numLongWait[call.uType]++;
        }
    }

    void tryMatchCallOperator() {
        int callType = -1;
        int operatorType = -1;

        for (int opType = 2; opType >= 0; opType--) { //Prioritize GOLD operators
            if(model.rgOperators[opType].numFreeOperators > 0) {
                if (!model.qWaitLines[Constants.GOLD].isEmpty()){
                    callType = Constants.GOLD;
                    operatorType = opType;
                    break;
                } else if ((opType == Constants.REGULAR || opType == Constants.SILVER)
                        && !model.qWaitLines[Constants.SILVER].isEmpty()) {
                    callType = Constants.SILVER;
                    operatorType = opType;
                    break;
                } else if (opType == Constants.REGULAR
                        && !model.qWaitLines[Constants.REGULAR].isEmpty()) {
                    callType = Constants.REGULAR;
                    operatorType = opType;
                    break;
                }
            }
        }
        if (callType != -1) {
            TalkToOperator talkOp = new TalkToOperator(model, callType, operatorType);
            model.spStart(talkOp);
        }
    }
}