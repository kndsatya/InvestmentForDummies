package datasource;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;


/**
 * The class represents a factory class that would create instances to fetch stock data according to
 * the given source. Based on what the user gives as source, it will create the instance that
 * implements the DataSourceInterface and will have the getSharePrice method that would fetch from
 * the stock data source. For example if the user enters as "fileinput' then it will create an
 * instance that would have its getSharePrice method to fetch from that file
 */
public class DataSourceCreator {

  /**
   * A static method that would take the user entered data source type and readable object to read
   * from the source.
   *
   * @param dataSource the source from which stock data is to be fetched
   * @param readable   the readable object that would read from the given source.
   * @return instance of DataSourceInterface that has the implementation to fetch data from the
   *         given stock data source
   * @throws IllegalArgumentException if the readable object is null or data source is null/empty.
   */

  public static DataSourceInterface getDataSource(String dataSource, Readable readable)
          throws IllegalArgumentException {

    if (checkForNull(readable)) {
      throw new IllegalArgumentException("Readable object can't be null");
    }


    Map<String, Supplier<DataSourceInterface>> knownDataSources;
    knownDataSources = new HashMap<>();
    knownDataSources.put("USERINPUT", () -> new InputFromUser(readable));
    knownDataSources.put("ALPHAAPI", () -> new AlphavantageAPI());
    //knownDataSources.put("FILEINPUT", () -> new FileInput(readable));
    Supplier<DataSourceInterface> supplier
            = knownDataSources.getOrDefault(dataSource.trim().toUpperCase(), null);
    if (supplier == null) {
      throw new IllegalArgumentException("Invalid datasource provided");
    }
    return supplier.get();
  }

  /**
   * checks if an object is null.
   *
   * @param object any object in java.
   * @return true if the object is null else false.
   */
  private static boolean checkForNull(Object object) {

    try {
      Objects.requireNonNull(object);
    } catch (NullPointerException e) {
      return true;
    }

    return false;

  }
}
