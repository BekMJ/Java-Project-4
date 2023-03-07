
public class Transaction implements Comparable<Transaction> {
	
	
	private int blockNubmer;
	private int index;
	private int gasLimit;
	private long gasPrice;
	private String fromAdr;
	private String toAdr;
	
	public Transaction(int number, int index, int gasLimit, long gasPrice, String fromAdr, String toAdr) {
		this.blockNubmer = number;
		this.index = index;
		this.gasLimit = gasLimit;
		this.gasPrice = gasPrice;
		this.fromAdr = fromAdr;
		this.toAdr = toAdr;
	}
	
	public int getBlockNumber() {
		return this.blockNubmer;
	}
	
	public int getIndex() {
		return index;
		
	}
	
	public int getGasLimit() {
		return gasLimit;
	}
	
	public long getGasPrice() {
		return gasPrice;
	}
	
	public String getFromAddress() {
		return fromAdr;
	}
	
	public String getToAddress() {
		return toAdr;
	}
	
	public String toString() {
		return "Transaction " + String.valueOf(getIndex()) + " for Block " + String.valueOf(getBlockNumber());	
	}

	@Override
	public int compareTo(Transaction t) {
		
		// TODO Auto-generated method stub
		return this.getIndex() - t.getIndex();
	}
	
	public double transactionCost() {
		
		return gasLimit*gasPrice/1e18;
	}
	
}
