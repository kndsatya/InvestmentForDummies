package controller.command;

import java.util.NoSuchElementException;
import java.util.Scanner;

import model.VirtualStockModelInterface;
import view.IView;

/**
 * This represents the Save Strategy command class that provides a method which reads strategy name
 * to run the save strategy command.
 */
public class SaveStrategy extends AbstractCommand {

  /**
   * Constructs the SaveStrategy object by taking scanner and view objects to read input and display
   * output using view.
   *
   * @param scanner the scanner object that reads the input
   * @param view    represents view object
   */
  public SaveStrategy(Scanner scanner, IView view) {

    super(scanner, view);
  }

  /**
   * The method reads strategy name to perform the save strategy operation.
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

    while (!isStrategyNameRead) {
      if (shouldReturn) {
        return CommandRunStatus.FAILURE;
      }

      try {
        readStrategyName();
      } catch (NoSuchElementException e) {
        throw new IllegalStateException("Unable to read from input");
      }
    }

    try {
      model.saveStrategy(strategyName);
      writeToOut("Strategy: " + strategyName.trim().toUpperCase() + " is saved successfully");
    } catch (IllegalArgumentException e) {

      writeToOut(e.getMessage());
    } catch (IllegalStateException e) {
      writeToOut(e.getMessage());
    }

    return CommandRunStatus.SUCCESSFUL;
  }
}
