package controller.command;

import java.util.NoSuchElementException;
import java.util.Scanner;

import model.VirtualStockModelInterface;
import view.IView;

/**
 * This represents the Save Portfolio command class that provides a method which reads portfolio
 * name to run the save portfolio command.
 */
public class SavePortfolio extends AbstractCommand {

  /**
   * Constructs the SavePortfolio object by taking scanner and view objects to read input and
   * display output using view.
   *
   * @param scanner the scanner object that helps to read the input
   * @param view    represents view object
   */
  public SavePortfolio(Scanner scanner, IView view) {

    super(scanner, view);
  }

  /**
   * The method reads portfolio name to perform the save portfolio operation.
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

    while (!isPortfolioRead) {
      if (shouldReturn) {
        return CommandRunStatus.FAILURE;
      }

      try {
        readPortfolio();
      } catch (NoSuchElementException e) {
        throw new IllegalStateException("Unable to read from input");
      }
    }

    try {
      model.savePortfolio(portfolioName);
      writeToOut("Portfolio: " + portfolioName.trim().toUpperCase() + " is saved successfully");
    } catch (IllegalArgumentException e) {
      writeToOut("Portfolio: " + portfolioName.trim().toUpperCase() + " does not exist");
    } catch (IllegalStateException e) {
      writeToOut("Unable to save the portfolio to the file.");
    }

    return CommandRunStatus.SUCCESSFUL;
  }
}
