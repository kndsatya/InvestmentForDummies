package model;

import com.google.gson.Gson;

import com.google.gson.JsonParseException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;


import datasource.DataSourceInterface;


/**
 * This is the model for the Virtual stock investment simulator that implements the
 * VirtualStockModelInterface. Through the methods that this model offers, it supports creating
 * portfolios and adding bought stocks to it. Find the total cost basis (the total money invested in
 * buying the stocks) till a given date. Find the total value of the portfolio (the worth of the
 * stocks based on the stock price on that date. Get the portfolios created and their stocks. Also
 * supports save and retrieving portfolios, strategies.
 */
public class VirtualStockModel implements VirtualStockModelInterface {

  private final List<PortfolioInterface> portfolios;
  private final DataSourceInterface dataSource;
  private final List<DollarCostStrategy> dollarCostStrategies;


  /**
   * Constructs the VirtualStockModel which takes the datasource for stock data as an argument. The
   * source input can be any concrete implementation of DataSource Interface so that the stock data
   * is fetched from that source. The model has a list of portfolios that are created and list of
   * strategies created.
   *
   * @param dataSource of type DataSourceInterface, which specifies from where the stock data is
   *                   fetched
   * @throws IllegalArgumentException when the datasource object is null
   */
  public VirtualStockModel(DataSourceInterface dataSource) throws IllegalArgumentException {
    if (dataSource == null) {
      throw new IllegalArgumentException("Data source can't be null");
    }
    this.dataSource = dataSource;
    this.portfolios = new ArrayList<>();
    this.dollarCostStrategies = new ArrayList<>();
  }

  /**
   * Creates the portfolio given the name for it. Portfolio name will be always stored in Capital
   * case with out any trailing or leading spaces.
   *
   * @param name the name of the portfolio
   * @throws IllegalArgumentException when the name is either null/empty or if already a portfolio
   *                                  exists with the same name
   */
  public void createPortfolio(String name) throws IllegalArgumentException {
    try {
      Objects.requireNonNull(name);
    } catch (NullPointerException e) {
      throw new IllegalArgumentException("Portfolio name can't be null");
    }
    if (name.equals("")) {
      throw new IllegalArgumentException("Portfolio name can't be empty");
    }
    name = name.trim();
    name = name.toUpperCase();
    for (PortfolioInterface portfolio : portfolios) {
      if (portfolio.getName().equals(name)) {
        throw new IllegalArgumentException("Portfolio already exists");
      }
    }
    portfolios.add(new Portfolio(name));

  }

  /**
   * <p>Through this method stocks can be bought and added to one of the portfolios and if the
   * portfolio does not exist, portfolio is created with the provided name and stock is added.</p>
   *
   * <p>The stocks are bought for the given amount, only whole stocks are bought that is the
   * number of stocks is integral and not fraction. The remaining balance of the given amount is
   * ignored. The amount cannot be negative or zero.</p>
   *
   * <p>Also if the stock data does not contain the given ticker symbol and the date for which the
   * buy request is placed, IllegalArgumentException is thrown.</p>
   *
   * <p>The shares cannot be bought on non business days that is weekends, holidays and also the
   * timings are from 9:00 to 16:00 (24 hour format is followed here). So shares cannot be bought
   * before 9:00 AM and after 4:00 PM on a business day.</p>
   *
   * @param tickerSymbol  the ticker symbol of the company eg 'GOOG' fro Google company
   * @param portfolioName the name of a portfolio to which the stock has to be added, if the name
   *                      does not exist new portfolio with the given name is created and the stock
   *                      is added to it.
   * @param amount        the amount for which shares are to be bought, this is a double.
   * @param date          the date on which the stock is to be bought. The date has to be in
   *                      dd-MM-yyyy HH:mm.
   * @throws IllegalArgumentException when the amount is negative or insufficient to buy at least
   *                                  one share of the stock, date is not in the given format.
   *                                  Ticker symbol, date, portfolio name are empty or null. The
   *                                  time is before 9 A.M. or after 3:59 P.M; when the date is in
   *                                  future or a weekend(Saturday and Sunday) or a public holiday
   *                                  in USA. Public holidays - New year( Jan 1) ,Christmas (Dec
   *                                  25), Independence Day(July 4th), Thanksgiving(Nov 4th Week
   *                                  Thursday), Memorial Day(last Monday of May), Labor Day((1st
   *                                  Monday of September),President's Day (3rd Monday of February),
   *                                  Veterans Day (November 11),  MLK Day (3rd Monday of January).
   *                                  When the commission fee is negative.
   */
  public void buySharesOfStock(String tickerSymbol, String portfolioName, double amount,
                               String date)
          throws IllegalArgumentException {

    LocalDate buyDate;
    LocalDateTime buyDateTime;

    validateDataofBuyShare(tickerSymbol, portfolioName, amount, date, 0.0);

    DateTimeFormatter buydateFormat = DateTimeFormatter.ofPattern("dd-MM-uuuu HH:mm")
            .withResolverStyle(ResolverStyle.STRICT);
    buyDateTime = LocalDateTime.parse(date, buydateFormat);
    buyDate = buyDateTime.toLocalDate();

    double sharePrice = dataSource.getPriceOfShare(tickerSymbol.trim().toUpperCase(), buyDate);
    if (sharePrice == 0.00) {
      throw new IllegalArgumentException("stock data for the ticker " + tickerSymbol + ""
              + " doesn't exist for the provided date");
    }
    long numberOfShares = (long) ( amount / sharePrice );

    if (numberOfShares <= 0) {
      throw new IllegalArgumentException("Insufficient funds to buy the share");
    }
    Stock stock = new Stock(tickerSymbol.trim().toUpperCase(), numberOfShares, buyDate,
            sharePrice);

    for (PortfolioInterface portfolio : portfolios) {
      if (portfolio.getName().equals(portfolioName.trim().toUpperCase())) {
        portfolio.addStock(stock);
        return;
      }
    }

    PortfolioInterface portfolio = new Portfolio(portfolioName.trim().toUpperCase());
    portfolio.addStock(stock);
    portfolios.add(portfolio);

  }


