public class Node 
{
	protected Node next;
	protected Node previous;
	protected int position;
	protected boolean isMaxPlayer;

	public Node(int position, Node previous, Node next, boolean isMaxPlayer)
	{
		this.position = position;
		this.previous = previous;
		this.next = next;
		this.isMaxPlayer = isMaxPlayer;
	}
	
	public Node(int position, Node previous, Node next) {
		this.position = position;
		this.previous = previous;
		this.next = next;
	}

	public int getPosition()
	{
		return this.position;
	}
		
}
