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

import static org.junit.Assert.fail;

public class ControllerTest {
  private IView view;

  @Test
  public void testForObjectCreation() {

    try {
      Appendable out = new StringBuffer("abc\n");
      view = new ViewImpl(out);
      ControllerInterface controller = new Controller(null, view);
      fail("Above line should have thrown exception");
    } catch (IllegalArgumentException e) {
      //DO Nothing
    }

    try {
      ControllerInterface controller = new Controller(new StringReader("abc\n"),
              null);
      fail("Above line should have thrown exception");
    } catch (IllegalArgumentException e) {
      //Do Nothing.
    }


    try {
      ControllerInterface controller = new Controller(null, null);
      fail("Above line should have thrown exception");
    } catch (IllegalArgumentException e) {
      //Do Nothing.
    }

    ControllerInterface controller = new Controller(new StringReader("abcq\n"),
            view);

  }

  @Test
  public void testForModelNull() {
    view = new ViewImpl(new StringBuffer("abc\n"));
    ControllerInterface controller = new Controller(new StringReader("q"), view);
    try {
      controller.run(null);
      fail("Above line should have thrown exception");
    } catch (IllegalArgumentException e) {
      //Do Nothing
    }
  }

  @Test
  public void testForReadableContainsNull() {
    String s = "x\n" + null + "q";
    view = new ViewImpl(new StringBuffer("abc\n"));
    ControllerInterface controller = new Controller(new StringReader(s), view);
    VirtualStockModelInterface model = new VirtualStockModel(DataSourceCreator
            .getDataSource("USERINPUT", new StringReader("q")));
    try {
      controller.run(model);
      fail("Above line should have thrown exception");
    } catch (IllegalStateException e) {
      //Do Nothing
    }

  }

  @Test
  public void testForWaitForRightInput() {
    VirtualStockModelInterface model = new VirtualStockModel(DataSourceCreator
            .getDataSource("USERINPUT", new StringReader("GOOG,22-03-2018,230.03\n"
                    + "MFT,23-02-2019,235.05\nGOOG,23-03-2018,545.9\nq")));

    String input = "x\ny\nz\nCREATE_PORTFOLIO\n123\nmy_port\nMY PORTFOLIO\na\nb\nDISPLAY_PORTFOLIO"
            + "\n34\n1\nmy portfolio\na\n\b\nBUY_STOCK\n123\nGOOG\n1\n2\nmy port\n12\na1\n"
            + "22-03-2018 00:01\n-2\n234.\nx\nGET_TOTAL_COST_BASIS\n1\nGOOG\na\n23-03-2018\n"
            + "a\nb\nGET_TOTAL_VALUE\n42\nGOOG\na\n23-03-2018\nCREATE_PORTFOLIO\nsecond portfolio\n"
            + "DISPLAY_ALL_PORTFOLIOS\nq";


    Appendable out = new StringBuffer();
    view = new ViewImpl(out);
    ControllerInterface controller = new Controller(new StringReader(input), view);
    controller.run(model);
    assertEquals("\u001B[35mAn invalid command is provided. Please provide it again\n"
            + "\u001B[35mAn invalid command is provided. Please provide it again\n"
            + "\u001B[35mAn invalid command is provided. Please provide it again\n"
            + "Portfolio name should contains only letters and spaces.Please enter "
            + "the portfolio"
            + " name again\n" + "Portfolio name should contains only letters and spaces."
            + "Please"
            + " enter the portfolio name again\n"
            + "Portfolio: MY PORTFOLIO is created successfully"
            + "\n" + "\u001B[35mAn invalid command is provided. Please provide it again\n"
            + "\u001B[35mAn invalid command is provided. Please provide it again\n"
            + "Portfolio name should contains only letters and spaces.Please enter the"
            + " portfolio name again\n"
            + "Portfolio name should contains only letters and spaces.Please enter "
            + "the portfolio"
            + " name again\n" + "Portfolio Name: MY PORTFOLIO\n"
            + "StockTickerSymbol     NumberofShares         PriceofUnitShare      BuyDate"
            + "         Commission Fee"
            + "\n" + "\u001B[35mAn invalid command is provided. Please provide it again\n"
            + "\u001B[35mAn invalid command is provided. Please provide it again\n"
            + "Ticker symbol should contain only letters and should have atleast one "
            + "letter." + "Please enter the ticker symbol again" + "\n"
            + "Portfolio name should contains only"
            + " letters and spaces.Please enter the portfolio name again\n"
            + "Portfolio name should contains only letters and spaces.Please "
            + "enter the portfolio name again\n" + "Please enter date again in valid "
            + "format:dd-mm-yyyy hh:mm"
            + "\n" + "Please enter date again in valid format:dd-mm-yyyy hh:mm"
            + "\n" + "Please enter a valid amount" + "\n"
            + "Can't buy shares after/before "
            + "business hours." + "\n" + "\u001B[35mAn invalid command is provided."
            + " Please provide it "
            + "again\n" + "Portfolio name should contains only letters and spaces."
            + "Please enter"
            + " the portfolio name again\n" + "Please enter date again in valid "
            + "format:dd-mm-yyyy" + "\n" + "Portfolio: GOOG doesn't exist"
            + "\n" + "\u001B[35mAn invalid command is provided."
            + " Please provide"
            + " it again\n" + "\u001B[35mAn invalid command is provided. Please provide it again\n"
            + "Portfolio name should contains only letters and spaces.Please"
            + " enter the portfolio"
            + " name again\n" + "Please enter date again in valid "
            + "format:dd-mm-yyyy\n" + "Portfolio: GOOG doesn't exist" + "\n"
            + "Portfolio: SECOND PORTFOLIO is created successfully" + "\n"
            + "Portfolio Name :"
            + " MY PORTFOLIO\n"
            + "StockTickerSymbol     NumberofShares         PriceofUnitShare      BuyDate"
            + "         Commission Fee\n"
            + "Portfolio Name : SECOND PORTFOLIO\n" + "StockTickerSymbol     NumberofShares"
            + "         PriceofUnitShare      BuyDate         Commission Fee"
            + "\n" + "\u001B[35mClosing the "
            + "application..........\n", out.toString());
  }

