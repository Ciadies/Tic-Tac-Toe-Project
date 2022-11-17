public class Node 
{
	protected Node next;
	protected Node previous;
	protected int value;
	protected boolean isMaxPlayer;

	public Node(int value, Node previous, Node next, boolean isMaxPlayer)
	{
		this.value = value;
		this.previous = previous;
		this.next = next;
		this.isMaxPlayer = isMaxPlayer;
	}
	
	public Node(int value, Node previous, Node next) {
		this.value = value;
		this.previous = previous;
		this.next = next;
	}

	public int getValue()
	{
		return this.value;
	}
		
}
