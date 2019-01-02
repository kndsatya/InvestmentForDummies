package guicontroller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

/**
 * A class that maintains a Map of buttonActionCommands and the corresponding action listener to
 * fire, implements ActionListener and overrides the method actionPerformed of the ActionListener
 * interface.
 */
public class ButtonListener implements ActionListener {
  Map<String, Runnable> buttonClickedActions;

  /**
   * Empty default constructor.
   */
  public ButtonListener() {
    //Do Nothing
  }

  /**
   * set the map for utton clicked events.
   * @param map A hash map that stores the button's action command and it's corresponding action
   *            listener to execute.
   */
  public void setButtonClickedActionMap(Map<String, Runnable> map) {
    buttonClickedActions = map;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (buttonClickedActions.containsKey(e.getActionCommand())) {

      buttonClickedActions.get(e.getActionCommand()).run();
    }
  }
}

