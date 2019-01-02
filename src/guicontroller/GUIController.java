package guicontroller;

import org.omg.CORBA.portable.ValueInputStream;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import model.VirtualStockModelInterface;
import view.IGUIView;

/**
 * Represents a GUI controller class that provides various methods that communicates with the GUI
 * View and model of virtual stock market in order to pass the data from one source to the other.
 */
public class GUIController {
  private final VirtualStockModelInterface model;
  private final IGUIView view;
  private List<String> errorMessages;


  /**
   * A constructor that constructs a GUI specific controller by taking in a model of type
   * VirtualStockModelInterface and a view of type IGUIView.
   *
   * @param m model of type VirtualStockModelInterface
   * @param v view of type IGUIView
   */
  public GUIController(VirtualStockModelInterface m, IGUIView v) {
    this.model = m;
    this.view = v;
    this.errorMessages = new ArrayList<>();
    configureButtonListener();
    try {
      view.showLoadingMessage();
      model.retrievePortfolios();
      model.retrieveStrategies();
      view.writeToAppendable("Data Loaded successfully");
    } catch (IllegalStateException|IllegalArgumentException e) {
      view.writeErrorMessage(e.getMessage());
      System.exit(0);
    }
  }

  /**
   * A private helper method that helps to configure button action listeners and call the view's
   * action listener method in order set the action listeners defined by this controller for the
   * buttons displayed by the view.
   */
  private void configureButtonListener() {
    Map<String, Runnable> buttonClickedMap = new HashMap<String, Runnable>();
    ButtonListener buttonListener = new ButtonListener();

    buttonClickedMap.put("CREATE_PORTFOLIO", new CreatePortfolio());
    buttonClickedMap.put("BUY_STOCK", new BuyStock());
    buttonClickedMap.put("GET_TOTAL_COST_BASIS", new TotalCostBasis());
    buttonClickedMap.put("GET_TOTAL_VALUE", new TotalValue());
    buttonClickedMap.put("DISPLAY_PORTFOLIO", new DisplayPortfolio());
    buttonClickedMap.put("DISPLAY_ALL_PORTFOLIOS", new DisplayAllPortfolios());
    buttonClickedMap.put("INVEST", new Invest());
    buttonClickedMap.put("APPLY_DOLLAR_COST_STRATEGY", new DollarCostStrategy());
    buttonClickedMap.put("CREATE_STRATEGY", new CreateStrategy());
    buttonClickedMap.put("ADD_STOCK", new AddStock());
    buttonClickedMap.put("APPLY_STRATEGY", new ApplyStrategy());
    buttonClickedMap.put("SAVE_STRATEGY", new SaveStrategy());
    buttonClickedMap.put("SAVE_PORTFOLIO", new SavePortfolio());
    buttonClickedMap.put("SAVE_SESSION", new SaveSession());
    buttonClickedMap.put("DISPLAY_STOCKS_OF_A_PORTFOLIO", new DisplayStocks());

    buttonListener.setButtonClickedActionMap(buttonClickedMap);
    this.view.addActionListener(buttonListener);
  }

  // THESE CLASSES ARE NESTED INSIDE THE CONTROLLER,
  // SO THAT THEY HAVE ACCESS TO THE VIEW

  /**
   * An inner class that implements Runnable interface to override it's only method run and use it
   * as the action listener for the Create Portfolio Button.
   */
  class CreatePortfolio implements Runnable {
    @Override
    public void run() {
      errorMessages = new ArrayList<>();
      errorMessages.add("Invalid Input");
      String[] data = view.readDataofPortfolio("Create Portfolio");

      if (data == null) {
        return;
      }

      boolean status = true;

      status &= isPortfolioNameValid(data[0]);

      if (!status) {
        view.writeErrorMessage(errorMessages.toArray(new String[0]));
        return;
      }

      try {
        model.createPortfolio(data[0]);
        view.writeToAppendable("Portfolio: " + data[0] + " created successfully!!!");
      } catch (IllegalArgumentException e) {
        view.writeErrorMessage(e.getMessage());
      }

    }
  }

  /**
   * An inner class that implements Runnable interface to override it's only method run and use it
   * as the action listener for the Buy Stock Button.
   */
  class BuyStock implements Runnable {

