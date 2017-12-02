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
  	protected void TtrunkLineReadyToAcceptCall(int callType, int numTrunkLineInUse)
    {
        if (callType == Constants.REGULAR)
        {
        	trunklines.numEmptyTrunkLine = Math.max(trunklines.numTrunkLineInUse - trunklines.numReservedLine, 0);
           trunklines.numEmptyTrunkLine = trunklines.numTrunkLine - numTrunkLineInUse;
        }

    }
    protected void CallRegistration(Call call)
    {
        TtrunkLineReadyToAcceptCall(call.uCustomerType, trunklines.numTrunkLineInUse);
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
        else
        {
           trunklines.numEmptyTrunkLine = trunklines.numTrunkLine - trunklines.numTrunkLineInUse;
    }

    }
    protected void CallRegistration(Call.CustomerType uCustomerType)
    {
        TtrunkLineReadyToAcceptCall(uCustomerType, trunklines.numTrunkLineInUse);
        if(trunklines.numEmptyTrunkLine>0 &&trunklines.numEmptyTrunkLine > trunklines.numReservedLine)
        {
            if(trunklines.numEmptyTrunkLine > trunklines.numReservedLine)
            {
                trunklines.numTrunkLineInUse++;
                model.qWaitLines[uCustomerType.getValue()].add(call);
            }
        }
        else if(trunklines.numReservedLine> 0 &&
       trunklines.numEmptyTrunkLine <= trunklines.numReservedLine)
        {
            if(uCustomerType.getValue()==Constants.GOLD ||uCustomerType.getValue()==Constants.SILVER)
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

    protected void processTalkToOperator(Operators.OperatorType operatorType)
    {
        if( operatorType.getValue()==Constants.GOLD ) {
            if (model.qWaitLines[Constants.GOLD].size() > 0)
            {
                model.qWaitLines[Constants.GOLD].remove(model.qWaitLines[Constants.GOLD].size() -1);
            }
        }
        else if( operatorType.getValue() == Constants.SILVER )
        {
            if (model.qWaitLines[Constants.GOLD].size() > 0)
            {
                model.qWaitLines[Constants.GOLD].remove(model.qWaitLines[Constants.GOLD].size() -1);
            }
            else if(model.qWaitLines[Constants.SILVER].size() > 0)
            {
                model.qWaitLines[Constants.SILVER].remove(model.qWaitLines[Constants.SILVER].size() -1);
            }
        }
        else
        {
            if (model.qWaitLines[Constants.GOLD].size() > 0)
            {
                model.qWaitLines[Constants.GOLD].remove(model.qWaitLines[Constants.GOLD].size() -1);
            }
            else if(model.qWaitLines[Constants.SILVER].size() > 0)
            {
                model.qWaitLines[Constants.SILVER].remove(model.qWaitLines[Constants.SILVER].size() -1);
            }
            else if(model.qWaitLines[Constants.REGULAR].size() > 0)
            {
                model.qWaitLines[Constants.REGULAR].remove(model.qWaitLines[Constants.REGULAR].size() -1);
            }
        }

    }
    protected void ProcessingStaffChange(Operators.OperatorType uOperatorsType,int shift)
    {	
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
