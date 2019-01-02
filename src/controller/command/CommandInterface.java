package controller.command;

import model.VirtualStockModelInterface;

/**
 * An interface for a command that offers a method runCommand which accepts a model as it's only
 * argument and returns the status of the command run.
 */
public interface CommandInterface {
  /**
   * This method runs a command provided by the user by calling the corresponding method from model
   * and returns the status of the operation.
   *
   * @param model represents the VirtualStockModel object.
   * @return CommandRunStatus.SUCCESSFUL if the command runs successfully else
   *         CommandRunStatus.FAILURE
   * @throws IllegalStateException if an operation to read from readable or if view is unable to
   *                               write to the appendable object.
   */
  CommandRunStatus runCommand(VirtualStockModelInterface model) throws IllegalStateException;
}
