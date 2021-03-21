import java.util.Random;

public class HashTable {
	@SuppressWarnings("rawtypes")
	HashObject[] table;
	private int tableSize;
	private int minusTwo;
	private boolean linear = false;
	private boolean doubleHash = false;
	
	public HashTable(String probeType)
	{
		tableSize = findPrime();
		table = new HashObject[tableSize];
		minusTwo = tableSize - 2;
		if (probeType.compareTo("linear") == 0)
		{
			linear = true;
		}
		if (probeType.compareTo("double") == 0)
		{
			doubleHash = true;
		}
	}
	
	// TODO: check page 32 after everything fails
	@SuppressWarnings("rawtypes")
	public boolean addObject(HashObject object)
	{
		int index = positiveMod(object.hashCode(), tableSize);
		boolean found = false;
		int i = 0;
		int probes = 0;
		boolean duplicate = false;
		
		if (doubleHash)
		{
			while (!found)
			{
				if (table[index].getKey() == null)
				{
					table[index] =  object;
					found = true;
					table[index].increaseProbeCount(probes);
				}
				else if (table[index].equals(object))
				{
					table[index].increaseDuplicate();
					found = true;
					duplicate = true;
				}
				else
				{
					index += (1 + positiveMod(object.hashCode(), minusTwo)) * i;
					i++;
					probes++;
				}
			}
		}
		
		if (linear)
		{
			while (!found)
			{
				if (table[index].getKey() == null)
				{
					table[index] = object;
					found = true;
					table[index].increaseProbeCount(probes);
				}
				else if (table[index].equals(object))
				{
					table[index].increaseDuplicate();
					found = true;
					duplicate = true;
				}
				else
				{
					index++;
					probes++;
				}
			}
		}
		return !duplicate;
	}
	
	public int averageProbe(int numEntries)
	{
		int probeCount = 0;
		for (int i = 0; i < tableSize; i++)
		{
			probeCount += table[i].getProbeCount();
		}
		return (probeCount / numEntries);
	}
	
	public int positiveMod(int dividend, int divisor)
	{
		int value = dividend % divisor;
		if (value < 0)
		{
			value += divisor;
		}
		return value;
	}
	
	public int tableSize()
	{
		return tableSize;
	}
	
	private boolean isPrime(int p)
	{
		long solution = 0;
		long base;
		boolean firstOne = true;
		
		Random rand = new Random();
		base = (int)(rand.nextInt(p-2)) + 2;
		String binary = Integer.toBinaryString(p);
		
		for (int j = 0; j < binary.length(); j++)
		{
			if (binary.charAt(j) == '1')
			{
				if (firstOne)
				{
					solution = base;
					firstOne = false;
				}
				else
				{
					solution = (((solution * solution) % p) * base) % p;
				}
			}
			if (binary.charAt(j) == '0')
			{
				solution = (base * solution) % p;
			}
		}
		
		if (solution == 1)
		{
			return true;
		}
		else
		{
			return false;
		}
		
	}
	
	
	private int findPrime()
	{
		for(int p = 95501; p < 96000; p+=2)
		{
			if (isPrime(p) ==  true)
			{
				if (isPrime(p) == true)
				{
					if (isPrime(p+2) == true)
					{
						if (isPrime(p+2) == true)
						{
							return (p+2);
						}
					}
				}
			}
		}
		return -1;
	}
	
	public String toString()
	{
		String newString = "";
		for (int i = 0; i < tableSize; i++)
		{
			if (table[i] != null)
			{
				newString += "\ntable[" + i + "]: " + table[i].toString();
			}
		}
		
		return newString;
	}

}
