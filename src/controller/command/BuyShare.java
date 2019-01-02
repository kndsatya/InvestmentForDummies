package controller.command;

import java.util.NoSuchElementException;
import java.util.Scanner;

import model.VirtualStockModelInterface;
import view.IView;

/**
 * This represents the BuyShare command class that provides a method which reads Ticker Symbol,
 * portfolio name, amount, date and commission fee to run the buyShare command.
 * It extends AbstractCommand class.
 */
public class BuyShare extends AbstractCommand {

  /**
   * Constructs the BuyShare object by taking scanner and view objects to read input
   * and display output using view.
   *
   * @param scanner the scanner object that reads the input
   * @param view    represents view object
   */
  public BuyShare(Scanner scanner, IView view) {
    super(scanner, view);

  }


  /**
   * The method reads Ticker Symbol, portfolio name, amount, date and commission fee
   * to perform the buyShare command.
   *
   * <p>A change from the previous version is we have added option to read the commission fee</p>
   * @param model the model of the virtual stock investment which is an implementation of the
   *              VirtualStockModelInterface
   * @return CommandRunStatus.SUCCESSFUL if the command runs successfully else
   *         CommandRunStatus.FAILURE
   * @throws IllegalStateException when readable runs out of input or view is unable to write to
   *                               appendable object
   */
  public CommandRunStatus runCommand(VirtualStockModelInterface model)
          throws IllegalStateException {
    try {
      readInput();
    } catch (NoSuchElementException e) {
      throw new IllegalStateException("Unable to read from input");
    }

    if (shouldReturn) {
      return CommandRunStatus.FAILURE;
    }

    try {
      view.displayWaitingMessage();
      model.buySharesOfStock(tickerSymbol, portfolioName, amount, date, commissionFee);
      writeToOut("Stock: " + tickerSymbol + " is bought successfully");
    } catch (IllegalArgumentException | UnsupportedOperationException e) {
      writeToOut(e.getMessage());
    }
    return CommandRunStatus.SUCCESSFUL;
  }


  /**
   * A private helper method to read input from readable object.
   */
  private void readInput() {
    while (!( isAmountRead && isDateRead && isPortfolioRead && isTickerRead
            && isCommissionFeeRead )) {

      if (!isTickerRead) {
        if (shouldReturn) {
          return;
        }
        readTicker();
        continue;
      }

      if (!isPortfolioRead) {
        if (shouldReturn) {
          return;
        }
        readPortfolio();
        continue;
      }

      if (!isDateRead) {
        if (shouldReturn) {
          return;
        }
        readDateTime();
        continue;
      }

      if (!isAmountRead) {
        if (shouldReturn) {
          return;
        }
        readAmount();
        continue;
      }

      if (!isCommissionFeeRead) {
        if (shouldReturn) {
          return;
        }
        readCommissionFee();
        continue;
      }
    }
  }


}