    @Override
    public void run() {
      errorMessages = new ArrayList<>();
      errorMessages.add("Invalid Input");
      String[] data = view.getDetailsToBuyStock(readTickerSymbols());

      if (data == null) {
        return;
      }

      boolean status = true;
      status &= isPortfolioNameValid(data[0]);
      status &= isTickerSymbolValid(data[1]);
      status &= isAmountValid(data[2]);
      status &= isDateTimeValid(data[3]);
      status &= isCommissionFeeValid(data[4]);

      if (!status) {
        view.writeErrorMessage(errorMessages.toArray(new String[0]));
        return;
      }
      view.showLoadingMessage();
      try {
        model.buySharesOfStock(data[1], data[0], Double.parseDouble(data[2]), data[3],
                Double.parseDouble(data[4]));
        view.writeToAppendable("Stock: " + data[1] + " is bought successfully");
      } catch (IllegalArgumentException e) {
        view.writeErrorMessage(e.getMessage());
      }

    }

  }

  /**
   * An inner class that implements Runnable interface to override it's only method run and use it
   * as the action listener for the Total Cost Basis Button.
   */
  class TotalCostBasis implements Runnable {

    @Override
    public void run() {

      errorMessages = new ArrayList<>();
      errorMessages.add("Invalid Input");

      String[] data = view.getTotalCostBasisData(model.getPortfolioNames());

      if (data == null) {
        return;
      }

      boolean status = true;

      status &= isPortfolioNameValid(data[0]);
      status &= isDateValid(data[1]);

      if (!status) {
        view.writeErrorMessage(errorMessages.toArray(new String[0]));
        return;
      }

      try {
        double totalCostBasis = model.getTotalCostBasis(data[0], data[1]);
        view.writeToAppendable("Total Cost Basis of portfolio: " + data[0] + " on " + data[1]
                + " is: $"
                + totalCostBasis);
      } catch (IllegalArgumentException e) {
        view.writeErrorMessage(e.getMessage());
      }

    }
  }


  /**
   * An inner class that implements Runnable interface to override it's only method run and use it
   * as the action listener for the Total Value Button.
   */
  class TotalValue implements Runnable {

    @Override
    public void run() {
      errorMessages = new ArrayList<>();
      errorMessages.add("Invalid Input");

      String[] data = view.getTotalValue(model.getPortfolioNames());

      if (data == null) {
        return;
      }

      boolean status = true;

      status &= isPortfolioNameValid(data[0]);
      status &= isDateValid(data[1]);

      if (!status) {
        view.writeErrorMessage(errorMessages.toArray(new String[0]));
        return;
      }

      try {
        view.showLoadingMessage();
        double totalValue = model.getTotalValue(data[0], data[1]);
        view.writeToAppendable("Total value of portfolio: " + data[0] + " on " + data[1] + " is: $"
                + totalValue);
      } catch (IllegalArgumentException e) {
        view.writeErrorMessage(e.getMessage());
      }

    }
  }


  /**
   * An inner class that implements Runnable interface to override it's only method run and use it
   * as the action listener for the Display Portfolio Button.
   */
  class DisplayPortfolio implements Runnable {
    @Override
    public void run() {

      errorMessages = new ArrayList<>();
      errorMessages.add("Invalid Input");
      String[] data = view.readDataofPortfolio("Display Portfolio", model.getPortfolioNames());

      if (data == null) {
        return;
      }

      boolean status = true;

      status &= isPortfolioNameValid(data[0]);

      if (!status) {
        view.writeErrorMessage(errorMessages.toArray(new String[0]));
        return;
      }

      List<String> portfolioContents = model.displayPortfolio(data[0]);
      view.displayPortfolio(portfolioContents, data[0]);
    }
  }


  /**
   * An inner class that implements Runnable interface to override it's only method run and use it
   * as the action listener for the Display All Portfolios Button.
   */
  class DisplayAllPortfolios implements Runnable {

    @Override
    public void run() {
      view.displayAllPortfolios(model.displayAllPortfolios());
    }

  }


  /**
   * An inner class that implements Runnable interface to override it's only method run and use it
   * as the action listener for the 'Invest' Button.
   */
  class Invest implements Runnable {

