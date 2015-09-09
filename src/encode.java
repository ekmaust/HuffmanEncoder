import java.io.*;

public class encode
{
	private String[] code = new String[70];
	int index = 0;

	public void traverseTree (Node n, String code, char[] ascii) 
	{
		if (n != null) 
		{
			if (n.frequency != 0 && n.character != '\0')
			{
				for (int i = 0; i <= ascii.length; i++)
				{
					if (ascii[i] == n.character)
					{
						index = i;
						break;
					}
				}
				this.code[index] = code;
				traverseTree(n.left, code + "1", ascii);
				traverseTree(n.right, code + "0", ascii);
			}
			else
			{
				traverseTree(n.left, code + "1", ascii);
				traverseTree(n.right, code + "0", ascii);
			}
		}
	}
	
	public String encodeFile(char[] fileText, String title, char[] ascii, int[] ratio, int[] fixedRatio, int[] frequency) throws IOException
	{
		String newTitle = System.getProperty("user.dir") + "/Encoded/Encoded_" + title;
		File newFile = new File(newTitle);
		FileWriter fw = new FileWriter(newFile);
		BufferedWriter bw = new BufferedWriter(fw);

		int current = 0;
		while(fileText[current] != '\0')
		{
			for (int i = 0; i < ascii.length; i++)
			{
				if (fileText[current] == ascii[i])
				{
					try
					{	
						bw.write(code[i]);
						ratio[i] = code[i].length() * frequency[i]; //frequency of letter * code-length of letter
						fixedRatio[i] = 16 * frequency[i]; //frequency of letter * standard ASCII char size (16 bits)
						break;
					}
					catch (Exception e)
					{
						System.out.println(e.toString());
					}
				}
			}
			current++;
		}
		
		bw.close();
		return newTitle;
	}
	
	
	public void decodeFile(BufferedReader file, String title, char[] ascii) throws IOException
	{
		String newTitle = System.getProperty("user.dir") + "/Decoded/Decoded_" + title;
		File newFile = new File(newTitle);
		FileWriter fw = new FileWriter(newFile);
		BufferedWriter bw = new BufferedWriter(fw);
		
		int current;
		String currcode = "";
		try
		{
			while((current = file.read()) != -1)
			{
				char currchar = (char)current;
				currcode = currcode + Character.toString(currchar);
				for (int i = 0; i < code.length; i++)
				{
					if (currcode.equals(code[i]))
					{
						bw.write(ascii[i]);
						currcode = "";
						break;
					}
					current = 0;
				}
			}
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}
		bw.close();
		file.close();
	}

}