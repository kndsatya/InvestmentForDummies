package controller;

import model.VirtualStockModelInterface;

/**
 * This is the controller interface for the virtual stock investments . The interface contains only
 * one method - run() which takes in the model object as it's only argument.
 */
public interface ControllerInterface {


  /**
   * The method reads the commands and performs the corresponding operations related to virtual
   * stock investment.
   *
   * @param model the model of the virtual stock investment which is an implementation of the
   *              VirtualStockModelInterface
   * @throws IllegalStateException when there are no further input to read as well as when the
   *                               writing to the appendable object fails
   */
  public void run(VirtualStockModelInterface model) throws IllegalStateException;

}
