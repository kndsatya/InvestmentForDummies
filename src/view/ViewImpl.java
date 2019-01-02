package view;


import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * A class that represents a view and implement IView interface providing concrete implementations
 * of the methods writeToAppendable, writeErrorOrQuitMessage, displayStocksOfPortfolio,
 * displayCommands and displayWaitingMessage.
 */
public class ViewImpl implements IView {

  private final Appendable out;

  /**
   * A constructor that constructs a ViewImpl object by taking Appendable object as an argument.
   * @param out represents the appendable object.
   */
  public ViewImpl(Appendable out) {
    this.out = out;
  }

  @Override
  public void writeToAppendable(String message) throws IllegalStateException {

    writetoOut(message + "\n");

  }

  @Override
  public void writeErrorOrQuitMessage(String message) throws IllegalStateException {


    try {
      out.append((char) 27 + "[35m" + message).append("\n");
    } catch (IOException e) {
      throw new IllegalStateException("Error: Unable to write the results\n");
    }

  }

  @Override
  public void displayStocksOfPortfolio(List<String> stocks, String portfolioName)
          throws IllegalStateException {

    writetoOut("Portfolio: " + portfolioName.trim().toUpperCase() + " contains below stocks:\n");
    for (String stock : stocks) {

      writetoOut(stock + "\n");
    }
  }

  @Override
  public void displayCommands() {
    writetoOut("\n\nCommand Name                        -      Command Description\n"
            + "-------------                              --------------------\n"
            + "CREATE_PORTFOLIO                    -      Command that helps to create a portfolio"
            + ".\n"
            + "DISPLAY_PORTFOLIO                   -      Command that displays the contents of a"
            + " portfolio.\n" + "BUY_STOCK                           -      Command to buy a stock"
            + " in"
            + " a portfolio with commission fee.\n" + "GET_TOTAL_COST_BASIS                -      "
            + "Command to get the total cost basis of a portfolio on a particular date.\n"
            + "GET_TOTAL_VALUE                     -      Command to get the total value of a"
            + " portfolio on a particular date.\n"
            + "DISPLAY_ALL_PORTFOLIOS              -      Command to display the contents of all"
            + " portfolios.\n" + "INVEST                              -      Command to invest in"
            + " an existing portfolio by specifying percentages of amount to invest.\n"
            + "ADD_STOCK                           -      Command to add stock(ticker symbol)"
            + " to a portfolio.\n"
            + "APPLY_DOLLAR_COST_STRATEGY          -      Command to create a new portfolio"
            + " and apply dollar cost averaging strategy on it.\n"
            + "DISPLAY_STOCKS_IN_PORTFOLIO         -      Command to display a set of stocks a "
            + "portfolio contains.\n"
            + "SAVE_PORTFOLIO                      -      Command to save a portfolio to a file.\n"
            + "SAVE_STRATEGY                       -      Command to save a strategy to a file.\n"
            + "CREATE_STRATEGY                     -      Command to create a dollar cost "
            + "averaging strategy.\n"
            + "APPLY_STRATEGY                      -      Command to apply a created strategy "
            + "on a portfolio.\n"
            + "SAVE_SESSION                        -      Command to save the current session, "
            + "saves all portfolios and strategies.\n"
            + "\n\nPlease enter any command from the above displayed list.\n\n");
  }

  @Override
  public void displayWaitingMessage() throws IllegalStateException {
    writetoOut("I am running the command. Please wait and bear with me............;)\n");
  }

  /**
   * <p>Method that formats the portfolio stock contents and displays it to the user.</p>
   *
   * <p>PortfolioName: portfolioName.
   * Ticker symbol: GOOG, Number of shares: 1, Price of Unit share: $927.13, Buy date: 05-05-2017,
   * Commission fee: 30.0. Ticker symbol: AAPL, Number of shares: 10, Price of Unit share: $148.96,
   * Buy date: 05-05-2017, Commission fee: 30.0. Ticker symbol: NFLX, Number of shares: 7, Price of
   * Unit share: $156.6, Buy date: 05-05-2017, Commission fee: 30.0</p>
   *
   * @param portfolioContent list of strings of stocks (containing details of ticker symbol, buy
   *                         date, unit share price and number of shares) for all the stocks that a
   *                         portfolio contains.
   * @param portfolioName    the name of the portfolio
   */
  public void displayPortfolio(List<String> portfolioContent, String portfolioName) {

    StringBuilder sb = new StringBuilder();
    if (portfolioContent == null) {
      writetoOut("The portfolio does not exist.");
    } else if (portfolioContent.size() == 0) {
      writetoOut("The portfolio " + portfolioName + " does not contain any stocks.");
    } else {
      sb.append("Portfolio Name: ");
      sb.append(portfolioName);
      sb.append("\n");
      for (String stockContent : portfolioContent) {
        sb.append(stockContent);
        sb.append("\n");
      }
      sb.deleteCharAt(sb.length() - 1);
      writetoOut(sb.toString());
    }
  }

  /**
   * <p>Method that formats the portfolio stock contents and displays it to the user. </p>
   *
   * <P>Portfolio Name: FUTURE PORTFOLIO
   * Ticker symbol: MSFT, Number of shares: 9, Price of Unit share: $106.47, Buy date: 26-11-2018,
   * Commission fee: 45.0 Ticker symbol: MSFT, Number of shares: 9, Price of Unit share: $110.19,
   * Buy date: 29-11-2018, Commission fee: 45.0 Ticker symbol: MSFT, Number of shares: 8, Price of
   * Unit share: $112.09, Buy date: 03-12-2018, Commission fee: 45.0
   *
   * Portfolio Name: MYPORTFOLIO Ticker symbol: GOOG, Number of shares: 2, Price of Unit share:
   * $922.9, Buy date: 09-08-2017, Commission fee: 30.5
   *
   * Portfolio Name: FUTURE PORTFLIOOO Ticker symbol: MSFT, Number of shares: 9, Price of Unit
   * share: $106.47, Buy date: 26-11-2018, Commission fee: 45.0 Ticker symbol: MSFT, Number of
   * shares: 9, Price of Unit share: $110.19, Buy date: 29-11-2018, Commission fee: 45.0 Ticker
   * symbol: MSFT, Number of shares: 8, Price of Unit share: $112.09, Buy date: 03-12-2018,
   * Commission fee: 45.0</P>
   *
   * @param portfoliosAndContents Map with key as portfolio name and value as corresponding list
   */
  public void displayAllPortfolios(Map<String, List<String>> portfoliosAndContents) {

    StringBuilder sb = new StringBuilder();
    for (Map.Entry<String, List<String>> entry : portfoliosAndContents.entrySet()) {
      sb.append("Portfolio Name: ");
      sb.append(entry.getKey());
      sb.append("\n");
      if (entry.getValue().size() == 0) {
        sb.append("Stocks yet to be added to ");
        sb.append(entry.getKey());
        sb.append("\n");
      } else {
        for (String stockContent : entry.getValue()) {
          sb.append(stockContent);
          sb.append("\n");
        }
        sb.append("\n");
      }

    }
    if (sb.length() == 0) {
      writetoOut("There are no portfolios to display.");
    } else {
      sb.deleteCharAt(sb.length() - 1);
      writetoOut(sb.toString());
    }

  }

  /**
   * A private helper method that helps to write to appendable object.
   *
   * @param message message to be written to appendable.
   * @throws IllegalStateException is thrown when writing to appendable fails.
   */
  private void writetoOut(String message) throws IllegalStateException {

    try {
      out.append(message);
    } catch (IOException e) {
      throw new IllegalStateException("Error: Unable to write the results\n");
    }

  }

}
