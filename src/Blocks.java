import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Scanner;
import java.util.TimeZone;
import java.util.TreeSet;
import java.text.SimpleDateFormat;


/**
 * @author askub
 *
 */
public class Blocks implements Comparable<Blocks> {
	private int number;				// Block number
	private String miner;			// Miner address
	private long timestamp; 		// Unix timestamp
	private int transactionCount;	// Transaction count
	private static ArrayList<Blocks> blocks = null; // all blocks list
	private StringBuilder returnString = new StringBuilder();
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("E, dd MMMM yyyy HH:mm:ss z"); // date format of the tiemstampgi
	private Date date;				// date in the format of "dateFormat
	private ArrayList<Transaction> transactions = null; //transactions list

	/**
	 * constructs an empty blocks object
	 */
	public Blocks() {
		returnString.append("Empty Block");
	}

	/**
	 * constructs a blocks object with the given number
	 * @param number
	 */
	public Blocks(int number) {
		this.number = number;
		returnString.append("Block Number: " + number);
	}

	/**
	 * constructs a blocks object with the given number and miner
	 * @param number
	 * @param miner
	 */
	public Blocks(int number, String miner) {
		this.number = number;
		this.miner = miner;
		returnString.append("Block Number: " + number + " Miner Address: " + miner);
	}
	
	/**
	 * constructs a blocks object with the given number, miner, timestamp, and transactionCount
	 * @param number
	 * @param miner
	 * @param timestamp
	 * @param transactionCount
	 * @throws FileNotFoundException
	 */
	public Blocks(int number, String miner, long timestamp, int transactionCount) throws FileNotFoundException {
		this.number = number;
		this.miner = miner;
		this.timestamp = timestamp;
		this.transactionCount = transactionCount;
		readTransactions("ethereumtransactions1.csv");
		returnString.append("Block Number: " + number + " Miner Address: " + miner);
	}
	
	/**
	 * @return a copy of transactions arraylist
	 */
	public ArrayList<Transaction> getTransactions(){
		return new ArrayList<>(transactions);
	}

	/**
	 * @return block number
	 */
	public int getNumber() {
		return this.number;
	}

	/**
	 * @return miner of the block
	 */
	public String getMiner() {
		return this.miner;
	}
	
	/**
	 * @return a count of all transactions
	 */
	public int getTransactionCount() {
		return this.transactionCount;
	}
	
	/**
	 * @return a copy of blocks list
	 */
	public static ArrayList<Blocks> getBlocks() {
		return new ArrayList<>(blocks);
	}
	
	

	
	/**
	 * // given an ArrayList of Blocks, finds each unique miner address and the frequency of times it appears and print according to output
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void calUniqMiners() throws FileNotFoundException, IOException {	
		// if blocks ArrayList has not been read, do so now
		if (blocks == null)
		{
			readFile("ethereumP1data.txt");
		}
		
		// initialize ArrayLists to store addresses and frequencies
		ArrayList<String> uniqMiners = new ArrayList<String>();
		ArrayList<Integer> uniqMinersFreq = new ArrayList<Integer>();
		// holds each miner address
		String miner;
		// loop through all Blocks
		for (int i = 0; i < blocks.size(); ++i)
		{
			miner = blocks.get(i).getMiner();
			// enter if the miner is new
			if (!(uniqMiners.contains(miner)))
			{
				// add the miner and add the frequency of 1
				uniqMiners.add(miner);
				uniqMinersFreq.add(1);
			}
			// otherwise increment the frequency of that miner
			else
			{
				for (int j = 0; j < uniqMiners.size(); ++j)
				{
					if (uniqMiners.get(j).equals(miner))
					{
						uniqMinersFreq.set(j, uniqMinersFreq.get(j) + 1);
					}
				}
			}
		}

		// print according to output
		System.out.println("Number of unique Miners: " + uniqMiners.size() + "\n");
		System.out.println("Each unique Miner and its frequency:");
		for (int i = 0; i < uniqMiners.size(); ++i)
		{
			System.out.println("Miner Address: " + uniqMiners.get(i) + "\nMiner Frequency: " + uniqMinersFreq.get(i) + "\n");
		}
	}

	
	/**
	 * calculate the difference in the block numbers of two blocks
	 * @param minuend
	 * @param subtrahend
	 * @return
	 */
	public static int blockDiff(Blocks minuend, Blocks subtrahend) {
		int diff = minuend.getNumber() - subtrahend.getNumber();

		return diff;
	}

