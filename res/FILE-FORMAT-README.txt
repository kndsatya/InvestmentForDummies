FILE FORMAT

1. The portfolios and strategies are stored in text files. Each portfolio and strategy is saved as a separate file with the file name same as portfolio or strategy name ie 
an individual file is created for each portfolio and strategy.
 
2. The content of the portfolio and strategies are stored in JSON format. 

Portfolio - {"portfolioName":"MYPORTFOLIO",
             "stocks":[{"tickerSymbol":"GOOG","numberOfShares":2,"buyDate":{"year":2017,"month":8,"day":9},"priceOfUnitShare":922.9,"commissionFee":30.5}],
             "uniqueStocksOfAPortfolio":["GOOG"],
             "strategies":[{"strategyName":"",
                            "startDate":{"year":2018,"month":11,"day":28},
                            "endDate":{"year":2018,"month":12,"day":3},
                            "investmentInterval":1,"commissionFee":30.0,
                            "amount":5000.0,
                            "stockAndPercentOfInvestment":{"goog":25.0,"aapl":25.0,"nflx":25.0,"Fb":25.0},"nextBuyDate":{"year":2018,"month":12,"day":4}}]}

Strategy - {"strategyName":"TEST STRATEGY",
            "startDate":{"year":2018,"month":11,"day":25},
            "endDate":{"year":2018,"month":12,"day":6},
            "investmentInterval":2,
            "commissionFee":34.0,
            "amount":2000.0,
            "stockAndPercentOfInvestment":{"goog":100.0}}
			
3. The full form of JSON is JavaScript Object Notation which is a data interchange-format. It stores the information
using key value pairs. For the portfolio, the keys are "portfolioName", "stocks", "uniqueStocksOfAPortfolio", and
"strategies". The "stocks" and "strategies" keys contain arrays of stock and strategy documents which is another
set of key-value pairs. For further reference, please refer - https://www.json.org/

4. A stock object contain "tickerSymbol", "numberOfShares", "buyDate", "priceOfUnitShare" and "commissionFee".

5. A strategy object consists of  "strategyName", "startDate", "endDate", "investmentInterval",  "commissionFee", "amount" 
and  "stockAndPercentOfInvestment" which contains the weights for each ticker symbol.

Note: The portfolios are saved under 'saved portfolios' directory and strategies under the directory 'saved strategies'. Also the portfolio and strategy JSON
strings have to be in one line without indentation. If the file is not in correct JSON format then the
application will not be able to load the data into it. 

example: {"portfolioName":"MYPORTFOLIO","stocks":[{"tickerSymbol":"GOOG","numberOfShares":2,"buyDate":{"year":2017,"month":8,"day":9},"priceOfUnitShare":922.9,"commissionFee":30.5}],"uniqueStocksOfAPortfolio":["GOOG"],"strategies":[]}

6. To create file manually consider this example - if the portfolio name is "MyPortfolio", then create a text file with name "MyPortfolio" and 
enter the details of the portfolio in key-value pairs as shown in the above example. Similarly do it for 
saving strategy files. Save the portfolios under a directory "saved portfolios" present in the same directory from
which jar is run. And for strategy save files under "saved strategies" present in jar folder. Please create these
folders inorder for the application to save and retrieve the files.

