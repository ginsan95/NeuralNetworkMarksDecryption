
public class Individual {

	private int oriValue;
	private int[] genes = new int[8]; //8 bit binary
	private int encryptedValue;
	
	public Individual(int value)
	{
		oriValue = value;
		genes = BinaryUtil.intToBinaryInt(oriValue);
	}
	
	public void crossover(int key1, int key2)
	{
		int temp = genes[key1];
		genes[key1] = genes[key2];
		genes[key2] = temp;
	}
	
	public void mutate(int key)
	{
		genes[key] = genes[key]==0? 1 : 0;
	}
	
	public void updateEncryptedValue()
	{
		encryptedValue = BinaryUtil.binaryIntToInt(genes);
	}
	
	public int[] getOriValueGenes()
	{
		return BinaryUtil.intToBinaryInt(oriValue);
	}
	
	//get set
	public void setOriValue(int num)
	{
		oriValue = num;
	}
	
	public int getOriValue()
	{
		return oriValue;
	}
	
	public void setGenes(int[] g)
	{
		genes = g;
	}
	
	public int[] getGenes()
	{
		return genes;
	}
	
	public void setEncryptedValue(int num)
	{
		encryptedValue = num;
	}
	
	public int getEncryptedValue()
	{
		return encryptedValue;
	}
}
