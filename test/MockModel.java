import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.VirtualStockModelInterface;

/**
 * Represents a mock model that's a dummy of the concrete implementation of Virtual Stock
 * investment model.
 */
public class MockModel implements VirtualStockModelInterface {

  private StringBuffer log;
  private final double uniqueValue;
  private final StringBuilder uniqueString;

  /**
   * Constructs a mock model provided with log, unique value and unique string as parameters.
   *
   * @param log          Represents the input log that logs all the input provided to the model by
   *                     the controller.
   * @param uniqueValue  represents a unique value that will be returned by a model to the
   *                     controller.
   * @param uniqueString represents a unique string that will be returned by a model to the
   */
  public MockModel(StringBuffer log, double uniqueValue, StringBuilder uniqueString) {
    this.log = log;
    this.uniqueValue = uniqueValue;
    this.uniqueString = uniqueString;
  }

  @Override
  public void createPortfolio(String name) throws IllegalArgumentException {
    log.append(name);
  }

  @Override
  public void buySharesOfStock(String tickerSymbol, String portfolioName, double amount,
                               String date) throws IllegalArgumentException {
    log.append(tickerSymbol + " " + portfolioName + " " + amount + " " + date);

  }

  @Override
  public void buySharesOfStock(String tickerSymbol, String portfolioName, double amount,
                               String date, double commissionFee) throws IllegalArgumentException {
    log.append(tickerSymbol + " " + portfolioName + " " + amount + " " + date + " "
            + commissionFee);

  }

  @Override
  public double getTotalCostBasis(String portfolioName, String date)
          throws IllegalArgumentException {
    log.append(portfolioName).append(" ").append(date);
    return uniqueValue;
  }

  @Override
  public double getTotalValue(String portfolioName, String date)
          throws IllegalArgumentException {
    log.append(portfolioName).append(" ").append(date);
    return uniqueValue;
  }

  @Override
  public List<String> displayPortfolio(String portfolioName)
          throws IllegalArgumentException {
    log.append(portfolioName);
    List<String> portfolioContent = new ArrayList<>();
    portfolioContent.add(uniqueString.toString());
    return portfolioContent;
  }

  @Override
  public Map<String,List<String>> displayAllPortfolios() {
    Map<String,List<String>> allportcontents = new HashMap<>();
    List<String> portfolioContent = new ArrayList<>();
    portfolioContent.add(uniqueString.toString());
    allportcontents.put(uniqueString.toString(),portfolioContent);
    return allportcontents;
  }

  @Override
  public String invest(String portfolioName, Map<String, Double> stockNameAndWeight, double amount,
                       double commissionFee, String date) {

    log.append(portfolioName).append(" ").append(stockNameAndWeight).append(" ").append(amount)
            .append(" ").append(commissionFee).append(" ").append(date);
    return uniqueString.toString();
  }

  @Override
  public void dollarCostAveraging(String portfolioName, Map<String, Double> stockNameAndWeight,
                                  double amount, int investmentInterval, String startDate,
                                  String endDate, double commissionFee) {
    log.append(portfolioName).append(" ").append(stockNameAndWeight).append(" ").append(amount)
            .append(" ").append(investmentInterval).append(" ").append(startDate).append(" ")
            .append(endDate).append(" ").append(commissionFee);
  }

}