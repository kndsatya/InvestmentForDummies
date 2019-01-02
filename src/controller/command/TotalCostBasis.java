package controller.command;

import java.util.NoSuchElementException;
import java.util.Scanner;

import model.VirtualStockModelInterface;
import view.IView;


/**
 * This represents the TotalCostBasis command class that provides a method to read portfolio name
 * and perform the TotalCostBasis operation.
 */
public class TotalCostBasis extends AbstractCommand {


  /**
   * Constructs the TotalCostBasis object by taking scanner and view objects to read and write
   * output using view.
   *
   * @param scanner the scanner object that reads the input
   * @param view    represents view object
   */
  public TotalCostBasis(Scanner scanner, IView view) {
    super(scanner, view);

  }


  /**
   * The method performs the TotalCostBasis operation. pass total cost basis value returned by model
   * to view so that it can display to the user.
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
      readInput();
    } catch (NoSuchElementException e) {
      throw new IllegalStateException("Unable to read input");
    }
    if (shouldReturn) {
      return CommandRunStatus.FAILURE;
    }
    try {
      double costbasis = model.getTotalCostBasis(portfolioName, date);
      writeToOut("Total Cost Basis of portfolio: " + portfolioName + " on " + date + " is: $ "
              + String.format("%.2f", costbasis));
    } catch (IllegalArgumentException e) {
      writeToOut(e.getMessage());
    }

    return CommandRunStatus.SUCCESSFUL;
  }

  /**
   * A helper method to read date and portfolio name.
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
