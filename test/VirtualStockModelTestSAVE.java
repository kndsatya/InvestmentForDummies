import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import datasource.DataSourceCreator;
import datasource.DataSourceInterface;
import model.VirtualStockModel;
import model.VirtualStockModelInterface;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class VirtualStockModelTestSAVE {

  /**
   * Testing if a portfolio can be saved.
   */
  @Test
  public void testSavePortfolio() {

    StringReader reader = new StringReader("");
    DataSourceInterface dataSource = DataSourceCreator.getDataSource("ALPHAAPI", reader);

    VirtualStockModelInterface virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.createPortfolio("MyPortfolio");

    virtualStockModel.buySharesOfStock("GOOG", "MyPortfolio",
            1853, "09-08-2017 12:56", 30.50);

    virtualStockModel.savePortfolio("MyPortfolio");

    assertEquals(1876.3, virtualStockModel
            .getTotalCostBasis("MyPortfolio", "09-08-2017"), 0.001);
  }


  /**
   * Testing if a portfolio can be retrieved.
   */
  @Test
  public void testRetrievePortfolio() {

    StringReader reader = new StringReader("");
    DataSourceInterface dataSource = DataSourceCreator.getDataSource("ALPHAAPI", reader);

    VirtualStockModelInterface virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.retrievePortfolios();

    assertEquals(1876.3, virtualStockModel
            .getTotalCostBasis("MyPortfolio", "09-08-2017"), 0.001);
  }


  /**
   * Testing if a strategy can be created, saved and applied to a portfolio.
   */
  @Test
  public void testCreateStrategy() {

    StringReader reader = new StringReader("");
    DataSourceInterface dataSource = DataSourceCreator.getDataSource("ALPHAAPI", reader);

    VirtualStockModelInterface virtualStockModel = new VirtualStockModel(dataSource);

    Map stockWeights = new HashMap<>();
    stockWeights = new HashMap<>();

    stockWeights.put("Fb", 25.00);
    stockWeights.put("aapl", 25.00);
    stockWeights.put("nflx", 25.00);
    stockWeights.put("goog", 25.00);

    virtualStockModel.createStrategy("dollarStrategy1", stockWeights, 5000,
            10, "05-05-2018", "07-05-2018", 30.00);

    virtualStockModel.createStrategy("dollarStrategy2", stockWeights, 5000,
            60, "05-05-2017", "07-05-2018", 30.00);

    virtualStockModel.saveStrategy("dollarStrategy2");

    virtualStockModel.applyStrategy("dollarStrategy1", "SecondPortfolio");

    assertEquals(4510.32, virtualStockModel.getTotalCostBasis("SecondPortfolio",
            "17-05-2018"), 0.001);

  }

  /**
   * Testing if saved strategies are retrieved.
   */
  @Test
  public void testRetrieveStrategy() {

    StringReader reader = new StringReader("");
    DataSourceInterface dataSource = DataSourceCreator.getDataSource("ALPHAAPI", reader);

    VirtualStockModelInterface virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.retrieveStrategies();

    Map stockWeights = new HashMap<>();
    stockWeights = new HashMap<>();

    stockWeights.put("Fb", 25.00);
    stockWeights.put("aapl", 25.00);
    stockWeights.put("nflx", 25.00);
    stockWeights.put("goog", 25.00);

    virtualStockModel.applyStrategy("dollarStrategy", "SecondPortfolio");

    assertEquals(4510.32, virtualStockModel.getTotalCostBasis("SecondPortfolio",
            "17-05-2018"), 0.001);

  }


  /**
   * Testing if saved strategies are retrieved.
   */
  @Test
  public void testRetrieveStrategy2() {

    StringReader reader = new StringReader("");
    DataSourceInterface dataSource = DataSourceCreator.getDataSource("ALPHAAPI", reader);

    VirtualStockModelInterface virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.retrieveStrategies();

    Map stockWeights = new HashMap<>();
    stockWeights = new HashMap<>();

    stockWeights.put("Fb", 25.00);
    stockWeights.put("aapl", 25.00);
    stockWeights.put("nflx", 25.00);
    stockWeights.put("goog", 25.00);

    virtualStockModel.applyStrategy("dollarStrategy2", "SecondPortfolio");

    assertEquals(32543.75, virtualStockModel.getTotalCostBasis(
            "SecondPortfolio", "17-05-2018"), 0.001);

  }


  /**
   * Testing if future stocks are bought when application is reopened.
   */
  @Test
  public void testBuyingFutureStocks() throws IOException {

    StringReader reader = new StringReader("");
    DataSourceInterface dataSource = DataSourceCreator.getDataSource("ALPHAAPI", reader);

    VirtualStockModelInterface virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.retrieveStrategies();

    Map stockWeights = new HashMap<>();
    stockWeights = new HashMap<>();

    stockWeights.put("Fb", 25.00);
    stockWeights.put("aapl", 25.00);
    stockWeights.put("nflx", 25.00);
    stockWeights.put("goog", 25.00);

    virtualStockModel.createStrategy("dollarStrategy3", stockWeights, 5000,
            1, "28-11-2018", "29-11-2018", 30.00);

    virtualStockModel.applyStrategy("dollarStrategy3", "ThirdPortfolio");

    System.out.println(virtualStockModel.getTotalCostBasis("ThirdPortfolio",
            "17-05-2018"));

    virtualStockModel.savePortfolio("ThirdPortfolio");
    virtualStockModel.saveStrategy("dollarStrategy3");

    assertEquals(34543.23, virtualStockModel.getTotalCostBasis(
            "SecondPortfolio", "17-06-2018"), 0.001);

  }

  /**
   * Testing if future stocks are bought when application is reopened.
   */
  @Test
  public void testBuyingFutureStocks2() {

    StringReader reader = new StringReader("");
    DataSourceInterface dataSource = DataSourceCreator.getDataSource("ALPHAAPI", reader);

    VirtualStockModelInterface virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.retrievePortfolios();
    virtualStockModel.retrieveStrategies();

    assertEquals(35543.50, virtualStockModel.getTotalCostBasis(
            "SecondPortfolio", "17-06-2018"), 0.001);
  }


  /**
   * Testing if multiple strategies can be applied on the same portfolio.
   */
  @Test
  public void testMultipleStrategiesOnPortfolio() {

    StringReader reader = new StringReader("");
    DataSourceInterface dataSource = DataSourceCreator.getDataSource("ALPHAAPI", reader);

    VirtualStockModelInterface virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.retrievePortfolios();
    virtualStockModel.retrieveStrategies();

    Map stockWeights = new HashMap<>();

    stockWeights.put("Fb", 15.00);
    stockWeights.put("aapl", 30.00);
    stockWeights.put("nflx", 25.00);
    stockWeights.put("goog", 30.00);

    virtualStockModel.dollarCostAveraging("ThirdPortfolio", stockWeights,
            5000, 60, "05-05-2017",
            "07-05-2018", 30.00);

    virtualStockModel.createStrategy("latestStrategy", stockWeights,
            4000, 60, "05-09-2018",
            "01-12-2018", 47.77);

    virtualStockModel.applyStrategy("latestStrategy", "ThirdPortfolio");

    virtualStockModel.saveSession();

    assertEquals(66061.34, virtualStockModel
            .getTotalCostBasis("ThirdPortfolio", "25-12-2018"), 0.001);

  }

  /**
   * Testing if save portfolio throws IllegalArgumentException if portfolio does not exists.
   */
  @Test
  void testSavePortfolioInvalid() {

    StringReader reader = new StringReader("");
    DataSourceInterface dataSource = DataSourceCreator.getDataSource("ALPHAAPI", reader);

    VirtualStockModelInterface virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.retrievePortfolios();
    virtualStockModel.retrieveStrategies();

    try {
      virtualStockModel.savePortfolio("New port");
      fail("Above line should have thrown exception");
    } catch (IllegalArgumentException e) {
      //Do Nothing
    }

  }

  /**
   * Testing if save portfolio throws IllegalArgumentException if portfolio does not exists.
   */
  @Test
  void testSaveStrategyInvalid() {

    StringReader reader = new StringReader("");
    DataSourceInterface dataSource = DataSourceCreator.getDataSource("ALPHAAPI", reader);

    VirtualStockModelInterface virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.retrievePortfolios();
    virtualStockModel.retrieveStrategies();

    try {
      virtualStockModel.saveStrategy("New strategy");
      fail("Above line should have thrown exception");
    } catch (IllegalArgumentException e) {
      //Do Nothing
    }

  }


  /**
   * Testing if apply strategy throws IllegalArgumentException if strategy is null/empty.
   */
  @Test
  void testSApplyStrategyInvalid() {

    StringReader reader = new StringReader("");
    DataSourceInterface dataSource = DataSourceCreator.getDataSource("ALPHAAPI", reader);

    VirtualStockModelInterface virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.retrievePortfolios();
    virtualStockModel.retrieveStrategies();

    try {
      virtualStockModel.applyStrategy(null, "thirdportfolio");
      fail("Above line should have thrown exception");
    } catch (IllegalArgumentException e) {
      //Do Nothing
    }

  }

  /**
   * Testing if apply strategy throws IllegalArgumentException if strategy does not exist.
   */
  @Test
  void testSApplyStrategyInvalid2() {

    StringReader reader = new StringReader("");
    DataSourceInterface dataSource = DataSourceCreator.getDataSource("ALPHAAPI", reader);

    VirtualStockModelInterface virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.retrievePortfolios();
    virtualStockModel.retrieveStrategies();

    try {
      virtualStockModel.applyStrategy("strat", "thirdportfolio");
      fail("Above line should have thrown exception");
    } catch (IllegalArgumentException e) {
      //Do Nothing
    }

  }

  /**
   * Testing if apply strategy throws IllegalArgumentException if portfolio is empty string.
   */
  @Test
  void testSApplyStrategyInvalid3() {

    StringReader reader = new StringReader("");
    DataSourceInterface dataSource = DataSourceCreator.getDataSource("ALPHAAPI", reader);

    VirtualStockModelInterface virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.retrievePortfolios();
    virtualStockModel.retrieveStrategies();

    try {
      virtualStockModel.applyStrategy("strat", "");
      fail("Above line should have thrown exception");
    } catch (IllegalArgumentException e) {
      //Do Nothing
    }

  }

  /**
   * Testing if apply strategy throws IllegalArgumentException if portfolio is null.
   */
  @Test
  void testSApplyStrategyInvalid4() {

    StringReader reader = new StringReader("");
    DataSourceInterface dataSource = DataSourceCreator.getDataSource("ALPHAAPI", reader);

    VirtualStockModelInterface virtualStockModel = new VirtualStockModel(dataSource);

    virtualStockModel.retrievePortfolios();
    virtualStockModel.retrieveStrategies();

    try {
      virtualStockModel.applyStrategy("strat", null);
      fail("Above line should have thrown exception");
    } catch (IllegalArgumentException e) {
      //Do Nothing
    }

  }

  /**
   * Testing if retrievePortfolios throws IllegalStateException.
   */
  @Test
  public void testRetrievePortfoliosInvalid() {

    StringReader reader = new StringReader("");
    DataSourceInterface dataSource = DataSourceCreator.getDataSource("ALPHAAPI", reader);

    VirtualStockModelInterface virtualStockModel = new VirtualStockModel(dataSource);

    try {
      virtualStockModel.retrievePortfolios();
      fail("Above line should have thrown exception");
    } catch (IllegalStateException e) {
      //Do Nothing
    }

  }

  /**
   * Testing if retrieveStrategies throws IllegalStateException.
   */
  @Test
  public void testRetrieveStrategiesInvalid() {

    StringReader reader = new StringReader("");
    DataSourceInterface dataSource = DataSourceCreator.getDataSource("ALPHAAPI", reader);

    VirtualStockModelInterface virtualStockModel = new VirtualStockModel(dataSource);

    try {
      virtualStockModel.retrieveStrategies();
      fail("Above line should have thrown exception");
    } catch (IllegalStateException e) {
      //Do Nothing
    }

  }

  /**
   * Testing if createStrategy throws IllegalArgumentException because strategy name is null.
   */
  @Test
  public void testCreateStrategyInvalid1() {

    StringReader reader = new StringReader("");
    DataSourceInterface dataSource = DataSourceCreator.getDataSource("ALPHAAPI", reader);

    VirtualStockModelInterface virtualStockModel = new VirtualStockModel(dataSource);

    Map stockWeights = new HashMap<>();

    stockWeights.put("msft", 63.62);
    stockWeights.put("goog", 36.22);

    try {
      virtualStockModel.createStrategy(null, stockWeights, 1000,
              10, "14-11-2018", "24-12-2018", 40.0);
      fail("Above line should have thrown exception");
    } catch (IllegalArgumentException e) {
      //Do Nothing
    }

  }


  /**
   * Testing if createStrategy throws IllegalArgumentException because strategy name is empty.
   */
  @Test
  public void testCreateStrategyInvalid2() {

    StringReader reader = new StringReader("");
    DataSourceInterface dataSource = DataSourceCreator.getDataSource("ALPHAAPI", reader);

    VirtualStockModelInterface virtualStockModel = new VirtualStockModel(dataSource);

    Map stockWeights = new HashMap<>();

    stockWeights.put("msft", 63.62);
    stockWeights.put("goog", 36.22);

    try {
      virtualStockModel.createStrategy("", stockWeights, 1000,
              10, "14-11-2018", "24-12-2018", 40.0);
      fail("Above line should have thrown exception");
    } catch (IllegalArgumentException e) {
      //Do Nothing
    }

  }


  /**
   * Testing if createStrategy throws IllegalArgumentException because amount is negative.
   */
  @Test
  public void testCreateStrategyInvalid3() {

    StringReader reader = new StringReader("");
    DataSourceInterface dataSource = DataSourceCreator.getDataSource("ALPHAAPI", reader);

    VirtualStockModelInterface virtualStockModel = new VirtualStockModel(dataSource);

    Map stockWeights = new HashMap<>();

    stockWeights.put("msft", 63.62);
    stockWeights.put("goog", 36.22);

    try {
      virtualStockModel.createStrategy("", stockWeights, -1000,
              10, "14-11-2018", "24-12-2018", 40.0);
      fail("Above line should have thrown exception");
    } catch (IllegalArgumentException e) {
      //Do Nothing
    }

  }


  /**
   * Testing if createStrategy throws IllegalArgumentException because commission fee is negative.
   */
  @Test
  public void testCreateStrategyInvalid4() {

    StringReader reader = new StringReader("");
    DataSourceInterface dataSource = DataSourceCreator.getDataSource("ALPHAAPI", reader);

    VirtualStockModelInterface virtualStockModel = new VirtualStockModel(dataSource);

    Map stockWeights = new HashMap<>();

    stockWeights.put("msft", 63.62);
    stockWeights.put("goog", 36.22);

    try {
      virtualStockModel.createStrategy("", stockWeights, 1000,
              10, "14-11-2018", "24-12-2018", -40.0);
      fail("Above line should have thrown exception");
    } catch (IllegalArgumentException e) {
      //Do Nothing
    }

  }

  /**
   * Testing if createStrategy throws IllegalArgumentException because start date is after end
   * date.
   */
  @Test
  public void testCreateStrategyInvalid5() {

    StringReader reader = new StringReader("");
    DataSourceInterface dataSource = DataSourceCreator.getDataSource("ALPHAAPI", reader);

    VirtualStockModelInterface virtualStockModel = new VirtualStockModel(dataSource);

    Map stockWeights = new HashMap<>();

    stockWeights.put("msft", 63.62);
    stockWeights.put("goog", 36.22);

    try {
      virtualStockModel.createStrategy("", stockWeights, 1000,
              10, "27-12-2019", "24-12-2018", 40.0);
      fail("Above line should have thrown exception");
    } catch (IllegalArgumentException e) {
      //Do Nothing
    }

  }

  /**
   * Testing if createStrategy throws IllegalArgumentException because start date is empty.
   */
  @Test
  public void testCreateStrategyInvalid6() {

    StringReader reader = new StringReader("");
    DataSourceInterface dataSource = DataSourceCreator.getDataSource("ALPHAAPI", reader);

    VirtualStockModelInterface virtualStockModel = new VirtualStockModel(dataSource);

    Map stockWeights = new HashMap<>();

    stockWeights.put("msft", 63.62);
    stockWeights.put("goog", 36.22);

    try {
      virtualStockModel.createStrategy("", stockWeights, 1000,
              10, "", "24-12-2018", 40.0);
      fail("Above line should have thrown exception");
    } catch (IllegalArgumentException e) {
      //Do Nothing
    }

  }


  /**
   * Testing if createStrategy throws IllegalArgumentException because end date is empty.
   */
  @Test
  public void testCreateStrategyInvalid7() {

    StringReader reader = new StringReader("");
    DataSourceInterface dataSource = DataSourceCreator.getDataSource("ALPHAAPI", reader);

    VirtualStockModelInterface virtualStockModel = new VirtualStockModel(dataSource);

    Map stockWeights = new HashMap<>();

    stockWeights.put("msft", 63.62);
    stockWeights.put("goog", 36.22);

    try {
      virtualStockModel.createStrategy("strat", stockWeights, 1000,
              10, "12-10-2017", "", 40.0);
      fail("Above line should have thrown exception");
    } catch (IllegalArgumentException e) {
      //Do Nothing
    }

  }


  /**
   * Testing if createStrategy throws IllegalArgumentException because sum of percentages of the
   * stocks is not equal to 100.
   */
  @Test
  public void testCreateStrategyInvalid8() {

    StringReader reader = new StringReader("");
    DataSourceInterface dataSource = DataSourceCreator.getDataSource("ALPHAAPI", reader);

    VirtualStockModelInterface virtualStockModel = new VirtualStockModel(dataSource);

    Map stockWeights = new HashMap<>();

    stockWeights.put("msft", 63.62);
    stockWeights.put("goog", 16.22);

    try {
      virtualStockModel.createStrategy("strategy", stockWeights, 1000,
              10, "05-03-2017", "06-03-2018", 40.0);
      fail("Above line should have thrown exception");
    } catch (IllegalArgumentException e) {
      //Do Nothing
    }

  }


  /**
   * Testing if createStrategy throws IllegalArgumentException because investment interval is zero.
   */
  @Test
  public void testCreateStrategyInvalid9() {

    StringReader reader = new StringReader("");
    DataSourceInterface dataSource = DataSourceCreator.getDataSource("ALPHAAPI", reader);

    VirtualStockModelInterface virtualStockModel = new VirtualStockModel(dataSource);

    Map stockWeights = new HashMap<>();

    stockWeights.put("msft", 63.62);
    stockWeights.put("goog", 36.22);

    try {
      virtualStockModel.createStrategy("strategy", stockWeights, 1000,
              0, "05-03-2017", "06-03-2018", 40.0);
      fail("Above line should have thrown exception");
    } catch (IllegalArgumentException e) {
      //Do Nothing
    }

  }


  /**
   * Testing if createStrategy throws IllegalArgumentException because investment interval is
   * negative.
   */
  @Test
  public void testCreateStrategyInvalid10() {

    StringReader reader = new StringReader("");
    DataSourceInterface dataSource = DataSourceCreator.getDataSource("ALPHAAPI", reader);

    VirtualStockModelInterface virtualStockModel = new VirtualStockModel(dataSource);

    Map stockWeights = new HashMap<>();

    stockWeights.put("msft", 63.62);
    stockWeights.put("goog", 36.22);

    try {
      virtualStockModel.createStrategy("strategy", stockWeights, 1000,
              -23, "05-03-2017", "06-03-2018", 40.0);
      fail("Above line should have thrown exception");
    } catch (IllegalArgumentException e) {
      //Do Nothing
    }

  }



}