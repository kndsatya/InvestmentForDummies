package controller.command;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import model.VirtualStockModelInterface;
import view.IView;

/**
 * This represents the Dollar cost average strategy command class that provides a method to read
 * portfolio name, amount, commission fee, stocks and the corresponding percentages, start date,
 * end date and applies the strategy on the given portfolio name.
 */
public class DollarCostStrategy extends AbstractCommand {

  /**
   * Constructs the DollarCostStrategy object by taking scanner and view objects to read inputs and
   * display output using view.
   *
   * @param scanner the scanner object that reads the input
   * @param view    represents view object
   */
  public DollarCostStrategy(Scanner scanner, IView view) {

    super(scanner, view);

  }


  /**
   * The method reads portfolio name, amount, commission fee, start date,end date, investment
   * interval and stocks to be added to the portfolio and the percentage of amount he want to invest
   * to perform the DollarCostStrategy command.
   *
   * @param model the model of the virtual stock investment which is an implementation of the
   *              VirtualStockModelInterface
   * @return CommandRunStatus.SUCCESSFUL if the command runs successfully else
   *         CommandRunStatus.FAILURE
   * @throws IllegalStateException when readable runs out of input or view is unable to write to
   *                               appendable object.
   */
  public CommandRunStatus runCommand(VirtualStockModelInterface model)
          throws IllegalStateException {
    try {
      readInput(model);
    } catch (NoSuchElementException e) {
      throw new IllegalStateException("Unable to read from input");
    }

    if (shouldReturn) {
      return CommandRunStatus.FAILURE;
    }

    try {
      view.displayWaitingMessage();
      model.dollarCostAveraging(portfolioName, stockAndPercent, amount, investmentInterval,
              date, endDate, commissionFee);
      writeToOut("Investment Strategy applied successfully");
    } catch (IllegalArgumentException e) {
      writeToOut(e.getMessage());
    }
    return CommandRunStatus.SUCCESSFUL;
  }


  /**
   * A private helper method to read input from readable object.
   *
   * @param model represents the model object.
   */
  private void readInput(VirtualStockModelInterface model) {

    List<String> stocksInAPortfolio = new ArrayList<>();

    while (!( isPortfolioRead && isAmountRead && areStocksRead && isStockPercentRead
            && isCommissionFeeRead && isDateRead && isEndDateRead && isInvestmentIntervalRead )) {

      if (!isPortfolioRead) {
        if (shouldReturn) {
          return;
        }
        readPortfolio();
        continue;
      }

      if (!isAmountRead) {
        if (shouldReturn) {
          return;
        }
        readAmount();
        continue;
      }

      if (!areStocksRead) {

        if (shouldReturn) {
          return;
        }

        stocksInAPortfolio = readStocks();
        continue;
      }

      if (!isStockPercentRead) {
        if (shouldReturn) {
          return;
        }
        readStockPercent(stocksInAPortfolio);
        continue;
      }


      if (!isCommissionFeeRead) {
        if (shouldReturn) {
          return;
        }
        readCommissionFee();
        continue;
      }

      if (!isDateRead) {
        if (shouldReturn) {
          return;
        }
        readDate("Please enter start date of investment in format:dd-mm-yyyy");
        continue;
      }

      if (!isEndDateRead) {
        if (shouldReturn) {
          return;
        }
        readEndDate();
        continue;
      }

      if (!isInvestmentIntervalRead) {
        if (shouldReturn) {
          return;
        }
        readInvestmentInterval();
        continue;
      }

    }
  }

}
