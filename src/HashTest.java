import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

public class HashTest {
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void main(String[] args)
	{
		// Usage Warning
		if (args.length != 2)
		{
			System.out.println("Usage: java HashTest <input type> <load factor> [<debug level>]");
			System.out.println("<input type>: 1 for random integers, 2 for current time, 3 for word list");
			System.out.println("<load factor>: decimal value for desired load on the table");
			System.out.println("[<debug level>]: 1 for summary, 2 for summary and printed hash table");
			System.exit(1);
		}
		
		// Initialize variables
		int chosenInput = Integer.parseInt(args[0]);
		double alpha = Double.parseDouble(args[1]);
		int chosenDebug = 0;
		if (args.length == 3)
		{
			chosenDebug = Integer.parseInt(args[2]);
		}
		
		HashTable linearHashTable = new HashTable("linear");
		HashTable doubleHashTable = new HashTable("double");
		int m = linearHashTable.tableSize();
		int n = (int)Math.floor(m * alpha);
		int entries = 0;
		int duplicates = 0;
		String sourceType = null;
		Random rand = new Random();
		
		// Get input and put in table
		if (chosenInput == 1)
		{
			sourceType = "Random Integer";
			while (entries < n)	// Check this logic
			{
				Integer randomEntry = rand.nextInt();
				HashObject linearEntry =  new HashObject(randomEntry);
				boolean added = linearHashTable.addObject(linearEntry);
				HashObject doubleEntry = new HashObject(randomEntry);
				doubleHashTable.addObject(doubleEntry);
				
				if (added)
				{
					entries++;
				}
				else
				{
					duplicates++;
				}
				
			}
		}
		else if (chosenInput == 2)
		{
			sourceType = "Current Time";
			while (entries < n)
			{
				Long currTime = System.currentTimeMillis();
				HashObject linearEntry = new HashObject(currTime);
				boolean added = linearHashTable.addObject(linearEntry);
				HashObject doubleEntry = new HashObject(currTime);
				doubleHashTable.addObject(doubleEntry);
				
				if (added)
				{
					entries++;
				}
				else
				{
					duplicates++;
				}
			}
		}
		else if (chosenInput == 3)
		{
			sourceType = "word-list";
			File wordList = new File("word-list");	// Enter pathname to file
			Scanner scanner;
			try {
				scanner = new Scanner(wordList);
				
				while (entries < n)
				{
					String nextWord = scanner.nextLine();
					HashObject linearEntry = new HashObject(nextWord);
					boolean added = linearHashTable.addObject(linearEntry);
					HashObject doubleEntry = new HashObject(nextWord);
					doubleHashTable.addObject(doubleEntry);
					
					if (added)
					{
						entries++;
					}
					else
					{
						duplicates++;
					}
				}
			} catch (FileNotFoundException e) {
				System.out.println("File not found");
				System.exit(1);
			}
		}
		else
		{
			System.out.println("Invalid Input Type");
			System.exit(1);
		}
		
		// Debug types,
		// Check to see if entries is total entries or just entries into the table (ie. does it include duplicates)
		System.out.println("A good table size is found: " + linearHashTable.tableSize());
		System.out.println("Data source type: " + sourceType);
		System.out.println("\nUsing Linear Hashing....");
		System.out.println("Input " + entries + " elements, of which " + duplicates + " duplicates");
		System.out.println("load factor =  " + alpha + ", Avg. no. of probes " + linearHashTable.averageProbe(entries));
		System.out.println("\nUsing Double Hashing....");
		System.out.println("Input " + entries + " elements, of which " + duplicates + " duplicates");
		System.out.println("load factor =  " + alpha + ", Avg. no. of probes " + doubleHashTable.averageProbe(entries));
		
		if (chosenDebug == 1)
		{
			//Print to a file
			try {
				File linearFile = new File("linear-dump.txt");
				linearFile.createNewFile();
				File doubleFile = new File("double-dump.txt");
				doubleFile.createNewFile();
			} catch (IOException e)
			{
				System.out.println("An error occured while creating files");
				System.exit(1);
			}
			try {
				FileWriter linearWriter = new FileWriter("linear-dump.txt");
				linearWriter.write(linearHashTable.toString());
				linearWriter.close();
				FileWriter doubleWriter = new FileWriter("double-dump.txt");
				doubleWriter.write(doubleHashTable.toString());
				doubleWriter.close();
			} catch (IOException e)
			{
				System.out.println("An error occurred while writing to file");
				System.exit(1);
			}
		}
	}
}
