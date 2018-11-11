import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Board {
	
	private static final int BOARD_SIZE = 3;
	
	private List<Location> locations = new ArrayList<>();
	
	public Board() {
		for (int i = 1; i <= Board.BOARD_SIZE; i++) {
			for (int j = 1; j <= Board.BOARD_SIZE; j++) {
				locations.add(new Location(i, j));
			}
		}
	}
	
	public boolean draw(Player player, int x, int y) {
		
		if (this.isGameOver() == true) {
			System.out.println("Game Over");
			return false;
		}
		
		// Find location using x, y coordinate
		Location cell = locations.stream().filter(isMatchLocation(x, y)).findFirst().orElse(null);
		
		if (cell == null) {
			System.out.println("Invalid location input!");
		}
		else if (cell.isAvailable() == false) {
			System.out.println("Location are not available");
		}
		else if (cell.isAvailable() == true) {
			cell.setPlayer(player);
			return true;
		}
		
		return false;
	}
	
	public List<Location> getAvailableLocations() {
		return this.locations.stream().filter(Location::isAvailable).collect(Collectors.toList());
	}
	
	public Player getWinner() {
		
		Player winner = Player.NONE;
		
		// Check rows and columns
		for (int i = 1; i <= Board.BOARD_SIZE; i++) {
			
			winner = this.getWinner(getRow(i));
			
			if (winner != Player.NONE) {
				return winner;
			}
			
			winner = this.getWinner(getColumn(i));
			
			if (winner != Player.NONE) {
				return winner;
			}
		}
		
		// Check diagonal winner
		Location[] cells = locations.stream().filter(x -> x.getX() == x.getY()).toArray(Location[]::new);
		
		winner = this.getWinner(cells);
		
		if (winner != Player.NONE) {
			return winner;
		}
		
		cells = new Location[Board.BOARD_SIZE];
		
		// Get location in {1,3} {2,2} {3,1}
		for (int i = 1, j = Board.BOARD_SIZE, index = 0; i <= Board.BOARD_SIZE; i++, j--, index++) {
			cells[index] = locations.stream().filter(isMatchLocation(i, j)).findFirst().get();
		}
		
		winner = this.getWinner(cells);
		
		if (winner != Player.NONE) {
			return winner;
		}
		
		return Player.NONE;
	}
	
	private Player getWinner(Location[] cells) {
		// Return player if all are same, else, return NONE
		Player player = cells[0].getPlayer();
		return Arrays.stream(cells).allMatch(x -> x.getPlayer() == player) ? player : Player.NONE;
	}
	
	private Location[] getRow(int n) {
		return locations.stream().filter(x -> x.getX() == n).toArray(Location[]::new);
	}
	
	private Location[] getColumn(int n) {
		return locations.stream().filter(x -> x.getY() == n).toArray(Location[]::new);
	}
	
	public boolean isGameOver() {
		return this.getAvailableLocations().isEmpty() || this.getWinner() != Player.NONE;
	}
	
	public void printBoard() {
		for (int i = 1; i <= Board.BOARD_SIZE; i++) {
			String str = Arrays.stream(this.getRow(i)).map(x -> String.valueOf(x.getPlayer().getSymbol())).collect(Collectors.joining("|"));
			System.out.println("|" + str + "|");
		}
	}

	private Predicate<Location> isMatchLocation(int x, int y) {
		return (t) -> t.isMatchLocation(x, y);
	}
}
