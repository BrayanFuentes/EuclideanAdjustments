import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Main {
	public static void main(String[] args) throws FileNotFoundException
	{
		File inputFile = new File("input.txt");
		Scanner scn = new Scanner(inputFile);
		PrintWriter out = new PrintWriter("output.txt");
		ArrayList<BigInteger> set = new ArrayList <BigInteger>();
		BigInteger offBy = null;
		int counter = 0;
		while(scn.hasNext())
		{
			if(counter == 0 && scn.hasNextLine())
			{
				offBy = new BigInteger(scn.nextLine());
			}
			else
			{
				set.add(new BigInteger(scn.nextLine()));
			}
			counter++;
		}
		HashMap<BigInteger,BigInteger> answer = findVariableValues(set,offBy);
		BigInteger tempsum = BigInteger.ZERO;
		for(Map.Entry map : answer.entrySet())
		{
			BigInteger temp = (BigInteger) map.getValue();
			BigInteger temp2 = (BigInteger) map.getKey();
			tempsum = tempsum.add(temp.multiply(temp2));
		}
		System.out.println(tempsum);
		scn.close();
		out.close();
	}
	
	public static HashMap<BigInteger,BigInteger> findVariableValues(ArrayList<BigInteger> set, BigInteger n)
	{
		//this is set by the key being number from the list and the values being what we find.
		HashMap<BigInteger,BigInteger> finalSet = new HashMap<BigInteger,BigInteger>();
		BigInteger tempGCD = null;	
		for(int i = 0; i <= set.size()-2; i++)
		{
			if(i==0)
			{
				ArrayList<BigInteger> initialValues = extended(set.get(i),set.get(i+1));
				tempGCD = set.get(i).gcd(set.get(i+1));
				finalSet.put(set.get(i), initialValues.get(0));
				finalSet.put(set.get(i+1), initialValues.get(1));
			    System.out.println(finalSet.toString());
			}
			else
			{
				ArrayList<BigInteger> values = extended(tempGCD,set.get(i+1));
				tempGCD = tempGCD.gcd(set.get(i+1));
				for(Map.Entry map : finalSet.entrySet())
				{
					BigInteger temp = (BigInteger) map.getValue();
					temp = temp.multiply(values.get(0));
					finalSet.replace((BigInteger) map.getKey(), temp);
				}
				finalSet.put(set.get(i+1),values.get(1));
				 System.out.println(finalSet.toString());
			}
		}
		
		for(Map.Entry map : finalSet.entrySet())
		{
			BigInteger temp = (BigInteger) map.getValue();
			temp = temp.multiply(n);
			finalSet.replace((BigInteger) map.getKey(), temp);
		}
		System.out.println(finalSet.toString());
		
		return finalSet;
	}
	//this is just a method that performs the extended euclidean algorithm 
	public static ArrayList<BigInteger> extended(BigInteger firstNum, BigInteger secondNum)
    {
        BigInteger x = BigInteger.ZERO;
        BigInteger y = BigInteger.ONE;
        BigInteger finalX = BigInteger.ONE;
        BigInteger finalY = BigInteger.ZERO;
        BigInteger temp = null;
        while (!secondNum.equals(BigInteger.ZERO))
        {
            BigInteger quotient = firstNum.divide(secondNum);
            BigInteger remainder = firstNum.mod(secondNum);
            firstNum = secondNum;
            secondNum = remainder;
            temp = x;
            x = finalX.subtract((quotient.multiply(x)));
            finalX = temp;
 
            temp = y;
            y = finalY.subtract((quotient.multiply(y)));
            finalY = temp;            
        }
        return new ArrayList<BigInteger>(Arrays.asList(finalX,finalY));
    }
	
}
