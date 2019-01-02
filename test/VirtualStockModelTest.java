import org.junit.Test;

import java.io.StringReader;

import datasource.DataSourceCreator;
import datasource.DataSourceInterface;
import model.VirtualStockModel;
import model.VirtualStockModelInterface;

import static org.junit.Assert.assertEquals;

public class VirtualStockModelTest {

  /**
   * Testing createPortfolio with one stock.
   */
  @Test
  public void testCreatePortfolio() {

    String stockPrices = "GOOG,09-08-2017,234.67\n"
            + "MSFT,03-12-2007,106.0\n";

    StringReader reader = new StringReader(stockPrices);
    DataSourceInterface dataSource = DataSourceCreator.getDataSource("UserInput", reader);

    VirtualStockModelInterface virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.createPortfolio("MyFirstPortfolio");

    virtualStockModel.buySharesOfStock("GOOG", "MyFirstPortfolio",
            500, "09-08-2017 12:56");

    String expectedString = "Portfolio Name: MYFIRSTPORTFOLIO\n"
            + "StockTickerSymbol     NumberofShares         PriceofUnitShare      BuyDate"
            + "         Commission Fee\n"
            + "GOOG                      2                    234.67             09-08-2017"
            + "         0.00      ";


    assertEquals(expectedString,
            virtualStockModel.displayPortfolio("MyFirstPortfolio"));

  }

  /**
   * Testing createPortfolio with no stocks, empty portfolio.
   */
  @Test
  public void testEmptyCreatePortfolio() {

    String stockPrices = "GOOG,09-08-2017,234.67\n"
            + "GOOG,10-08-2017,200.00\n"
            + "GOOG,10-08-2017,200.00\n"
            + "MSFT,10-11-2017,106.0\n";

    StringReader reader = new StringReader(stockPrices);
    DataSourceInterface dataSource = DataSourceCreator.getDataSource("UserInput", reader);

    VirtualStockModelInterface virtualStockModel = new VirtualStockModel(dataSource);
    virtualStockModel.createPortfolio("MyFirstPortfolio");
    String expectedString = "Portfolio Name: MYFIRSTPORTFOLIO\n"
            + "StockTickerSymbol     NumberofShares         PriceofUnitShare      BuyDate"
            + "         Commission Fee";


    assertEquals(expectedString,
            virtualStockModel.displayPortfolio("MyFirstPortfolio"));

  }

  /**
   * Testing single portfolio with multiple stocks.
   */
  @Test
  public void testPortfolioMultipleStocks() {

    String stockPrices = "GOOG,09-08-2017,234.67\n"
            + "GOOG,10-08-2017,200.00\n"
            + "GOOG,10-08-2017,200.00\n"
            + "MSFT,10-11-2017,106.00\n";

    StringReader reader = new StringReader(stockPrices);
    DataSourceInterface dataSource = DataSourceCreator.getDataSource("UserInput", reader);

    VirtualStockModelInterface virtualStockModel = new VirtualStockModel(dataSource);
    virtualStockModel.createPortfolio("MyFirstPortfolio");
    virtualStockModel.buySharesOfStock("GOOG", "MyFirstPortfolio",
            500, "09-08-2017 12:56");
    virtualStockModel.buySharesOfStock("GOOG", "MyFirstPortfolio",
            1000, "10-08-2017 10:30");
    virtualStockModel.buySharesOfStock("msft", "MyFirstPortfolio",
            700, "10-11-2017 15:59");

    String expectedString = "Portfolio Name: MYFIRSTPORTFOLIO\n"
            + "StockTickerSymbol     NumberofShares         PriceofUnitShare      BuyDate"
            + "         Commission Fee\n"
            + "GOOG                      2                    234.67             09-08-2017"
            + "         0.00      \n"
            + "GOOG                      5                    200.00             10-08-2017"
            + "         0.00      \n"
            + "MSFT                      6                    106.00             10-11-2017 "
            + "        0.00      ";

    assertEquals(expectedString,
            virtualStockModel.displayPortfolio("MyFirstPortfolio"));
  }

  /**
   * Testing multiple createPortfolio with multiple stocks.
   */
  @Test
  public void testMultipleCreatePortfolio() {

    String stockPrices = "GOOG,09-08-2017,234.67\n"
            + "GOOG,10-08-2017,200.00\n"
            + "GOOG,10-08-2017,200.00\n"
            + "MSFT,10-11-2017,106.00\n";

    StringReader reader = new StringReader(stockPrices);
    DataSourceInterface dataSource = DataSourceCreator.getDataSource("UserInput", reader);

    VirtualStockModelInterface virtualStockModel = new VirtualStockModel(dataSource);
    virtualStockModel.createPortfolio("MyFirstPortfolio");
    virtualStockModel.buySharesOfStock("GOOG", "MyFirstPortfolio",
            500, "09-08-2017 12:56");
    virtualStockModel.buySharesOfStock("GOOG", "MyFirstPortfolio",
            1000, "10-08-2017 10:30");
    virtualStockModel.buySharesOfStock("msft", "MyFirstPortfolio",
            700, "10-11-2017 15:59");

    virtualStockModel.createPortfolio("Second");
    virtualStockModel.buySharesOfStock("GOOG", "Second",
            1500, "10-08-2017 12:56");

    String expectedString = "Portfolio Name : MYFIRSTPORTFOLIO\n"
            + "StockTickerSymbol     NumberofShares         PriceofUnitShare      BuyDate"
            + "         Commission Fee\n"
            + "GOOG                      2                    234.67             09-08-2017"
            + "         0.00      \n"
            + "GOOG                      5                    200.00             10-08-2017"
            + "         0.00      \n"
            + "MSFT                      6                    106.00             10-11-2017"
            + "         0.00      \n"
            + "Portfolio Name : SECOND\n"
            + "StockTickerSymbol     NumberofShares         PriceofUnitShare      BuyDate"
            + "         Commission Fee\n"
            + "GOOG                      7                    200.00             10-08-2017"
            + "         0.00      ";

    assertEquals(expectedString,
            virtualStockModel.displayAllPortfolios());

  }

