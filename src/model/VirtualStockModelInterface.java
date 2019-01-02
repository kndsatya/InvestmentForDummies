package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>This is the interface of the model of the virtual stock game. This provides functionalities
 * to create one or more portfolios to which virtual stocks can be bought and added, each of which
 * belongs to a company and bought at a specific date. The stock in turn contains one or more shares
 * whose value changes everyday same as a stock market.</p>
 *
 * <p>Also provides functionalities that helps to find the totalCostBasis which gives the total
 * amount the user has invested in buying the stocks on or before a particular date. Another
 * functionality TotalValue gives the worth of the stocks bought on or before the given date based
 * on the stock price for that date.</p>
 *
 * <p>Provides Display portfolio and display all portfolios functionality that gives the portfolio
 * and its stock contents such as ticker symbol, buy date, number of shares and price of each
 * share.</p>
 *
 * <p>Changes made for Assignment 9 - To accommodate the new features, new methods are added to buy
 * stocks with the commission fee, investment in multiple stocks and setting Dollar cost average
 * strategy on a portfolio to the existing interface itself. The reasons for changing the existing
 * interface are:
 *
 * 1. If we choose an alternative approach of creating new model interface that extends the existing
 * interface which will have the new methods, then the VirtualStockModel class has to change as well
 * as the argument type to controller and command classes should change to use this new model
 * interface. In case of changing the existing interface only VirtualStockModel class changes i.e.
 * it will implement more methods which will have to change anyway.
 *
 * 2. The only consumer of this existing is the VirtualStockModel class and so changing the existing
 * VirtualStockModelInterface won't affect any other consumers. Even if there are other consumers,
 * the new methods are added using Java 8 'default' method in interface feature. This way no
 * existing users of the VirtualStockModelInterface won't face any compilation error.
 *
 * The new methods added are buySharesofStock() that takes commission fee as extra, invest() that
 * buys shares of multiple stocks and adds them to an existing portofolio and dollarCostAverage()
 * method that invests a fixed amount on the user specified stocks over repeatedly over a given
 * period of time.
 *
 * Also, in the total_cost_basis method we have included commission fee while calculating the
 * total_cost_basis.</p>
 *
 * <p> Changes made for Assignment 10 - Now the VirtualStockModel supports persistence i.e. saving
 * portfolios, strategies to files and also retrieving them back from files. Also apply strategy
 * applies an existing strategy on an existing portfolio,
 * </p>
 *
 * <p>These new methods are added to the existing VirtualStockModelInterface due to the same
 * reasons mentioned above.</p>
 */
public interface VirtualStockModelInterface {

  /**
   * Creates a new portfolio by taking the name for the portfolio.
   *
   * @param name the name of the portfolio
   * @throws IllegalArgumentException when the name is null or empty
   */
  void createPortfolio(String name) throws IllegalArgumentException;

  /**
   * Buy a stock of a company and add it to one of the existing portfolios. The user gives the
   * amount and for which the shares are bought as whole - integral shares.
   *
   * @param tickerSymbol  the ticker symbol of the company
   * @param portfolioName the name of teh portfolio to which the stock has to be added
   * @param amount        the amount for which shares are to be bought
   * @param date          the date on which the stock is to be bought
   * @throws IllegalArgumentException when one of the parameters is null or empty. The amount cannot
   *                                  be negative and also when the amount is insufficient to buy at
   *                                  least one share.
   */
  void buySharesOfStock(String tickerSymbol, String portfolioName, double amount, String date)
          throws IllegalArgumentException;

  /**
   * Buy a stock of a company and add it to one of the existing portfolios. The user gives the
   * amount and for which the shares are bought as whole - integral shares. In addition to this,
   * commission fee can be added for each transaction. Commission fee is the fee paid to brokerage
   * for buying certain number of shares of a company stock. It is exclusive to amount.
   *
   * @param tickerSymbol  the ticker symbol of the company
   * @param portfolioName the name of the portfolio to which the stock has to be added
   * @param amount        the amount for which shares are to be bought
   * @param date          the date on which the stock is to be bought
   * @param commissionFee the fee paid as brokerage to buy the stocks. It cannot be negative.
   * @throws IllegalArgumentException when one of the parameters is null or empty. The amount cannot
   *                                  be negative and also when the amount is insufficient to buy at
   *                                  least one share. If commission fee is negative.
   */
  default void buySharesOfStock(String tickerSymbol, String portfolioName, double amount,
                                String date, double commissionFee)
          throws IllegalArgumentException {
    return;
  }

