import java.util.Random;


public class Population {

	private static Random rand = new Random();
	private Individual[] individuals;
	private int[] crossoverKey = new int[2];
	private int mutationKey;
	
	public Population()
	{
		this(rand.nextInt(101), rand.nextInt(101));
	}
	
	public Population(int examMarks, int courseworkMarks)
	{
		individuals = new Individual[2];
		individuals[0] = new Individual(examMarks);
		individuals[1] = new Individual(courseworkMarks);
	}
	
	public void encrypt(int cKey1, int cKey2, int mKey)
	{
		crossoverKey = new int[]{cKey1, cKey2};
		mutationKey = mKey;
		
		for(Individual individual : individuals)
		{
			individual.crossover(crossoverKey[0], crossoverKey[1]);
			individual.mutate(mutationKey);
			individual.updateEncryptedValue();
		}
	}
	
	@Override
	public String toString()
	{
		return String.format("Crossover key:{%d,%d}, Mutation key:%d\n"+
				"Exam marks:%d, Encrypted value:%d\n"+
				"Coursework marks:%d, Encrypted value:%d",
				crossoverKey[0], crossoverKey[1], mutationKey,
				individuals[0].getOriValue(), individuals[0].getEncryptedValue(),
				individuals[1].getOriValue(), individuals[1].getEncryptedValue());
	}
	
	//get set
	public void setIndividuals(Individual[] indi)
	{
		individuals = indi;
	}
	
	public Individual[] getIndividuals()
	{
		return individuals;
	}
	
	public void setCrossoverKey(int[] key)
	{
		crossoverKey = key;
	}
	
	public int[] getCrossoverKey()
	{
		return crossoverKey;
	}
	
	public void setMutationKey(int key)
	{
		mutationKey = key;
	}
	
	public int getMutationKey()
	{
		return mutationKey;
	}
}
