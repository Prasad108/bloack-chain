package BlockChain.incubation;


import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import info.blockchain.api.blockexplorer.BlockExplorer;
import info.blockchain.api.blockexplorer.entity.Address;
import info.blockchain.api.blockexplorer.entity.Balance;
import info.blockchain.api.blockexplorer.entity.Block;
import info.blockchain.api.blockexplorer.entity.FilterType;
import info.blockchain.api.blockexplorer.entity.Input;
import info.blockchain.api.blockexplorer.entity.LatestBlock;
import info.blockchain.api.blockexplorer.entity.MultiAddress;
import info.blockchain.api.blockexplorer.entity.SimpleBlock;
import info.blockchain.api.blockexplorer.entity.Transaction;
import info.blockchain.api.blockexplorer.entity.UnspentOutput;
import info.blockchain.api.blockexplorer.entity.XpubFull;
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
        
        
        
     // instantiate a block explorer
    	BlockExplorer blockExplorer = new BlockExplorer();
        
      		// get the ticker map
              Map<String, Currency> ticker = exchange.getTicker();
              BigDecimal BTCUSDsell = ticker.get("USD").getSell();

              System.out.println( "converting 5362 EUR to Bitcoin = "+ exchange.toBTC("EUR", new BigDecimal(53620)) );
              // convert 5362 EUR to BTC
             // BigDecimal EURToBTC = exchange.toBTC("EUR", new BigDecimal(53620));
              
              // convert 100,000,000 satoshi to USD
            //  BigDecimal BTCToUSD = exchange.toFiat("USD", new BigDecimal(1));
            //  System.out.println( "converting 100 Bitcoin to USD = "+ exchange.toFiat("USD", new BigDecimal(100)) );
          	// get a transaction by hash and list the value of all its inputs
          	Transaction tx = blockExplorer.getTransaction("df67414652722d38b43dcbcac6927c97626a65bd4e76a2e2787e22948a7c5c47");
          	for (Input i : tx.getInputs())
          	{
          		System.out.println("preveous bloack "+i.getPreviousOutput().getValue());
          	}

          	// get a block by hash and read the number of transactions in the block
          	Block block = blockExplorer.getBlock("0000000000000000050fe18c9b961fc7c275f02630309226b15625276c714bf1");
          	int numberOfTxsInBlock = block.getTransactions().size();
          	

          	// get an address and read its final balance
          	Address address = blockExplorer.getAddress("1EjmmDULiZT2GCbJSeXRbjbJVvAPYkSDBw");
          	long finalBalance = address.getFinalBalance();
          	System.out.println("final balance of given address is "+finalBalance);
          	
          	// get an address and read its final balance with filter, limit, and offset
             // Address address1 = client.getAddress("1jH7K4RJrQBXijtLj1JpzqPRhR7MdFtaW", FilterType.All, 10, 5);    	
              long finalBalance1 = address.getFinalBalance();


          	// get a list of currently unconfirmed transactions and print the relay IP address for each
          	List<Transaction> unconfirmedTxs = blockExplorer.getUnconfirmedTransactions();
          	for (Transaction unconfTx : unconfirmedTxs)
          		System.out.println(tx.getRelayedBy());

          	// calculate the balanace of an address by fetching a list of all its unspent outputs
          	List<UnspentOutput> outs = blockExplorer.getUnspentOutputs("1EjmmDULiZT2GCbJSeXRbjbJVvAPYkSDBw");
          	long totalUnspentValue = 0;
          	for (UnspentOutput out : outs)
          		totalUnspentValue += out.getValue();

          	// calculate the balanace of an address by fetching a list of all its unspent outputs with confirmations and limit
          	List<UnspentOutput> outs1 = blockExplorer.getUnspentOutputs(Arrays.asList("1EjmmDULiZT2GCbJSeXRbjbJVvAPYkSDBw"), 5, 10);
          	long totalUnspentValue1 = 0;
          	for (UnspentOutput out : outs1)
          		totalUnspentValue += out.getValue();
          		
          	// returns the address balance summary for each address provided
          	Map<String, Balance> balances = blockExplorer.getBalance(Arrays.asList("1EjmmDULiZT2GCbJSeXRbjbJVvAPYkSDBw"), FilterType.All);
          	
          	// returns an aggregated summary on all addresses provided.
          	MultiAddress multiAddr = blockExplorer.getMultiAddress(Arrays.asList("1EjmmDULiZT2GCbJSeXRbjbJVvAPYkSDBw"), FilterType.All, 10, 5);
          	
          	// returns xpub summary on a xpub provided, with its overall balance and its transactions.
          	XpubFull xpub = blockExplorer.getXpub("xpub6CmZamQcHw2TPtbGmJNEvRgfhLwitarvzFn3fBYEEkFTqztus7W7CNbf48Kxuj1bRRBmZPzQocB6qar9ay6buVkQk73ftKE1z4tt9cPHWRn", FilterType.All, 10, 5);
          	
          	// get the latest block on the main chain and read its height
          	LatestBlock latestBlock = blockExplorer.getLatestBlock();
          	long latestBlockHeight = latestBlock.getHeight();

          	// use the previous block height to get a list of blocks at that height
      		// and detect a potential chain fork
          	List<Block> blocksAtHeight = blockExplorer.getBlocksAtHeight(latestBlockHeight);
          	if (blocksAtHeight.size() > 1)
          		System.out.println("The chain has forked!");
          	else
          		System.out.println("The chain is still in one piece :)");

          	// get a list of all blocks that were mined today since 00:00 UTC
          	List<SimpleBlock> todaysBlocks = blockExplorer.getBlocks();
          	System.out.println(todaysBlocks.size());
    }
}
