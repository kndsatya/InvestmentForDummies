import org.junit.Test;

import java.io.StringReader;

import controller.Controller;
import controller.ControllerInterface;
import datasource.DataSourceCreator;
import model.VirtualStockModel;
import model.VirtualStockModelInterface;
import view.IView;
import view.ViewImpl;

import static org.junit.Assert.assertEquals;


public class ControllerTest2UsingAlphaAPI {

  private IView view;
  private String commandString = "\n\nCommand Name                        -      Command"
          + " Description"
          + "\n"
          + "-------------                              --------------------\n"
          + "CREATE_PORTFOLIO                    -      Command that helps to create a portfolio.\n"
          + "DISPLAY_PORTFOLIO                   -      Command that displays the contents of a"
          + " portfolio.\n" + "BUY_STOCK                           -      Command to buy a stock in"
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
          + "portfolio contains.\n\nPlease enter any command from the above displayed list.\n\n";

  @Test
  public void testForWaitForRightInput() {
    VirtualStockModelInterface model = new VirtualStockModel(DataSourceCreator
            .getDataSource("ALPHAAPI", new StringReader("")));

    String input = "ADD_STOCK\n123\nHEALTH\n123\nGOOG\nADD_STOCK\nHEALTH\nMSFT\nADD_STOCK\nHEALTH\n"
            + "TSLA\nINVEST\n123\nHEALTH\na\n20000\na\nn\na\n33.33\n-2\n33.33\n33\na\n1230\nin\n"
            + "27-11-2018 13:10\nQ";
    Appendable out = new StringBuffer();
    view = new ViewImpl(out);
    ControllerInterface controller = new Controller(new StringReader(input), view);
    controller.run(model);
    assertEquals(commandString
            + "Please enter the portfolio name.\n"
            + "Invalid input. Portfolio name should contains only letters and spaces.\n"
            + "Please enter the portfolio name.\n"
            + "Please enter the ticker symbol\n"
            + "Invalid input. Ticker symbol should contain only letters and should have at least"
            + " one letter.\n"
            + "Please enter the ticker symbol\n"
            + "Stock: GOOG is added to the portfolio: HEALTH successfully\n"
            + commandString
            + "Please enter the portfolio name.\n"
            + "Please enter the ticker symbol\n"
            + "Stock: MSFT is added to the portfolio: HEALTH successfully\n"
            + commandString
            + "Please enter the portfolio name.\n"
            + "Please enter the ticker symbol\n"
            + "Stock: TSLA is added to the portfolio: HEALTH successfully\n"
            + commandString
            + "Please enter the portfolio name.\n"
            + "Invalid input. Portfolio name should contains only letters and spaces.\n"
            + "Please enter the portfolio name.\n"
            + "Please enter the amount in dollars\n"
            + "Invalid input. Amount should contain only non-negative numbers\n"
            + "Please enter the amount in dollars\n"
            + "Do you want to invest equal percent of amount for all stocks."
            + "If so, press 'Y' else press 'N'\n"
            + "Invalid input. Please enter 'Y' if you want to invest percent of amount for"
            + " all stocks, else enter 'N'\n"
            + "Please enter the percentage of amount you want to invest in stock MSFT\n"
            + "Invalid input.Percent should be between 0 and 100 inclusively.\n"
            + "Please enter the percentage of amount you want to invest in stock MSFT\n"
            + "Please enter the percentage of amount you want to invest in stock GOOG\n"
            + "Invalid input.Percent should be between 0 and 100 inclusively.\n"
            + "Please enter the percentage of amount you want to invest in stock GOOG\n"
            + "Please enter the percentage of amount you want to invest in stock TSLA\n"
            + "Please enter the commission fee\n"
            + "Invalid commissionFee. Commission Fee should contain only non-negative "
            + "numbers\n"
            + "Please enter the commission fee\n"
            + "Please enter date and time in the 24 hr format:dd-mm-yyyy hh:mm\n"
            + "you have entered an invalid date and time format\n"
            + "Please enter date and time in the 24 hr format:dd-mm-yyyy hh:mm\n"
            + "I am running the command. Please wait and bear with me............;)\n"
            + "Successfully invested in MSFT\n"
            + "Successfully invested in GOOG\n"
            + "Successfully invested in TSLA\n"
            + commandString
            + "[35mClosing the application..........\n", out.toString());
  }

