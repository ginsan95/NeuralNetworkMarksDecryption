

public class Layer{
	
	private SigmoidNeuron[] neuronArray;
	
	public Layer(int neuronAmt, int neuronInputSize, int eBias)
	{
		neuronArray = new SigmoidNeuron[neuronAmt];
		for(int i=0; i<neuronArray.length; i++)
		{
			neuronArray[i] = new SigmoidNeuron(neuronInputSize, eBias);
		}
	}
	
	public double[] feedForward(double[] inputVector)
	{
		double[] layerOutput = new double[neuronArray.length];
		
		for(int i=0; i<neuronArray.length; i++)
		{
			layerOutput[i] = neuronArray[i].calculateOutput(inputVector);
		}
		
		return layerOutput;
	}
	
	public double[] weightSharing(double[] error)
	{
		double[] layerError = new double[neuronArray[0].getInputSize()];
		
		for(int i=0; i<neuronArray.length; i++)
		{
			double[] neuronError = neuronArray[i].updateInputWeight(error[i]);
			
			//sum value into layer error
			for(int j=0; j<neuronError.length; j++)
			{
				layerError[j] += neuronError[j];
			}
		}
		
		return layerError;
	}
	
	public double[] backPropagate(double error)
	{
		return weightSharing(new double[]{error});
	}
	
	@Override
	public String toString()
	{
		String message = "";
		for(int i=0; i<neuronArray.length; i++)
		{
			message += String.format("Neuron %d\n%s\n", i+1, neuronArray[i].toString());
		}
		return message;
	}
	
	//get set
	public void setNeuronArray(SigmoidNeuron[] neurons)
	{
		neuronArray = neurons;
	}
	
	public SigmoidNeuron[] getNeuronArray()
	{
		return neuronArray;
	}
	
	public int getErrorSize()
	{
		return neuronArray[0].getInputSize();
	}
	
}
