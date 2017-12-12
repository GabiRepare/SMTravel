package simModel;

import simModel.Call.CustomerType;
import simModel.Operators.OperatorShift;

import java.lang.Math;

public class UDPs {
    SMTravel model;  // for accessing the clock

    // Constructor
    protected UDPs(SMTravel model) {
        this.model = model;
    }


    // Translate User Defined Procedures into methods
    protected int TrunkLineReadyToAcceptCall(Call call) {
        if (call.uType == Constants.REGULAR) {
            return Math.max(
                    (model.rgTrunkLines.numTrunkLine
                            - model.rgTrunkLines.numTrunkLineInUse
                            - model.rgTrunkLines.numReservedLine),
                    0);
        } else //CARDHOLDER
            return model.rgTrunkLines.numTrunkLine - model.rgTrunkLines.numTrunkLineInUse;
    }

    protected void CallRegistration(Call call) {

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
        } else if (model.rgTrunkLines.numReservedLine > 0 &&
                numAvailableTrunkLine <= model.rgTrunkLines.numReservedLine) {
            if (call.uType.getValue() == Constants.GOLD || call.uType.getValue() == Constants.SILVER) {

                model.rgTrunkLines.numTrunkLineInUse++;
                model.qWaitLines[call.uType.getValue()].add(call);
            }
        } else if (numAvailableTrunkLine == 0) {
            if(call.uType.getValue() == Constants.CARDHOLDER) {
                model.output.numBusySignalCardholder++;
            }
            else
            {
                model.output.numBusySignalRegular++;
            }
        }
    }


    protected void processTalkToOperator(Operators.OperatorType operatorType) {
        if (operatorType.getValue() == Constants.GOLD) {
            if (model.qWaitLines[Constants.GOLD].size() > 0) {
                model.qWaitLines[Constants.GOLD].remove(model.qWaitLines[Constants.GOLD].size() - 1);
            }
        } else if (operatorType.getValue() == Constants.SILVER) {
            if (model.qWaitLines[Constants.GOLD].size() > 0) {
                model.qWaitLines[Constants.GOLD].remove(model.qWaitLines[Constants.GOLD].size() - 1);
            } else if (model.qWaitLines[Constants.SILVER].size() > 0) {
                model.qWaitLines[Constants.SILVER].remove(model.qWaitLines[Constants.SILVER].size() - 1);
            }
        } else {
            if (model.qWaitLines[Constants.GOLD].size() > 0) {
                model.qWaitLines[Constants.GOLD].remove(model.qWaitLines[Constants.GOLD].size() - 1);
            } else if (model.qWaitLines[Constants.SILVER].size() > 0) {
                model.qWaitLines[Constants.SILVER].remove(model.qWaitLines[Constants.SILVER].size() - 1);
            } else if (model.qWaitLines[Constants.REGULAR].size() > 0) {
                model.qWaitLines[Constants.REGULAR].remove(model.qWaitLines[Constants.REGULAR].size() - 1);
            }
        }

    }

    protected void ProcessingStaffChange(int shift) {
        operators.numFreeOperators += operators.operatorQt[shift][Constants.GOLD];
        operators.numFreeOperators += operators.operatorQt[shift][Constants.SILVER];
        operators.numFreeOperators += operators.operatorQt[shift][Constants.REGULAR];
    }

    protected double MaxQualityWaitTime(Call.CustomerType uType)
    {
        if(uType.getValue() == Constants.GOLD)
        {
            return 90/60; // converted to min.
        }
        else if(uType.getValue() == Constants.SILVER)
        {
            return 180/60;
        }
        else
        {
            return 900/60;
        }
    }
}