  @Test
  public void testForWaitForRightInput2() {
    VirtualStockModelInterface model = new VirtualStockModel(DataSourceCreator
            .getDataSource("ALPHAAPI", new StringReader("")));

    String input = "APPLY_DOLLAR_COST_STRATEGY\n123\nHEALTH\na\n20000\na\n1\n12\nGOOG\nn\na\n100.00"
            + "\na\n123\n"
            + "27-11-2018\na\n27-11-2018\na\n1\nQ";
    Appendable out = new StringBuffer();
    view = new ViewImpl(out);
    ControllerInterface controller = new Controller(new StringReader(input), view);
    controller.run(model);
    assertEquals(commandString
            + "Please enter the portfolio name.\n"
            + "Invalid input. Portfolio name should contains only letters and spaces.\n"
            + "Please enter the portfolio name.\n"
            + "Please enter the amount in dollars\n"
            + "Invalid input. Amount should contain only non-negative numbers\n"
            + "Please enter the amount in dollars\n"
            + "Please enter the number of stocks you want to add into the"
            + " dollar cost strategy portfolio: HEALTH\n"
            + "Stock number should be a positive integer(i.e. >=1). Please enter again\n"
            + "Please enter the stock names you want to add to the portfolio: HEALTH line"
            + " wise.\n"
            + "Stock Name should contain only english alphabet with no spaces between "
            + "letters."
            + " Also it should be listed in the NYSE.Please enter the stock name again\n"
            + "Do you want to invest equal percent of amount for all stocks.If so, press"
            + " 'Y' else press 'N'\n"
            + "Please enter the percentage of amount you want to invest in stock GOOG\n"
            + "Invalid input.Percent should be between 0 and 100 inclusively.\n"
            + "Please enter the percentage of amount you want to invest in stock GOOG\n"
            + "Please enter the commission fee\n"
            + "Invalid commissionFee. Commission Fee should contain only non-negative"
            + " numbers\n"
            + "Please enter the commission fee\n"
            + "Please enter start date of investment in format:dd-mm-yyyy\n"
            + "Please enter an endDate in the format: dd-MM-yyyy\n"
            + "you have entered an invalid date format\n"
            + "Please enter an endDate in the format: dd-MM-yyyy\n"
            + "Please enter an investment interval in no.of days\n"
            + "Invalid input. investment interval should contain only integers greater"
            + " than 0\n"
            + "Please enter an investment interval in no.of days\n"
            + "I am running the command. Please wait and bear with me............;)\n"
            + "Investment Strategy applied successfully\n"
            + commandString
            + "[35mClosing the application..........\n", out.toString());
  }

