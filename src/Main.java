import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;

public class Main {

	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		
		List<Game> games = new LinkedList<>();
		
		int option;
		
		do {
			
			System.out.println("1. Start Game");
			System.out.println("2. Show last 5 games");
			System.out.println("3. Reset");
			System.out.println("4. Exit");
			System.out.print("Enter option: ");
			
			option = scanner.nextInt();
			
			if (option == 1) {
				startGame(games);
			}
			else if (option == 2) {
				showLastRecords(games, 5);
			}
			else if (option == 3) {
				games.clear();
				System.out.println("History Cleared");
			}
			
			System.out.println();

		} while (option > 0 && option < 4);
	}
	
	private static void startGame(List<Game> games) {
		Game game = new Game();
		games.add(game);
		game.start();
	}
	
	private static void showLastRecords(List<Game> games, int n) {
		if (games.isEmpty()) {
			System.out.println("No games records.");
		}
		else if (games.size() <= n) {
			games.stream().forEach(printGameHistoryConsumer());
		}
		else {
			games.stream().skip(games.size() - n).forEach(printGameHistoryConsumer());
		}
	}
	
	private static Consumer<Game> printGameHistoryConsumer() {
		return x -> {
			x.printGameResult();
		};
	}
}
