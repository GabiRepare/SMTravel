package simModel;

import simModel.Call.CustomerType;
import simModel.Operators.OperatorShift;

import java.lang.Math;

public class UDPs {
    SMTravel model;  // for accessing the clock
    protected Call call = new Call(model);
    protected TrunkLines trunklines = new TrunkLines(model);
    protected Operators operators = new Operators();
    protected Output output = new Output(model);

    // Constructor
    protected UDPs(SMTravel model) {
        this.model = model;
    }


    // Translate User Defined Procedures into methods
    protected int TrunkLineReadyToAcceptCall(Call.CustomerType customerType, int numTrunkLineInUse) {
        if (customerType.getValue() == Constants.REGULAR) {
            return Math.max(trunklines.numTrunkLineInUse - trunklines.numReservedLine, 0);
        } else
            return trunklines.numTrunkLine - numTrunkLineInUse;

    }

    protected void CallRegistration(Call call) {
        trunklines.numEmptyTrunkLine = TrunkLineReadyToAcceptCall(call.uCustomerType, trunklines.numTrunkLineInUse);
        if (trunklines.numEmptyTrunkLine > 0 && trunklines.numEmptyTrunkLine > trunklines.numReservedLine) {
            if (trunklines.numEmptyTrunkLine > trunklines.numReservedLine) {
                trunklines.numTrunkLineInUse++;
                model.qWaitLines[call.uCustomerType.getValue()].add(call);
            }
        } else if (trunklines.numReservedLine > 0 &&
                trunklines.numEmptyTrunkLine <= trunklines.numReservedLine) {
            if (call.uCustomerType.getValue() == Constants.GOLD || call.uCustomerType.getValue() == Constants.SILVER) {

                trunklines.numTrunkLineInUse++;
                model.qWaitLines[call.uCustomerType.getValue()].add(call);
            }
        } else if (trunklines.numEmptyTrunkLine == 0) {
            if(call.uCustomerType.getValue() == Constants.CARDHOLDER) {
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

    protected double MaxQualityWaitTime(Call.CustomerType uCustomerType)
    {
        if(uCustomerType.getValue() == Constants.GOLD)
        {
            return 90/60; // converted to min.
        }
        else if(uCustomerType.getValue() == Constants.SILVER)
        {
            return 180/60;
        }
        else
        {
            return 900/60;
        }
    }
}