  @Test
  public void testForQuitsInvest() {
    VirtualStockModelInterface model = new VirtualStockModel(DataSourceCreator
            .getDataSource("ALPHAAPI", new StringReader("")));

    String input = "ADD_STOCK\nHEALTH\nGOOG\nADD_STOCK\nHEALTH\nMSFT\nADD_STOCK\nHEALTH\n"
            + "TSLA\nINVEST\nq\nHEALTH\n20000\nn\n33.33\n33.33\n33\n1230\n"
            + "27-11-2018 13:10\nQ";
    Appendable out = new StringBuffer();
    view = new ViewImpl(out);
    ControllerInterface controller = new Controller(new StringReader(input), view);
    controller.run(model);
    assertEquals(commandString
            + "Please enter the portfolio name.\n"
            + "Please enter the ticker symbol\n"
            + "Stock: GOOG is added to the portfolio: HEALTH successfully\n"
            + commandString
            + "Please enter the portfolio name.\n"
            + "Please enter the ticker symbol\n"
            + "Stock: MSFT is added to the portfolio: HEALTH successfully\n"
            + commandString
            + "Please enter the portfolio name.\n"
            + "Please enter the ticker symbol\n"
            + "Stock: TSLA is added to the portfolio: HEALTH successfully\n"
            + commandString
            + "Please enter the portfolio name.\n"
            + "Quitting application........\n", out.toString());

    input = "ADD_STOCK\nHEALTH\nGOOG\nADD_STOCK\nHEALTH\nMSFT\nADD_STOCK\nHEALTH\n"
            + "TSLA\nINVEST\nHEALTH\nq\n20000\nn\n33.33\n33.33\n33\n1230\n"
            + "27-11-2018 13:10\nQ";

    model = new VirtualStockModel(DataSourceCreator
            .getDataSource("ALPHAAPI", new StringReader("")));
    out = new StringBuffer();
    view = new ViewImpl(out);
    controller = new Controller(new StringReader(input), view);
    controller.run(model);
    assertEquals(commandString
            + "Please enter the portfolio name.\n"
            + "Please enter the ticker symbol\n"
            + "Stock: GOOG is added to the portfolio: HEALTH successfully\n"
            + commandString
            + "Please enter the portfolio name.\n"
            + "Please enter the ticker symbol\n"
            + "Stock: MSFT is added to the portfolio: HEALTH successfully\n"
            + commandString
            + "Please enter the portfolio name.\n"
            + "Please enter the ticker symbol\n"
            + "Stock: TSLA is added to the portfolio: HEALTH successfully\n"
            + commandString
            + "Please enter the portfolio name.\n"
            + "Please enter the amount in dollars\n"
            + "Quitting application........\n", out.toString());


    input = "ADD_STOCK\nHEALTH\nGOOG\nADD_STOCK\nHEALTH\nMSFT\nADD_STOCK\nHEALTH\n"
            + "TSLA\nINVEST\nHEALTH\n20000\nq\nn\n33.33\n33.33\n33\n1230\n"
            + "27-11-2018 13:10\nQ";

    model = new VirtualStockModel(DataSourceCreator
            .getDataSource("ALPHAAPI", new StringReader("")));
    out = new StringBuffer();
    view = new ViewImpl(out);
    controller = new Controller(new StringReader(input), view);
    controller.run(model);
    assertEquals(commandString
            + "Please enter the portfolio name.\n"
            + "Please enter the ticker symbol\n"
            + "Stock: GOOG is added to the portfolio: HEALTH successfully\n"
            + commandString
            + "Please enter the portfolio name.\n"
            + "Please enter the ticker symbol\n"
            + "Stock: MSFT is added to the portfolio: HEALTH successfully\n"
            + commandString
            + "Please enter the portfolio name.\n"
            + "Please enter the ticker symbol\n"
            + "Stock: TSLA is added to the portfolio: HEALTH successfully\n"
            + commandString
            + "Please enter the portfolio name.\n"
            + "Please enter the amount in dollars\n"
            + "Do you want to invest equal percent of amount for all stocks.If so, press 'Y'"
            + " else press 'N'\n"
            + "Quitting application........\n", out.toString());


    input = "ADD_STOCK\nHEALTH\nGOOG\nADD_STOCK\nHEALTH\nMSFT\nADD_STOCK\nHEALTH\n"
            + "TSLA\nINVEST\nHEALTH\n20000\nn\nq\n33.33\n33.33\n33\n1230\n"
            + "27-11-2018 13:10\nQ";

    model = new VirtualStockModel(DataSourceCreator
            .getDataSource("ALPHAAPI", new StringReader("")));
    out = new StringBuffer();
    view = new ViewImpl(out);
    controller = new Controller(new StringReader(input), view);
    controller.run(model);
    assertEquals(commandString
            + "Please enter the portfolio name.\n"
            + "Please enter the ticker symbol\n"
            + "Stock: GOOG is added to the portfolio: HEALTH successfully\n"
            + commandString
            + "Please enter the portfolio name.\n"
            + "Please enter the ticker symbol\n"
            + "Stock: MSFT is added to the portfolio: HEALTH successfully\n"
            + commandString
            + "Please enter the portfolio name.\n"
            + "Please enter the ticker symbol\n"
            + "Stock: TSLA is added to the portfolio: HEALTH successfully\n"
            + commandString
            + "Please enter the portfolio name.\n"
            + "Please enter the amount in dollars\n"
            + "Do you want to invest equal percent of amount for all stocks.If so, press 'Y'"
            + " else press 'N'\n"
            + "Please enter the percentage of amount you want to invest in stock MSFT\n"
            + "Quitting application........\n", out.toString());


    input = "ADD_STOCK\nHEALTH\nGOOG\nADD_STOCK\nHEALTH\nMSFT\nADD_STOCK\nHEALTH\n"
            + "TSLA\nINVEST\nHEALTH\n20000\nn\n33.33\n33.33\n33\nq\n1230\n"
            + "27-11-2018 13:10\nQ";

    model = new VirtualStockModel(DataSourceCreator
            .getDataSource("ALPHAAPI", new StringReader("")));
    out = new StringBuffer();
    view = new ViewImpl(out);
    controller = new Controller(new StringReader(input), view);
    controller.run(model);
    assertEquals(commandString
            + "Please enter the portfolio name.\n"
            + "Please enter the ticker symbol\n"
            + "Stock: GOOG is added to the portfolio: HEALTH successfully\n"
            + commandString
            + "Please enter the portfolio name.\n"
            + "Please enter the ticker symbol\n"
            + "Stock: MSFT is added to the portfolio: HEALTH successfully\n"
            + commandString
            + "Please enter the portfolio name.\n"
            + "Please enter the ticker symbol\n"
            + "Stock: TSLA is added to the portfolio: HEALTH successfully\n"
            + commandString
            + "Please enter the portfolio name.\n"
            + "Please enter the amount in dollars\n"
            + "Do you want to invest equal percent of amount for all stocks.If so, press 'Y'"
            + " else press 'N'\n"
            + "Please enter the percentage of amount you want to invest in stock MSFT\n"
            + "Please enter the percentage of amount you want to invest in stock GOOG\n"
            + "Please enter the percentage of amount you want to invest in stock TSLA\n"
            + "Please enter the commission fee\n"
            + "Quitting application........\n", out.toString());


    input = "ADD_STOCK\nHEALTH\nGOOG\nADD_STOCK\nHEALTH\nMSFT\nADD_STOCK\nHEALTH\n"
            + "TSLA\nINVEST\nHEALTH\n20000\nn\n33.33\n33.33\n33\n1230\n"
            + "q\n27-11-2018 13:10\nQ";

    model = new VirtualStockModel(DataSourceCreator
            .getDataSource("ALPHAAPI", new StringReader("")));
    out = new StringBuffer();
    view = new ViewImpl(out);
    controller = new Controller(new StringReader(input), view);
    controller.run(model);
    assertEquals(commandString
            + "Please enter the portfolio name.\n"
            + "Please enter the ticker symbol\n"
            + "Stock: GOOG is added to the portfolio: HEALTH successfully\n"
            + commandString
            + "Please enter the portfolio name.\n"
            + "Please enter the ticker symbol\n"
            + "Stock: MSFT is added to the portfolio: HEALTH successfully\n"
            + commandString
            + "Please enter the portfolio name.\n"
            + "Please enter the ticker symbol\n"
            + "Stock: TSLA is added to the portfolio: HEALTH successfully\n"
            + commandString
            + "Please enter the portfolio name.\n"
            + "Please enter the amount in dollars\n"
            + "Do you want to invest equal percent of amount for all stocks.If so, press 'Y'"
            + " else press 'N'\n"
            + "Please enter the percentage of amount you want to invest in stock MSFT\n"
            + "Please enter the percentage of amount you want to invest in stock GOOG\n"
            + "Please enter the percentage of amount you want to invest in stock TSLA\n"
            + "Please enter the commission fee\n"
            + "Please enter date and time in the 24 hr format:dd-mm-yyyy hh:mm\n"
            + "Quitting application........\n", out.toString());
  }

