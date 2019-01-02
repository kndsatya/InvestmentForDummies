package model;

import java.time.LocalDate;


/**
 * This is a stock interface that provides functionalities such as get the ticker symbol, date at
 * which it was bought, number of shares of the stock, get commission fee. A Ticker symbol uniquely
 * identifies a company that sells its shares.
 */
public interface StockInterface {

  /**
   * Gets the ticker symbol, represents the stock of a particular company whose ticker symbol it is.
   * 'GOOG' is the ticker symbol of Google company.
   *
   * @return String the ticker symbol of the stock which was bought by user
   */
  String getStockTickerSymbol();

  /**
   * Get the date on which the stock was bought in the format dd-mm-yyyy, time is not returned in
   * this method and only date.
   *
   * @return LocalDate date when the stock was bought
   */
  LocalDate getBuyDate();

  /**
   * Get the price of single share of that stock.
   *
   * @return double price of individual share of a stock
   */
  double getPriceOfUnitShare();

  /**
   * Get the number of shares in a bought stock. For example if the stock is of Google and that
   * stock has 5 shares, then this method will return 5.
   *
   * @return long number of shares of thatb stock
   */
  long getNumberOfShares();


  /**
   * Gets the commission fee paid as brokerage for the stock purchased.
   *
   * @return int the brokerage fee
   */
  public double getCommissionFee();
}
