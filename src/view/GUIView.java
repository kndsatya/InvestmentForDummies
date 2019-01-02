package view;

import java.util.List;
import java.util.Map;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.BoxLayout;


import guicontroller.ButtonListener;

public class GUIView extends JFrame implements IGUIView {

  private JLabel display;
  private JButton portfolioCreationButton;
  private JButton buyStockButton;
  private JButton getTotalCostBasis;
  private JButton getTotalValue;
  private JButton buttonToDisplayPortfolio;
  private JButton buttonToDisplayAllPortfolios;
  private JButton investmentButton;
  private JButton dollarCostStrategy;
  private JButton createStrategy;
  private JButton addStock;
  private JButton applyStrategy;
  private JButton saveStrategy;
  private JButton savePortfolio;
  private JButton saveSession;
  private JButton displayStockOfaPortfolio;
  private JPanel  mainPanel;


  /**
   * A constructor that constructs an object of GUIView which creates a Jframe, it's components, add
   * the components to the  frame and sets Action Command.
   */
  public GUIView() {

    super("Investments Simulator");

    setSize(1000, 500);
    setLocation(200, 200);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    this.setLayout(new FlowLayout());

    createButton();
    setActionCommand();
    addElementToFrame();
    mainPanel.setAutoscrolls(true);
    pack();

    setVisible(true);


  }


  @Override
  public void addActionListener(ButtonListener buttonListener) {

    portfolioCreationButton.addActionListener(buttonListener);
    buyStockButton.addActionListener(buttonListener);
    getTotalCostBasis.addActionListener(buttonListener);
    getTotalValue.addActionListener(buttonListener);
    buttonToDisplayPortfolio.addActionListener(buttonListener);
    buttonToDisplayAllPortfolios.addActionListener(buttonListener);
    investmentButton.addActionListener(buttonListener);
    dollarCostStrategy.addActionListener(buttonListener);
    createStrategy.addActionListener(buttonListener);
    addStock.addActionListener(buttonListener);
    applyStrategy.addActionListener(buttonListener);
    saveStrategy.addActionListener(buttonListener);
    savePortfolio.addActionListener(buttonListener);
    saveSession.addActionListener(buttonListener);
    displayStockOfaPortfolio.addActionListener(buttonListener);
  }

  @Override
  public String[] readDataofPortfolio(String title, List<String> portfolioNames) {

    JComboBox portfolioName = new JComboBox(portfolioNames.toArray(new String[0]));
    Object[] message = {"Portfolio Name", portfolioName};

    int option = JOptionPane.showOptionDialog(null, message,
            title,
            JOptionPane.OK_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            new String[]{"Submit"},
            "default");

    if (option == JOptionPane.OK_OPTION) {
      return new String[]{(String) portfolioName.getSelectedItem()};
    }
    return null;

  }

  @Override
  public String[] readDataofPortfolio(String title) {

    JTextField portfolioName = new JTextField();
    Object[] message = {"Portfolio Name", portfolioName};


    int option = JOptionPane.showOptionDialog(null, message,
            title,
            JOptionPane.OK_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            new String[]{"Submit"},
            "default");

    if (option == JOptionPane.OK_OPTION) {
      return new String[]{portfolioName.getText()};
    }
    return null;
  }


  @Override
  public void writeToAppendable(String message) {

    Object[] displayContent = {message};
    JOptionPane.showOptionDialog(null, displayContent,
            "Status",
            JOptionPane.OK_OPTION,
            JOptionPane.INFORMATION_MESSAGE,
            null,
            new String[]{"OK"},
            "default");
  }

  @Override
  public void writeErrorMessage(String[] messages) {
    String errorMessage = "";
    for (String s : messages) {
      errorMessage += ( s + "\n" );
    }
    Object[] displayContent = {errorMessage + "\n"};
    JOptionPane.showOptionDialog(null, displayContent,
            "Status",
            JOptionPane.OK_OPTION,
            JOptionPane.ERROR_MESSAGE,
            null,
            new String[]{"OK"},
            "default");
  }

