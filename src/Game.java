import java.util.Arrays;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Game {

	private Board board;
	private Player currentPlayer;
	private Player winner;
	private Map<Player, Integer> playerStep;
	
	public Game() {
		
		board = new Board();
		playerStep = new HashMap<>();
		currentPlayer = Player.O;
		
		playerStep.put(Player.X, 0);
		playerStep.put(Player.O, 0);
	}
	
	public void start() {
		
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Start Game");
		
		do {
			System.out.println();
			board.printBoard();
			System.out.println();
			
			String strLocations = board.getAvailableLocations().stream().map(Object::toString).collect(Collectors.joining(", "));
			System.out.println("Available location: " + strLocations);
			
			try {
				System.out.printf("Player %c - Enter location x: ", currentPlayer.getSymbol());
				int x = scanner.nextInt();
				System.out.printf("Player %c - Enter location y: ", currentPlayer.getSymbol());
				int y = scanner.nextInt();
				
				Location location = board.getAvailableLocations().stream().filter(a -> a.getX() == x && a.getY() == y).findAny().orElse(null);
				
				if (location == null) {
					System.out.println("Location is invalid or not available.");
					continue;
				}
				
				boolean drawResult = board.draw(currentPlayer, x, y);
				
				if (drawResult == true) {
					updatePlayerStep();
					nextPlayer();
				}
			}
			catch (InputMismatchException e) {
				System.err.println("Invalid input.");
				scanner.nextLine(); // clear buffer in scanner when input format invalid
				continue;
			}
			
		} while (board.isGameOver() == false);

		System.out.println();
		System.out.println("Game Over");
		this.winner = board.getWinner();
		this.printGameResult();
		
		scanner.close();
	}
	
	public void printGameResult() {
		System.out.println();
		System.out.println("--------------------------------------------");
		board.printBoard();
		System.out.println();
		
		if (this.winner == Player.NONE) {
			System.out.println("Draw");
		}
		else {
			System.out.println("Winner: Player " + this.winner.getSymbol());
			System.out.println("Steps taken: " + this.playerStep.get(this.winner));
		}
		
		System.out.println("--------------------------------------------");
	}
	
	private void updatePlayerStep() {
		int step = this.playerStep.get(currentPlayer) + 1;
		this.playerStep.put(currentPlayer, step);
	}
	
	private void nextPlayer() {
		if (currentPlayer == Player.O) {
			currentPlayer = Player.X;
		}
		else if (currentPlayer == Player.X) {
			currentPlayer = Player.O;
		}
		else {
			currentPlayer = Player.NONE;
		}
	}
}
