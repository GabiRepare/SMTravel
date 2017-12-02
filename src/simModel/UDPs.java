package simModel;

import simModel.Call.CustomerType;
import simModel.Operators.OperatorShift;

import java.lang.Math;

public class UDPs
{
    SMTravel model;  // for accessing the clock
    protected Call call = new Call(model);
    protected TrunkLines trunklines =new TrunkLines(model);
    protected Operators operators =new Operators(model);
    protected Output output =new Output(model);
    // Constructor
    protected UDPs(SMTravel model) { this.model = model; }
	


	// Translate User Defined Procedures into methods
  	protected int TrunkLineReadyToAcceptCall(Call.CustomerType customerType, int numTrunkLineInUse)
    {
        if (customerType.getValue() == Constants.REGULAR)
        {
        	return Math.max(trunklines.numTrunkLineInUse - trunklines.numReservedLine, 0);}
        else  
        return trunklines.numTrunkLine - numTrunkLineInUse;
        
    }
    protected void CallRegistration(Call call)
    {
    	trunklines.numEmptyTrunkLine = TrunkLineReadyToAcceptCall(call.uCustomerType, trunklines.numTrunkLineInUse);
        if(trunklines.numEmptyTrunkLine>0 &&trunklines.numEmptyTrunkLine > trunklines.numReservedLine)
        {
            if(trunklines.numEmptyTrunkLine > trunklines.numReservedLine)
            {
                trunklines.numTrunkLineInUse++;
                model.qWaitLines[call.uCustomerType.getValue()].add(call);
            }
        }
        else if(trunklines.numReservedLine> 0 &&
       trunklines.numEmptyTrunkLine <= trunklines.numReservedLine)
        {
            if(call.uCustomerType.getValue() == Constants.GOLD||call.uCustomerType.getValue() == Constants.SILVER)
            {

                trunklines.numTrunkLineInUse++;
                model.qWaitLines[call.uCustomerType.getValue()].add(call);
            }
        }
        else if(trunklines.numEmptyTrunkLine == 0)
        {
            output.numBusySignal++;
        }
    }

    }
    protected void CallRegistration(Call.CustomerType uCustomerType)
    {
    	trunklines.numEmptyTrunkLine =   TrunkLineReadyToAcceptCall(uCustomerType, trunklines.numTrunkLineInUse);
        if(trunklines.numEmptyTrunkLine>0 &&trunklines.numEmptyTrunkLine > trunklines.numReservedLine)
        {
            if(trunklines.numEmptyTrunkLine > trunklines.numReservedLine)
            {
                trunklines.numTrunkLineInUse++;
                model.qWaitLines[uCustomerType.getValue()].add(call);
            }
        } else if (simModle.RGTrunkLine.numReservedLine > 0 &&
                simModel.RgTrunkLine.numEmptyTrunkLine <= simModel.RgTrunkLine.RgTrunkLine.numReservedLine) {
            if (uCustomerType.toString() == "GOLD" || uCustomerType.toString() == "SILVER") {
            if(uCustomerType == Constants.GOLD ||uCustomerType == Constants.SILVER)

                trunklines.numTrunkLineInUse++;
                model.qWaitLines[call.uCustomerType.getValue()].add(call);
            }
        } else if (simModel.RgTrunkLine.numEmptyTrunkLine == 0) {
            output.numBusySignal++;
        }
    }

    protected void processTalkToOperator(Operators.OperatorType operatorType) {
        if( operatorType == Constants.GOLD ) {
            if (simModel.QwaitLine[GOLD].length > 0) {
                simModel.QwaitLine[Gold].RemoveQueue(simModle.QwaitLine[GOLD].length - 1);
                model.qWaitLines[Constants.GOLD].remove(model.qWaitLines[Constants.GOLD].size() -1);
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
                model.qWaitLines[Constants.GOLD].remove(model.qWaitLines[Constants.GOLD].size() -1);
            else if(simModel.QwaitLine[Constants.SILVER].length > 0)
                simModel.QwaitLine[Constants.SILVER].RemoveQueue(simModle.QwaitLine[Constants.SILVER].length -1);
            } else if (simModel.QwaitLine[REGULAR].length > 0) {
            else if(simModel.QwaitLine[Constants.REGULAR].length > 0)
                simModel.QwaitLine[Constants.REGULAR].RemoveQueue(simModle.QwaitLine[Constants.REGULAR].length -1);
            }
                model.qWaitLines[Constants.SILVER].remove(model.qWaitLines[Constants.SILVER].size() -1);
        }

    }

    protected void ProcessingStaffChange(int shift)
    {
        if(simModel.Operator.uOperatorType == Constants.GOLD)
        {
                model.qWaitLines[Constants.GOLD].remove(model.qWaitLines[Constants.GOLD].size() -1);
        }
        if(simModel.Operator.uOperatorType == Constants.SILVER)
        {
                model.qWaitLines[Constants.SILVER].remove(model.qWaitLines[Constants.SILVER].size() -1);
        }
        if(simModel.Operator.uOperatorType == Constants.REGULAR)
        {
                model.qWaitLines[Constants.REGULAR].remove(model.qWaitLines[Constants.REGULAR].size() -1);
        }
    }
}


        if(uOperatorsType.getValue() == Constants.GOLD)
        {
        	operators.numFreeOperators += operators.operatorQt[shift][Constants.GOLD];
        }
        if(uOperatorsType.getValue() == Constants.SILVER)
        {
        	operators.numFreeOperators += operators.operatorQt[shift][Constants.SILVER];
        }
        if(uOperatorsType.getValue() == Constants.REGULAR)
        {
        	operators.numFreeOperators += operators.operatorQt[shift][Constants.REGULAR];
        }
    }
}
