
public class MiniMax {
	
	private String board[] = new String[9];
	Node head = null;
	
	public void listAllPossibleMoves() {
		add(1);
		add(2);
		add(3);
		add(4);
		add(5);
		add(6);
		add(7);
		add(8);
		add(9);
	}
	
	public int minimax(boolean isMaximizingPlayer, String board[]) { //Currently set up only to work while X is cpu
		int value = 0;
		for (int i = 0; i <= size(); i++) { //goes through each possible move
			String[] boardClone = (String[]) board.clone();
			boardClone[i] = "X";
			if (isMaximizingPlayer == true) {
				if (getIsVictorious(1, boardClone) == true) {
					return 1;
				} else {
					return minimax(false, boardClone);
				}
			} else {
				if (getIsVictorious(2, boardClone) == true) {
					return -1;
				} else {
					return minimax(true, boardClone);
				}
			}
		}
		return value;
	}
	
	private boolean getIsVictorious(int currentTurn, String board[]) {
		String tile = null;
		if (currentTurn == 1) {
			tile = "X";
		} else {
			tile = "O";
		}
		for (int i = 0; i <= 2; i++) {
			if (board[i] == tile && board[i+3] == tile && board[i+6] == tile) {
				 return true; //win
			}
		}
		for (int i = 0; i <= 7; i+=3) {
			if (board[i] == tile && board[i+1] == tile && board[i+2] == tile) {
				return true; // win
			}
		}
		if (board[0] == tile && board[4] == tile && board[8] == tile) {
			return true; // win
		}
		if (board[2] == tile && board[4] == tile && board[6] == tile) {			
			return true; //win
		}
		if (board[0] != null && board[1] != null && board[2] != null && board[3] != null && board[4] != null && 
				board[5] != null && board[6] != null && board[7] != null && board[8] != null) {
		}
		return false; //no state
	}


	public int size() {
		Node currNode = head;

		int result = 0;

		while (currNode != null) {
			if (currNode.next == null) {
				result++;
			} else {
				result++;
			}
			currNode = currNode.next;
		}
		return result;
	}

	public void add(int data) {
		add(this.size() + 1, data);
	}

	public void add(int index, int data) {
		if (index > this.size() + 1 || index < 0) {
			throw new IndexOutOfBoundsException();
		}
		Node newNode = new Node(data, null, null);
		if (head == null) {
			head = newNode;
		} else if (index == 0) {
			Node next = head;
			head = newNode;
			head.next = next;
		} else {
			Node last = head;
			int pos = 0;
			while (last.next != null && pos < index - 1) {
				last = last.next;
				pos++;
			}	
			newNode.next = last.next;
			last.next = newNode;
			newNode.previous = last;
			if (newNode.next != null) {
				newNode.next.previous = newNode;
			}
		}

	}

	public Object get(int index) {

		Node currNode = head;
		int iteration = 0;

		while (currNode != null) {
			if (iteration == index) {
				return currNode.getValue();
			}
			iteration++;
			currNode = currNode.next;

		}
		return index;


	}

	public void remove(int index) {
		Node currNode = head;

		int iteration = 0;

		while (currNode != null) {
			if (currNode.next != null) {
				if (index == 0 && iteration == 0) {
					head = currNode.next;
				}
				else if (iteration + 1 == index) {
					currNode.next = currNode.next.next;
				} 

			}
			iteration++;
			currNode = currNode.next;
		}
	}

	public void clear() {
		head = null;

	}

}