  /**
   * <p>Through this method stocks can be bought and added to one of the portfolios and if the
   * portfolio does not exist, portfolio is created with the provided name and stock is added.</p>
   *
   * <p>The stocks are bought for the given amount, only whole stocks are bought that is the
   * number of stocks is integral and not fraction. The remaining balance of the given amount is
   * ignored. The amount cannot be negative or zero.</p>
   *
   * <p>Also if the stock data does not contain the given ticker symbol and the date for which the
   * buy request is placed, IllegalArgumentException is thrown.</p>
   *
   * <p>The shares cannot be bought on non business days that is weekends, holidays and also the
   * timings are from 9:00 to 16:00 (24 hour format is followed here). So shares cannot be bought
   * before 9:00 AM and after 4:00 PM on a business day.</p>
   *
   * @param tickerSymbol  the ticker symbol of the company eg 'GOOG' for Google company
   * @param portfolioName the name of a portfolio to which the stock has to be added, if the name
   *                      does not exist new portfolio with the given name is created and the stock
   *                      is added to it.
   * @param amount        the amount for which shares are to be bought, this is a double.
   * @param date          the date on which the stock is to be bought. The date has to be in
   *                      dd-MM-yyyy HH:mm.
   * @param commissionFee the fee paid as brokerage for buying N number of shares of a company's
   *                      stock.
   * @throws IllegalArgumentException when the amount is negative or insufficient to buy at least
   *                                  one share of the stock, date is not in the given format.
   *                                  Ticker symbol, date, portfolio name are empty or null. The
   *                                  time is before 9 A.M. or after 3:59 P.M; when the date is in
   *                                  future or a weekend(Saturday and Sunday) or a public holiday
   *                                  in USA. Public holidays - New year( Jan 1) ,Christmas (Dec
   *                                  25), Independence Day(July 4th), Thanksgiving(Nov 4th Week
   *                                  Thursday), Memorial Day(last Monday of May), Labor Day((1st
   *                                  Monday of September),President's Day (3rd Monday of February),
   *                                  Veterans Day (November 11),  MLK Day (3rd Monday of January).
   */
  public void buySharesOfStock(String tickerSymbol, String portfolioName, double amount,
                               String date, double commissionFee)
          throws IllegalArgumentException {

    LocalDate buyDate;
    LocalDateTime buyDateTime;


    validateDataofBuyShare(tickerSymbol, portfolioName, amount, date, commissionFee);

    DateTimeFormatter buydateFormat = DateTimeFormatter.ofPattern("dd-MM-uuuu HH:mm")
            .withResolverStyle(ResolverStyle.STRICT);
    buyDateTime = LocalDateTime.parse(date, buydateFormat);
    buyDate = buyDateTime.toLocalDate();

    double sharePrice = dataSource.getPriceOfShare(tickerSymbol.trim().toUpperCase(), buyDate);
    if (sharePrice == 0.00) {

      throw new IllegalArgumentException("stock data for the ticker " + tickerSymbol + ""
              + " doesn't exist for the provided date");
    }

    long numberOfShares = (long) ( amount / sharePrice );

    if (numberOfShares == 0) {
      throw new IllegalArgumentException("Insufficient funds to buy the share");
    }
    Stock stock = new Stock(tickerSymbol.trim().toUpperCase(), numberOfShares, buyDate,
            sharePrice, commissionFee);

    for (PortfolioInterface portfolio : portfolios) {
      if (portfolio.getName().equals(portfolioName.trim().toUpperCase())) {
        portfolio.addStock(stock);
        return;
      }
    }

    PortfolioInterface portfolio = new Portfolio(portfolioName.trim().toUpperCase());
    portfolio.addStock(stock);
    portfolios.add(portfolio);

  }

  /**
   * A private helper method to validate ticker symbol, portfolio if it exists or not, amount,
   * commission fee and given date.
   *
   * @param tickerSymbol  the ticker symbol of the company eg 'GOOG' for Google company
   * @param portfolioName the name of the portfolio
   * @param amount        the amount for which shares are to be bought, this is a double.
   * @param date          the date on which the stock is to be bought. The date has to be in
   *                      dd-MM-yyyy HH:mm.
   * @param commissionFee the fee paid as brokerage for buying N number of shares of a company's
   *                      stock.
   * @throws IllegalArgumentException when the amount is negative or insufficient to buy at least
   *                                  one share of the stock, date is not in the given format.
   *                                  Ticker symbol, date, portfolio name are empty or null. The
   *                                  time is before 9 A.M. or after 3:59 P.M; when the date is in
   *                                  future or a weekend(Saturday and Sunday) or a public holiday
   *                                  in USA. Public holidays - New year( Jan 1) ,Christmas (Dec
   *                                  25), Independence Day(July 4th), Thanksgiving(Nov 4th Week
   *                                  Thursday), Memorial Day(last Monday of May), Labor Day((1st
   *                                  Monday of September),President's Day (3rd Monday of February),
   *                                  Veterans Day (November 11),  MLK Day (3rd Monday of January).
   */
  private void validateDataofBuyShare(String tickerSymbol, String portfolioName, double amount,
                                      String date, double commissionFee)
          throws IllegalArgumentException {

    LocalDate buyDate;
    LocalDateTime buyDateTime;
    Calendar calendar;

    if (tickerSymbol == null || tickerSymbol.equals("")) {
      throw new IllegalArgumentException("Ticker symbol cannot be empty or null");
    }
    if (portfolioName == null || portfolioName.equals("")) {
      throw new IllegalArgumentException("Portfolio name cannot be empty or null");
    }

    if (date == null || date.equals("")) {
      throw new IllegalArgumentException("Date cannot be empty or null");
    }

    if (commissionFee < 0) {
      throw new IllegalArgumentException("The commission fee cannot be negative.");
    }

    portfolioName = portfolioName.trim().toUpperCase();
    try {
      DateTimeFormatter buydateFormat = DateTimeFormatter.ofPattern("dd-MM-uuuu HH:mm")
              .withResolverStyle(ResolverStyle.STRICT);
      buyDateTime = LocalDateTime.parse(date, buydateFormat);
      buyDate = buyDateTime.toLocalDate();
      calendar = GregorianCalendar.from(buyDate.atStartOfDay(ZoneId.systemDefault()));
    } catch (DateTimeParseException e) {
      throw new IllegalArgumentException("Entered date should be in format 'dd-MM-yyyy HH24:mm'"
              + " and a valid calendar date.");
    }

    if (isHoliday(calendar)) {
      throw new IllegalArgumentException("Can't buy shares on a holiday");
    }
    if (buyDateTime.getHour() < 9 || buyDateTime.getHour() >= 16) {
      throw new IllegalArgumentException("Can't buy shares after/before business hours.");
    }

    if (amount < 0) {
      throw new IllegalArgumentException("Amount cannot be negative");
    }
  }

