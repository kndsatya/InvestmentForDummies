package view;

/**
 * An interface for a command line view that offers methods such as displayCommands and
 * displayWaitingMessage. These methods helps to display the data send by model via controller. It
 * extends the interface IMultiView.
 */
public interface IView extends IMultiView {

  /**
   * A method that displays the list of commands and their description to the user.
   *
   * @throws IllegalStateException is thrown when writing to appendable fails.
   */
  public void displayCommands() throws IllegalStateException;

  /**
   * A method that displays a waiting message asking the user to user to wait, while the operation
   * of a command is in progress.
   *
   * @throws IllegalStateException is thrown when writing to appendable fails.
   */
  public void displayWaitingMessage() throws IllegalStateException;

}
