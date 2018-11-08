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
	
	public void draw(Player player, int x, int y) {
		
		Cell cell = flatTable.get().filter(a -> a.isMatchLocation(x, y)).findFirst().orElse(null);
		
		if (cell != null && cell.isAvailable() == true) {
			cell.setPlayer(player);
		}
	}
	
	public List<Cell> getAvailableCells() {
		return this.flatTable.get().filter(Cell::isAvailable).collect(Collectors.toList());
	}
	
	public boolean isPlayerWin(Player player) {
		
		Predicate<Cell> isMatchPlayer = (x) -> x.getPlayer() == player;
		
		for (int i = 1; i <= 3; i++) {
			
			int a = i;
			
			if (flatTable.get().filter(x -> x.getX() == a).allMatch(isMatchPlayer) == true) {
				return true;
			}
			
			if (flatTable.get().filter(y -> y.getY() == a).allMatch(isMatchPlayer) == true) {
				return true;
			}
		}
		
		return false;
	}
	
	public Player getWinner() {
		
		for (int i = 1; i <= 3; i++) {
			
			int a = i;
			
			flatTable.get().filter(x -> x.getX() == a).collect(Collectors.toList());
		}
		
		return null;
	}
	
	public boolean isGameOver() {
		return this.getAvailableCells().isEmpty();
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

}
