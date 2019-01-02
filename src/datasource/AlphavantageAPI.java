package datasource;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a data source that fetches data from Alphavantage API and implements the
 * DataSourceInterface which provides a method to obtain the closing price of a share on a
 * particular day.
 */
public class AlphavantageAPI implements DataSourceInterface {

  private int counter; // Number of api calls made
  private LocalDateTime lastReadTime; // Timestamp when the last api call was made
  private final Map<String, String> stockData; // stores stock data obtained from api call.

  /**
   * Constructs the AlphavantageAPI object by initializing the counter to ZERO, lastReadTime to
   * current time and creating a hash map to store stock data.
   */
  public AlphavantageAPI() {

    counter = 0;
    lastReadTime = LocalDateTime.now();
    stockData = new HashMap<>();

  }

  /**
   * provides the price of each share of a particular stock on the requested date.
   *
   * @param tickerSymbol the ticker symbol of the company
   * @param date         the date for which the stock price is to be found
   * @return price of the individual share on a particular day.
   * @throws IllegalArgumentException when the program is unable to fetch data provided by the API.
   * @throws RuntimeException         when the URL related to the API call isn't working any more.
   */
  public double getPriceOfShare(String tickerSymbol, LocalDate date)
          throws IllegalArgumentException, RuntimeException {


    if (LocalDate.now().compareTo(date) < 0) {
      return 0.00;
    }

    if (stockData.containsKey(tickerSymbol)) {
      if (LocalDate.parse(stockData.get(tickerSymbol).substring(38, 48))
              .compareTo(LocalDate.now()) < 0) {

        adjustCounter();
        stockData.put(tickerSymbol, fetchDataFromAPI(tickerSymbol));
      }
    } else {
      adjustCounter();
      stockData.put(tickerSymbol, fetchDataFromAPI(tickerSymbol));
    }

    String[] dayWiseData = stockData.get(tickerSymbol).split("\n");
    for (String dataInaDay : dayWiseData) {

      String[] data = dataInaDay.split(",");
      if (data[0].equals(date.toString())) {
        return Double.parseDouble(data[4]);
      }
    }
    return 0.00;
  }

  /**
   * A helper method that fetches data related to a particular stock from Alphavantage API.
   *
   * @param stockSymbol represents a tickerSymbol of a stock.
   * @return data fetched from API.
   * @throws IllegalArgumentException when the program is unable to fetch data provided by the API.
   * @throws RuntimeException         when the URL related to the API call isn't working any more.
   */
  private String fetchDataFromAPI(String stockSymbol)
          throws IllegalArgumentException, RuntimeException {


    //the API key needed to use this web service.
    //Please get your own free API key here: https://www.alphavantage.co/
    //Please look at documentation here: https://www.alphavantage.co/documentation/
    String apiKey = "T7PMTCSO9K6HA9TW";
    URL url = null;

    try {
      /*
      create the URL. This is the query to the web service. The query string
      includes the type of query (DAILY stock prices), stock symbol to be
      looked up, the API key and the format of the returned
      data (comma-separated values:csv). This service also supports JSON
      which you are welcome to use.
       */
      url = new URL("https://www.alphavantage"
              + ".co/query?function=TIME_SERIES_DAILY"
              + "&outputsize=full"
              + "&symbol"
              + "=" + stockSymbol + "&apikey=" + apiKey + "&datatype=csv");
    } catch (MalformedURLException e) {
      throw new RuntimeException("the alphavantage API has either changed or "
              + "no longer works");
    }

    InputStream in = null;
    StringBuilder output = new StringBuilder();


    try {
      /*
      Execute this query. This returns an InputStream object.
      In the csv format, it returns several lines, each line being separated
      by commas. Each line contains the date, price at opening time, highest
      price for that date, lowest price for that date, price at closing time
      and the volume of trade (no. of shares bought/sold) on that date.
      */
      in = url.openStream();
      int byteRead;

      while (( byteRead = in.read() ) != -1) {
        output.append((char) byteRead);
      }
    } catch (IOException e) {
      throw new IllegalArgumentException("No price data found for " + stockSymbol);
    }

    return output.toString();

  }

  /**
   * A helper method that keeps track of the number of api calls made to API. If the number of calls
   * made to an API exceeds 5 with in a minute, then the program wait for 60 seconds.
   */
  private void adjustCounter() {

    if (counter == 5) {
      counter = 1;
      Duration duration = Duration.between(lastReadTime, LocalDateTime.now());
      if (duration.toMinutes() < 1) {
        try {
          Thread.sleep(60000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    } else {

      counter++;
    }

    lastReadTime = LocalDateTime.now();
  }

}
