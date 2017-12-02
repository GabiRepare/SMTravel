package simModel;

import simModel.Call.CustomerType;

public class UDPs
{
    SMTravel model;  // for accessing the clock
    protected Call call = new Call(model);
    // Constructor
    protected UDPs(SMTravel model) { this.model = model; }



    // Translate User Defined Procedures into methods
  	protected int TtrunkLineReadyToAcceptCall(int callType, int numTrunkLineInUse)
    {
        if (callType == Constants.REGULAR)
        {
            return max(numTrunkLineInUse - numReserveLine, 0);
        } else {
            return numTrunkLine - numTrunkLineInUse;
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
            if(uCustomerType == Constants.GOLD ||uCustomerType == Constants.SILVER)

                simModel.RgTrunkLine.numTrunkLineInUse++;
                simModel.QwaitLine[caller.uCustomerType].insertQueue(uCustomerType);
            }
        } else if (simModel.RgTrunkLine.numEmptyTrunkLine == 0) {
            simModel.SSOV.numBusySignal++;
        }
    }

    protected void processTalkToOperator(Operators.OperatorType operatorType) {
        if( operatorType == Constants.GOLD ) {
            if (simModel.QwaitLine[GOLD].length > 0) {
                simModel.QwaitLine[Gold].RemoveQueue(simModle.QwaitLine[GOLD].length - 1);
            }
        } else if (operatorType.toString() == "SILVER") {
        else if( operatorType == Constants.SILVER )
            if (simModel.QwaitLine[Constants.GOLD].length > 0)
                simModel.QwaitLine[Constants.Gold].RemoveQueue(simModle.QwaitLine[GOLD].length -1);
            } else if (simModel.QwaitLine[SILVER].length > 0) {
            else if(simModel.QwaitLine[Constants.SILVER].length > 0)
                simModel.QwaitLine[Constants.SILVER].RemoveQueue(simModle.QwaitLine[Constants.SILVER].length -1);
            }
        } else {
            if (simModel.QwaitLine[GOLD].length > 0) {
            if (simModel.QwaitLine[Constants.GOLD].length > 0)
                simModel.QwaitLine[Constants.Gold].RemoveQueue(simModle.QwaitLine[Constants.GOLD].length -1);
            } else if (simModel.QwaitLine[SILVER].length > 0) {
            else if(simModel.QwaitLine[Constants.SILVER].length > 0)
                simModel.QwaitLine[Constants.SILVER].RemoveQueue(simModle.QwaitLine[Constants.SILVER].length -1);
            } else if (simModel.QwaitLine[REGULAR].length > 0) {
            else if(simModel.QwaitLine[Constants.REGULAR].length > 0)
                simModel.QwaitLine[Constants.REGULAR].RemoveQueue(simModle.QwaitLine[Constants.REGULAR].length -1);
            }
        }

    }

    protected void ProcessingStaffChange(int shift)
    {
        if(simModel.Operator.uOperatorType == Constants.GOLD)
        {
            simModel.numFreeOperators += simModel.Operator.OperatorQt[shift][Constants.GOLD];
        }
        if(simModel.Operator.uOperatorType == Constants.SILVER)
        {
            simModel.numFreeOperators += simModel.Operator.OperatorQt[shift][Constants.SILVER];
        }
        if(simModel.Operator.uOperatorType == Constants.REGULAR)
        {
            simModel.numFreeOperators += simModel.Operator.OperatorQt[shift][Constants.REGULAR];
        }
    }
}


