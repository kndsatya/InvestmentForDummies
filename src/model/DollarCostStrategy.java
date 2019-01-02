package model;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * This class represents the Dollar cost strategy that can be applied on a portfolio over a certain
 * duration with specified stocks and corresponding weights for a fixed amount, at a specific
 * investment interval and commission fee.
 */
public class DollarCostStrategy {

  private final String strategyName;
  private final LocalDate startDate;
  private final LocalDate endDate;
  private final int investmentInterval;
  private final double commissionFee;
  private final double amount;
  private final Map<String, Double> stockAndPercentOfInvestment;
  private LocalDate nextBuyDate;

  /**
   * Constructs a dollar cost strategy object by taking in strategy name, start date, end date,
   * investment interval, commission fee, amount, stocks and corresponding percentages.
   *
   * @param strategyName                the name of the strategy
   * @param startDate                   the start date of the investment strategy
   * @param endDate                     the end date of the investment strategy
   * @param investmentInterval          the frequency of the investment in days.
   * @param commissionFee               the fee paid for brokerage. The commission fee is applicable
   *                                    for 'n' number of stocks of each company stock. If the
   *                                    stocks are Google and Apple and if the commission fee is
   *                                    $100, then in total commission would be $200.
   * @param amount                      the fixed amount to be invested periodically.
   * @param stockAndPercentOfInvestment A map of stock names and corresponding percentage weight of
   *                                    the amount to be invested for that particular stock.
   */
  public DollarCostStrategy(String strategyName, LocalDate startDate, LocalDate endDate,
                            int investmentInterval, double commissionFee, double amount,
                            Map<String, Double> stockAndPercentOfInvestment) {

    this.strategyName = strategyName;
    this.startDate = startDate;
    this.endDate = endDate;
    this.investmentInterval = investmentInterval;
    this.commissionFee = commissionFee;
    this.amount = amount;
    this.stockAndPercentOfInvestment = stockAndPercentOfInvestment;
    this.nextBuyDate = null;
  }

  /**
   * Constructs a strategy object by taking start date, end date, investment interval, commission
   * fee, amount, stocks and corresponding percentages. This constructor does not take name for the
   * strategy.
   *
   * @param startDate                   the start date of the investment strategy
   * @param endDate                     the end date of the investment strategy
   * @param investmentInterval          the frequency of the investment in days.
   * @param commissionFee               the fee paid for brokerage. The commission fee is applicable
   *                                    for 'n' number of stocks of each company stock. If the
   *                                    stocks are Google and Apple and if the commission fee is
   *                                    $100, then in total commission would be $200.
   * @param amount                      the fixed amount to be invested periodically.
   * @param stockAndPercentOfInvestment A map of stock names and corresponding percentage weight of
   *                                    the amount to be invested for that particular stock.
   * @param nextBuyDate                 represents a date on which the stocks will be bought using
   *                                    the strategy.
   */
  public DollarCostStrategy(LocalDate startDate, LocalDate endDate, int investmentInterval,
                            double commissionFee, double amount,
                            Map<String, Double> stockAndPercentOfInvestment,
                            LocalDate nextBuyDate) {

    this.strategyName = "";
    this.startDate = startDate;
    this.endDate = endDate;
    this.investmentInterval = investmentInterval;
    this.commissionFee = commissionFee;
    this.amount = amount;
    this.stockAndPercentOfInvestment = stockAndPercentOfInvestment;
    this.nextBuyDate = nextBuyDate;
  }

  /**
   * Constructs a strategy object by taking start date, investment interval, commission fee, amount,
   * stocks and corresponding percentages. This constructor does not take name for the strategy.
   * This constructor does not take end date.
   *
   * @param startDate                   the start date of the investment strategy
   * @param investmentInterval          the frequency of the investment in days.
   * @param commissionFee               the fee paid for brokerage. The commission fee is applicable
   *                                    for 'n' number of stocks of each company stock. If the
   *                                    stocks are Google and Apple and if the commission fee is
   *                                    $100, then in total commission would be $200.
   * @param amount                      the fixed amount to be invested periodically.
   * @param stockAndPercentOfInvestment A map of stock names and corresponding percentage weight of
   *                                    the amount to be invested for that particular stock.
   * @param  nextBuyDate                 represents a date on which the stocks will be bought using
   *                                    the strategy.
   */
  public DollarCostStrategy(LocalDate startDate, int investmentInterval,
                            double commissionFee, double amount,
                            Map<String, Double> stockAndPercentOfInvestment,
                            LocalDate nextBuyDate) {

    this.strategyName = "";
    this.startDate = startDate;
    this.endDate = null;
    this.investmentInterval = investmentInterval;
    this.commissionFee = commissionFee;
    this.amount = amount;
    this.stockAndPercentOfInvestment = stockAndPercentOfInvestment;
    this.nextBuyDate = nextBuyDate;
  }

  /**
   * Getter method that returns the start date of the strategy.
   *
   * @return start date of the strategy
   */
  LocalDate getStartDate() {
    return this.startDate;
  }

  /**
   * Getter method that returns the end date of the strategy.
   *
   * @return end date of the strategy
   */
  LocalDate getEndDate() {
    return this.endDate;
  }

  /**
   * Getter method that returns the investment interval of the strategy.
   *
   * @return investment interval of the strategy
   */
  int getInvestmentInterval() {
    return this.investmentInterval;
  }

  /**
   * Getter method that returns the fixed investment amount of the strategy.
   *
   * @return investment amount of the strategy
   */
  double getAmount() {
    return this.amount;
  }

  /**
   * Getter method that returns the commission fee of the strategy.
   *
   * @return commission fee of the strategy
   */
  double getCommissionFee() {
    return commissionFee;
  }

  /**
   * Getter method that returns the stocks and their corresponding weights of the strategy.
   *
   * @return the stocks and their corresponding weights of the strategy
   */
  Map<String, Double> getStockAndPercentOfInvestment() {
    return new HashMap<>(stockAndPercentOfInvestment);
  }

  /**
   * Getter method that returns the name of the strategy.
   *
   * @return the name of the strategy
   */
  String getStrategyName() {
    return this.strategyName;
  }

  /**
   * Getter method that returns the next investment date of the strategy.
   *
   * @return the next investment date of the strategy
   */
  LocalDate getNextBuyDate() {
    return LocalDate.of(nextBuyDate.getYear(), nextBuyDate.getMonth(), nextBuyDate.getDayOfMonth());
  }

  /**
   * Setter method that sets the next investment date of the strategy.
   */
  void setNextBuyDate(LocalDate newNextBuyDate) {
    this.nextBuyDate = newNextBuyDate;
  }

}