    @Override
    public void run() {

      errorMessages = new ArrayList<>();
      errorMessages.add("Invalid Input");
      String[] data = view.readDataofPortfolio("Invest", model.getPortfolioNames());

      if (data == null) {
        return;
      }

      boolean status = true;

      status &= isPortfolioNameValid(data[0]);

      String portfolioName = data[0];
      if (!status) {
        view.writeErrorMessage(errorMessages.toArray(new String[0]));
        return;
      }

      List<String> stocksNames = new ArrayList<>();
      try {
        stocksNames = model.getStocksOfAPortfolio(data[0]);
      } catch (IllegalArgumentException e) {
        view.writeErrorMessage("Portfolio: " + data[0] + " doesn't exist. Please create one and "
                + "add stocks before you invest");
        return;
      }

      if (stocksNames.size() == 0) {
        view.writeErrorMessage("There are no stocks in the portfolio: " + data[0]
                + " . Please add stocks" + "to the portfolio before you invest");
        return;
      }

      data = view.getInvestmentDetails(model.getStocksOfAPortfolio(data[0]));
      if (data == null) {
        return;
      }
      status = true;
      status &= isAmountValid(data[0]);
      status &= isCommissionFeeValid(data[1]);
      status &= isDateTimeValid(data[2]);
      status &= isPercentValid(stocksNames, data, 3);

      if (!status) {
        view.writeErrorMessage(errorMessages.toArray(new String[0]));
        return;
      }


      view.showLoadingMessage();
      try {
        String result = model.invest(portfolioName, getStockWeights(stocksNames, data, 3),
                Double.parseDouble(data[0]), Double.parseDouble(data[1]), data[2]);
        view.writeToAppendable(result);
      } catch (IllegalArgumentException e) {
        view.writeErrorMessage(e.getMessage());
      }

    }
  }


  /**
   * An inner class that implements Runnable interface to override it's only method run and use it
   * as the action listener for the 'APPLY_DOLLAR_COST_STRATEGY' Button.
   */
  class DollarCostStrategy implements Runnable {
    @Override
    public void run() {

      errorMessages = new ArrayList<>();
      errorMessages.add("Invalid Input");
      String[] data = view.readDataOfDollarCostStrategy("Portfolio Name");

      if (data == null) {
        return;
      }

      if (!isDataOfDollarCostStrategyValid(data)) {
        view.writeErrorMessage(errorMessages.toArray(new String[0]));
        return;
      }
      String portfolioName = data[0];
      String startDate = data[1];
      String endDate = data[2];
      int investmentInterval = Integer.parseInt(data[3]);
      double amount = Double.parseDouble(data[4]);
      double commissionFee = Double.parseDouble(data[5]);
      int numberOfStocks = Integer.parseInt(data[6]);
      List<String> stocksData;
      String[] percentages;

      while (true) {

        data = view.getStocksAndWeightsData(numberOfStocks, readTickerSymbols());

        if (data == null) {
          return;
        }

        stocksData = extractStockData(data);
        percentages = extractWeightsData(data);
        if (isPercentValid(stocksData, percentages, 0)) {
          break;
        } else {
          view.writeErrorMessage(errorMessages.toArray(new String[0]));
          errorMessages = new ArrayList<>();
        }
      }

      if (data == null) {
        return;
      }

      Map<String, Double> stocksAndWeight = getStockWeights(stocksData, percentages, 0);

      view.showLoadingMessage();
      try {
        model.dollarCostAveraging(portfolioName, stocksAndWeight, amount, investmentInterval,
                startDate, endDate, commissionFee);
        view.writeToAppendable("Dollar cost averaging strategy applied successfully on portfolio: "
                + portfolioName);
      } catch (IllegalArgumentException e) {
        view.writeErrorMessage(e.getMessage());
      }

    }
  }


  /**
   * An inner class that implements Runnable interface to override it's only method run and use it
   * as the action listener for the 'create strategy' Button.
   */
  class CreateStrategy implements Runnable {