  @Test
  public void testConstructPortFolioCommand() {

    VirtualStockModelInterface model = new VirtualStockModel(DataSourceCreator
            .getDataSource("USERINPUT", new StringReader("GOOG,22-03-2018,230.03\n"
                    + "MFT,23-02-2019,235.05\nGOOG,23-03-2018,545.9\nq")));

    String input = "CREATE_PORTFOLIO\n" + "" + "\nq";
    Appendable out = new StringBuffer();
    view = new ViewImpl(out);
    ControllerInterface controller = new Controller(new StringReader(input), view);
    controller.run(model);
    assertEquals(out.toString(), "Portfolio name should contains only letters and spaces."
            + "Please enter the portfolio name again\n"
            + "Quitting application........\n");
    input = "CREATE_PORTFOLIO\n" + "Health Portfolio" + "\nq";
    out = new StringBuffer();
    view = new ViewImpl(out);
    controller = new Controller(new StringReader(input), view);
    controller.run(model);
    assertEquals(out.toString(), "Portfolio: HEALTH PORTFOLIO is created successfully"
            + "\n"
            + "\u001B[35mClosing the application..........\n");
  }

  @Test
  public void testForBuyShares() {

    VirtualStockModelInterface model = new VirtualStockModel(DataSourceCreator
            .getDataSource("USERINPUT", new StringReader("GOOG,22-03-2018,230.03\n"
                    + "MFT,23-02-2019,235.05\nGOOG,23-03-2018,545.9\nq")));

    String input = "BUY_STOCK\n" + "GOOG\n" + "HEALTH\n" + "22-03-2018 13:30\n" + "234.\n" + "q";
    StringBuffer out = new StringBuffer();
    view = new ViewImpl(out);
    ControllerInterface controller = new Controller(new StringReader(input), view);
    controller.run(model);
    assertEquals(out.toString(), "Stock: GOOG is bought successfully"
            + "\n" + "\u001B[35mClosing the application..........\n");
    input = "BUY_STOCK\n" + "GOOG\n" + "HEALTH\n" + "22-03-2018 01:30\n" + "234.\n" + "q";
    out = new StringBuffer();
    view = new ViewImpl(out);
    controller = new Controller(new StringReader(input), view);
    controller.run(model);
    assertEquals(out.toString(), "Can't buy shares after/before business hours.\n"
            + "\u001B[35mClosing the application..........\n");
  }