  @Test
  public void testForQuitsofDollar_Cost() {
    VirtualStockModelInterface model = new VirtualStockModel(DataSourceCreator
            .getDataSource("ALPHAAPI", new StringReader("")));

    String input = "APPLY_DOLLAR_COST_STRATEGY\nq\nHEALTH\n20000\n1\nGOOG\nn\n100.00"
            + "\n123\n"
            + "27-11-2018\n27-11-2018\n1\nQ";
    Appendable out = new StringBuffer();
    view = new ViewImpl(out);
    ControllerInterface controller = new Controller(new StringReader(input), view);
    controller.run(model);
    assertEquals(commandString
            + "Please enter the portfolio name.\n"
            + "Quitting application........\n", out.toString());

    model = new VirtualStockModel(DataSourceCreator
            .getDataSource("ALPHAAPI", new StringReader("")));

    input = "APPLY_DOLLAR_COST_STRATEGY\nHEALTH\nq\n20000\n1\nGOOG\nn\n100.00"
            + "\n123\n"
            + "27-11-2018\n27-11-2018\n1\nQ";
    out = new StringBuffer();
    view = new ViewImpl(out);
    controller = new Controller(new StringReader(input), view);
    controller.run(model);
    assertEquals(commandString
            + "Please enter the portfolio name.\n"
            + "Please enter the amount in dollars\n"
            + "Quitting application........\n", out.toString());


    model = new VirtualStockModel(DataSourceCreator
            .getDataSource("ALPHAAPI", new StringReader("")));

    input = "APPLY_DOLLAR_COST_STRATEGY\nHEALTH\n20000\nq\n1\nGOOG\nn\n100.00"
            + "\n123\n"
            + "27-11-2018\n27-11-2018\n1\nQ";
    out = new StringBuffer();
    view = new ViewImpl(out);
    controller = new Controller(new StringReader(input), view);
    controller.run(model);
    assertEquals(commandString
            + "Please enter the portfolio name.\n"
            + "Please enter the amount in dollars\n"
            + "Please enter the number of stocks you want to add into"
            + " the dollar cost strategy portfolio: HEALTH\n"
            + "Quitting application........\n", out.toString());


    model = new VirtualStockModel(DataSourceCreator
            .getDataSource("ALPHAAPI", new StringReader("")));

    input = "APPLY_DOLLAR_COST_STRATEGY\nHEALTH\n20000\n1\nq\nGOOG\nn\n100.00"
            + "\n123\n"
            + "27-11-2018\n27-11-2018\n1\nQ";
    out = new StringBuffer();
    view = new ViewImpl(out);
    controller = new Controller(new StringReader(input), view);
    controller.run(model);
    assertEquals(commandString
            + "Please enter the portfolio name.\n"
            + "Please enter the amount in dollars\n"
            + "Please enter the number of stocks you want to add into"
            + " the dollar cost strategy portfolio: HEALTH\n"
            + "Please enter the stock names you want to add to the"
            + " portfolio: HEALTH line wise.\n"
            + "Quitting application........\n", out.toString());


    model = new VirtualStockModel(DataSourceCreator
            .getDataSource("ALPHAAPI", new StringReader("")));

    input = "APPLY_DOLLAR_COST_STRATEGY\nHEALTH\n20000\n1\nGOOG\nq\nn\n100.00"
            + "\n123\n"
            + "27-11-2018\n27-11-2018\n1\nQ";
    out = new StringBuffer();
    view = new ViewImpl(out);
    controller = new Controller(new StringReader(input), view);
    controller.run(model);
    assertEquals(commandString
            + "Please enter the portfolio name.\n"
            + "Please enter the amount in dollars\n"
            + "Please enter the number of stocks you want to add into"
            + " the dollar cost strategy portfolio: HEALTH\n"
            + "Please enter the stock names you want to add to the"
            + " portfolio: HEALTH line wise.\n"
            + "Do you want to invest equal percent of amount"
            + " for all stocks.If so, press 'Y' else press 'N'\n"
            + "Quitting application........\n", out.toString());


    model = new VirtualStockModel(DataSourceCreator
            .getDataSource("ALPHAAPI", new StringReader("")));

    input = "APPLY_DOLLAR_COST_STRATEGY\nHEALTH\n20000\n1\nGOOG\nn\nq\n100.00"
            + "\n123\n"
            + "27-11-2018\n27-11-2018\n1\nQ";
    out = new StringBuffer();
    view = new ViewImpl(out);
    controller = new Controller(new StringReader(input), view);
    controller.run(model);
    assertEquals(commandString
            + "Please enter the portfolio name.\n"
            + "Please enter the amount in dollars\n"
            + "Please enter the number of stocks you want to add into"
            + " the dollar cost strategy portfolio: HEALTH\n"
            + "Please enter the stock names you want to add to the"
            + " portfolio: HEALTH line wise.\n"
            + "Do you want to invest equal percent of amount"
            + " for all stocks.If so, press 'Y' else press 'N'\n"
            + "Please enter the percentage of amount you want to invest in stock GOOG\n"
            + "Quitting application........\n", out.toString());


    model = new VirtualStockModel(DataSourceCreator
            .getDataSource("ALPHAAPI", new StringReader("")));

    input = "APPLY_DOLLAR_COST_STRATEGY\nHEALTH\n20000\n1\nGOOG\nn\n100.00"
            + "\nq\n123\n"
            + "27-11-2018\n27-11-2018\n1\nQ";
    out = new StringBuffer();
    view = new ViewImpl(out);
    controller = new Controller(new StringReader(input), view);
    controller.run(model);
    assertEquals(commandString
            + "Please enter the portfolio name.\n"
            + "Please enter the amount in dollars\n"
            + "Please enter the number of stocks you want to add into"
            + " the dollar cost strategy portfolio: HEALTH\n"
            + "Please enter the stock names you want to add to the"
            + " portfolio: HEALTH line wise.\n"
            + "Do you want to invest equal percent of amount"
            + " for all stocks.If so, press 'Y' else press 'N'\n"
            + "Please enter the percentage of amount you want to invest in stock GOOG\n"
            + "Please enter the commission fee\n"
            + "Quitting application........\n", out.toString());


    model = new VirtualStockModel(DataSourceCreator
            .getDataSource("ALPHAAPI", new StringReader("")));

    input = "APPLY_DOLLAR_COST_STRATEGY\nHEALTH\n20000\n1\nGOOG\nn\n100.00"
            + "\n123\n"
            + "q\n27-11-2018\n27-11-2018\n1\nQ";
    out = new StringBuffer();
    view = new ViewImpl(out);
    controller = new Controller(new StringReader(input), view);
    controller.run(model);
    assertEquals(commandString
                    + "Please enter the portfolio name.\n"
                    + "Please enter the amount in dollars\n"
                    + "Please enter the number of stocks you want to add into"
                    + " the dollar cost strategy portfolio: HEALTH\n"
                    + "Please enter the stock names you want to add to the"
                    + " portfolio: HEALTH line wise.\n"
                    + "Do you want to invest equal percent of amount"
                    + " for all stocks.If so, press 'Y' else press 'N'\n"
                    + "Please enter the percentage of amount you want to invest in stock GOOG\n"
                    + "Please enter the commission fee\n"
                    + "Please enter start date of investment in format:dd-mm-yyyy\n"
                    + "Quitting application........\n", out.toString());


    model = new VirtualStockModel(DataSourceCreator
            .getDataSource("ALPHAAPI", new StringReader("")));

    input = "APPLY_DOLLAR_COST_STRATEGY\nHEALTH\n20000\n1\nGOOG\nn\n100.00"
            + "\n123\n"
            + "27-11-2018\nq\n27-11-2018\n1\nQ";
    out = new StringBuffer();
    view = new ViewImpl(out);
    controller = new Controller(new StringReader(input), view);
    controller.run(model);
    assertEquals(commandString
                    + "Please enter the portfolio name.\n"
                    + "Please enter the amount in dollars\n"
                    + "Please enter the number of stocks you want to add into"
                    + " the dollar cost strategy portfolio: HEALTH\n"
                    + "Please enter the stock names you want to add to the"
                    + " portfolio: HEALTH line wise.\n"
                    + "Do you want to invest equal percent of amount"
                    + " for all stocks.If so, press 'Y' else press 'N'\n"
                    + "Please enter the percentage of amount you want to invest in stock GOOG\n"
                    + "Please enter the commission fee\n"
                    + "Please enter start date of investment in format:dd-mm-yyyy\n"
                    + "Please enter an endDate in the format: dd-MM-yyyy\n"
                    + "Quitting application........\n", out.toString());


    model = new VirtualStockModel(DataSourceCreator
            .getDataSource("ALPHAAPI", new StringReader("")));

    input = "APPLY_DOLLAR_COST_STRATEGY\nHEALTH\n20000\n1\nGOOG\nn\n100.00"
            + "\n123\n"
            + "27-11-2018\n27-11-2018\nq\n1\nQ";
    out = new StringBuffer();
    view = new ViewImpl(out);
    controller = new Controller(new StringReader(input), view);
    controller.run(model);
    assertEquals(commandString
            + "Please enter the portfolio name.\n"
            + "Please enter the amount in dollars\n"
            + "Please enter the number of stocks you want to add into"
            + " the dollar cost strategy portfolio: HEALTH\n"
            + "Please enter the stock names you want to add to the"
            + " portfolio: HEALTH line wise.\n"
            + "Do you want to invest equal percent of amount"
            + " for all stocks.If so, press 'Y' else press 'N'\n"
            + "Please enter the percentage of amount you want to invest in stock GOOG\n"
            + "Please enter the commission fee\n"
            + "Please enter start date of investment in format:dd-mm-yyyy\n"
            + "Please enter an endDate in the format: dd-MM-yyyy\n"
            + "Please enter an investment interval in no.of days\n"
            + "Quitting application........\n", out.toString());

  }


}
