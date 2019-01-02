package controller.command;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import view.IView;

/**
 * This is the abstract class for all the command classes that contains the common methods for all
 * the command classes.
 */
public abstract class AbstractCommand implements CommandInterface {
  protected final IView view;
  protected final Scanner scanner;
  protected String portfolioName;
  protected String tickerSymbol;
  protected String date;
  protected String endDate;
  protected double amount;
  protected int investmentInterval;
  protected Map<String, Double> stockAndPercent;
  protected double commissionFee;
  protected boolean isPortfolioRead;
  protected boolean isTickerRead;
  protected boolean isDateRead;
  protected boolean isAmountRead;
  protected boolean shouldReturn;
  protected boolean isStockPercentRead;
  protected boolean isCommissionFeeRead;
  protected boolean isInputReadSuccessfully;
  protected boolean isEndDateRead;
  protected boolean isInvestmentIntervalRead;
  protected boolean areStocksRead;

  protected String strategyName;
  protected boolean isStrategyNameRead;

  /**
   * Constructs the object by taking in the Scanner and the appendable objects provided by the
   * controller.
   *
   * @param scanner Scanner object
   * @param view    represents view object
   */
  protected AbstractCommand(Scanner scanner, IView view) {
    this.scanner = scanner;
    this.view = view;
    stockAndPercent = new HashMap<>();
    isPortfolioRead = isTickerRead = isDateRead = isAmountRead = shouldReturn = false;

    isStrategyNameRead = false;

    isStockPercentRead = isCommissionFeeRead = isInputReadSuccessfully
            = isEndDateRead = isInvestmentIntervalRead = areStocksRead = false;
  }

  /**
   * A helper method that invokes a method in view and asks view to display the message.
   *
   * @param message the string to be displayed by the view.
   * @throws IllegalStateException when view is unable to write to Appendable object.
   */
  protected void writeToOut(String message) {

    view.writeToAppendable(message);

  }

  /**
   * A helper method to read from the readable object. This method reads the portfolio name from the
   * readable object.
   */
  protected void readPortfolio() {

    writeToOut("Please enter the portfolio name.");
    String portfolio = scanner.nextLine();
    checkForQuit(portfolio);
    if (shouldReturn) {
      return;
    }
    if (portfolio.matches("([\\s]*[a-zA-Z][\\s]*)+")) {
      this.portfolioName = portfolio;
      isPortfolioRead = true;
    } else {
      writeToOut("Invalid input. Portfolio name should contains only letters and spaces.");
    }

  }


  protected void readStrategyName() {

    writeToOut("Please enter the strategy name.");
    String strategyName = scanner.nextLine();
    checkForQuit(strategyName);
    if (shouldReturn) {
      return;
    }
    if (strategyName.matches("([\\s]*[a-zA-Z][\\s]*)+")) {
      this.strategyName = strategyName;
      isStrategyNameRead = true;
    } else {
      writeToOut("Invalid input. Strategy name should contains only letters and spaces.");
    }

  }

  /**
   * A helper method to check if the input is 'q' or 'Q' and if so ends the application.
   *
   * @param message the input for which quit is checked.
   */
  protected void checkForQuit(String message) {
    if (message.equals("q") || message.equals("Q")
            || message.equalsIgnoreCase("QUIT")) {
      writeToOut("Quitting application........");
      shouldReturn = true;
    }

  }

  /**
   * A helper method to read ticker symbol from the readable object.
   */
  protected void readTicker() {
    writeToOut("Please enter the ticker symbol");
    String ticker = scanner.nextLine();

    checkForQuit(ticker);

    if (shouldReturn) {
      return;
    }

    if (ticker.matches("[\\s]*[a-zA-Z]+[\\s]*")) {
      this.tickerSymbol = ticker;
      isTickerRead = true;
    } else {
      writeToOut("Invalid input. Ticker symbol should contain only letters and should"
              + " have at least one letter.");
    }
  }

  /**
   * A helper method to read the date from the readable object.
   *
   * @param message displays the message so that user can enter the date in required format by
   *                reading this message string.
   */
  protected void readDate(String message) {
    writeToOut(message);
    String date = scanner.nextLine();
    date = date.trim();
    checkForQuit(date);
    if (shouldReturn) {
      return;
    }
    String dateRegex = "^(([012][0-9])|(3[01]))-([0][1-9]|1[012])-\\d\\d\\d\\d[\\s]*$";
    if (date.matches(dateRegex)) {
      this.date = date;
      isDateRead = true;
    } else {
      writeToOut("you have entered an invalid date format.");
    }
  }

