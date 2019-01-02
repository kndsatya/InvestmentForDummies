package controller.command;

import java.util.NoSuchElementException;
import java.util.Scanner;

import model.VirtualStockModelInterface;
import view.IView;

/**
 * This represents the create command class that provides a method which reads portfolio name to run
 * the create portfolio command.
 */
public class CreatePortfolio extends AbstractCommand {

  /**
   * Constructs the create portfolio object by taking scanner and view objects to read input
   * and display output using view.
   *
   * @param scanner the scanner object that reads the input
   * @param view    represents view object
   */
  public CreatePortfolio(Scanner scanner, IView view) {

    super(scanner, view);

  }

  /**
   * The method reads portfolio name to perform the createPortfolio operation.
   *
   * @param model the model of the virtual stock investment which is an implementation of the
   *              VirtualStockModelInterface
   * @return CommandRunStatus.SUCCESSFUL if the command runs successfully else
   *         CommandRunStatus.FAILURE
   * @throws IllegalStateException when readable runs out of input or view is unable to write to
   *                               view object.
   */
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
      model.createPortfolio(portfolioName);
      writeToOut("Portfolio: " + portfolioName.trim().toUpperCase() + " is created successfully");
    } catch (IllegalArgumentException e) {
      writeToOut("Portfolio: " + portfolioName.trim().toUpperCase() + " already exists");
    }

    return CommandRunStatus.SUCCESSFUL;
  }
}
