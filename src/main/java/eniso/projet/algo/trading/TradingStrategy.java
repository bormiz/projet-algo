package eniso.projet.algo.trading;

import java.util.List;

public class TradingStrategy {

    private double buyThreshold;  // Buy signal when price > buyThreshold
    private double sellThreshold; // Sell signal when price < sellThreshold
    private double[] fitness = new double[2];  // fitness[0]: return, fitness[1]: risk

    // Constructor, getters, and setters
    public TradingStrategy(double buyThreshold, double sellThreshold) {
        this.buyThreshold = buyThreshold;
        this.sellThreshold = sellThreshold;
    }

    public double getBuyThreshold() {
        return buyThreshold;
    }

    public void setBuyThreshold(double buyThreshold) {
        this.buyThreshold = buyThreshold;
    }

    public double getSellThreshold() {
        return sellThreshold;
    }

    public void setSellThreshold(double sellThreshold) {
        this.sellThreshold = sellThreshold;
    }

    // Get fitness (return and risk)
    public double[] getFitness() {
        return fitness;
    }

    // Set fitness (total return and risk)
    public void setFitness(double totalReturn, double totalRisk) {
        this.fitness[0] = totalReturn;  // Return
        this.fitness[1] = totalRisk;    // Risk
    }

    // Evaluate the strategy: Calculate profit and risk based on historical data
    public void evaluate(List<HistoricalQuote> history) {
        double totalReturn = 0;
        double totalRisk = 0;
        double lastPrice = 0;
        double portfolioValue = 10000; // Starting portfolio value, can be adjusted
        boolean inPosition = false;    // Whether we are in a trade

        // Simulate the strategy
        for (int i = 1; i < history.size(); i++) {
            HistoricalQuote currentQuote = history.get(i);
            HistoricalQuote previousQuote = history.get(i - 1);

            if (!inPosition && currentQuote.getClose() > buyThreshold) {
                // Buy signal: Buy when price is above buyThreshold
                portfolioValue -= currentQuote.getClose();
                inPosition = true;
            } else if (inPosition && currentQuote.getClose() < sellThreshold) {
                // Sell signal: Sell when price is below sellThreshold
                portfolioValue += currentQuote.getClose();
                inPosition = false;
            }

            // Calculate total return (we can use portfolio value to reflect trading result)
            totalReturn = portfolioValue;

            // Risk is typically measured as volatility (standard deviation of returns)
            totalRisk += Math.abs(currentQuote.getClose() - previousQuote.getClose());
        }

        // Set the calculated return and risk as fitness values
        setFitness(totalReturn, totalRisk);
    }
}