  /**
   * Testing IllegalArgumentException, when portfolio name already exists, case is ignored and of if
   * a portfolio with the same name exists, throws IllegalArgumentException.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testDuplicatePortfolioName() {

    String stockPrices = "GOOG,09-08-2017,234.67\n"
            + "GOOG,10-08-2017,200.00\n"
            + "GOOG,10-08-2017,200.00\n"
            + "MSFT,10-11-2017,106.00\n";

    StringReader reader = new StringReader(stockPrices);
    DataSourceInterface dataSource = DataSourceCreator.getDataSource("UserInput", reader);

    VirtualStockModelInterface virtualStockModel = new VirtualStockModel(dataSource);
    virtualStockModel.createPortfolio("MyFirstPortfolio");
    virtualStockModel.buySharesOfStock("GOOG", "MyFirstPortfolio",
            500, "09-08-2017 12:56");
    virtualStockModel.buySharesOfStock("GOOG", "MyFirstPortfolio",
            1000, "10-08-2017 10:30");
    virtualStockModel.buySharesOfStock("msft", "MyFirstPortfolio",
            700, "10-11-2017 15:59");

    virtualStockModel.createPortfolio("MyFirstportfolio");

  }

  /**
   * Testing if number of shares is as expected.
   */
  @Test
  public void testNumberOfShares() {

    String stockPrices = "GOOG,09-08-2017,100.00\n"
            + "MSFT,03-12-2007,106.0\n";

    StringReader reader = new StringReader(stockPrices);
    DataSourceInterface dataSource = DataSourceCreator.getDataSource("UserInput", reader);

    VirtualStockModelInterface virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.createPortfolio("MyFirstPortfolio");

    virtualStockModel.buySharesOfStock("GOOG", "MyFirstPortfolio",
            500, "09-08-2017 12:56");

    String expectedString = "Portfolio Name: MYFIRSTPORTFOLIO\n"
            + "StockTickerSymbol     NumberofShares         PriceofUnitShare      BuyDate"
            + "         Commission Fee\n"
            + "GOOG                      5                    100.00             09-08-2017"
            + "         0.00      ";

    assertEquals(expectedString,
            virtualStockModel.displayPortfolio("MyFirstPortfolio"));

  }

  /**
   * Share cannot be bought on an holiday - weekend.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testBuyOnHoliday() {

    String stockPrices = "GOOG,09-08-2017,100.00\n"
            + "MSFT,03-12-2007,106.0\n";

    StringReader reader = new StringReader(stockPrices);
    DataSourceInterface dataSource = DataSourceCreator.getDataSource("UserInput", reader);

    VirtualStockModelInterface virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.createPortfolio("MyFirstPortfolio");

    virtualStockModel.buySharesOfStock("GOOG", "MyFirstPortfolio",
            500, "12-08-2017 12:56");
  }

  /**
   * User cannot buy shares before 9AM.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testBuyBefore9AM() {

    String stockPrices = "GOOG,09-08-2017,100.00\n"
            + "MSFT,03-12-2007,106.0\n";

    StringReader reader = new StringReader(stockPrices);
    DataSourceInterface dataSource = DataSourceCreator.getDataSource("UserInput", reader);

    VirtualStockModelInterface virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.createPortfolio("MyFirstPortfolio");

    virtualStockModel.buySharesOfStock("MSFT", "MyFirstPortfolio",
            500, "03-12-2007 08:59");
  }

  /**
   * User cannot buy shares before 9AM.
   */
  @Test(expected = IllegalArgumentException.class)
  public void test2BuyBefore9AM() {

    String stockPrices = "GOOG,09-08-2017,100.00\n"
            + "MSFT,03-12-2007,106.0\n";

    StringReader reader = new StringReader(stockPrices);
    DataSourceInterface dataSource = DataSourceCreator.getDataSource("UserInput", reader);

    VirtualStockModelInterface virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.createPortfolio("MyFirstPortfolio");

    virtualStockModel.buySharesOfStock("MSFT", "MyFirstPortfolio",
            500, "03-12-2007 06:12");
  }

  /**
   * User cannot buy shares before 9AM.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testBuyAfter4PM() {

    String stockPrices = "GOOG,09-08-2017,100.00\n"
            + "MSFT,03-12-2007,106.0\n";

    StringReader reader = new StringReader(stockPrices);
    DataSourceInterface dataSource = DataSourceCreator.getDataSource("UserInput", reader);

    VirtualStockModelInterface virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.createPortfolio("MyFirstPortfolio");

    virtualStockModel.buySharesOfStock("MSFT", "MyFirstPortfolio",
            500, "03-12-2007 16:01");
  }

  /**
   * User cannot buy shares before 9AM.
   */
  @Test(expected = IllegalArgumentException.class)
  public void test2BuyAfter4PM() {

    String stockPrices = "GOOG,09-08-2017,100.00\n"
            + "MSFT,03-12-2007,106.0\n";

    StringReader reader = new StringReader(stockPrices);
    DataSourceInterface dataSource = DataSourceCreator.getDataSource("UserInput", reader);

    VirtualStockModelInterface virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.createPortfolio("MyFirstPortfolio");

    virtualStockModel.buySharesOfStock("MSFT", "MyFirstPortfolio",
            500, "03-12-2007 22:00");
  }