  /**
   * A helper method to read the date and time from the readable object.
   */
  protected void readDateTime() {
    writeToOut("Please enter date and time in the 24 hr format:dd-mm-yyyy hh:mm");
    String date = scanner.nextLine();
    date = date.trim();
    checkForQuit(date);
    if (shouldReturn) {
      return;
    }
    String dateRegex = "^(([012][0-9])|(3[01]))-([0][1-9]|1[012])-\\d\\d\\d\\d[\\s]+"
            + "([0-1]?[0-9]|2?[0-3]):([0-5]\\d)$";
    if (date.matches(dateRegex)) {
      this.date = date;
      isDateRead = true;
    } else {
      writeToOut("you have entered an invalid date and time format");
    }
  }


  /**
   * A helper method to read the amount from the readable object.
   */
  protected void readAmount() {
    writeToOut("Please enter the amount in dollars");
    String amount = scanner.nextLine();
    amount = amount.trim();
    checkForQuit(amount);
    if (shouldReturn) {
      return;
    }
    if (amount.matches("[0-9]+.*[0-9]*")) {
      this.amount = Double.parseDouble(amount);
      isAmountRead = true;
    } else {
      writeToOut("Invalid input. Amount should contain only non-negative numbers");
    }
  }

  /**
   * A helper method that reads the percentage of amount a user wants to use to invest in a
   * particular stock.
   * @param stocksInAPortfolio List of stocks a portfolio contains.
   */
  protected void readStockPercent(List<String> stocksInAPortfolio) {

    int index = 0;
    double totalPercent = 0.00;

    view.writeToAppendable("Do you want to invest equal percent of amount for all stocks."
            + "If so, press 'Y' else press 'N'");

    while (true) {
      String input = scanner.nextLine();
      checkForQuit(input);

      if (shouldReturn) {
        return;
      }

      if (input.matches("[\\s]*[YNyn][\\s]*")) {
        input = input.trim().toUpperCase();
        if (input.equals("Y")) {
          readEqualPercentages(stocksInAPortfolio);
          return;
        }
        break;
      } else {
        view.writeToAppendable("Invalid input. Please enter 'Y' if you want to invest "
                + "percent of amount for all stocks, else enter 'N'");
      }
    }

    while (index <= stocksInAPortfolio.size() - 1) {
      view.writeToAppendable("Please enter the percentage of amount you want to invest in stock "
              + stocksInAPortfolio.get(index));
      String percent = scanner.nextLine();
      percent = percent.trim();
      checkForQuit(percent);
      if (shouldReturn) {
        return;
      }
      if (percent.matches("(([0-9]|[0-9][0-9])[.]+[0-9]*)")
              || percent.matches("([0-9]|[0-9][0-9]|100)")
              || percent.matches("100|100.|100.[0]*")) {

        double percentage = Double.parseDouble(percent);

        if (totalPercent + percentage > 100) {
          view.writeToAppendable("The total percentage of investment amount exceeds 100, "
                  + "Please enter such that total percentage is less than or equal to 100");
          continue;
        }
        stockAndPercent.put(stocksInAPortfolio.get(index), percentage);
        totalPercent += percentage;
        index++;
      } else {
        writeToOut("Invalid input.Percent should be between 0 and 100 inclusively.");
      }
    }
    isStockPercentRead = true;
  }

  /**
   *  A helper method to read the commission fee entered by the user.
   */
  protected void readCommissionFee() {

    writeToOut("Please enter the commission fee");
    String commissionFee = scanner.nextLine();
    commissionFee = commissionFee.trim();
    checkForQuit(commissionFee);
    if (shouldReturn) {
      return;
    }
    if (commissionFee.matches("[0-9]+.*[0-9]*")) {
      this.commissionFee = Double.parseDouble(commissionFee);
      isCommissionFeeRead = true;
    } else {
      writeToOut("Invalid commissionFee. Commission Fee should contain only non-negative numbers");
    }

  }