	// 
	/**
	 * given the Block number retrieve the Blocks object that corresponds to that number from blocks ArrayList and return it
	 * @param num block number
	 * @return a block object correspondinmg to the nubmer
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static Blocks getBlockByNumber(int num) throws FileNotFoundException, IOException {
		
		if(blocks == null) {
			Blocks.readFile("ethereumP1data.txt");
		}
		
		for(int i = 0; i < blocks.size(); ++i) {
			if (blocks.get(i).getNumber() == num) {
				return blocks.get(i);
			}
		}

		return null;
	}

	/**
	 * 
	 */
	public String toString() {
		return returnString.toString();
	}

	
	/**
	 * 
	 * reads a file of given filename and fills an ArrayList of Blocks
	 * @param filename
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static ArrayList<Blocks> readFile(String filename) throws FileNotFoundException, IOException {
		File file = new File(filename);

		// construct a scanner to read the file.
		Scanner fileScanner = new Scanner(file);

		// blocks ArrayList to store Blocks objects
		ArrayList<Blocks> b = new ArrayList<Blocks>();

		// create the Array that will store each lines data so we can grab the required fields
		String[] fileData = null;

		// Store each line of the file into the ArrayList.
		while (fileScanner.hasNextLine()) {
			String line = fileScanner.nextLine();

			// split each line along the commas
			fileData = line.trim().split(",");

			// fileData[0] corresponds to block number, fileData[9] to miner address
			// fileData[16] corresponds to unix timestamp, fileData[17] corresponds to transaction count
			b.add(new Blocks(Integer.parseInt(fileData[0]), fileData[9], Integer.parseInt(fileData[16]), Integer.parseInt(fileData[17])));
		}

		fileScanner.close();

		blocks = new ArrayList<>(b);

		return b;
	}

	/**
	 * sort the blocks ArrayList in ascending order based on block number
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void sortBlocksByNumber() throws FileNotFoundException, IOException {
		if (blocks==null) {
			readFile("ethereumP1.txt");
		}
		
		Collections.sort(blocks);
	}

	/**
	 *overriding compareTO method
	 */
	@Override
	public int compareTo(Blocks b) {
		Integer x = number;
		Integer y = b.getNumber();
		return x.compareTo(y);
	}
	
	
	/**
	 * // print the date with the correct format
	 * @return date with the correct format
	 */
	public String getDate() {
		// initialize date in milliseconds
		date = new Date(timestamp * 1000);
		dateFormat.setTimeZone(TimeZone.getTimeZone("CST"));
		return dateFormat.format(date);
	}
	
	
	/**
	 * find the difference in time between two given Blocks in hours, minutes, and seconds
	 * @param first
	 * @param second
	 */
	public static void timeDiff(Blocks first, Blocks second) {
		//make sure given Blocks aren't null
		if ((first == null) || (second == null)) {
			System.out.println("A given Block is null.");
		}
		else {
			String hours = " hours, ";
			String minutes = " minutes, and ";
			String seconds = " seconds.";
			// use timestamps to find hours, minutes, seconds
			int diffInSeconds = (int) Math.abs(first.timestamp - second.timestamp);
			int diffInMinutes = diffInSeconds / 60;
			int diffInHours = diffInMinutes / 60;
			diffInSeconds = diffInSeconds % 60;
			diffInMinutes = diffInMinutes % 60;
			
			if (diffInHours == 1) {
				hours = " hour, ";
			}
			if (diffInMinutes == 1) {
				minutes = " minute, and ";
			}
			if (diffInSeconds == 1) {
				seconds = " second.";
			}
			

			System.out.println("The difference in time between Block " + first.getNumber() + " and Block " + second.getNumber() + " is "
					+ diffInHours + hours + diffInMinutes + minutes + diffInSeconds + seconds);
		}
	}
	
	
	/**
	 * // return the number of transactions between two Blocks not inclusive
	// return -1 if the Blocks are null/not in the blocks ArrayList
	// or if second is before first in the ArrayList
	 * @param first
	 * @param second
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static int transactionDiff(Blocks first, Blocks second) throws FileNotFoundException, IOException {
		
		// if blocks ArrayList has not been read, do so now and sort it
		if (blocks == null)
		{
			readFile("ethereumP1data.txt");
			sortBlocksByNumber();
		}
		
		// make sure given Blocks aren't null
		if ((first == null) || (second == null)) {
			return -1;
		}
		
		int indexA = -1;		// index of first in blocks ArrayList
		int indexB = -1;		// index of second in blocks ArrayList
		int count = 0;			// number of transactions between the two Blocks
		
		
		// for loop to find indexA and indexB
		for (int i = 0; i < blocks.size(); ++i) {
			if (first.getNumber() == blocks.get(i).getNumber()) {
				indexA = i;
			}
			if (second.getNumber() == blocks.get(i).getNumber()) {
				indexB = i;
			}
		}
		
		// make sure first and second are elements of blocks
		if ((indexA < 0) || (indexB < 0)) {
			return -1;
		}
		// make sure first comes before second
		if (indexA >= indexB) {
			return -1;
		}
		
		// for loop to count the transactions
		for (int i = indexA+1; i < indexB; ++i) {
			count += blocks.get(i).getTransactionCount();
		}
		
		return count;
	}
	
	/**
	 * This method read certain columns from the data file in order to fill the transactions 
	 * ArrayList with Transaction objects by using the Transaction constructor.
	 * @param filename 
	 * @throws FileNotFoundException
	 */
	public void readTransactions(String filename) throws FileNotFoundException {
		File file = new File(filename);

		// construct a scanner to read the file.
		Scanner fileScanner = new Scanner(file);

		// Transaction ArrayList to store Transactions objects
		TreeSet<Transaction> t = new TreeSet<Transaction>();

		// create the Array that will store each lines data so we can grab the required fields
		String[] fileData = null;

		// Store each line of the file into the ArrayList.
		while (fileScanner.hasNextLine()) {
			String line = fileScanner.nextLine();

			// split each line along the commas
			fileData = line.trim().split(",");
			
			Transaction a = new Transaction(Integer.parseInt(fileData[3]), Integer.parseInt(fileData[4]), Integer.parseInt(fileData[8]), 
					Double.valueOf(fileData[9]).longValue(), fileData[5], fileData[6]);
			if(Integer.parseInt(fileData[3]) == this.getNumber()) {
				
			t.add(a);
			}
		}

		fileScanner.close();

		transactions = new ArrayList<>(t);

		
	}
	
