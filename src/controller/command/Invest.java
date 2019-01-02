package controller.command;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import model.VirtualStockModelInterface;
import view.IView;

/**
 * This represents the Invest command class that provides a method to
 * read portfolio name, amount, commission fee, stocks and the corresponding percentages,
 * start date, end date and buys multiple stocks and add them to the given portfolio name.
 */
public class Invest extends AbstractCommand {

  /**
   * constructs a Invest object by taking scanner and view objects as arguments.
   * @param scanner represents the scanner object.
   * @param view represents the view object.
   */
  public Invest(Scanner scanner, IView view) {
    super(scanner, view);

  }

  /**
   * The method reads Ticker Symbol, portfolio name, amount of investment, percentage of  amount to
   * invest for each stock in a portfolio, commission fee for a transaction and date of investment
   * to run INVEST command.
   *
   * @param model the model of the virtual stock investment which is an implementation of the
   *              VirtualStockModelInterface
   * @return CommandRunStatus.SUCCESSFUL if the command runs successfully else
   *         CommandRunStatus.FAILURE
   * @throws IllegalStateException when readable runs out of input or view is unable to write to
   *                               appendable.
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

    if (!isInputReadSuccessfully) {
      return CommandRunStatus.SUCCESSFUL;
    }


    try {
      view.displayWaitingMessage();
      String message = model.invest(portfolioName, stockAndPercent, amount, commissionFee, date);
      writeToOut(message);
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

    while (!( isPortfolioRead && isAmountRead && isStockPercentRead && isCommissionFeeRead
            && isDateRead )) {

      if (!isPortfolioRead) {
        if (shouldReturn) {
          return;
        }
        readPortfolio();

        if (isPortfolioRead) {
          try {
            stocksInAPortfolio = model.getStocksOfAPortfolio(portfolioName);
          } catch (IllegalArgumentException e) {
            view.writeToAppendable("Investment Can't be done as the portfolio: " + portfolioName
                    + " doesn't exist. Please run the INVEST command after creating the portfolio");
            return;
          }
        }


        if (isPortfolioRead && stocksInAPortfolio.size() == 0) {
          view.writeToAppendable("Portfolio: " + portfolioName + " doesn't contains any stocks."
                  + " Please add stocks and then run the INVEST command");
          return;
        }
        continue;
      }

      if (!isAmountRead) {
        if (shouldReturn) {
          return;
        }
        readAmount();
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
        readDateTime();
        continue;
      }

    }
    isInputReadSuccessfully = true;
  }

}
