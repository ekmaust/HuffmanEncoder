import java.io.*;
import java.util.Scanner;

/**
 * 
 * @author Emily Maust
 *
 */
public class runner 
{
	static char[] ascii = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '.', ',', '?', '!', ':', ';', '-', '"', '\'', ' ', '\n'};
	static int[] standardFreq = {915, 185, 202, 312, 934, 142, 260, 460, 652, 57, 200, 438, 318, 615, 709, 164, 9, 424, 519, 704, 310, 80, 195, 28, 274, 27, 169, 401, 338, 940, 381, 48, 185, 205, 550, 935, 8};
	static char[] fileText = new char[20000];
	static int[] ratio = new int[ascii.length];
	static int totalChars = 0;
	static int[] fixedRatio = new int[ascii.length];
	
	public static void convertFile(String title) throws IOException
	{
		File book = new File(title);
		BufferedReader in = new BufferedReader(new FileReader(book));
		int position = 0, current = 0;
		while ((current = in.read()) != -1)
		{
			fileText[position] = Character.toLowerCase((char)current);
			totalChars++;
			position++;	
			current++;
		}
		while (position < 20000)
		{
			fileText[position] = '\0';
			position++;
		}
		in.close();
	}

	public static void main(String[] args) throws IOException
	{
		File textFile = new File("Titles.txt");
		BufferedReader in = new BufferedReader(new FileReader(textFile));

		Scanner scan = new Scanner(System.in);
		System.out.println("Would you like to customized or standard compression? (c/s)");
		String which = scan.nextLine();
		char type = which.charAt(0);
		if (type == 'c')
		{
			for (int i = 0; i < 30; i++)
			{
				String Title = in.readLine();
				convertFile(Title);
				String newTitle = "Custom_" + Title;
				
				huffman creation = new huffman();
				int[] frequency = creation.countOccurences(fileText, ascii);
				
				Node nodeTree = Node.makeTree(frequency, ascii);
				encode tree = new encode();
				tree.traverseTree(nodeTree, "0", ascii);

				System.out.println();
				String encodeTitle = tree.encodeFile(fileText, newTitle, ascii, ratio, fixedRatio, frequency);

				BufferedReader decodeFile = new BufferedReader(new FileReader(encodeTitle));
				tree.decodeFile(decodeFile, newTitle, ascii);
				decodeFile.close();
				System.out.println();
				System.out.println(Title + " Completed!");
				totalChars = 0;
			}
		}
		else if (type == 's')
		{
			for (int i = 0; i < 30; i++)
			{
				String Title = in.readLine();
				convertFile(Title);
				String newTitle = "Standard_" + Title;

				Node nodeTree = Node.makeTree(standardFreq, ascii);
				encode tree = new encode();
				tree.traverseTree(nodeTree, "0", ascii);

				String encodeTitle = tree.encodeFile(fileText, newTitle, ascii, ratio, fixedRatio, standardFreq);

				BufferedReader decodeFile = new BufferedReader(new FileReader(encodeTitle));
				tree.decodeFile(decodeFile, newTitle, ascii);
				decodeFile.close();
				System.out.println();
				System.out.println(Title + " Completed!");
			}	
		}
		System.out.println();
		System.out.print("Goodbye.");
		in.close();
		scan.close();
	}
}