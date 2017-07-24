import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JButton;
import javax.swing.border.Border;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JProgressBar;


public class NeuralNetworkDecryption {

	private JFrame frmNeuralNetworkMarks;
	private JSpinner examMarksSpinner, courseworkMarksSpinner;
	private JSpinner crossoverKey1Spinner, crossoverKey2Spinner, mutationKeySpinner;
	private JLabel deExamMarksLabel;
	private JLabel deCourseworkMarksLabel;
	private JLabel progressLabel;
	private JProgressBar progressBar;
	private JButton encryptButton, resetButton;
	
	//create 3 layers - 2 in this case as input layer is hidden inside the Layer object
	private final int GEN_COUNT = 30000;
	private Layer[] layerArray = new Layer[2];
	private int currentCKey1=-1, currentCKey2=-1, currentMKey=-1;
	private double currentError;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NeuralNetworkDecryption window = new NeuralNetworkDecryption();
					window.frmNeuralNetworkMarks.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public NeuralNetworkDecryption() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmNeuralNetworkMarks = new JFrame();
		frmNeuralNetworkMarks.setTitle("Neural Network Marks Decryption");
		frmNeuralNetworkMarks.setBounds(100, 100, 450, 420);
		frmNeuralNetworkMarks.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmNeuralNetworkMarks.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Encrypted Final Examination Marks");
		lblNewLabel.setBounds(10, 11, 200, 14);
		frmNeuralNetworkMarks.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Encrypted Coursework Marks");
		lblNewLabel_1.setBounds(240, 11, 171, 14);
		frmNeuralNetworkMarks.getContentPane().add(lblNewLabel_1);
		
		examMarksSpinner = new JSpinner();
		examMarksSpinner.setModel(new SpinnerNumberModel(0, 0, 255, 1));
		examMarksSpinner.setBounds(10, 36, 114, 20);
		frmNeuralNetworkMarks.getContentPane().add(examMarksSpinner);
		
		courseworkMarksSpinner = new JSpinner();
		courseworkMarksSpinner.setModel(new SpinnerNumberModel(0, 0, 255, 1));
		courseworkMarksSpinner.setBounds(239, 36, 114, 20);
		frmNeuralNetworkMarks.getContentPane().add(courseworkMarksSpinner);
		
		JLabel lblNewLabel_2 = new JLabel("Crossover Key");
		lblNewLabel_2.setBounds(10, 67, 130, 14);
		frmNeuralNetworkMarks.getContentPane().add(lblNewLabel_2);
		
		crossoverKey1Spinner = new JSpinner();
		crossoverKey1Spinner.setModel(new SpinnerNumberModel(0, 0, 7, 1));
		crossoverKey1Spinner.setBounds(10, 92, 57, 20);
		frmNeuralNetworkMarks.getContentPane().add(crossoverKey1Spinner);
		
		crossoverKey2Spinner = new JSpinner();
		crossoverKey2Spinner.setModel(new SpinnerNumberModel(0, 0, 7, 1));
		crossoverKey2Spinner.setBounds(77, 92, 57, 20);
		frmNeuralNetworkMarks.getContentPane().add(crossoverKey2Spinner);
		
		JLabel lblNewLabel_3 = new JLabel("Mutation Key");
		lblNewLabel_3.setBounds(240, 67, 130, 14);
		frmNeuralNetworkMarks.getContentPane().add(lblNewLabel_3);
		
		mutationKeySpinner = new JSpinner();
		mutationKeySpinner.setModel(new SpinnerNumberModel(0, 0, 7, 1));
		mutationKeySpinner.setBounds(240, 92, 57, 20);
		frmNeuralNetworkMarks.getContentPane().add(mutationKeySpinner);
		
		JLabel lblNewLabel_5 = new JLabel("Progress....");
		lblNewLabel_5.setBounds(10, 133, 93, 14);
		frmNeuralNetworkMarks.getContentPane().add(lblNewLabel_5);
		
		progressLabel = new JLabel("No progress");
		progressLabel.setFont(new Font("Tahoma", Font.ITALIC, 11));
		Border border2 = BorderFactory.createMatteBorder(0,0,1,0,Color.RED);
		progressLabel.setBounds(10, 158, 171, 20);
		progressLabel.setBorder(border2);
		frmNeuralNetworkMarks.getContentPane().add(progressLabel);
		
		JLabel lblNewLabel_4 = new JLabel("Decrypted Final Examination Marks");
		lblNewLabel_4.setBounds(10, 214, 234, 14);
		frmNeuralNetworkMarks.getContentPane().add(lblNewLabel_4);
		
		JLabel lblEncryptedCourseworkMarks = new JLabel("Decrypted Coursework Marks");
		lblEncryptedCourseworkMarks.setBounds(10, 270, 234, 14);
		frmNeuralNetworkMarks.getContentPane().add(lblEncryptedCourseworkMarks);
		