  @Override
  public void writeErrorMessage(String message) {

    Object[] displayContent = {message};
    JOptionPane.showOptionDialog(null, displayContent,
            "Status",
            JOptionPane.OK_OPTION,
            JOptionPane.ERROR_MESSAGE,
            null,
            new String[]{"OK"},
            "default");
  }

  @Override
  public String[] getDetailsToBuyStock(String[] tickerSymbols) {

    JTextField portfolioName = new JTextField();
    JComboBox tickerSymbol = new JComboBox(tickerSymbols);
    JTextField amount = new JTextField();
    JTextField dateTime = new JTextField();
    JTextField commissionFee = new JTextField("0.00");

    Object[] message = {"Portfolio Name", portfolioName, "Ticker Symbol",
                        tickerSymbol, "Amount", amount, "DateTime(dd-MM-YYYY hh24:mm)", dateTime,
                        "Commission Fee", commissionFee};

    int option = JOptionPane.showOptionDialog(null, message,
            "Buy Stock",
            JOptionPane.OK_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            new String[]{"Submit"},
            "default");

    if (option == JOptionPane.OK_OPTION) {
      String[] inputData = {portfolioName.getText(), (String) tickerSymbol.getSelectedItem(),
              amount.getText(), dateTime.getText(), commissionFee.getText()};
      return inputData;
    }

    return null;
  }

  @Override
  public String[] getTotalCostBasisData(List<String> portfolioNames) {

    JComboBox portfolioName = new JComboBox(portfolioNames.toArray(new String[0]));
    JTextField date = new JTextField();

    Object[] message = {"Portfolio Name", portfolioName, "DATE(dd-MM-yyyy)", date};

    int option = JOptionPane.showOptionDialog(null, message,
            "GET TOTAL COST BASIS",
            JOptionPane.OK_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            new String[]{"Submit"},
            "default");

    if (option == JOptionPane.OK_OPTION) {
      String[] inputData = {(String) portfolioName.getSelectedItem(), date.getText()};
      return inputData;
    }

    return null;
  }

  @Override
  public String[] getTotalValue(List<String> portfolioNames) {

    JComboBox portfolioName = new JComboBox(portfolioNames.toArray(new String[0]));
    JTextField date = new JTextField();

    Object[] message = {"Portfolio Name", portfolioName, "DATE(dd-MM-yyyy)", date};

    int option = JOptionPane.showOptionDialog(null, message,
            "GET TOTAL VALUE",
            JOptionPane.OK_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            new String[]{"Submit"},
            "default");

    if (option == JOptionPane.OK_OPTION) {
      String[] inputData = {(String) portfolioName.getSelectedItem(), date.getText()};
      return inputData;
    }

    return null;

  }

  @Override
  public String[] getInvestmentDetails(List<String> stocks) {

    JTextField amount = new JTextField();
    JTextField dateTime = new JTextField();
    JTextField commissionFee = new JTextField("0.00");

    Object[] message = new Object[6 + stocks.size() * 2];
    message[0] = "Investment amount";
    message[1] = amount;
    message[2] = "Commission Fee";
    message[3] = commissionFee;
    message[4] = "Date Of Investment(Format: dd-MM-yyyy hh24:mm)";
    message[5] = dateTime;
    double equalPercentValue = ( 100.00 ) / stocks.size();
    int messageIndex = 6;

    for (int i = 0; i < stocks.size(); i++) {
      message[messageIndex] = "Percent of amount to invest in stock: " + stocks.get(i);
      message[messageIndex + 1] = new JTextField(String.valueOf(equalPercentValue));
      messageIndex += 2;
    }

    int option = JOptionPane.showOptionDialog(null, message,
            "Invest",
            JOptionPane.OK_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            new String[]{"Submit"},
            "default");

    if (option == JOptionPane.OK_OPTION) {
      String[] inputData = new String[3 + stocks.size()];
      inputData[0] = amount.getText();
      inputData[1] = commissionFee.getText();
      inputData[2] = dateTime.getText();
      int inputDataIndex = 3;
      for (int m = 7; m < message.length; m += 2) {
        inputData[inputDataIndex++] = ( (JTextField) message[m] ).getText();
      }
      return inputData;
    }
    return null;
  }

