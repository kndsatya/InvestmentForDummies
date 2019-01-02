package root;

import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.Scanner;

import controller.Controller;
import controller.ControllerInterface;
import datasource.DataSourceCreator;
import datasource.DataSourceInterface;
import guicontroller.GUIController;
import model.VirtualStockModel;
import model.VirtualStockModelInterface;
import view.GUIView;
import view.IView;
import view.ViewImpl;


/**
 * This class is the starting point of the entire application. The class contains the main method
 * from where the control goes to the controller. When the application is run, this is the first
 * class that gets loaded.
 */
public class StartApplication {
  /**
   * <p>This is the method where the flow of the application starts. The main method gets the type
   * of the data source through the command line arguments. Based on the user's type of source data,
   * the corresponding DataSource object that implements the DataSourceInterface is created.</p>
   *
   * <p>Followed by this the model object is created as well as the controller. The created
   * sourceData is passed to the model, which uses that object to get the share price for a ticker
   * symbol and the date. This model object is in turn passed to the controller and the controller
   * is used to take commmands from the user and call the corresponding model method</p>
   *
   * @param arguments command line arguments that provides information on which data source, UI to
   *                  use.
   */
  public static void main(String[] arguments) {

    String dataSource;
    Readable rd;
    DataSourceInterface datasourceobject;
    if (arguments.length == 0 || arguments.length == 1 || arguments.length == 2) {
      System.out.println("Please provide type of UI and datasource you want to have"
              + " as the command line argument as mentioned in setup-readme.txt");
      return;
    }


    switch (arguments[2].trim().toUpperCase()) {
      case "USERINPUT":
        dataSource = "USERINPUT";

        System.out.println("Please enter the stock related data in the below format:\n"
                + "TickerSymbol(in CAPS),date,closing Price.\nEach input should be on a"
                + " new line. Once input is complete press 'q'");
        rd = new InputStreamReader(System.in);
        Scanner sc = new Scanner(System.in);
        StringBuilder datainput = new StringBuilder();
        boolean readData = true;
        while (readData) {
          String input = sc.nextLine();
          if (input.equalsIgnoreCase("q")
                  || input.equalsIgnoreCase("quit")) {
            readData = false;
          } else {
            datainput.append(input).append("\n");
          }
        }
        datasourceobject = DataSourceCreator.getDataSource(dataSource,
                new StringReader(datainput.toString()));
        System.out.println("DataSource read successfully. You can proceed with operations\n");
        break;

      case "ALPHAAPI":
        dataSource = "ALPHAAPI";
        rd = new InputStreamReader(System.in);
        datasourceobject = DataSourceCreator.getDataSource(dataSource, rd);
        System.out.println("DataSource read successfully. You can proceed with operations\n");
        break;

      default:
        System.out.println("Please provide a valid dataSource.\n");
        return;

    }

    VirtualStockModelInterface model = new VirtualStockModel(datasourceobject);
    if (!arguments[0].trim().equalsIgnoreCase("-VIEW")) {
      System.out.println("Please provide valid options as mentioned in setup-readme.txt");
      return;
    }

    switch (arguments[1].trim().toUpperCase()) {

      case "CONSOLE":
        IView view = new ViewImpl(System.out);
        ControllerInterface controller = new Controller(rd, view);
        controller.run(model);
        break;

      case "GUI":
        GUIView guiview = new GUIView();
        GUIController guiController = new GUIController(model, guiview);
        break;

      default:
        System.out.println("Please provide a valid UI option.\n");
        return;
    }
  }
}