	/**
	 * @return avg transaction count
	 */
	public double avgTransactionCost() {
		double sum = 0;
		for (int i =0; i < transactions.size(); i++) {
			sum += transactions.get(i).transactionCost();
		}
		return sum/(double)transactions.size();
	}
	
	/**
	 * This method look at all the transactions in a Block, and find every unique from address. Then it keep track of every to address 
	 * that corresponds to each unique from address. It also keep track of the combined cost of each transaction associated with each unique from address.
	 */
	public void uniqFromTo() {
		
		 ArrayList<String> uniq = new ArrayList<String>();
		 
		uniq.add(this.getTransactions().get(0).getFromAddress());
		for(Transaction t : this.getTransactions()) {
			if(!uniq.contains(t.getFromAddress())) {
				uniq.add(t.getFromAddress());
			}
			
		}
		System.out.println("Each transaction by from aaddress for Block " + this.getNumber() +":");
		
		
		for(String a : uniq) {
			System.out.println();
			System.out.println("From " + a);
			double sum = 0;
			for(Transaction t : this.getTransactions()) {
				if(t.getFromAddress().equals(a)) {
					System.out.println(" -> " + t.getToAddress());
					sum += t.transactionCost();
					
					}
				}
			System.out.printf("Total cost of transactions: %.8f ETH\n", sum );
			
			}
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	


