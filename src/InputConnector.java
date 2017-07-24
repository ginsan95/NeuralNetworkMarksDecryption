import java.util.Random;


public class InputConnector{
	
	private static final Random rand = new Random();
	private double inputValue;
	private double weight;
	
	public InputConnector(int inputSize)
	{
		weight = -(2.4/inputSize) + ((2.4/inputSize) - -(2.4/inputSize)) * rand.nextDouble();
	}
	
	//for bias
	public InputConnector(int inputSize, double value)
	{
		this(inputSize);
		inputValue = value;
	}
	
	public double calculateInput()
	{
		return inputValue*weight;
	}
	
	public void addWeight(double value)
	{
		weight += value;
	}
	
	//get set
	public void setInputValue(double iv)
	{
		inputValue = iv;
	}
	
	public double getInputValue()
	{
		return inputValue;
	}
	
	public void setWeight(double w)
	{
		weight = w;
	}
	
	public double getWeight()
	{
		return weight;
	}
}