  /**
   * Thorws IllegalArgumentException when the ticker symbol does not exist.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testTickerSymbolDoesNotExist() {

    String stockPrices = "GOOG,09-08-2017,100.00\n"
            + "MSFT,03-12-2007,106.0\n";

    StringReader reader = new StringReader(stockPrices);
    DataSourceInterface dataSource = DataSourceCreator.getDataSource("UserInput", reader);

    VirtualStockModelInterface virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.createPortfolio("MyFirstPortfolio");

    virtualStockModel.buySharesOfStock("xyz", "MyFirstPortfolio",
            500, "03-12-2007 15:30");
  }


  /**
   * Testing multiple portfolios with multiple stocks of different companies. Testing Display All
   * portfolios.
   */
  @Test
  public void testMultiplePortfoliosMultipleStocks() {

    String stockPrices = "GOOG,09-08-2017,234.67\n"
            + "GOOG,19-07-2016,756.11\n"
            + "GOOG,25-11-2015,748.34\n"
            + "GOOG,01-12-2015,747.12\n"
            + "GOOG,02-12-2015,746.22\n"
            + "GOOG,03-12-2015,730.98\n"
            + "GOOG,04-12-2015,745.12\n"
            + "GOOG,07-12-2015,712.12\n"
            + "GOOG,08-12-2015,745.12\n"
            + "GOOG,09-12-2015,767.12\n"
            + "GOOG,10-11-2015,789.45\n"
            + "GOOG,11-11-2015,756.78\n"
            + "GOOG,12-08-2014,580.66\n"
            + "GOOG,13-08-2014,567.34\n"
            + "GOOG,14-08-2014,534.90\n"
            + "GOOG,15-08-2014,555.87\n"
            + "GOOG,18-08-2014,509.67\n"
            + "MSFT,02-01-1998,129.94\n"
            + "MSFT,06-01-1998,130.45\n"
            + "MSFT,07-01-1998,145.45\n"
            + "MSFT,08-01-1998,123.78\n"
            + "MSFT,09-01-1998,123.99\n"
            + "MSFT,12-01-1998,124.56\n"
            + "MSFT,13-01-1998,128.02\n"
            + "MSFT,14-01-1998,122.12\n"
            + "MSFT,15-01-1998,124.56\n"
            + "MSFT,16-01-1998,124.92\n"
            + "AAPL,02-02-2007,85.89\n"
            + "AAPL,05-02-2007,87.23\n"
            + "AAPL,06-02-2007,90.45\n"
            + "AAPL,07-02-2007,94.45\n"
            + "AAPL,08-02-2007,35.12\n"
            + "AAPL,09-02-2007,34.56\n"
            + "TSLA,29-06-2010,45.11\n"
            + "TSLA,30-06-2010,44.66\n"
            + "TSLA,01-07-2010,39.12\n"
            + "TSLA,02-07-2010,45.56\n"
            + "TSLA,06-07-2010,42.92\n"
            + "TSLA,07-07-2010,41.34\n";


    StringReader reader = new StringReader(stockPrices);
    DataSourceInterface dataSource = DataSourceCreator.getDataSource("UserInput", reader);

    VirtualStockModelInterface virtualStockModel = new VirtualStockModel(dataSource);
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
            + "GOOG                      2                    756.11             19-07-2016"
            + "         0.00      \n"
            + "GOOG                      1                    712.12             07-12-2015"
            + "         0.00      \n"
            + "MSFT                      20                   145.45             07-01-1998"
            + "         0.00      \n"
            + "Portfolio Name : SECOND\n"
            + "StockTickerSymbol     NumberofShares         PriceofUnitShare      BuyDate"
            + "         Commission Fee\n"
            + "GOOG                      2                    767.12             09-12-2015"
            + "         0.00      \n"
            + "AAPL                      16                   90.45              06-02-2007"
            + "         0.00      \n"
            + "TSLA                      17                   39.12              01-07-2010"
            + "         0.00      \n"
            + "Portfolio Name : THIRD\n"
            + "StockTickerSymbol     NumberofShares         PriceofUnitShare      BuyDate"
            + "         Commission Fee\n"
            + "TSLA                      48                   41.34              07-07-2010"
            + "         0.00      \n"
            + "TSLA                      23                   42.92              06-07-2010"
            + "         0.00      \n"
            + "TSLA                      2                    45.56              02-07-2010"
            + "         0.00      \n"
            + "Portfolio Name : FOURTH\n"
            + "StockTickerSymbol     NumberofShares         PriceofUnitShare      BuyDate"
            + "         Commission Fee\n"
            + "MSFT                      3                    145.45             07-01-1998"
            + "         0.00      ";

    assertEquals(expectedResult, virtualStockModel.displayAllPortfolios());
  }

  @Test
  public void testSameStocksOnSameDate() {

    String stockPrices = "GOOG,09-08-2017,234.67\n"
            + "GOOG,19-07-2016,756.10\n"
            + "GOOG,25-11-2015,748.34\n"
            + "GOOG,01-12-2015,747.12\n"
            + "GOOG,02-12-2015,746.22\n"
            + "GOOG,03-12-2015,730.98\n"
            + "GOOG,04-12-2015,745.12\n"
            + "GOOG,07-12-2015,712.12\n"
            + "GOOG,08-12-2015,745.12\n"
            + "GOOG,09-12-2015,767.12\n"
            + "GOOG,10-11-2015,789.45\n"
            + "GOOG,11-11-2015,756.78\n"
            + "GOOG,12-08-2014,580.66\n"
            + "GOOG,13-08-2014,567.34\n"
            + "GOOG,14-08-2014,534.90\n"
            + "GOOG,15-08-2014,555.87\n"
            + "GOOG,18-08-2014,509.67\n"
            + "MSFT,02-01-1998,129.94\n"
            + "MSFT,06-01-1998,130.45\n"
            + "MSFT,07-01-1998,145.45\n"
            + "MSFT,08-01-1998,123.78\n"
            + "MSFT,09-01-1998,123.99\n"
            + "MSFT,12-01-1998,124.56\n"
            + "MSFT,13-01-1998,128.02\n"
            + "MSFT,14-01-1998,122.12\n"
            + "MSFT,15-01-1998,124.56\n"
            + "MSFT,16-01-1998,124.92\n"
            + "AAPL,02-02-2007,85.89\n"
            + "AAPL,05-02-2007,87.23\n"
            + "AAPL,06-02-2007,90.45\n"
            + "AAPL,07-02-2007,94.45\n"
            + "AAPL,08-02-2007,35.12\n"
            + "AAPL,09-02-2007,34.56\n"
            + "TSLA,29-06-2010,45.11\n"
            + "TSLA,30-06-2010,44.66\n"
            + "TSLA,01-07-2010,39.12\n"
            + "TSLA,02-07-2010,45.56\n"
            + "TSLA,06-07-2010,42.90\n"
            + "TSLA,07-07-2010,41.34\n";


    StringReader reader = new StringReader(stockPrices);
    DataSourceInterface dataSource = DataSourceCreator.getDataSource("UserInput", reader);

    VirtualStockModelInterface virtualStockModel = new VirtualStockModel(dataSource);
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

    String expectedResult = "Portfolio Name : FIRST\n"
            + "StockTickerSymbol     NumberofShares         PriceofUnitShare      BuyDate"
            + "         Commission Fee\n"
            + "GOOG                      2                    756.10             19-07-2016"
            + "        "
            + " 0.00      \n"
            + "GOOG                      1                    756.10             19-07-2016"
            + "         "
            + "0.00      \n"
            + "MSFT                      20                   145.45             07-01-1998"
            + "         "
            + "0.00      \n"
            + "Portfolio Name : SECOND\n"
            + "StockTickerSymbol     NumberofShares         PriceofUnitShare      BuyDate        "
            + " Commission Fee\n"
            + "Portfolio Name : THIRD\n"
            + "StockTickerSymbol     NumberofShares         PriceofUnitShare      BuyDate"
            + "         Commission Fee\n"
            + "Portfolio Name : FOURTH\n"
            + "StockTickerSymbol     NumberofShares         PriceofUnitShare      BuyDate         "
            + "Commission Fee";

    assertEquals(expectedResult, virtualStockModel.displayAllPortfolios());

  }