  /**
   * Gets the total cost basis (the total amount invested by the user in buying the stocks) of a
   * particular portfolio given it's name and the date on which the cost basis value is required. If
   * the portfolio name does not exists then this method returns 0.0.
   *
   * <p>The method will not give the sum of costs of stocks bought after the given date as it is
   * supposed to do. Returns 0.0 if no stocks are bought on or before the given date.</p>
   *
   * @param portfolioName the name of the portfolio for which totalCostBasis is to be found.
   * @param date          date on which the costs basis value is required and should be in format
   *                      dd-mm-yyyy.
   * @return double the total cost basis of the portfolio for the given date.
   * @throws IllegalArgumentException when the date is not in the specified format.
   */
  public double getTotalCostBasis(String portfolioName, String date)
          throws IllegalArgumentException {
    LocalDate dateAsLocalDate;
    boolean portfolioexists = false;
    try {
      DateTimeFormatter dateformat = DateTimeFormatter.ofPattern("dd-MM-uuuu")
              .withResolverStyle(ResolverStyle.STRICT);
      dateAsLocalDate = LocalDate.parse(date, dateformat);
    } catch (DateTimeParseException e) {
      throw new IllegalArgumentException("Entered date should be in format 'dd-MM-yyyy and a "
              + "valid calendar date'");
    }
    portfolioName = portfolioName.trim().toUpperCase();
    double totalCostBasis = 0.00;
    for (PortfolioInterface portfolio : portfolios) {
      if (portfolio.getName().equals(portfolioName)) {

        portfolioexists = true;
        List<StockInterface> stock = portfolio.getStocks();
        totalCostBasis = stock.stream().filter(s -> s.getBuyDate()
                .compareTo(dateAsLocalDate) <= 0)
                .mapToDouble(s -> ( s.getNumberOfShares() * s.getPriceOfUnitShare() )
                        + s.getCommissionFee()).sum();

      }
    }
    if (!portfolioexists) {
      throw new IllegalArgumentException("Portfolio: " + portfolioName + " doesn't exist");
    }
    return totalCostBasis;
  }


  /**
   * Gets the total value (worth of the portfolio on a given date based on the stock price on that
   * date) given the portfolio name and the date on which the worth has to be estimated. If a stock
   * data for the provided portfolio's stock isn't available a value of 0.00 will be added to the
   * total value.
   *
   * <p>If the given date is a holiday then it takes the last business day's closing stock price
   * while evaluating each stock's worth.</p>
   *
   * <p>If the given date is in future, returns 0.00</p>
   *
   * <p>The method only gives the sum of values of stocks bought on or before the provided date.
   * </p>
   *
   * @param portfolioName the name of the portfolio for which totalCostBasis is to be found.
   * @param date          the date on which the portfolio's value is needed. The date has to be in
   *                      dd-MM-yyyy format.
   * @return double the total value of the portfolio for the given date
   * @throws IllegalArgumentException when one of the parameters is not valid, that is when name,
   *                                  date is null or empty. Also, exception is thrown when the
   *                                  portfolio doesn't exist.
   */
  public double getTotalValue(String portfolioName, String date) throws IllegalArgumentException {
    LocalDate dateAsLocalDate;
    boolean portfolioexists = false;
    DateTimeFormatter dateformat = DateTimeFormatter.ofPattern("dd-MM-uuuu")
            .withResolverStyle(ResolverStyle.STRICT);
    try {
      dateAsLocalDate = LocalDate.parse(date, dateformat);
    } catch (DateTimeParseException e) {
      throw new IllegalArgumentException("Entered date should be in format 'dd-MM-yyyy and a "
              + "valid calendar date.'");
    }
    Calendar calendar = GregorianCalendar.from(dateAsLocalDate
            .atStartOfDay(ZoneId.systemDefault()));
    while (isHoliday(calendar)) {
      dateAsLocalDate = dateAsLocalDate.minusDays(1);
      calendar = GregorianCalendar.from(dateAsLocalDate
              .atStartOfDay(ZoneId.systemDefault()));
    }
    LocalDate modifiedDate = dateAsLocalDate;
    portfolioName = portfolioName.trim().toUpperCase();
    double totalPortfolioWorthAtCertainDate = 0.00;
    for (PortfolioInterface portfolio : portfolios) {
      if (portfolio.getName().equals(portfolioName)) {

        portfolioexists = true;
        List<StockInterface> stock = portfolio.getStocks();
        totalPortfolioWorthAtCertainDate = stock.stream()
                .filter(s -> s.getBuyDate()
                        .compareTo(modifiedDate) <= 0)
                .mapToDouble(s -> s.getNumberOfShares()
                        * dataSource.getPriceOfShare(s.getStockTickerSymbol(), modifiedDate))
                .sum();
      }
    }

    if (!portfolioexists) {
      throw new IllegalArgumentException("Portfolio: " + portfolioName + " doesn't exist");
    }
    return totalPortfolioWorthAtCertainDate;
  }


  /**
   * <p>Get a portfolio's list of strings (containing details of ticker symbol,
   * buy date, unit share price, commission fee and number of shares) for all the stocks that
   * portfolio contains.</p>
   *
   * <p>If the portfolio name does not exist returns null.</p>
   *
   * @param portfolioName the name of the portfolio.
   * @return the list of stocks in a portfolio.
   */
  public List<String> displayPortfolio(String portfolioName) {

    for (PortfolioInterface portfolio : portfolios) {
      if (portfolio.getName().equals(portfolioName.trim().toUpperCase())) {
        return getPortfolioStockStrings(portfolio);
      }
    }
    return null;
  }

  /**
   * <p>Get all the portfolios with their names and their stock contents as a Map with key as
   * portfolio name and value as corresponding list of stocks in string format .</p>
   *
   * @return Map with key as portfolio name and value as corresponding list of stocks.
   */
  public Map<String, List<String>> displayAllPortfolios() {

    Map<String, List<String>> portfoliosWithStocks = new HashMap<>();
    for (PortfolioInterface portfolio : this.portfolios) {
      String portfolioName = portfolio.getName();
      portfoliosWithStocks.put(portfolioName, getPortfolioStockStrings(portfolio));
    }

    return portfoliosWithStocks;

  }


  /**
   * A private helper method that returns list of strings (containing details of ticker symbol, buy
   * date, unit share price and number of shares) for all the stocks that a portfolio contains.
   *
   * @param portfolio the name of the portfolio
   * @return list of strings (containing details of ticker symbol, buy date, unit share price and
   *         number of shares) for all the stocks that a portfolio contains.
   */
  private List<String> getPortfolioStockStrings(PortfolioInterface portfolio) {

    List<String> stockToStrings = new ArrayList<>();

    for (StockInterface stock : portfolio.getStocks()) {
      stockToStrings.add(stock.toString());
    }
    return stockToStrings;
  }