  @Test
  public void testForTotalCostBasisandValue() {

    VirtualStockModelInterface model = new VirtualStockModel(DataSourceCreator
            .getDataSource("USERINPUT", new StringReader("GOOG,22-03-2018,230.03\n"
                    + "MFT,23-02-2019,235.05\nGOOG,23-03-2018,545.9\nq")));

    String input = "BUY_STOCK\n" + "GOOG\n" + "HEALTH\n" + "22-03-2018 13:30\n" + "234.\n"
            + "GET_TOTAL_COST_BASIS\nHEALTH\n22-03-2018\nq";
    StringBuffer out = new StringBuffer();
    view = new ViewImpl(out);
    ControllerInterface controller = new Controller(new StringReader(input), view);
    controller.run(model);
    assertEquals(out.toString(), "Stock: GOOG is bought successfully\n"
            + "Total Cost Basis of portfolio: HEALTH on 22-03-2018 is:230.03\n"
            + "\u001B[35mClosing the application..........\n");

    input = "BUY_STOCK\n" + "GOOG\n" + "HEALTH\n" + "22-03-2018 13:30\n" + "234.\n"
            + "GET_TOTAL_COST_BASIS\nHEALTH\n22-03-2018\nGET_TOTAL_VALUE\nHEALTH\n23-03-2018\nq";
    model = new VirtualStockModel(DataSourceCreator
            .getDataSource("USERINPUT", new StringReader("GOOG,22-03-2018,230.03\n"
                    + "MFT,23-02-2019,235.05\nGOOG,23-03-2018,545.9\nq")));
    out = new StringBuffer();
    view = new ViewImpl(out);
    controller = new Controller(new StringReader(input), view);
    controller.run(model);
    assertEquals(out.toString(), "Stock: GOOG is bought successfully\n"
            + "Total Cost Basis of portfolio: HEALTH on 22-03-2018 is:230.03\n"
            + "Total cost value of portfolio: HEALTH on 23-03-2018 is:545.90"
            + "\n" + "\u001B[35mClosing the"
            + " application..........\n");

  }

  @Test
  public void testForDisplayAll_portfolios() {
    String input = "CREATE_PORTFOLIO\n" + "Health Portfolio" + "\n" + "BUY_STOCK\n" + "GOOG\n"
            + "HEALTH\n"
            + "22-03-2018 13:30\n" + "234.\n"
            + "GET_TOTAL_COST_BASIS\nHEALTH\n22-03-2018\nGET_TOTAL_VALUE\nHEALTH\n23-03-2018\n"
            + "DISPLAY_ALL_PORTFOLIOS\nq";
    VirtualStockModelInterface model = new VirtualStockModel(DataSourceCreator
            .getDataSource("USERINPUT", new StringReader("GOOG,22-03-2018,230.03\n"
                    + "MFT,23-02-2019,235.05\nGOOG,23-03-2018,545.9\nq")));
    StringBuffer out = new StringBuffer();
    view = new ViewImpl(out);
    ControllerInterface controller = new Controller(new StringReader(input), view);
    controller.run(model);
    assertEquals(out.toString(), "Portfolio: HEALTH PORTFOLIO is created successfully\n"
            + "Stock: GOOG is bought successfully\n"
            + "Total Cost Basis of portfolio: HEALTH on 22-03-2018 is:230.03"
            + "\n"
            + "Total cost value of portfolio: HEALTH on 23-03-2018 is:545.90\n"
            + "Portfolio Name : HEALTH PORTFOLIO\n"
            + "StockTickerSymbol     NumberofShares         PriceofUnitShare      BuyDate"
            + "         Commission Fee\n"
            + "Portfolio Name : HEALTH\n"
            + "StockTickerSymbol     NumberofShares         PriceofUnitShare      BuyDate"
            + "         Commission Fee\n"
            + "GOOG                      1                    230.03             22-03-2018"
            + "         0.00      \n"
            + "\u001B[35mClosing the application..........\n");
  }