    @Override
    public void run() {

      errorMessages = new ArrayList<>();
      errorMessages.add("Invalid Input");
      String[] data = view.readDataOfDollarCostStrategy("Strategy Name");
      if (data == null) {
        return;
      }
      if (!isDataOfCreateStrategyValid(data)) {
        view.writeErrorMessage(errorMessages.toArray(new String[0]));
        return;
      }
      String strategyName = data[0];
      String startDate = data[1];
      String endDate = data[2];
      int investmentInterval = Integer.parseInt(data[3]);
      double amount = Double.parseDouble(data[4]);
      double commissionFee = Double.parseDouble(data[5]);
      int numberOfStocks = Integer.parseInt(data[6]);
      List<String> stocksData;
      String[] percentages;

      while (true) {

        data = view.getStocksAndWeightsData(numberOfStocks, readTickerSymbols());

        if (data == null) {
          return;
        }

        stocksData = extractStockData(data);
        percentages = extractWeightsData(data);
        if (isPercentValid(stocksData, percentages, 0)) {
          break;
        } else {
          view.writeErrorMessage(errorMessages.toArray(new String[0]));
          errorMessages = new ArrayList<>();
        }
      }


      if (data == null) {
        return;
      }

      Map<String, Double> stocksAndWeight = getStockWeights(stocksData, percentages, 0);

      try {
        model.createStrategy(strategyName, stocksAndWeight, amount, investmentInterval,
                startDate, endDate, commissionFee);
        view.writeToAppendable("Strategy: " + strategyName + " created successfully");
      } catch (IllegalArgumentException e) {
        view.writeErrorMessage(e.getMessage());
      }

    }
  }


  /**
   * An inner class that implements Runnable interface to override it's only method run and use it
   * as the action listener for the 'ADD_STOCK' Button.
   */
  class AddStock implements Runnable {
    @Override
    public void run() {

      errorMessages = new ArrayList<>();
      errorMessages.add("Invalid Input");
      String[] data = view.readDataOfAddStock(readTickerSymbols());
      if (data == null) {
        return;
      }
      String portfolioName = data[0];
      String stock = data[1];

      boolean status = true;
      status &= isPortfolioNameValid(portfolioName);

      if (!status) {
        view.writeErrorMessage(errorMessages.toArray(new String[0]));
        return;
      }

      try {
        model.addStock(portfolioName, stock);
        view.writeToAppendable("Stock: " + stock + "added to the portfolio: " + portfolioName
                + " successfully");
      } catch (IllegalArgumentException e) {
        view.writeErrorMessage(e.getMessage());
      }

    }
  }


  /**
   * An inner class that implements Runnable interface to override it's only method run and use it
   * as the action listener for the APPLY_STRATEGY Button.
   */
  class ApplyStrategy implements Runnable {

    @Override
    public void run() {

      errorMessages = new ArrayList<>();
      errorMessages.add("Invalid Input");
      String[] data = view.readDataToApplyStrategy(model.getStrategyNames(),
              model.getPortfolioNames());

      if (data == null) {
        return;
      }
      String portfolioName = data[0];
      String strategy = data[1];

      boolean status = true;
      status &= isPortfolioNameValid(portfolioName);
      status &= isStrategyNameValid(strategy);

      if (!status) {
        view.writeErrorMessage(errorMessages.toArray(new String[0]));
        return;
      }

      try {
        view.showLoadingMessage();
        model.applyStrategy(strategy, portfolioName);
        view.writeToAppendable("Strategy: " + strategy + "applied to the portfolio: "
                + portfolioName
                + " successfully");
      } catch (IllegalArgumentException e) {
        view.writeErrorMessage(e.getMessage());
      }
    }
  }


  /**
   * An inner class that implements Runnable interface to override it's only method run and use it
   * as the action listener for the Save Strategy Button.
   */
  class SaveStrategy implements Runnable {
    @Override
    public void run() {

      errorMessages = new ArrayList<>();
      errorMessages.add("Invalid Input");
      String[] data = view.readStrategyToSave(model.getStrategyNames());

      if (data == null) {
        return;
      }

      String strategy = data[0];

      boolean status = true;
      status &= isStrategyNameValid(strategy);

      if (!status) {
        view.writeErrorMessage(errorMessages.toArray(new String[0]));
        return;
      }

      try {
        model.saveStrategy(strategy);
        view.writeToAppendable("Strategy: " + strategy + "saved " + "successfully");
      } catch (IllegalArgumentException | IllegalStateException e) {
        view.writeErrorMessage(e.getMessage());
      }
    }
  }


