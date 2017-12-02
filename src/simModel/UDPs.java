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
    protected void CallRegistration(Call caller)
    {
        TtrunkLineReadyToAcceptCall(caller.uCustomerType, trunklines.numTrunkLineInUse);
        if(trunklines.numEmptyTrunkLine>0 &&trunklines.numEmptyTrunkLine > trunklines.numReservedLine)
        {
            if(trunklines.numEmptyTrunkLine > trunklines.numReservedLine)
            {
                trunklines.numTrunkLineInUse++;
                simModel.QwaitLine[caller.uCustomerType].insertQueue(caller);
            }
        }
        else if(trunklines.numReservedLine> 0 &&
       trunklines.numEmptyTrunkLine <= trunklines.numReservedLine)
        {
            if(caller.uCustomerType.getValue() == Constants.GOLD||caller.uCustomerType.getValue() == Constants.SILVER)
            {

                trunklines.numTrunkLineInUse++;
                simModel.QwaitLine[caller.uCustomerType].insertQueue(caller);
            }
        }
        else if(trunklines.numEmptyTrunkLine == 0)
        {
            simModel.SSOV.numBusySignal++;
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
                simModel.QwaitLine[uCustomerType].insertQueue(caller);
            }
        }
        else if(trunklines.numReservedLine> 0 &&
       trunklines.numEmptyTrunkLine <= simModel.RgTrunkLine.RgTrunkLine.numReservedLine)
        {
            if(uCustomerType.getValue()==Constants.GOLD ||uCustomerType.getValue()==Constants.SILVER)
            {

                trunklines.numTrunkLineInUse++;
                simModel.QwaitLine[caller.uCustomerType].insertQueue(caller);
            }
        }
        else if(trunklines.numEmptyTrunkLine == 0)
        {
            simModel.SSOV.numBusySignal++;
        }
    }

    protected void processTalkToOperator(Operators.OperatorType operatorType)
    {
        if( operatorType.getValue()==Constants.GOLD ) {
            if (simModel.QwaitLine[GOLD].length > 0)
            {
                simModel.QwaitLine[Gold].RemoveQueue(simModle.QwaitLine[GOLD].length -1);
            }
        }
        else if( operatorType.getValue() == Constants.SILVER )
        {
            if (simModel.QwaitLine[GOLD].length > 0)
            {
                simModel.QwaitLine[Gold].RemoveQueue(simModle.QwaitLine[GOLD].length -1);
            }
            else if(simModel.QwaitLine[SILVER].length > 0)
            {
                simModel.QwaitLine[SILVER].RemoveQueue(simModle.QwaitLine[SILVER].length -1);
            }
        }
        else
        {
            if (simModel.QwaitLine[GOLD].length > 0)
            {
                simModel.QwaitLine[Gold].RemoveQueue(simModle.QwaitLine[GOLD].length -1);
            }
            else if(simModel.QwaitLine[SILVER].length > 0)
            {
                simModel.QwaitLine[SILVER].RemoveQueue(simModle.QwaitLine[SILVER].length -1);
            }
            else if(simModel.QwaitLine[REGULAR].length > 0)
            {
                simModel.QwaitLine[REGULAR].RemoveQueue(simModle.QwaitLine[REGULAR].length -1);
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
