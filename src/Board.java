import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Board {
	
	private Location[][] table = new Location[3][3];
	
	private Supplier<Stream<Location>> flatTable = () -> Arrays.stream(table).flatMap(Arrays::stream);
	
	public Board() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				table[i][j] = new Location(i + 1, j + 1);
			}
		}
	}
	
	public int getBaordSize() {
		return table.length;
	}
	
	public boolean draw(Player player, int x, int y) {
		
		if (this.isGameOver() == true) {
			System.out.println("Game Over");
			return false;
		}
		
		if (this.getAvailableLocations().isEmpty()) {
			System.out.println("No cells available");
			return false;
		}
		
		Location cell = flatTable.get().filter(a -> a.isMatchLocation(x, y)).findFirst().orElse(null);
		
		if (cell == null) {
			System.out.println("Invalid location input!");
		}
		else if (cell.isAvailable() == false) {
			System.out.println("Cell are not available");
		}
		else if (cell.isAvailable() == true) {
			cell.setPlayer(player);
			return true;
		}
		
		return false;
	}
	
	public List<Location> getAvailableLocations() {
		return this.flatTable.get().filter(Location::isAvailable).collect(Collectors.toList());
	}
	
	public Player getWinner() {
		
		Player winner = Player.NONE;
		
		// Check rows and columns
		for (int i = 1; i <= table.length; i++) {
			
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
		Location[] cells = flatTable.get().filter(x -> x.getX() == x.getY()).toArray(Location[]::new);
		
		winner = this.getWinner(cells);
		
		if (winner != Player.NONE) {
			return winner;
		}
		
		cells = new Location[table.length];
		
		for (int i = 1, j = table.length, index = 0; i <= table.length; i++, j--, index++) {
			cells[index] = flatTable.get().filter(isMatchLocation(i, j)).findFirst().get();
		}
		
		winner = this.getWinner(cells);
		
		if (winner != Player.NONE) {
			return winner;
		}
		
		return Player.NONE;
	}
	
	private Player getWinner(Location[] cells) {
		
		Player player = cells[0].getPlayer();
		
		if (player == Player.NONE) {
			return Player.NONE;
		}
		
		return Arrays.stream(cells).allMatch(x -> x.getPlayer() == player) ? player : Player.NONE;
	}
	
	private Location[] getRow(int n) {
		return flatTable.get().filter(x -> x.getX() == n).toArray(Location[]::new);
	}
	
	private Location[] getColumn(int n) {
		return flatTable.get().filter(x -> x.getY() == n).toArray(Location[]::new);
	}
	
	public boolean isGameOver() {
		return this.getAvailableLocations().isEmpty() || this.getWinner() != Player.NONE;
	}
	
	public void printBoard() {
		for (int i = 1; i <= table.length; i++) {
			String str = Arrays.stream(this.getRow(i)).map(x -> String.valueOf(x.getPlayer().getSymbol())).collect(Collectors.joining("|"));
			System.out.println("|" + str + "|");
		}
	}

	private Predicate<Location> isMatchLocation(int x, int y) {
		return (t) -> t.isMatchLocation(x, y);
	}
}
