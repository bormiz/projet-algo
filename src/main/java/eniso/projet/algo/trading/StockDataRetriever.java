package eniso.projet.algo.trading;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StockDataRetriever {

    // Method to read stock data from CSV file
    public static List<HistoricalQuote> getHistoricalData(String filePath) {
        List<HistoricalQuote> historicalQuotes = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            // Skip header
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                Date date = sdf.parse(data[0]);  // Assuming date format is yyyy-MM-dd
                Double closePrice = Double.parseDouble(data[4]); // Closing price is in 5th column
                historicalQuotes.add(new HistoricalQuote(date, closePrice));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return historicalQuotes;
    }
}
