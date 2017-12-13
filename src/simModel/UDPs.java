package simModel;

import java.lang.Math;

class UDPs {
    SMTravel model;  // for accessing the clock

    // Constructor
    UDPs(SMTravel model) {
        this.model = model;
    }


    // Translate User Defined Procedures into methods
    private int TrunkLineReadyToAcceptCall(Call call) {
        if (call.uType == Constants.REGULAR) {
            return Math.max(
                    (model.rgTrunkLines.numTrunkLine
                            - model.rgTrunkLines.numTrunkLineInUse
                            - model.rgTrunkLines.numReservedLine),
                    0);
        } else //CARDHOLDER
            return model.rgTrunkLines.numTrunkLine - model.rgTrunkLines.numTrunkLineInUse;
    }

    void CallRegistration(Call call) {
        int numAvailableTrunkLine = TrunkLineReadyToAcceptCall(call);
        if (numAvailableTrunkLine > 0)
        {
            model.rgTrunkLines.numTrunkLineInUse++;
            if (model.rgTrunkLines.numTrunkLineInUse > model.output.maxTrunkLineUsed){
                model.output.maxTrunkLineUsed = model.rgTrunkLines.numTrunkLineInUse;
            }
            call.startWaitTime = model.getClock();
            model.qWaitLines[call.uType].add(call);
            model.output.numWait[call.uType]++;
            TryMatchCallOperator();
        } else {
            if (call.uType == Constants.REGULAR) {
                model.output.numBusySignalRegular++;
            } else { //CARDHOLDER
                model.output.numBusySignalCardholder++;
            }
        }
    }

    void ProcessingStaffChange() {
        for (int i = 0; i < 9; i++){
            if (model.getClock() == Constants.STAFF_CHANGE_TIME_SEQ[i]){
                int shift = i % 5;
                if (i < 5) { //Shift beginning
                    for (int opType = 0; opType < 3; opType++){
                        model.rgOperators[opType].numFreeOperators += model.rgOperators[opType].schedule[shift];
                        TryMatchCallOperator();
                    }
                } else { //Shift ending
                    for (int opType = 0; opType < 3; opType++){
                        model.rgOperators[opType].numFreeOperators -= model.rgOperators[opType].schedule[shift];
                    }
                }
            }
        }
    }

    void CheckForLongWait(Call call) {
        if (model.getClock() - call.startWaitTime > Constants.LONG_WAIT_THRESHOLD[call.uType]){
            model.output.numLongWait[call.uType]++;
        }
    }

    void TryMatchCallOperator() {
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