  /**
   * Gives the total amount invested in buying the stocks on a particular date which is called the
   * total cost basis for the portfolio. This gives the total amount of all the stocks bought on or
   * before that date.
   *
   * @param portfolioName the name of the portfolio for which totalCostBasis is to be found
   * @param date          the date till which the totalCostBasis is found
   * @return double the totalCostBasis for the portfolio on the given date
   * @throws IllegalArgumentException when the portfolio name doesn't exist or name or date is not
   *                                  valid or empty,null
   */
  double getTotalCostBasis(String portfolioName, String date) throws IllegalArgumentException;

  /**
   * Gives the total value of the portfolio on a specific date based on the stocks bought on or
   * before the given date and the price of the share for that stock on that given date.
   *
   * @param portfolioName the name of the portfolio for which total value is to be found
   * @param date          the date on which the total value is to be found
   * @return double the total value or worth of the portfolio based on the stocks bought on or
   *         before the given date and the price of the share for that stock on that given date.
   * @throws IllegalArgumentException when one of the parameters is not valid, that is when name,
   *                                  date is null or empty. Also, exception is thrown when the
   *                                  portfolio doesn't exist.
   */
  double getTotalValue(String portfolioName, String date) throws IllegalArgumentException;

  /**
   * Provides the content of a single portfolio and its stock details in a specific format.
   *
   * @param portfolioName the name of the portfolio for which total value is to be found
   * @return String the portfolio and its stock in the specified format
   * @throws IllegalArgumentException when the given portfolio name is null or empty
   */
  List<String> displayPortfolio(String portfolioName) throws IllegalArgumentException;

  /**
   * Provides the content of all the portfolios and their stock details in a specific format.
   *
   * @return String the portfolios and their stocks in the specified format
   */
  Map<String, List<String>> displayAllPortfolios();

  /**
   * <p>Invest a fixed amount into an existing portfolio containing multiple stocks, using a
   * specified percentage of amount for each stock in the portfolio. For example, the user can
   * create a FANG portfolio (Facebook, Apple, Netflix, Google) and then specify to invest $2000 in
   * the portfolio, such that 40% goes towards Facebook, 20% towards Apple, 30% towards Netflix and
   * 10% towards Google)</p>
   *
   * <p> If for any of the stocks the specified amount is insufficient to buy even one share of the
   * stock then that stock is not bought, but remaining stocks are bought and added to the
   * portfolio
   * </p>
   *
   * @param portfolioName      the name of the portfolio on which the investment is to be made.
   * @param stockNameAndWeight the different percentages for the stocks
   * @param amount             the amount to be invested.
   * @param commissionFee      the fee paid for brokerage. The commission fee is applicable for 'n'
   *                           number of stocks of each company stock. If the stocks are Google and
   *                           Apple and if the commission fee is $100, then in total it would cost
   *                           $200 for buying certain number of Google and Apple stocks.
   * @param date               the date on which the stock is to be bought. The date has to be in
   *                           dd-MM-yyyy HH:mm.
   * @return String the investment summary stating whether the investment was successfully or not
   *         for each of the stocks.
   * @throws IllegalArgumentException when portfolio name is null or empty. The given portfolio does
   *                                  not exists. Also when the commission fee is negative. The
   *                                  individual percentage given for the stocks should not be
   *                                  negative and the sum of the percentages is either lesser or
   *                                  greater then 100. If the date isn't in valid format or when
   *                                  investment is to done on a non-business day/hour.
   */
  default String invest(String portfolioName, Map<String, Double> stockNameAndWeight, double amount,
                        double commissionFee, String date) throws IllegalArgumentException {
    return "";
  }

