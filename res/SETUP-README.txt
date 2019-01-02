Please follow the below steps in order to use this program:
1. Start program using:
 java -jar Assignment9.jar -view "<view-name>" <Data-Source>
 
 Note: while running the above command , please make sure you have the provided file 'tickersymbols.txt' and the directories "saved portfolios"
,"saved strategies" in the same directory as of the jar.

option for <View-name> can be "command" for command interface or "gui" for graphical user interface.

2. As of now you have two options to choose for <Data-Source> parameter i.e USERINPUT and ALPHAAPI. For USERINPUT the user manually enters
   the stock data, whereas ALPHAAPI uses the stock data provided by Alpha Vantage API (for reference -https://www.alphavantage.co/)
3. In the future release you can have more choices of data source to choose from. A data source provides the program with stock data.
4. For USERINPUT, you will be asked to enter the stock data. Format to enter will be shown by the program.
5. Once data input is complete enter either 'q' or 'quit'. These are case insensitive.

->If the command interface is selected you will be displayed the set of available commands to choose from as shown below.
6. Now you can run any of the below set of commands. Once you entered a command you will prompted for inputs. Based on the prompt, you can enter whatever is requested.
Command Name                        -      Command Description
-------------                              --------------------
CREATE_PORTFOLIO                    -      Command that helps to create a portfolio.
DISPLAY_PORTFOLIO                   -      Command that displays the contents of a portfolio.
BUY_STOCK                           -      Command to buy a stock in a portfolio with commission fee.
GET_TOTAL_COST_BASIS                -      Command to get the total cost basis of a portfolio on a particular date.
GET_TOTAL_VALUE                     -      Command to get the total value of a portfolio on a particular date.
DISPLAY_ALL_PORTFOLIOS              -      Command to display the contents of all portfolios.
INVEST                              -      Command to invest in an existing portfolio by specifying percentages of amount to invest.
ADD_STOCK                           -      Command to add stock(ticker symbol) to a portfolio.
APPLY_DOLLAR_COST_STRATEGY          -      Command to create a new portfolio and apply dollar cost averaging strategy on it.
DISPLAY_STOCKS_IN_PORTFOLIO         -      Command to display a set of stocks a portfolio contains.
SAVE_PORTFOLIO                      -      Command to save a portfolio to a file.
SAVE_STRATEGY                       -      Command to save a strategy to a file.
CREATE_STRATEGY                     -      Command to create a dollar cost averaging strategy.
APPLY_STRATEGY                      -      Command to apply a created strategy on a portfolio.
SAVE_SESSION                        -      Command to save the current session, saves all portfolios and strategies.


7. If GUI is selected for view you will be shown a GUI and it's self explanatory. Just Follow click on the button you wish and follow the instructions it show to you.


Mention for 3rd party libraries.:
---------------------------------
we have used GSON to parse and retrieve data in Json format. It's an open source and we have included it's jar in our jar library. But if you want to run the program using intellij please have the jar downloaded from the link "https://repo1.maven.org/maven2/com/google/code/gson/gson/2.6.2/" and the name of the jar to
download is: "gson-2.6.2.jar". Once downloaded include the jar to the libraries. For that follow below steps:

1. Open Files->Project Structure->modules->dependencies->click + on the right side pane->select jar and select the jar you have downloaded.
2. Now click apply. Now you can run the program as the dependencies are added.

Link for License to include gson jar in our code and using it-https://github.com/google/gson/blob/master/LICENSE

Persistence:
-------------
1.If a portfolio is saved it will be saved in it's named textfile i.e.   SAT.txt if the portfolio name is 'sat' under the directory "saved portfolios" that is in the same directory as of jar file.

2. If a strategy is saved it will be saved in it's named textfile i.e ONE.txt if the strategy name is 'one' under the directory "saved strategies" that is in the same directory of jar file.