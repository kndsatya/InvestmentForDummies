package controller.command;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import model.VirtualStockModelInterface;
import view.IView;

/**
 * This represents the displayALLPortfolio command class that provides a method to read portfolio
 * name and run the displayAllPortfolios command.
 */
public class DisplayAllPortfolios extends AbstractCommand {

  /**
   * Constructs the displayAllPortfolio object by taking scanner and view objects to read input and
   * display output using view.
   *
   * @param scanner the scanner object that reads the input
   * @param view    represents view object
   */
  public DisplayAllPortfolios(Scanner scanner, IView view) {
    super(scanner, view);
  }

  /**
   * The method performs the displayAllPortfolios operation. pass on information sent by model to
   * view so that view can display  contents of all portfolios.
   *
   * @param model the model of the virtual stock investment which is an implementation of the
   *              VirtualStockModelInterface
   * @return CommandRunStatus.SUCCESSFUL if the command runs successfully else
   *         CommandRunStatus.FAILURE
   * @throws IllegalStateException when readable runs out of input or view is unable to write to the
   *                               appendable object.
   */
  public CommandRunStatus runCommand(VirtualStockModelInterface model)
          throws IllegalStateException {

    Map<String, List<String>> portfoliosAndContents = model.displayAllPortfolios();
    view.displayAllPortfolios(portfoliosAndContents);
    return CommandRunStatus.SUCCESSFUL;
  }
}
