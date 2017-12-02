package simModel;

import simModel.Call.CustomerType;

public class UDPs
{
    SMTravel model;  // for accessing the clock
    protected Call call = new Call(model);
    // Constructor
    protected UDPs(SMTravel model) { this.model = model; }



    // Translate User Defined Procedures into methods
    protected void TtrunkLineReadyToAcceptCall(int callType, int numTrunkLineInUse)
    {
        if (callType == Constants.REGULAR)
        {
            simModel.RgTrunkLine.numEmptyTrunkLine = max(numTrunkLineInUse - numReserveLine, 0);
            simModel.RgTrunkLine.numEmptyTrunkLine = numTrunkLine - numTrunkLineInUse;
        }

    }
    protected void CallRegistration(Call caller) {
        TtrunkLineReadyToAcceptCall(Caller.uCustomerType, simModel.RgTrunkLine.numTrunkLineInUse);
        if (simModel.RgTrunkLine.numEmptyTrunkLine > 0 && simModel.RgTrunkLine.numEmptyTrunkLine > simModel.TrunkLine.numReservedLine) {
            if (simModel.RgTrunkLine.numEmptyTrunkLine > simModel.RgTrunkLine.numReservedLine) {
                simModel.RgTrunkLine.numTrunkLineInUse++;
                simModel.QwaitLine[caller.uCustomerType].insertQueue(caller);
            }
        } else if (simModle.RGTrunkLine.numReservedLine > 0 &&
                simModel.RgTrunkLine.numEmptyTrunkLine <= simModel.RgTrunkLine.RgTrunkLine.numReservedLine) {
            if (caller.uCustomerType == GOLD || caller.uCustomerType == SILVER) {

                simModel.RgTrunkLine.numTrunkLineInUse++;
                simModel.QwaitLine[caller.uCustomerType].insertQueue(caller);
            }
        } else if (simModel.RgTrunkLine.numEmptyTrunkLine == 0) {
            simModel.SSOV.numBusySignal++;
        } else {
            simModel.RgTrunkLine.numEmptyTrunkLine = numTrunkLine - numTrunkLineInUse;
        }
    }

    protected void CallRegistration(Call.CustomerType uCustomerType) {
        TtrunkLineReadyToAcceptCall(uCustomerType, simModel.RgTrunkLine.numTrunkLineInUse);
        if (simModel.RgTrunkLine.numEmptyTrunkLine > 0 && simModel.RgTrunkLine.numEmptyTrunkLine > simModel.TrunkLine.numReservedLine) {
            if (simModel.RgTrunkLine.numEmptyTrunkLine > simModel.RgTrunkLine.numReservedLine) {
                simModel.RgTrunkLine.numTrunkLineInUse++;
                simModel.QwaitLine[uCustomerType].insertQueue(caller);
            }
        } else if (simModle.RGTrunkLine.numReservedLine > 0 &&
                simModel.RgTrunkLine.numEmptyTrunkLine <= simModel.RgTrunkLine.RgTrunkLine.numReservedLine) {
            if (uCustomerType.toString() == "GOLD" || uCustomerType.toString() == "SILVER") {

                simModel.RgTrunkLine.numTrunkLineInUse++;
                simModel.QwaitLine[caller.uCustomerType].insertQueue(caller);
            }
        } else if (simModel.RgTrunkLine.numEmptyTrunkLine == 0) {
            simModel.SSOV.numBusySignal++;
        }
    }

    protected void processTalkToOperator(Operators.OperatorType operatorType) {
        if (operatorType.toString() == "GOLD") {
            if (simModel.QwaitLine[GOLD].length > 0) {
                simModel.QwaitLine[Gold].RemoveQueue(simModle.QwaitLine[GOLD].length - 1);
            }
        } else if (operatorType.toString() == "SILVER") {
            if (simModel.QwaitLine[GOLD].length > 0) {
                simModel.QwaitLine[Gold].RemoveQueue(simModle.QwaitLine[GOLD].length - 1);
            } else if (simModel.QwaitLine[SILVER].length > 0) {
                simModel.QwaitLine[SILVER].RemoveQueue(simModle.QwaitLine[SILVER].length - 1);
            }
        } else {
            if (simModel.QwaitLine[GOLD].length > 0) {
                simModel.QwaitLine[Gold].RemoveQueue(simModle.QwaitLine[GOLD].length - 1);
            } else if (simModel.QwaitLine[SILVER].length > 0) {
                simModel.QwaitLine[SILVER].RemoveQueue(simModle.QwaitLine[SILVER].length - 1);
            } else if (simModel.QwaitLine[REGULAR].length > 0) {
                simModel.QwaitLine[REGULAR].RemoveQueue(simModle.QwaitLine[REGULAR].length - 1);
            }
        }

    }
}