  /**
   * A private helper method that checks if the given date in a calendar is a holiday or not.
   *
   * @param calendar the date that is of calendar type
   * @return boolean if the given date is a holiday or not
   */
  private boolean isHoliday(Calendar calendar) {

    if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
            || calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
      return true;
    }


    if (calendar.get(Calendar.MONTH) == Calendar.JANUARY
            && calendar.get(Calendar.DAY_OF_MONTH) == 1) {
      return true;
    }


    if (calendar.get(Calendar.MONTH) == Calendar.DECEMBER
            && calendar.get(Calendar.DAY_OF_MONTH) == 25) {
      return true;
    }

    if (calendar.get(Calendar.MONTH) == Calendar.JANUARY
            && calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH) == 3
            && calendar.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
      return true;
    }

    if (calendar.get(Calendar.MONTH) == Calendar.FEBRUARY
            && calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH) == 3
            && calendar.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
      return true;
    }

    if (calendar.get(Calendar.MONTH) == Calendar.MAY
            && calendar.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY
            && calendar.get(Calendar.DAY_OF_MONTH) > ( 31 - 7 )) {
      return true;
    }

    if (calendar.get(Calendar.MONTH) == Calendar.JULY
            && calendar.get(Calendar.DAY_OF_MONTH) == 4) {
      return true;
    }


    if (calendar.get(Calendar.MONTH) == Calendar.SEPTEMBER
            && calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH) == 1
            && calendar.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
      return true;
    }

    if (calendar.get(Calendar.MONTH) == Calendar.NOVEMBER
            && calendar.get(Calendar.DAY_OF_MONTH) == 11) {
      return true;
    }

    return ( calendar.get(Calendar.MONTH) == Calendar.NOVEMBER
            && calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH) == 4
            && calendar.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY );

  }

  /**
   * <p>Invest a fixed amount into an existing portfolio containing multiple stocks, using a
   * specified percentage of amount for each stock in the portfolio. For example, the user can
   * create a FANG portfolio (Facebook, Apple, Netflix, Google) and then specify to invest $2000 in
   * the portfolio, such that 40% goes towards Facebook, 20% towards Apple, 30% towards Netflix and
   * 10% towards Google)</p>
   *
   * <p> If for any of the stocks the specified amount is insufficient to buy even one share of the
   * stock then that stock is not bought, but remaining stocks are bought and added to the
   * portfolio</p>
   *
   * @param portfolioName      the name of the portfolio on which the investment is to be made.
   * @param stockNameAndWeight the different percentages for the stocks
   * @param amount             the amount to be invested.
   * @param commissionFee      the fee paid for brokerage. The commission fee is applicable for 'n'
   *                           number of stocks of each company. If the stocks are Google and Apple
   *                           and if the commission fee is $100, then in total it would cost $200
   *                           for buying certain number of Google and Apple stocks.
   * @param date               the date on which the stock is to be bought. The date has to be in
   *                           dd-MM-yyyy HH:mm.
   * @return String the investment summary stating whether the investment was successful or not for
   *         each of the stocks.
   * @throws IllegalArgumentException when portfolio name is null or empty. The given portfolio does
   *                                  not exist. Also when the commission fee is negative. The
   *                                  individual percentage given for the stocks should not be
   *                                  negative and the sum of the percentages is either lesser or
   *                                  greater then 100. If the date isn't in valid format or when
   *                                  investment is to done on a non-business day/hour.
   */

  public String invest(String portfolioName, Map<String, Double> stockNameAndWeight, double amount,
                       double commissionFee, String date) throws IllegalArgumentException {

    PortfolioInterface portfolio = getPortfolio(portfolioName);

    if (portfolio == null) {
      throw new IllegalArgumentException("The portfolio does not exist.");
    }

    isAmountCommissionPercentageValid(amount, commissionFee, stockNameAndWeight);

    StringBuilder investmentSummary = new StringBuilder();
    for (Map.Entry<String, Double> entry : stockNameAndWeight.entrySet()) {

      String tickerSymbol = entry.getKey();
      double percentageOfInvestment = entry.getValue();
      double amountToInvest = ( amount * percentageOfInvestment ) / 100.00;

      if (percentageOfInvestment != 0.00) {
        try {
          buySharesOfStock(tickerSymbol, portfolioName, amountToInvest, date, commissionFee);
          investmentSummary.append("Successfully invested in " + tickerSymbol + "\n");
        } catch (IllegalArgumentException e) {
          if (e.getMessage().equalsIgnoreCase("Insufficient funds to buy the share")) {
            investmentSummary.append("Could not buy " + tickerSymbol + " due to insufficient"
                    + " amount\n");
          } else {
            throw e;
          }
        }
      }

    }
    return investmentSummary.deleteCharAt(investmentSummary.length() - 1).toString();
  }

  /**
   * <p>Dollar Cost Averaging is an higher-level investment strategy where one can create a
   * portfolio of N stocks, and invest $X in the portfolio every Y days starting on startDate until
   * endDate using the same, fixed weights.</p>
   *
   * <p>The method can be called with empty end date string, in that case the investment will be
   * on going without an end date.</p>
   *
   * <p>If for any investment, the single share price of a stock is greater than the available
   * amount, investment does not happen for that stock but rest of the investment will be
   * successful.</p>
   *
   * <p>In case if a day on which investment needs to be done is a holiday, investment will be done
   * on the next available business day</p>
   *
   * <p>For each investment the closing price is taken as the share price for all the ticker
   * symbols.</p>
   *
   * @param portfolioName      the name of the portfolio for which Dollar Cost Averaging investment
   *                           is made.
   * @param stockNameAndWeight A map of stock names and corresponding percentage weights of the
   *                           amount to be invested for that particular stock.
   * @param amount             the fixed amount to be invested periodically.
   * @param investmentInterval the frequency of the investment in days.
   * @param startDate          the starting date of the investment.
   * @param endDate            the end date of the investment
   * @param commissionFee      the fee paid for brokerage. The commission fee is applicable for 'n'
   *                           number of stocks of each company stock. If the stocks are Google and
   *                           Apple and if the commission fee is $100, then in total commission
   *                           would be $200.
   * @throws IllegalArgumentException when the investment interval is less than 1, start date is
   *                                  null or empty, Entered date not in format: 'dd-MM-yyyy', start
   *                                  date is not before the end date, amount or commission fee is
   *                                  negative, the sum of percentages for the stocks is not equal
   *                                  to 100 or if the percentage is negative, if the ticker symbol
   *                                  is invalid.
   */
  public void dollarCostAveraging(String portfolioName, Map<String, Double> stockNameAndWeight,
                                  double amount, int investmentInterval, String startDate,
                                  String endDate, double commissionFee)
          throws IllegalArgumentException {

    validateInputForDollarCostAverageBasis(stockNameAndWeight, amount,
            investmentInterval, startDate, endDate, commissionFee);

    if (portfolioName == null || portfolioName.equals("")) {
      throw new IllegalArgumentException("The portfolio name cannot be null or empty.");
    }

    DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-uuuu")
            .withResolverStyle(ResolverStyle.STRICT);
    LocalDate startdate = LocalDate.parse(startDate, format);
    LocalDate actualEnddate = null;

    if (!endDate.equals("")) {
      actualEnddate = LocalDate.parse(endDate, format);
    }

    LocalDate enddate;

    if (endDate.equals("")) {
      enddate = LocalDate.now();
    } else {

      enddate = LocalDate.parse(endDate, format);

      if (enddate.compareTo(LocalDate.now()) > 0) {

        enddate = LocalDate.now();
      }
    }

    LocalDate investmentDate = startdate;
    Calendar investmentCalendar = GregorianCalendar.from(investmentDate.atStartOfDay(
            ZoneId.systemDefault()));

    LocalDate actualInvestmentDate = startdate;

    PortfolioInterface portfolio = getPortfolio(portfolioName);

    if (portfolio == null) {
      portfolio = new Portfolio(portfolioName.trim().toUpperCase());
      this.portfolios.add(portfolio);
    }


    while (investmentDate.compareTo(enddate) <= 0) {

      if (isHoliday(investmentCalendar)) {
        investmentDate = investmentDate.plusDays(1);
        investmentCalendar = GregorianCalendar.from(investmentDate
                .atStartOfDay(ZoneId.systemDefault()));
        continue;
      }

      String time = "15:59";
      String investmentDateString = this.getDateInFormat(investmentDate) + " " + time;

      invest(portfolioName, stockNameAndWeight, amount, commissionFee, investmentDateString);

      investmentDate = actualInvestmentDate.plusDays(investmentInterval);
      actualInvestmentDate = investmentDate;
      investmentCalendar = GregorianCalendar.from(investmentDate
              .atStartOfDay(ZoneId.systemDefault()));
    }

    if (actualEnddate == null) {

      portfolio.addStrategy(new DollarCostStrategy(startdate, investmentInterval,
              commissionFee, amount, stockNameAndWeight,
              actualInvestmentDate));
    } else {

      portfolio.addStrategy(new DollarCostStrategy(startdate, actualEnddate, investmentInterval,
              commissionFee, amount, stockNameAndWeight,
              actualInvestmentDate));
    }


  }

  @Override
  public List<String> getPortfolioNames() {

    List<String> portfolioNames = new ArrayList<>();

    for (PortfolioInterface portfolio : this.portfolios) {
      portfolioNames.add(portfolio.getName());
    }

    return portfolioNames;
  }

  @Override
  public List<String> getStrategyNames() {

    List<String> strategyNames = new ArrayList<>();
    for (DollarCostStrategy strategy : this.dollarCostStrategies) {
      strategyNames.add(strategy.getStrategyName());
    }
    return strategyNames;
  }

  /**
   * A helper method that returns the portfolio object if it exists else will return null.
   *
   * @param portfolioName the name of the portfolio, it is case insensitive
   * @return portfolio object of type PortfolioInterface if it exists otherwise null
   * @throws IllegalArgumentException if the portfolio name is null or empty
   */
  private PortfolioInterface getPortfolio(String portfolioName) throws IllegalArgumentException {

    if (portfolioName == null || portfolioName.equals("")) {
      throw new IllegalArgumentException("Portfolio name cannot be empty or null");
    }

    PortfolioInterface portfolio = null;

    for (PortfolioInterface p : portfolios) {
      if (p.getName().equalsIgnoreCase(portfolioName.trim())) {
        portfolio = p;
      }
    }
    return portfolio;
  }

  /**
   * A private helper method to check if amount, commission fee, stock name and corresponding
   * investment percentages are all valid.
   *
   * @param amount             the amount for which investment is to be made on a portfolio.
   * @param commissionFee      the fee paid as brokerage for buying N number of shares of a ticker
   *                           symbol.
   * @param stockNameAndWeight map containing company's ticker symbol as the key and corresponding
   *                           investment percentage of the total investment amount.
   * @throws IllegalArgumentException when the amount is negative, commission fee is negative, the
   *                                  sum of percentages for the stocks is not equal to 100
   */
  private void isAmountCommissionPercentageValid(double amount, double commissionFee,
                                                 Map<String, Double> stockNameAndWeight)
          throws IllegalArgumentException {

    if (amount < 0.00) {
      throw new IllegalArgumentException("The investment amount cannot be negative");
    }

    if (commissionFee < 0.00) {
      throw new IllegalArgumentException("The commission fee cannot be negative");
    }

    double totalPercentage = 0.00;

    for (Map.Entry<String, Double> entry : stockNameAndWeight.entrySet()) {

      String tickerSymbol = entry.getKey();

      if (tickerSymbol == null || tickerSymbol.equals("")) {
        throw new IllegalArgumentException("The ticker symbol cannot be null or empty.");
      }

      double investmentPercentage = entry.getValue();

      if (investmentPercentage < 0.00) {
        throw new IllegalArgumentException("The percentage for stock investment cannot "
                + "be negative.");
      }
      totalPercentage += investmentPercentage;

    }

    if (!( ( 100.00 - totalPercentage ) >= 0.00 && ( 100.00 - totalPercentage ) < 1.00 )) {
      throw new IllegalArgumentException("Unable to invest as the sum of the percentage of"
              + " stock investments is not"
              + "equal to 100 or 99.xx");
    }

  }

  /**
   * a helper method to validate date is in format dd-MM-YYYY or not.
   *
   * @param date date string in dd-mm-yyyy format
   * @param name which type of date, start date or end date.
   * @throws IllegalArgumentException when the date is of unsupported format.
   */
  private void validateDate(String date, String name) throws IllegalArgumentException {

    try {
      DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-uuuu")
              .withResolverStyle(ResolverStyle.STRICT);
      LocalDate.parse(date, format);
    } catch (DateTimeParseException e) {
      throw new IllegalArgumentException(name + " Date should be of format dd-MM-yyyy and should be"
              + " a valid calendar date.");
    }
  }

  /**
   * A private helper method to check the validity of portfolio name, stocks and weight percentages,
   * amount, commission fee, investment interval, start date and end date used by invest and dollar
   * cost average methods.
   *
   * @param stockNameAndWeight A map of stock names and corresponding percentage weight of the
   *                           amount to be invested for that particular stock.
   * @param amount             the fixed amount to be invested periodically.
   * @param investmentInterval the frequency of the investment in days.
   * @param startDate          the starting date of the investment.
   * @param endDate            the end date of the investment
   * @param commissionFee      the fee paid for brokerage. The commission fee is applicable for 'n'
   *                           number of stocks of each company stock. If the stocks are Google and
   *                           Apple and if the commission fee is $100, then in total commission
   *                           would be $200. This is in addition to the amount, exclusive.
   * @throws IllegalArgumentException when the portfolio already exists, the investment interval is
   *                                  less than 1 day, start date is null or empty, Entered date not
   *                                  in format: 'dd-MM-yyyy', stockandweight is null, start date is
   *                                  not before the end date, start date in future, amount or
   *                                  commission fee is negative,  the sum of percentages for the
   *                                  stocks is not equal to 100 or if the percentage is negative,
   *                                  if the ticker symbol is invalid.
   */
  private void validateInputForDollarCostAverageBasis(Map<String, Double> stockNameAndWeight,
                                                      double amount, int investmentInterval,
                                                      String startDate,
                                                      String endDate, double commissionFee)
          throws IllegalArgumentException {

    if (investmentInterval < 1) {
      throw new IllegalArgumentException(" Unable to apply strategy."
              + "The interval between the investments cannot be less "
              + "than one day");
    }

    if (stockNameAndWeight != null) {
      if (stockNameAndWeight.size() == 0) {
        throw new IllegalArgumentException("For applying strategy portfolio"
                + "should have at least one stock added to it");
      }
    }

    isAmountCommissionPercentageValid(amount, commissionFee, stockNameAndWeight);

    if (startDate == null || startDate.equals("")) {
      throw new IllegalArgumentException("The start date cannot be null or empty");
    }
    if (endDate == null) {
      throw new IllegalArgumentException("The end date cannot be null");
    }
    validateDate(startDate, "start");

    if (!endDate.equals("")) {
      validateDate(endDate, "end");
    }

    DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-uuuu")
            .withResolverStyle(ResolverStyle.STRICT);
    LocalDate startdate = LocalDate.parse(startDate, format);
    LocalDate enddate;

    if (endDate.equals("")) {
      enddate = LocalDate.now();
    } else {
      enddate = LocalDate.parse(endDate, format);

      if (startdate.compareTo(enddate) > 0) {

        throw new IllegalArgumentException("Unable to apply strategy."
                + "start date should be before end date");
      }
    }

    for (String tickerSymbol : stockNameAndWeight.keySet()) {
      isTickerSymbolValid(tickerSymbol.trim().toUpperCase());
    }

  }

  /**
   * A private helper method to determine whether the given ticker symbol is valid or not based on
   * the identified valid symbols present in the file.
   *
   * @param tickerSymbol the ticker symbol of a company which uniquely identifies it in a stock
   *                     market to buy share of the company.
   * @throws IllegalArgumentException if the ticker symbol does not exist in the file
   * @throws FileNotFoundException    if the file is not found in the provided file path
   */
  private void isTickerSymbolValid(String tickerSymbol) throws IllegalArgumentException {

    String filePath = "./tickersymbols.txt";
    File file = new File(filePath);
    Scanner sc;
    StringBuilder sb = new StringBuilder();
    try {
      sc = new Scanner(file);
      while (sc.hasNext()) {
        if (tickerSymbol.equalsIgnoreCase(sc.next())) {
          return;
        }
      }
    } catch (FileNotFoundException e) {

      throw new IllegalStateException("The valid ticker symbol file is not found.");
    }
    throw new IllegalArgumentException("Ticker Symbol: " + tickerSymbol + " is invalid");
  }

  /**
   * A private helper method to return the LocalDate object as string in dd-mm-yyyy date format.
   *
   * @param date LocalDate object, any valid date.
   * @return String the given LocalDate in the format dd-mm-yyyy
   */
  private String getDateInFormat(LocalDate date) {

    StringBuilder sb = new StringBuilder();
    String day = "" + date.getDayOfMonth();
    if (day.length() == 1) {
      day = "0" + day;
    }
    sb.append(day);
    sb.append("-");
    String monthValue = "" + date.getMonthValue();
    if (monthValue.length() == 1) {
      monthValue = "0" + monthValue;
    }
    sb.append(monthValue);
    sb.append("-");
    sb.append(date.getYear());

    return sb.toString();
  }


  /**
   * A method that takes a portfolio name, ticker symbol and add the stock(i.e. ticker symbol) to
   * the set of stocks the portfolio can contain. If the provided portfolio doesn't exist, it will
   * be created and then the ticker symbol will be added.
   *
   * @param portfolioName Name of the portfolio.
   * @param tickerSymbol  Symbol that can uniquely identify a stock in a stock market.
   * @throws IllegalArgumentException when the portfolio name is null or empty, ticker symbol is
   *                                  null, empty or if it doesn't matches with any of the ticker
   *                                  symbols(stocks) listed in the NYSE.
   */
  public void addStock(String portfolioName, String tickerSymbol) throws
          IllegalArgumentException {

    PortfolioInterface portfolio = getPortfolio(portfolioName);
    if (portfolio == null) {
      portfolio = new Portfolio(portfolioName.trim().toUpperCase());
      portfolios.add(portfolio);
    }

    if (tickerSymbol == null || tickerSymbol.equals("")) {
      throw new IllegalArgumentException("Ticker symbol can't be null or empty");
    }
    tickerSymbol = tickerSymbol.trim().toUpperCase();
    isTickerSymbolValid(tickerSymbol);
    portfolio.addToStocksSet(tickerSymbol);
  }


  @Override
  public List<String> getStocksOfAPortfolio(String portfolioName) throws IllegalArgumentException {

    PortfolioInterface portfolio = getPortfolio(portfolioName);

    if (portfolio == null) {
      throw new IllegalArgumentException("Provided portfolio name doesn't exist");
    }

    return portfolio.getStocksInAPortfolio();

  }


  /**
   * The method saves the portfolio in a text file under the directory 'saved portfolios', the
   * directory must exist inorder for the application to save the file. For each portfolio one text
   * file with the name same as the portfolio name is created.
   *
   * @param portfolioName the name of the portfolio to be saved
   * @throws IllegalArgumentException when the portfolio name is null, empty or the portfolio
   *                                  does not exist.
   * @throws IllegalStateException    if the file cannot be saved.
   */
  public void savePortfolio(String portfolioName) throws IllegalArgumentException,
          IllegalStateException {

    PortfolioInterface portfolio = getPortfolio(portfolioName);

    if (portfolio == null) {
      throw new IllegalArgumentException("The portfolio does not exists.");
    }

    Gson json = new Gson();
    String portfolioInJSONFormat = json.toJson(portfolio, Portfolio.class);
    writePortfolioToFile(portfolioInJSONFormat, portfolioName);

  }


  @Override
  public void retrievePortfolios() throws IllegalStateException {

    Gson json = new Gson();
    String target_dir = "./saved portfolios";
    File dir = new File(target_dir);
    File[] files = dir.listFiles();

    if (files == null) {
      throw new IllegalStateException("Expected directory: "
              + target_dir.substring(2) + " doesn't exist."
              + " Please have it in place as mentioned in SETUP-README.txt file "
              + "and restart application");
    }

    int i = 0;
    for (File f : files) {
      int count = 0;
      if (f.isFile()) {
        BufferedReader inputStream = null;
        try {
          inputStream = new BufferedReader(
                  new FileReader(f));
          String line;
          while (( line = inputStream.readLine() ) != null) {
            PortfolioInterface portfolio;

            try {
              portfolio = json.fromJson(line, Portfolio.class);
            } catch (JsonParseException e) {
              throw new IllegalStateException("Sorry unable to load the data, please reopen "
                      + "the application");
            }
            PortfolioInterface duplicate = getPortfolio(portfolio.getName());

            if (duplicate != null) {
              continue;
            }

            portfolios.add(portfolio);
            if (( (Portfolio) portfolio ).getStrategies() != null) {
              for (DollarCostStrategy dollarCostStrategy : ( (Portfolio) portfolio )
                      .getStrategies()) {

                checkForStocks(portfolio, dollarCostStrategy);
              }
            }
          }
        } catch (FileNotFoundException e) {
          throw new IllegalStateException("Unable to load from the file. Please reopen the "
                  + "application.");
        } catch (IOException e) {
          throw new IllegalStateException("Unable to load the data. Please reopen the "
                  + "application.");
        } finally {
          if (inputStream != null) {
            try {
              inputStream.close();
            } catch (IOException e) {
              throw new IllegalStateException("Unable to load the data. Please reopen the "
                      + "application.");
            }
          }
        }
      }
    }
  }


  @Override
  public void createStrategy(String strategyName, Map<String, Double> stockNameAndWeight,
                             double amount, int investmentInterval, String startDate,
                             String endDate, double commissionFee)
          throws IllegalArgumentException {

    DollarCostStrategy dollarCostStrategy = getStrategy(strategyName);

    if (dollarCostStrategy != null) {
      throw new IllegalArgumentException("Unable to create strategy as the name already exists");
    }

    validateInputForDollarCostAverageBasis(stockNameAndWeight, amount,
            investmentInterval, startDate, endDate, commissionFee);

    DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-uuuu")
            .withResolverStyle(ResolverStyle.STRICT);
    LocalDate startdate = LocalDate.parse(startDate, format);
    LocalDate enddate;

    if (endDate.equals("")) {
      enddate = null;
    } else {
      enddate = LocalDate.parse(endDate, format);
    }

    this.dollarCostStrategies.add(new DollarCostStrategy(strategyName.trim().toUpperCase(),
            startdate, enddate, investmentInterval, commissionFee, amount,
            stockNameAndWeight));

  }

  @Override
  public void applyStrategy(String strategyName, String portfolioName)
          throws IllegalArgumentException {

    DollarCostStrategy dollarCostStrategy = getStrategy(strategyName);

    if (dollarCostStrategy == null) {
      throw new IllegalArgumentException("Unable to apply strategy: " + strategyName
              + " as it does not exist.");
    }

    String startDate = getDateInFormat(dollarCostStrategy.getStartDate());

    String endDate = "";
    if (dollarCostStrategy.getEndDate() == null) {
      endDate = "";
    } else {
      endDate = getDateInFormat(dollarCostStrategy.getEndDate());
    }

    dollarCostAveraging(portfolioName, dollarCostStrategy.getStockAndPercentOfInvestment(),
            dollarCostStrategy.getAmount(), dollarCostStrategy.getInvestmentInterval(),
            startDate, endDate, dollarCostStrategy.getCommissionFee());
  }


  /**
   * The method saves the strategy in a text file under the directory 'saved strategies', the
   * directory must exist inorder for the application to save the file. For each strategy one text
   * file with the name same as the strategy name is created.
   *
   * @param strategyName the name of the strategy to be saved
   * @throws IllegalArgumentException when the strategy name is null, empty or the strategy itself
   *                                  does not exist.
   * @throws IllegalStateException    if the file cannot be saved.
   */
  public void saveStrategy(String strategyName) throws IllegalArgumentException,
          IllegalStateException {

    DollarCostStrategy dollarCostStrategy = getStrategy(strategyName);

    if (dollarCostStrategy == null) {
      throw new IllegalArgumentException("The Dollar cost strategy does not exist.");
    }

    Gson json = new Gson();
    String strategyInJSONFormat = json.toJson(dollarCostStrategy);
    writeStrategyToFile(strategyInJSONFormat, strategyName);

  }

  @Override
  public void retrieveStrategies() throws IllegalStateException {

    Gson json = new Gson();
    String target_dir = "./saved strategies";
    File dir = new File(target_dir);

    File[] files = dir.listFiles();

    if (files == null) {
      throw new IllegalStateException("Expected directory: "
              + target_dir.substring(2) + " doesn't exist."
              + " Please have it in place as mentioned in SETUP-README.txt file "
              + "and restart application");
    }

    ArrayList<DollarCostStrategy> strategies = new ArrayList<>();

    for (File f : files) {
      if (f.isFile()) {
        BufferedReader inputStream = null;
        try {
          inputStream = new BufferedReader(
                  new FileReader(f));
          String line;
          while (( line = inputStream.readLine() ) != null) {

            DollarCostStrategy strategy;
            try {
              strategy = json.fromJson(line, DollarCostStrategy.class);
            } catch (JsonParseException e) {
              throw new IllegalStateException("Sorry unable to load the data, please reopen "
                      + "the application");
            }
            this.dollarCostStrategies.add(strategy);
          }
        } catch (IOException e) {
          throw new IllegalStateException("Unable to load from the file. Please reopen the "
                  + "application.");
        } finally {
          if (inputStream != null) {
            try {
              inputStream.close();
            } catch (IOException e) {
              throw new IllegalStateException("Unable to load the data. Please reopen the "
                      + "application.");
            }
          }
        }
      }
    }
  }

  /**
   * A method to save all the portfolios and strategies created, updated during the current
   * application state.
   *
   * @throws IllegalArgumentException when the portfolio name or strategy name is null, empty or the
   *                                  portfolio or strategy does not exist.
   * @throws IllegalStateException    if the file cannot be saved.
   */
  public void saveSession() throws IllegalArgumentException, IllegalStateException {

    for (PortfolioInterface portfolio : this.portfolios) {
      savePortfolio(portfolio.getName());
    }
    for (DollarCostStrategy dollarCostStrategy : this.dollarCostStrategies) {
      saveStrategy(dollarCostStrategy.getStrategyName());
    }
  }

  /**
   * A private helper to check if the portfolio exists or not in th list of portfolios. Also
   * validates the name of the given portfolio.
   *
   * @param portfolioName the name of the portfolio.
   * @return whether portfolio exists or not
   */
  private boolean isPortfolioExists(String portfolioName) {

    if (portfolioName == null || portfolioName.equals("")) {
      throw new IllegalArgumentException("The given portfolio name cannot be null or empty "
              + "for the save " + "operation.");
    }

    for (PortfolioInterface portfolio : portfolios) {
      if (portfolio.getName().equalsIgnoreCase(portfolioName)) {
        //this is the portfolio to be saved in JSON format.
        return true;
      }
    }
    return false;
  }

  /**
   * A private helper method to write the portfolio content in JSON to the file in the folder 'saved
   * portfolios'. The method creates a text file with the name as the portfolio name and writes the
   * content to it.
   *
   * @param content       the portfolio in JSON format
   * @param portfolioName the name of the portfolio to be saved
   * @throws IllegalStateException if the file cannot be saved.
   */
  private void writePortfolioToFile(String content, String portfolioName)
          throws IllegalStateException {

    BufferedWriter writer = null;
    try {
      writer = new BufferedWriter(new FileWriter(
              ".\\saved portfolios\\" + portfolioName + ".txt"));
      writer.write(content);

    } catch (IOException e) {
      throw new IllegalStateException("Unable to save the portfolio.");
    } finally {
      try {
        if (writer != null) {
          writer.close();
        }
      } catch (IOException e) {
        throw new IllegalStateException("Unable to save the portfolio.");
      }

    }
  }

  /**
   * A private helper method to write the strategy content in JSON to the file in the folder 'saved
   * strategies'. The method creates a text file with the name as the strategy name and writes the
   * content to it.
   *
   * @param content      the strategy in JSON format
   * @param strategyName the name of the strategy to be saved
   * @throws IllegalStateException if the strategy cannot be saved.
   */
  private void writeStrategyToFile(String content, String strategyName)
          throws IllegalStateException {

    BufferedWriter writer = null;
    try {
      writer = new BufferedWriter(new FileWriter(
              ".\\saved strategies\\" + strategyName + ".txt"));
      writer.write(content);

    } catch (IOException e) {
      throw new IllegalStateException("Unable to save the strategy.");
    } finally {
      try {
        if (writer != null) {
          writer.close();
        }
      } catch (IOException e) {
        throw new IllegalStateException("Unable to save the strategy.");
      }
    }
  }

  /**
   * A private helper method to buy and add stocks to the portfolio for the strategies whose end
   * date is in future.
   *
   * @param portfolio          the portfolio object
   * @param dollarCostStrategy the strategy for which stocks are checked
   */
  private void checkForStocks(PortfolioInterface portfolio,
                              DollarCostStrategy dollarCostStrategy) {

    LocalDate investmentDate = dollarCostStrategy.getNextBuyDate();
    LocalDate actualInvestmentDate = investmentDate;

    LocalDate endDateOfStrategy = dollarCostStrategy.getEndDate();

    if (endDateOfStrategy == null) {
      endDateOfStrategy = LocalDate.now();
    }

    double amount = dollarCostStrategy.getAmount();
    double commissionFee = dollarCostStrategy.getCommissionFee();
    Map<String, Double> stockNameAndWeight = dollarCostStrategy.getStockAndPercentOfInvestment();
    int investmentInterval = dollarCostStrategy.getInvestmentInterval();

    Calendar investmentCalendar = GregorianCalendar.from(investmentDate.atStartOfDay(
            ZoneId.systemDefault()));

    LocalDate dateTillStockDataAvailable = null;

    LocalDate todaysDate = LocalDate.now();

    if (endDateOfStrategy.compareTo(todaysDate) == -1) {
      dateTillStockDataAvailable = endDateOfStrategy;
    } else {
      dateTillStockDataAvailable = todaysDate;
    }
    while (investmentDate.compareTo(dateTillStockDataAvailable) <= 0) {
      if (isHoliday(investmentCalendar)) {
        investmentDate = investmentDate.plusDays(1);
        investmentCalendar = GregorianCalendar.from(investmentDate
                .atStartOfDay(ZoneId.systemDefault()));
        continue;
      }
      String time = "15:59";
      String investmentDateString = this.getDateInFormat(investmentDate) + " " + time;

      invest(portfolio.getName(), stockNameAndWeight, amount, commissionFee, investmentDateString);

      investmentDate = actualInvestmentDate.plusDays(investmentInterval);
      actualInvestmentDate = investmentDate;
      investmentCalendar = GregorianCalendar.from(investmentDate
              .atStartOfDay(ZoneId.systemDefault()));
    }

    dollarCostStrategy.setNextBuyDate(actualInvestmentDate);
    savePortfolio(portfolio.getName());
  }


  /**
   * A private helper method to find if a given strategy already exists in the model or not. if
   * present returns that strategy otherwise null. Also validates the name of the strategy.
   *
   * @param strategyName the name of the strategy
   * @return the strategy
   * @throws IllegalArgumentException when the strategy name is null or empty
   */
  private DollarCostStrategy getStrategy(String strategyName) throws IllegalArgumentException {

    if (strategyName == null || strategyName.equals("")) {
      throw new IllegalArgumentException("The strategy name cannot be null or empty.");
    }
    for (DollarCostStrategy strategy : this.dollarCostStrategies) {

      if (strategyName.equalsIgnoreCase(strategy.getStrategyName())) {
        return strategy;
      }
    }
    return null;
  }

}