  /**
   * <p>Dollar Cost Averaging is an higher-level investment strategy where one can create a
   * portfolio of N stocks, and invest $X in the portfolio every Y days starting on startDate until
   * endDate using the same, fixed weights.</p>
   *
   * <p>The method can be called with empty end date, in that case the investment will be
   * ongoing without an end date.</p>
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
   * <p>If the portfolio does not exist, then this method creates a new portfolio and applies
   * dollar cost average strategy method on it. Multiple startegies can be applied on the same
   * portfolio</p>
   *
   * @param portfolioName      the name of the portfolio for which Dollar Cost Averaging investment
   *                           is made.
   * @param stockNameAndWeight A map of stock names and corresponding percentage weight of the
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
   *                                  date is not before the end date, start date in future, amount
   *                                  or commission fee is negative, the sum of percentages for the
   *                                  stocks is not equal to 100 or if the percentage is negative,
   *                                  if the ticker symbol is invalid.
   */
  default void dollarCostAveraging(String portfolioName, Map<String, Double> stockNameAndWeight,
                                   double amount, int investmentInterval, String startDate,
                                   String endDate, double commissionFee)
          throws IllegalArgumentException, UnsupportedOperationException {

    return;
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
  default void addStock(String portfolioName, String tickerSymbol) throws
          IllegalArgumentException {

    return;
  }

  /**
   * A method that takes a portfolio name and provides a list of stock details that are present in a
   * portfolio. If no stocks are present, an empty list will be returned. The default interface
   * implementation does nothing.
   *
   * @param portfolioName Name of the portfolio
   * @return List of stocks a portfolio contains.
   * @throws IllegalArgumentException if the portfolioName is null or empty or the portfolio doesn't
   *                                  exist.
   */
  default List<String> getStocksOfAPortfolio(String portfolioName)
          throws IllegalArgumentException {
    return new ArrayList<>();
  }

  /**
   * Method that provides all the portfolio names in a list of strings.
   *
   * @return list of portfolio names
   */
  default List<String> getPortfolioNames() {
    return new ArrayList<>();
  }

  /**
   * The method saves the portfolio in a file. Given the name of the portfolio, this method saves
   * the portfolio with its name if it exists. The default interface implementation does nothing.
   *
   * @param portfolioName the name of the portfolio
   * @throws IllegalArgumentException when the portfolio name is null, empty or the portfolio itself
   *                                  does not exist.
   * @throws IllegalStateException    if the portfolio cannot be saved.
   */
  default void savePortfolio(String portfolioName) throws IllegalArgumentException,
          IllegalStateException {
    return;
  }

  /**
   * Method retrieves all the portfolios along with their stocks and strategies applied on it, and
   * loads the data into the application. The default interface implementation does nothing.
   *
   * @throws IllegalStateException when the portfolios cannot be loaded into the application.
   */
  default void retrievePortfolios() throws IllegalStateException {
    return;
  }

  /**
   * Method to create a dollar cost averaging strategy. Provided the parameters such as amount to be
   * invested, stocks and their corresponding weights, duration of the investment strategy and
   * commission fee, creates a strategy with the provided name thus enabling to uniquely identify
   * the created strategy and reapply the strategy on other portfolios. The default interface
   * implementation does nothing.
   *
   * @param strategyName       the name of the strategy
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
   * @throws IllegalArgumentException when strategy name is null, empty or already exists. The
   *                                  investment interval is less than 1 day, start date is null or
   *                                  empty, Entered date not in format: 'dd-MM-yyyy', start date is
   *                                  not before the end date, start date in future, amount or
   *                                  commission fee is negative, the sum of percentages for the
   *                                  stocks is not equal to 100 or if the percentage is negative,
   *                                  if the ticker symbol is invalid.
   */
  default void createStrategy(String strategyName, Map<String, Double> stockNameAndWeight,
                              double amount, int investmentInterval, String startDate,
                              String endDate, double commissionFee)
          throws IllegalArgumentException {
    return;
  }

  /**
   * Method to set the created dollar cost averaging strategy using the createStrategy method on an
   * existing or new portfolio. Takes in the name of the strategy which was created and the name of
   * the portfolio on which the strategy is to be applied. The same strategy can be applied on
   * multiple portfolios as well as on the same portfolio multiple times. If the portfolio does not
   * exist then creates a new portfolio and applies the strategy on it. The default interface
   * implementation does nothing.
   *
   * @param strategyName  the name of the strategy
   * @param portfolioName the name of the portfolio
   * @throws IllegalArgumentException when the strategy name or portfolio names is null, empty or
   *                                  either of them or both does not exists
   */
  default void applyStrategy(String strategyName, String portfolioName)
          throws IllegalArgumentException {
    return;
  }

  /**
   * This method saves the strategy in a file. Given the name of the strategy, this method saves the
   * strategy with that name if it exists. The default interface implementation does nothing.
   *
   * @param strategyName the name of the strategy
   * @throws IllegalArgumentException when the strategy name is null, empty or the strategy itself
   *                                  does not exist.
   * @throws IllegalStateException    if the strategy cannot be saved.
   */
  default void saveStrategy(String strategyName) throws IllegalArgumentException,
          IllegalStateException {
    return;
  }


  /**
   * Method retrieves all the strategies, which are saved in the application and loads the data into
   * the application. The default interface implementation does nothing.
   *
   * @throws IllegalStateException when unable to load the strategies
   */
  default void retrieveStrategies() throws IllegalStateException {
    return;
  }

  /**
   * This method saves all the portfolios along with their stocks and strategies applied on it. Also
   * saves all the strategies created during the current application state in files. The default
   * interface implementation does nothing.
   *
   * @throws IllegalStateException if the file cannot be saved.
   */
  default void saveSession() throws
          IllegalStateException {
    return;
  }


  /**
   * Method gets all the strategy names in a list of strings.
   *
   * @return list of strategy names
   */
  default List<String> getStrategyNames() {
    return new ArrayList<>();
  }

}
