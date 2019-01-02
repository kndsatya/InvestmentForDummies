import org.junit.Test;

import java.io.StringReader;

import controller.Controller;
import controller.ControllerInterface;
import model.VirtualStockModelInterface;
import view.IView;
import view.ViewImpl;

import static org.junit.Assert.assertEquals;

public class MockModelTest {

  @Test
  public void testMockModel() {
    IView view;
    String commandString = "\n\nCommand Name                        -      Command"
            + " Description"
            + "\n"
            + "-------------                              --------------------\n"
            + "CREATE_PORTFOLIO                    -      Command that helps to create a portfolio."
            + "\n"
            + "DISPLAY_PORTFOLIO                   -      Command that displays the contents of a"
            + " portfolio.\n" + "BUY_STOCK                           -      Command to buy a stock "
            + "in"
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

    StringBuffer log = new StringBuffer();
    StringBuilder uniqueString = new StringBuilder("uniqueString");
    VirtualStockModelInterface mockmodel = new MockModel(log, 2.50, uniqueString);
    StringBuffer out = new StringBuffer();
    view = new ViewImpl(out);
    String input = "CREATE_PORTFOLIO\ntest portfolio\nq";
    ControllerInterface controller = new Controller(new StringReader(input), view);
    controller.run(mockmodel);
    assertEquals("test portfolio", log.toString());
    input = "BUY_STOCK\n" + "GOOG\n" + "HEALTH\n" + "22-03-2018 13:30\n" + "234.\n0.00\n" + "q";
    view = new ViewImpl(out);
    controller = new Controller(new StringReader(input), view);
    log = new StringBuffer();
    mockmodel = new MockModel(log, 2.50, uniqueString);
    controller.run(mockmodel);
    assertEquals("GOOG HEALTH 234.0 22-03-2018 13:30 0.0", log.toString());
    input = "DISPLAY_PORTFOLIO\nHEALTH\nq";
    out = new StringBuffer();
    view = new ViewImpl(out);
    controller = new Controller(new StringReader(input), view);
    log = new StringBuffer();

    mockmodel = new MockModel(log, 2.50, uniqueString);
    controller.run(mockmodel);
    assertEquals("HEALTH", log.toString());
    assertEquals(commandString
            + "Please enter the portfolio name.\n"
            + "uniqueString\n"
            + commandString
            + "[35mClosing the application..........\n", out.toString());
    input = "DISPLAY_ALL_PORTFOLIOS\nq";
    out = new StringBuffer();
    view = new ViewImpl(out);
    controller = new Controller(new StringReader(input), view);
    log = new StringBuffer();
    mockmodel = new MockModel(log, 2.50, uniqueString);
    controller.run(mockmodel);
    assertEquals(commandString
            + "uniqueString\n"
            + commandString
            + "[35mClosing the application..........\n", out.toString());

    input = "GET_TOTAL_COST_BASIS\nHEALTH\n22-03-2018\nq";
    out = new StringBuffer();
    view = new ViewImpl(out);
    controller = new Controller(new StringReader(input), view);
    log = new StringBuffer();
    mockmodel = new MockModel(log, 2.50, uniqueString);
    controller.run(mockmodel);
    assertEquals("HEALTH 22-03-2018", log.toString());
    assertEquals(commandString
            + "Please enter the portfolio name.\n"
            + "Please enter date in format:dd-mm-yyyy\n"
            + "Total Cost Basis of portfolio: HEALTH on 22-03-2018 is: $ 2.50\n"
            + commandString
            + "[35mClosing the application..........\n", out.toString());
    input = "GET_TOTAL_VALUE\nHEALTH\n22-03-2018\nq";
    out = new StringBuffer();
    view = new ViewImpl(out);
    controller = new Controller(new StringReader(input), view);
    log = new StringBuffer();
    mockmodel = new MockModel(log, 2.50, uniqueString);
    controller.run(mockmodel);
    assertEquals("HEALTH 22-03-2018", log.toString());
    assertEquals(commandString
            + "Please enter the portfolio name.\n"
            + "Please enter date in format:dd-mm-yyyy\n"
            + "Total cost value of portfolio: HEALTH on 22-03-2018 is: $2.50\n"
            + commandString
            + "[35mClosing the application..........\n", out.toString());


  }
}
