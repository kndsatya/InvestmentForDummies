package view;

import java.util.List;
import java.util.Map;

/**
 * Interface that provides common methods available for any GUI interface that extends it. Some of
 * them are writeToAppendable, writeErrorOrQuitMessage, displayStocksOfPortfolio and
 * displayAllPortfolios.
 */
public interface IMultiView {

  /**
   * A method that displays the message sent by the controller to the user.
   *
   * @param message message that needs to be displayed to the user.
   * @throws IllegalStateException is thrown when view is unable to display data to the uer.
   */
  public void writeToAppendable(String message) throws IllegalStateException;

  /**
   * A method that helps to display the error or quit messages sent by controller/model to the
   * view.
   *
   * @param message message that needs to be displayed to the user as error.
   * @throws IllegalStateException is thrown when view is unable to display message to the user.
   */
  default void writeErrorOrQuitMessage(String message) throws IllegalStateException {
    return;
  }

  /**
   * A method that displays the stock details of a portfolio to the user.
   *
   * @param stocks        represents a list of ticker symbols.
   * @param portfolioName represents a portfolio name
   * @throws IllegalStateException is thrown when view is unable to display.
   */
  public void displayStocksOfPortfolio(List<String> stocks, String portfolioName)
          throws IllegalStateException;


  /**
   * <p>Method that formats the portfolio stock contents and displays it to the user.</p>
   *
   * @param portfolioContent contents of a portfolio i.e. stocks details. Each element in the list
   *                         have a string representing a stock.
   * @param portfolioName    the name of the portfolio
   * @throws IllegalStateException is thrown when view is unable to display.
   */
  public void displayPortfolio(List<String> portfolioContent, String portfolioName)
          throws IllegalStateException;

  /**
   * <p>Method that formats the portfolio stock contents and displays it to the user. </p>
   *
   * @param portfoliosAndContents Map with key as portfolio name and value as corresponding list of
   *                              stocks in string format.
   * @throws IllegalStateException is thrown when view is unable to display.
   */
  public void displayAllPortfolios(Map<String, List<String>> portfoliosAndContents)
          throws IllegalStateException;

}