  /**
   * Throws IllegalArgumentException when the amount given is insuffcient to buy at least one
   * share.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInsufficientFund() {

    String stockPrices = "GOOG,09-08-2017,100.00\n"
            + "MSFT,03-12-2007,106.0\n";

    StringReader reader = new StringReader(stockPrices);
    DataSourceInterface dataSource = DataSourceCreator.getDataSource("UserInput", reader);

    VirtualStockModelInterface virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.createPortfolio("MyFirstPortfolio");

    virtualStockModel.buySharesOfStock("MSFT", "MyFirstPortfolio",
            99, "03-12-2007 15:30");
  }

  /**
   * Throws IllegalArgumentException when the amount given is insuffcient to buy at least one
   * share.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInsufficientFund2() {

    String stockPrices = "GOOG,09-08-2017,100.00\n"
            + "MSFT,03-12-2007,106.0\n";

    StringReader reader = new StringReader(stockPrices);
    DataSourceInterface dataSource = DataSourceCreator.getDataSource("UserInput", reader);

    VirtualStockModelInterface virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.createPortfolio("MyFirstPortfolio");

    virtualStockModel.buySharesOfStock("MSFT", "MyFirstPortfolio",
            0, "03-12-2007 15:30");
  }

  /**
   * Amount cannot be negative.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNegativeAmount() {

    String stockPrices = "GOOG,09-08-2017,100.00\n"
            + "MSFT,03-12-2007,106.0\n";

    StringReader reader = new StringReader(stockPrices);
    DataSourceInterface dataSource = DataSourceCreator.getDataSource("UserInput", reader);

    VirtualStockModelInterface virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.createPortfolio("MyFirstPortfolio");

    virtualStockModel.buySharesOfStock("MSFT", "MyFirstPortfolio",
            -99, "03-12-2007 15:30");

  }

  /**
   * Date cannot be null or empty.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNullDate() {

    String stockPrices = "GOOG,09-08-2017,100.00\n"
            + "MSFT,03-12-2007,106.0\n";

    StringReader reader = new StringReader(stockPrices);
    DataSourceInterface dataSource = DataSourceCreator.getDataSource("UserInput", reader);

    VirtualStockModelInterface virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.createPortfolio("MyFirstPortfolio");

    virtualStockModel.buySharesOfStock("MSFT", "MyFirstPortfolio",
            999, null);

  }

  /**
   * Date cannot be null or empty.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testEmptyDate() {

    String stockPrices = "GOOG,09-08-2017,100.00\n"
            + "MSFT,03-12-2007,106.0\n";

    StringReader reader = new StringReader(stockPrices);
    DataSourceInterface dataSource = DataSourceCreator.getDataSource("UserInput", reader);

    VirtualStockModelInterface virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.createPortfolio("MyFirstPortfolio");

    virtualStockModel.buySharesOfStock("MSFT", "MyFirstPortfolio",
            900, "");

  }

  /**
   * Ticker symbol cannot be null or empty.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testEmptyTickerSymbol() {

    String stockPrices = "GOOG,09-08-2017,100.00\n"
            + "MSFT,03-12-2007,106.0\n";

    StringReader reader = new StringReader(stockPrices);
    DataSourceInterface dataSource = DataSourceCreator.getDataSource("UserInput", reader);

    VirtualStockModelInterface virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.createPortfolio("MyFirstPortfolio");

    virtualStockModel.buySharesOfStock("", "MyFirstPortfolio",
            1000, "03-12-2007 15:30");

  }

  /**
   * Ticker symbol cannot be null or empty.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNullTickerSymbol() {

    String stockPrices = "GOOG,09-08-2017,100.00\n"
            + "MSFT,03-12-2007,106.0\n";

    StringReader reader = new StringReader(stockPrices);
    DataSourceInterface dataSource = DataSourceCreator.getDataSource("UserInput", reader);

    VirtualStockModelInterface virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.createPortfolio("MyFirstPortfolio");

    virtualStockModel.buySharesOfStock(null, "MyFirstPortfolio",
            1000, "03-12-2007 15:30");

  }

  /**
   * Portfolio name cannot be null or empty.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNullPortfolioName() {

    String stockPrices = "GOOG,09-08-2017,100.00\n"
            + "MSFT,03-12-2007,106.0\n";

    StringReader reader = new StringReader(stockPrices);
    DataSourceInterface dataSource = DataSourceCreator.getDataSource("UserInput", reader);

    VirtualStockModelInterface virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.createPortfolio("MyFirstPortfolio");

    virtualStockModel.buySharesOfStock("MSFT", null,
            1000, "03-12-2007 15:30");

  }

  /**
   * Portfolio name cannot be null or empty.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testEmptyPortfolioName() {

    String stockPrices = "GOOG,09-08-2017,100.00\n"
            + "MSFT,03-12-2007,106.0\n";

    StringReader reader = new StringReader(stockPrices);
    DataSourceInterface dataSource = DataSourceCreator.getDataSource("UserInput", reader);

    VirtualStockModelInterface virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.createPortfolio("MyFirstPortfolio");

    virtualStockModel.buySharesOfStock("MSFT", "",
            1000, "03-12-2007 15:30");

  }

  /**
   * Testing totalCostBasis for a single stock.
   */
  @Test
  public void testTotalCostBasis() {

    String stockPrices = "GOOG,01-11-2016,700.47\n"
            + "MSFT,03-12-2007,106.0\n";

    StringReader reader = new StringReader(stockPrices);
    DataSourceInterface dataSource = DataSourceCreator.getDataSource("UserInput", reader);

    VirtualStockModelInterface virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.createPortfolio("MyFirstPortfolio");

    virtualStockModel.buySharesOfStock("GOOG", "MyFirstPortfolio",
            10000, "01-11-2016 12:30");

    assertEquals(9806.58,
            virtualStockModel.getTotalCostBasis("MyFirstPortfolio",
                    "03-11-2016"), 0.001);

  }

