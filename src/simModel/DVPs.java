package simModel;

class DVPs 
{
    ModelName model;  // for accessing the clock

    private int DuRegularCustomer;

    private int DuCardholderCustomer;

    private double uCallType.PROINFO = 0.16;
    private double uCallType.PROPRES = 0.76;
    private double uCallType.PROPCH = 0.08;

    private double uCardholderType.PROPGOLD = 0.32;
    private double uCardholderType.PROSIL = 0.68;

    protected double uServeTime(){

    if(customer == 2)//customer is REGULAR
    {
        double result_2;

        if(uCallType == 3)
        {
            result_2 = (double)(Math.random()*(3.75-1.2)) + 1.2;
            return result_2;//1.2-3.75
        }
        if(uCallType == 4)
        {
            result_2 = (double)(Math.random()*(8.6-2.25)) + 2.25;
            return result_2;//2.25-8.6
        }
        if(uCallType == 5)
        {
            result_2 = (double)(Math.random()*(5.8-1.2)) + 1.2;
            return result_2;//1.2-5.8
        }
    }

    else if(customer == 0)//customer is GOLD
    {
        double result_0;

        if(uCallType == 3)
        {
            result_0 = (double)(Math.random()*(3.3-1.056)) + 1.056;
            return result_0;//1.056-3.3
        }
        if(uCallType == 4)
        {
            result_0 = (double)(Math.random()*(7.568-1.98)) + 1.98;
            return result_0;//1.98-7.568
        }
        if(uCallType == 5)
        {
            result_0 = (double)(Math.random()*(5.104-1.056)) + 1.056;
            return result_0;//1.056-5.104
        }
    }

    else if(customer == 1)//customer is SILVER
    {
        double result_1;

        if(uCallType == 3)
        {
            result_1 = (double)(Math.random()*(3.5625-1.14)) + 1.14;
            return result_1;//1.14-3.5625
        }
        if(uCallType == 4)
        {
            result_1 = (double)(Math.random()*(8.17-2.1375)) + 2.1375;
            return result_1;//2.1375-8.17
        }
        if(uCallType == 5)
        {
            result_1 = (double)(Math.random()*(5.51-1.14)) + 1.14;
            return result_1;//1.14-5.51
        }
    }
    }


    protected double uAfterCallTime()
    {

        double result;

        if(customer == 0)
        {
            result = (double)(Math.random()*(0.1-0.05)) + 0.05;
            return result;//0.05-0.1
        }
        if(customer == 1)
        {
            result = (double)(Math.random()*(0.8-0.05)) + 0.05;
            return result;//0.05-0.8
        }
        if(customer == 2) return;
        {
            result = (double)(Math.random()*(0.6-0.4)) + 0.4;
            return result;//0.4-0.6
        }
    }

    protected int uToleratedWaitTime()
    {
        int tolewaittime;
        if (customer == 2) tolewaittime=30;
        else    tolewaittime=17;

        return tolewaittime;
    }

    protected int uMaxQualityWaitTime()
    {
        int maxwaittime;
        //GOLD
        if(customer == 0) maxwaittime=90;
        //SILVER
        if(customer == 1) maxwaittime=180;
        //REGULAR
        if(customer == 2) maxwaittime=900;

        return maxwaittime;
    }

    // Constructor
    protected DVPs(ModelName model) { this.model = model; }

    // Translate deterministic value procedures into methods
        /* -------------------------------------------------
                           Example
    protected double getEmpNum()  // for getting next value of EmpNum(t)
    {
       double nextTime;
       if(model.clock == 0.0) nextTime = 90.0;
       else if(model.clock == 90.0) nextTime = 210.0;
       else if(model.clock == 210.0) nextTime = 420.0;
       else if(model.clock == 420.0) nextTime = 540.0;
       else nextTime = -1.0;  // stop scheduling
       return(nextTime);
    }
    ------------------------------------------------------------*/
