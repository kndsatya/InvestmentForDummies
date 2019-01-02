package model;

import java.util.List;

/**
 * <p>Interface for a stock portfolio that offer methods such as getName,addStock,getstocks.</p>
 *
 * <p>Changes -  Two new methods are added - addToStocksSet() and getStocksInAPortfolio() as well as
 *  instance variable uniqueStocksOfAPortfolio is added which represents a set of stocks a
 *  portfolio can contain. These methods are used for getting the percentage
 *  weights for each stock for invest and dollarCostAverage methods</p>
 */
public interface PortfolioInterface {

  /**
   * Gets the name of an individual portfolio.
   *
   * @return String name of the portfolio.
   */
  String getName();

  /**
   * Add a stock of type StockInterface to the portfolio.
   *
   * @param stock of type StockInterface
   */
  void addStock(Stock stock);


  /**
   * Gets all the stocks of the portfolio.
   *
   * @return List of stocks of type StockInterface
   */
  List<StockInterface> getStocks();

  /**
   * A method that takes a tickerSymbol as input, add it to set of stocks a portfolio can contain.
   *
   * @param tickerSymbol a symbol that uniquely identifies a stock in a stock market.
   */
  void addToStocksSet(String tickerSymbol);

  /**
   * A method that provides you with the list of stocks that a portfolio contain.
   *
   * @return List of stocks present in a portfolio.
   */
  List<String> getStocksInAPortfolio();

  /**
   * Method adds a strategy applied on this portfolio to its list of strategies applied on it.
   * The default interface definition does nothing.
   *
   * @param dollarCostStrategy represents a strategy object.
   */
  default void addStrategy(DollarCostStrategy dollarCostStrategy) {
    return;
  }

  /**
   * Method returns a list of all the strategies applied on the portfolio.
   * The default implementation return null.
   *
   * @return list of strategies applied on the portfolio
   *
   */
  default List<DollarCostStrategy> getStrategies() {
    return null;
  }

}
