package eniso.projet.algo.trading;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        // File path to CSV containing stock data
        String filePath = "/home/ahmed/Desktop/projet algo/src/main/resources/stock_data.csv";

        // Period for RSI calculation (14 is commonly used)
        int period = 14;

        // Fetch historical data from CSV file
        List<HistoricalQuote> history = StockDataRetriever.getHistoricalData(filePath);

        if (history != null && history.size() > 0) {
            // Calculate RSI for the given stock
            double rsi = RSI.calculateRSI(history, period);
            System.out.println("RSI: " + rsi);
        } else {
            System.out.println("No historical data found.");
        }
    }
}