  @Override
  public String[] readDataOfDollarCostStrategy(String name) {


    JTextField portfolioName = new JTextField();
    JTextField startDate = new JTextField();
    JTextField endDate = new JTextField();
    JTextField investmentInterval = new JTextField();
    JTextField amount = new JTextField();
    JTextField commissionFee = new JTextField();
    JTextField numberOfStocks = new JTextField();

    Object[] message = {name, portfolioName, "Start-Date of strategy(dd-MM-yyyy)",
                        startDate, "End Date of strategy(dd-MM-yyyy)- Leave it blank if end date"
                        + " is unknown", endDate, "Investment Interval",
                        investmentInterval, "Amount", amount, "Commission Fee", commissionFee,
                        "Stocks count" + " for application of strategy", numberOfStocks};

    int option = JOptionPane.showOptionDialog(null, message,
            "APPLY_DOLLAR_COST_STRATEGY",
            JOptionPane.OK_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            new String[]{"Submit"},
            "default");

    if (option == JOptionPane.OK_OPTION) {
      String[] inputData = {portfolioName.getText(), startDate.getText(), endDate.getText(),
              investmentInterval.getText(), amount.getText(), commissionFee.getText(),
              numberOfStocks.getText()};

      return inputData;
    }
    return null;
  }

  @Override
  public String[] getStocksAndWeightsData(int stocksCount, String[] tickerSymbols) {

    Object[] message = new Object[stocksCount * 4];
    double percentage = 100.00 / stocksCount;
    int stockIndex = 1;
    for (int i = 0; i < message.length; i += 4) {

      message[i] = "STOCK: " + stockIndex;
      message[i + 1] = new JComboBox(tickerSymbols);
      message[i + 2] = "Percentage of amount to invest in stock: " + stockIndex++;
      message[i + 3] = new JTextField(String.valueOf(percentage));

    }


    int option = JOptionPane.showOptionDialog(null, message,
            "APPLY_DOLLAR_COST_STRATEGY",
            JOptionPane.OK_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            new String[]{"Submit"},
            "default");

    if (option == JOptionPane.OK_OPTION) {

      String[] inputData = new String[stocksCount * 2];

      int messageIndex = 0;
      for (int i = 0; i < stocksCount * 2; i += 2) {

        inputData[i] = (String) ( (JComboBox) message[messageIndex + 1] ).getSelectedItem();
        inputData[i + 1] = ( (JTextField) message[messageIndex + 3] ).getText();
        messageIndex += 4;

      }
      return inputData;
    }
    return null;
  }

  @Override
  public String[] readDataOfAddStock(String[] tickerSymbols) {

    JTextField portfolioName = new JTextField();

    JComboBox stock = new JComboBox(tickerSymbols);

    Object[] message = {"Portfolio Name", portfolioName, "Stock", stock};


    int option = JOptionPane.showOptionDialog(null, message,
            "ADD STOCK",
            JOptionPane.OK_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            new String[]{"Submit"},
            "default");

    if (option == JOptionPane.OK_OPTION) {
      return new String[]{portfolioName.getText(), (String) stock.getSelectedItem()};
    }

    return null;
  }