  /**
   * An inner class that implements Runnable interface to override it's only method run and use it
   * as the action listener for the Save Portfolio Button.
   */
  class SavePortfolio implements Runnable {
    @Override
    public void run() {
      errorMessages = new ArrayList<>();
      errorMessages.add("Invalid Input");
      String[] data = view.readPortfolioToSave(model.getPortfolioNames());

      if (data == null) {
        return;
      }

      String portfolioName = data[0];

      boolean status = true;
      status &= isPortfolioNameValid(portfolioName);

      if (!status) {
        view.writeErrorMessage(errorMessages.toArray(new String[0]));
        return;
      }

      try {
        model.savePortfolio(portfolioName);
        view.writeToAppendable("Portfolio: " + portfolioName + "saved " + "successfully");
      } catch (IllegalArgumentException | IllegalStateException e) {
        view.writeErrorMessage(e.getMessage());
      }
    }
  }


  /**
   * An inner class that implements Runnable interface to override it's only method run and use it
   * as the action listener for the Save Session Button.
   */
  class SaveSession implements Runnable {
    @Override
    public void run() {

      try {
        model.saveSession();
        view.writeToAppendable("Session Saved successfully");
      } catch (IllegalStateException e) {
        view.writeErrorMessage(e.getMessage());
      }

    }
  }


  /**
   * An inner class that implements Runnable interface to override it's only method run and use it
   * as the action listener for the display stocks Button.
   */
  class DisplayStocks implements Runnable {

    public void run() {

      String[] data = view.readDataofPortfolio("Display_Stocks_Of_A_Portfolio",
              model.getPortfolioNames());
      if (data == null) {
        return;
      }
      boolean status = isPortfolioNameValid(data[0]);

      if (!status) {
        view.writeErrorMessage(errorMessages.toArray(new String[0]));
        return;
      }

      try {
        List<String> stocks = model.getStocksOfAPortfolio(data[0]);
        view.displayStocksOfPortfolio(stocks, data[0]);
      } catch (IllegalArgumentException e) {
        view.writeErrorMessage(e.getMessage());
      }
    }
  }

  /**
   * A private helper method that provides the list of ticker symbols for all the stocks listed in
   * NYSE till date.
   *
   * @return list of ticker symbols for all the stocks listed in NYSE till date.
   */
  private String[] readTickerSymbols() {

    String filePath = "./tickersymbols.txt";
    File file = new File(filePath);
    Scanner sc;
    List<String> tickerSymbols = new ArrayList<>();
    try {
      sc = new Scanner(file);
      while (sc.hasNext()) {
        tickerSymbols.add(sc.nextLine());
      }
    } catch (FileNotFoundException e) {
      view.writeErrorMessage(" We are facing an issue reading ticker symbols data. "
              + "Please retry after sometime");
    }
    return tickerSymbols.toArray(new String[0]);
  }

  /**
   * A private helper method to check whether the portfolio name provided by the view as entered by
   * the user is valid or not.
   *
   * @param portfolioName represents a portfolio name.
   * @return true if the portfolio name is valid, else false.
   */
  private boolean isPortfolioNameValid(String portfolioName) {

    if (portfolioName == null || !portfolioName.matches("([\\s]*[a-zA-Z][\\s]*)+")) {
      errorMessages.add("Portfolio name should contain at least one letter and only letters.");
      return false;
    }

    return true;
  }


  /**
   * A private helper method to check whether the ticker symbol provided by the view as entered by
   * the user is valid or not.
   *
   * @param tickerSymbol represents a tickerSymbol.
   * @return true if the tickerSymbol is valid, else false.
   */
  private boolean isTickerSymbolValid(String tickerSymbol) {


    if (tickerSymbol == null || !tickerSymbol.matches("([\\s]*[a-zA-Z][\\s]*)+")) {

      errorMessages.add("Ticker Symbol should contain at least one letter.");
      return false;
    }

    return true;
  }

