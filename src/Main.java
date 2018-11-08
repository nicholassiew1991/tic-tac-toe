
public class Main {

	public static void main(String[] args) {
		
		Board b = new Board();
		b.printBoard();
		
		b.getAvailableCells().stream().filter(x -> x.getX() == x.getY()).forEach(x -> {
			System.out.println(x.getX() + ", " + x.getY());
		});
	}
}