  @Override
  public void displayPortfolio(List<String> portfolioContent, String portfolioName) {
    StringBuilder sb = new StringBuilder();
    if (portfolioContent == null) {
      writeErrorMessage("The portfolio does not exist.");
    } else if (portfolioContent.size() == 0) {
      writeToAppendable("The portfolio " + portfolioName + " does not contain any stocks.");
    } else {
      sb.append("Portfolio Name: ");
      sb.append(portfolioName);
      sb.append("\n");
      for (String stockContent : portfolioContent) {
        sb.append(stockContent);
        sb.append("\n");
      }
      sb.deleteCharAt(sb.length() - 1);
      JTextArea textArea = new JTextArea(sb.toString());
      JScrollPane scrollPane = new JScrollPane(textArea);
      textArea.setLineWrap(true);
      textArea.setWrapStyleWord(true);
      scrollPane.setPreferredSize(new Dimension(700, 200));
      JOptionPane.showMessageDialog(null, scrollPane, "DISPLAY PORTFOLIO",
              JOptionPane.INFORMATION_MESSAGE);

    }
  }

  @Override
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
      writeToAppendable("There are no portfolios to display.");
    } else {
      sb.deleteCharAt(sb.length() - 1);
      JTextArea textArea = new JTextArea(sb.toString());
      JScrollPane scrollPane = new JScrollPane(textArea);
      textArea.setLineWrap(true);
      textArea.setWrapStyleWord(true);
      scrollPane.setPreferredSize(new Dimension(700, 200));
      JOptionPane.showMessageDialog(null, scrollPane, "DISPLAY ALL PORTFOLIOS",
              JOptionPane.INFORMATION_MESSAGE);
    }

  }

  @Override
  public String[] readDataToApplyStrategy(List<String> strategies, List<String> portfolios) {

    JComboBox portfolioName = new JComboBox(portfolios.toArray(new String[0]));
    JComboBox strategy = new JComboBox(strategies.toArray(new String[0]));

    Object[] message = {"Portfolio Name", portfolioName, "Strategy", strategy};

    int option = JOptionPane.showOptionDialog(null, message,
            "APPLY STRATEGY",
            JOptionPane.OK_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            new String[]{"Submit"},
            "default");

    if (option == JOptionPane.OK_OPTION) {
      String[] inputData = {(String) portfolioName.getSelectedItem(),
                            (String) strategy.getSelectedItem()};
      return inputData;
    }
    return null;
  }

  @Override
  public String[] readStrategyToSave(List<String> strategies) {

    JComboBox strategy = new JComboBox(strategies.toArray(new String[0]));

    Object[] message = {"Strategy Name", strategy};

    int option = JOptionPane.showOptionDialog(null, message,
            "SAVE STRATEGY",
            JOptionPane.OK_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            new String[]{"SAVE"},
            "default");

    if (option == JOptionPane.OK_OPTION) {
      String[] inputData = {(String) strategy.getSelectedItem()};
      return inputData;
    }
    return null;
  }

  @Override
  public String[] readPortfolioToSave(List<String> portfolios) {

    JComboBox portfolioName = new JComboBox(portfolios.toArray(new String[0]));

    Object[] message = {"Portfolio Name", portfolioName};

    int option = JOptionPane.showOptionDialog(null, message,
            "SAVE PORTFOLIO",
            JOptionPane.OK_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            new String[]{"SAVE"},
            "default");

    if (option == JOptionPane.OK_OPTION) {
      String[] inputData = {(String) portfolioName.getSelectedItem()};
      return inputData;
    }
    return null;
  }

  @Override
  public void displayStocksOfPortfolio(List<String> stocks, String portfolioName)
          throws IllegalStateException {

    StringBuilder stocksData = new StringBuilder();
    for (int i = 0; i < stocks.size(); i++) {
      stocksData.append(stocks.get(i)).append("\n");
    }

    if (stocksData.length() == 0) {
      writeToAppendable("There are no stocks in the portfolio: " + portfolioName);
      return;
    }

    JTextArea textArea = new JTextArea(stocksData.toString());
    JScrollPane scrollPane = new JScrollPane(textArea);
    textArea.setLineWrap(true);
    textArea.setWrapStyleWord(true);
    scrollPane.setPreferredSize(new Dimension(700, 200));
    JOptionPane.showMessageDialog(null, scrollPane, "DISPLAY STOCKS OF "
                    + "A PORTFOLIO",
            JOptionPane.INFORMATION_MESSAGE);

  }

  @Override
  public void showLoadingMessage(){

    JLabel label = new JLabel("Please note that it will take time to fetch the data once you"
            + " click ok. Please wait till then. We appreciate your patience");

    Object[] message = {label};

    int option = JOptionPane.showOptionDialog(null, message,
            "SAVE PORTFOLIO",
            JOptionPane.OK_OPTION,
            JOptionPane.INFORMATION_MESSAGE,
            null,
            new String[]{"OK"},
            "default");
  }

  /**
   * A private helper method that helps to create JFrame's button components.
   */
  private void createButton() {
    display = new JLabel("Please Select the Operation you want to perform\n");
    portfolioCreationButton = new JButton("Create Portfolio");
    buyStockButton = new JButton("Buy Stock");
    getTotalCostBasis = new JButton("Get Total Cost Basis");
    getTotalValue = new JButton("Get Total Value");
    buttonToDisplayPortfolio = new JButton("Display Portfolio");
    buttonToDisplayAllPortfolios = new JButton("Display All Portfolios");
    investmentButton = new JButton("Invest in a portfolio");
    dollarCostStrategy = new JButton("Apply Dollar Cost Averaging Strategy");
    createStrategy = new JButton("Create Strategy");
    addStock = new JButton("Add Stock");
    applyStrategy = new JButton("Apply Strategy");
    saveStrategy = new JButton("Save Strategy");
    savePortfolio = new JButton("Save Portfolio");
    saveSession = new JButton("Save Current Session");
    displayStockOfaPortfolio = new JButton("Display Stocks Of a Portfolio");
  }

  /**
   * A private helper method that helps to set Action command for a button.
   */
  private void setActionCommand() {
    portfolioCreationButton.setActionCommand("CREATE_PORTFOLIO");
    buyStockButton.setActionCommand("BUY_STOCK");
    getTotalCostBasis.setActionCommand("GET_TOTAL_COST_BASIS");
    getTotalValue.setActionCommand("GET_TOTAL_VALUE");
    buttonToDisplayPortfolio.setActionCommand("DISPLAY_PORTFOLIO");
    buttonToDisplayAllPortfolios.setActionCommand("DISPLAY_ALL_PORTFOLIOS");
    investmentButton.setActionCommand("INVEST");
    dollarCostStrategy.setActionCommand("APPLY_DOLLAR_COST_STRATEGY");
    createStrategy.setActionCommand("CREATE_STRATEGY");
    addStock.setActionCommand("ADD_STOCK");
    applyStrategy.setActionCommand("APPLY_STRATEGY");
    saveStrategy.setActionCommand("SAVE_STRATEGY");
    savePortfolio.setActionCommand("SAVE_PORTFOLIO");
    saveSession.setActionCommand("SAVE_SESSION");
    displayStockOfaPortfolio.setActionCommand("DISPLAY_STOCKS_OF_A_PORTFOLIO");
  }

  /**
   * A private helper method that helps to add a component to a Frame.
   */
  private void addElementToFrame() {
    mainPanel = new JPanel();
    add(mainPanel);
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
    mainPanel.add(display);
    mainPanel.add(portfolioCreationButton);
    mainPanel.add(buyStockButton);
    mainPanel.add(getTotalCostBasis);
    mainPanel.add(getTotalValue);
    mainPanel.add(buttonToDisplayPortfolio);
    mainPanel.add(buttonToDisplayAllPortfolios);
    mainPanel.add(investmentButton);
    mainPanel.add(dollarCostStrategy);
    mainPanel.add(createStrategy);
    mainPanel.add(createStrategy);
    mainPanel.add(addStock);
    mainPanel.add(applyStrategy);
    mainPanel.add(saveStrategy);
    mainPanel.add(savePortfolio);
    mainPanel.add(saveSession);
    mainPanel.add(displayStockOfaPortfolio);
  }
}