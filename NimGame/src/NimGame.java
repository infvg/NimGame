import java.util.Scanner;

public class NimGame {

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		boolean done = false;
		int coins = 0, difficulty = 1, choice = 0, player = 1;
		do {
			System.out.print("Enter how many coins to play with: ");
			try {
				coins = s.nextInt();
				done = true;
			} catch (Exception e) {
				s.next();
				System.out.println("\nValid input is an integer! \n");
			}
		} while (!done);
		done = false;
		do {
			System.out.print("Enter difficulty (easiest to hardest) [1,2,3]: ");
			try {
				difficulty = s.nextInt();
				if (!(difficulty >= 1 && difficulty <= 3))
					throw new InvalidChoiceException();
				done = true;
			} catch (InvalidChoiceException e) {
				s.next();
				System.out.println("Invalid choice selected");
			} catch (Exception e) {
				s.next();
				System.out.println("Valid input is an integer! \n");
			}
		} while (!done);
		done = false;
		do {
			System.out.print("Enter 1 to play first, 2 to play 2nd: ");
			try {
				player = s.nextInt();
				if (!(player >= 1 && player <= 2))
					throw new InvalidChoiceException();
				done = true;
			} catch (InvalidChoiceException e) {
				s.next();
				System.out.println("\nInvalid choice selected\n");
			} catch (Exception e) {
				s.next();
				System.out.println("\nValid input is an integer! \n");
			}
		} while (!done);
		done = false;
		NimTree tree = new NimTree(coins);
		if (player == 2) {
			printCoins(tree);
			botMove(difficulty, tree);
		}
		do {
			printCoins(tree);
			do {
				System.out.print("Enter your choice [0 for commands]: ");
				try {
					choice = s.nextInt();
					if (!(choice >= 0 && choice <= 5))
						throw new InvalidChoiceException();
					done = true;
				} catch (InvalidChoiceException e) {
					s.next();
					System.out.println("\nInvalid choice selected\n");
				} catch (Exception e) {
					s.next();
					System.out.println("\nValid input is an integer! \n");
				}
			} while (!done);
			done = false;
			switch (choice) {
			case 0:
				System.out.println("\nCOMMANDS: ");
				System.out.println("0: Help");
				System.out.println("1: Remove one coin");
				System.out.println("2: Remove two coins");
				System.out.println("3: Hint");
				System.out.println("5: Get tree statistics\n");
				break;
			case 1:
				System.out.println("\nYou removed one coin\n");
				tree.removeOne();
				printCoins(tree);
				if (tree.getCurrentCoins() != 0) {
					botMove(difficulty, tree);
				}
				break;
			case 2:
				if (!tree.canRemoveTwo()) {
					System.out.println("\nYou cannot remove two coins!\n");
					break;
				}
				System.out.println("\nYou removed two coins\n");
				tree.removeTwo();
				printCoins(tree);
				if (tree.getCurrentCoins() != 0) {
					botMove(difficulty, tree);
				}
				break;
			case 3:
				if (tree.nextBestMove() == 2)
					System.out.println("\nHINT: You should take 2 coins\n");
				System.out.println("\nHINT:You should take 1 coin\n");
				break;
			case 5:
				System.out
						.println(String.format("\nTree statistics:\nHeight of current tree: %d\nNumber of nodes: %d\n",
								tree.getHeight(), tree.getNumNodes()));
				break;
			}
			if (tree.getCurrentCoins() == 0) {
				if (tree.getWinner() == 1) {
					if (player == 1)
						System.out.println("\nThe AI won!\n");
					else
						System.out.println("\nYou win!\n");
				} else {
					if (player == 1)
						System.out.println("\nYou win!\n");
					else
						System.out.println("\nThe AI won!\n");
				}
				done = true;
			}
		} while (!done);

	}

	public static void botMove(int dif, NimTree tree) {
		if (dif == 2)
			if (Math.random() > 0.5)
				dif += 1;
			else
				dif -= 1;
		switch (dif) {
		case 1:
			if (Math.random() > 0.5 && tree.canRemoveTwo()) {
				tree.removeTwo();
				System.out.println("\nThe AI removed two coins\n");
			} else {
				tree.removeOne();
				System.out.println("\nThe AI removed one\n");
			}
			break;
		case 3:
			if (tree.nextBestMove() == 2 && tree.canRemoveTwo()) {
				tree.removeTwo();
				System.out.println("\nThe AI removed two\n");
			} else {
				tree.removeOne();
				System.out.println("\nThe AI removed one\n");
			}
			break;
		}
	}

	public static void printCoins(NimTree tree) {
		if (tree.getCurrentCoins() > 1) {
			System.out.println(String.format("There are %d coins remaining.", tree.getCurrentCoins()));
		} else if (tree.getCurrentCoins() == 1) {
			System.out.println("There is 1 coin remaining.");
		} else {
			System.out.println("There are no coins remaining.");
		}
	}

	public static class InvalidChoiceException extends Exception {
	}
}