  /**
   * A private helper method to determine whether the amount value provided by the view as entered
   * by the user is valid or not.
   *
   * @param amount represents the amount entered by the user.
   * @return return true if the amount is valid, else false.
   */
  private boolean isAmountValid(String amount) {

    if (amount == null || !amount.matches("[0-9]+.*[0-9]*")) {
      errorMessages.add("Amount should contain only non-negative numbers.");
      return false;
    }
    return true;
  }

  /**
   * A private helper method to determine whether the commission fee provided by the view as entered
   * by the user is valid or not.
   *
   * @param commissionFee represents the commission fee.
   * @return true if the commission fee entered is valid, else false.
   */
  private boolean isCommissionFeeValid(String commissionFee) {

    if (commissionFee == null || !commissionFee.matches("[0-9]+.*[0-9]*")) {
      errorMessages.add("commissionFee should contain only non-negative"
              + " numbers.");
      return false;
    }
    return true;
  }

  /**
   * A private helper method to determine whether the date and time provided by the view as entered
   * by the user is valid or not.
   *
   * @param dateTime represents a date and time.
   * @return true if the date and time entered are valid, else false.
   */
  private boolean isDateTimeValid(String dateTime) {

    if (dateTime == null || !dateTime.matches("^(([012][0-9])|(3[01]))-([0][1-9]|1[012])"
            + "-\\d\\d\\d\\d[\\s]+"
            + "([0-1]?[0-9]|2?[0-3]):([0-5]\\d)$")) {
      errorMessages.add("Date and Time should be of format dd-MM-yyyy hh24:mm");
      return false;
    }

    return true;
  }

  /**
   * A private helper method to determine whether the date provided by the view as entered by the
   * user is valid or not.
   *
   * @param date represents a date.
   * @return true if the date entered is valid, else false.
   */
  private boolean isDateValid(String date) {
    if (date == null || !date.matches("^(([012][0-9])|(3[01]))-([0][1-9]|1[012])"
            + "-\\d\\d\\d\\d[\\s]*$")) {
      errorMessages.add("Date should be of format dd-MM-yyyy and should be a valid calendar date");
      return false;
    }

    return true;
  }


  /**
   * A private helper method that constructs a map with the stock names as keys and values as the
   * percentage of amount of investment.
   *
   * @param stockNames List of stock names.
   * @param data       array of percentages of investment as sent by the view.
   * @param index      represents the index in the data array at which the iteration starts.
   * @return a map with the stock names as keys and values as the percentage of amount of
   *         investment.
   */
  private Map<String, Double> getStockWeights(List<String> stockNames, String[] data, int index) {

    Map<String, Double> stocksAndPercent = new HashMap<>();
    for (int i = 0; i < stockNames.size(); i++) {

      stocksAndPercent.put(stockNames.get(i), Double.parseDouble(data[index++]));
    }

    return stocksAndPercent;
  }

  /**
   * A private helper method that helps to check if the percentages of amount a user want to invest
   * are valid or not.
   *
   * @param stockNames represents a list of stocks
   * @param data       represents an array of percentages sent by the view.
   * @param index      represents the index in the data array at which the iteration starts.
   * @return true if all the provided corresponding percentages are valid, else false is returned.
   */
  private boolean isPercentValid(List<String> stockNames, String[] data, int index) {

    boolean isPercentValid = true;

    for (int i = 0; i < stockNames.size(); i++, index++) {

      if (data[index].equals("")) {
        errorMessages.add("Percentage of investment for stock: " + stockNames.get(i)
                + " can't be empty");
        isPercentValid = false;
      } else if (Double.parseDouble(data[index]) < 0.00) {
        errorMessages.add("Percentage of investment for stock: " + stockNames.get(i)
                + " can't be negative");
        isPercentValid = false;
      }

    }

    return isPercentValid;

  }

  /**
   * A private helper method that helps to check if an investment interval is valid or not.
   *
   * @param investmentInterval represents an investment interval
   * @return true if the investment interval is valid, else false.
   */
  private boolean isInvestmentIntervalValid(String investmentInterval) {
    if (investmentInterval == null || !investmentInterval.matches("^(0*[1-9])[0-9]*")) {
      errorMessages.add("Investment interval should contain only integer numbers and should be "
              + "at least 1 day");
      return false;
    }
    return true;
  }

