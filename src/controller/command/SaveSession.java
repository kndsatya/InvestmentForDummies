package controller.command;

import java.util.Scanner;

import model.VirtualStockModelInterface;
import view.IView;

/**
 * This represents the Save Session command class that provides a method to run the save session
 * command.
 */
public class SaveSession extends AbstractCommand {

  /**
   * Constructs the SaveSession object by taking scanner and view objects to read input and display
   * output using view.
   *
   * @param scanner the scanner object that reads the input
   * @param view    represents view object
   */
  public SaveSession(Scanner scanner, IView view) {

    super(scanner, view);
  }

  /**
   * The method performs the save session operation to save all the portfolios with their stocks and
   * strategies applied on them. Also all the created strategies are saved.
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
    try {
      model.saveSession();
    } catch (IllegalArgumentException e) {
      writeToOut(e.getMessage());
    } catch (IllegalStateException e) {
      writeToOut(e.getMessage());
    }
    writeToOut("Session saved successfully.");
    return CommandRunStatus.SUCCESSFUL;
  }
}