  /**
   * Testing totalCostBasis for multiple stocks.
   */
  @Test
  public void testTotalCostBasis2() {

    String stockPrices = "GOOG,01-11-2016,700.47\n"
            + "GOOG,07-12-2016,750.23\n"
            + "GOOG,27-02-2017,780.17\n"
            + "AAPL,15-05-2018,820.34\n"
            + "TSLA,24-08-2018,880.76\n";

    StringReader reader = new StringReader(stockPrices);
    DataSourceInterface dataSource = DataSourceCreator.getDataSource("UserInput", reader);

    VirtualStockModelInterface virtualStockModel = new VirtualStockModel(dataSource);

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

    assertEquals(48454.05,
            virtualStockModel.getTotalCostBasis("MyFirstPortfolio",
                    "24-08-2018"), 0.001);

  }

  /**
   * Testing totalCostBasis for no stocks bought before the date.
   */
  @Test
  public void testTotalCostBasisNoStocksBeforeDate() {

    String stockPrices = "GOOG,01-11-2016,700.47\n"
            + "GOOG,07-12-2016,750.23\n"
            + "GOOG,27-02-2017,780.17\n"
            + "AAPL,15-05-2018,820.34\n"
            + "TSLA,24-08-2018,880.76\n";

    StringReader reader = new StringReader(stockPrices);
    DataSourceInterface dataSource = DataSourceCreator.getDataSource("UserInput", reader);

    VirtualStockModelInterface virtualStockModel = new VirtualStockModel(dataSource);

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

  }

  /**
   * Testing totalCostBasis for some stocks bought before the date.
   */
  @Test
  public void testTotalCostBasisSomeStocksBeforeDate() {

    String stockPrices = "GOOG,01-11-2016,700.47\n"
            + "GOOG,07-12-2016,750.23\n"
            + "GOOG,27-02-2017,780.17\n"
            + "AAPL,15-05-2018,820.34\n"
            + "TSLA,24-08-2018,880.76\n";

    StringReader reader = new StringReader(stockPrices);
    DataSourceInterface dataSource = DataSourceCreator.getDataSource("UserInput", reader);

    VirtualStockModelInterface virtualStockModel = new VirtualStockModel(dataSource);

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

    assertEquals(28921.61,
            virtualStockModel.getTotalCostBasis("MyFirstPortfolio",
                    "28-02-2017"), 0.001);

  }

  /**
   * If the given portfolio name does not exist then returns 0.0.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testTotalCostBasisNoPortfolioName() {

    String stockPrices = "GOOG,01-11-2016,700.47\n"
            + "GOOG,07-12-2016,750.23\n"
            + "GOOG,27-02-2017,780.17\n"
            + "AAPL,15-05-2018,820.34\n"
            + "TSLA,24-08-2018,880.76\n";

    StringReader reader = new StringReader(stockPrices);
    DataSourceInterface dataSource = DataSourceCreator.getDataSource("UserInput",
            reader);

    VirtualStockModelInterface virtualStockModel = new VirtualStockModel(dataSource);

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


    virtualStockModel.getTotalCostBasis("second",
            "24-08-2018");

  }

  /**
   * If the date and time is not in the expected format throws IllegalArgumentException.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testTotalCostBasisDateInvalid() {

    String stockPrices = "GOOG,01-11-2016,700.47\n"
            + "GOOG,07-12-2016,750.23\n"
            + "GOOG,27-02-2017,780.17\n"
            + "AAPL,15-05-2018,820.34\n"
            + "TSLA,24-08-2018,880.76\n";

    StringReader reader = new StringReader(stockPrices);
    DataSourceInterface dataSource = DataSourceCreator.getDataSource("UserInput", reader);

    VirtualStockModelInterface virtualStockModel = new VirtualStockModel(dataSource);

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
            10000, "24-08-2018 2:00");
  }

  /**
   * If the date and time is not in the expected format throws IllegalArgumentException. For single
   * digit day or time given with zero ir for time 7:30 -> 07:30 and date 4-2-2014 -> 04-02-2014.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testTotalCostBasisDateInvalid2() {

    String stockPrices = "GOOG,01-11-2016,700.47\n"
            + "GOOG,07-12-2016,750.23\n"
            + "GOOG,27-02-2017,780.17\n"
            + "AAPL,15-05-2018,820.34\n"
            + "TSLA,24-08-2018,880.76\n";

    StringReader reader = new StringReader(stockPrices);
    DataSourceInterface dataSource = DataSourceCreator.getDataSource("UserInput", reader);

    VirtualStockModelInterface virtualStockModel = new VirtualStockModel(dataSource);

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
            10000, "9-08-2018 2:00");
  }

  /**
   * User cannot buy on a future date. Throws IllegalArgumentException.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testTotalCostBasisDateInvalid3() {

    String stockPrices = "GOOG,01-11-2016,700.47\n"
            + "GOOG,07-12-2016,750.23\n"
            + "GOOG,27-02-2017,780.17\n"
            + "AAPL,15-05-2018,820.34\n"
            + "TSLA,24-08-2018,880.76\n";

    StringReader reader = new StringReader(stockPrices);
    DataSourceInterface dataSource = DataSourceCreator.getDataSource("UserInput", reader);

    VirtualStockModelInterface virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.createPortfolio("MyFirstPortfolio");

    virtualStockModel.buySharesOfStock("GOOG", "MyFirstPortfolio",
            10000, "01-11-2019 12:30");

  }

  /**
   * Testing get the totalPortfolioWorth on a certain date.
   */
  @Test
  public void testTotalPortfolioWorth() {

    String stockPrices = "GOOG,01-11-2016,700.47\n"
            + "GOOG,07-12-2016,750.23\n"
            + "GOOG,27-02-2017,780.17\n"
            + "AAPL,15-05-2018,820.34\n"
            + "TSLA,24-08-2018,880.76\n"
            + "GOOG,28-09-2018,900.67\n"
            + "AAPL,28-09-2018,987.81\n"
            + "TSLA,28-09-2018,980.76\n";

    StringReader reader = new StringReader(stockPrices);
    DataSourceInterface dataSource = DataSourceCreator.getDataSource("UserInput", reader);

    VirtualStockModelInterface virtualStockModel = new VirtualStockModel(dataSource);

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

    assertEquals(57768.21, virtualStockModel.getTotalValue(
            "MyFirstPortfolio", "28-09-2018"), 0.001);

  }

