package controller.command;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import model.VirtualStockModelInterface;
import view.IView;

/**
 * This represents the displayPortfolio command class that provides a method to read portfolio name
 * and run the display Portfolio operation.
 */
public class DisplayPortfolio extends AbstractCommand {

  /**
   * Constructs the displayPortfolio object by taking scanner and view objects to read input and
   * display output using view.
   *
   * @param scanner the scanner object that reads the input
   * @param view    represents the view object.
   */
  public DisplayPortfolio(Scanner scanner, IView view) {
    super(scanner, view);
  }

  /**
   * The method performs the displayPortfolios operation. pass on information sent by model to view
   * so that view can display portfolio contents.
   *
   * @param model the model of the virtual stock investment which is an implementation of the
   *              VirtualStockModelInterface
   * @return CommandRunStatus.SUCCESSFUL if the command runs successfully else
   *         CommandRunStatus.FAILURE
   * @throws IllegalStateException when readable runs out of input and appendable cannot write
   */
  public CommandRunStatus runCommand(VirtualStockModelInterface model)
          throws IllegalStateException {
    readInput();
    if (shouldReturn) {
      return CommandRunStatus.FAILURE;
    }
    List<String> portfolioContent = model.displayPortfolio(portfolioName);
    view.displayPortfolio(portfolioContent, portfolioName);
    return CommandRunStatus.SUCCESSFUL;
  }


  /**
   * A helper method to read the portfolio name from the readable object.
   */
  private void readInput() {

    while (!isPortfolioRead) {

      if (shouldReturn) {
        return;
      }
      try {
        readPortfolio();
      } catch (NoSuchElementException e) {
        throw new IllegalStateException("Unable to read from input");
      }
    }
  }
}
