package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>This class represents a stock portfolio. It is the implementation of the
 * PortfolioInterface. The portfolio class provides functionalities such as adding stocks to the
 * portfolio, and getting all the stocks contained by the portfolio at any point of time</p>
 *
 * <p>Changes -  Two new methods are added - addToStocksSet() and getStocksInAPortfolio() as well
 * as instance variable uniqueStocksOfAPortfolio is added in order to maintain the set of stocks a
 * portfolio contains.(stocks that are not bought, it just contain tickers).  </p>
 */
public class Portfolio implements PortfolioInterface {

  protected final String portfolioName;
  protected final List<Stock> stocks;//represents the list of stocks a portfolio contains.
  protected final Set<String> uniqueStocksOfAPortfolio;//set of stocks a portfolio can contain.
  protected List<DollarCostStrategy> strategies;

  /**
   * Constructs a Portfolio object by assigning the name of the portfolio, initializing the stock
   * list to an empty ArrayList and initializing the set of uniqueStocksOfAPortfolio to empty set.
   *
   * @param name Name of the portfolio
   */
  public Portfolio(String name) {
    this.portfolioName = name;
    stocks = new ArrayList<>();
    uniqueStocksOfAPortfolio = new HashSet<>();
    strategies = new ArrayList<>();
  }


  /**
   * Returns the name of the portfolio.
   *
   * @return String name of the portfolio
   */
  public String getName() {
    return portfolioName;
  }

  /**
   * Add a stock to the portfolio which is of StockInterface type. Also add the ticker symbol of the
   * stock to the set of uniqueStocks the portfolio contains.
   *
   * @param stock of type StockInterface
   */
  public void addStock(Stock stock) {
    stocks.add(stock);
    addToStocksSet(stock.getStockTickerSymbol());
  }

  /**
   * Get the list of stocks and corresponding shares contained by the portfolio. The stock is of
   * StockInterface type.
   *
   * @return List list of stocks
   */
  public List<StockInterface> getStocks() {
    return new ArrayList<>(stocks);
  }

  @Override
  public List<DollarCostStrategy> getStrategies() {
    return this.strategies;
  }

  @Override
  public void addToStocksSet(String tickerSymbol) {
    uniqueStocksOfAPortfolio.add(tickerSymbol);
  }

  @Override
  public List<String> getStocksInAPortfolio() {
    return new ArrayList<>(uniqueStocksOfAPortfolio);
  }

  @Override
  public void addStrategy(DollarCostStrategy strategy) {
    this.strategies.add(strategy);
  }


}
