package datasource;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class implements the DataSourceInterface and the method getSharePrice will fetch the stock
 * prices from the user. The user will pass the stock data as a string in readable object.
 */
public class InputFromUser implements DataSourceInterface {

  private final List<String> stockPriceData;

  /**
   * Constructs the User input taking in the readable object.
   *
   * @param readable readable object to read stock data from the source
   */
  public InputFromUser(Readable readable) throws IllegalArgumentException {

    stockPriceData = new ArrayList();
    Scanner scan = new Scanner(readable).useDelimiter("\\n");
    while (scan.hasNext()) {
      stockPriceData.add(scan.nextLine());
    }

  }


  /**
   * The method gets the share price for the given ticker symbol and the date by parsing the string
   * passed in the readable object.
   *
   * @param tickerSymbol the ticker symbol of the company
   * @param localDate    date for which the share price is to be found
   * @return double the share price for the given ticker symbol and date
   * @throws IllegalArgumentException if the ticker symbol or date is not valid.
   */
  public double getPriceOfShare(String tickerSymbol, LocalDate localDate)
          throws IllegalArgumentException {

    StringBuilder date = new StringBuilder();
    String day = "" + localDate.getDayOfMonth();
    if (day.length() == 1) {
      day = "0" + day;
    }
    String monthValue = "" + localDate.getMonthValue();
    if (monthValue.length() == 1) {
      monthValue = "0" + monthValue;
    }

    date.append(day);
    date.append("-");
    date.append(monthValue);
    date.append("-");
    date.append(localDate.getYear());

    for (String stockData : stockPriceData) {

      if (stockData.contains(tickerSymbol + "," + date)) {
        String[] stockdetails = stockData.split(",");
        return Double.parseDouble(stockdetails[stockdetails.length - 1]);
      }
    }

    return 0.00;
  }

}
