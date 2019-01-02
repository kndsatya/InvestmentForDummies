package controller;

import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;
import java.util.Objects;
import java.util.function.Supplier;

import controller.command.AddStock;
import controller.command.ApplyStrategy;
import controller.command.BuyShare;
import controller.command.CommandInterface;
import controller.command.CommandRunStatus;
import controller.command.CreatePortfolio;
import controller.command.CreateStrategy;
import controller.command.DisplayAllPortfolios;
import controller.command.DisplayPortfolio;
import controller.command.DisplayStocksInAPortfolio;
import controller.command.DollarCostStrategy;
import controller.command.Invest;
import controller.command.SavePortfolio;
import controller.command.SaveSession;
import controller.command.SaveStrategy;
import controller.command.TotalCostBasis;
import controller.command.TotalValue;
import model.VirtualStockModelInterface;
import view.IView;

/**
 * This class represents the controller of the virtual stock investment that implements the
 * ControllerInterface. The class has only one method -run() that takes in the commands and calls
 * the corresponding model method to perform the particular utility operation that the command
 * intends to achieve.
 *
 * <p>As part of change for assignment 9, new commands like INVEST, APPLY_DOLLAR_COST_STRATEGY
 * have been added to the list of commands</p>
 */
public class Controller implements ControllerInterface {

  private final Readable in;
  private final IView view;


  /**
   * Constructs the controller object by taking in Readable and view objects to get input and pass
   * output.
   *
   * @param rd   Readable object to take commands
   * @param view represents a view object.
   * @throws IllegalArgumentException when either of the Readable or view object is null.
   */
  public Controller(Readable rd, IView view) throws IllegalArgumentException {

    if (checkForNull(rd)) {
      throw new IllegalArgumentException("Readable object can't be null");
    }

    if (checkForNull(view)) {
      throw new IllegalArgumentException("Appendable object can't be null");
    }


    this.in = rd;
    this.view = view;
  }


  /**
   * checks if an object is null.
   *
   * @param object any object in java.
   * @return true if the object is null else false.
   */
  private boolean checkForNull(Object object) {

    try {
      Objects.requireNonNull(object);
    } catch (NullPointerException e) {
      return true;
    }

    return false;

  }


  /**
   * <p>The method reads the commands and performs the corresponding operations related to virtual
   * stock investment. The method uses a HashMap to store the commands as keys and supplier
   * functions that supplies the corresponding command objects.</p>
   *
   * <p>The input commands should be in the specified format below.
   * First comes the command (case insensitive) for example to create portfolio, the first line
   * should contain 'CREATE_PORTFOLIO' command followed by portfolio name on a separate line. Each
   * input should be on a newline. When the controller encounters 'q' or 'quit' (case insensitive)
   * it ends the application running.</p>
   *
   * @param model the model of the virtual stock investment which is an implementation of the
   *              VirtualStockModelInterface
   * @throws IllegalStateException    when input cannot be read from readable and output cannot be
   *                                  appended to the appendable object.
   * @throws IllegalArgumentException if the passed model is null
   */
  public void run(VirtualStockModelInterface model) throws IllegalStateException,
          IllegalArgumentException {

    if (model == null) {
      throw new IllegalArgumentException("model can't be null");
    }
    try {
      view.displayWaitingMessage();
      model.retrieveStrategies();
      model.retrievePortfolios();
      view.writeToAppendable("Loaded data successfully");
    } catch (IllegalStateException|IllegalArgumentException e) {
      view.writeErrorOrQuitMessage(e.getMessage());
      return;
    }
    Scanner scanner = new Scanner(in);
    String command;
    Map<String, Supplier<CommandInterface>> knownCommands = new HashMap<>();
    knownCommands.put("CREATE_PORTFOLIO", () -> new CreatePortfolio(scanner, view));
    knownCommands.put("DISPLAY_PORTFOLIO", () -> new DisplayPortfolio(scanner, view));
    knownCommands.put("BUY_STOCK", () -> new BuyShare(scanner, view));
    knownCommands.put("GET_TOTAL_COST_BASIS", () -> new TotalCostBasis(scanner, view));
    knownCommands.put("GET_TOTAL_VALUE", () -> new TotalValue(scanner, view));
    knownCommands.put("DISPLAY_ALL_PORTFOLIOS", () -> new DisplayAllPortfolios(scanner, view));
    knownCommands.put("INVEST", () -> new Invest(scanner, view));
    knownCommands.put("ADD_STOCK", () -> new AddStock(scanner, view));
    knownCommands.put("APPLY_DOLLAR_COST_STRATEGY", () -> new DollarCostStrategy(scanner, view));
    knownCommands.put("DISPLAY_STOCKS_IN_PORTFOLIO", () -> new DisplayStocksInAPortfolio(scanner,
            view));

    knownCommands.put("SAVE_PORTFOLIO", () -> new SavePortfolio(scanner, view));
    knownCommands.put("SAVE_STRATEGY", () -> new SaveStrategy(scanner, view));
    knownCommands.put("CREATE_STRATEGY", () -> new CreateStrategy(scanner, view));
    knownCommands.put("APPLY_STRATEGY", () -> new ApplyStrategy(scanner, view));
    knownCommands.put("SAVE_SESSION", () -> new SaveSession(scanner, view));

    while (true) {
      try {
        view.displayCommands();
        command = scanner.nextLine();
      } catch (NoSuchElementException e) {
        throw new IllegalStateException("Input should end with a q or Quit");
      }
      if (command.equalsIgnoreCase("q")
              || command.equalsIgnoreCase("quit")) {

        view.writeErrorOrQuitMessage("Closing the application..........");
        return;
      }
      Supplier<CommandInterface> cmd = knownCommands.getOrDefault(command.toUpperCase(),
              null);
      if (cmd == null) {

        view.writeErrorOrQuitMessage("An invalid command is provided");

      } else {
        CommandInterface commandControl = cmd.get();
        if (commandControl.runCommand(model) == CommandRunStatus.FAILURE) {
          return;
        }
      }

    }

  }

}