  /**
   * Testing get the totalPortfolioWorth on a Holiday such that it returns the worth based on the
   * last working day before the holiday.
   */
  @Test
  public void testTotalPortfolioWorth2() {

    String stockPrices = "GOOG,01-11-2016,700.47\n"
            + "GOOG,07-12-2016,750.23\n"
            + "GOOG,27-02-2017,780.17\n"
            + "AAPL,15-05-2018,820.34\n"
            + "TSLA,24-08-2018,880.76\n"
            + "GOOG,28-09-2018,900.67\n"
            + "AAPL,28-09-2018,987.81\n"
            + "TSLA,28-09-2018,980.76\n";

    StringReader reader = new StringReader(stockPrices);
    DataSourceInterface dataSource = DataSourceCreator.getDataSource("UserInput", reader);

    VirtualStockModelInterface virtualStockModel = new VirtualStockModel(dataSource);

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

    assertEquals(57768.21, virtualStockModel.getTotalValue(
            "MyFirstPortfolio", "30-09-2018"), 0.001);

  }

  /**
   * Testing get the totalPortfolioWorth on a date when only certain stocks have their unit share
   * price. In that case, for the stocks that don't have valuen for that date, just 0 is added to
   * the total value. Therefore only total worth of stocks that have value on that date is
   * returned.
   */
  @Test
  public void testTotalPortfolioWorth3() {

    String stockPrices = "GOOG,01-11-2016,700.47\n"
            + "GOOG,07-12-2016,750.23\n"
            + "GOOG,27-02-2017,780.17\n"
            + "AAPL,15-05-2018,820.34\n"
            + "TSLA,24-08-2018,880.76\n"
            + "GOOG,28-09-2018,900.67\n";

    StringReader reader = new StringReader(stockPrices);
    DataSourceInterface dataSource = DataSourceCreator.getDataSource("UserInput",
            reader);

    VirtualStockModelInterface virtualStockModel = new VirtualStockModel(dataSource);

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

    assertEquals(35126.13, virtualStockModel.getTotalValue(
            "MyFirstPortfolio", "30-09-2018"), 0.001);

  }

  /**
   * Testing get the totalPortfolioWorth is when no stocks are bought and added to the portfolio
   * yet.
   */
  @Test
  public void testTotalPortfolioWorth4() {

    String stockPrices = "GOOG,01-11-2016,700.47\n"
            + "GOOG,07-12-2016,750.23\n"
            + "GOOG,27-02-2017,780.17\n"
            + "AAPL,15-05-2018,820.34\n"
            + "TSLA,24-08-2018,880.76\n"
            + "GOOG,28-09-2018,900.67\n"
            + "AAPL,28-09-2018,987.81\n"
            + "TSLA,28-09-2018,980.76\n";

    StringReader reader = new StringReader(stockPrices);
    DataSourceInterface dataSource = DataSourceCreator.getDataSource("UserInput",
            reader);

    VirtualStockModelInterface virtualStockModel = new VirtualStockModel(dataSource);

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

  }

  /**
   * Testing get the totalPortfolioWorth is when zero stocks are bought.
   */
  @Test
  public void testTotalPortfolioWorth5() {

    String stockPrices = "GOOG,01-11-2016,700.47\n"
            + "GOOG,07-12-2016,750.23\n"
            + "GOOG,27-02-2017,780.17\n"
            + "AAPL,15-05-2018,820.34\n"
            + "TSLA,24-08-2018,880.76\n"

            + "GOOG,26-09-2018,900.67\n"
            + "AAPL,26-09-2018,987.81\n"
            + "TSLA,26-09-2018,980.76\n";

    StringReader reader = new StringReader(stockPrices);
    DataSourceInterface dataSource = DataSourceCreator.getDataSource("UserInput",
            reader);

    VirtualStockModelInterface virtualStockModel = new VirtualStockModel(dataSource);

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


    assertEquals(20256.21, virtualStockModel.getTotalValue(
            "MyFirstPortfolio", "07-12-2016"), 0.001);

  }

  /**
   * Testing get the totalPortfolioWorth is when only some stocks are bought.
   */
  @Test
  public void testTotalPortfolioWorth6() {

    String stockPrices = "GOOG,01-11-2016,700.47\n"
            + "GOOG,07-12-2016,750.23\n"
            + "GOOG,27-02-2017,780.17\n"
            + "AAPL,15-05-2018,820.34\n"
            + "GOOG,16-05-2018,900.61\n"
            + "AAPL,16-05-2018,950.81\n"
            + "TSLA,16-05-2018,914.76\n"
            + "TSLA,24-08-2018,880.76\n"
            + "GOOG,26-09-2018,900.67\n"
            + "AAPL,26-09-2018,987.81\n"
            + "TSLA,26-09-2018,980.76\n";

    StringReader reader = new StringReader(stockPrices);
    DataSourceInterface dataSource = DataSourceCreator.getDataSource("UserInput",
            reader);

    VirtualStockModelInterface virtualStockModel = new VirtualStockModel(dataSource);

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

    assertEquals(46533.51, virtualStockModel.getTotalValue(
            "MyFirstPortfolio", "16-05-2018"), 0.001);
  }

  /**
   * Testing get the totalPortfolioWorth is zero when no stocks are bought.
   */
  @Test
  public void testTotalPortfolioWorth7() {

    String stockPrices = "GOOG,01-11-2016,700.47\n"
            + "GOOG,07-12-2016,750.23\n"
            + "GOOG,27-02-2017,780.17\n"
            + "AAPL,15-05-2018,820.34\n"
            + "GOOG,16-05-2018,900.61\n"
            + "AAPL,16-05-2018,950.81\n"
            + "TSLA,16-05-2018,914.76\n"
            + "TSLA,24-08-2018,880.76\n"
            + "GOOG,26-09-2018,900.67\n"
            + "AAPL,26-09-2018,987.81\n"
            + "TSLA,26-09-2018,980.76\n";

    StringReader reader = new StringReader(stockPrices);
    DataSourceInterface dataSource = DataSourceCreator.getDataSource("UserInput",
            reader);

    VirtualStockModelInterface virtualStockModel = new VirtualStockModel(dataSource);

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
  }

