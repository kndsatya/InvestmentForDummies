package controller.command;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import model.VirtualStockModelInterface;
import view.IView;

/**
 * This represents the Create Strategy command class that provides a method which reads strategy
 * name to run the create strategy command.
 */
public class CreateStrategy extends AbstractCommand {

  /**
   * Constructs the CreateStrategy object by taking scanner and view objects to read input
   * and display output using view.
   *
   * @param scanner the scanner object that reads the input
   * @param view    represents view object
   */
  public CreateStrategy(Scanner scanner, IView view) {

    super(scanner, view);
  }

  /**
   * The method reads strategy name, start date, end date, amount, commission fee, investment
   * interval, stocks and their corresponding weights to perform the create strategy operation.
   *
   * @param model the model of the virtual stock investment which is an implementation of the
   *              VirtualStockModelInterface
   * @return CommandRunStatus.SUCCESSFUL if the command runs successfully else
   *         CommandRunStatus.FAILURE
   * @throws IllegalStateException when readable runs out of input or view is unable to write to
   *                               view object.
   */
  @Override
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
      model.createStrategy(strategyName, stockAndPercent, amount, investmentInterval,
              date, endDate, commissionFee);
      writeToOut("Strategy: " + strategyName + " created successfully");
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

    while (!( isStrategyNameRead && isAmountRead && areStocksRead && isStockPercentRead
            && isCommissionFeeRead && isDateRead && isEndDateRead && isInvestmentIntervalRead )) {

      if (!isStrategyNameRead) {
        if (shouldReturn) {
          return;
        }
        readStrategyName();
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