  /**
   * A helper method that asks the user the number of stocks he want to add to a dollar cost
   * portfolio and then read each of those stock's ticker symbols one by one.
   * @return list of ticker symbols read.
   */
  protected List<String> readStocks() {

    int stockCount = 0;
    List<String> stockList = new ArrayList<>();
    writeToOut("Please enter the number of stocks you want to add to the strategy.");

    while (true) {

      String numberOfStocks = scanner.nextLine();
      checkForQuit(numberOfStocks);
      if (shouldReturn) {
        return new ArrayList<>();
      }
      if (numberOfStocks.matches("^(0*[1-9])[0-9]*")) {
        stockCount = Integer.parseInt(numberOfStocks);
        break;
      } else {
        writeToOut("Stock number should be a positive integer(i.e. >=1). Please enter again");
      }
    }

    int counter = 1;

    view.writeToAppendable("Please enter the stock names you want to add to the strategy.");

    while (counter <= stockCount) {

      String stockName = scanner.nextLine();
      checkForQuit(stockName);
      if (shouldReturn) {
        return new ArrayList<>();
      }
      if (stockName.matches("[\\s]*[a-zA-Z]+[\\s]*") && isTickerSymbolValid(stockName)) {
        stockList.add(stockName);
        counter++;
      } else {
        writeToOut("Stock Name should contain only english alphabet with no spaces between"
                + " letters. Also it should be listed in the NYSE."
                + "Please enter the stock name again");
      }
    }
    areStocksRead = true;
    return stockList;
  }


  /**
   * A helper method that check if the ticker symbol(stock) entered by the user is listed in NYSE
   * or not.
   *
   * @param tickerSymbol represents a stock's unique symbol.
   * @return true if the ticker symbol is listed in the NYSE, else return false.
   */
  protected boolean isTickerSymbolValid(String tickerSymbol) {

    String filePath = "./tickersymbols.txt";
    File file = new File(filePath);
    Scanner sc;
    StringBuilder sb = new StringBuilder();
    try {
      sc = new Scanner(file);
      while (sc.hasNext()) {
        if (tickerSymbol.equalsIgnoreCase(sc.next())) {
          return true;
        }
      }
    } catch (FileNotFoundException e) {
      writeToOut("Sorry tickersymbols file not out.");
    }

    return false;
  }

  /**
   * A helper method that reads the end date for a dollar cost strategy.
   */
  protected void readEndDate() {
    writeToOut("Please enter an endDate in the format: dd-MM-yyyy. If you don't want to provide"
            + " an end date, just press enter button on the key board.");
    String date = scanner.nextLine();
    date = date.trim();
    checkForQuit(date);
    if (shouldReturn) {
      return;
    }
    String dateRegex = "^(([012][0-9])|(3[01]))-([0][1-9]|1[012])-\\d\\d\\d\\d[\\s]*$";
    if (date.matches(dateRegex) || date.matches("")) {
      this.endDate = date;
      isEndDateRead = true;
    } else {
      writeToOut("you have entered an invalid date format");
    }
  }

  /**
   * A helper method that reads the investment interval when dollar cost average strategy is
   * applied on portfolio.
   */
  protected void readInvestmentInterval() {

    writeToOut("Please enter an investment interval in no.of days");
    String investmentInterval = scanner.nextLine();
    investmentInterval = investmentInterval.trim();
    checkForQuit(investmentInterval);
    if (shouldReturn) {
      return;
    }
    if (investmentInterval.matches("^(0*[1-9])[0-9]*")) {
      this.investmentInterval = Integer.parseInt(investmentInterval);
      isInvestmentIntervalRead = true;
    } else {
      writeToOut("Invalid input. investment interval should contain only integers greater than 0");
    }
  }

  /**
   * A helper method that will calculate equal percentages when user wants to assign equal percent
   * of amount for all stocks present in a portfolio.
   * @param stocksInAPortfolio represents a list of stocks(i.e. ticker symbols) present in a
   *                           portfolio.
   */
  protected void readEqualPercentages(List<String> stocksInAPortfolio) {

    int counter = 0;
    double percent = 100.00 / stocksInAPortfolio.size();
    while (counter <= stocksInAPortfolio.size() - 1) {
      stockAndPercent.put(stocksInAPortfolio.get(counter), percent);
      counter++;
    }
    isStockPercentRead = true;
  }
}
