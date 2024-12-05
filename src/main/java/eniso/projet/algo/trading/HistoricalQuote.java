package eniso.projet.algo.trading;

import java.util.Date;

public class HistoricalQuote {
    private Date date;
    private Double close;

    public HistoricalQuote(Date date, Double close) {
        this.date = date;
        this.close = close;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getClose() {
        return close;
    }

    public void setClose(Double close) {
        this.close = close;
    }
}