		encryptButton = new JButton("Decrypt");
		encryptButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		encryptButton.setBounds(10, 338, 105, 30);
		encryptButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				new Thread(new Runnable(){
					public void run() {
						decrypt();
					}
				}).start();
			}
		});
		frmNeuralNetworkMarks.getContentPane().add(encryptButton);
		
		resetButton = new JButton("Reset");
		resetButton.setBounds(139, 338, 105, 30);
		resetButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				reset();
			}
		});
		frmNeuralNetworkMarks.getContentPane().add(resetButton);
		
		Border border = BorderFactory.createLineBorder(Color.BLUE, 2);
		deExamMarksLabel = new JLabel("");
		deExamMarksLabel.setBounds(10, 239, 120, 20);
		deExamMarksLabel.setBorder(border);
		frmNeuralNetworkMarks.getContentPane().add(deExamMarksLabel);
		
		deCourseworkMarksLabel = new JLabel("");
		deCourseworkMarksLabel.setBounds(10, 295, 120, 20);
		deCourseworkMarksLabel.setBorder(border);
		frmNeuralNetworkMarks.getContentPane().add(deCourseworkMarksLabel);
		
		progressBar = new JProgressBar(0, GEN_COUNT);
		progressBar.setBounds(10, 189, 171, 14);
		progressBar.setStringPainted(true);
		frmNeuralNetworkMarks.getContentPane().add(progressBar);
	}
	
	//perform NN decryption
	public void decrypt()
	{
		//disable button
		encryptButton.setEnabled(false);
		resetButton.setEnabled(false);
		
		int examMarks = (int)examMarksSpinner.getValue();
		int courseworkMarks = (int)courseworkMarksSpinner.getValue();
		
		//check if this is first run thus must train NN
		//check if the key changed, if yes then must train NN
		if((currentCKey1<0 || currentCKey1!=(int)crossoverKey1Spinner.getValue())
				|| (currentCKey2<0 || currentCKey2!=(int)crossoverKey2Spinner.getValue())
				|| (currentMKey<0 || currentMKey!=(int)mutationKeySpinner.getValue()))
		{
			do
			{
				currentCKey1 = (int)crossoverKey1Spinner.getValue();
				currentCKey2 = (int)crossoverKey2Spinner.getValue();
				currentMKey = (int)mutationKeySpinner.getValue();
				progressLabel.setText("Training neural network...");
				progressBar.setValue(0);
				trainNeuralNetwork(currentCKey1, currentCKey2, currentMKey);
				
			}while(currentError>=0.1); //keep training until the error is small enough
		}
		
		//use NN to decrypt
		progressLabel.setText("Decrypting...");
		int deExamMarks = neuralNetworkDecryption(examMarks);
		deExamMarksLabel.setText(String.valueOf(deExamMarks));
		
		int deCourseworkMarks = neuralNetworkDecryption(courseworkMarks);
		deCourseworkMarksLabel.setText(String.valueOf(deCourseworkMarks));
		
		progressLabel.setText("Finish");
		
		//enable button
		encryptButton.setEnabled(true);
		resetButton.setEnabled(true);
	}
	
	public void trainNeuralNetwork(int crossoverKey1, int crossoverKey2, int mutationKey)
	{
		//8 inputs in input layer, 6 neurons in hidden layer, 8 outputs in output layer, external bias 1
		layerArray[0] = new Layer(6, 8, 1);
		layerArray[1] = new Layer(8, 6, 1);
		
		//create value for training
		Population[] populations = new Population[25]; //50 examples to train with
		double[][] inputValues = new double[populations.length*2][8];
		double[][] correctValues = new double[populations.length*2][8];
		for(int i=0; i<populations.length; i++)
		{
			populations[i] = new Population();
			populations[i].encrypt(crossoverKey1, crossoverKey2, mutationKey);
			for(int j=0; j<populations[i].getIndividuals().length; j++)
			{
				for(int k=0; k<populations[i].getIndividuals()[j].getGenes().length; k++)
				{
					inputValues[2*i+j][k] = populations[i].getIndividuals()[j].getGenes()[k];
					correctValues[2*i+j][k] = populations[i].getIndividuals()[j].getOriValueGenes()[k];
				}
			}
		}
		
		//start training - train for GEN_COUNT generations
		for(int gen=0; gen<GEN_COUNT; gen++)
		{
			double errorSquareSum = 0.0;
			
			for(int i=0; i<inputValues.length; i++)
			{				
				//feed forward
				double[] layerOutput = layerArray[0].feedForward(inputValues[i]);
				//repeat for other layers
				for(int j=1; j<layerArray.length; j++)
				{
					layerOutput = layerArray[j].feedForward(layerOutput);
				}			
				
				//calculate error
				double[] layerError = new double[layerOutput.length];
				double error = 0.0;
				for(int j=0; j<layerOutput.length; j++)
				{
					layerError[j] = correctValues[i][j] - layerOutput[j];
					error += correctValues[i][j] - layerOutput[j];
				}
				errorSquareSum += error * error;
				
				//weight sharing / back propagrate
				layerError = layerArray[layerArray.length-1].weightSharing(layerError);
				//repeat for other layers
				for(int j=layerArray.length-2; j>=0; j--)
				{
					layerError = layerArray[j].weightSharing(layerError);
				}
			}
			progressBar.setValue(gen+1);
			currentError = errorSquareSum;
		}
	}
	
	public int neuralNetworkDecryption(int marks)
	{
		//convert to binary
		int[] binary = BinaryUtil.intToBinaryInt(marks);
		double[] inputValue = new double[binary.length];
		//convert to double for processing
		for(int i=0; i<inputValue.length; i++)
		{
			inputValue[i] = (double)binary[i];
		}
		
		double[] layerOutput = layerArray[0].feedForward(inputValue);
		for(int j=1; j<layerArray.length; j++)
		{
			layerOutput = layerArray[j].feedForward(layerOutput);
		}
		
		//convert double binary to marks
		int decryptedMarks = BinaryUtil.binaryDoubleToInt(layerOutput);
		return decryptedMarks;
	}
	
	public void reset()
	{
		examMarksSpinner.setValue(0);
		courseworkMarksSpinner.setValue(0);
		crossoverKey1Spinner.setValue(0);
		crossoverKey2Spinner.setValue(0);
		mutationKeySpinner.setValue(0);
		deExamMarksLabel.setText("");
		deCourseworkMarksLabel.setText("");
		progressLabel.setText("No progress");
	}
}
