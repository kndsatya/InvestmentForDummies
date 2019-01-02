package controller.command;

import java.util.NoSuchElementException;
import java.util.Scanner;

import model.VirtualStockModelInterface;
import view.IView;

/**
 * This represents the Apply Strategy command class that provides a method which reads strategy
 * name and portfolio name to run the apply strategy command thereby applying the strategy on the
 * portfolio.
 */
public class ApplyStrategy extends AbstractCommand {

  /**
   * Constructs the ApplyStrategy object by taking scanner and view objects to read input and
   * display output using view.
   *
   * @param scanner the scanner object that reads the input
   * @param view    represents view object
   */
  public ApplyStrategy(Scanner scanner, IView view) {

    super(scanner, view);
  }

  /**
   * The method reads strategy name and portfolio name to run the apply strategy command.
   *
   * @param model represents the VirtualStockModel object.
   * @return CommandRunStatus.SUCCESSFUL if the command runs successfully else
   *         CommandRunStatus.FAILURE
   * @throws IllegalStateException when readable runs out of input or view is unable to write.
   */
  @Override
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
      view.displayWaitingMessage();
      model.applyStrategy(strategyName, portfolioName);
      writeToOut("Strategy: " + strategyName + " has been successfully applied on the "
              + "portfolio " + portfolioName);
    } catch (IllegalArgumentException e) {
      writeToOut(e.getMessage());
    }
    return CommandRunStatus.SUCCESSFUL;

  }

  /**
   * A private helper method to read input from readable object.
   */
  private void readInput() {

    while (!( isPortfolioRead && isStrategyNameRead )) {

      if (!isPortfolioRead) {
        if (shouldReturn) {
          return;
        }
        readPortfolio();
        continue;
      }

      if (!isStrategyNameRead) {
        if (shouldReturn) {
          return;
        }
        readStrategyName();
        continue;
      }
    }
  }
}
