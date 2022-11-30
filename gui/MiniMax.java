
public class MiniMax {
	
	static int computer = 0;
	static int human = 0;
	
	private String board[] = new String[9];
	Node head = null;
	
	public void listAllPossibleMoves(String[] board) {
		clear();
		if (board[0] == null) {
			add(1);
		} if (board[1] == null) {
			add(2);
		} if (board[2] == null) {
			add(3);
		} if (board[3] == null) {
			add(4);
		} if (board[4] == null) {
			add(5);
		} if (board[5] == null) {
			add(6);
		} if (board[6] == null) {
			add(7);
		} if (board[7] == null) {
			add(8);
		} if (board[8] == null) {
			add(9);
		} 
		
	}
	
	public int minimax(String[] board, boolean isMaximizingPlayer, int computerIs) {
		int score = evaluate(board);
		String tile = null;
		if (computerIs == 1) {
			tile = "X";
		} else {
			tile = "O";
		}
		if (score == 10) {
			return score;
		} //computer auto win
		
		if (score == -10) {
			return score;
		} //human auto win 
		
		if (isMovesLeft(board) == false) {
			return 0;
		} //terminating condition for draws
		
		if (isMaximizingPlayer == true) {
			int best = -100000000;
			
			for (int i = 0; i < 9; i++) {
				if (board[i] == null) {
					String[] boardClone = (String[]) board.clone();
					boardClone[i] = tile;
					best = Math.max(best, minimax(boardClone, !isMaximizingPlayer, computerIs)); //find the move that wins the most
				}
			}
			return best;
		}
		else {
			int best = 100000;
			
			for (int i = 0; i < 9; i++) {
				if (board[i] == null) {
					String[] boardClone = (String[]) board.clone();
					boardClone[i] = "X";
					best = Math.min(best, minimax(boardClone, !isMaximizingPlayer, computerIs)); //Find the move that loses the least
				}
			}
			return best;
		}
	}
	
	static int evaluate(String board[]) { //Since this will only be called on computer turn, turn = computer always
		int turn = AnimationFrame.getCurrentTurn();
		int oppTurn;
		if (turn == 1) {
			oppTurn = 2;
		} else {
			oppTurn = 1;
		}
		if (getIsVictorious(turn, board) == true) {
			return 10;
		} else if (getIsVictorious(oppTurn, board) == true) {
			return -10;
		}
		return 0;
	}
	
	public Node findBestMove(boolean isMaximizingPlayer, String[] board, int computerIs) {
		String tile = null;
		if (computerIs == 1) {
			tile = "X";
			
		} else if (computerIs == 2){
			tile = "O";
		}	
		
		int bestVal = -1000000;
		Node bestMove = new Node(-1, null, null, false);
		
		for (int i = 0; i < 9; i++) { //pass through all moves and take the highest value of all end states
			String[] boardClone = (String[]) board.clone();
			if (board[i] == null) {
				boardClone[i] = tile;
				int	moveVal = minimax(boardClone, false, computerIs);
				if (moveVal > bestVal) {
					bestMove.position = i + 1;
					bestVal = moveVal;
				}
			}
		}
//		System.out.println("the best move is: " + bestMove.position); testing code to understand which moves cpu wants to do
		return bestMove;
	}
	
	
	private boolean isMovesLeft(String[] board) {
		clear();
		listAllPossibleMoves(board);
		if (size() == 0) {
			return false;
		}
		return true;
	}
	
	private static boolean getIsVictorious(int currentTurn, String board[]) {
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
		else if (board[2] == tile && board[4] == tile && board[6] == tile) {			
			return true; //win
		}
		else if (board[0] != null && board[1] != null && board[2] != null && board[3] != null && board[4] != null && 
				board[5] != null && board[6] != null && board[7] != null && board[8] != null) {
			return false; //draw
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
		if (index > 8 || index < 0) {
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

	public int get(int index) {

		Node currNode = head;
		int iteration = 0;

		while (currNode != null) {
			if (iteration == index) {
				return currNode.getPosition();
			}
			iteration++;
			currNode = currNode.next;

		}
		return -1;


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