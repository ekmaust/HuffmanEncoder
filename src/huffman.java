import java.io.IOException;

public class huffman 
{
	
	public int[] countOccurences(char[] fileText, char[] ascii) throws IOException
	{
		int[] count = new int[ascii.length];
		int current = 0;
		while(fileText[current] != '\0')
		{
			for (int i = 0; i < ascii.length; i++)
			{
				if (fileText[current] == (ascii[i]))
				{
					count[i]++;
					break;
				}
			}
			current++;
		}
//		printCount(count, ascii);
		return count;
	}
	
	public void printCount(int[] count, char[] ascii)
	{
		for (int i = 0; i < count.length; i++)
		{
			if (count[i] != 0)
			{
				if (ascii[i] != '\n' && ascii[i] != ' ')
				{
					System.out.println(ascii[i] + " = " + count[i]);	
				}
				else if (ascii[i] == '\n')
				{
					System.out.println("New line = " + count[i]);	
				}
				else if (ascii[i] == ' ')
				{
					System.out.println("Space = " + count[i]);
				}
			}
		}
	}
}