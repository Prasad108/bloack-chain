package BlockChain.incubation;

import java.math.BigDecimal;
import java.util.Map;

import info.blockchain.api.exchangerates.Currency;
import info.blockchain.api.exchangerates.ExchangeRates;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception 
    {
        System.out.println( "Hello World!" );
        ExchangeRates exchange = new ExchangeRates();
        
      		// get the ticker map
              Map<String, Currency> ticker = exchange.getTicker();
              BigDecimal BTCUSDsell = ticker.get("USD").getSell();

              System.out.println( exchange.toBTC("EUR", new BigDecimal(53620)) );
              // convert 5362 EUR to BTC
              BigDecimal EURToBTC = exchange.toBTC("EUR", new BigDecimal(53620));
              
              // convert 100,000,000 satoshi to USD
              BigDecimal BTCToUSD = exchange.toFiat("USD", new BigDecimal(100000000));
    }
}
