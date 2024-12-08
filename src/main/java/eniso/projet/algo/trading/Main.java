package eniso.projet.algo.trading;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        // File path to CSV containing stock data
        String filePath = "/home/ahmed/Desktop/projet algo/src/main/resources/stock_data.csv";

        // Fetch historical data from CSV file
        List<HistoricalQuote> history = StockDataRetriever.getHistoricalData(filePath);

        if (history != null && history.size() > 0) {
            // Initialize and run the Multi-Objective Genetic Algorithm
            Moga moga = new Moga();
            moga.evolve(history);

            // Print results of the best strategy (for example)
            TradingStrategy bestStrategy = moga.getBestStrategy();
            System.out.println("Best Strategy: Buy Threshold: " + bestStrategy.getBuyThreshold() +
                    ", Sell Threshold: " + bestStrategy.getSellThreshold());
        } else {
            System.out.println("No historical data found.");
        }
    }
}
