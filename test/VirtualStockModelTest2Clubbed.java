import org.junit.Test;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import datasource.DataSourceCreator;
import datasource.DataSourceInterface;
import model.VirtualStockModel;
import model.VirtualStockModelInterface;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class VirtualStockModelTest2Clubbed {

  @Test
  public void test() {

    StringReader reader = new StringReader("");
    DataSourceInterface dataSource = DataSourceCreator.getDataSource("ALPHAAPI", reader);

    /**
     * Testing buySharesOfStock with commission fee method - to buy one stock. Checking using
     * displayPortfolio.
     */
    VirtualStockModelInterface virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.buySharesOfStock("GOOG", "MyFirstPortfolio",
            1853, "09-08-2017 12:56", 30.50);

    String expectedString = "Portfolio Name: MYFIRSTPORTFOLIO\n"
            + "StockTickerSymbol     NumberofShares         PriceofUnitShare      BuyDate         "
            + "Commission Fee\n"
            + "GOOG                      2                    922.90             09-08-2017"
            + "         "
            + "30.50     ";


    assertEquals(expectedString,
            virtualStockModel.displayPortfolio("MyFirstPortfolio"));


    /**
     * Testing buySharesOfStock with commission fee method - to buy one stock. Checking using
     * totalCostBasis.
     */

    virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.createPortfolio("MyFirstPortfolio");

    virtualStockModel.buySharesOfStock("GOOG", "MyFirstPortfolio",
            1853, "09-08-2017 12:56", 30.50);

    assertEquals(1876.3,
            virtualStockModel.getTotalCostBasis("MYFIRSTPORTFOLIO",
                    "09-08-2017"), 0.001);


    /**
     * Testing buySharesOfStock with commission fee method - to buy multiple stocks. Checking using
     * totalCostBasis.
     */

    virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.createPortfolio("MyFirstPortfolio");

    virtualStockModel.buySharesOfStock("GOOG", "MyFirstPortfolio",
            1853, "09-08-2017 12:56", 30.50);

    virtualStockModel.buySharesOfStock("GOOG", "mySecond",
            2000, "21-04-2014 12:56", 30);

    virtualStockModel.buySharesOfStock("GOOG", "mySecond",
            2500, "21-11-2018 12:56", 50);

    virtualStockModel.buySharesOfStock("aapl", "mySecond",
            550, "11-03-2016 12:56", 50.67);

    virtualStockModel.buySharesOfStock("msft", "mySecond",
            2500, "28-07-2015 12:56", 50);

    assertEquals(6846.75,
            virtualStockModel.getTotalCostBasis("mySecond",
                    "23-11-2018"), 0.001);


    /**
     * Testing buySharesOfStock with commission fee method - to buy multiple stocks. Checking using
     * totalCostBasis. In between multiple stocks are bought.
     */

    virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.createPortfolio("MyFirstPortfolio");

    virtualStockModel.buySharesOfStock("GOOG", "MyFirstPortfolio",
            1853, "09-08-2017 12:56", 30.50);

    virtualStockModel.buySharesOfStock("GOOG", "mySecond",
            2000, "21-04-2014 12:56", 30);

    virtualStockModel.buySharesOfStock("GOOG", "mySecond",
            2500, "21-11-2018 12:56", 50);

    virtualStockModel.buySharesOfStock("aapl", "mySecond",
            550, "11-03-2016 12:56", 50.67);

    virtualStockModel.buySharesOfStock("msft", "mySecond",
            2500, "28-07-2015 12:56", 50);

    assertEquals(4159.56,
            virtualStockModel.getTotalCostBasis("mySecond",
                    "04-02-2016"), 0.001);

    /**
     * Testing total value for multiple stocks - in the middle.
     */

    virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.createPortfolio("MyFirstPortfolio");

    virtualStockModel.buySharesOfStock("GOOG", "MyFirstPortfolio",
            1853, "09-08-2017 12:56", 30.50);

    virtualStockModel.buySharesOfStock("GOOG", "mySecond",
            2000, "21-04-2014 12:56", 30);

    virtualStockModel.buySharesOfStock("GOOG", "mySecond",
            2500, "21-11-2018 12:56", 50);

    virtualStockModel.buySharesOfStock("aapl", "mySecond",
            550, "11-03-2016 12:56", 50.67);

    virtualStockModel.buySharesOfStock("msft", "mySecond",
            2500, "28-07-2015 12:56", 50);

    assertEquals(4984.03,
            virtualStockModel.getTotalValue("mySecond",
                    "04-02-2016"), 0.001);

    /**
     * Testing total value for multiple stocks - latest date.
     */

    virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.createPortfolio("MyFirstPortfolio");

    virtualStockModel.buySharesOfStock("GOOG", "MyFirstPortfolio",
            1853, "09-08-2017 12:56", 30.50);

    virtualStockModel.buySharesOfStock("GOOG", "mySecond",
            2000, "21-04-2014 12:56", 30);

    virtualStockModel.buySharesOfStock("GOOG", "mySecond",
            2500, "21-11-2018 12:56", 50);

    virtualStockModel.buySharesOfStock("aapl", "mySecond",
            550, "11-03-2016 12:56", 50.67);

    virtualStockModel.buySharesOfStock("msft", "mySecond",
            2500, "28-07-2015 12:56", 50);

    assertEquals(11743.0,
            virtualStockModel.getTotalValue("mySecond",
                    "22-11-2018"), 0.001);

    /**
     * Testing buySharesOfStock with commission fee method - to buy one stock. Checking using
     * totalCostBasis.
     */


    virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.createPortfolio("MyFirstPortfolio");

    virtualStockModel.buySharesOfStock("GOOG", "MyFirstPortfolio",
            1853, "09-08-2017 12:56", 30.50);

    assertEquals(1876.3,
            virtualStockModel.getTotalCostBasis("MYFIRSTPORTFOLIO",
                    "09-08-2017"), 0.001);


    /**
     * Testing for invalid ticker symbol. Throws IllegalArgumentException.
     */

    virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.createPortfolio("MyFirstPortfolio");

    try {
      virtualStockModel.buySharesOfStock("GO", "MyFirstPortfolio",
              1853, "09-08-2017 12:56", 30.50);
      fail("Above line should have thrown exception");
    } catch (IllegalArgumentException e) {
      //Do Nothing
    }


    /**
     * Testing for portfolio name doesn't exist. Creates a new portfolio with the given name and
     * adds the stock to that portfolio. Also the commission fee can be given as $0.00.
     */

    virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.buySharesOfStock("GOOG", "MyFirstPortfolio",
            1853, "09-08-2017 12:56", 0.00);

    assertEquals(1845.8,
            virtualStockModel.getTotalCostBasis("MYFIRSTPORTFOLIO",
                    "09-08-2017"), 0.001);


    /**
     * Testing for portfolio name passed as null. Throws IllegalArgumentException.
     */

    virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.createPortfolio("MyFirstPortfolio");

    try {
      virtualStockModel.buySharesOfStock("GO", null,
              1853, "09-08-2017 12:56", 30.50);
      fail("Above line should have thrown exception");
    } catch (IllegalArgumentException e) {
      //Do Nothing
    }

    /**
     * Testing for portfolio name passed as empty string. Throws IllegalArgumentException.
     */

    virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.createPortfolio("MyFirstPortfolio");

    try {
      virtualStockModel.buySharesOfStock("GO", "",
              1853, "09-08-2017 12:56", 30.50);
      fail("Above line should have thrown exception");
    } catch (IllegalArgumentException e) {
      //Do Nothing
    }

    /**
     * The amount to buy stocks cannot be negative. Throws IllegalArgumentException.
     */

    virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.createPortfolio("MyFirstPortfolio");

    try {
      virtualStockModel.buySharesOfStock("GO", "MYFIRSTPORTFOLIO",
              -100, "09-08-2017 12:56", 30.50);
      fail("Above line should have thrown exception");
    } catch (IllegalArgumentException e) {
      //Do Nothing
    }

    /**
     * The commission fee to buy stocks cannot be negative. Throws IllegalArgumentException.
     */

    virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.createPortfolio("MyFirstPortfolio");

    try {
      virtualStockModel.buySharesOfStock("GO", "MYFIRSTPORTFOLIO",
              1853, "09-08-2017 12:56", -30.50);
      fail("Above line should have thrown exception");
    } catch (IllegalArgumentException e) {
      //Do Nothing
    }


    /**
     * The date cannot be null. Throws IllegalArgumentException.
     */

    virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.createPortfolio("MyFirstPortfolio");

    try {
      virtualStockModel.buySharesOfStock("GO", "MYFIRSTPORTFOLIO",
              1853, null, 30.50);
      fail("Above line should have thrown exception");
    } catch (IllegalArgumentException e) {
      //Do Nothing
    }

    /**
     * The date cannot be empty string. Throws IllegalArgumentException.
     */

    virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.createPortfolio("MyFirstPortfolio");

    try {
      virtualStockModel.buySharesOfStock("GO", "MYFIRSTPORTFOLIO",
              1853, "", 30.50);
      fail("Above line should have thrown exception");
    } catch (IllegalArgumentException e) {
      //Do Nothing
    }

    /**
     * The date should be in dd-MM-yyyy HH:mm, if the date does not have time.
     * Throws IllegalArgumentException.
     */

    virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.createPortfolio("MyFirstPortfolio");

    try {
      virtualStockModel.buySharesOfStock("GO", "MYFIRSTPORTFOLIO",
              1853, "09-08-2017", 30.50);
      fail("Above line should have thrown exception");
    } catch (IllegalArgumentException e) {
      //Do Nothing
    }

    /**
     * The date should be in dd-MM-yyyy HH:mm, For day, month, hours and minutes, if it is a single
     * digit, please mention it with zero.  7-8-2017 4:30-> 07-08-2017 04:30.
     * Throws IllegalArgumentException.
     */

    virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.createPortfolio("MyFirstPortfolio");

    try {
      virtualStockModel.buySharesOfStock("GO", "MYFIRSTPORTFOLIO",
              1853, "9-08-2017 4:30", 30.50);
      fail("Above line should have thrown exception");
    } catch (IllegalArgumentException e) {
      //Do Nothing
    }

    /**
     * Cannot buy the stock before 9 AM and after 4PM. Throws IllegalArgumentException.
     */

    virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.createPortfolio("MyFirstPortfolio");

    try {
      virtualStockModel.buySharesOfStock("GO", "MYFIRSTPORTFOLIO",
              1853, "09-08-2017 08:30", 30.50);
      fail("Above line should have thrown exception");
    } catch (IllegalArgumentException e) {
      //Do Nothing
    }


    /**
     * Portfolio name does not exist. Throws IllegalArgumentException.
     */

    virtualStockModel = new VirtualStockModel(dataSource);

    Map<String, Double> stockWeights = new HashMap<>();

    stockWeights.put("goog", 50.00);
    stockWeights.put("mstf", 50.00);

    try {

      ( (VirtualStockModel) virtualStockModel ).invest("myone", stockWeights,
              2000, 40, "09-08-2017 12:56");
      fail("Above line should have thrown exception");
    } catch (IllegalArgumentException e) {
      //Do Nothing
    }


    /**
     * Portfolio name cannot be null. Throws IllegalArgumentException.
     */

    virtualStockModel = new VirtualStockModel(dataSource);

    stockWeights = new HashMap<>();

    stockWeights.put("goog", 50.00);
    stockWeights.put("mstf", 50.00);

    try {

      ( (VirtualStockModel) virtualStockModel ).invest(null, stockWeights,
              2000, 40, "09-08-2017 12:56");
      fail("Above line should have thrown exception");
    } catch (IllegalArgumentException e) {
      //Do Nothing
    }


    /**
     * Portfolio name cannot be empty string. Throws IllegalArgumentException.
     */

    virtualStockModel = new VirtualStockModel(dataSource);

    stockWeights = new HashMap<>();

    stockWeights.put("goog", 50.00);
    stockWeights.put("mstf", 50.00);

    try {

      ( (VirtualStockModel) virtualStockModel ).invest("", stockWeights,
              2000, 40, "09-08-2017 12:56");
      fail("Above line should have thrown exception");
    } catch (IllegalArgumentException e) {
      //Do Nothing
    }


    /**
     * Amount to be invested cannot be negative. Throws IllegalArgumentException.
     */

    virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.createPortfolio("MyFirstPortfolio");

    virtualStockModel.buySharesOfStock("GOOG", "MyFirstPortfolio",
            1853, "09-08-2017 12:56", 30.50);

    virtualStockModel.buySharesOfStock("GOOG", "mySecond",
            2000, "21-04-2014 12:56", 30);

    virtualStockModel.buySharesOfStock("GOOG", "mySecond",
            2500, "21-11-2018 12:56", 50);

    virtualStockModel.buySharesOfStock("aapl", "mySecond",
            550, "11-03-2016 12:56", 50.67);

    virtualStockModel.buySharesOfStock("msft", "mySecond",
            2500, "28-07-2015 12:56", 50);

    virtualStockModel.buySharesOfStock("tsla", "mySecond",
            2500, "28-07-2015 12:56", 50);

    stockWeights = new HashMap<>();

    stockWeights.put("goog", 50.00);
    stockWeights.put("mstf", 25.00);
    stockWeights.put("aapl", 12.50);
    stockWeights.put("tsla", 12.50);

    try {

      ( (VirtualStockModel) virtualStockModel ).invest("MyFirstPortfolio", stockWeights,
              -2000, 40, "09-08-2017 12:56");
      fail("Above line should have thrown exception");
    } catch (IllegalArgumentException e) {
      //Do Nothing
    }


    /**
     * Commission fee cannot be negative. Throws IllegalArgumentException.
     */

    virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.createPortfolio("MyFirstPortfolio");

    virtualStockModel.buySharesOfStock("GOOG", "MyFirstPortfolio",
            1853, "09-08-2017 12:56", 30.50);

    virtualStockModel.buySharesOfStock("GOOG", "mySecond",
            2000, "21-04-2014 12:56", 30);

    virtualStockModel.buySharesOfStock("GOOG", "mySecond",
            2500, "21-11-2018 12:56", 50);

    virtualStockModel.buySharesOfStock("aapl", "mySecond",
            550, "11-03-2016 12:56", 50.67);

    virtualStockModel.buySharesOfStock("msft", "mySecond",
            2500, "28-07-2015 12:56", 50);

    virtualStockModel.buySharesOfStock("tsla", "mySecond",
            2500, "28-07-2015 12:56", 50);

    stockWeights = new HashMap<>();

    stockWeights.put("goog", 50.00);
    stockWeights.put("mstf", 25.00);
    stockWeights.put("aapl", 12.50);
    stockWeights.put("tsla", 12.50);

    try {

      ( (VirtualStockModel) virtualStockModel ).invest("MyFirstPortfolio",
              stockWeights,
              2000, -40, "09-08-2017 12:56");
      fail("Above line should have thrown exception");
    } catch (IllegalArgumentException e) {
      //Do Nothing
    }

    /**
     * The sum of percentages of the ticker symbol must be 100.00 or 99.00.
     */

    virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.createPortfolio("MyFirstPortfolio");

    virtualStockModel.buySharesOfStock("msft", "mySecond",
            2500, "26-06-2015 12:56", 50);

    virtualStockModel.buySharesOfStock("tsla", "mySecond",
            2500, "28-07-2015 12:56", 50);

    stockWeights = new HashMap<>();


    stockWeights.put("MSFT", 25.00);
    stockWeights.put("TSLA", 12.50);

    try {
      virtualStockModel.invest("mySecond", stockWeights,
              10000, 40, "09-08-2017 12:56");
      fail("Above line should have thrown exception");
    } catch (IllegalArgumentException e) {
      //Do Nothing
    }


    /**
     * The individual percentage of the ticker symbol cannot be negative.
     * Throws IllegalArgumentException.
     */


    virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.createPortfolio("MyFirstPortfolio");

    virtualStockModel.buySharesOfStock("msft", "mySecond",
            2500, "26-06-2015 12:56", 50);

    virtualStockModel.buySharesOfStock("tsla", "mySecond",
            2500, "28-07-2015 12:56", 50);

    stockWeights = new HashMap<>();


    stockWeights.put("MSFT", -25.00);
    stockWeights.put("TSLA", 12.50);


    try {
      virtualStockModel.invest("mySecond", stockWeights,
              10000, 40, "09-08-2017 12:56");
      fail("Above line should have thrown exception");
    } catch (IllegalArgumentException e) {
      //Do Nothing
    }

    /**
     * The date must be in dd-MM-yyy HH:MM format only and for single digits
     * please enter it with zero 7-8-2018 6:50 -> 07-08-2018 06:50.
     */

    virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.createPortfolio("MyFirstPortfolio");

    virtualStockModel.buySharesOfStock("msft", "mySecond",
            2500, "26-06-2015 12:56", 50);

    virtualStockModel.buySharesOfStock("tsla", "mySecond",
            2500, "28-07-2015 12:56", 50);

    stockWeights = new HashMap<>();


    stockWeights.put("MSFT", 50.00);
    stockWeights.put("TSLA", 50.00);

    try {
      virtualStockModel.invest("mySecond", stockWeights,
              10000, 40, "09-08-2017");
      fail("Above line should have thrown exception");
    } catch (IllegalArgumentException e) {
      //Do Nothing
    }

    /**
     * The investment cannot be done on a holiday. Throws IllegalArgumentException.
     */

    virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.createPortfolio("MyFirstPortfolio");

    virtualStockModel.buySharesOfStock("msft", "mySecond",
            2500, "26-06-2015 12:56", 50);

    virtualStockModel.buySharesOfStock("tsla", "mySecond",
            2500, "28-07-2015 12:56", 50);

    stockWeights = new HashMap<>();


    stockWeights.put("MSFT", 50.00);
    stockWeights.put("TSLA", 50.00);

    try {
      virtualStockModel.invest("mySecond", stockWeights,
              10000, 40, "12-08-2017 12:00");
      fail("Above line should have thrown exception");
    } catch (IllegalArgumentException e) {
      //Do Nothing
    }

    /**
     * The ticker symbol cannot be null. Throws IllegalArgumentException.
     */

    virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.createPortfolio("MyFirstPortfolio");

    virtualStockModel.buySharesOfStock("msft", "mySecond",
            2500, "26-06-2015 12:56", 50);

    virtualStockModel.buySharesOfStock("tsla", "mySecond",
            2500, "28-07-2015 12:56", 50);

    stockWeights = new HashMap<>();


    stockWeights.put(null, 25.00);
    stockWeights.put("TSLA", 12.50);

    try {
      virtualStockModel.invest("mySecond", stockWeights,
              10000, 40, "09-08-2017 12:56");
      fail("Above line should have thrown exception");
    } catch (IllegalArgumentException e) {
      //Do Nothing
    }

    /**
     * The ticker symbol cannot be empty. Throws IllegalArgumentException.
     */

    virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.createPortfolio("MyFirstPortfolio");

    virtualStockModel.buySharesOfStock("msft", "mySecond",
            2500, "26-06-2015 12:56", 50);

    virtualStockModel.buySharesOfStock("tsla", "mySecond",
            2500, "28-07-2015 12:56", 50);

    stockWeights = new HashMap<>();


    stockWeights.put("", 25.00);
    stockWeights.put("TSLA", 12.50);

    try {
      virtualStockModel.invest("mySecond", stockWeights,
              10000, 40, "09-08-2017 12:56");
      fail("Above line should have thrown exception");
    } catch (IllegalArgumentException e) {
      //Do Nothing
    }

    /**
     * The invest method invests in the given stocks. The user just creates and adds the stocks.
     * Using the invest method buys shares for those companies mentioned by him.
     */

    virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.addStock("investPortfolio", "Fb");
    virtualStockModel.addStock("investPortfolio", "aapl");
    virtualStockModel.addStock("investPortfolio", "nflx");
    virtualStockModel.addStock("investPortfolio", "goog");

    stockWeights = new HashMap<>();

    stockWeights.put("Fb", 25.00);
    stockWeights.put("aapl", 25.00);
    stockWeights.put("nflx", 25.00);
    stockWeights.put("goog", 25.00);

    String expected = "Successfully invested in goog, Successfully invested in aapl, "
            + "Successfully " + "invested in nflx, Successfully invested in Fb, ";

    assertEquals(expected, virtualStockModel.invest("investPortfolio", stockWeights,
            10000, 40, "11-03-2016 12:56"));


    /**
     * The invest method invests in the given stocks. The user just creates and adds the stocks.
     * Using the invest method buys shares for those companies mentioned by him.
     * Checking the investment using display portfolio method.
     */

    virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.addStock("investPortfolio", "Fb");
    virtualStockModel.addStock("investPortfolio", "aapl");
    virtualStockModel.addStock("investPortfolio", "nflx");
    virtualStockModel.addStock("investPortfolio", "goog");

    stockWeights = new HashMap<>();

    stockWeights.put("Fb", 25.00);
    stockWeights.put("aapl", 25.00);
    stockWeights.put("nflx", 25.00);
    stockWeights.put("goog", 25.00);

    expected = "Portfolio Name: INVESTPORTFOLIO\n"
            + "StockTickerSymbol     NumberofShares         PriceofUnitShare      "
            + "BuyDate         Commission Fee\n"
            + "GOOG                      3                    726.82             "
            + "11-03-2016         40.00     \n"
            + "AAPL                      24                   102.26             "
            + "11-03-2016         40.00     \n"
            + "NFLX                      25                   97.66              "
            + "11-03-2016         40.00     \n"
            + "FB                        22                   109.41             "
            + "11-03-2016         40.00     ";

    virtualStockModel.invest("investPortfolio", stockWeights,
            10000, 40, "11-03-2016 12:56");

    assertEquals(expected, virtualStockModel.displayPortfolio("investPortfolio"));

    /**
     * The invest method invests in the given stocks. The user just creates and adds the stocks.
     * Using the invest method buys shares for those companies mentioned by him.
     * Checking the investment using total cost basis.
     */

    virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.addStock("investPortfolio", "Fb");
    virtualStockModel.addStock("investPortfolio", "aapl");
    virtualStockModel.addStock("investPortfolio", "nflx");
    virtualStockModel.addStock("investPortfolio", "goog");

    stockWeights = new HashMap<>();

    stockWeights.put("Fb", 25.00);
    stockWeights.put("aapl", 25.00);
    stockWeights.put("nflx", 25.00);
    stockWeights.put("goog", 25.00);

    virtualStockModel.invest("investPortfolio", stockWeights,
            10000, 40, "11-03-2016 12:56");

    assertEquals(9643.220000000001,
            virtualStockModel.getTotalCostBasis("investPortfolio",
                    "11-03-2016"), 0.001);


    /**
     * The invest method invests in the given stocks. The user just creates and adds the stocks.
     * Using the invest method buys shares for those companies mentioned by him.
     * Checking the investment using total value on a particular day.
     */

    virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.addStock("investPortfolio", "Fb");
    virtualStockModel.addStock("investPortfolio", "aapl");
    virtualStockModel.addStock("investPortfolio", "nflx");
    virtualStockModel.addStock("investPortfolio", "goog");

    stockWeights = new HashMap<>();

    stockWeights.put("Fb", 25.00);
    stockWeights.put("aapl", 25.00);
    stockWeights.put("nflx", 25.00);
    stockWeights.put("goog", 25.00);

    virtualStockModel.invest("investPortfolio", stockWeights,
            10000, 40, "11-03-2016 12:56");

    assertEquals(20160.7,
            virtualStockModel.getTotalValue("investPortfolio",
                    "11-03-2018"), 0.001);

    /**
     * If suppose for one of the stocks the amount is insufficient to buy even one share of the
     * stock, then that stock is bought and a message is appended to the investment summary,
     * stating that no shares can be bought for that ticker symbol.
     */

    virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.addStock("investPortfolio", "Fb");
    virtualStockModel.addStock("investPortfolio", "aapl");
    virtualStockModel.addStock("investPortfolio", "nflx");
    virtualStockModel.addStock("investPortfolio", "goog");

    stockWeights = new HashMap<>();

    stockWeights.put("Fb", 25.00);
    stockWeights.put("aapl", 25.00);
    stockWeights.put("nflx", 25.00);
    stockWeights.put("goog", 25.00);

    expected = "Could not buy goog due to insufficient amount, Successfully invested "
            + "in aapl, " + "Successfully invested in nflx, Successfully invested in Fb, ";

    assertEquals(expected, virtualStockModel.invest("investPortfolio", stockWeights,
            1000, 40, "11-03-2016 12:56"));


    /**
     * Checking if one of the stocks can be with 0 percentage and also others can be of varying
     * weights.
     */

    virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.addStock("investPortfolio", "Fb");
    virtualStockModel.addStock("investPortfolio", "aapl");
    virtualStockModel.addStock("investPortfolio", "nflx");
    virtualStockModel.addStock("investPortfolio", "goog");

    stockWeights = new HashMap<>();

    stockWeights.put("Fb", 56.00);
    stockWeights.put("aapl", 24.50);
    stockWeights.put("nflx", 0.00);
    stockWeights.put("goog", 19.50);


    virtualStockModel.invest("investPortfolio", stockWeights,
            1000, 40, "11-03-2016 12:56");

    assertEquals(831.5699999999999, virtualStockModel
            .getTotalCostBasis("investPortfolio",
                    "12-03-2016"), 0.001);


    /**
     * Checking if only one of the stocks can be with 100 percentage and others can be of with zero
     * weights.
     */


    virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.addStock("investPortfolio", "Fb");
    virtualStockModel.addStock("investPortfolio", "aapl");
    virtualStockModel.addStock("investPortfolio", "nflx");
    virtualStockModel.addStock("investPortfolio", "goog");

    stockWeights = new HashMap<>();

    stockWeights.put("Fb", 0.00);
    stockWeights.put("aapl", 0.00);
    stockWeights.put("nflx", 0.00);
    stockWeights.put("goog", 100.00);


    virtualStockModel.invest("investPortfolio", stockWeights,
            1000, 50, "11-03-2016 12:56");

    assertEquals(776.82, virtualStockModel
            .getTotalCostBasis("investPortfolio",
                    "12-03-2016"), 0.001);


    /**
     * Checking invest method can be applied to a portfolio with existing stocks as well using
     * total cost basis.
     */

    virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.createPortfolio("invest");

    virtualStockModel.buySharesOfStock("goog", "invest",
            1000, "11-02-2016 10:00", 70);

    virtualStockModel.buySharesOfStock("msft", "invest",
            1000, "11-02-2016 10:00", 70);

    stockWeights = new HashMap<>();

    stockWeights.put("msft", 63.62);
    stockWeights.put("goog", 36.22);

    virtualStockModel.invest("invest", stockWeights,
            10000, 50, "11-03-2016 12:56");

    assertEquals(11139.52, virtualStockModel
            .getTotalCostBasis("invest",
                    "12-03-2016"), 0.001);

    /**
     * Checking invest method can be applied to a portfolio with existing stocks as well using
     * display portfolio.
     */

    virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.createPortfolio("invest");

    virtualStockModel.buySharesOfStock("goog", "invest",
            1000, "11-02-2016 10:00", 70);

    virtualStockModel.buySharesOfStock("msft", "invest",
            1000, "11-02-2016 10:00", 70);

    stockWeights = new HashMap<>();

    stockWeights.put("msft", 63.62);
    stockWeights.put("goog", 36.22);

    virtualStockModel.invest("invest", stockWeights,
            10000, 50, "11-03-2016 12:56");

    expected = "Portfolio Name: INVEST\n"
            + "StockTickerSymbol     NumberofShares         PriceofUnitShare      "
            +
            "BuyDate         Commission Fee\n"
            + "GOOG                      1                    683.11             "
            +
            "11-02-2016         70.00     \n"
            + "MSFT                      20                   49.69              "
            +
            "11-02-2016         70.00     \n"
            + "GOOG                      4                    726.82             "
            +
            "11-03-2016         50.00     \n"
            + "MSFT                      119                  53.07              "
            + "11-03-2016         50.00     ";

    assertEquals(expected, virtualStockModel
            .displayPortfolio("invest"));


    /**
     * Checking Dollar cost averaging method, portfolio should not exist previously.
     * Throws IllegalArgumentException.
     */

    virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.createPortfolio("invest");

    stockWeights = new HashMap<>();

    stockWeights.put("msft", 63.62);
    stockWeights.put("goog", 36.22);


    try {
      virtualStockModel.dollarCostAveraging("invest", stockWeights,
              2000, 30, "12-08-2016",
              "12-08-2017", 30.00);
      fail("Above line should have thrown exception");
    } catch (IllegalArgumentException e) {
      //Do Nothing
    }


    /**
     * Portfolio name cannot be null.
     * Throws IllegalArgumentException.
     */


    virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.createPortfolio("invest");

    stockWeights = new HashMap<>();

    stockWeights.put("msft", 63.62);
    stockWeights.put("goog", 36.22);


    try {
      virtualStockModel.dollarCostAveraging(null, stockWeights,
              2000, 30, "12-08-2016",
              "12-08-2017", 30.00);
      fail("Above line should have thrown exception");
    } catch (IllegalArgumentException e) {
      //Do Nothing
    }


    /**
     * Portfolio name cannot be empty string.
     * Throws IllegalArgumentException.
     */

    virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.createPortfolio("invest");

    stockWeights = new HashMap<>();

    stockWeights.put("msft", 63.62);
    stockWeights.put("goog", 36.22);


    try {
      virtualStockModel.dollarCostAveraging("", stockWeights,
              2000, 30, "12-08-2016",
              "12-08-2017", 30.00);
      fail("Above line should have thrown exception");
    } catch (IllegalArgumentException e) {
      //Do Nothing
    }

    /**
     * The interval between the investments cannot be less than one day.
     * Throws IllegalArgumentException.
     */

    virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.createPortfolio("invest");

    stockWeights = new HashMap<>();

    stockWeights.put("msft", 63.62);
    stockWeights.put("goog", 36.22);


    try {
      virtualStockModel.dollarCostAveraging("myPortfolio", stockWeights,
              2000, 0, "12-08-2016",
              "12-08-2017", 30.00);
      fail("Above line should have thrown exception");
    } catch (IllegalArgumentException e) {
      //Do Nothing
    }


    /**
     * The interval cannot be negative.
     * Throws IllegalArgumentException.
     */


    virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.createPortfolio("invest");

    stockWeights = new HashMap<>();

    stockWeights.put("msft", 63.62);
    stockWeights.put("goog", 36.22);


    try {
      virtualStockModel.dollarCostAveraging("myPortfolio", stockWeights,
              2000, -5, "12-08-2016",
              "12-08-2017", 30.00);
      fail("Above line should have thrown exception");
    } catch (IllegalArgumentException e) {
      //Do Nothing
    }

    /**
     * The amount to be invested in the Dollar Cost averaging method cannot be negative.
     * Throws IllegalArgumentException.
     */


    virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.createPortfolio("invest");

    stockWeights = new HashMap<>();

    stockWeights.put("msft", 63.62);
    stockWeights.put("goog", 36.22);


    try {
      virtualStockModel.dollarCostAveraging("myPortfolio", stockWeights,
              -2000, 5, "12-08-2016",
              "12-08-2017", 30.00);
      fail("Above line should have thrown exception");
    } catch (IllegalArgumentException e) {
      //Do Nothing
    }


    /**
     * The sum of the percentages of the ticker symbols must be between 99 and 100.
     * Throws IllegalArgumentException.
     */


    virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.createPortfolio("invest");

    stockWeights = new HashMap<>();

    stockWeights.put("msft", 63.62);
    stockWeights.put("goog", 30.00);


    try {
      virtualStockModel.dollarCostAveraging("myPortfolio", stockWeights,
              2000, 5, "12-08-2016",
              "12-08-2017", 30.00);
      fail("Above line should have thrown exception");
    } catch (IllegalArgumentException e) {
      //Do Nothing
    }


    /**
     * The ticker symbols cannot be empty string.
     * Throws IllegalArgumentException.
     */

    virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.createPortfolio("invest");

    stockWeights = new HashMap<>();

    stockWeights.put("", 63.62);
    stockWeights.put("goog", 36.30);


    try {
      virtualStockModel.dollarCostAveraging("myPortfolio", stockWeights,
              2000, 5, "12-08-2016",
              "12-08-2017", 30.00);
      fail("Above line should have thrown exception");
    } catch (IllegalArgumentException e) {
      //Do Nothing
    }


    virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.createPortfolio("invest");

    stockWeights = new HashMap<>();

    stockWeights.put(null, 63.62);
    stockWeights.put("goog", 36.30);

    try {
      virtualStockModel.dollarCostAveraging("myPortfolio", stockWeights,
              2000, 5, "12-08-2016",
              "12-08-2017", 30.00);
      fail("Above line should have thrown exception");
    } catch (IllegalArgumentException e) {
      //Do Nothing
    }


    /**
     * The start date cannot be null.
     * Throws IllegalArgumentException.
     */

    virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.createPortfolio("invest");

    stockWeights = new HashMap<>();

    stockWeights.put("msft", 63.62);
    stockWeights.put("goog", 36.30);

    try {
      virtualStockModel.dollarCostAveraging("myPortfolio", stockWeights,
              2000, 5, null,
              "12-08-2017", 30.00);
      fail("Above line should have thrown exception");
    } catch (IllegalArgumentException e) {
      //Do Nothing
    }


    /**
     * The start date cannot be empty string.
     * Throws IllegalArgumentException.
     */

    virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.createPortfolio("invest");

    stockWeights = new HashMap<>();

    stockWeights.put("msft", 63.62);
    stockWeights.put("goog", 36.30);

    try {
      virtualStockModel.dollarCostAveraging("myPortfolio", stockWeights,
              2000, 5, "",
              "12-08-2017", 30.00);
      fail("Above line should have thrown exception");
    } catch (IllegalArgumentException e) {
      //Do Nothing
    }


    /**
     * The end date cannot be null string.
     * Throws IllegalArgumentException.
     */


    virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.createPortfolio("invest");

    stockWeights = new HashMap<>();

    stockWeights.put("msft", 63.62);
    stockWeights.put("goog", 36.30);

    try {
      virtualStockModel.dollarCostAveraging("myPortfolio", stockWeights,
              2000, 5, "12-08-2016",
              null, 30.00);
      fail("Above line should have thrown exception");
    } catch (IllegalArgumentException e) {
      //Do Nothing
    }


    /**
     * If the end date is empty, then the end date is taken as the current date.
     * Checking the DollarCost Average method using display portfolio, the two stocks are bought
     * at two dates.
     */


    virtualStockModel = new VirtualStockModel(dataSource);


    stockWeights = new HashMap<>();

    stockWeights.put("msft", 63.62);
    stockWeights.put("goog", 36.30);

    virtualStockModel.dollarCostAveraging("myPortfolio", stockWeights,
            5000, 300, "07-11-2017",
            "", 30.00);

    expected = "Portfolio Name: MYPORTFOLIO\n"
            + "StockTickerSymbol     NumberofShares         PriceofUnitShare     "
            + " BuyDate         Commission Fee\n"
            + "GOOG                      1                    1033.33            "
            + "07-11-2017         30.00     \n"
            + "MSFT                      37                   84.27              "
            + "07-11-2017         30.00     \n"
            + "GOOG                      1                    1197.00            "
            + "04-09-2018         30.00     \n" + "MSFT                      28                   "
            + "111.71             04-09-2018         30.00     ";

    assertEquals(expected, virtualStockModel.displayPortfolio("myPortfolio"));


    /**
     * If the end date is empty, then the end date is taken as the current date.
     * Checking DollarCostAverage method using total cost basis.
     */


    virtualStockModel = new VirtualStockModel(dataSource);


    stockWeights = new HashMap<>();

    stockWeights.put("msft", 63.62);
    stockWeights.put("goog", 36.30);

    virtualStockModel.dollarCostAveraging("myPortfolio", stockWeights,
            5000, 300, "07-11-2017",
            "", 30.00);

    assertEquals(8596.199999999999, virtualStockModel
            .getTotalCostBasis("myPortfolio", "27-11-2018"), 0.001);


    /**
     * If the end date is empty, then the end date is taken as the current date.
     * Checking DollarCostAverage method using total cost basis in the middle of the investment
     * period.
     */


    virtualStockModel = new VirtualStockModel(dataSource);


    stockWeights = new HashMap<>();

    stockWeights.put("msft", 63.62);
    stockWeights.put("goog", 36.30);

    virtualStockModel.dollarCostAveraging("myPortfolio", stockWeights,
            5000, 300, "07-11-2017",
            "", 30.00);

    assertEquals(4211.32, virtualStockModel
            .getTotalCostBasis("myPortfolio", "12-12-2017"), 0.001);


    /**
     * The start date cannot be in the future.
     * Throws IllegalArgumentException.
     */


    virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.createPortfolio("invest");

    stockWeights = new HashMap<>();

    stockWeights.put("msft", 63.62);
    stockWeights.put("goog", 36.30);

    try {
      virtualStockModel.dollarCostAveraging("myPortfolio", stockWeights,
              2000, 5, "12-01-2019",
              "12-02-2020", 30.00);
      fail("Above line should have thrown exception");
    } catch (IllegalArgumentException e) {
      //Do Nothing
    }


    /**
     * The start date cannot be after the end date.
     * Throws IllegalArgumentException.
     */


    virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.createPortfolio("invest");

    stockWeights = new HashMap<>();

    stockWeights.put("msft", 63.62);
    stockWeights.put("goog", 36.30);

    try {
      virtualStockModel.dollarCostAveraging("myPortfolio", stockWeights,
              2000, 5, "27-03-2018",
              "12-02-2018", 30.00);
      fail("Above line should have thrown exception");
    } catch (IllegalArgumentException e) {
      //Do Nothing
    }


    /**
     * Checking for total value in the middle of the investment period.
     */


    virtualStockModel = new VirtualStockModel(dataSource);


    stockWeights = new HashMap<>();

    stockWeights.put("msft", 63.62);
    stockWeights.put("goog", 36.30);

    virtualStockModel.dollarCostAveraging("myPortfolio", stockWeights,
            5000, 300, "07-11-2017",
            "", 30.00);

    assertEquals(4206.9400000000005, virtualStockModel.getTotalValue(
            "myPortfolio", "12-12-2017"), 0.001);


    /**
     * Checking for total value at the end of the investment period.
     */


    virtualStockModel = new VirtualStockModel(dataSource);


    stockWeights = new HashMap<>();

    stockWeights.put("msft", 63.62);
    stockWeights.put("goog", 36.30);

    virtualStockModel.dollarCostAveraging("myPortfolio", stockWeights,
            5000, 300, "07-11-2017",
            "", 30.00);

    assertEquals(8777.369999999999, virtualStockModel.getTotalValue(
            "myPortfolio", "21-11-2018"), 0.001);


    /**
     * Checking for dollar cost average method where investment day falls on a holiday.
     * The investment happens on the next business day.
     */


    virtualStockModel = new VirtualStockModel(dataSource);


    stockWeights = new HashMap<>();

    stockWeights.put("Fb", 25.00);
    stockWeights.put("aapl", 25.00);
    stockWeights.put("nflx", 25.00);
    stockWeights.put("goog", 25.00);

    virtualStockModel.dollarCostAveraging("myPortfolio", stockWeights,
            5000, 4, "05-05-2018",
            "07-05-2018", 30.00);

    expected = "Portfolio Name: MYPORTFOLIO\n"
            + "StockTickerSymbol     NumberofShares         PriceofUnitShare      "
            + "BuyDate         Commission Fee\n"
            + "GOOG                      1                    1054.79            "
            + "07-05-2018         30.00     \n"
            + "AAPL                      6                    185.16             "
            + "07-05-2018         30.00     \n"
            + "NFLX                      3                    326.26             "
            + "07-05-2018         30.00     \n"
            + "FB                        7                    177.97             "
            + "07-05-2018         30.00     ";


    assertEquals(expected, virtualStockModel.displayPortfolio("myPortfolio"));


    /**
     * Start date falls on a holiday and takes the next day. Checking the total cost basis.
     */

    virtualStockModel = new VirtualStockModel(dataSource);
    stockWeights = new HashMap<>();

    stockWeights.put("Fb", 25.00);
    stockWeights.put("aapl", 25.00);
    stockWeights.put("nflx", 25.00);
    stockWeights.put("goog", 25.00);

    virtualStockModel.dollarCostAveraging("myPortfolio", stockWeights,
            5000, 4, "05-05-2018",
            "07-05-2018", 30.00);

    assertEquals(4510.32, virtualStockModel.getTotalCostBasis("myPortfolio",
            "08-05-2018"), 0.001);


    /**
     * Once Dollar cost averaging strategy is set on a portfolio, no further stocks can be
     * added to that portfolio. It is exclusive to the strategy and hence throws
     * UnsupportedOperationException.
     */


    virtualStockModel = new VirtualStockModel(dataSource);


    stockWeights = new HashMap<>();

    stockWeights.put("Fb", 25.00);
    stockWeights.put("aapl", 25.00);
    stockWeights.put("nflx", 25.00);
    stockWeights.put("goog", 25.00);

    virtualStockModel.dollarCostAveraging("myPortfolio", stockWeights,
            5000, 4, "05-05-2018",
            "07-05-2018", 30.00);

    try {
      virtualStockModel.buySharesOfStock("goog", "myPortfolio",
              2000, "07-05-2018 12:00");
      fail("Above line should have thrown exception");
    } catch (UnsupportedOperationException e) {
      //Do Nothing
    }


    /**
     * Testing Dollar cost averaging method for 1 year with 60 days as investment frequency.
     * Checking using the total cost basis.
     */


    virtualStockModel = new VirtualStockModel(dataSource);


    stockWeights = new HashMap<>();

    stockWeights.put("Fb", 25.00);
    stockWeights.put("aapl", 25.00);
    stockWeights.put("nflx", 25.00);
    stockWeights.put("goog", 25.00);

    virtualStockModel.dollarCostAveraging("myPortfolio", stockWeights,
            5000, 60, "05-05-2017",
            "07-05-2018", 30.00);

    assertEquals(32543.75, virtualStockModel.getTotalCostBasis("myPortfolio",
            "10-05-2018"), 0.001);


    /**
     * Testing Dollar cost averaging method for 1 year with 60 days as investment frequency.
     * Checking using the total cost basis in the middle of the investment time period.
     */

    virtualStockModel = new VirtualStockModel(dataSource);


    stockWeights = new HashMap<>();

    stockWeights.put("Fb", 25.00);
    stockWeights.put("aapl", 25.00);
    stockWeights.put("nflx", 25.00);
    stockWeights.put("goog", 25.00);

    virtualStockModel.dollarCostAveraging("myPortfolio", stockWeights,
            5000, 60, "05-05-2017",
            "07-05-2018", 30.00);

    assertEquals(18302.34, virtualStockModel.getTotalCostBasis("myPortfolio",
            "02-12-2017"), 0.001);


    /**
     * Testing Dollar cost averaging method for 1 year with 60 days as investment frequency.
     * Checking using the total value in the middle of the investment time period.
     */

    virtualStockModel = new VirtualStockModel(dataSource);


    stockWeights = new HashMap<>();

    stockWeights.put("Fb", 25.00);
    stockWeights.put("aapl", 25.00);
    stockWeights.put("nflx", 25.00);
    stockWeights.put("goog", 25.00);

    virtualStockModel.dollarCostAveraging("myPortfolio", stockWeights,
            5000, 60, "05-05-2017",
            "07-05-2018", 30.00);

    assertEquals(19481.04, virtualStockModel.getTotalValue("myPortfolio",
            "01-12-2017"), 0.001);


    /**
     * Testing Dollar cost averaging method for 1 year with 60 days as investment frequency.
     * Checking using the total value at the end of the investment time period.
     */


    virtualStockModel = new VirtualStockModel(dataSource);


    stockWeights = new HashMap<>();

    stockWeights.put("Fb", 25.00);
    stockWeights.put("aapl", 25.00);
    stockWeights.put("nflx", 25.00);
    stockWeights.put("goog", 25.00);

    virtualStockModel.dollarCostAveraging("myPortfolio", stockWeights,
            5000, 60, "05-05-2017",
            "07-05-2018", 30.00);

    assertEquals(39362.38, virtualStockModel.getTotalValue("myPortfolio",
            "08-05-2018"), 0.001);


    /**
     * Invest method, first by adding just stocks (this allows the user to just add the stocks
     * without actually buying) and then investing.
     */


    virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.createPortfolio("invest");

    virtualStockModel.addStock("invest", "msft");
    virtualStockModel.addStock("invest", "goog");

    stockWeights = new HashMap<>();

    stockWeights.put("msft", 63.62);
    stockWeights.put("goog", 36.22);

    virtualStockModel.invest("invest", stockWeights,
            10000, 50, "11-03-2016 12:56");

    assertEquals(9322.61, virtualStockModel
            .getTotalCostBasis("invest",
                    "12-03-2016"), 0.001);


    /**
     * Invest method, first by adding just stocks (this allows the user to just add the stocks
     * without actually buying) and then investing. But if the user provides an invalid ticker
     * symbol, then throws IllegalArgumentException.
     */


    virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.createPortfolio("invest");


    try {
      virtualStockModel.addStock("invest", "apple");
      virtualStockModel.addStock("invest", "goog");
      fail("Above line should have thrown exception");
    } catch (IllegalArgumentException e) {
      //Do Nothing
    }


    /**
     * Testing Dollar cost averaging method (only one stock with 100) for 1 year with 60 days as
     * investment frequency. Checking using the total value at the end of the investment time
     * period.
     */

    virtualStockModel = new VirtualStockModel(dataSource);


    stockWeights = new HashMap<>();

    stockWeights.put("Fb", 0.00);
    stockWeights.put("aapl", 0.00);
    stockWeights.put("nflx", 0.00);
    stockWeights.put("goog", 100.00);

    virtualStockModel.dollarCostAveraging("myPortfolio", stockWeights,
            5000, 60, "05-05-2017",
            "07-05-2018", 30.00);

    assertEquals(30755.85, virtualStockModel.getTotalCostBasis(
            "myPortfolio", "08-05-2018"), 0.001);

    /**
     * Testing Dollar cost averaging method (each stock with different percenatges) for 1 year
     * with 60 days as investment frequency. Checking using the total value at the end of the
     * investment time period.
     */

    virtualStockModel = new VirtualStockModel(dataSource);


    stockWeights = new HashMap<>();

    stockWeights.put("Fb", 15.00);
    stockWeights.put("aapl", 30.67);
    stockWeights.put("nflx", 27.81);
    stockWeights.put("goog", 26.50);

    virtualStockModel.dollarCostAveraging("myPortfolio", stockWeights,
            5000, 60, "05-05-2017",
            "07-05-2018", 30.00);

    assertEquals(31481.32, virtualStockModel.getTotalCostBasis(
            "myPortfolio", "08-05-2018"), 0.001);


    /**
     * The sum of the percentages is over 100. Throws IllegalArgumentException.
     */

    virtualStockModel = new VirtualStockModel(dataSource);


    stockWeights = new HashMap<>();

    stockWeights.put("Fb", 15.00);
    stockWeights.put("aapl", 30.67);
    stockWeights.put("nflx", 27.81);
    stockWeights.put("goog", 27.50);


    try {
      virtualStockModel.dollarCostAveraging("myPortfolio", stockWeights,
              5000, 60, "05-05-2017",
              "07-05-2018", 30.00);
      fail("Above line should have thrown exception");
    } catch (IllegalArgumentException e) {
      //Do Nothing
    }

  }


}