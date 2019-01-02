package view;

import java.util.List;

import guicontroller.ButtonListener;

/**
 * An interface for GUI based views of virtual stock model that offers various methods that help
 * controller to read data entered by the user. It extends the IMultiView interface as well.
 */
public interface IGUIView extends IMultiView {

  /**
   * A method that helps the controller to pass it self as an ActionListener and define it's own
   * actions instead of depending on the view.
   *
   * @param buttonListener represents a ButtonListener object.
   */
  public void addActionListener(ButtonListener buttonListener);

  /**
   * A method that provides the name of the portfolio entered by the user to the controller.
   *
   * @param title represents the name of the window through which data will be read from the user.
   * @return the String array of data entered by the user.
   */
  public String[] readDataofPortfolio(String title);

  /**
   * A method that provides the portfolio name selected by the user.
   *
   * @param title          represents the title of the window
   * @param portfolioNames list of available portfolios
   * @return the string array of data entered by the user
   */
  public String[] readDataofPortfolio(String title, List<String> portfolioNames);


  /**
   * A method that provides the data entered by the user in order to buy a stock.
   *
   * @param tickerSymbols represents the list of ticker symbols so that view can ask the user only
   *                      to choose from that list.
   * @return the string array that contains the data entered by the user.
   */
  public String[] getDetailsToBuyStock(String[] tickerSymbols);

  /**
   * A method that provides the data entered by the user in order to get the total cost basis of a
   * portfolio.
   *
   * @param portfolioNames represents a list of portfolio names that exists in the model, so that
   *                       view can ask the user to select a portfolio only from that list.
   * @return the string array that contains the data entered by the user.
   */
  public String[] getTotalCostBasisData(List<String> portfolioNames);

  /**
   * A method that provides the data entered by the user in order to get the total value of a
   * portfolio.
   *
   * @param portfolioNames represents a list of portfolio names that exists in the model, so that
   *                       view can ask the user to select a portfolio only from that list.
   * @return the string array that contains the data entered by the user.
   */
  public String[] getTotalValue(List<String> portfolioNames);

  /**
   * A method that provides the data entered by the user to a controller in order to invest in a
   * portfolio.
   *
   * @param stocks represents a list of stocks present in the portfolio and helps the view to ask
   *               the user to provide the percentage of amount he wants to invest for each of a
   *               stock.
   * @return a string array that contains the data entered  by the user.
   */
  public String[] getInvestmentDetails(List<String> stocks);

  /**
   * A method that provides the data entered by the user in order to apply dollar cost averaging
   * strategy on a portfolio.
   *
   * @param title represents the title of the window that asks the user for input.
   * @return a string array that contains the data entered by the user.
   */
  public String[] readDataOfDollarCostStrategy(String title);

  /**
   * A method that provides the data of stocks and their corresponding weights for periodic
   * investment entered by the user to the controller.
   *
   * @param stocksCount   represents the number of stocks user wants to have in a strategy.
   * @param tickerSymbols represents the list of ticker symbols that helps the view to ask the user
   *                      to choose only from those set of symbols.
   * @return a string array of data entered by the user.
   */
  public String[] getStocksAndWeightsData(int stocksCount, String[] tickerSymbols);

  /**
   * A method that provides the stock name and portfolio to which the stock needs to be added as
   * entered by the user to the controller.
   *
   * @param tickerSymbols represents a list of ticker symbols listed on NYSE.
   * @return a string array of data entered by the user.
   */
  public String[] readDataOfAddStock(String[] tickerSymbols);


  /**
   * A method that provides the portfolio name and strategy name entered by the user in order to
   * apply the strategy on a portfolio.
   *
   * @param strategies represents a list of available strategies
   * @param portfolios represents a list of available portfolios
   * @return a sting array of data entered by the user
   */
  public String[] readDataToApplyStrategy(List<String> strategies, List<String> portfolios);

  /**
   * A method that provides the strategy name selected by the user in order to save in permanent
   * storage.
   *
   * @param strategies represents a list of available strategies
   * @return a string array of data entered by the user
   */
  public String[] readStrategyToSave(List<String> strategies);

  /**
   * A method that provides the portfolio name selected by the user in order to save in permanent
   * storage.
   *
   * @param portfolios represents a list of available portfolios
   * @return a string array of data entered by the user
   */
  public String[] readPortfolioToSave(List<String> portfolios);



  /**
   * An overloaded method that displays a single error message sent by the controller/model.
   *
   * @param message represents an error messsage string.
   */
  public void writeErrorMessage(String message);

  /**
   * A method that displays a group of error messages sent by controller/model.
   *
   * @param messages represent a group of error messages sent by the controller/model.
   * @throws IllegalStateException is thrown when the view is unable to display message.
   */
  public void writeErrorMessage(String[] messages) throws IllegalStateException;

  public void showLoadingMessage();

}
