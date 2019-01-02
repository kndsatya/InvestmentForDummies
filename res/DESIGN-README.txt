1.Entire design is divided among 4 main packages:
   a)model
   b)controller
   c)root
   d)datasource
   
2. model contains a model interface and a class which offer methods that help to create a portfolio, buying stocks to a portfolio, retrieve the total cost,total
value of a portfolio at a given date and display the contents of the portfolio and display all the contents of the portfolio.

3. Model object maintains a list of portfolios. When ever a portfolio gets created it is added to this list.

4. In order to make model independent of the data source from where it fetches the stock data, we are passing the stock data source object to the model's constructor while instantiating it. So, model works with any valid data source.

5. A model fetches the data from the data source by calling a method on the data source object that it contains.

6. we have provided the flexibility to the user to choose the data source he wants to use from a set of available data sources.For this the user needs
to pass the data source name he wants to use as a command line argument while the program starts.

7.once the program starts the main method in the root package will create the instance of the data source by using the argument received from the command line.
So, as we are needed to create the data source object dynamically and pass to model, factory design pattern is used.

8. After a model is created the controller(Controller class from controller package) will be instantiated which will take the commands and the related arguments from the user line by line and calls the controller of that particular command to be executed. In order to make the design cleaner and accommodate any further additional commands we have used command design pattern for the controller.

Changes as part of assignment 9:
---------------------------------
1. Now along with the existing 4 packages we have one new package called view, so that we have segregated the duty of displaying the data and interaction with the user to the view. This view package contains an 'Iview' interface and a 'viewImpl' class. 

2.To accommodate the new features, new methods are added to buy stocks with the
commission fee, investment in multiple stocks and setting Dollar cost average strategy on a
portfolio to the existing model interface itself.
The reasons for changing the existing interface are:

a. If we choose an alternative approach of creating new model interface that extends the existing
interface which will have the new methods, then the VirtualStockModel class has to change as
well as the argument type to controller and command classes methods should also change to use this new model
interface. In case of adding new methods to the existing interface only VirtualStockModel class changes i.e.
it will implement more methods which it will have in either of the approaches.

b. The only consumer of this existing interface is the VirtualStockModel class and so changing the existing
VirtualStockModelInterface won't affect any other consumers. Even if there are other consumers,
the new methods are added using Java 8 'default' method in interface feature. This way no
existing users of the VirtualStockModelInterface won't face any compilation error.

c.The new methods added are buySharesofStock() that takes commission fee as extra argument, invest() that
buys shares of multiple stocks and adds them to an existing portfolio and dollarCostAverage()
method that invests a fixed amount on the user specified stocks repeatedly over a given
period of time

d. Also, in the total_cost_basis method we have included commission fee while calculating the total_cost_basis.

3. Also, a new feature of adding stocks to a portfolio with out the need to buy and then add to the portfolio is introduced.

4. Similarly, 3 new command classes are introduced to accommodate the new requirements in the controller package. Existing BUY_SHARE command is modified to include the commission fee as well.

changes as part of Assignment 10:
------------------------------------
1. Now we have one more package for GUI controller. So, we now have a controller for the new GUI.

2. The reason we have opted for new controller instead of using the existing controller is  that if we have used that existing controller we would have to read the data one prompt at a time and it doesn't mean any thing to the end user. He will not even remember what he has entered previously. So, in order for the user to have a better UI experience, we went ahead with creating a new GUI controller.

3. Now we have introduced new GUI interface called "IGUIView" and we found some common functionalities between the command line view and GUI view's  interface. So, we segregated the common methods to one parent Interface called "IMultiView" and made the two interfaces "IView"(for command line) and "IGUIView" (for GUI) extend the interface "IMultiView". Thus we have attained the interface segregation and no client is forced to use the unwanted methods.

4. we have now new methods added to the existing model interface to support persistence i.e. save portfolio, save strategy to files and retrieve them back from files. The reason we edited the existing model interface is same as specified in same as the one mentioned under changes for assignment-9.

5. we are storing the data in JSON Format and using a third party library called "gson"

6. So, now user can use whatever UI he wants i.e either he can use command line UI or GUI.
