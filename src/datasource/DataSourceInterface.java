package datasource;

import java.time.LocalDate;

/**
 * The interface that provides only one method - getPriceOfShare that will fetch the stock data from
 * a given stock data source.
 */
public interface DataSourceInterface {

  /**
   * The method will fetch the share price for the given ticker symbol and the date from the
   * provided stock data source.
   *
   * @param tickerSymbol the ticker symbol of the company
   * @param date         the date for which the stock price is to be found
   * @return double the share price for the given ticker symbol and the date
   * @throws IllegalArgumentException when stock price is not found for the given ticker symbol and
   *                                  the date
   */
  double getPriceOfShare(String tickerSymbol, LocalDate date)
          throws IllegalArgumentException;

}