  /**
   * A private helper method that helps to check if the number of stocks value provided by the view
   * as entered by the user is valid or not.
   *
   * @param numberOfStocks represents the number of stocks a user want to invest in using a
   *                       strategy.
   * @return true if the value of number of stocks entered by the user is valid, else false.
   */
  private boolean isNumberofStocksValid(String numberOfStocks) {
    if (numberOfStocks == null || !numberOfStocks.matches("^(0*[1-9])[0-9]*")) {
      errorMessages.add("Number of Stocks should be an integer and should be greater than 0");
      return false;
    }
    return true;
  }

  /**
   * A private helper method that helps to determine if the strategy name provided by the view as
   * provided by the user is valid or not.
   *
   * @param strategyName name of the strategy
   * @return true if the strategy is valid, else return false.
   */
  private boolean isStrategyNameValid(String strategyName) {

    if (strategyName == null || !strategyName.matches("([\\s]*[a-zA-Z][\\s]*)+")) {
      errorMessages.add("Strategy name should contain at least one letter and only letters.");
      return false;
    }

    return true;
  }

  /**
   * A private helper method that helps to determine if the data of dollar cost strategy provided by
   * the view as entered by the user.
   *
   * @param data the data related to the dollar cost average strategy provided by the view.
   * @return return true if the data is valid, else false.
   */
  private boolean isDataOfDollarCostStrategyValid(String[] data) {

    boolean isDataValid = true;
    if (data == null) {
      return false;
    }

    isDataValid &= isPortfolioNameValid(data[0]);
    isDataValid &= isDateValid(data[1]);
    isDataValid &= isEndDateValid(data[2]);
    isDataValid &= isInvestmentIntervalValid(data[3]);
    isDataValid &= isAmountValid(data[4]);
    isDataValid &= isCommissionFeeValid(data[5]);
    isDataValid &= isNumberofStocksValid(data[6]);
    return isDataValid;
  }

  /**
   * A private helper method that helps to extract the List of stocks from the data provided by the
   * view as entered by the user.
   *
   * @param data the data provided by the view as entered by the user.
   * @return the list of stocks extracted.
   */
  private List<String> extractStockData(String[] data) {

    List<String> stockData = new ArrayList<>();

    for (int i = 0; i < data.length; i += 2) {
      stockData.add(data[i]);
    }

    return stockData;

  }

  /**
   * A private helper method to extract the the percentages of investment for stocks from the data
   * provided by the view as entered by the user.
   *
   * @param data represents the data provided by the view as entered by the user.
   * @return the array of percentages extracted.
   */
  private String[] extractWeightsData(String[] data) {

    String[] weightsData = new String[data.length / 2];

    int weightsDataIndex = 0;

    for (int i = 0; i < data.length; i += 2) {
      weightsData[weightsDataIndex++] = data[i + 1];
    }

    return weightsData;
  }


  /**
   * A private helper method that helps to validate the data provided by the view as entered by the
   * user in order to create the strategy.
   *
   * @param data represents the data provided by the view as entered by the user.
   * @return true if the data is valid,else false.
   */
  private boolean isDataOfCreateStrategyValid(String[] data) {

    boolean isDataValid = true;
    if (data == null) {
      return false;
    }
    isDataValid &= isStrategyNameValid(data[0]);
    isDataValid &= isDateValid(data[1]);
    isDataValid &= isEndDateValid(data[2]);
    isDataValid &= isInvestmentIntervalValid(data[3]);
    isDataValid &= isAmountValid(data[4]);
    isDataValid &= isCommissionFeeValid(data[5]);
    isDataValid &= isNumberofStocksValid(data[6]);
    return isDataValid;

  }


  /**
   * A private helper method that helps to determine if the end date provided by the view as entered
   * by the user as part of startegy creation is valid or not.
   *
   * @param date represents the end date
   * @return true if the end date is valid, else false is returned.
   */
  private boolean isEndDateValid(String date) {

    if (date.equals("")) {
      return true;
    }

    if (date == null || !date.matches("^(([012][0-9])|(3[01]))-([0][1-9]|1[012])"
            + "-\\d\\d\\d\\d[\\s]*$")) {
      errorMessages.add("End Date should be of format dd-MM-yyyy and should be a valid "
              + "calendar date");
      return false;
    }

    return true;
  }


}