package controller.command;

import java.util.NoSuchElementException;
import java.util.Scanner;

import model.VirtualStockModelInterface;
import view.IView;

/**
 * This represents the TotalValue command class that provides a method to read portfolio name and
 * date to perform the TotalValue operation.
 */
public class TotalValue extends AbstractCommand {

  /**
   * Constructs the TotalValue object by taking scanner and view objects to read and
   * output is displayed using view.
   *
   * @param scanner the scanner object that reads the input
   * @param view    represents view object
   */
  public TotalValue(Scanner scanner, IView view) {
    super(scanner, view);
  }

  /**
   * The method performs the TotalValue operation. provides the double price returned by model
   * to the appendable object.
   *
   * @param model the model of the virtual stock investment which is an implementation of the
   *              VirtualStockModelInterface
   * @return CommandRunStatus.SUCCESSFUL if the command runs successfully else
   *         CommandRunStatus.FAILURE
   * @throws IllegalStateException when readable runs out of input or view is unable to write to
   *                               the appendable object.
   */
  public CommandRunStatus runCommand(VirtualStockModelInterface model)
          throws IllegalStateException {
    try {
      readInput();
    } catch (NoSuchElementException e) {
      throw new IllegalStateException("Unable to read input");
    }
    if (shouldReturn) {
      return CommandRunStatus.FAILURE;
    }

    try {
      double totalValue = model.getTotalValue(portfolioName, date);
      writeToOut("Total cost value of portfolio: " + portfolioName + " on " + date + " is: $"
              + String.format("%.2f", totalValue));
    } catch (IllegalArgumentException e) {
      writeToOut(e.getMessage());
    }
    return CommandRunStatus.SUCCESSFUL;
  }

  /**
   * A helper method to read date and portfolio name from the readable object.
   */
  private void readInput() {
    while (!( isDateRead && isPortfolioRead )) {

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
        readDate("Please enter date in format:dd-mm-yyyy");
        continue;
      }
    }
  }
}
