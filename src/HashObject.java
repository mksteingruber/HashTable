
public class HashObject<T> {
	private T object;
	private int duplicateCount = 0;
	private int probeCount =  0;
	
	public HashObject(T newObject)
	{
		object = newObject;
		duplicateCount = 0;
		probeCount = 1;
	}
	
	public int getDuplicateCount()
	{
		return duplicateCount;
	}
	
	public int getProbeCount()
	{
		return probeCount;
	}
	
	public void increaseProbeCount(int numProbes)
	{
		probeCount += numProbes;
	}
	
	public void increaseDuplicate()
	{
		duplicateCount++;
	}
	
	public T getKey()
	{
		return object;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public boolean equals(Object newObject)
	{
		HashObject other = (HashObject) newObject;
		return object.equals(other.getKey());
	}
	
	@Override
	public String toString()
	{
		return String.format(object + " " + duplicateCount + " " + probeCount);
	}

}
