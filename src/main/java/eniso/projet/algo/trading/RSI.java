package eniso.projet.algo.trading;

import java.util.List;

public class RSI {

    public static double calculateRSI(List<HistoricalQuote> history, int period) {
        double gain = 0;
        double loss = 0;

        // Calculate gains and losses for the first period
        for (int i = 1; i <= period; i++) {
            double change = history.get(i).getClose() - history.get(i - 1).getClose();
            if (change > 0) {
                gain += change;
            } else {
                loss -= change;
            }
        }

        // Calculate average gain and average loss for the first period
        double avgGain = gain / period;
        double avgLoss = loss / period;

        // Smooth the averages for the remaining data points
        for (int i = period + 1; i < history.size(); i++) {
            double change = history.get(i).getClose() - history.get(i - 1).getClose();
            if (change > 0) {
                gain = change;
                loss = 0;
            } else {
                gain = 0;
                loss = -change;
            }

            avgGain = ((avgGain * (period - 1)) + gain) / period;
            avgLoss = ((avgLoss * (period - 1)) + loss) / period;
        }

        // Avoid division by zero (in case avgLoss is zero)
        if (avgLoss == 0) {
            return 100; // If avgLoss is 0, RSI is 100 (strongly overbought)
        }

        // Calculate Relative Strength (RS)
        double rs = avgGain / avgLoss;

        // Calculate RSI using the formula
        return 100 - (100 / (1 + rs));
    }
}
