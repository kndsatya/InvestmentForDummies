package model;

import java.time.LocalDate;


/**
 * This class represents a stock and implements StockInterface and contains the methods that gets
 * the user its Ticker symbol, date when it was bought, number of shares it has and price of an
 * individual share.
 */
public class Stock implements StockInterface {

  private final String tickerSymbol;
  private final long numberOfShares;
  private final LocalDate buyDate;
  private final double priceOfUnitShare;
  private final double commissionFee;


  /**
   * Constructs the stock object taking ticker symbol, number of shares, date when it was bought and
   * price of unit share.
   *
   * @param tickerSymbol     the ticker symbol that represents the stock's company
   * @param numberOfShares   the number of shares that the stock has
   * @param buyDate          the date when the stock was bought
   * @param priceOfUnitShare price of a unit share in that stock
   */
  public Stock(String tickerSymbol, long numberOfShares, LocalDate buyDate,
               double priceOfUnitShare) {
    this.tickerSymbol = tickerSymbol;
    this.numberOfShares = numberOfShares;
    this.buyDate = buyDate;
    this.priceOfUnitShare = priceOfUnitShare;
    this.commissionFee = 0.00;
  }


  /**
   * Constructs the stock object taking ticker symbol, number of shares, date when it was bought and
   * price of unit share. Change - To accommodate the commission fee paid for each stock bought, the
   * stock class has an extra commission variable.
   *
   * @param tickerSymbol     the ticker symbol that represents the stock's company
   * @param numberOfShares   the number of shares that the stock has
   * @param buyDate          the date when the stock was bought
   * @param priceOfUnitShare price of a unit share in that stock
   * @param commissionFee    the fee paid as brokerage for buying N number of shares of a company's
   *                         stock
   */
  public Stock(String tickerSymbol, long numberOfShares, LocalDate buyDate,
               double priceOfUnitShare, double commissionFee) {
    this.tickerSymbol = tickerSymbol;
    this.numberOfShares = numberOfShares;
    this.buyDate = buyDate;
    this.priceOfUnitShare = priceOfUnitShare;
    this.commissionFee = commissionFee;
  }


  @Override
  public String getStockTickerSymbol() {
    return tickerSymbol;
  }

  @Override
  public LocalDate getBuyDate() {
    return buyDate;
  }

  @Override
  public double getPriceOfUnitShare() {
    return priceOfUnitShare;
  }

  @Override
  public long getNumberOfShares() {
    return numberOfShares;
  }

  @Override
  public double getCommissionFee() {
    return this.commissionFee;
  }

  /**
   * Gets the stock in this format: Ticker symbol:value, Number of share:value, price of unit share:
   * value, buydate:value, commissionFee: value.
   *
   * @return string the date of buy in the above format
   */
  public String toString() {

    StringBuilder sb = new StringBuilder();

    sb.append("Ticker symbol: ");
    sb.append(this.getStockTickerSymbol() + ", ");

    sb.append("Number of shares: ");
    sb.append(this.getNumberOfShares() + ", ");

    sb.append("Price of Unit share: $");
    sb.append(this.getPriceOfUnitShare() + ", ");

    sb.append("Buy date: ");

    String day = "" + this.getBuyDate().getDayOfMonth();
    if (day.length() == 1) {
      day = "0" + day;
    }
    sb.append(day);
    sb.append("-");
    String monthValue = "" + this.getBuyDate().getMonthValue();
    if (monthValue.length() == 1) {
      monthValue = "0" + monthValue;
    }
    sb.append(monthValue);
    sb.append("-");
    sb.append(this.getBuyDate().getYear());
    sb.append(", ");

    sb.append("Commission fee: ");
    sb.append(this.getCommissionFee());

    return sb.toString();
  }
}
