

public class SigmoidNeuron{

	private static final double LEARNING_RATE = 0.1;
	private int inputSize;
	private InputConnector[] connectorArray;
	private InputConnector bias;
	private double output;
	
	public SigmoidNeuron(int inSize)
	{
		inputSize = inSize;
		connectorArray = new InputConnector[inputSize];
		initializeInput(inputSize);
	}
	
	public SigmoidNeuron(int inSize, int eBias)
	{
		this(inSize);
		bias = new InputConnector(inSize, eBias);
	}
	
	private void initializeInput(int inSize)
	{
		for(int i=0; i<connectorArray.length; i++)
		{
			connectorArray[i] = new InputConnector(inSize);
		}
	}

	public double calculateOutput(double[] inputVector)
	{
		//Change inputs
		for(int i=0; i<connectorArray.length; i++)
		{
			connectorArray[i].setInputValue(inputVector[i]);
		}
		
		//Linear summation
		double sum = 0.0;
		for(InputConnector input : connectorArray)
		{
			sum += input.calculateInput();
		}
		sum += bias.calculateInput();
		
		//Sigmoid function
		output = 1 / (1+Math.exp(-sum));
		
		return output;
	}
	
	public double[] updateInputWeight(double error)
	{
		//calculate error gradient
		double errorGradient = output*(1-output)*error;
		double[] connectorError = new double[connectorArray.length];
		
		for(int i=0; i<connectorArray.length; i++)
		{
			//put in error
			connectorError[i] = connectorArray[i].getWeight()*errorGradient;
			
			//update weight
			double deltaWeight = LEARNING_RATE*connectorArray[i].getInputValue()*errorGradient;
			connectorArray[i].addWeight(deltaWeight);
		}
		
		//update bias
		double deltaBias = LEARNING_RATE*bias.getInputValue()*errorGradient;
		bias.addWeight(deltaBias);
		
		//return the error
		return connectorError;
	}
	
	@Override
	public String toString()
	{
		String message = "";
		for(int i=0; i<connectorArray.length; i++)
		{
			message += String.format("Input %d - weight: %.4f\n", i+1, connectorArray[i].getWeight());
		}
		message += String.format("Bias: %.4f", bias.getWeight());
		return message;
	}
	
	//get set
	public void setInputSize(int s)
	{
		inputSize = s;
	}
	
	public int getInputSize()
	{
		return inputSize;
	}
	
	public void setConnectorArray(InputConnector[] inputs)
	{
		connectorArray = inputs;
	}
	
	public InputConnector[] getConnectorArray()
	{
		return connectorArray;
	}
	
	public void setBias(InputConnector b)
	{
		bias = b;
	}
	
	public InputConnector getBias()
	{
		return bias;
	}
	
	public void setOutput(double out)
	{
		output = out;
	}
	
	public double getOutput()
	{
		return output;
	}
}
