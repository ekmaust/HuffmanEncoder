import java.util.PriorityQueue;

public class Node implements Comparable<Node>
{
	Node left;
	Node right;
	Node parent;
	char character;
	int frequency;
	
	public Node (char letter, int freq) 
	{
		character = letter;
		frequency = freq;
	}
	public Node (int freq)
	{
		character = '\0';
		frequency = freq;
	}
	
	@Override
	public int compareTo(Node node) {
		if (frequency < node.frequency)
		{
			return -1;
		}
		else if (frequency > node.frequency)
		{
			return 1;
		}
		return 0;
	}

	public static Node makeTree(int[] frequency, char[] ascii)
	{
		PriorityQueue<Node> queue = new PriorityQueue<Node>();
		for (int i = 0; i < ascii.length; i++)
		{
			if (frequency[i] != 0)
			{
				Node newNode = new Node(ascii[i], frequency[i]);
				queue.add(newNode);
			}
		}
		Node root = null;
		while (queue.size() > 1)
		{
			Node l1 = queue.poll();
			Node l2 = queue.poll();
			Node together = new Node(l1.frequency + l2.frequency);
			together.right = l1;
			together.left = l2;
			l1.parent = together;
			l2.parent = together;
			queue.add(together);
			root = together;
		}
		return root;
	}
}