  /**
   * Testing get the totalPortfolioWorth is zero when no stocks are bought.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testTotalPortfolioWorthDateInvalid() {

    String stockPrices = "GOOG,01-11-2016,700.47\n"
            + "GOOG,07-12-2016,750.23\n"
            + "GOOG,27-02-2017,780.17\n"
            + "AAPL,15-05-2018,820.34\n"
            + "GOOG,16-05-2018,900.61\n"
            + "AAPL,16-05-2018,950.81\n"
            + "TSLA,16-05-2018,914.76\n"
            + "TSLA,24-08-2018,880.76\n"
            + "GOOG,26-09-2018,900.67\n"
            + "AAPL,26-09-2018,987.81\n"
            + "TSLA,26-09-2018,980.76\n";

    StringReader reader = new StringReader(stockPrices);
    DataSourceInterface dataSource = DataSourceCreator.getDataSource("UserInput",
            reader);

    VirtualStockModelInterface virtualStockModel = new VirtualStockModel(dataSource);

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
            "MyFirstPortfolio", "27-10-2016 2:00"), 0.001);
  }

  /**
   * Date should be entered in the specfied format.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testTotalPortfolioWorthDateInvalid2() {

    String stockPrices = "GOOG,01-11-2016,700.47\n"
            + "GOOG,07-12-2016,750.23\n"
            + "GOOG,27-02-2017,780.17\n"
            + "AAPL,15-05-2018,820.34\n"
            + "GOOG,16-05-2018,900.61\n"
            + "AAPL,16-05-2018,950.81\n"
            + "TSLA,16-05-2018,914.76\n"
            + "TSLA,24-08-2018,880.76\n"
            + "GOOG,26-09-2018,900.67\n"
            + "AAPL,26-09-2018,987.81\n"
            + "TSLA,26-09-2018,980.76\n";

    StringReader reader = new StringReader(stockPrices);
    DataSourceInterface dataSource = DataSourceCreator.getDataSource("UserInput",
            reader);

    VirtualStockModelInterface virtualStockModel = new VirtualStockModel(dataSource);

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

    virtualStockModel.getTotalValue(
            "MyFirstPortfolio", "27-10 01:00");
  }


  /**
   * Testing buySharesOfStock throws IllegalArgumentException when given future date.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testBuyFutureDate() {

    String stockPrices = "GOOG,01-11-2016,700.47\n"
            + "GOOG,07-12-2016,750.23\n"
            + "GOOG,27-02-2017,780.17\n"
            + "AAPL,15-05-2018,820.34\n"
            + "TSLA,24-08-2018,880.76\n"
            + "GOOG,28-09-2018,900.67\n"
            + "AAPL,28-09-2018,987.81\n"
            + "TSLA,28-09-2018,980.76\n";

    StringReader reader = new StringReader(stockPrices);
    DataSourceInterface dataSource = DataSourceCreator.getDataSource("UserInput",
            reader);

    VirtualStockModelInterface virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.createPortfolio("MyFirstPortfolio");

    virtualStockModel.buySharesOfStock("GOOG", "MyFirstPortfolio",
            10000, "02-11-2020 12:30");

  }

  /**
   * Testing getTotalWorth returns 0.0 when given future date.
   */
  @Test
  public void testGetTotalWorthFutureDate() {

    String stockPrices = "GOOG,01-11-2016,700.47\n"
            + "GOOG,07-12-2016,750.23\n"
            + "GOOG,27-02-2017,780.17\n"
            + "AAPL,15-05-2018,820.34\n"
            + "GOOG,16-05-2018,900.61\n"
            + "AAPL,16-05-2018,950.81\n"
            + "TSLA,16-05-2018,914.76\n"
            + "TSLA,24-08-2018,880.76\n"
            + "GOOG,26-09-2018,900.67\n"
            + "AAPL,26-09-2018,987.81\n"
            + "TSLA,26-09-2018,980.76\n";

    StringReader reader = new StringReader(stockPrices);
    DataSourceInterface dataSource = DataSourceCreator.getDataSource("UserInput",
            reader);

    VirtualStockModelInterface virtualStockModel = new VirtualStockModel(dataSource);

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

  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreatePortfolioWhenGivenEmptyName() {

    String stockPrices = "GOOG,01-11-2016,700.47\n"
            + "GOOG,07-12-2016,750.23\n"
            + "GOOG,27-02-2017,780.17\n"
            + "AAPL,15-05-2018,820.34\n"
            + "GOOG,16-05-2018,900.61\n"
            + "AAPL,16-05-2018,950.81\n"
            + "TSLA,16-05-2018,914.76\n"
            + "TSLA,24-08-2018,880.76\n"
            + "GOOG,26-09-2018,900.67\n"
            + "AAPL,26-09-2018,987.81\n"
            + "TSLA,26-09-2018,980.76\n";

    StringReader reader = new StringReader(stockPrices);
    DataSourceInterface dataSource = DataSourceCreator.getDataSource("UserInput",
            reader);

    VirtualStockModelInterface virtualStockModel = new VirtualStockModel(dataSource);
    virtualStockModel.createPortfolio("");

  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreatePortfolioWhenGivenNullName() {

    String stockPrices = "GOOG,01-11-2016,700.47\n"
            + "GOOG,07-12-2016,750.23\n"
            + "GOOG,27-02-2017,780.17\n"
            + "AAPL,15-05-2018,820.34\n"
            + "GOOG,16-05-2018,900.61\n"
            + "AAPL,16-05-2018,950.81\n"
            + "TSLA,16-05-2018,914.76\n"
            + "TSLA,24-08-2018,880.76\n"
            + "GOOG,26-09-2018,900.67\n"
            + "AAPL,26-09-2018,987.81\n"
            + "TSLA,26-09-2018,980.76\n";

    StringReader reader = new StringReader(stockPrices);
    DataSourceInterface dataSource = DataSourceCreator.getDataSource("UserInput",
            reader);

    VirtualStockModelInterface virtualStockModel = new VirtualStockModel(dataSource);
    virtualStockModel.createPortfolio(null);

  }

  /**
   * When given to buy shares with a portfolio name that does not exist, then new portfolio with the
   * given name is created and stock is added to it.
   */
  @Test
  public void testBuyShareWhenPortfolioDoesNotExist() {

    String stockPrices = "GOOG,01-11-2016,700.47\n"
            + "GOOG,07-12-2016,750.23\n"
            + "GOOG,27-02-2017,780.17\n"
            + "AAPL,15-05-2018,820.34\n"
            + "GOOG,16-05-2018,900.61\n"
            + "AAPL,16-05-2018,950.81\n"
            + "TSLA,16-05-2018,914.76\n"
            + "TSLA,24-08-2018,880.76\n"
            + "GOOG,26-09-2018,900.67\n"
            + "AAPL,26-09-2018,987.81\n"
            + "TSLA,26-09-2018,980.76\n";

    StringReader reader = new StringReader(stockPrices);
    DataSourceInterface dataSource = DataSourceCreator.getDataSource("UserInput",
            reader);

    VirtualStockModelInterface virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.buySharesOfStock("GOOG", "MyFirstPortfolio",
            10000, "01-11-2016 12:30");

    String result = "Portfolio Name: MYFIRSTPORTFOLIO\n"
            + "StockTickerSymbol     NumberofShares         PriceofUnitShare      BuyDate"
            + "         Commission Fee\n"
            + "GOOG                      14                   700.47             01-11-2016"
            + "         0.00      ";

    assertEquals(result, virtualStockModel.displayPortfolio("myfirstportfolio"));

  }

  /**
   * Shares cannot be bought on holidays - weekends and public holidays.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testBuyOnSaturday() {

    String stockPrices = "GOOG,01-11-2016,700.47\n"
            + "GOOG,07-12-2016,750.23\n"
            + "GOOG,27-02-2017,780.17\n"
            + "AAPL,15-05-2018,820.34\n"
            + "GOOG,16-05-2018,900.61\n"
            + "AAPL,16-05-2018,950.81\n"
            + "TSLA,16-05-2018,914.76\n"
            + "TSLA,24-08-2018,880.76\n"
            + "GOOG,26-09-2018,900.67\n"
            + "AAPL,26-09-2018,987.81\n"
            + "TSLA,26-09-2018,980.76\n";

    StringReader reader = new StringReader(stockPrices);
    DataSourceInterface dataSource = DataSourceCreator.getDataSource("UserInput",
            reader);

    VirtualStockModelInterface virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.buySharesOfStock("GOOG", "MyFirstPortfolio",
            10000, "10-11-2018 12:30");
  }

  /**
   * Shares cannot be bought on holidays - weekends and public holidays.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testBuyOnSunday() {

    String stockPrices = "GOOG,01-11-2016,700.47\n"
            + "GOOG,07-12-2016,750.23\n"
            + "GOOG,27-02-2017,780.17\n"
            + "AAPL,15-05-2018,820.34\n"
            + "GOOG,16-05-2018,900.61\n"
            + "AAPL,16-05-2018,950.81\n"
            + "TSLA,16-05-2018,914.76\n"
            + "TSLA,24-08-2018,880.76\n"
            + "GOOG,26-09-2018,900.67\n"
            + "AAPL,26-09-2018,987.81\n"
            + "TSLA,26-09-2018,980.76\n";

    StringReader reader = new StringReader(stockPrices);
    DataSourceInterface dataSource = DataSourceCreator.getDataSource("UserInput",
            reader);

    VirtualStockModelInterface virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.buySharesOfStock("GOOG", "MyFirstPortfolio",
            10000, "11-11-2018 12:30");
  }

  /**
   * Throws IllegalArugumentException if the tikcer symbol and corresponding date does not exist.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testWhenTickerSymbolandDateDoesNotExist() {

    String stockPrices = "GOOG,01-11-2016,700.47\n"
            + "GOOG,07-12-2016,750.23\n"
            + "GOOG,27-02-2017,780.17\n"
            + "AAPL,15-05-2018,820.34\n"
            + "GOOG,16-05-2018,900.61\n"
            + "AAPL,16-05-2018,950.81\n"
            + "TSLA,16-05-2018,914.76\n"
            + "TSLA,24-08-2018,880.76\n"
            + "GOOG,26-09-2018,900.67\n"
            + "AAPL,26-09-2018,987.81\n"
            + "TSLA,26-09-2018,980.76\n";

    StringReader reader = new StringReader(stockPrices);
    DataSourceInterface dataSource = DataSourceCreator.getDataSource("UserInput",
            reader);

    VirtualStockModelInterface virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.buySharesOfStock("GOOG", "MyFirstPortfolio",
            10000, "09-11-2018 12:30");

  }

  /**
   * Throws IllegalArugumentException if the date is new year.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testHolidayNewYear() {

    String stockPrices = "GOOG,01-11-2016,700.47\n"
            + "GOOG,07-12-2016,750.23\n"
            + "GOOG,27-02-2017,780.17\n"
            + "AAPL,15-05-2018,820.34\n"
            + "GOOG,16-05-2018,900.61\n"
            + "AAPL,16-05-2018,950.81\n"
            + "TSLA,16-05-2018,914.76\n"
            + "TSLA,24-08-2018,880.76\n"
            + "GOOG,26-09-2018,900.67\n"
            + "AAPL,26-09-2018,987.81\n"
            + "TSLA,26-09-2018,980.76\n";

    StringReader reader = new StringReader(stockPrices);
    DataSourceInterface dataSource = DataSourceCreator.getDataSource("UserInput",
            reader);

    VirtualStockModelInterface virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.buySharesOfStock("GOOG", "MyFirstPortfolio",
            10000, "01-01-2018 12:30");

  }

  /**
   * Throws IllegalArugumentException if the date is thanks giving.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testHolidaythanksgiving() {

    String stockPrices = "GOOG,01-11-2016,700.47\n"
            + "GOOG,07-12-2016,750.23\n"
            + "GOOG,27-02-2017,780.17\n"
            + "AAPL,15-05-2018,820.34\n"
            + "GOOG,16-05-2018,900.61\n"
            + "AAPL,16-05-2018,950.81\n"
            + "TSLA,16-05-2018,914.76\n"
            + "TSLA,24-08-2018,880.76\n"
            + "GOOG,26-09-2018,900.67\n"
            + "AAPL,26-09-2018,987.81\n"
            + "TSLA,26-09-2018,980.76\n";

    StringReader reader = new StringReader(stockPrices);
    DataSourceInterface dataSource = DataSourceCreator.getDataSource("UserInput",
            reader);

    VirtualStockModelInterface virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.buySharesOfStock("GOOG", "MyFirstPortfolio",
            10000, "22-11-2018 12:30");

  }

}