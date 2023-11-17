package Defender.org;

/**
 * This class is used to construct defenders to defent about the assets node and to prevent attackers
 */
public class Defenders {
    private double invest_D1;
    private double invest_D2;
    private double invest_D3;
     public static double spareBudget_D1 = 4.5d;
    public static double spareBudget_D2 = 3.375d;
    public static double spareBudget_D3 = 2.25d;

    /**
     * This constructor is used to initialize the initial budget for each defender
     * @param invest_d1 Referred to first defender
     * @param invest_d2 Referred to second defender
     * @param invest_d3 Referred to third defender
     */
    public Defenders(double invest_d1, double invest_d2, double invest_d3) {
        invest_D1 = invest_d1;
        invest_D2 = invest_d2;
        invest_D3 = invest_d3;
    }

    /**
     * This method is used to retrieve the budget of first defender
     * @return The budget of first defender
     */
    public double getInvest_D1() {
        return invest_D1;
    }

    /**
     * This method is used to retrieve the budget of second defender
     * @return The budget of second defender
     */
    public double getInvest_D2() {
        return invest_D2;
    }

    /**
     * This method is used to retrieve the budget of third defender
     * @return The budget of third defender
     */
    public double getInvest_D3() {
        return invest_D3;
    }

    /**
     * This method is used to add more budget to the current one of first defender
     * @param value The budget to add
     * @return The budget if there is as existed amount and not exceeds the total capacity of spare budget
     */
    public double addSpareInvestFor_D1(double value) {
        if (spareBudget_D1 - value < 0)
            return spareBudget_D1;
        else
        {
            spareBudget_D1 -= value;
            return value;
        }
    }

    /**
     * This method is used to add more budget to the current one of second defender
     * @param value The budget to add
     * @return The budget if there is as existed amount and not exceeds the total capacity of spare budget
     */
    public double addSpareInvestFor_D2(double value) {
        if (spareBudget_D2 - value < 0)
            return spareBudget_D2;
        else
        {
            spareBudget_D2 -= value;
            return value;
        }
    }

    /**
     * This method is used to add more budget to the current one of third defender
     * @param value The budget to add
     * @return The budget if there is as existed amount and not exceeds the total capacity of spare budget
     */
    public double addSpareInvestFor_D3(double value) {
        if (spareBudget_D3 - value < 0)
            return spareBudget_D3;
        else
        {
            spareBudget_D3 -= value;
            return value;
        }
    }

    /**
     * This method is used to set the budget for first defender
     * @param invest_D1 The budget amount to be set
     */
    public void setInvest_D1(double invest_D1) {
        this.invest_D1 = invest_D1;
    }

    /**
     * This method is used to set the budget for second defender
     * @param invest_D2 The budget amount to be set
     */
    public void setInvest_D2(double invest_D2) {
        this.invest_D2 = invest_D2;
    }

    /**
     * This method is used to set the budget for third defender
     * @param invest_D3 The budget amount to be set
     */
    public void setInvest_D3(double invest_D3) {
        this.invest_D3 = invest_D3;
    }

    /**
     * This method is used to retrieve the total budget for the three defenders
     * @return The summation of defender 1 budget and defender 2 budget and defender 3 budget
     */
    public double totalInvest() {
        return getInvest_D1() + getInvest_D2() + getInvest_D3();
    }
}