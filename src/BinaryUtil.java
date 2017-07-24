
public class BinaryUtil {

	public static int[] intToBinaryInt(int num)
	{
		int[] binary = {0, 0, 0, 0, 0, 0, 0, 0}; //8 bit binary
		char[] binaryTemp = Integer.toBinaryString(num).toCharArray();
		for(int i=0; i<binaryTemp.length; i++)
		{
			binary[binary.length-binaryTemp.length+i] = binaryTemp[i]-48;
		}
		return binary;
	}
	
	public static int binaryIntToInt(int[] binary)
	{
		StringBuilder builder = new StringBuilder();
		for (int num : binary) {
		  builder.append(num);
		}
		String binaryString = builder.toString();
		
		int num = Integer.parseInt(binaryString, 2);
		return num;
	}
	
	//perform round off
	public static int binaryDoubleToInt(double[] binary)
	{
		StringBuilder builder = new StringBuilder();
		for (double num : binary) {
		  builder.append(Math.round(num));
		}
		String binaryString = builder.toString();
		
		int num = Integer.parseInt(binaryString, 2);
		return num;
	}
	
}
