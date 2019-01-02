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

public class VirtualStockModelTestUsingALPHAAPI {


  @Test
  public void testModel() {
    /**
     * Testing createPortfolio with one stock.
     */

    StringReader reader = new StringReader("");
    DataSourceInterface dataSource = DataSourceCreator.getDataSource("ALPHAAPI", reader);

    VirtualStockModelInterface virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.createPortfolio("MyFirstPortfolio");

    virtualStockModel.buySharesOfStock("GOOG", "MyFirstPortfolio",
            1853, "09-08-2017 12:56");

    String expectedString = "Portfolio Name: MYFIRSTPORTFOLIO\n"
            + "StockTickerSymbol     NumberofShares         PriceofUnitShare      BuyDate"
            + "         Commission Fee\n"
            + "GOOG                      2                    922.90             09-08-2017"
            + "         0.00      ";


    assertEquals(expectedString,
            virtualStockModel.displayPortfolio("MyFirstPortfolio"));

    /**
     * Testing createPortfolio with no stocks, empty portfolio.
     */

    virtualStockModel = new VirtualStockModel(dataSource);
    virtualStockModel.createPortfolio("MyFirstPortfolio");
    expectedString = "Portfolio Name: MYFIRSTPORTFOLIO\n"
            + "StockTickerSymbol     NumberofShares         PriceofUnitShare"
            + "      BuyDate         Commission Fee";


    assertEquals(expectedString,
            virtualStockModel.displayPortfolio("MyFirstPortfolio"));


    /**
     * Testing single portfolio with multiple stocks.
     */

    virtualStockModel = new VirtualStockModel(dataSource);
    virtualStockModel.createPortfolio("MyFirstPortfolio");
    virtualStockModel.buySharesOfStock("GOOG", "MyFirstPortfolio",
            1853, "09-08-2017 12:56");
    virtualStockModel.buySharesOfStock("GOOG", "MyFirstPortfolio",
            4700, "10-08-2017 10:30");
    virtualStockModel.buySharesOfStock("msft", "MyFirstPortfolio",
            5200, "10-11-2017 15:59");

    expectedString = "Portfolio Name: MYFIRSTPORTFOLIO\n"
            + "StockTickerSymbol     NumberofShares         PriceofUnitShare      BuyDate"
            + "         Commission Fee\n"
            + "GOOG                      2                    922.90             09-08-2017"
            + "         0.00      \n"
            + "GOOG                      5                    907.24             10-08-2017"
            + "         0.00      \n"
            + "MSFT                      62                   83.87              10-11-2017"
            + "         0.00      ";

    assertEquals(expectedString,
            virtualStockModel.displayPortfolio("MyFirstPortfolio"));

    /**
     * Testing multiple createPortfolio with multiple stocks.
     */

    virtualStockModel = new VirtualStockModel(dataSource);
    virtualStockModel.createPortfolio("MyFirstPortfolio");
    virtualStockModel.buySharesOfStock("GOOG", "MyFirstPortfolio",
            1853, "09-08-2017 12:56");
    virtualStockModel.buySharesOfStock("GOOG", "MyFirstPortfolio",
            4700, "10-08-2017 10:30");
    virtualStockModel.buySharesOfStock("msft", "MyFirstPortfolio",
            5200, "10-11-2017 15:59");

    virtualStockModel.createPortfolio("Second");
    virtualStockModel.buySharesOfStock("GOOG", "Second",
            1500, "10-08-2017 12:56");

    expectedString = "Portfolio Name : MYFIRSTPORTFOLIO\n"
            + "StockTickerSymbol     NumberofShares         PriceofUnitShare      BuyDate"
            + "         Commission Fee\n"
            + "GOOG                      2                    922.90             09-08-2017"
            + "         0.00      \n"
            + "GOOG                      5                    907.24             10-08-2017"
            + "         0.00      \n"
            + "MSFT                      62                   83.87              10-11-2017"
            + "         0.00      \n"
            + "Portfolio Name : SECOND\n"
            + "StockTickerSymbol     NumberofShares         PriceofUnitShare      BuyDate"
            + "         Commission Fee\n"
            + "GOOG                      1                    907.24             10-08-2017"
            + "         0.00      ";

    assertEquals(expectedString,
            virtualStockModel.displayAllPortfolios());


    /**
     * Testing IllegalArgumentException, when portfolio name already exists, case is ignored and
     * of if a portfolio with the same name exists, throws IllegalArgumentException.
     */

    virtualStockModel = new VirtualStockModel(dataSource);
    virtualStockModel.createPortfolio("MyFirstPortfolio");
    virtualStockModel.buySharesOfStock("GOOG", "MyFirstPortfolio",
            1900, "09-08-2017 12:56");
    virtualStockModel.buySharesOfStock("GOOG", "MyFirstPortfolio",
            1900, "10-08-2017 10:30");
    virtualStockModel.buySharesOfStock("msft", "MyFirstPortfolio",
            700, "10-11-2017 15:59");

    try {
      virtualStockModel.createPortfolio("MyFirstportfolio");
      fail("Above line should have thrown exception");
    } catch (IllegalArgumentException e) {
      //Do Nothing
    }

    /**
     * Testing if number of shares is as expected.
     */

    virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.createPortfolio("MyFirstPortfolio");

    virtualStockModel.buySharesOfStock("GOOG", "MyFirstPortfolio",
            1900, "09-08-2017 12:56");

    expectedString = "Portfolio Name: MYFIRSTPORTFOLIO\n"
            + "StockTickerSymbol     NumberofShares         PriceofUnitShare      BuyDate"
            + "         Commission Fee\n"
            + "GOOG                      2                    922.90             09-08-2017"
            + "         0.00      ";

    assertEquals(expectedString,
            virtualStockModel.displayPortfolio("MyFirstPortfolio"));

    /**
     * Share cannot be bought on an holiday - weekend.
     */

    virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.createPortfolio("MyFirstPortfolio");

    try {
      virtualStockModel.buySharesOfStock("GOOG", "MyFirstPortfolio",
              1900, "12-08-2017 12:56");
      fail("Above line should ahve thrown excetion");
    } catch (IllegalArgumentException e) {
      //Do Nothing
    }

    /**
     * User cannot buy shares before 9AM.
     */
    virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.createPortfolio("MyFirstPortfolio");

    try {
      virtualStockModel.buySharesOfStock("MSFT", "MyFirstPortfolio",
              500, "03-12-2007 08:59");
      fail("Above line should have thrown exception");
    } catch (IllegalArgumentException e) {
      //Do Nothing
    }

    /**
     * User cannot buy shares before 9AM.
     */

    virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.createPortfolio("MyFirstPortfolio");
    try {
      virtualStockModel.buySharesOfStock("MSFT", "MyFirstPortfolio",
              500, "03-12-2007 06:12");
      fail("Above line should have thrown exception");
    } catch (IllegalArgumentException e) {
      // DO Nothing
    }

    /**
     * User cannot buy shares at 4 P.M.
     */

    virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.createPortfolio("MyFirstPortfolio");
    try {
      virtualStockModel.buySharesOfStock("MSFT", "MyFirstPortfolio",
              500, "03-12-2007 16:00");
      fail("Above line should have thrown exception");
    } catch (IllegalArgumentException e) {
      // DO Nothing
    }

    /**
     * User cannot buy shares after 4 P.M.
     */

    virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.createPortfolio("MyFirstPortfolio");

    try {
      virtualStockModel.buySharesOfStock("MSFT", "MyFirstPortfolio",
              500, "03-12-2007 22:00");
      fail("Above line should have thrown exception");
    } catch (IllegalArgumentException e) {
      //Do Nothing
    }

    /**
     * Throws IllegalArgumentException when the ticker symbol does not exist.
     *testTickerSymbolDoesNotExist.
     */
    virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.createPortfolio("MyFirstPortfolio");

    try {
      virtualStockModel.buySharesOfStock("xyz", "MyFirstPortfolio",
              500, "03-12-2007 15:30");
    } catch (IllegalArgumentException e) {
      //Do Nothing
    }


    /**
     * Testing multiple portfolios with multiple stocks of different companies. Testing Display All
     * portfolios.
     */

    virtualStockModel = new VirtualStockModel(dataSource);
    virtualStockModel.createPortfolio("First");
    virtualStockModel.createPortfolio("Second");
    virtualStockModel.createPortfolio("Third");
    virtualStockModel.createPortfolio("Fourth");

    virtualStockModel.buySharesOfStock("GOOG", "first",
            2000, "19-07-2016 12:56");
    virtualStockModel.buySharesOfStock("GOOG", "first",
            1000, "07-12-2015 12:54");
    virtualStockModel.buySharesOfStock("msft", "first",
            3000, "07-01-1998 15:10");

    virtualStockModel.buySharesOfStock("GOOG", "second",
            2000, "09-12-2015 13:11");
    virtualStockModel.buySharesOfStock("AAPL", "second",
            1500, "06-02-2007 11:27");
    virtualStockModel.buySharesOfStock("TSLA", "second",
            700, "01-07-2010 09:34");

    virtualStockModel.buySharesOfStock("TSLA", "third",
            2000, "07-07-2010 12:56");
    virtualStockModel.buySharesOfStock("TSLA", "Third",
            1000, "06-07-2010 10:30");
    virtualStockModel.buySharesOfStock("TSLA", "third ",
            100, "02-07-2010 15:59");

    virtualStockModel.buySharesOfStock("MSFT", "fourth",
            500, "07-01-1998 12:56");

    String expectedResult = "Portfolio Name : FIRST\n"
            + "StockTickerSymbol     NumberofShares         PriceofUnitShare      BuyDate"
            + "         Commission Fee\n"
            + "GOOG                      2                    736.96             19-07-2016"
            + "         0.00      \n"
            + "GOOG                      1                    763.25             07-12-2015"
            + "         0.00      \n"
            + "MSFT                      23                   129.56             07-01-1998"
            + "         0.00      \n"
            + "Portfolio Name : SECOND\n"
            + "StockTickerSymbol     NumberofShares         PriceofUnitShare      BuyDate"
            + "         Commission Fee\n"
            + "GOOG                      2                    751.61             09-12-2015"
            + "         0.00      \n"
            + "AAPL                      17                   84.15              06-02-2007"
            + "         0.00      \n"
            + "TSLA                      31                   21.96              01-07-2010"
            + "         0.00      \n"
            + "Portfolio Name : THIRD\n"
            + "StockTickerSymbol     NumberofShares         PriceofUnitShare      BuyDate"
            + "         Commission Fee\n"
            + "TSLA                      126                  15.80              07-07-2010"
            + "         0.00      \n"
            + "TSLA                      62                   16.11              06-07-2010"
            + "         0.00      \n"
            + "TSLA                      5                    19.20              02-07-2010"
            + "         0.00      \n"
            + "Portfolio Name : FOURTH\n"
            + "StockTickerSymbol     NumberofShares         PriceofUnitShare      BuyDate"
            + "         Commission Fee\n"
            + "MSFT                      3                    129.56             07-01-1998"
            + "         0.00      ";

    assertEquals(expectedResult, virtualStockModel.displayAllPortfolios());

    /**
     * testSameStocksOnSameDate
     */
    virtualStockModel = new VirtualStockModel(dataSource);
    virtualStockModel.createPortfolio("First");
    virtualStockModel.createPortfolio("Second");
    virtualStockModel.createPortfolio("Third");
    virtualStockModel.createPortfolio("Fourth");

    virtualStockModel.buySharesOfStock("GOOG", "first",
            2000, "19-07-2016 12:56");
    virtualStockModel.buySharesOfStock("GOOG", "first",
            1000, "19-07-2016 12:56");
    virtualStockModel.buySharesOfStock("msft", "first",
            3000, "07-01-1998 15:10");

    expectedResult = "Portfolio Name : FIRST\n"
            + "StockTickerSymbol     NumberofShares         PriceofUnitShare      BuyDate"
            + "         Commission Fee\n"
            + "GOOG                      2                    736.96             19-07-2016"
            + "         0.00      \n"
            + "GOOG                      1                    736.96             19-07-2016"
            + "         0.00      \n"
            + "MSFT                      23                   129.56             07-01-1998"
            + "         0.00      \n"
            + "Portfolio Name : SECOND\n"
            + "StockTickerSymbol     NumberofShares         PriceofUnitShare      BuyDate"
            + "         Commission Fee\n"
            + "Portfolio Name : THIRD\n"
            + "StockTickerSymbol     NumberofShares         PriceofUnitShare      BuyDate"
            + "         Commission Fee\n"
            + "Portfolio Name : FOURTH\n"
            + "StockTickerSymbol     NumberofShares         PriceofUnitShare      BuyDate"
            + "         Commission Fee";

    assertEquals(expectedResult, virtualStockModel.displayAllPortfolios());

    /**
     * Throws IllegalArgumentException when the amount given is insufficient to buy at least one
     * share.
     */

    virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.createPortfolio("MyFirstPortfolio");

    try {
      virtualStockModel.buySharesOfStock("MSFT", "MyFirstPortfolio",
              10, "03-12-2007 15:30");
      fail("Above line should have thrown exception");
    } catch (IllegalArgumentException e) {
      //Do Nothing
    }

    /**
     * Throws IllegalArgumentException when the amount given is insufficient to buy at least one
     * share.
     */
    virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.createPortfolio("MyFirstPortfolio");

    try {
      virtualStockModel.buySharesOfStock("MSFT", "MyFirstPortfolio",
              0, "03-12-2007 15:30");
      fail("Above line should have thrown exception");
    } catch (IllegalArgumentException e) {
      //Do Nothing
    }

    /**
     * Amount cannot be negative.
     */
    virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.createPortfolio("MyFirstPortfolio");

    try {
      virtualStockModel.buySharesOfStock("MSFT", "MyFirstPortfolio",
              -99, "03-12-2007 15:30");
      fail("Above line should have thrown exception");
    } catch (IllegalArgumentException e) {
      //Do Nothing
    }

    /**
     * Date cannot be null or empty. test null date.
     */
    virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.createPortfolio("MyFirstPortfolio");

    try {
      virtualStockModel.buySharesOfStock("MSFT", "MyFirstPortfolio",
              999, null);
      fail("Above line should have thrown exception");
    } catch (IllegalArgumentException e) {
      //Do Nothing
    }

    /**
     * Date cannot be null or empty. test empty date.
     */
    virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.createPortfolio("MyFirstPortfolio");

    try {
      virtualStockModel.buySharesOfStock("MSFT", "MyFirstPortfolio",
              900, "");
      fail("Above line should have thrown exception");
    } catch (IllegalArgumentException e) {
      //Do Nothing
    }

    /**
     * Ticker symbol cannot be null or empty. Symbol is e,pty.
     */
    virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.createPortfolio("MyFirstPortfolio");

    try {
      virtualStockModel.buySharesOfStock("", "MyFirstPortfolio",
              1000, "03-12-2007 15:30");
      fail("Above line should have thrown exception");
    } catch (IllegalArgumentException e) {
      //Do Nothing
    }

    /**
     * Ticker symbol cannot be null or empty. ticker symbol is null.
     */
    virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.createPortfolio("MyFirstPortfolio");

    try {
      virtualStockModel.buySharesOfStock(null, "MyFirstPortfolio",
              1000, "03-12-2007 15:30");
      fail("Above line should have thrown exception");
    } catch (IllegalArgumentException e) {
      //Do Nothing
    }

    /**
     * Portfolio name cannot be null or empty. Portfolio name is null.
     */
    virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.createPortfolio("MyFirstPortfolio");

    try {
      virtualStockModel.buySharesOfStock("MSFT", null,
              1000, "03-12-2007 15:30");
      fail("Above line should have thrown exception");
    } catch (IllegalArgumentException e) {
      //Do Nothing
    }

    /**
     * Portfolio name cannot be null or empty. portfolio name is empty.
     */
    virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.createPortfolio("MyFirstPortfolio");
    try {
      virtualStockModel.buySharesOfStock("MSFT", "",
              1000, "03-12-2007 15:30");
      fail("Above line should have thrown exception");
    } catch (IllegalArgumentException e) {
      //Do Nothing.
    }

    /**
     * Testing totalCostBasis for a single stock.
     */

    virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.createPortfolio("MyFirstPortfolio");

    virtualStockModel.buySharesOfStock("GOOG", "MyFirstPortfolio",
            10000, "01-11-2016 12:30");

    assertEquals(9403.32,
            virtualStockModel.getTotalCostBasis("MyFirstPortfolio",
                    "03-11-2016"), 0.001);

    /**
     * Testing totalCostBasis for multiple stocks.
     */

    virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.createPortfolio("MyFirstPortfolio");

    virtualStockModel.buySharesOfStock("GOOG", "MyFirstPortfolio",
            10000, "01-11-2016 12:30");
    virtualStockModel.buySharesOfStock("GOOG", "MyFirstPortfolio",
            10000, "07-12-2016 10:44");
    virtualStockModel.buySharesOfStock("GOOG", "MyFirstPortfolio",
            10000, "27-02-2017 12:00");
    virtualStockModel.buySharesOfStock("AAPL", "MyFirstPortfolio",
            10000, "15-05-2018 10:43");
    virtualStockModel.buySharesOfStock("TSLA", "MyFirstPortfolio",
            10000, "24-08-2018 14:27");

    assertEquals(48174.88,
            virtualStockModel.getTotalCostBasis("MyFirstPortfolio",
                    "24-08-2018"), 0.001);
    /**
     * Testing totalCostBasis for no stocks bought before the date.
     */

    virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.createPortfolio("MyFirstPortfolio");

    virtualStockModel.buySharesOfStock("GOOG", "MyFirstPortfolio",
            10000, "01-11-2016 12:30");
    virtualStockModel.buySharesOfStock("GOOG", "MyFirstPortfolio",
            10000, "07-12-2016 10:44");
    virtualStockModel.buySharesOfStock("GOOG", "MyFirstPortfolio",
            10000, "27-02-2017 12:00");
    virtualStockModel.buySharesOfStock("AAPL", "MyFirstPortfolio",
            10000, "15-05-2018 10:43");
    virtualStockModel.buySharesOfStock("TSLA", "MyFirstPortfolio",
            10000, "24-08-2018 14:27");

    assertEquals(0.0,
            virtualStockModel.getTotalCostBasis("MyFirstPortfolio",
                    "01-01-1999"), 0.001);

    /**
     * Testing totalCostBasis for some stocks bought before the date.
     */
    virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.createPortfolio("MyFirstPortfolio");

    virtualStockModel.buySharesOfStock("GOOG", "MyFirstPortfolio",
            10000, "01-11-2016 12:30");
    virtualStockModel.buySharesOfStock("GOOG", "MyFirstPortfolio",
            10000, "07-12-2016 10:44");
    virtualStockModel.buySharesOfStock("GOOG", "MyFirstPortfolio",
            10000, "27-02-2017 12:00");
    virtualStockModel.buySharesOfStock("AAPL", "MyFirstPortfolio",
            10000, "15-05-2018 10:43");
    virtualStockModel.buySharesOfStock("TSLA", "MyFirstPortfolio",
            10000, "24-08-2018 14:27");

    assertEquals(28608.96,
            virtualStockModel.getTotalCostBasis("MyFirstPortfolio",
                    "28-02-2017"), 0.001);
    /**
     * If the given portfolio name does not exist total cost basis will throw exception.
     */

    virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.createPortfolio("MyFirstPortfolio");

    virtualStockModel.buySharesOfStock("GOOG", "MyFirstPortfolio",
            10000, "01-11-2016 12:30");
    virtualStockModel.buySharesOfStock("GOOG", "MyFirstPortfolio",
            10000, "07-12-2016 10:44");
    virtualStockModel.buySharesOfStock("GOOG", "MyFirstPortfolio",
            10000, "27-02-2017 12:00");
    virtualStockModel.buySharesOfStock("AAPL", "MyFirstPortfolio",
            10000, "15-05-2018 10:43");
    virtualStockModel.buySharesOfStock("TSLA", "MyFirstPortfolio",
            10000, "24-08-2018 14:27");


    try {
      virtualStockModel.getTotalCostBasis("second",
              "24-08-2018");
      fail("Above line should have thrown exception");
    } catch (IllegalArgumentException e) {
      //Do Nothing.
    }
    /**
     * If the date and time is not in the expected format throws IllegalArgumentException.
     * Time should be entered as two digits.
     */
    virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.createPortfolio("MyFirstPortfolio");

    virtualStockModel.buySharesOfStock("GOOG", "MyFirstPortfolio",
            10000, "01-11-2016 12:30");
    virtualStockModel.buySharesOfStock("GOOG", "MyFirstPortfolio",
            10000, "07-12-2016 10:44");
    virtualStockModel.buySharesOfStock("GOOG", "MyFirstPortfolio",
            10000, "27-02-2017 12:00");
    virtualStockModel.buySharesOfStock("AAPL", "MyFirstPortfolio",
            10000, "15-05-2018 10:43");
    try {
      virtualStockModel.buySharesOfStock("TSLA", "MyFirstPortfolio",
              10000, "24-08-2018 2:00");
      fail("Above line should have thrown exception");
    } catch (IllegalArgumentException e) {
      // DO nothing
    }

    /**
     * If the date and time is not in the expected format throws IllegalArgumentException.
     * For single digit day or time given with zero or for time 7:30 -> 07:30 and
     * date 4-2-2014 -> 04-02-2014.
     */
    virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.createPortfolio("MyFirstPortfolio");

    virtualStockModel.buySharesOfStock("GOOG", "MyFirstPortfolio",
            10000, "01-11-2016 12:30");
    virtualStockModel.buySharesOfStock("GOOG", "MyFirstPortfolio",
            10000, "07-12-2016 10:44");
    virtualStockModel.buySharesOfStock("GOOG", "MyFirstPortfolio",
            10000, "27-02-2017 12:00");
    virtualStockModel.buySharesOfStock("AAPL", "MyFirstPortfolio",
            10000, "15-05-2018 10:43");
    try {
      virtualStockModel.buySharesOfStock("TSLA", "MyFirstPortfolio",
              10000, "9-08-2018 02:00");
      fail("Above line should have thrown exception");
    } catch (IllegalArgumentException e) {
      //DO Nothing
    }

    /**
     * User cannot buy on a future date. Throws IllegalArgumentException.
     */
    virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.createPortfolio("MyFirstPortfolio");

    try {
      virtualStockModel.buySharesOfStock("GOOG", "MyFirstPortfolio",
              10000, "01-11-2019 12:30");
      fail("Above line should have thrown exception");
    } catch (IllegalArgumentException e) {
      //DO Nothing
    }

    /**
     * Testing get the totalPortfolioWorth on a certain date.
     */

    virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.createPortfolio("MyFirstPortfolio");

    virtualStockModel.buySharesOfStock("GOOG", "MyFirstPortfolio",
            10000, "01-11-2016 12:30");
    virtualStockModel.buySharesOfStock("GOOG", "MyFirstPortfolio",
            10000, "07-12-2016 10:44");
    virtualStockModel.buySharesOfStock("GOOG", "MyFirstPortfolio",
            10000, "27-02-2017 12:00");
    virtualStockModel.buySharesOfStock("AAPL", "MyFirstPortfolio",
            10000, "15-05-2018 10:43");
    virtualStockModel.buySharesOfStock("TSLA", "MyFirstPortfolio",
            10000, "24-08-2018 14:00");

    assertEquals(62872.24, virtualStockModel.getTotalValue(
            "MyFirstPortfolio", "28-09-2018"), 0.001);

    /**
     * Testing get the totalPortfolioWorth on a Holiday such that it returns the worth based on the
     * last working day before the holiday.
     */

    virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.createPortfolio("MyFirstPortfolio");

    virtualStockModel.buySharesOfStock("GOOG", "MyFirstPortfolio",
            10000, "01-11-2016 12:30");
    virtualStockModel.buySharesOfStock("GOOG", "MyFirstPortfolio",
            10000, "07-12-2016 10:44");
    virtualStockModel.buySharesOfStock("GOOG", "MyFirstPortfolio",
            10000, "27-02-2017 12:00");
    virtualStockModel.buySharesOfStock("AAPL", "MyFirstPortfolio",
            10000, "15-05-2018 10:43");
    virtualStockModel.buySharesOfStock("TSLA", "MyFirstPortfolio",
            10000, "24-08-2018 14:00");

    assertEquals(62872.24, virtualStockModel.getTotalValue(
            "MyFirstPortfolio", "30-09-2018"), 0.001);

    /**
     * Testing get the totalPortfolioWorth on a date when only certain stocks have their unit share
     * price. In that case, for the stocks that don't have valuen for that date, just 0 is added to
     * the total value. Therefore only total worth of stocks that have value on that date is
     * returned.
     */

    virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.createPortfolio("MyFirstPortfolio");

    virtualStockModel.buySharesOfStock("GOOG", "MyFirstPortfolio",
            10000, "01-11-2016 12:30");
    virtualStockModel.buySharesOfStock("GOOG", "MyFirstPortfolio",
            10000, "07-12-2016 10:44");
    virtualStockModel.buySharesOfStock("GOOG", "MyFirstPortfolio",
            10000, "27-02-2017 12:00");
    virtualStockModel.buySharesOfStock("AAPL", "MyFirstPortfolio",
            10000, "15-05-2018 10:43");
    virtualStockModel.buySharesOfStock("TSLA", "MyFirstPortfolio",
            10000, "24-08-2018 14:00");

    assertEquals(62872.24, virtualStockModel.getTotalValue(
            "MyFirstPortfolio", "30-09-2018"), 0.001);

    /**
     * Testing get the totalPortfolioWorth is when no stocks are bought and added to the portfolio
     * yet.
     */

    virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.createPortfolio("MyFirstPortfolio");

    virtualStockModel.buySharesOfStock("GOOG", "MyFirstPortfolio",
            10000, "01-11-2016 12:30");
    virtualStockModel.buySharesOfStock("GOOG", "MyFirstPortfolio",
            10000, "07-12-2016 10:44");
    virtualStockModel.buySharesOfStock("GOOG", "MyFirstPortfolio",
            10000, "27-02-2017 12:00");
    virtualStockModel.buySharesOfStock("AAPL", "MyFirstPortfolio",
            10000, "15-05-2018 10:43");
    virtualStockModel.buySharesOfStock("TSLA", "MyFirstPortfolio",
            10000, "24-08-2018 14:00");

    assertEquals(0.0, virtualStockModel.getTotalValue(
            "MyFirstPortfolio", "30-09-2014"), 0.001);


    /**
     * Testing get the totalPortfolioWorth is when zero stocks are bought.
     */

    virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.createPortfolio("MyFirstPortfolio");

    virtualStockModel.buySharesOfStock("GOOG", "MyFirstPortfolio",
            10000, "01-11-2016 12:30");
    virtualStockModel.buySharesOfStock("GOOG", "MyFirstPortfolio",
            10000, "07-12-2016 10:44");
    virtualStockModel.buySharesOfStock("GOOG", "MyFirstPortfolio",
            10000, "27-02-2017 12:00");
    virtualStockModel.buySharesOfStock("AAPL", "MyFirstPortfolio",
            10000, "15-05-2018 10:43");
    virtualStockModel.buySharesOfStock("TSLA", "MyFirstPortfolio",
            10000, "24-08-2018 14:00");


    assertEquals(18508.56, virtualStockModel.getTotalValue(
            "MyFirstPortfolio", "07-12-2016"), 0.001);

    /**
     * Testing get the totalPortfolioWorth is when only some stocks are bought.
     */
    virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.createPortfolio("MyFirstPortfolio");

    virtualStockModel.buySharesOfStock("GOOG", "MyFirstPortfolio",
            10000, "01-11-2016 12:30");
    virtualStockModel.buySharesOfStock("GOOG", "MyFirstPortfolio",
            10000, "07-12-2016 10:44");
    virtualStockModel.buySharesOfStock("GOOG", "MyFirstPortfolio",
            10000, "27-02-2017 12:00");
    virtualStockModel.buySharesOfStock("AAPL", "MyFirstPortfolio",
            10000, "15-05-2018 10:43");
    virtualStockModel.buySharesOfStock("TSLA", "MyFirstPortfolio",
            10000, "24-08-2018 14:00");

    assertEquals(48917.26, virtualStockModel.getTotalValue(
            "MyFirstPortfolio", "16-05-2018"), 0.001);

    /**
     * Testing get the totalPortfolioWorth is zero when no stocks are bought.
     */

    virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.createPortfolio("MyFirstPortfolio");

    virtualStockModel.buySharesOfStock("GOOG", "MyFirstPortfolio",
            10000, "01-11-2016 12:30");
    virtualStockModel.buySharesOfStock("GOOG", "MyFirstPortfolio",
            10000, "07-12-2016 10:44");
    virtualStockModel.buySharesOfStock("GOOG", "MyFirstPortfolio",
            10000, "27-02-2017 12:00");
    virtualStockModel.buySharesOfStock("AAPL", "MyFirstPortfolio",
            10000, "15-05-2018 10:43");
    virtualStockModel.buySharesOfStock("TSLA", "MyFirstPortfolio",
            10000, "24-08-2018 14:00");

    assertEquals(0.0, virtualStockModel.getTotalValue(
            "MyFirstPortfolio", "27-10-2016"), 0.001);

    /**
     * exception when date is entered in invalid format for the totalValue method.
     */

    virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.createPortfolio("MyFirstPortfolio");

    virtualStockModel.buySharesOfStock("GOOG", "MyFirstPortfolio",
            10000, "01-11-2016 12:30");
    virtualStockModel.buySharesOfStock("GOOG", "MyFirstPortfolio",
            10000, "07-12-2016 10:44");
    virtualStockModel.buySharesOfStock("GOOG", "MyFirstPortfolio",
            10000, "27-02-2017 12:00");
    virtualStockModel.buySharesOfStock("AAPL", "MyFirstPortfolio",
            10000, "15-05-2018 10:43");
    virtualStockModel.buySharesOfStock("TSLA", "MyFirstPortfolio",
            10000, "24-08-2018 14:00");
    try {
      assertEquals(0.0, virtualStockModel.getTotalValue(
              "MyFirstPortfolio", "27-10-2016 2:00"), 0.001);
      fail("Above line should have thrown exception");
    } catch (IllegalArgumentException e) {
      //Do Nothing
    }

    /**
     * Date should be entered in the specified format.
     */

    virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.createPortfolio("MyFirstPortfolio");

    virtualStockModel.buySharesOfStock("GOOG", "MyFirstPortfolio",
            10000, "01-11-2016 12:30");
    virtualStockModel.buySharesOfStock("GOOG", "MyFirstPortfolio",
            10000, "07-12-2016 10:44");
    virtualStockModel.buySharesOfStock("GOOG", "MyFirstPortfolio",
            10000, "27-02-2017 12:00");
    virtualStockModel.buySharesOfStock("AAPL", "MyFirstPortfolio",
            10000, "15-05-2018 10:43");
    virtualStockModel.buySharesOfStock("TSLA", "MyFirstPortfolio",
            10000, "24-08-2018 14:00");

    try {
      virtualStockModel.getTotalValue(
              "MyFirstPortfolio", "27-10 01:00");
      fail("Above line should have thrown exception");
    } catch (IllegalArgumentException e) {
      //Do Nothing
    }

    /**
     * Testing buySharesOfStock throws IllegalArgumentException when given future date.
     */

    virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.createPortfolio("MyFirstPortfolio");

    try {
      virtualStockModel.buySharesOfStock("GOOG", "MyFirstPortfolio",
              10000, "02-11-2020 12:30");
      fail("Above line should have thrown exception");
    } catch (IllegalArgumentException e) {
      //Do Nothing
    }

    /**
     * Testing getTotalWorth returns 0.0 when given future date.
     */

    virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.createPortfolio("MyFirstPortfolio");

    virtualStockModel.buySharesOfStock("GOOG", "MyFirstPortfolio",
            10000, "01-11-2016 12:30");
    virtualStockModel.buySharesOfStock("GOOG", "MyFirstPortfolio",
            10000, "07-12-2016 10:44");
    virtualStockModel.buySharesOfStock("GOOG", "MyFirstPortfolio",
            10000, "27-02-2017 12:00");
    virtualStockModel.buySharesOfStock("AAPL", "MyFirstPortfolio",
            10000, "15-05-2018 10:43");
    virtualStockModel.buySharesOfStock("TSLA", "MyFirstPortfolio",
            10000, "24-08-2018 14:00");

    assertEquals(0.0, virtualStockModel.getTotalValue(
            "MyFirstPortfolio", "27-10-2020"), 0.001);

    /**
     * Test create portfolio when given an empty name.
     */
    virtualStockModel = new VirtualStockModel(dataSource);
    try {
      virtualStockModel.createPortfolio("");
      fail("Above line should have thrown exception");
    } catch (IllegalArgumentException e) {
      //Do Nothing
    }

    /**
     * Test Create portfolio when given a null
     */
    virtualStockModel = new VirtualStockModel(dataSource);
    try {
      virtualStockModel.createPortfolio(null);
      fail("Above line should hae thrown exception");
    } catch (IllegalArgumentException e) {
      //Do Nothing
    }

    /**
     * When given to buy shares with a portfolio name that does not exist, then new portfolio
     * with the given name is created and stock is added to it.
     */

    virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.buySharesOfStock("GOOG", "MyFirstPortfolio",
            10000, "01-11-2016 12:30");

    String result = "Portfolio Name: MYFIRSTPORTFOLIO\n"
            + "StockTickerSymbol     NumberofShares         PriceofUnitShare      BuyDate"
            + "         Commission Fee\n"
            + "GOOG                      12                   783.61             01-11-2016"
            + "         0.00      ";

    assertEquals(result, virtualStockModel.displayPortfolio("myfirstportfolio"));

    /**
     * Shares cannot be bought on holidays - weekends and public holidays.
     */

    virtualStockModel = new VirtualStockModel(dataSource);

    try {
      virtualStockModel.buySharesOfStock("GOOG", "MyFirstPortfolio",
              10000, "10-11-2018 12:30");
      fail("Above line should have thrown exception");
    } catch (IllegalArgumentException e) {
      //Do Nothing
    }

    /**
     * Shares cannot be bought on holidays - weekends and public holidays.
     */

    virtualStockModel = new VirtualStockModel(dataSource);

    try {
      virtualStockModel.buySharesOfStock("GOOG", "MyFirstPortfolio",
              10000, "11-11-2018 12:30");
      fail("Above line should have thrown exception");
    } catch (IllegalArgumentException e) {
      //Do Nothing.
    }

    /**
     * Throws IllegalArugumentException if the date is new year.
     */

    virtualStockModel = new VirtualStockModel(dataSource);

    try {
      virtualStockModel.buySharesOfStock("GOOG", "MyFirstPortfolio",
              10000, "01-01-2018 12:30");
      fail("Above line should have thrown exception");
    } catch (IllegalArgumentException e) {
      //DO Nothing
    }


    /**
     * Throws IllegalArugumentException if the date is thanks giving.
     */
    virtualStockModel = new VirtualStockModel(dataSource);

    try {
      virtualStockModel.buySharesOfStock("GOOG", "MyFirstPortfolio",
              10000, "22-11-2018 12:30");
      fail("Above line should have thrown exception");
    } catch (IllegalArgumentException e) {
      //Do Nothing
    }


  }

  @Test
  public void testInvestInvalid6() {

    StringReader reader = new StringReader("");
    DataSourceInterface dataSource = DataSourceCreator.getDataSource("ALPHAAPI", reader);

    VirtualStockModelInterface virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.createPortfolio("MyFirstPortfolio");

    virtualStockModel.buySharesOfStock("msft", "mySecond",
            2500, "26-06-2015 12:56", 50);

    virtualStockModel.buySharesOfStock("tsla", "mySecond",
            2500, "28-07-2015 12:56", 50);

    Map<String, Double> stockWeights = new HashMap<>();


    stockWeights.put("MSFT", 25.00);
    stockWeights.put("TSLA", 12.50);

    try {
      virtualStockModel.invest("mySecond", stockWeights,
              10000, 40, "09-08-2017 12:56");
      fail("Above line should have thrown exception");
    } catch (IllegalArgumentException e) {
      //Do Nothing
    }
  }
}