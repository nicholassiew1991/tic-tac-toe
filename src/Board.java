import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Board {
	
	private Cell[][] tbl = new Cell[3][3];
	
	private Supplier<Stream<Cell>> flatTable = () -> Arrays.stream(tbl).flatMap(Arrays::stream);
	
	public Board() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				tbl[i][j] = new Cell(i + 1, j + 1);
			}
		}
	}
	
	public int getBaordSize() {
		return tbl.length;
	}
	
	public boolean draw(Player player, int x, int y) {
		
		if (this.isGameOver() == true) {
			System.out.println("Game Over");
			return false;
		}
		
		if (this.getAvailableCells().isEmpty()) {
			System.out.println("No cells available");
			return false;
		}
		
		Cell cell = flatTable.get().filter(a -> a.isMatchLocation(x, y)).findFirst().orElse(null);
		
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
	
	public List<Cell> getAvailableCells() {
		return this.flatTable.get().filter(Cell::isAvailable).collect(Collectors.toList());
	}
	
	public Player getWinner() {
		
		Player winner = Player.NONE;
		
		// Check rows and columns
		for (int i = 1; i <= tbl.length; i++) {
			
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
		Cell[] cells = flatTable.get().filter(x -> x.getX() == x.getY()).toArray(Cell[]::new);
		
		winner = this.getWinner(cells);
		
		if (winner != Player.NONE) {
			return winner;
		}
		
		cells = new Cell[tbl.length];
		
		for (int i = 1, j = tbl.length, index = 0; i <= tbl.length; i++, j--, index++) {
			cells[index] = flatTable.get().filter(isMatchLocation(i, j)).findFirst().get();
		}
		
		winner = this.getWinner(cells);
		
		if (winner != Player.NONE) {
			return winner;
		}
		
		return Player.NONE;
	}
	
	private Player getWinner(Cell[] cells) {
		
		Player player = cells[0].getPlayer();
		
		if (player == Player.NONE) {
			return Player.NONE;
		}
		
		return Arrays.stream(cells).allMatch(x -> x.getPlayer() == player) ? player : Player.NONE;
	}
	
	private Cell[] getRow(int n) {
		return flatTable.get().filter(x -> x.getX() == n).toArray(Cell[]::new);
	}
	
	private Cell[] getColumn(int n) {
		return flatTable.get().filter(x -> x.getY() == n).toArray(Cell[]::new);
	}
	
	public boolean isGameOver() {
		return this.getAvailableCells().isEmpty() || this.getWinner() != Player.NONE;
	}
	
	public void printBoard() {

		for (int i = 0; i < tbl.length; i++) {
			
			System.out.print("|");
			
			for (int j = 0; j < tbl[i].length; j++) {
				System.out.print(tbl[i][j].getPlayer().getSymbol() + "|");
			}
			
			System.out.println();
		}
	}
	
	private Predicate<Cell> isMatchLocation(int x, int y) {
		return (t) -> t.isMatchLocation(x, y);
	}
}
