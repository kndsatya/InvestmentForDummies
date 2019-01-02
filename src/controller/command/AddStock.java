package controller.command;

import java.util.NoSuchElementException;
import java.util.Scanner;

import model.VirtualStockModelInterface;
import view.IView;


/**
 * This represents the addStock command class that provides a method to read ticker symbol and
 * portfolio name to add stock to a portfolio.
 */
public class AddStock extends AbstractCommand {

  /**
   * Constructs the AddStock object by taking scanner and view objects to read input
   * and display output using view.
   * @param scanner the scanner object that reads the input
   * @param view represents view object
   */
  public AddStock(Scanner scanner, IView view) {

    super(scanner, view);

  }


  /**
   * The method reads Ticker Symbol, portfolio name to run the addStock command.
   *
   * @param model the model of the virtual stock investment which is an implementation of the
   *              VirtualStockModelInterface
   * @return CommandRunStatus.SUCCESSFUL if the command runs successfully else
   *         CommandRunStatus.FAILURE
   * @throws IllegalStateException when readable runs out of input or view is unable to write.
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
      model.addStock(portfolioName, tickerSymbol);
      writeToOut("Stock: " + tickerSymbol + " is added to the portfolio: " + portfolioName
              + " successfully");
    } catch (IllegalArgumentException e) {
      writeToOut("Stock: " + tickerSymbol + " is not listed yet in stock market. Till it is listed"
              + " in"
              + "  the stock market you are not allowed to add it to a portfolio");
    } catch (UnsupportedOperationException e) {
      writeToOut(e.getMessage());
    }

    return CommandRunStatus.SUCCESSFUL;
  }


  /**
   * A private helper method to read input from readable object.
   */
  private void readInput() {
    while (!( isPortfolioRead && isTickerRead )) {


      if (!isPortfolioRead) {
        if (shouldReturn) {
          return;
        }
        readPortfolio();
        continue;
      }

      if (!isTickerRead) {
        if (shouldReturn) {
          return;
        }
        readTicker();
        continue;
      }

    }
  }
}
