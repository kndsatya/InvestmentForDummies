import org.junit.Test;

import java.io.StringReader;

import controller.Controller;
import controller.ControllerInterface;
import datasource.DataSourceCreator;
import datasource.DataSourceInterface;
import model.VirtualStockModel;
import model.VirtualStockModelInterface;
import view.IView;
import view.ViewImpl;

import static org.junit.Assert.assertEquals;

import static org.junit.Assert.fail;

public class ControllerTestUsingALPHAAPI {

  @Test
  public void testForController() {


    IView view = new ViewImpl(new StringBuffer());
    String commandString = "\n\nCommand Name                        -      Command"
            + " Description"
            + "\n"
            + "-------------                              --------------------\n"
            + "CREATE_PORTFOLIO                    -      Command that helps to create a portfolio."
            + "\n"
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
            + "portfolio contains.\n\nPlease enter any command from the above displayed list.\n\n";
    Appendable out;
    ControllerInterface controller;
    /**Test for object creation**/
    try {
      out = new StringBuffer("abc\n");
      view = new ViewImpl(out);
      controller = new Controller(null, view);
      fail("Above line should have thrown exception");
    } catch (IllegalArgumentException e) {
      //DO Nothing
    }

    try {
      controller = new Controller(new StringReader("abc\n"),
              null);
      fail("Above line should have thrown exception");
    } catch (IllegalArgumentException e) {
      //Do Nothing.
    }


    try {
      controller = new Controller(null, null);
      fail("Above line should have thrown exception");
    } catch (IllegalArgumentException e) {
      //Do Nothing.
    }

    controller = new Controller(new StringReader("abcq\n"),
            view);

    /*Test For Model Null.*/
    view = new ViewImpl(new StringBuffer("abc\n"));
    controller = new Controller(new StringReader("q"), view);
    try {
      controller.run(null);
      fail("Above line should have thrown exception");
    } catch (IllegalArgumentException e) {
      //Do Nothing
    }


    /*Test For readable contains null.*/
    String s = "x\n" + null + "q";
    view = new ViewImpl(new StringBuffer("abc\n"));
    controller = new Controller(new StringReader(s), view);
    DataSourceInterface datasource = DataSourceCreator
            .getDataSource("ALPHAAPI", new StringReader("q"));
    VirtualStockModelInterface model = new VirtualStockModel(datasource);
    try {
      controller.run(model);
      fail("Above line should have thrown exception");
    } catch (IllegalStateException e) {
      //Do Nothing
    }


    /*Test For wait for input*/
    model = new VirtualStockModel(datasource);
    String input = "x\ny\nz\nCREATE_PORTFOLIO\n123\nmy_port\nMY PORTFOLIO\na\nb\nDISPLAY_PORTFOLIO"
            + "\n34\n1\nmy portfolio\na\n\b\nBUY_STOCK\n123\nGOOG\n1\n2\nmy port\n12\na1\n"
            + "22-03-2018 00:01\n-2\n234.\nx\nGET_TOTAL_COST_BASIS\n1\nGOOG\na\n23-03-2018\n"
            + "a\nb\nGET_TOTAL_VALUE\n42\nGOOG\na\n23-03-2018\nCREATE_PORTFOLIO\nsecond portfolio\n"
            + "DISPLAY_ALL_PORTFOLIOS\nq";


    out = new StringBuffer();
    view = new ViewImpl(out);
    controller = new Controller(new StringReader(input), view);
    controller.run(model);
    assertEquals(commandString
            + "[35mAn invalid command is provided\n"
            + commandString
            + "[35mAn invalid command is provided\n"
            + commandString
            + "[35mAn invalid command is provided\n"
            + commandString
            + "Please enter the portfolio name.\n"
            + "Invalid input. Portfolio name should contains only letters and spaces.\n"
            + "Please enter the portfolio name.\n"
            + "Invalid input. Portfolio name should contains only letters and spaces.\n"
            + "Please enter the portfolio name.\n"
            + "Portfolio: MY PORTFOLIO is created successfully\n"
            + commandString
            + "[35mAn invalid command is provided\n"
            + commandString
            + "[35mAn invalid command is provided\n"
            + commandString
            + "Please enter the portfolio name.\n"
            + "Invalid input. Portfolio name should contains only letters and spaces.\n"
            + "Please enter the portfolio name.\n"
            + "Invalid input. Portfolio name should contains only letters and spaces.\n"
            + "Please enter the portfolio name.\n"
            + "Portfolio Name: MY PORTFOLIO\n"
            + "StockTickerSymbol     NumberofShares         PriceofUnitShare      BuyDate"
            + "         Commission Fee\n"
            + commandString
            + "[35mAn invalid command is provided\n"
            + commandString
            + "[35mAn invalid command is provided\n"
            + commandString
            + "Please enter the ticker symbol\n"
            + "Invalid input. Ticker symbol should contain only letters and should have at least"
            + " one letter.\n"
            + "Please enter the ticker symbol\n"
            + "Please enter the portfolio name.\n"
            + "Invalid input. Portfolio name should contains only letters and spaces.\n"
            + "Please enter the portfolio name.\n"
            + "Invalid input. Portfolio name should contains only letters and spaces.\n"
            + "Please enter the portfolio name.\n"
            + "Please enter date and time in the 24 hr format:dd-mm-yyyy hh:mm\n"
            + "you have entered an invalid date and time format\n"
            + "Please enter date and time in the 24 hr format:dd-mm-yyyy hh:mm\n"
            + "you have entered an invalid date and time format\n"
            + "Please enter date and time in the 24 hr format:dd-mm-yyyy hh:mm\n"
            + "Please enter the amount in dollars\n"
            + "Invalid input. Amount should contain only non-negative numbers\n"
            + "Please enter the amount in dollars\n"
            + "Please enter the commission fee\n"
            + "Invalid commissionFee. Commission Fee should contain only non-negative numbers\n"
            + "Please enter the commission fee\n"
            + "Invalid commissionFee. Commission Fee should contain only non-negative numbers\n"
            + "Please enter the commission fee\n"
            + "I am running the command. Please wait and bear with me............;)\n"
            + "Can't buy shares after/before business hours.\n"
            + commandString
            + "[35mAn invalid command is provided\n"
            + commandString
            + "[35mAn invalid command is provided\n"
            + commandString
            + "[35mAn invalid command is provided\n"
            + commandString
            + "[35mAn invalid command is provided\n"
            + commandString
            + "[35mAn invalid command is provided\n"
            + commandString
            + "Please enter the portfolio name.\n"
            + "Invalid input. Portfolio name should contains only letters and spaces.\n"
            + "Please enter the portfolio name.\n"
            + "Please enter date in format:dd-mm-yyyy\n"
            + "you have entered an invalid date format.\n"
            + "Please enter date in format:dd-mm-yyyy\n"
            + "Portfolio: GOOG doesn't exist\n"
            + commandString
            + "Please enter the portfolio name.\n"
            + "Portfolio: SECOND PORTFOLIO is created successfully\n"
            + commandString
            + "Portfolio Name : MY PORTFOLIO\n"
            + "StockTickerSymbol     NumberofShares         PriceofUnitShare      BuyDate"
            + "         Commission Fee\n"
            + "Portfolio Name : SECOND PORTFOLIO\n"
            + "StockTickerSymbol     NumberofShares         PriceofUnitShare      BuyDate"
            + "         Commission Fee\n"
            + commandString
            + "[35mClosing the application..........\n", out.toString());

    /*Test construct portfolio command*/

    model = new VirtualStockModel(datasource);

    input = "CREATE_PORTFOLIO\n" + "" + "\nq";
    out = new StringBuffer();
    view = new ViewImpl(out);
    controller = new Controller(new StringReader(input), view);
    controller.run(model);
    assertEquals(commandString
            + "Please enter the portfolio name.\n"
            + "Invalid input. Portfolio name should contains only letters and spaces.\n"
            + "Please enter the portfolio name.\n"
            + "Quitting application........\n", out.toString());
    out = new StringBuffer();
    view = new ViewImpl(out);
    input = "CREATE_PORTFOLIO\n" + "HEALTH PORTFOLIO" + "\nq";
    controller = new Controller(new StringReader(input), view);
    controller.run(model);
    assertEquals(commandString
            + "Please enter the portfolio name.\n"
            + "Portfolio: HEALTH PORTFOLIO is created successfully\n"
            + commandString
            + "[35mClosing the application..........\n", out.toString());

    /*TestForBuyshares*/
    model = new VirtualStockModel(datasource);

    input = "BUY_STOCK\n" + "GOOG\n" + "HEALTH\n" + "22-03-2018 13:30\n" + "2345.\n" + "q";
    out = new StringBuffer();
    view = new ViewImpl(out);
    controller = new Controller(new StringReader(input), view);
    controller.run(model);
    assertEquals(commandString
            + "Please enter the ticker symbol\n"
            + "Please enter the portfolio name.\n"
            + "Please enter date and time in the 24 hr format:dd-mm-yyyy hh:mm\n"
            + "Please enter the amount in dollars\n"
            + "Please enter the commission fee\n"
            + "Quitting application........\n", out.toString());

    input = "BUY_STOCK\n" + "GOOG\n" + "HEALTH\n" + "22-03-2018 01:30\n" + "2345.\n0.00\n" + "q";
    out = new StringBuffer();
    view = new ViewImpl(out);
    controller = new Controller(new StringReader(input), view);
    controller.run(model);
    assertEquals(commandString
            + "Please enter the ticker symbol\n"
            + "Please enter the portfolio name.\n"
            + "Please enter date and time in the 24 hr format:dd-mm-yyyy hh:mm\n"
            + "Please enter the amount in dollars\n"
            + "Please enter the commission fee\n"
            + "I am running the command. Please wait and bear with me............;)\n"
            + "Can't buy shares after/before business hours.\n"
            + commandString
            + "[35mClosing the application..........\n", out.toString());

    /*Test for total cost basis and value*/
    model = new VirtualStockModel(datasource);

    input = "BUY_STOCK\n" + "GOOG\n" + "HEALTH\n" + "22-03-2018 13:30\n" + "2345.\n0.00\n"
            + "GET_TOTAL_COST_BASIS\nHEALTH\n22-03-2018\nq";
    out = new StringBuffer();
    view = new ViewImpl(out);
    controller = new Controller(new StringReader(input), view);
    controller.run(model);
    assertEquals(commandString
            + "Please enter the ticker symbol\n"
            + "Please enter the portfolio name.\n"
            + "Please enter date and time in the 24 hr format:dd-mm-yyyy hh:mm\n"
            + "Please enter the amount in dollars\n"
            + "Please enter the commission fee\n"
            + "I am running the command. Please wait and bear with me............;)\n"
            + "Stock: GOOG is bought successfully\n"
            + commandString
            + "Please enter the portfolio name.\n"
            + "Please enter date in format:dd-mm-yyyy\n"
            + "Total Cost Basis of portfolio: HEALTH on 22-03-2018 is: $ 2098.16\n"
            + commandString

            + "[35mClosing the application..........\n", out.toString());

    input = "BUY_STOCK\n" + "GOOG\n" + "HEALTH\n" + "22-03-2018 13:30\n" + "2345.\n0.00\n"
            + "GET_TOTAL_COST_BASIS\nHEALTH\n22-03-2018\nGET_TOTAL_VALUE\nHEALTH\n23-03-2018\nq";
    model = new VirtualStockModel(DataSourceCreator
            .getDataSource("ALPHAAPI", new StringReader("")));
    out = new StringBuffer();
    view = new ViewImpl(out);
    controller = new Controller(new StringReader(input), view);
    controller.run(model);
    assertEquals(commandString
            + "Please enter the ticker symbol\n"
            + "Please enter the portfolio name.\n"
            + "Please enter date and time in the 24 hr format:dd-mm-yyyy hh:mm\n"
            + "Please enter the amount in dollars\n"
            + "Please enter the commission fee\n"
            + "I am running the command. Please wait and bear with me............;)\n"
            + "Stock: GOOG is bought successfully\n"
            + commandString
            + "Please enter the portfolio name.\n"
            + "Please enter date in format:dd-mm-yyyy\n"
            + "Total Cost Basis of portfolio: HEALTH on 22-03-2018 is: $ 2098.16\n"
            + commandString
            + "Please enter the portfolio name.\n"
            + "Please enter date in format:dd-mm-yyyy\n"
            + "Total cost value of portfolio: HEALTH on 23-03-2018 is: $2043.14\n"
            + commandString
            + "[35mClosing the application..........\n", out.toString());

    /**test for display all portfolios*/
    input = "CREATE_PORTFOLIO\n" + "Health Portfolio" + "\n" + "BUY_STOCK\n" + "GOOG\n"
            + "HEALTH\n"
            + "22-03-2018 13:30\n" + "2345.\n0.00\n"
            + "GET_TOTAL_COST_BASIS\nHEALTH\n22-03-2018\nGET_TOTAL_VALUE\nHEALTH\n23-03-2018\n"
            + "DISPLAY_ALL_PORTFOLIOS\nq";
    model = new VirtualStockModel(datasource);
    out = new StringBuffer();
    view = new ViewImpl(out);
    controller = new Controller(new StringReader(input), view);
    controller.run(model);
    assertEquals(commandString
            + "Please enter the portfolio name.\n"
            + "Portfolio: HEALTH PORTFOLIO is created successfully\n"
            + commandString
            + "Please enter the ticker symbol\n"
            + "Please enter the portfolio name.\n"
            + "Please enter date and time in the 24 hr format:dd-mm-yyyy hh:mm\n"
            + "Please enter the amount in dollars\n"
            + "Please enter the commission fee\n"
            + "I am running the command. Please wait and bear with me............;)\n"
            + "Stock: GOOG is bought successfully\n"
            + commandString
            + "Please enter the portfolio name.\n"
            + "Please enter date in format:dd-mm-yyyy\n"
            + "Total Cost Basis of portfolio: HEALTH on 22-03-2018 is: $ 2098.16\n"
            + commandString
            + "Please enter the portfolio name.\n"
            + "Please enter date in format:dd-mm-yyyy\n"
            + "Total cost value of portfolio: HEALTH on 23-03-2018 is: $2043.14\n"
            + commandString
            + "Portfolio Name : HEALTH PORTFOLIO\n"
            + "StockTickerSymbol     NumberofShares         PriceofUnitShare      BuyDate"
            + "         Commission Fee\n"
            + "Portfolio Name : HEALTH\n"
            + "StockTickerSymbol     NumberofShares         PriceofUnitShare      BuyDate"
            + "         Commission Fee\n"
            + "GOOG                      2                    1049.08            22-03-2018"
            + "         0.00      \n"
            + commandString
            + "[35mClosing the application..........\n", out.toString());

    /*testForquits*/
    model = new VirtualStockModel(datasource);
    input = "CREATE_PORTFOLIO\n" + "q";
    out = new StringBuffer();
    view = new ViewImpl(out);
    controller = new Controller(new StringReader(input), view);
    controller.run(model);
    assertEquals(commandString
            + "Please enter the portfolio name.\n"
            + "Quitting application........\n", out.toString());
    input = "BUY_STOCK\n" + "q\n" + "HEALTH\n" + "22-03-2018 01:30\n" + "2345.\n" + "q";
    out = new StringBuffer();
    view = new ViewImpl(out);
    controller = new Controller(new StringReader(input), view);
    controller.run(model);
    assertEquals(commandString
            + "Please enter the ticker symbol\n"
            + "Quitting application........\n", out.toString());
    input = "BUY_STOCK\n" + "GOOG\n" + "q\n" + "22-03-2018 01:30\n" + "2345.\n" + "q";
    out = new StringBuffer();
    view = new ViewImpl(out);
    controller = new Controller(new StringReader(input), view);
    controller.run(model);
    assertEquals(commandString
            + "Please enter the ticker symbol\n"
            + "Please enter the portfolio name.\n"
            + "Quitting application........\n", out.toString());
    input = "BUY_STOCK\n" + "GOOG\n" + "HEALTH\n" + "q\n" + "2345.\n" + "q";
    out = new StringBuffer();
    view = new ViewImpl(out);
    controller = new Controller(new StringReader(input), view);
    controller.run(model);
    assertEquals(commandString
            + "Please enter the ticker symbol\n"
            + "Please enter the portfolio name.\n"
            + "Please enter date and time in the 24 hr format:dd-mm-yyyy hh:mm\n"
            + "Quitting application........\n", out.toString());
    input = "BUY_STOCK\n" + "GOOG\n" + "HEALTH\n" + "22-03-2018 13:30\n" + "q";
    out = new StringBuffer();
    view = new ViewImpl(out);
    controller = new Controller(new StringReader(input), view);
    controller.run(model);
    assertEquals(commandString
            + "Please enter the ticker symbol\n"
            + "Please enter the portfolio name.\n"
            + "Please enter date and time in the 24 hr format:dd-mm-yyyy hh:mm\n"
            + "Please enter the amount in dollars\n"
            + "Quitting application........\n", out.toString());

    input = "BUY_STOCK\n" + "GOOG\n" + "HEALTH\n" + "22-03-2018 13:30\n" + "2345.\n"
            + "GET_TOTAL_COST_BASIS\nq";
    out = new StringBuffer();
    view = new ViewImpl(out);
    controller = new Controller(new StringReader(input), view);
    controller.run(model);
    assertEquals(commandString
            + "Please enter the ticker symbol\n"
            + "Please enter the portfolio name.\n"
            + "Please enter date and time in the 24 hr format:dd-mm-yyyy hh:mm\n"
            + "Please enter the amount in dollars\n"
            + "Please enter the commission fee\n"
            + "Invalid commissionFee. Commission Fee should contain only non-negative numbers\n"
            + "Please enter the commission fee\n"
            + "Quitting application........\n", out.toString());

    input = "GET_TOTAL_VALUE\nq";
    out = new StringBuffer();
    view = new ViewImpl(out);
    controller = new Controller(new StringReader(input), view);
    controller.run(model);
    assertEquals(commandString
            + "Please enter the portfolio name.\n"
            + "Quitting application........\n", out.toString());

    input = "DISPLAY_PORTFOLIO\nq";
    out = new StringBuffer();
    view = new ViewImpl(out);
    controller = new Controller(new StringReader(input), view);
    controller.run(model);
    assertEquals(commandString
            + "Please enter the portfolio name.\n"
            + "Quitting application........\n", out.toString());


    /*test For input not ended with q*/
    model = new VirtualStockModel(datasource);

    input = "CREATE_PORTFOLIO\n";
    out = new StringBuffer();
    view = new ViewImpl(out);
    controller = new Controller(new StringReader(input), view);
    try {
      controller.run(model);
    } catch (IllegalStateException e) {

      //Do Nothing
    }

  }

}