  @Test
  public void testForQuits() {

    VirtualStockModelInterface model = new VirtualStockModel(DataSourceCreator
            .getDataSource("USERINPUT", new StringReader("GOOG,22-03-2018,230.03\n"
                    + "MFT,23-02-2019,235.05\nGOOG,23-03-2018,545.9\nq")));

    String input = "CREATE_PORTFOLIO\n" + "q";
    StringBuffer out = new StringBuffer();
    view = new ViewImpl(out);
    ControllerInterface controller = new Controller(new StringReader(input), view);
    controller.run(model);
    assertEquals(out.toString(), "Quitting application........\n");
    input = "BUY_STOCK\n" + "q\n" + "HEALTH\n" + "22-03-2018 01:30\n" + "234.\n" + "q";
    out = new StringBuffer();
    view = new ViewImpl(out);
    controller = new Controller(new StringReader(input), view);
    controller.run(model);
    assertEquals(out.toString(), "Quitting application........\n");
    input = "BUY_STOCK\n" + "GOOG\n" + "q\n" + "22-03-2018 01:30\n" + "234.\n" + "q";
    out = new StringBuffer();
    view = new ViewImpl(out);
    controller = new Controller(new StringReader(input), view);
    controller.run(model);
    assertEquals(out.toString(), "Quitting application........\n");
    input = "BUY_STOCK\n" + "GOOG\n" + "HEALTH\n" + "q\n" + "234.\n" + "q";
    out = new StringBuffer();
    view = new ViewImpl(out);
    controller = new Controller(new StringReader(input), view);
    controller.run(model);
    assertEquals(out.toString(), "Quitting application........\n");
    input = "BUY_STOCK\n" + "GOOG\n" + "HEALTH\n" + "22-03-2018 13:30\n" + "q";
    out = new StringBuffer();
    view = new ViewImpl(out);
    controller = new Controller(new StringReader(input), view);
    controller.run(model);
    assertEquals(out.toString(), "Quitting application........\n");

    input = "BUY_STOCK\n" + "GOOG\n" + "HEALTH\n" + "22-03-2018 13:30\n" + "234.\n"
            + "GET_TOTAL_COST_BASIS\nq";
    out = new StringBuffer();
    view = new ViewImpl(out);
    controller = new Controller(new StringReader(input), view);
    controller.run(model);
    assertEquals(out.toString(), "Stock: GOOG is bought successfully"
            + "\n" + "Quitting application........\n");

    input = "GET_TOTAL_VALUE\nq";
    out = new StringBuffer();
    view = new ViewImpl(out);
    controller = new Controller(new StringReader(input), view);
    controller.run(model);
    assertEquals(out.toString(), "Quitting application........\n");

    input = "DISPLAY_PORTFOLIO\nq";
    out = new StringBuffer();
    view = new ViewImpl(out);
    controller = new Controller(new StringReader(input), view);
    controller.run(model);
    assertEquals(out.toString(), "Quitting application........\n");


  }

  @Test(expected = IllegalStateException.class)
  public void testForInputNotendedwithQ() {
    VirtualStockModelInterface model = new VirtualStockModel(DataSourceCreator
            .getDataSource("USERINPUT", new StringReader("GOOG,22-03-2018,230.03\n"
                    + "MFT,23-02-2019,235.05\nGOOG,23-03-2018,545.9\nq")));

    String input = "CREATE_PORTFOLIO\n";
    StringBuffer out = new StringBuffer();
    view = new ViewImpl(out);
    ControllerInterface controller = new Controller(new StringReader(input), view);
    controller.run(model);

  }
}
